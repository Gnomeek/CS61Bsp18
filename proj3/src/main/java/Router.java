import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {

    private static class Node implements Comparable<Node> {
        Long id;
        double priority;
        Node pre;

        public Node(long curID, double priority, Node prev) {
            this.id = curID;
            this.priority = priority;
            this.pre = prev;
        }

        @Override
        public int compareTo(Node node) {
            return Double.compare(this.priority, node.priority);
        }

        @Override
        public boolean equals(Object o) {
            if (this.getClass() != o.getClass()) {
                return false;
            }
            return this.id.equals(((Node) o).id);
        }

        @Override
        public int hashCode() {
            return (int) (long) this.id;
        }
    }

    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        PriorityQueue<Node> fringe = new PriorityQueue<>();
        HashSet<Node> marked = new HashSet<>();
        HashMap<Long, Double> bestKnownDistance = new HashMap<>();
        List<Long> ans = new LinkedList<>();
        boolean foundPath = false;

        long startID = g.closest(stlon, stlat);
        long endID = g.closest(destlon, destlat);
        double distance = g.distance(startID, endID);

        fringe.add(new Node(startID, distance, null));
        bestKnownDistance.put(startID, 0.0);

        while (!foundPath && !fringe.isEmpty()) {
            Node v = fringe.poll();
            marked.add(v);
            if (v.id == endID) {
                foundPath = true;
                ArrayList<Long> reverseSol = new ArrayList<>();
                Node mover = v;
                while (mover != null) {
                    reverseSol.add(mover.id);
                    mover = mover.pre;
                }

                for (int i = 0; i < reverseSol.size(); i++) {
                    ans.add(reverseSol.get(reverseSol.size() - i - 1));
                }
                return ans;
            }

            for (long w : g.adjacent(v.id)) {
                double edVW = g.distance(v.id, w);
                double dSV = bestKnownDistance.get(v.id);
                double dHW = g.distance(w, endID);

                if (!bestKnownDistance.containsKey(w)
                        || bestKnownDistance.get(w) > (edVW + dSV)) {
                    bestKnownDistance.put(w, edVW + dSV);
                    fringe.add(new Node(w, edVW + dSV + dHW, v));
                }
            }
        }
        return ans;
    }


    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     * still have some minor problems
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        List<NavigationDirection> ans = new LinkedList<>();
        String curName = NavigationDirection.UNKNOWN_ROAD;
        int curDirection = NavigationDirection.START;
        long curID = route.get(0);
        long nexID = route.get(1);
        for (int j = 0; j < g.adjacent.get(curID).size(); j += 1) {
            if (g.adjacent.get(curID).get(j).verticeID == nexID) {
                if (g.adjacent.get(curID).get(j).name != null) {
                    curName = g.adjacent.get(curID).get(j).name;
                }
            }
        }
        double distance = g.distance(curID, nexID);
        double curBearing = g.bearing(curID, nexID);

        for (int i = 1; i < route.size() - 1; i += 1) {
            String nexName = NavigationDirection.UNKNOWN_ROAD;
            curID = route.get(i);
            nexID = route.get(i + 1);
            double nexBearing = g.bearing(curID, nexID);

            for (int j = 0; j < g.adjacent.get(curID).size(); j += 1) {
                if (g.adjacent.get(curID).get(j).verticeID == nexID) {
                    if (g.adjacent.get(curID).get(j).name != null) {
                        nexName = g.adjacent.get(curID).get(j).name;
                    }
                }
            }

            if (!nexName.equals(curName)) {

                NavigationDirection naviOnce = new NavigationDirection();
                naviOnce.way = curName;
                naviOnce.direction = curDirection;
                naviOnce.distance = distance;
                //update information
                distance = g.distance(curID, nexID);
                ans.add(naviOnce);
                curName = nexName;
                curDirection = NavigationDirection.getDirection(nexBearing - curBearing);
            } else {
                distance += g.distance(curID, nexID);
            }

            curBearing = nexBearing;

            if (i == route.size() - 2) {
                NavigationDirection endNavi = new NavigationDirection();
                endNavi.direction = curDirection;
                endNavi.way = curName;
                endNavi.distance = distance;
                ans.add(endNavi);
            }
        }
        return ans;
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }


        private static int getDirection(double angle) {
            if (angle >= -15 && angle <= 15) {
                return STRAIGHT;
            } else if (angle > 15 && angle <= 30) {
                return SLIGHT_RIGHT;
            } else if (angle > 30 && angle <= 100) {
                return RIGHT;
            } else if (angle > 100) {
                return SHARP_RIGHT;
            } else if (angle < -15 && angle >= -30) {
                return SLIGHT_LEFT;
            } else if (angle < -30 && angle >= -100) {
                return LEFT;
            } else {
                return SHARP_LEFT;
            }
        }
    }
}
