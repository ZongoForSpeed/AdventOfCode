package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day02Test {
    private static long wrappingPaper(String line) {
        long[] dimensions = Arrays.stream(line.split("x")).mapToLong(Long::parseLong).toArray();
        long volume = Arrays.stream(dimensions).reduce(1L, (a, b) -> a * b);
        long[] areas = Arrays.stream(dimensions).map(d -> volume / d).toArray();
        return Arrays.stream(areas).map(a -> 2 * a).sum() + Arrays.stream(areas).min().orElseThrow();
    }

    private static long wrappingBow(String line) {
        long[] dimensions = Arrays.stream(line.split("x")).mapToLong(Long::parseLong).sorted().toArray();
        long volume = Arrays.stream(dimensions).reduce(1L, (a, b) -> a * b);
        return dimensions[0] * 2 + dimensions[1] * 2 + volume;
    }

    private static long wrappingPaper(Scanner scanner) {
        long total = 0;
        while (scanner.hasNextLine()) {
            total += wrappingPaper(scanner.nextLine());
        }
        return total;
    }

    private static long wrappingBow(Scanner scanner) {
        long total = 0;
        while (scanner.hasNextLine()) {
            total += wrappingBow(scanner.nextLine());
        }
        return total;
    }

    @Test
    void inputExample() {
        assertThat(wrappingPaper("2x3x4")).isEqualTo(58);
        assertThat(wrappingPaper("1x1x10")).isEqualTo(43);
        assertThat(wrappingBow("2x3x4")).isEqualTo(34);
        assertThat(wrappingBow("1x1x10")).isEqualTo(14);
    }

    /**
     * --- Day 2: I Was Told There Would Be No Math ---
     *
     * The elves are running low on wrapping paper, and so they need to submit an
     * order for more. They have a list of the dimensions (length l, width w, and
     * height h) of each present, and only want to order exactly as much as they
     * need.
     *
     * Fortunately, every present is a box (a perfect right rectangular prism),
     * which makes calculating the required wrapping paper for each gift a little
     * easier: find the surface area of the box, which is 2*l*w + 2*w*h + 2*h*l.
     * The elves also need a little extra paper for each present: the area of the
     * smallest side.
     *
     * For example:
     *
     *   - A present with dimensions 2x3x4 requires 2*6 + 2*12 + 2*8 = 52 square
     *     feet of wrapping paper plus 6 square feet of slack, for a total of 58
     *     square feet.
     *   - A present with dimensions 1x1x10 requires 2*1 + 2*10 + 2*10 = 42
     *     square feet of wrapping paper plus 1 square foot of slack, for a total
     *     of 43 square feet.
     *
     * All numbers in the elves' list are in feet. How many total square feet of wrapping paper should they order?
     *
     * Your puzzle answer was 1588178.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2015/day/2/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(wrappingPaper(scanner)).isEqualTo(1588178);
        }
    }

    /**
     * --- Part Two ---
     *
     * The elves are also running low on ribbon. Ribbon is all the same width, so
     * they only have to worry about the length they need to order, which they
     * would again like to be exact.
     *
     * The ribbon required to wrap a present is the shortest distance around its
     * sides, or the smallest perimeter of any one face. Each present also
     * requires a bow made out of ribbon as well; the feet of ribbon required for
     * the perfect bow is equal to the cubic feet of volume of the present. Don't
     * ask how they tie the bow, though; they'll never tell.
     *
     * For example:
     *
     *   - A present with dimensions 2x3x4 requires 2+2+3+3 = 10 feet of ribbon
     *     to wrap the present plus 2*3*4 = 24 feet of ribbon for the bow, for a
     *     total of 34 feet.
     *   - A present with dimensions 1x1x10 requires 1+1+1+1 = 4 feet of ribbon
     *     to wrap the present plus 1*1*10 = 10 feet of ribbon for the bow, for a
     *     total of 14 feet.
     *
     * How many total feet of ribbon should they order?
     *
     * Your puzzle answer was 3783758.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2015/day/2/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(wrappingBow(scanner)).isEqualTo(3783758);
        }
    }
}
