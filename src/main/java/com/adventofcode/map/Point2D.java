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

    public Point2D move(Point2D d) {
        return new Point2D(x + d.x, y + d.y);
    }
}

