package com.adventofcode.common.point.range;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Cuboid(Range dx, Range dy, Range dz) {
    private static final Logger LOGGER = LoggerFactory.getLogger(Cuboid.class);

    public static Cuboid of(Range dx, Range dy, Range dz) {
        return new Cuboid(dx, dy, dz);
    }

    public static Cuboid of(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
        return new Cuboid(Range.of(xMin, xMax), Range.of(yMin, yMax), Range.of(zMin, zMax));
    }

    public static boolean intersect(Cuboid c1, Cuboid c2) {
        return Range.intersect(c1.dx, c2.dx)
               && Range.intersect(c1.dy, c2.dy)
               && Range.intersect(c1.dz, c2.dz);
    }

    public static Optional<Cuboid> intersection(Cuboid c1, Cuboid c2) {
        Optional<Range> intersectionX = Range.intersection(c1.dx, c2.dx);
        Optional<Range> intersectionY = Range.intersection(c1.dy, c2.dy);
        Optional<Range> intersectionZ = Range.intersection(c1.dz, c2.dz);

        if (intersectionX.isEmpty() || intersectionY.isEmpty() || intersectionZ.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new Cuboid(intersectionX.get(), intersectionY.get(), intersectionZ.get()));
    }

    public static List<Cuboid> fullUnion(Cuboid c1, Cuboid c2) {
        List<Range> xRanges = Range.fullUnion(c1.dx, c2.dx);
        List<Range> yRanges = Range.fullUnion(c1.dy, c2.dy);
        List<Range> zRanges = Range.fullUnion(c1.dz, c2.dz);

        List<Cuboid> cuboids = new ArrayList<>(27);

        for (Range xRange : xRanges) {
            for (Range yRange : yRanges) {
                for (Range zRange : zRanges) {
                    cuboids.add(new Cuboid(xRange, yRange, zRange));
                }
            }
        }

        LOGGER.trace("Full difference {} & {} : {}", c1, c2, cuboids);
        return cuboids;
    }

    public static List<Cuboid> difference(Cuboid c1, Cuboid c2) {
        if (!intersect(c1, c2)) {
            return List.of(c1);
        }

        return fullUnion(c1, c2).stream().filter(Cuboid::valid).filter(c -> c1.contains(c) && !c2.contains(c)).toList();
    }

    public boolean contains(Cuboid c) {
        return dx.contains(c.dx)
               && dy.contains(c.dy)
               && dz.contains(c.dz);
    }

    public boolean valid() {
        return dx.valid() && dy.valid() && dz.valid();
    }

    public long size() {
        return dx.size() * dy.size() * dz.size();
    }

    @Override
    public String toString() {
        return "{x=" + dx + ",y=" + dy + ",z=" + dz + "}";
    }
}
