package com.adventofcode.year2023;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public final class Day01 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day01.class);

    private Day01() {
        // No-Op
    }

    /**
     * --- Day 1: Trebuchet?! ---
     *
     * Something is wrong with global snow production, and you've been selected to
     * take a look. The Elves have even given you a map; on it, they've used stars
     * to mark the top fifty locations that are likely to be having problems.
     *
     * You've been doing this long enough to know that to restore snow operations,
     * you need to check all fifty stars by December 25th.
     *
     * Collect stars by solving puzzles. Two puzzles will be made available on
     * each day in the Advent calendar; the second puzzle is unlocked when you
     * complete the first. Each puzzle grants one star. Good luck!
     *
     * You try to ask why they can't just use a weather machine ("not powerful
     * enough") and where they're even sending you ("the sky") and why your map
     * looks mostly blank ("you sure ask a lot of questions") and hang on did you
     * just say the sky ("of course, where do you think snow comes from") when you
     * realize that the Elves are already loading you into a trebuchet ("please
     * hold still, we need to strap you in").
     *
     * As they're making the final adjustments, they discover that their
     * calibration document (your puzzle input) has been amended by a very young
     * Elf who was apparently just excited to show off her art skills.
     * Consequently, the Elves are having trouble reading the values on the
     * document.
     *
     * The newly-improved calibration document consists of lines of text; each
     * line originally contained a specific calibration value that the Elves now
     * need to recover. On each line, the calibration value can be found by
     * combining the first digit and the last digit (in that order) to form a single two-digit number.
     *
     * For example:
     *
     * 1abc2
     * pqr3stu8vwx
     * a1b2c3d4e5f
     * treb7uchet
     *
     * In this example, the calibration values of these four lines are 12, 38, 15,
     * and 77. Adding these together produces 142.
     *
     * Consider your entire calibration document. What is the sum of all of the
     * calibration values?
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static int sumCalibrationValues(Scanner scanner) {
            int sum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int value = calibrationValue(line);
                LOGGER.debug("line = {} ==> value = {}", line, value);
                sum += value;
            }

            return sum;
        }

        public static int calibrationValue(String line) {
            IntList chars = new IntArrayList();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c >= '0' && c <= '9') {
                    chars.add(c - '0');
                }
            }
            return chars.getInt(0) * 10 + chars.getInt(chars.size() - 1);
        }
    }

    /**
     * --- Part Two ---
     *
     * Your calculation isn't quite right. It looks like some of the digits are
     * actually spelled out with letters: one, two, three, four, five, six, seven,
     * eight, and nine also count as valid "digits".
     *
     * Equipped with this new information, you now need to find the real first and
     * last digit on each line. For example:
     *
     * two1nine
     * eightwothree
     * abcone2threexyz
     * xtwone3four
     * 4nineeightseven2
     * zoneight234
     * 7pqrstsixteen
     *
     * In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76.
     * Adding these together produces 281.
     *
     * What is the sum of all of the calibration values?
     */
    public static final class PartTwo {

        private static final Map<String, Integer> digits = Map.ofEntries(
                Map.entry("one", 1), Map.entry("1", 1),
                Map.entry("two", 2), Map.entry("2", 2),
                Map.entry("three", 3), Map.entry("3", 3),
                Map.entry("four", 4), Map.entry("4", 4),
                Map.entry("five", 5), Map.entry("5", 5),
                Map.entry("six", 6), Map.entry("6", 6),
                Map.entry("seven", 7), Map.entry("7", 7),
                Map.entry("eight", 8), Map.entry("8", 8),
                Map.entry("nine", 9), Map.entry("9", 9)
        );

        private PartTwo() {
            // No-Op
        }

        public static int sumCalibrationValues(Scanner scanner) {
            int sum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int value = calibrationValue(line);
                LOGGER.debug("line = {} ==> value = {}", line, value);
                sum += value;
            }

            return sum;
        }

        private static int calibrationValue(String line) {
            Optional<Pair<Integer, Integer>> min = digits.entrySet().stream()
                    .map(e -> Pair.of(line.indexOf(e.getKey()), e.getValue()))
                    .filter(p -> p.left() != -1)
                    .min(Comparator.comparingInt(Pair::left));
            Optional<Pair<Integer, Integer>> max = digits.entrySet().stream()
                    .map(e -> Pair.of(line.lastIndexOf(e.getKey()), e.getValue()))
                    .filter(p -> p.left() != -1)
                    .max(Comparator.comparingInt(Pair::left));

            int value = min.map(Pair::value).orElse(0) * 10 + max.map(Pair::right).orElse(0);
            LOGGER.debug("line = {} ==> min = {}, max = {}, value = {}", line, min, max, value);
            return value;
        }
    }
}
