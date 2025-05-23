package com.adventofcode.year2019;

import com.adventofcode.common.graph.AStar;
import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.Point3D;
import com.adventofcode.common.point.map.CharMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;

public final class Day20 {
    private Day20() {
        // No-Op
    }

    /**
     * --- Day 20: Donut Maze ---
     * You notice a strange pattern on the surface of Pluto and land nearby to get a closer look. Upon closer inspection,
     * you realize you've come across one of the famous space-warping mazes of the long-lost Pluto civilization!
     * <p>
     * Because there isn't much space on Pluto, the civilization that used to live here thrived by inventing a method for
     * folding spacetime. Although the technology is no longer understood, mazes like this one provide a small glimpse
     * into the daily life of an ancient Pluto citizen.
     * <p>
     * This maze is shaped like a donut. Portals along the inner and outer edge of the donut can instantly teleport you
     * from one side to the other. For example:
     * <p>
     * A
     * A
     * #######.#########
     * #######.........#
     * #######.#######.#
     * #######.#######.#
     * #######.#######.#
     * #####  B    ###.#
     * BC...##  C    ###.#
     * ##.##       ###.#
     * ##...DE  F  ###.#
     * #####    G  ###.#
     * #########.#####.#
     * DE..#######...###.#
     * #.#########.###.#
     * FG..#########.....#
     * ###########.#####
     * Z
     * Z
     * This map of the maze shows solid walls (#) and open passages (.). Every maze on Pluto has a start (the open tile
     * next to AA) and an end (the open tile next to ZZ). Mazes on Pluto also have portals; this maze has three pairs of
     * portals: BC, DE, and FG. When on an open tile next to one of these labels, a single step can take you to the other
     * tile with the same label. (You can only walk on . tiles; labels and empty space are not traversable.)
     * <p>
     * One path through the maze doesn't require any portals. Starting at AA, you could go down 1, right 8, down 12,
     * left 4, and down 1 to reach ZZ, a total of 26 steps.
     * <p>
     * However, there is a shorter path: You could walk from AA to the inner BC portal (4 steps), warp to the outer BC
     * portal (1 step), walk to the inner DE (6 steps), warp to the outer DE (1 step), walk to the outer FG (4 steps),
     * warp to the inner FG (1 step), and finally walk to ZZ (6 steps). In total, this is only 23 steps.
     * <p>
     * Here is a larger example:
     * <p>
     * A
     * A
     * #################.#############
     * #.#...#...................#.#.#
     * #.#.#.###.###.###.#########.#.#
     * #.#.#.......#...#.....#.#.#...#
     * #.#########.###.#####.#.#.###.#
     * #.............#.#.....#.......#
     * ###.###########.###.#####.#.#.#
     * #.....#        A   C    #.#.#.#
     * #######        S   P    #####.#
     * #.#...#                 #......VT
     * #.#.#.#                 #.#####
     * #...#.#               YN....#.#
     * #.###.#                 #####.#
     * DI....#.#                 #.....#
     * #####.#                 #.###.#
     * ZZ......#               QG....#..AS
     * ###.###                 #######
     * JO..#.#.#                 #.....#
     * #.#.#.#                 ###.#.#
     * #...#..DI             BU....#..LF
     * #####.#                 #.#####
     * YN......#               VT..#....QG
     * #.###.#                 #.###.#
     * #.#...#                 #.....#
     * ###.###    J L     J    #.#.###
     * #.....#    O F     P    #.#...#
     * #.###.#####.#.#####.#####.###.#
     * #...#.#.#...#.....#.....#.#...#
     * #.#####.###.###.#.#.#########.#
     * #...#.#.....#...#.#.#.#.....#.#
     * #.###.#####.###.###.#.#.#######
     * #.#.........#...#.............#
     * #########.###.###.#############
     * B   J   C
     * U   P   P
     * Here, AA has no direct path to ZZ, but it does connect to AS and CP. By passing through AS, QG, BU, and JO, you
     * can reach ZZ in 58 steps.
     * <p>
     * In your maze, how many steps does it take to get from the open tile marked AA to the open tile marked ZZ?
     */
    public static long solveMaze(Scanner scanner) {
        CharMap map = CharMap.read(scanner, ignore -> true);
        return new DonutMaze(map).solveMaze();
    }

    /**
     * --- Part Two ---
     * Strangely, the exit isn't open when you reach it. Then, you remember: the ancient Plutonians were famous for building recursive spaces.
     * <p>
     * The marked connections in the maze aren't portals: they physically connect to a larger or smaller copy of the maze. Specifically, the labeled tiles around the inside edge actually connect to a smaller copy of the same maze, and the smaller copy's inner labeled tiles connect to yet a smaller copy, and so on.
     * <p>
     * When you enter the maze, you are at the outermost level; when at the outermost level, only the outer labels AA and ZZ function (as the start and end, respectively); all other outer labeled tiles are effectively walls. At any other level, AA and ZZ count as walls, but the other outer labeled tiles bring you one level outward.
     * <p>
     * Your goal is to find a path through the maze that brings you back to ZZ at the outermost level of the maze.
     * <p>
     * In the first example above, the shortest path is now the loop around the right side. If the starting level is 0, then taking the previously-shortest path would pass through BC (to level 1), DE (to level 2), and FG (back to level 1). Because this is not the outermost level, ZZ is a wall, and the only option is to go back around to BC, which would only send you even deeper into the recursive maze.
     * <p>
     * In the second example above, there is no path that brings you to ZZ at the outermost level.
     * <p>
     * Here is a more interesting example:
     * <p>
     * Z L X W       C
     * Z P Q B       K
     * ###########.#.#.#.#######.###############
     * #...#.......#.#.......#.#.......#.#.#...#
     * ###.#.#.#.#.#.#.#.###.#.#.#######.#.#.###
     * #.#...#.#.#...#.#.#...#...#...#.#.......#
     * #.###.#######.###.###.#.###.###.#.#######
     * #...#.......#.#...#...#.............#...#
     * #.#########.#######.#.#######.#######.###
     * #...#.#    F       R I       Z    #.#.#.#
     * #.###.#    D       E C       H    #.#.#.#
     * #.#...#                           #...#.#
     * #.###.#                           #.###.#
     * #.#....OA                       WB..#.#..ZH
     * #.###.#                           #.#.#.#
     * CJ......#                           #.....#
     * #######                           #######
     * #.#....CK                         #......IC
     * #.###.#                           #.###.#
     * #.....#                           #...#.#
     * ###.###                           #.#.#.#
     * XF....#.#                         RF..#.#.#
     * #####.#                           #######
     * #......CJ                       NM..#...#
     * ###.#.#                           #.###.#
     * RE....#.#                           #......RF
     * ###.###        X   X       L      #.#.#.#
     * #.....#        F   Q       P      #.#.#.#
     * ###.###########.###.#######.#########.###
     * #.....#...#.....#.......#...#.....#.#...#
     * #####.#.###.#######.#######.###.###.#.#.#
     * #.......#.......#.#.#.#.#...#...#...#.#.#
     * #####.###.#####.#.#.#.#.###.###.#.###.###
     * #.......#.....#.#...#...............#...#
     * #############.#.#.###.###################
     * A O F   N
     * A A D   M
     * One shortest path through the maze is the following:
     * <p>
     * Walk from AA to XF (16 steps)
     * Recurse into level 1 through XF (1 step)
     * Walk from XF to CK (10 steps)
     * Recurse into level 2 through CK (1 step)
     * Walk from CK to ZH (14 steps)
     * Recurse into level 3 through ZH (1 step)
     * Walk from ZH to WB (10 steps)
     * Recurse into level 4 through WB (1 step)
     * Walk from WB to IC (10 steps)
     * Recurse into level 5 through IC (1 step)
     * Walk from IC to RF (10 steps)
     * Recurse into level 6 through RF (1 step)
     * Walk from RF to NM (8 steps)
     * Recurse into level 7 through NM (1 step)
     * Walk from NM to LP (12 steps)
     * Recurse into level 8 through LP (1 step)
     * Walk from LP to FD (24 steps)
     * Recurse into level 9 through FD (1 step)
     * Walk from FD to XQ (8 steps)
     * Recurse into level 10 through XQ (1 step)
     * Walk from XQ to WB (4 steps)
     * Return to level 9 through WB (1 step)
     * Walk from WB to ZH (10 steps)
     * Return to level 8 through ZH (1 step)
     * Walk from ZH to CK (14 steps)
     * Return to level 7 through CK (1 step)
     * Walk from CK to XF (10 steps)
     * Return to level 6 through XF (1 step)
     * Walk from XF to OA (14 steps)
     * Return to level 5 through OA (1 step)
     * Walk from OA to CJ (8 steps)
     * Return to level 4 through CJ (1 step)
     * Walk from CJ to RE (8 steps)
     * Return to level 3 through RE (1 step)
     * Walk from RE to IC (4 steps)
     * Recurse into level 4 through IC (1 step)
     * Walk from IC to RF (10 steps)
     * Recurse into level 5 through RF (1 step)
     * Walk from RF to NM (8 steps)
     * Recurse into level 6 through NM (1 step)
     * Walk from NM to LP (12 steps)
     * Recurse into level 7 through LP (1 step)
     * Walk from LP to FD (24 steps)
     * Recurse into level 8 through FD (1 step)
     * Walk from FD to XQ (8 steps)
     * Recurse into level 9 through XQ (1 step)
     * Walk from XQ to WB (4 steps)
     * Return to level 8 through WB (1 step)
     * Walk from WB to ZH (10 steps)
     * Return to level 7 through ZH (1 step)
     * Walk from ZH to CK (14 steps)
     * Return to level 6 through CK (1 step)
     * Walk from CK to XF (10 steps)
     * Return to level 5 through XF (1 step)
     * Walk from XF to OA (14 steps)
     * Return to level 4 through OA (1 step)
     * Walk from OA to CJ (8 steps)
     * Return to level 3 through CJ (1 step)
     * Walk from CJ to RE (8 steps)
     * Return to level 2 through RE (1 step)
     * Walk from RE to XQ (14 steps)
     * Return to level 1 through XQ (1 step)
     * Walk from XQ to FD (8 steps)
     * Return to level 0 through FD (1 step)
     * Walk from FD to ZZ (18 steps)
     * This path takes a total of 396 steps to move from AA at the outermost layer to ZZ at the outermost layer.
     * <p>
     * In your maze, when accounting for recursion, how many steps does it take to get from the open tile marked AA to the open tile marked ZZ, both at the outermost layer?
     */
    public static long solveRecursiveMaze(Scanner scanner) {
        CharMap map = CharMap.read(scanner, ignore -> true);
        DonutMaze donutMaze = new DonutMaze(map);
        return donutMaze.solveRecursiveMaze();
    }

    public static class DonutMaze {
        private final Map<Point2D, List<Point2D>> graph = new HashMap<>();
        private final Map<String, List<Point2D>> wrap = new HashMap<>();

        private final Map<Point2D, Point2D> innerWraps = new HashMap<>();
        private final Map<Point2D, Point2D> outerWraps = new HashMap<>();

        public DonutMaze(CharMap map) {
            int yMax = map.yMax();
            int xMax = map.xMax();
            Set<Point2D> innerDoors = new HashSet<>();
            Set<Point2D> outerDoors = new HashSet<>();
            for (int y = 2; y <= yMax - 2; y++) {
                for (int x = 2; x <= xMax - 2; x++) {
                    if (map.get(x, y) == '.') {
                        Point2D d = Point2D.of(x, y);
                        for (Direction direction : Direction.values()) {
                            Point2D move = d.move(direction);
                            char c = map.get(move);
                            if (c == '.') {
                                graph.computeIfAbsent(d, ignore -> new ArrayList<>()).add(move);
                            } else if (Character.isAlphabetic(c)) {
                                Point2D shift = move.move(direction);
                                char cc = map.get(shift);
                                String name = getWrapName(direction, c, cc);
                                if (shift.x() < 2 || shift.x() > xMax - 2 || shift.y() < 2 || shift.y() > yMax - 2) {
                                    outerDoors.add(d);
                                } else {
                                    innerDoors.add(d);
                                }
                                wrap.computeIfAbsent(name, ignore -> new ArrayList<>()).add(d);
                            }
                        }
                    }
                }
            }


            for (List<Point2D> value : wrap.values()) {
                if (value.size() == 2) {
                    Point2D outer = value.stream().filter(outerDoors::contains).findFirst().orElse(null);
                    Point2D inner = value.stream().filter(innerDoors::contains).findFirst().orElse(null);

                    innerWraps.put(inner, outer);
                    outerWraps.put(outer, inner);
                }
            }
        }

        private static String getWrapName(Direction d, char c1, char c2) {
            return switch (d) {
                case UP, LEFT -> "" + c2 + c1;
                case DOWN, RIGHT -> "" + c1 + c2;
            };

        }

        private List<AStar.Move<Point3D>> flatNeighbours(Point3D node) {
            List<AStar.Move<Point3D>> neighbours = new ArrayList<>();
            graph.getOrDefault(node.project(), Collections.emptyList())
                    .stream()
                    .map(p -> new Point3D(p, node.z()))
                    .map(AStar.Move::of)
                    .forEach(neighbours::add);
            Point2D innerWrap = innerWraps.get(node.project());
            if (innerWrap != null) {
                neighbours.add(AStar.Move.of(new Point3D(innerWrap, node.z())));
            }
            Point2D outerWrap = outerWraps.get(node.project());
            if (outerWrap != null) {
                neighbours.add(AStar.Move.of(new Point3D(outerWrap, node.z())));
            }
            return neighbours;
        }

        private List<AStar.Move<Point3D>> recursiveNeighbours(Point3D node) {
            List<AStar.Move<Point3D>> neighbours = new ArrayList<>();
            graph.getOrDefault(node.project(), Collections.emptyList()).stream()
                    .map(p -> new Point3D(p, node.z()))
                    .map(AStar.Move::of)
                    .forEach(neighbours::add);
            Point2D innerWrap = innerWraps.get(node.project());
            if (innerWrap != null) {
                neighbours.add(AStar.Move.of(new Point3D(innerWrap, node.z() + 1)));
            }
            Point2D outerWrap = outerWraps.get(node.project());
            if (outerWrap != null && node.z() > 0) {
                neighbours.add(AStar.Move.of(new Point3D(outerWrap, node.z() - 1)));
            }
            return neighbours;
        }

        @SuppressWarnings("NullAway")
        public long solveMaze() {
            Point3D start = new Point3D(wrap.get("AA").getFirst(), 0);
            Point3D end = new Point3D(wrap.get("ZZ").getFirst(), 0);

            return new MazeAlgorithm(this::flatNeighbours).algorithm(start, end);
        }

        @SuppressWarnings("NullAway")
        public long solveRecursiveMaze() {
            Point3D start = new Point3D(wrap.get("AA").getFirst(), 0);
            Point3D end = new Point3D(wrap.get("ZZ").getFirst(), 0);

            return new MazeAlgorithm(this::recursiveNeighbours).algorithm(start, end);
        }
    }

    private static final class MazeAlgorithm extends AStar<Point3D> {

        private final Function<Point3D, Collection<Move<Point3D>>> graph;

        private MazeAlgorithm(Function<Point3D, Collection<Move<Point3D>>> graph) {
            this.graph = graph;
        }

        @Override
        public Iterable<Move<Point3D>> next(Point3D node) {
            return graph.apply(node);
        }

    }

}
