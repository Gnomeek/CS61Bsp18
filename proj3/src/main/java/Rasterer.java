import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private int leftRowIndex;
    private int rightRowIndex;
    private int upperColIndex;
    private int lowerColIndex;
    private double lonPixelPerTile;
    private double latPixelPerTile;

    public Rasterer() {
        leftRowIndex = 0;
        rightRowIndex = 0;
        upperColIndex = 0;
        lowerColIndex = 0;
        lonPixelPerTile = 0;
        latPixelPerTile = 0;

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        double desiredLRLON = params.get("lrlon");
        double desiredULLON = params.get("ullon");
        double desiredLRLAT = params.get("lrlat");
        double desiredULLAT = params.get("ullat");
        double width = params.get("w");
        Map<String, Object> results = new HashMap<>();

        /*deal with unsatisfied parameters
        if (desiredULLON >= desiredLRLON || desiredULLAT <= desiredLRLAT || width <=0 || height <= 0
            || desiredULLON > MapServer.ROOT_LRLON || desiredLRLON < MapServer.ROOT_ULLON
            || desiredLRLAT > MapServer.ROOT_ULLAT || desiredULLAT < MapServer.ROOT_LRLAT) {
            results.put("raster_ul_lon", 0);
            results.put("raster_ul_lat", 0);
            results.put("raster_lr_lon", 0);
            results.put("raster_lr_lat", 0);
            results.put("depth", 0);
            results.put("query_success", false);
            results.put("render_grid", 0);
        }
        */

        results.put("query_success", true);

        int depth = getDepth(desiredLRLON, desiredULLON, width);
        results.put("depth", depth);

        findRasterTile(depth, desiredLRLON, desiredULLON, desiredLRLAT, desiredULLAT);
        results.put("raster_ul_lon", MapServer.ROOT_ULLON + lonPixelPerTile * leftRowIndex);
        results.put("raster_ul_lat", MapServer.ROOT_ULLAT - latPixelPerTile * upperColIndex);
        results.put("raster_lr_lon", MapServer.ROOT_ULLON + lonPixelPerTile * (rightRowIndex + 1));
        results.put("raster_lr_lat", MapServer.ROOT_ULLAT - latPixelPerTile * (lowerColIndex + 1));

        int rowNum = rightRowIndex - leftRowIndex + 1;
        int colNum = lowerColIndex - upperColIndex + 1;
        String[][] tileGrid = new String[colNum][rowNum];
        for (int i = 0; i < colNum; i += 1) {
            for (int j = 0; j < rowNum; j += 1) {
                tileGrid[i][j] = ("d" + depth + "_x" + (j + leftRowIndex)
                        + "_y" + (i + upperColIndex) + ".png");
            }
        }
        results.put("render_grid", tileGrid);

        return results;
    }

    public static int getDepth(double desiredLRLON, double desiredULLON, double width) {
        double desiredDDP = (desiredLRLON - desiredULLON) / width;
        double rootDDP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        int depth = 0;
        while (rootDDP > desiredDDP) {
            rootDDP /= 2;
            depth += 1;
            if (depth >= 7) {
                break;
            }
        }
        return depth;
    }

    public void findRasterTile(int depth, double desiredLRLON, double desiredULLON,
                                         double desiredLRLAT, double desiredULLAT) {
        lonPixelPerTile = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, depth);
        latPixelPerTile = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / Math.pow(2, depth);

        leftRowIndex = (int) ((desiredULLON - MapServer.ROOT_ULLON) / lonPixelPerTile);
        rightRowIndex = (int) ((desiredLRLON - MapServer.ROOT_ULLON) / lonPixelPerTile);
        upperColIndex = (int) ((MapServer.ROOT_ULLAT - desiredULLAT) / latPixelPerTile);
        lowerColIndex = (int) ((MapServer.ROOT_ULLAT - desiredLRLAT) / latPixelPerTile);
    }

    /*
    public static void main(String[] args) {
        Map<String, Double> parameters = new HashMap<>();

        parameters.put("lrlon", -122.2104604264636);
        parameters.put("ullon", -122.30410170759153);
        parameters.put("w", 1091.0);
        parameters.put("h", 566);
        parameters.put("ullat", 37.870213571328854);
        parameters.put("lrlat", 37.8318576119893);

        Map<String, Object> results = getMapRaster(parameters);
    }
    */
}
