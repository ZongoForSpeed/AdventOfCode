package com.adventofcode.map;

public record Point2D(int x, int y) {
    public static Point2D of(int x, int y) {
        return new Point2D(x, y);
    }

    public Point2D move(Direction direction) {
        return switch (direction) {
            case NORTH -> new Point2D(x, y - 1);
            case SOUTH -> new Point2D(x, y + 1);
            case WEST -> new Point2D(x - 1, y);
            case EAST -> new Point2D(x + 1, y);
        };
    }

    public Point2D move(Direction direction, int xMax, int yMax) {
        return switch (direction) {
            case NORTH -> new Point2D(x, range(y - 1, yMax));
            case SOUTH -> new Point2D(x, range(y + 1, yMax));
            case WEST -> new Point2D(range(x - 1, xMax), y);
            case EAST -> new Point2D(range(x + 1, xMax), y);
        };
    }

    private static int range(int p, int pMax) {
        if (p > pMax) {
            return 0;
        } else if (p < 0) {
            return pMax;
        } else {
            return p;
        }
    }

    public Point2D move(Point2D d) {
        return new Point2D(x + d.x, y + d.y);
    }
}

