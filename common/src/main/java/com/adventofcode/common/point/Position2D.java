package com.adventofcode.common.point;

import com.google.errorprone.annotations.Immutable;

@Immutable
public record Position2D(Point2D p, Direction direction) {
    public static Position2D of(Point2D p, Direction direction) {
        return new Position2D(p, direction);
    }

    public static Position2D of(int x, int y, Direction direction) {
        return new Position2D(Point2D.of(x, y), direction);
    }

    public int x() {
        return p.x();
    }

    public int y() {
        return p.y();
    }

    public Position2D left() {
        return new Position2D(p, direction.left());
    }

    public Position2D right() {
        return new Position2D(p, direction.right());
    }

    public Position2D move() {
        return new Position2D(p.move(direction), direction);
    }

    public Position2D moveLoop(int xMax, int yMax) {
        return new Position2D(p.moveLoop(direction, xMax, yMax), direction);
    }

}
