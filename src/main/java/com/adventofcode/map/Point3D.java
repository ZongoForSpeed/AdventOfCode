package com.adventofcode.map;

public record Point3D(int x, int y, int z) {
    public Point3D(Point2D p, int z) {
        this(p.x(), p.y(), z);
    }

    public static Point3D of(int x, int y, int z) {
        return new Point3D(x, y, z);
    }

    public Point2D project() {
        return Point2D.of(x, y);
    }

    public static long ManhattanDistance(Point3D a, Point3D b) {
        return Point2D.ManhattanDistance(a.project(), b.project()) + Math.abs(a.z - b.z);
    }
}
