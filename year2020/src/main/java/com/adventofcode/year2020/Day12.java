package com.adventofcode.year2020;

import com.adventofcode.common.point.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class Day12 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12.class);

    private static PositionWaypoint move(PositionWaypoint position, String action) {
        int x = position.x();
        int y = position.y();

        int waypointX = position.waypointX();
        int waypointY = position.waypointY();

        char type = action.charAt(0);
        int value = Integer.parseInt(action.substring(1));
        switch (type) {
            case 'N' -> waypointY += value;
            case 'S' -> waypointY -= value;
            case 'E' -> waypointX += value;
            case 'W' -> waypointX -= value;
            case 'L' -> {
                Point2D waypoint = turnLeft(waypointX, waypointY, value);
                waypointX = waypoint.x();
                waypointY = waypoint.y();
            }
            case 'R' -> {
                Point2D waypoint = turnRight(waypointX, waypointY, value);
                waypointX = waypoint.x();
                waypointY = waypoint.y();
            }
            case 'F' -> {
                x += waypointX * value;
                y += waypointY * value;
            }
            default -> throw new IllegalStateException("Unknown action : " + type);
        }

        return new PositionWaypoint(x, y, waypointX, waypointY);
    }

    static PositionWaypoint moveWithWaypoint(List<String> actions) {
        PositionWaypoint position = new PositionWaypoint(0, 0, 10, 1);
        for (String action : actions) {
            position = move(position, action);
            LOGGER.debug("Position after move {}: {}", action, position);
        }

        return position;
    }

    private static Point2D turnLeft(int x, int y, int angle) {
        return switch (angle) {
            case 90 -> Point2D.of(-y, x);
            case 180 -> Point2D.of(-x, -y);
            case 270 -> Point2D.of(y, -x);
            default -> throw new IllegalStateException("Cannot turn left : " + angle);
        };
    }

    private static Point2D turnRight(int x, int y, int angle) {
        return switch (angle) {
            case 90 -> Point2D.of(y, -x);
            case 180 -> Point2D.of(-x, -y);
            case 270 -> Point2D.of(-y, x);
            default -> throw new IllegalStateException("Cannot turn right : " + angle);
        };
    }

    private static PositionDirection move(PositionDirection position, String action) {
        Direction direction = position.d();
        int x = position.x();
        int y = position.y();

        char type = action.charAt(0);
        int value = Integer.parseInt(action.substring(1));
        switch (type) {
            case 'N' -> y += value;
            case 'S' -> y -= value;
            case 'E' -> x += value;
            case 'W' -> x -= value;
            case 'L' -> direction = direction.turnLeft(value);
            case 'R' -> direction = direction.turnRight(value);
            case 'F' -> {
                Point2D move = direction.move(x, y, value);
                x = move.x();
                y = move.y();
            }
            default -> throw new IllegalStateException("Unknown action : " + type);
        }

        return new PositionDirection(direction, x, y);
    }

    /**
     * --- Day 12: Rain Risk ---
     *
     * Your ferry made decent progress toward the island, but the storm came in
     * faster than anyone expected. The ferry needs to take evasive actions!
     *
     * Unfortunately, the ship's navigation computer seems to be malfunctioning;
     * rather than giving a route directly to safety, it produced extremely
     * circuitous instructions. When the captain uses the PA system to ask if
     * anyone can help, you quickly volunteer.
     *
     * The navigation instructions (your puzzle input) consists of a sequence of
     * single-character actions paired with integer input values. After staring at
     * them for a few minutes, you work out what they probably mean:
     *
     * - Action N means to move north by the given value.
     * - Action S means to move south by the given value.
     * - Action E means to move east by the given value.
     * - Action W means to move west by the given value.
     * - Action L means to turn left the given number of degrees.
     * - Action R means to turn right the given number of degrees.
     * - Action F means to move forward by the given value in the direction the
     * ship is currently facing.
     *
     * The ship starts by facing east. Only the L and R actions change the
     * direction the ship is facing. (That is, if the ship is facing east and the
     * next instruction is N10, the ship would move north 10 units, but would
     * still move east if the following action were F.)
     *
     * For example:
     *
     * F10
     * N3
     * F7
     * R90
     * F11
     *
     * These instructions would be handled as follows:
     *
     * - F10 would move the ship 10 units east (because the ship starts by
     * facing east) to east 10, north 0.
     * - N3 would move the ship 3 units north to east 10, north 3.
     * - F7 would move the ship another 7 units east (because the ship is still
     * facing east) to east 17, north 3.
     * - R90 would cause the ship to turn right by 90 degrees and face south;
     * it remains at east 17, north 3.
     * - F11 would move the ship 11 units south to east 17, south 8.
     *
     * At the end of these instructions, the ship's Manhattan distance (sum of the
     * absolute values of its east/west position and its north/south position)
     * from its starting position is 17 + 8 = 25.
     *
     * Figure out where the navigation instructions lead. What is the Manhattan
     * distance between that location and the ship's starting position?
     *
     * --- Part Two ---
     *
     * Before you can give the destination to the captain, you realize that the
     * actual action meanings were printed on the back of the instructions the
     * whole time.
     *
     * Almost all of the actions indicate how to move a waypoint which is relative
     * to the ship's position:
     *
     * - Action N means to move the waypoint north by the given value.
     * - Action S means to move the waypoint south by the given value.
     * - Action E means to move the waypoint east by the given value.
     * - Action W means to move the waypoint west by the given value.
     * - Action L means to rotate the waypoint around the ship left
     * (counter-clockwise) the given number of degrees.
     * - Action R means to rotate the waypoint around the ship right
     * (clockwise) the given number of degrees.
     * - Action F means to move forward to the waypoint a number of times equal
     * to the given value.
     *
     * The waypoint starts 10 units east and 1 unit north relative to the ship.
     * The waypoint is relative to the ship; that is, if the ship moves, the
     * waypoint moves with it.
     *
     * For example, using the same instructions as above:
     *
     * - F10 moves the ship to the waypoint 10 times (a total of 100 units east
     * and 10 units north), leaving the ship at east 100, north 10. The
     * waypoint stays 10 units east and 1 unit north of the ship.
     * - N3 moves the waypoint 3 units north to 10 units east and 4 units north
     * of the ship. The ship remains at east 100, north 10.
     * - F7 moves the ship to the waypoint 7 times (a total of 70 units east
     * and 28 units north), leaving the ship at east 170, north 38. The
     * waypoint stays 10 units east and 4 units north of the ship.
     * - R90 rotates the waypoint around the ship clockwise 90 degrees, moving
     * it to 4 units east and 10 units south of the ship. The ship remains at
     * east 170, north 38.
     * - F11 moves the ship to the waypoint 11 times (a total of 44 units east
     * and 110 units south), leaving the ship at east 214, south 72. The
     * waypoint stays 4 units east and 10 units south of the ship.
     *
     * After these operations, the ship's Manhattan distance from its starting position is 214 + 72 = 286.
     *
     * Figure out where the navigation instructions actually lead. What is the Manhattan distance between that location and the ship's starting position?
     */
    static PositionDirection moveWithDirection(List<String> actions) {
        PositionDirection position = new PositionDirection(Direction.EAST, 0, 0);
        for (String action : actions) {
            position = move(position, action);
            LOGGER.debug("Position after move {}: {}", action, position);
        }

        return position;
    }

    static int moveWithDirectionDistance(List<String> actions) {
        PositionDirection position = moveWithDirection(actions);
        LOGGER.info("Last position {}", position);
        return Math.abs(position.x()) + Math.abs(position.y());
    }

    static int moveWithWaypointDistance(List<String> actions) {
        PositionWaypoint position = moveWithWaypoint(actions);
        LOGGER.info("Last position {}", position);
        return Math.abs(position.x()) + Math.abs(position.y());
    }

    enum Direction {
        NORTH {
            @Override
            public Point2D move(int x, int y, int n) {
                return Point2D.of(x, y + n);
            }
        },
        WEST {
            @Override
            public Point2D move(int x, int y, int n) {
                return Point2D.of(x - n, y);
            }
        },
        SOUTH {
            @Override
            public Point2D move(int x, int y, int n) {
                return Point2D.of(x, y - n);
            }
        },
        EAST {
            @Override
            public Point2D move(int x, int y, int n) {
                return Point2D.of(x + n, y);
            }
        };

        public Direction turnLeft(int n) {
            return values()[(ordinal() + n / 90) % 4];
        }

        public Direction turnRight(int n) {
            return values()[(ordinal() + 4 - n / 90) % 4];

        }

        public abstract Point2D move(int x, int y, int n);
    }

    record PositionWaypoint(int x, int y, int waypointX, int waypointY) {
    }

    record PositionDirection(Direction d, int x, int y) {
    }
}
