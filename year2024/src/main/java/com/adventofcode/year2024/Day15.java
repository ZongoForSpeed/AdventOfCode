package com.adventofcode.year2024;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public final class Day15 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day15.class);

    private Day15() {
        // No-Op
    }

    private static Direction direction(char command) {
        return switch (command) {
            case '^' -> Direction.UP;
            case 'v' -> Direction.DOWN;
            case '<' -> Direction.LEFT;
            case '>' -> Direction.RIGHT;
            default -> throw new IllegalStateException("Unknown command: " + command);
        };
    }

    /**
     * --- Day 15: Warehouse Woes ---
     * <p>
     * You appear back inside your own mini submarine! Each Historian drives their
     * mini submarine in a different direction; maybe the Chief has his own
     * submarine down here somewhere as well?
     * <p>
     * You look up to see a vast school of lanternfish swimming past you. On
     * closer inspection, they seem quite anxious, so you drive your mini
     * submarine over to see if you can help.
     * <p>
     * Because lanternfish populations grow rapidly, they need a lot of food, and
     * that food needs to be stored somewhere. That's why these lanternfish have
     * built elaborate warehouse complexes operated by robots!
     * <p>
     * These lanternfish seem so anxious because they have lost control of the
     * robot that operates one of their most important warehouses! It is currently
     * running amok, pushing around boxes in the warehouse with no regard for
     * lanternfish logistics or lanternfish inventory management strategies.
     * <p>
     * Right now, none of the lanternfish are brave enough to swim up to an
     * unpredictable robot so they could shut it off. However, if you could
     * anticipate the robot's movements, maybe they could find a safe option.
     * <p>
     * The lanternfish already have a map of the warehouse and a list of movements
     * the robot will attempt to make (your puzzle input). The problem is that the
     * movements will sometimes fail as boxes are shifted around, making the
     * actual movements of the robot difficult to predict.
     * <p>
     * For example:
     * <p>
     * ##########
     * #..O..O.O#
     * #......O.#
     * #.OO..O.O#
     * #..O@..O.#
     * #O#..O...#
     * #O..O..O.#
     * #.OO.O.OO#
     * #....O...#
     * ##########
     *
     * <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
     * vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
     * ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
     * <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
     * ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
     * ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
     * >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
     * <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
     * ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
     * v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
     * <p>
     * As the robot (@) attempts to move, if there are any boxes (O) in the way,
     * the robot will also attempt to push those boxes. However, if this action
     * would cause the robot or a box to move into a wall (#), nothing moves
     * instead, including the robot. The initial positions of these are shown on
     * the map at the top of the document the lanternfish gave you.
     * <p>
     * The rest of the document describes the moves (^ for up, v for down, < for
     * left, > for right) that the robot will attempt to make, in order. (The
     * moves form a single giant sequence; they are broken into multiple lines
     * just to make copy-pasting easier. Newlines within the move sequence should
     * be ignored.)
     * <p>
     * Here is a smaller example to get started:
     * <p>
     * ########
     * #..O.O.#
     * ##@.O..#
     * #...O..#
     * #.#.O..#
     * #...O..#
     * #......#
     * ########
     * <p>
     * <^^>>>vv<v>>v<<
     * <p>
     * Were the robot to attempt the given sequence of moves, it would push around
     * the boxes as follows:
     * <p>
     * Initial state:
     * ########
     * #..O.O.#
     * ##@.O..#
     * #...O..#
     * #.#.O..#
     * #...O..#
     * #......#
     * ########
     * <p>
     * Move <:
     * ########
     * #..O.O.#
     * ##@.O..#
     * #...O..#
     * #.#.O..#
     * #...O..#
     * #......#
     * ########
     * <p>
     * Move ^:
     * ########
     * #.@O.O.#
     * ##..O..#
     * #...O..#
     * #.#.O..#
     * #...O..#
     * #......#
     * ########
     * <p>
     * Move ^:
     * ########
     * #.@O.O.#
     * ##..O..#
     * #...O..#
     * #.#.O..#
     * #...O..#
     * #......#
     * ########
     * <p>
     * Move >:
     * ########
     * #..@OO.#
     * ##..O..#
     * #...O..#
     * #.#.O..#
     * #...O..#
     * #......#
     * ########
     * <p>
     * Move >:
     * ########
     * #...@OO#
     * ##..O..#
     * #...O..#
     * #.#.O..#
     * #...O..#
     * #......#
     * ########
     * <p>
     * Move >:
     * ########
     * #...@OO#
     * ##..O..#
     * #...O..#
     * #.#.O..#
     * #...O..#
     * #......#
     * ########
     * <p>
     * Move v:
     * ########
     * #....OO#
     * ##..@..#
     * #...O..#
     * #.#.O..#
     * #...O..#
     * #...O..#
     * ########
     * <p>
     * Move v:
     * ########
     * #....OO#
     * ##..@..#
     * #...O..#
     * #.#.O..#
     * #...O..#
     * #...O..#
     * ########
     * <p>
     * Move <:
     * ########
     * #....OO#
     * ##.@...#
     * #...O..#
     * #.#.O..#
     * #...O..#
     * #...O..#
     * ########
     * <p>
     * Move v:
     * ########
     * #....OO#
     * ##.....#
     * #..@O..#
     * #.#.O..#
     * #...O..#
     * #...O..#
     * ########
     * <p>
     * Move >:
     * ########
     * #....OO#
     * ##.....#
     * #...@O.#
     * #.#.O..#
     * #...O..#
     * #...O..#
     * ########
     * <p>
     * Move >:
     * ########
     * #....OO#
     * ##.....#
     * #....@O#
     * #.#.O..#
     * #...O..#
     * #...O..#
     * ########
     * <p>
     * Move v:
     * ########
     * #....OO#
     * ##.....#
     * #.....O#
     * #.#.O@.#
     * #...O..#
     * #...O..#
     * ########
     * <p>
     * Move <:
     * ########
     * #....OO#
     * ##.....#
     * #.....O#
     * #.#O@..#
     * #...O..#
     * #...O..#
     * ########
     * <p>
     * Move <:
     * ########
     * #....OO#
     * ##.....#
     * #.....O#
     * #.#O@..#
     * #...O..#
     * #...O..#
     * ########
     * <p>
     * The larger example has many more moves; after the robot has finished those
     * moves, the warehouse would look like this:
     * <p>
     * ##########
     * #.O.O.OOO#
     * #........#
     * #OO......#
     * #OO@.....#
     * #O#.....O#
     * #O.....OO#
     * #O.....OO#
     * #OO....OO#
     * ##########
     * <p>
     * The lanternfish use their own custom Goods Positioning System (GPS for
     * short) to track the locations of the boxes. The GPS coordinate of a box is
     * equal to 100 times its distance from the top edge of the map plus its
     * distance from the left edge of the map. (This process does not stop at wall
     * tiles; measure all the way to the edges of the map.)
     * <p>
     * So, the box shown below has a distance of 1 from the top edge of the map
     * and 4 from the left edge of the map, resulting in a GPS coordinate of
     * 100 * 1 + 4 = 104.
     * <p>
     * #######
     * #...O..
     * #......
     * <p>
     * The lanternfish would like to know the sum of all boxes' GPS coordinates
     * after the robot finishes moving. In the larger example, the sum of all
     * boxes' GPS coordinates is 10092. In the smaller example, the sum is 2028.
     * <p>
     * Predict the motion of the robot and boxes in the warehouse. After the robot
     * is finished moving, what is the sum of all boxes' GPS coordinates?
     */
    public static int partOne(Scanner scanner, boolean debug) {
        CharMap map = CharMap.read(scanner, _ -> true, true);

        LOGGER.info("map :\n{}", map);
        Set<Point2D> walls = new HashSet<>();
        Set<Point2D> boxes = new HashSet<>();
        Point2D robot = null;
        for (ObjectCharPair<Point2D> entry : map.entries()) {
            switch (entry.rightChar()) {
                case '#' -> walls.add(entry.left());
                case 'O' -> boxes.add(entry.left());
                case '@' -> robot = entry.left();
                default -> {
                }
            }
        }

        if (robot == null) {
            throw new IllegalStateException("Robot is null");
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); ++i) {
                Direction direction = direction(line.charAt(i));
                Point2D newRobot = robot.move(direction);
                if (walls.contains(newRobot)) {
                    // No-Op
                } else if (boxes.contains(newRobot)) {
                    Optional<Point2D> newBox = moveBox(direction, newRobot, walls, boxes);
                    if (newBox.isPresent()) {
                        boxes.remove(newRobot);
                        robot = newRobot;
                    }
                } else {
                    robot = newRobot;
                }

                if (debug) {
                    LOGGER.info("After '{}' command:", line.charAt(i));
                    print(walls, boxes, robot);
                }
            }
        }

        return boxes.stream().mapToInt(Day15::gpsCoordinates).sum();
    }

    private static Optional<Point2D> moveBox(Direction direction, Point2D box, Set<Point2D> walls, Set<Point2D> boxes) {
        Point2D move = box.move(direction);
        if (walls.contains(move)) {
            return Optional.empty();
        }

        if (boxes.add(move)) {
            return Optional.of(move);
        }

        Optional<Point2D> point2D = moveBox(direction, move, walls, boxes);
        if (point2D.isPresent()) {
            return Optional.of(move);
        }

        return Optional.empty();
    }

    private static int gpsCoordinates(Point2D box) {
        return box.y() * 100 + box.x();
    }

    private static void print(Set<Point2D> walls, Set<Point2D> boxes, Point2D robot) {
        CharMap map = new CharMap();
        walls.forEach(w -> map.set(w, '#'));
        boxes.forEach(w -> map.set(w, 'O'));
        map.set(robot, '@');

        LOGGER.info("\n{}", map);
    }

    /**
     * --- Part Two ---
     * <p>
     * The lanternfish use your information to find a safe moment to swim in and
     * turn off the malfunctioning robot! Just as they start preparing a festival
     * in your honor, reports start coming in that a second warehouse's robot is
     * also malfunctioning.
     * <p>
     * This warehouse's layout is surprisingly similar to the one you just helped.
     * There is one key difference: everything except the robot is twice as wide!
     * The robot's list of movements doesn't change.
     * <p>
     * To get the wider warehouse's map, start with your original map and, for
     * each tile, make the following changes:
     * <p>
     * - If the tile is #, the new map contains ## instead.
     * - If the tile is O, the new map contains [] instead.
     * - If the tile is ., the new map contains .. instead.
     * - If the tile is @, the new map contains @. instead.
     * <p>
     * This will produce a new warehouse map which is twice as wide and with wide
     * boxes that are represented by []. (The robot does not change size.)
     * <p>
     * The larger example from before would now look like this:
     * <p>
     * ####################
     * ##....[]....[]..[]##
     * ##............[]..##
     * ##..[][]....[]..[]##
     * ##....[]@.....[]..##
     * ##[]##....[]......##
     * ##[]....[]....[]..##
     * ##..[][]..[]..[][]##
     * ##........[]......##
     * ####################
     * <p>
     * Because boxes are now twice as wide but the robot is still the same size
     * and speed, boxes can be aligned such that they directly push two other
     * boxes at once. For example, consider this situation:
     * <p>
     * #######
     * #...#.#
     * #.....#
     * #..OO@#
     * #..O..#
     * #.....#
     * #######
     * <p>
     * <vv<<^^<<^^
     * <p>
     * After appropriately resizing this map, the robot would push around these
     * boxes as follows:
     * <p>
     * Initial state:
     * ##############
     * ##......##..##
     * ##..........##
     * ##....[][]@.##
     * ##....[]....##
     * ##..........##
     * ##############
     * <p>
     * Move <:
     * ##############
     * ##......##..##
     * ##..........##
     * ##...[][]@..##
     * ##....[]....##
     * ##..........##
     * ##############
     * <p>
     * Move v:
     * ##############
     * ##......##..##
     * ##..........##
     * ##...[][]...##
     * ##....[].@..##
     * ##..........##
     * ##############
     * <p>
     * Move v:
     * ##############
     * ##......##..##
     * ##..........##
     * ##...[][]...##
     * ##....[]....##
     * ##.......@..##
     * ##############
     * <p>
     * Move <:
     * ##############
     * ##......##..##
     * ##..........##
     * ##...[][]...##
     * ##....[]....##
     * ##......@...##
     * ##############
     * <p>
     * Move <:
     * ##############
     * ##......##..##
     * ##..........##
     * ##...[][]...##
     * ##....[]....##
     * ##.....@....##
     * ##############
     * <p>
     * Move ^:
     * ##############
     * ##......##..##
     * ##...[][]...##
     * ##....[]....##
     * ##.....@....##
     * ##..........##
     * ##############
     * <p>
     * Move ^:
     * ##############
     * ##......##..##
     * ##...[][]...##
     * ##....[]....##
     * ##.....@....##
     * ##..........##
     * ##############
     * <p>
     * Move <:
     * ##############
     * ##......##..##
     * ##...[][]...##
     * ##....[]....##
     * ##....@.....##
     * ##..........##
     * ##############
     * <p>
     * Move <:
     * ##############
     * ##......##..##
     * ##...[][]...##
     * ##....[]....##
     * ##...@......##
     * ##..........##
     * ##############
     * <p>
     * Move ^:
     * ##############
     * ##......##..##
     * ##...[][]...##
     * ##...@[]....##
     * ##..........##
     * ##..........##
     * ##############
     * <p>
     * Move ^:
     * ##############
     * ##...[].##..##
     * ##...@.[]...##
     * ##....[]....##
     * ##..........##
     * ##..........##
     * ##############
     * <p>
     * This warehouse also uses GPS to locate the boxes. For these larger boxes,
     * distances are measured from the edge of the map to the closest edge of the
     * box in question. So, the box shown below has a distance of 1 from the top
     * edge of the map and 5 from the left edge of the map, resulting in a GPS
     * coordinate of 100 * 1 + 5 = 105.
     * <p>
     * ##########
     * ##...[]...
     * ##........
     * <p>
     * In the scaled-up version of the larger example from above, after the robot
     * has finished all of its moves, the warehouse would look like this:
     * <p>
     * ####################
     * ##[].......[].[][]##
     * ##[]...........[].##
     * ##[]........[][][]##
     * ##[]......[]....[]##
     * ##..##......[]....##
     * ##..[]............##
     * ##..@......[].[][]##
     * ##......[][]..[]..##
     * ####################
     * <p>
     * The sum of these boxes' GPS coordinates is 9021.
     * <p>
     * Predict the motion of the robot and boxes in this new, scaled-up warehouse.
     * What is the sum of all boxes' final GPS coordinates?
     */
    public static int partTwo(Scanner scanner, boolean debug) {
        CharMap map = CharMap.read(scanner, _ -> true, true);

        Map<Point2D, Obstacle> obstacles = new HashMap<>();

        Point2D robot = null;

        for (ObjectCharPair<Point2D> entry : map.entries()) {
            switch (entry.rightChar()) {
                case '#' -> {
                    obstacles.put(new Point2D(2 * entry.left().x(), entry.left().y()), Obstacle.ROCK);
                    obstacles.put(new Point2D(2 * entry.left().x() + 1, entry.left().y()), Obstacle.ROCK);
                }
                case 'O' -> {
                    obstacles.put(new Point2D(2 * entry.left().x(), entry.left().y()), Obstacle.LEFT_BOX);
                    obstacles.put(new Point2D(2 * entry.left().x() + 1, entry.left().y()), Obstacle.RIGHT_BOX);
                }
                case '@' -> robot = new Point2D(2 * entry.left().x(), entry.left().y());
                default -> {
                    // No-Op
                }
            }
        }

        if (robot == null) {
            throw new IllegalStateException("Robot is null");
        }


        LOGGER.info("Initial State:");
        print(obstacles, robot);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); ++i) {
                Direction direction = direction(line.charAt(i));
                Point2D newRobot = robot.move(direction);
                Obstacle obstacle = obstacles.get(newRobot);
                if (obstacle == null) {
                    robot = newRobot;
                } else if (obstacle == Obstacle.ROCK) {
                    // No-Op
                } else {
                    Point2D friend = obstacle.friend(newRobot);
                    if (canMove(direction, newRobot, obstacles) && canMove(direction, friend, obstacles)) {
                        moveBox(direction, newRobot, friend, obstacles);
                        robot = newRobot;
                    }
                }

                if (debug) {
                    LOGGER.info("After '{}' command:", line.charAt(i));
                    print(obstacles, robot);
                }
            }
        }

        LOGGER.info("After all moves:");
        print(obstacles, robot);

        return obstacles.entrySet().stream()
                .filter(e -> e.getValue() == Obstacle.LEFT_BOX)
                .map(Map.Entry::getKey)
                .mapToInt(Day15::gpsCoordinates)
                .sum();
    }

    private static boolean canMove(Direction direction, Point2D box, Map<Point2D, Obstacle> obstacles) {
        Point2D move = box.move(direction);
        Obstacle obstacle = obstacles.get(move);
        if (obstacle == null) {
            return true;
        }
        if (obstacle == Obstacle.ROCK) {
            return false;
        }
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            return canMove(direction, move, obstacles);
        }
        Point2D friend = obstacle.friend(move);
        return canMove(direction, move, obstacles) && canMove(direction, friend, obstacles);
    }

    private static void moveBox(Direction direction, Point2D box1, Point2D box2, Map<Point2D, Obstacle> obstacles) {
        Obstacle remove1 = obstacles.remove(box1);
        Obstacle remove2 = obstacles.remove(box2);

        moveBox(direction, box1, obstacles, remove1);
        moveBox(direction, box2, obstacles, remove2);

        obstacles.put(box1.move(direction), remove1);
        obstacles.put(box2.move(direction), remove2);
    }

    private static void moveBox(Direction direction, Point2D box, Map<Point2D, Obstacle> obstacles, Obstacle type) {
        Point2D move = box.move(direction);
        Obstacle obstacle = obstacles.get(move);
        if (obstacle == null) {
            obstacles.put(move, type);
            return;
        }
        if (obstacle == Obstacle.ROCK) {
            throw new IllegalStateException("Cannot move ROCK");
        }

        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            moveBox(direction, move, obstacles, obstacle);
            obstacles.put(move, type);
            return;
        }
        Point2D friend = obstacle.friend(move);
        moveBox(direction, move, friend, obstacles);
    }

    private static void print(Map<Point2D, Obstacle> obstacles, Point2D robot) {
        CharMap map = new CharMap();
        obstacles.forEach((key, value) -> map.set(key, value.value));
        map.set(robot, '@');
        LOGGER.info("\n{}", map);
    }

    enum Obstacle {
        ROCK('#') {
            @Override
            public Point2D friend(Point2D p) {
                throw new IllegalStateException("Cannot move ROCK");
            }
        },
        LEFT_BOX('[') {
            @Override
            public Point2D friend(Point2D p) {
                return p.move(Direction.RIGHT);
            }
        },
        RIGHT_BOX(']') {
            @Override
            public Point2D friend(Point2D p) {
                return p.move(Direction.LEFT);
            }
        };

        private final char value;

        Obstacle(char value) {
            this.value = value;
        }

        public abstract Point2D friend(Point2D p);
    }
}
