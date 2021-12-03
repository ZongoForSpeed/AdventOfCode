package com.adventofcode;

import com.adventofcode.utils.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day03Test {

    private static Pair<Integer, Integer> binaryDiagnostic(List<String> strings) {
        int length = strings.iterator().next().length();
        int[] frequency = new int[length];
        for (String string : strings) {
            char[] charArray = string.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if (c == '1') {
                    frequency[i]++;
                }
            }
        }

        int size = strings.size();
        StringBuilder sbGamma = new StringBuilder();
        StringBuilder sbEpsilon = new StringBuilder();
        for (int i : frequency) {
            sbGamma.append(i > size / 2 ? '1' : '0');
            sbEpsilon.append(i < size / 2 ? '1' : '0');
        }

        System.out.println(sbGamma);
        System.out.println(sbEpsilon);

        Integer gamma = Integer.valueOf(sbGamma.toString(), 2);
        System.out.println(gamma);
        Integer epsilon = Integer.valueOf(sbEpsilon.toString(), 2);
        System.out.println(epsilon);

        return Pair.of(gamma, epsilon);
    }

    private static int oxygenGeneratorRating(List<String> strings) {
        strings = new ArrayList<>(strings);
        int position = 0;
        while (strings.size() > 1) {
            int frequency = 0;
            for (String s : strings) {
                if (s.charAt(position) == '1') {
                    frequency++;
                }
            }
            char bit = (2 * frequency >= strings.size()) ? '1' : '0';
            int finalPosition = position;
            strings.removeIf(s -> s.charAt(finalPosition) != bit);

            ++position;
        }

        System.out.println(strings);
        String next = strings.iterator().next();
        return Integer.valueOf(next, 2);
    }
    private static int CO2ScrubberRating(List<String> strings) {
        strings = new ArrayList<>(strings);
        int position = 0;
        while (strings.size() > 1) {
            int frequency = 0;
            for (String s : strings) {
                if (s.charAt(position) == '1') {
                    frequency++;
                }
            }
            char bit = (2 * frequency >= strings.size()) ? '1' : '0';
            int finalPosition = position;
            strings.removeIf(s -> s.charAt(finalPosition) == bit);

            ++position;
        }

        System.out.println(strings);
        String next = strings.iterator().next();
        return Integer.valueOf(next, 2);
    }

    @Test
    void inputExample() {
        List<String> strings = List.of("00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010");

        Pair<Integer, Integer> p = binaryDiagnostic(strings);

        assertThat(p.getLeft()).isEqualTo(22);
        assertThat(p.getRight()).isEqualTo(9);

        int oxygen = oxygenGeneratorRating(strings);
        int co2 = CO2ScrubberRating(strings);
        assertThat(oxygen).isEqualTo(23);
        assertThat(co2).isEqualTo(10);
    }

    /**
     * --- Day 3: Binary Diagnostic ---
     * The submarine has been making some odd creaking noises, so you ask it to
     * produce a diagnostic report just in case.
     * <p>
     * The diagnostic report (your puzzle input) consists of a list of binary
     * numbers which, when decoded properly, can tell you many useful things about
     * the conditions of the submarine. The first parameter to check is the power
     * consumption.
     * <p>
     * You need to use the binary numbers in the diagnostic report to generate two
     * new binary numbers (called the gamma rate and the epsilon rate). The power
     * consumption can then be found by multiplying the gamma rate by the epsilon
     * rate.
     * <p>
     * Each bit in the gamma rate can be determined by finding the most common bit
     * in the corresponding position of all numbers in the diagnostic report. For
     * example, given the following diagnostic report:
     * <p>
     * 00100
     * 11110
     * 10110
     * 10111
     * 10101
     * 01111
     * 00111
     * 11100
     * 10000
     * 11001
     * 00010
     * 01010
     * <p>
     * Considering only the first bit of each number, there are five 0 bits and
     * seven 1 bits. Since the most common bit is 1, the first bit of the gamma
     * rate is 1.
     * <p>
     * The most common second bit of the numbers in the diagnostic report is 0, so
     * the second bit of the gamma rate is 0.
     * <p>
     * The most common value of the third, fourth, and fifth bits are 1, 1, and 0,
     * respectively, and so the final three bits of the gamma rate are 110.
     * <p>
     * So, the gamma rate is the binary number 10110, or 22 in decimal.
     * <p>
     * The epsilon rate is calculated in a similar way; rather than use the most
     * common bit, the least common bit from each position is used. So, the
     * epsilon rate is 01001, or 9 in decimal. Multiplying the gamma rate (22) by
     * the epsilon rate (9) produces the power consumption, 198.
     * <p>
     * Use the binary numbers in your diagnostic report to calculate the gamma
     * rate and epsilon rate, then multiply them together. What is the power
     * consumption of the submarine? (Be sure to represent your answer in decimal,
     * not binary.)
     */
    @Test
    void inputPartOne() throws IOException {
        List<String> input = FileUtils.readLines("/day/3/input");

        Pair<Integer, Integer> p = binaryDiagnostic(input);

        assertThat(p.getLeft() * p.getRight()).isEqualTo(738234);
    }

    /**
     * --- Part Two ---
     * Next, you should verify the life support rating, which can be determined by
     * multiplying the oxygen generator rating by the CO2 scrubber rating.
     * <p>
     * Both the oxygen generator rating and the CO2 scrubber rating are values
     * that can be found in your diagnostic report - finding them is the tricky
     * part. Both values are located using a similar process that involves
     * filtering out values until only one remains. Before searching for either
     * rating value, start with the full list of binary numbers from your
     * diagnostic report and consider just the first bit of those numbers. Then:
     * <p>
     * Keep only numbers selected by the bit criteria for the type of rating value for which you are searching. Discard numbers which do not match the bit criteria.
     * If you only have one number left, stop; this is the rating value for which you are searching.
     * Otherwise, repeat the process, considering the next bit to the right.
     * <p>
     * The bit criteria depends on which type of rating value you want to find:
     * <p>
     * To find oxygen generator rating, determine the most common value (0 or 1) in the current bit position, and keep only numbers with that bit in that position. If 0 and 1 are equally common, keep values with a 1 in the position being considered.
     * To find CO2 scrubber rating, determine the least common value (0 or 1) in the current bit position, and keep only numbers with that bit in that position. If 0 and 1 are equally common, keep values with a 0 in the position being considered.
     * <p>
     * For example, to determine the oxygen generator rating value using the same
     * example diagnostic report from above:
     * <p>
     * Start with all 12 numbers and consider only the first bit of each number. There are more 1 bits (7) than 0 bits (5), so keep only the 7 numbers with a 1 in the first position: 11110, 10110, 10111, 10101, 11100, 10000, and 11001.
     * Then, consider the second bit of the 7 remaining numbers: there are more 0 bits (4) than 1 bits (3), so keep only the 4 numbers with a 0 in the second position: 10110, 10111, 10101, and 10000.
     * In the third position, three of the four numbers have a 1, so keep those three: 10110, 10111, and 10101.
     * In the fourth position, two of the three numbers have a 1, so keep those two: 10110 and 10111.
     * In the fifth position, there are an equal number of 0 bits and 1 bits (one each). So, to find the oxygen generator rating, keep the number with a 1 in that position: 10111.
     * As there is only one number left, stop; the oxygen generator rating is 10111, or 23 in decimal.
     * <p>
     * Then, to determine the CO2 scrubber rating value from the same example
     * above:
     * <p>
     * Start again with all 12 numbers and consider only the first bit of each number. There are fewer 0 bits (5) than 1 bits (7), so keep only the 5 numbers with a 0 in the first position: 00100, 01111, 00111, 00010, and 01010.
     * Then, consider the second bit of the 5 remaining numbers: there are fewer 1 bits (2) than 0 bits (3), so keep only the 2 numbers with a 1 in the second position: 01111 and 01010.
     * In the third position, there are an equal number of 0 bits and 1 bits (one each). So, to find the CO2 scrubber rating, keep the number with a 0 in that position: 01010.
     * As there is only one number left, stop; the CO2 scrubber rating is 01010, or 10 in decimal.
     * Finally, to find the life support rating, multiply the oxygen generator rating (23) by the CO2 scrubber rating (10) to get 230.
     * <p>
     * Use the binary numbers in your diagnostic report to calculate the oxygen
     * generator rating and CO2 scrubber rating, then multiply them together. What
     * is the life support rating of the submarine? (Be sure to represent your
     * answer in decimal, not binary.)
     */
    @Test
    void inputPartTwo() throws IOException {
        List<String> input = FileUtils.readLines("/day/3/input");

        int oxygen = oxygenGeneratorRating(input);
        int co2 = CO2ScrubberRating(input);
        assertThat(oxygen * co2).isEqualTo(3969126);

    }

}
