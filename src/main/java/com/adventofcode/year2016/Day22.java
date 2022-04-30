package com.adventofcode.year2016;

import com.adventofcode.graph.AStar;
import com.adventofcode.point.Direction;
import com.adventofcode.point.Point2D;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Day22 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day22.class);

    private static short parseString(String s) {
        return Short.parseShort(s.substring(0, s.length() - 1));
    }

    static List<GridNode> parseGridNodes(Scanner scanner) {
        List<GridNode> nodes = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("/dev/")) {
                GridNode node = GridNode.of(line);
                LOGGER.debug("Grid: {} -> {}", line, node);
                nodes.add(node);
            }
        }
        return nodes;
    }

    private static int distance(Point2D a, Point2D b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    static long distance(State a, State b) {
        return distance(a.empty(), a.goal()) + distance(a.goal(), b.goal()) + distance(b.empty(), b.goal());
    }

    /**
     * --- Day 22: Grid Computing ---
     *
     * You gain access to a massive storage cluster arranged in a grid; each
     * storage node is only connected to the four nodes directly adjacent to it
     * (three if the node is on an edge, two if it's in a corner).
     *
     * You can directly access data only on node /dev/grid/node-x0-y0, but you can
     * perform some limited actions on the other nodes:
     *
     *   - You can get the disk usage of all nodes (via df). The result of doing
     *     this is in your puzzle input.
     *   - You can instruct a node to move (not copy) all of its data to an
     *     adjacent node (if the destination node has enough space to receive the
     *     data). The sending node is left empty after this operation.
     *
     * Nodes are named by their position: the node named node-x10-y10 is adjacent
     * to nodes node-x9-y10, node-x11-y10, node-x10-y9, and node-x10-y11.
     *
     * Before you begin, you need to understand the arrangement of data on these
     * nodes. Even though you can only move data between directly connected nodes,
     * you're going to need to rearrange a lot of the data to get access to the
     * data you need. Therefore, you need to work out how you might be able to
     * shift data around.
     *
     * To do this, you'd like to count the number of viable pairs of nodes. A
     * viable pair is any two nodes (A,B), regardless of whether they are directly
     * connected, such that:
     *
     *   - Node A is not empty (its Used is not zero).
     *   - Nodes A and B are not the same node.
     *   - The data on node A (its Used) would fit on node B (its Avail).
     *
     * How many viable pairs of nodes are there?
     *
     * Your puzzle answer was 1034.
     */
    static int countViablePairs(Scanner scanner) {
        List<GridNode> nodes = parseGridNodes(scanner);

        int viablePairs = 0;
        for (int i = 0; i < nodes.size(); i++) {
            GridNode nodeA = nodes.get(i);
            for (int j = 0; j < nodes.size(); ++j) {
                GridNode nodeB = nodes.get(j);
                if (i != j && nodeA.used() > 0 && nodeA.used() <= nodeB.available()) {
                    viablePairs++;
                }
            }
        }
        return viablePairs;
    }

    /**
     * --- Part Two ---
     *
     * Now that you have a better understanding of the grid, it's time to get to
     * work.
     *
     * Your goal is to gain access to the data which begins in the node with y=0
     * and the highest x (that is, the node in the top-right corner).
     *
     * For example, suppose you have the following grid:
     *
     * Filesystem            Size  Used  Avail  Use%
     * /dev/grid/node-x0-y0   10T    8T     2T   80%
     * /dev/grid/node-x0-y1   11T    6T     5T   54%
     * /dev/grid/node-x0-y2   32T   28T     4T   87%
     * /dev/grid/node-x1-y0    9T    7T     2T   77%
     * /dev/grid/node-x1-y1    8T    0T     8T    0%
     * /dev/grid/node-x1-y2   11T    7T     4T   63%
     * /dev/grid/node-x2-y0   10T    6T     4T   60%
     * /dev/grid/node-x2-y1    9T    8T     1T   88%
     * /dev/grid/node-x2-y2    9T    6T     3T   66%
     *
     * In this example, you have a storage grid 3 nodes wide and 3 nodes tall. The
     * node you can access directly, node-x0-y0, is almost full. The node
     * containing the data you want to access, node-x2-y0 (because it has y=0 and
     * the highest x value), contains 6 terabytes of data - enough to fit on your
     * node, if only you could make enough space to move it there.
     *
     * Fortunately, node-x1-y1 looks like it has enough free space to enable you
     * to move some of this data around. In fact, it seems like all of the nodes
     * have enough space to hold any node's data (except node-x0-y2, which is much
     * larger, very full, and not moving any time soon). So, initially, the grid's
     * capacities and connections look like this:
     *
     * ( 8T/10T) --  7T/ 9T -- [ 6T/10T]
     *     |           |           |
     *   6T/11T  --  0T/ 8T --   8T/ 9T
     *     |           |           |
     *  28T/32T  --  7T/11T --   6T/ 9T
     *
     * The node you can access directly is in parentheses; the data you want
     * starts in the node marked by square brackets.
     *
     * In this example, most of the nodes are interchangable: they're full enough
     * that no other node's data would fit, but small enough that their data could
     * be moved around. Let's draw these nodes as .. The exceptions are the empty
     * node, which we'll draw as _, and the very large, very full node, which
     * we'll draw as #. Let's also draw the goal data as G. Then, it looks like
     * this:
     *
     * (.) .  G
     *  .  _  .
     *  #  .  .
     *
     * The goal is to move the data in the top right, G, to the node in
     * parentheses. To do this, we can issue some commands to the grid and
     * rearrange the data:
     *
     *   - Move data from node-y0-x1 to node-y1-x1, leaving node node-y0-x1
     *     empty:
     *
     *     (.) _  G
     *      .  .  .
     *      #  .  .
     *
     *   - Move the goal data from node-y0-x2 to node-y0-x1:
     *
     *     (.) G  _
     *      .  .  .
     *      #  .  .
     *
     *   - At this point, we're quite close. However, we have no deletion
     *     command, so we have to move some more data around. So, next, we move
     *     the data from node-y1-x2 to node-y0-x2:
     *
     *     (.) G  .
     *      .  .  _
     *      #  .  .
     *
     *   - Move the data from node-y1-x1 to node-y1-x2:
     *
     *     (.) G  .
     *      .  _  .
     *      #  .  .
     *
     *   - Move the data from node-y1-x0 to node-y1-x1:
     *
     *     (.) G  .
     *      _  .  .
     *      #  .  .
     *
     *   - Next, we can free up space on our node by moving the data from
     *     node-y0-x0 to node-y1-x0:
     *
     *     (_) G  .
     *      .  .  .
     *      #  .  .
     *
     *   - Finally, we can access the goal data by moving the it from node-y0-x1 to node-y0-x0:
     *
     *     (G) _  .
     *      .  .  .
     *      #  .  .
     *
     * So, after 7 steps, we've accessed the data we want. Unfortunately, each of
     * these moves takes time, and we need to be efficient:
     *
     * What is the fewest number of steps required to move your goal data to
     * node-x0-y0?
     *
     * Your puzzle answer was 1034.
     */
    static long countSteps(Scanner scanner, Point2D goal) {
        List<GridNode> nodes = parseGridNodes(scanner);
        Set<Pair<Point2D, Point2D>> pairs = new HashSet<>();

        for (int i = 0; i < nodes.size(); i++) {
            GridNode nodeA = nodes.get(i);
            for (int j = 0; j < nodes.size(); ++j) {
                GridNode nodeB = nodes.get(j);
                if (i != j && nodeA.used() > 0 && nodeA.used() <= nodeB.available()) {
                    pairs.add(Pair.of(nodeA.position(), nodeB.position()));
                }
            }
        }

        Set<Point2D> points = pairs.stream().map(Pair::getLeft).collect(Collectors.toSet());
        List<Point2D> emptyNodes = pairs.stream().map(Pair::getRight).distinct().toList();
        if (emptyNodes.size() > 1) {
            throw new IllegalStateException("Cannot use multiple empty nodes");
        }

        Point2D empty = emptyNodes.get(0);
        LOGGER.info("Empty node is {}", empty);
        points.add(empty);

        Map<Point2D, List<Point2D>> graph = new HashMap<>();
        for (Point2D point : points) {
            List<Point2D> neighbours = Arrays.stream(Direction.values()).map(point::move).filter(points::contains).toList();
            graph.put(point, neighbours);
        }

        return AStar.algorithmHeuristic(state -> state.nextStates(graph), (a, b) -> 1L, Day22::distance, State.of(goal, empty), State.of(Point2D.of(0, 0), Point2D.of(1, 0)));
    }

    private record State(Point2D goal, Point2D empty) {
        public static State of(Point2D goal, Point2D empty) {
            return new State(goal, empty);
        }

        public List<State> nextStates(Map<Point2D, List<Point2D>> graph) {
            List<Point2D> neighbours = graph.get(empty);
            List<State> next = new ArrayList<>();
            for (Point2D neighbour : neighbours) {
                if (goal.equals(neighbour)) {
                    next.add(State.of(empty, neighbour));
                } else {
                    next.add(State.of(goal, neighbour));
                }
            }
            return next;
        }
    }

    record GridNode(Point2D position, short size, short used, short available, short pct) {
        public static GridNode of(String line) {

            String[] array = Arrays.stream(line.split(" "))
                    .filter(StringUtils::isNotBlank)
                    .toArray(String[]::new);

            int[] ints = Arrays.stream(array[0].split("-"))
                    .skip(1)
                    .map(s -> s.substring(1))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            Point2D position = Point2D.of(ints[0], ints[1]);
            return new GridNode(position, parseString(array[1]), parseString(array[2]), parseString(array[3]), parseString(array[4]));
        }
    }
}
