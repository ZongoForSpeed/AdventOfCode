package com.adventofcode.point;

import java.util.StringJoiner;

public record Point3D(int x, int y, int z) {
    public static final Point3D ORIGIN = of(0, 0, 0);

    public Point3D(Point2D p, int z) {
        this(p.x(), p.y(), z);
    }

    public static Point3D of(int x, int y, int z) {
        return new Point3D(x, y, z);
    }

    public static double distance(Point3D p1, Point3D p2) {
        return p1.distance(p2);
    }

    public static Point3D add(Point3D p1, Point3D p2) {
        return of(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
    }

    public static Point3D minus(Point3D p1, Point3D p2) {
        return of(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
    }

    public static Point3D minus(Point3D p1) {
        return of(-p1.x, -p1.y, -p1.z);
    }

    public static int manhattanDistance(Point3D a, Point3D b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y()) + Math.abs(a.z() - b.z());
    }

    public Point2D project() {
        return Point2D.of(x, y);
    }

    public double distance(Point3D p) {
        double dx = (x - p.x);
        double dy = (y - p.y);
        double dz = (z - p.z);
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "(", ")");
        joiner.add(Integer.toString(x));
        joiner.add(Integer.toString(y));
        joiner.add(Integer.toString(z));
        return joiner.toString();
    }
}
