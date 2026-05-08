package com.adventofcode.common.point.range;

import com.google.common.base.Splitter;
import com.google.errorprone.annotations.Immutable;

import java.util.List;
import java.util.Optional;

@Immutable
public record Range(long lower, long upper) {
    public static Range of(long lower, long upper) {
        return new Range(lower, upper);
    }

    public static Range of(String d) {
        int[] ints = Splitter.on("..").splitToStream(d).mapToInt(Integer::parseInt).toArray();
        return new Range(ints[0], ints[1]);
    }

    public static List<Range> fullUnion(Range r1, Range r2) {
        long lowerMin = Math.min(r1.lower, r2.lower);
        long lowerMax = Math.max(r1.lower, r2.lower);
        long upperMin = Math.min(r1.upper, r2.upper);
        long upperMax = Math.max(r1.upper, r2.upper);

        return List.of(
                new Range(lowerMin, lowerMax - 1),
                new Range(lowerMax, upperMin),
                new Range(upperMin + 1, upperMax)
        );
    }

    public static List<Range> difference(Range r1, Range r2) {
        return fullUnion(r1, r2).stream().filter(r -> !intersect(r, r2)).toList();
    }

    public static boolean intersect(Range r1, Range r2) {
        long lowerMax = Math.max(r1.lower, r2.lower);
        long upperMin = Math.min(r1.upper, r2.upper);

        return lowerMax <= upperMin;
    }

    public static Range union(Range r1, Range r2) {
        long lower_min = Math.min(r1.lower, r2.lower);
        long upper_max = Math.max(r1.upper, r2.upper);

        return new Range(lower_min, upper_max);
    }

    public static Optional<Range> intersection(Range r1, Range r2) {
        long lowerMax = Math.max(r1.lower, r2.lower);
        long upperMin = Math.min(r1.upper, r2.upper);

        if (lowerMax > upperMin) {
            return Optional.empty();
        }

        return Optional.of(new Range(lowerMax, upperMin));
    }

    public boolean contains(Range r) {
        return lower <= r.lower && r.upper <= upper;
    }

    public boolean contains(long id) {
        return id >= lower && id <= upper;
    }

    public boolean valid() {
        return upper >= lower;
    }

    public long size() {
        return upper - lower + 1;
    }

    @Override
    public String toString() {
        return "[" + lower + ".." + upper + "]";
    }
}
