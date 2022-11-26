package com.adventofcode.point;

import java.util.List;

public record Point2D(int x, int y) implements Comparable<Point2D> {
    public static final List<Point2D> ADJACENT =
            List.of(of(-1, -1),
                    of(-1, 0),
                    of(-1, 1),
                    of(0, -1),
                    of(0, 1),
                    of(1, -1),
                    of(1, 0),
                    of(1, 1)
            );

    public static Point2D of(int x, int y) {
        return new Point2D(x, y);
    }

    private static int loop(int p, int pMax) {
        if (p > pMax) {
            return 0;
        } else if (p < 0) {
            return pMax;
        } else {
            return p;
        }
    }

    public static int manhattanDistance(Point2D a, Point2D b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    public Point2D move(Direction direction) {
        return move(direction, 1);
    }

    public Point2D move(Direction direction, int d) {
        return switch (direction) {
            case NORTH -> Point2D.of(x, y - d);
            case SOUTH -> Point2D.of(x, y + d);
            case WEST -> Point2D.of(x - d, y);
            case EAST -> Point2D.of(x + d, y);
        };
    }

    public Point2D moveRange(Direction direction, int xMax, int yMax) {
        return switch (direction) {
            case NORTH -> Point2D.of(x, Math.max(y - 1, 0));
            case SOUTH -> Point2D.of(x, Math.min(y + 1, yMax));
            case WEST -> Point2D.of(Math.max(x - 1, 0), y);
            case EAST -> Point2D.of(Math.min(x + 1, xMax), y);
        };
    }

    public Point2D moveLoop(Direction direction, int xMax, int yMax) {
        return switch (direction) {
            case NORTH -> Point2D.of(x, loop(y - 1, yMax));
            case SOUTH -> Point2D.of(x, loop(y + 1, yMax));
            case WEST -> Point2D.of(loop(x - 1, xMax), y);
            case EAST -> Point2D.of(loop(x + 1, xMax), y);
        };
    }

    public Point2D move(Point2D d) {
        return Point2D.of(x + d.x, y + d.y);
    }

    @Override
    public int compareTo(Point2D o) {
        int compare = Integer.compare(y, o.y);
        if (compare != 0) {
            return compare;
        }
        return Integer.compare(x, o.x);
    }
}

