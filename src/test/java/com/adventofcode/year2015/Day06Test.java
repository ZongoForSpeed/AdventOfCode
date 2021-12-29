package com.adventofcode.year2015;

import com.adventofcode.map.IntegerMap;
import com.adventofcode.utils.TriConsumer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


class Day06Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day06Test.class);
    private static final Pattern PATTERN = Pattern.compile("(turn off|turn on|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)");

    private static long computeLights(Scanner scanner) {
        List<BitSet> lights = new ArrayList<>(1000);
        IntStream.range(0, 1000).mapToObj(ignore -> new BitSet(1000)).forEach(lights::add);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                LOGGER.debug("Line: {}-{}-{}-{}-{}", matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
                int xMin = Integer.parseInt(matcher.group(2));
                int yMin = Integer.parseInt(matcher.group(3));
                int xMax = Integer.parseInt(matcher.group(4));
                int yMax = Integer.parseInt(matcher.group(5));
                TriConsumer<BitSet, Integer, Integer> operation = switch (matcher.group(1)) {
                    case "turn on" -> BitSet::set;
                    case "turn off" -> BitSet::clear;
                    case "toggle" -> BitSet::flip;
                    default -> throw new IllegalStateException();
                };
                for (int x = xMin; x <= xMax; ++x) {
                    operation.accept(lights.get(x), yMin, yMax + 1);
                }
            } else {
                LOGGER.error("Unknown line: {}", line);
            }
        }
        return lights.stream().mapToLong(BitSet::cardinality).sum();
    }

    private static long computeBrightness(Scanner scanner) {
        IntegerMap map = new IntegerMap(1000, 1000, 0);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                LOGGER.debug("Line: {}-{}-{}-{}-{}", matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
                int xMin = Integer.parseInt(matcher.group(2));
                int yMin = Integer.parseInt(matcher.group(3));
                int xMax = Integer.parseInt(matcher.group(4));
                int yMax = Integer.parseInt(matcher.group(5));
                int delta = switch (matcher.group(1)) {
                    case "turn on" -> 1;
                    case "turn off" -> -1;
                    case "toggle" -> 2;
                    default -> throw new IllegalStateException();
                };
                for (int x = xMin; x <= xMax; ++x) {
                    for (int y = yMin; y <= yMax; ++y) {
                        map.compute(x, y, value -> Math.max(0, value + delta));
                    }
                }
            } else {
                LOGGER.error("Unknown line: {}", line);
            }
        }
        long brightness = 0;
        for (int x = 0; x < 1000; ++x) {
            for (int y = 0; y < 1000; ++y) {
                brightness += map.get(x, y);
            }
        }
        return brightness;
    }

    /**
     * --- Day 6: Probably a Fire Hazard ---
     *
     * Because your neighbors keep defeating you in the holiday house decorating
     * contest year after year, you've decided to deploy one million lights in a
     * 1000x1000 grid.
     *
     * Furthermore, because you've been especially nice this year, Santa has
     * mailed you instructions on how to display the ideal lighting configuration.
     *
     * Lights in your grid are numbered from 0 to 999 in each direction; the
     * lights at each corner are at 0,0, 0,999, 999,999, and 999,0. The
     * instructions include whether to turn on, turn off, or toggle various
     * inclusive ranges given as coordinate pairs. Each coordinate pair represents
     * opposite corners of a rectangle, inclusive; a coordinate pair like
     * 0,0 through 2,2 therefore refers to 9 lights in a 3x3 square. The lights
     * all start turned off.
     *
     * To defeat your neighbors this year, all you have to do is set up your lights by doing the instructions Santa sent you in order.
     *
     * For example:
     *
     *   - turn on 0,0 through 999,999 would turn on (or leave on) every light.
     *   - toggle 0,0 through 999,0 would toggle the first line of 1000 lights,
     *     turning off the ones that were on, and turning on the ones that were
     *     off.
     *   - turn off 499,499 through 500,500 would turn off (or leave off) the
     *     middle four lights.
     *
     * After following the instructions, how many lights are lit?
     *
     * Your puzzle answer was 377891.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day06Test.class.getResourceAsStream("/2015/day/6/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(computeLights(scanner)).isEqualTo(377891);
        }
    }

    /**
     * --- Part Two ---
     *
     * You just finish implementing your winning light pattern when you realize
     * you mistranslated Santa's message from Ancient Nordic Elvish.
     *
     * The light grid you bought actually has individual brightness controls; each
     * light can have a brightness of zero or more. The lights all start at zero.
     *
     * The phrase turn on actually means that you should increase the brightness
     * of those lights by 1.
     *
     * The phrase turn off actually means that you should decrease the brightness
     * of those lights by 1, to a minimum of zero.
     *
     * The phrase toggle actually means that you should increase the brightness of
     * those lights by 2.
     *
     * What is the total brightness of all lights combined after following Santa's
     * instructions?
     *
     * For example:
     *
     *   - turn on 0,0 through 0,0 would increase the total brightness by 1.
     *   - toggle 0,0 through 999,999 would increase the total brightness by 2000000.
     *
     * Your puzzle answer was 14110788.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day06Test.class.getResourceAsStream("/2015/day/6/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(computeBrightness(scanner)).isEqualTo(14110788);
        }
    }
}
