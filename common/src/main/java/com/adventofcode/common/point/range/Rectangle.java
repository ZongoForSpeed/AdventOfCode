package com.adventofcode.common.point.range;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Rectangle(Range dx, Range dy) {
    private static final Logger LOGGER = LoggerFactory.getLogger(Rectangle.class);

    public static Rectangle of(Range dx, Range dy) {
        return new Rectangle(dx, dy);
    }

    public static Rectangle of(int xMin, int xMax, int yMin, int yMax) {
        return new Rectangle(Range.of(xMin, xMax), Range.of(yMin, yMax));
    }

    public static boolean intersect(Rectangle c1, Rectangle c2) {
        return Range.intersect(c1.dx, c2.dx)
               && Range.intersect(c1.dy, c2.dy);
    }

    public static Optional<Rectangle> intersection(Rectangle c1, Rectangle c2) {
        Optional<Range> intersectionX = Range.intersection(c1.dx, c2.dx);
        Optional<Range> intersectionY = Range.intersection(c1.dy, c2.dy);

        if (intersectionX.isEmpty() || intersectionY.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new Rectangle(intersectionX.get(), intersectionY.get()));
    }

    public static List<Rectangle> fullUnion(Rectangle c1, Rectangle c2) {
        List<Range> xRanges = Range.fullUnion(c1.dx, c2.dx);
        List<Range> yRanges = Range.fullUnion(c1.dy, c2.dy);

        List<Rectangle> rectangles = new ArrayList<>(9);

        for (Range xRange : xRanges) {
            for (Range yRange : yRanges) {
                    rectangles.add(new Rectangle(xRange, yRange));
            }
        }

        LOGGER.trace("Full difference {} & {} : {}", c1, c2, rectangles);
        return rectangles;
    }

    public static List<Rectangle> difference(Rectangle c1, Rectangle c2) {
        if (!intersect(c1, c2)) {
            return List.of(c1);
        }

        return fullUnion(c1, c2).stream().filter(Rectangle::valid).filter(c -> c1.contains(c) && !c2.contains(c)).toList();
    }

    public static List<Rectangle> union(Rectangle c1, Rectangle c2) {
        if (!intersect(c1, c2)) {
            return List.of(c1, c2);
        }

        return fullUnion(c1, c2).stream().filter(Rectangle::valid).filter(c -> c1.contains(c) || c2.contains(c)).toList();
    }

    public boolean contains(Rectangle c) {
        return dx.contains(c.dx)
               && dy.contains(c.dy);
    }

    public boolean valid() {
        return dx.valid() && dy.valid();
    }

    public long size() {
        return dx.size() * dy.size();
    }

    public String toString() {
        return "{x=" + dx + ",y=" + dy + "}";
    }
}
