package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    /* Track the position of the start location of each line */
    private static class Position {
        int x = 0;
        int y = 0;
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        for (int i = 0; i < s; i += 1) {
            drawRow(world, p, s + 2 * i, t);
            updatePosition(p, i, s, 1);
        }
        for (int j = s - 1; j >= 0; j -= 1) {
            drawRow(world, p, s + 2 * j, t);
            updatePosition(p, j, s, 0);
        }
    }

    /* fill every line with tile, the length depends on p.y */
    private static void drawRow(TETile[][] world, Position p, int length, TETile t) {
        for (int i = 0; i < length; i += 1){
            world[p.x + i][p.y] = t;
        }

    }
    /** update position, p.y always increases while p.x needs to decrease
     *  and stay unchanged cuz the middle
     */
    private static void updatePosition(Position p, int row, int s, int signal) {
        if (signal > 0) {
            if (row != s - 1){
                p.x -= 1;
            }
        } else {
            p.x += 1;
        }
        p.y += 1;
    }


    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Position p = new Position();
        p.x = 11;
        p.y = 0;
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        addHexagon(world, p, 4, Tileset.WALL);


        ter.renderFrame(world);
    }
}


