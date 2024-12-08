package com.adventofcode.year2021;

import com.adventofcode.common.maths.Permutations;
import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day08 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day08.class);
    private static final Pattern PATTERN = Pattern.compile("^(.*) | (.*)$");

    public final List<Map<String, Integer>> mappings;

    Day08() {
        mappings = Permutations.permutations("abcdefg").stream().map(Day08::getMapping).toList();
    }

    public static String sort(String inputString) {
        char[] tempArray = inputString.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

    private static String buildSignal(String permutation, IntList digits) {
        StringBuilder sb = new StringBuilder();
        for (Integer digit : digits) {
            sb.append(permutation.charAt(digit));
        }

        return sort(sb.toString());
    }

    private static Map<String, Integer> getMapping(String permutation) {
        Map<String, Integer> mapping = new HashMap<>();
        //                                              a  b  c  e  f  g
        mapping.put(buildSignal(permutation, IntList.of(0, 1, 2, 4, 5, 6)), 0);
        //                                              c  f
        mapping.put(buildSignal(permutation, IntList.of(2, 5)), 1);
        //                                              a  c  d  e  g
        mapping.put(buildSignal(permutation, IntList.of(0, 2, 3, 4, 6)), 2);
        //                                              a  c  d  f  g
        mapping.put(buildSignal(permutation, IntList.of(0, 2, 3, 5, 6)), 3);
        //                                              b  c  d  f
        mapping.put(buildSignal(permutation, IntList.of(1, 2, 3, 5)), 4);
        //                                              a  b  d  f  g
        mapping.put(buildSignal(permutation, IntList.of(0, 1, 3, 5, 6)), 5);
        //                                              a  b  d  e  f  g
        mapping.put(buildSignal(permutation, IntList.of(0, 1, 3, 4, 5, 6)), 6);
        //                                              a  c  f
        mapping.put(buildSignal(permutation, IntList.of(0, 2, 5)), 7);
        //                                              a  b  c  d  e  f  g
        mapping.put(buildSignal(permutation, IntList.of(0, 1, 2, 3, 4, 5, 6)), 8);
        //                                              a  b  c  d  f  g
        mapping.put(buildSignal(permutation, IntList.of(0, 1, 2, 3, 5, 6)), 9);
        return Map.copyOf(mapping);
    }

    /**
     * --- Day 8: Seven Segment Search ---
     * <p>
     * You barely reach the safety of the cave when the whale smashes into the
     * cave mouth, collapsing it. Sensors indicate another exit to this cave at a
     * much greater depth, so you have no choice but to press on.
     * <p>
     * As your submarine slowly makes its way through the cave system, you notice
     * that the four-digit seven-segment displays in your submarine are
     * malfunctioning; they must have been damaged during the escape. You'll be
     * in a lot of trouble without them, so you'd better figure out what's wrong.
     * <p>
     * Each digit of a seven-segment display is rendered by turning on or off any
     * of seven segments named a through g:
     * <p>
     * 0:      1:      2:      3:      4:
     * aaaa    ....    aaaa    aaaa    ....
     * b    c  .    c  .    c  .    c  b    c
     * b    c  .    c  .    c  .    c  b    c
     * ....    ....    dddd    dddd    dddd
     * e    f  .    f  e    .  .    f  .    f
     * e    f  .    f  e    .  .    f  .    f
     * gggg    ....    gggg    gggg    ....
     * <p>
     * 5:      6:      7:      8:      9:
     * aaaa    aaaa    aaaa    aaaa    aaaa
     * b    .  b    .  .    c  b    c  b    c
     * b    .  b    .  .    c  b    c  b    c
     * dddd    dddd    ....    dddd    dddd
     * .    f  e    f  .    f  e    f  .    f
     * .    f  e    f  .    f  e    f  .    f
     * gggg    gggg    ....    gggg    gggg
     * <p>
     * So, to render a 1, only segments c and f would be turned on; the rest
     * would be off. To render a 7, only segments a, c, and f would be turned on.
     * <p>
     * The problem is that the signals which control the segments have been mixed
     * up on each display. The submarine is still trying to display numbers by
     * producing output on signal wires a through g, but those wires are
     * connected to segments randomly. Worse, the wire/segment connections are
     * mixed up separately for each four-digit display! (All of the digits within
     * a display use the same connections, though.)
     * <p>
     * So, you might know that only signal wires b and g are turned on, but that
     * doesn't mean segments b and g are turned on: the only digit that uses two
     * segments is 1, so it must mean segments c and f are meant to be on. With
     * just that information, you still can't tell which wire (b/g) goes to which
     * segment (c/f). For that, you'll need to collect more information.
     * <p>
     * For each display, you watch the changing signals for a while, make a note
     * of all ten unique signal patterns you see, and then write down a single
     * four digit output value (your puzzle input). Using the signal patterns, you
     * should be able to work out which pattern corresponds to which digit.
     * <p>
     * For example, here is what you might see in a single entry in your notes:
     * <p>
     * acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab |
     * cdfeb fcadb cdfeb cdbaf
     * <p>
     * (The entry is wrapped here to two lines so it fits; in your notes, it will
     * all be on a single line.)
     * <p>
     * Each entry consists of ten unique signal patterns, a | delimiter, and
     * finally the four digit output value. Within an entry, the same wire/segment
     * connections are used (but you don't know what the connections actually
     * are). The unique signal patterns correspond to the ten different ways the
     * submarine tries to render a digit using the current wire/segment
     * connections. Because 7 is the only digit that uses three segments, dab in
     * the above example means that to render a 7, signal lines d, a, and b are
     * on. Because 4 is the only digit that uses four segments, eafb means that
     * to render a 4, signal lines e, a, f, and b are on.
     * <p>
     * Using this information, you should be able to work out which combination of
     * signal wires corresponds to each of the ten digits. Then, you can decode
     * the four digit output value. Unfortunately, in the above example, all of
     * the digits in the output value (cdfeb fcadb cdfeb cdbaf) use five segments
     * and are more difficult to deduce.
     * <p>
     * For now, focus on the easy digits. Consider this larger example:
     * <p>
     * be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb |
     * fdgacbe cefdb cefbgd gcbe
     * edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec |
     * fcgedb cgb dgebacf gc
     * fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef |
     * cg cg fdcagb cbg
     * fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega |
     * efabcd cedba gadfec cb
     * aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga |
     * gecf egdcabf bgf bfgea
     * fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf |
     * gebdcfa ecba ca fadegcb
     * dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf |
     * cefg dcbef fcge gbcadfe
     * bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd |
     * ed bcgafe cdgba cbgef
     * egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg |
     * gbdfcae bgc cg cgb
     * gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc |
     * fgae cfgab fg bagce
     * <p>
     * Because the digits 1, 4, 7, and 8 each use a unique number of segments,
     * you should be able to tell which combinations of signals correspond to
     * those digits. Counting only digits in the output values (the part after |
     * on each line), in the above example, there are 26 instances of digits that
     * use a unique number of segments (highlighted above).
     * <p>
     * In the output values, how many times do digits 1, 4, 7, or 8 appear?
     * <p>
     * Your puzzle answer was 355.
     */
    public long count1478(Scanner scanner) {
        long count = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.find()) {
                count += Splitter.on(' ').splitToStream(matcher.group(2)).mapToInt(String::length).filter(i -> i == 2 || i == 3 || i == 4 || i == 7).count();
            } else {
                throw new IllegalStateException("Cannot parse line: " + line);
            }
        }
        return count;
    }

    public int solveMapping(String line) {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find()) {
            List<String> left = Splitter.on(' ').splitToStream(matcher.group(1)).map(Day08::sort).toList();
            List<String> right = Splitter.on(' ').splitToStream(matcher.group(2)).map(Day08::sort).toList();

            for (Map<String, Integer> mapping : mappings) {
                if (left.stream().allMatch(mapping::containsKey)) {
                    LOGGER.debug("Mapping for line {} is mapping {}", line, mapping);
                    int reduce = right.stream().map(mapping::get).reduce(0, (a, b) -> a * 10 + b);
                    LOGGER.info("Numbers = {}, reduce = {}", right, reduce);
                    return reduce;
                }
            }

            LOGGER.error("Cannot find mapping for line = {}", line);
            return 0;
        } else {
            throw new IllegalStateException("Cannot parse line:" + line);
        }
    }

    /**
     * --- Part Two ---
     * <p>
     * Through a little deduction, you should now be able to determine the
     * remaining digits. Consider again the first example above:
     * <p>
     * acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab |
     * cdfeb fcadb cdfeb cdbaf
     * <p>
     * After some careful analysis, the mapping between signal wires and segments
     * only make sense in the following configuration:
     * <p>
     * dddd
     * e    a
     * e    a
     * ffff
     * g    b
     * g    b
     * cccc
     * <p>
     * So, the unique signal patterns would correspond to the following digits:
     * <p>
     * - acedgfb: 8
     * - cdfbe: 5
     * - gcdfa: 2
     * - fbcad: 3
     * - dab: 7
     * - cefabd: 9
     * - cdfgeb: 6
     * - eafb: 4
     * - cagedb: 0
     * - ab: 1
     * <p>
     * Then, the four digits of the output value can be decoded:
     * <p>
     * - cdfeb: 5
     * - fcadb: 3
     * - cdfeb: 5
     * - cdbaf: 3
     * <p>
     * Therefore, the output value for this entry is 5353.
     * <p>
     * Following this same process for each entry in the second, larger example
     * above, the output value of each entry can be determined:
     * <p>
     * - fdgacbe cefdb cefbgd gcbe: 8394
     * - fcgedb cgb dgebacf gc: 9781
     * - cg cg fdcagb cbg: 1197
     * - efabcd cedba gadfec cb: 9361
     * - gecf egdcabf bgf bfgea: 4873
     * - gebdcfa ecba ca fadegcb: 8418
     * - cefg dcbef fcge gbcadfe: 4548
     * - ed bcgafe cdgba cbgef: 1625
     * - gbdfcae bgc cg cgb: 8717
     * - fgae cfgab fg bagce: 4315
     * <p>
     * Adding all of the output values in this larger example produces 61229.
     * <p>
     * For each entry, determine all of the wire/segment connections and decode
     * the four-digit output values. What do you get if you add up all of the
     * output values?
     * <p>
     * Your puzzle answer was 983030.
     */
    public int solveMapping(Scanner scanner) {
        int sum = 0;
        while (scanner.hasNextLine()) {
            sum += solveMapping(scanner.nextLine());
        }
        return sum;
    }
}
