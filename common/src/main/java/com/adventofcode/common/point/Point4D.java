package com.adventofcode.common.point;

import com.google.errorprone.annotations.Immutable;

@Immutable
public record Point4D(int x, int y, int z, int w) {
    public static Point4D of(int x, int y, int z, int w) {
        return new Point4D(x, y, z, w);
    }

    public static int manhattanDistance(Point4D a, Point4D b) {
        return Math.abs(a.x() - b.x())
               + Math.abs(a.y() - b.y())
               + Math.abs(a.z() - b.z())
               + Math.abs(a.w() - b.w());
    }
}
