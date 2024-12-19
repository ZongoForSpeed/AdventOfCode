package com.adventofcode.year2024;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class Day19 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day19.class);

    private Day19() {
        // No-Op
    }

    /**
     * --- Day 19: Linen Layout ---
     * <p>
     * Today, The Historians take you up to the hot springs on Gear Island! Very
     * suspiciously, absolutely nothing goes wrong as they begin their careful
     * search of the vast field of helixes.
     * <p>
     * Could this finally be your chance to visit the onsen next door? Only one
     * way to find out.
     * <p>
     * After a brief conversation with the reception staff at the onsen front
     * desk, you discover that you don't have the right kind of money to pay the
     * admission fee. However, before you can leave, the staff get your attention.
     * Apparently, they've heard about how you helped at the hot springs, and
     * they're willing to make a deal: if you can simply help them arrange their
     * towels, they'll let you in for free!
     * <p>
     * Every towel at this onsen is marked with a pattern of colored stripes.
     * There are only a few patterns, but for any particular pattern, the staff
     * can get you as many towels with that pattern as you need. Each stripe can
     * be white (w), blue (u), black (b), red (r), or green (g). So, a towel with
     * the pattern ggr would have a green stripe, a green stripe, and then a red
     * stripe, in that order. (You can't reverse a pattern by flipping a towel
     * upside-down, as that would cause the onsen logo to face the wrong way.)
     * <p>
     * The Official Onsen Branding Expert has produced a list of designs - each a
     * long sequence of stripe colors - that they would like to be able to
     * display. You can use any towels you want, but all of the towels' stripes
     * must exactly match the desired design. So, to display the design rgrgr, you
     * could use two rg towels and then an r towel, an rgr towel and then a gr
     * towel, or even a single massive rgrgr towel (assuming such towel patterns
     * were actually available).
     * <p>
     * To start, collect together all of the available towel patterns and the list
     * of desired designs (your puzzle input). For example:
     * <p>
     * r, wr, b, g, bwu, rb, gb, br
     * <p>
     * brwrr
     * bggr
     * gbbr
     * rrbgbr
     * ubwu
     * bwurrg
     * brgr
     * bbrgwb
     * <p>
     * The first line indicates the available towel patterns; in this example, the
     * onsen has unlimited towels with a single red stripe (r), unlimited towels
     * with a white stripe and then a red stripe (wr), and so on.
     * <p>
     * After the blank line, the remaining lines each describe a design the onsen
     * would like to be able to display. In this example, the first design (brwrr)
     * indicates that the onsen would like to be able to display a black stripe, a
     * red stripe, a white stripe, and then two red stripes, in that order.
     * <p>
     * Not all designs will be possible with the available towels. In the above
     * example, the designs are possible or impossible as follows:
     * <p>
     * - brwrr can be made with a br towel, then a wr towel, and then finally
     * an r towel.
     * - bggr can be made with a b towel, two g towels, and then an r towel.
     * - gbbr can be made with a gb towel and then a br towel.
     * - rrbgbr can be made with r, rb, g, and br.
     * - ubwu is impossible.
     * - bwurrg can be made with bwu, r, r, and g.
     * - brgr can be made with br, g, and r.
     * - bbrgwb is impossible.
     * <p>
     * In this example, 6 of the eight designs are possible with the available
     * towel patterns.
     * <p>
     * To get into the onsen as soon as possible, consult your list of towel
     * patterns and desired designs carefully. How many designs are possible?
     */
    public static int partOne(Scanner scanner) {
        String firstLine = scanner.nextLine();
        List<String> patterns = Splitter.on(", ").splitToList(firstLine);

        List<String> towels = readTowels(scanner);

        LOGGER.trace("patterns = {}, towels = {}", patterns, towels);
        int count = 0;
        Map<String, Boolean> cache = new HashMap<>();
        for (String towel : towels) {
            if (possible(cache, towel, patterns)) {
                LOGGER.info("towel {} is possible", towel);
                ++count;
            } else {
                LOGGER.info("towel {} is impossible", towel);
            }
        }
        return count;
    }

    /**
     * --- Part Two ---
     * <p>
     * The staff don't really like some of the towel arrangements you came up
     * with. To avoid an endless cycle of towel rearrangement, maybe you should
     * just give them every possible option.
     * <p>
     * Here are all of the different ways the above example's designs can be made:
     * <p>
     * brwrr can be made in two different ways: b, r, wr, r or br, wr, r.
     * <p>
     * bggr can only be made with b, g, g, and r.
     * <p>
     * gbbr can be made 4 different ways:
     * <p>
     * - g, b, b, r
     * - g, b, br
     * - gb, b, r
     * - gb, br
     * <p>
     * rrbgbr can be made 6 different ways:
     * <p>
     * - r, r, b, g, b, r
     * - r, r, b, g, br
     * - r, r, b, gb, r
     * - r, rb, g, b, r
     * - r, rb, g, br
     * - r, rb, gb, r
     * <p>
     * bwurrg can only be made with bwu, r, r, and g.
     * <p>
     * brgr can be made in two different ways: b, r, g, r or br, g, r.
     * <p>
     * ubwu and bbrgwb are still impossible.
     * <p>
     * Adding up all of the ways the towels in this example could be arranged into
     * the desired designs yields 16 (2 + 1 + 4 + 6 + 1 + 2).
     * <p>
     * They'll let you into the onsen as soon as you have the list. What do you
     * get if you add up the number of different ways you could make each design?
     */
    public static long partTwo(Scanner scanner) {
        String firstLine = scanner.nextLine();
        List<String> patterns = Splitter.on(", ").splitToList(firstLine);

        List<String> towels = readTowels(scanner);

        LOGGER.trace("patterns = {}, towels = {}", patterns, towels);
        long count = 0;
        Map<String, Long> cache = new HashMap<>();
        for (String towel : towels) {
            long layout = layout(cache, towel, patterns);
            LOGGER.info("tower {} has {} layouts", towel, layout);
            count += layout;
        }
        return count;
    }

    private static List<String> readTowels(Scanner scanner) {
        List<String> towels = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (StringUtils.isNotBlank(line)) {
                towels.add(line);
            }
        }
        return towels;
    }

    private static boolean possible(Map<String, Boolean> cache, String towel, List<String> patterns) {
        Boolean value = cache.get(towel);
        if (value != null) {
            return value;
        }

        value = false;
        for (String pattern : patterns) {
            if (towel.startsWith(pattern)) {
                if (towel.length() == pattern.length()) {
                    value = true;
                    break;
                }
                String substring = towel.substring(pattern.length());
                if (possible(cache, substring, patterns)) {
                    value = true;
                    break;
                }
            }
        }

        cache.put(towel, value);
        return value;
    }

    private static long layout(Map<String, Long> cache, String towel, List<String> patterns) {
        Long value = cache.get(towel);
        if (value != null) {
            return value;
        }

        long result = 0;
        for (String pattern : patterns) {
            if (towel.startsWith(pattern)) {
                if (towel.length() == pattern.length()) {
                    result++;
                    continue;
                }
                String substring = towel.substring(pattern.length());
                result += layout(cache, substring, patterns);
            }
        }

        cache.put(towel, result);
        return result;
    }
}
