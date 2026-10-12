package com.adventofcode.common.point;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /// Horizontal step: -1, 0 or 1 (x grows to the right).
    public int dx() {
        return dx;
    }

    /// Vertical step: -1, 0 or 1 (y grows downwards, so `UP` is -1).
    public int dy() {
        return dy;
    }

    public Direction reverse() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

    /// Turns 90° counter-clockwise.
    public Direction left() {
        return switch (this) {
            case UP -> LEFT;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case RIGHT -> UP;
        };
    }

    /// Turns 90° clockwise.
    public Direction right() {
        return switch (this) {
            case UP -> RIGHT;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case RIGHT -> DOWN;
        };
    }
}
