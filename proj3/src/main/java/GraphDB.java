import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc.
     * Using HashMap to store vertices and edge information
     * */

    protected HashMap<Long, Node> vertice = new HashMap<>();
    protected HashMap<Long, ArrayList<Edge>> adjacent = new HashMap<>();

    public Node setNode(double nodeLon, double nodeLat) {
        Node node = new Node();
        node.lon = nodeLon;
        node.lat = nodeLat;
        return node;
    }

    public Edge setEdge(long verticeid) {
        Edge edge = new Edge();
        edge.verticeID = verticeid;
        return edge;
    }

    protected class Node {
        String name;
        double lon;
        double lat;
    }

    protected class Edge {
        String name;
        long verticeID;
        int maxSpeed;

    }

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        ArrayList<Long> unconnectedNode = new ArrayList<>();
        for (long v : vertices()) {
            if (!adjacent.containsKey(v)) {
                unconnectedNode.add(v);
            }
        }

        for (long unconnectedVertice : unconnectedNode) {
            vertice.remove(unconnectedVertice);
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        return vertice.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        ArrayList<Long> neighbors = new ArrayList<>();

        if (adjacent.containsKey(v)) {
            for (Edge edge : adjacent.get(v)) {
                neighbors.add(edge.verticeID);
            }
        }
        return neighbors;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double minDistance = Double.POSITIVE_INFINITY;
        long ans = 0;
        for (Long v : vertices()) {
            double curDistance = GraphDB.distance(vertice.get(v).lon, vertice.get(v).lat, lon, lat);
            if (curDistance < minDistance) {
                ans = v;
                minDistance = curDistance;
            }
        }
        return ans;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return vertice.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return vertice.get(v).lat;
    }



    //AutoCompletion part
    protected NameSet locations = new NameSet();

    public List<String> getLocationsByPrefix(String prefix) {
        return locations.keyWithPrefix(prefix);
    }


    public List<Map<String, Object>> getLocations(String locationName) {
        LinkedList<Map<String, Object>> result = new LinkedList<>();
        String clean = GraphDB.cleanString(locationName);
        if (locations.get(clean) == null) {
            return result;
        }
        for (long w : locations.get(clean).keySet()) {
            result.add(locations.get(clean).get(w));
        }
        return result;
    }

    public class NameSet {
        private static final int R = 27;
        private TrieNode root;
        private int keySize;

        private int ascToIndex(char c) {
            if (c == ' ') {
                return 0;
            } else {
                return c - 96;
            }
        }

        private char indexToAsc(char c) {
            if ((int) c == 0) {
                return ' ';
            } else {
                return (char) (c + 96);
            }
        }

        private class TrieNode {
            private HashMap<Long, HashMap<String, Object>> value;
            private TrieNode[] next = new TrieNode[R];
        }

        public HashMap<Long, HashMap<String, Object>> get(String key) {
            TrieNode x = get(root, key, 0);
            if (x == null) {
                return null;
            } else {
                return x.value;
            }
        }

        private TrieNode get(TrieNode x, String key, int depth) {
            if (x == null) {
                return null;
            }

            if (depth == key.length()) {
                return x;
            }

            char c = key.charAt(depth);
            return get(x.next[ascToIndex(c)], key, depth + 1);
        }

        public void put(String key, HashMap<String, Object> info) {
            if (key != null) {
                root = put(root, key, 0, info);
            }
        }

        private TrieNode put(TrieNode x, String key, int depth, HashMap<String, Object> info) {
            if (x == null) {
                x = new TrieNode();
            }
            if (depth == key.length()) {
                if (x.value == null) {
                    x.value = new HashMap<>();
                    keySize += 1;
                }
                x.value.put((Long) info.get("id"), info);
                return x;
            }
            char c = key.charAt(depth);
            x.next[ascToIndex(c)] = put(x.next[ascToIndex(c)], key, depth + 1, info);
            return x;
        }

        public LinkedList<String> keyWithPrefix(String prefix) {
            String cleanedPrefix = GraphDB.cleanString(prefix);
            LinkedList<String> ans = new LinkedList<>();
            TrieNode x = get(root, cleanedPrefix, 0);
            collect(x, new StringBuilder(cleanedPrefix), ans);
            return ans;
        }

        private void collect(TrieNode x, StringBuilder prefix, LinkedList<String> ans) {
            if (x == null) {
                return;
            }
            if (x.value != null) {
                for (long w : x.value.keySet()) {
                    ans.add((String) x.value.get(w).get("name"));
                }
            }
            for (char c = 0; c < R; c++) {
                prefix.append(indexToAsc(c));
                collect(x.next[c], prefix, ans);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }
}
