package com.adventofcode.year2021;

import com.adventofcode.utils.CharPair;
import it.unimi.dsi.fastutil.chars.Char2LongMap;
import it.unimi.dsi.fastutil.chars.Char2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2CharMap;
import it.unimi.dsi.fastutil.objects.Object2CharOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day14 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day14.class);

    private Day14() {
        // No-Op
    }

    private static Object2LongMap<CharPair> extendedPolymerization(Object2LongMap<CharPair> template, Object2CharMap<CharPair> rules) {
        Object2LongMap<CharPair> newTemplate = new Object2LongOpenHashMap<>();
        for (Object2LongMap.Entry<CharPair> entry : template.object2LongEntrySet()) {
            CharPair pair = entry.getKey();
            long count = entry.getLongValue();
            Character character = rules.get(pair);
            if (character != null) {
                newTemplate.mergeLong(CharPair.of(pair.left(), character), count, Long::sum);
                newTemplate.mergeLong(CharPair.of(character, pair.right()), count, Long::sum);
            } else {
                newTemplate.mergeLong(CharPair.of(pair.left(), pair.right()), count, Long::sum);
            }
        }

        return newTemplate;
    }

    /**
     * --- Day 14: Extended Polymerization ---
     *
     * The incredible pressures at this depth are starting to put a strain on your
     * submarine. The submarine has polymerization equipment that would produce
     * suitable materials to reinforce the submarine, and the nearby volcanically-
     * active caves should even have the necessary input elements in sufficient
     * quantities.
     *
     * The submarine manual contains instructions for finding the optimal polymer
     * formula; specifically, it offers a polymer template and a list of pair
     * insertion rules (your puzzle input). You just need to work out what polymer
     * would result after repeating the pair insertion process a few times.
     *
     * For example:
     *
     * NNCB
     *
     * CH -> B
     * HH -> N
     * CB -> H
     * NH -> C
     * HB -> C
     * HC -> B
     * HN -> C
     * NN -> C
     * BH -> H
     * NC -> B
     * NB -> B
     * BN -> B
     * BB -> N
     * BC -> B
     * CC -> N
     * CN -> C
     *
     * The first line is the polymer template - this is the starting point of the
     * process.
     *
     * The following section defines the pair insertion rules. A rule like AB -> C
     * means that when elements A and B are immediately adjacent, element C should
     * be inserted between them. These insertions all happen simultaneously.
     *
     * So, starting with the polymer template NNCB, the first step simultaneously
     * considers all three pairs:
     *
     *   - The first pair (NN) matches the rule NN -> C, so element C is inserted
     *     between the first N and the second N.
     *   - The second pair (NC) matches the rule NC -> B, so element B is
     *     inserted between the N and the C.
     *   - The third pair (CB) matches the rule CB -> H, so element H is inserted
     *     between the C and the B.
     *
     * Note that these pairs overlap: the second element of one pair is the first
     * element of the next pair. Also, because all pairs are considered
     * simultaneously, inserted elements are not considered to be part of a pair
     * until the next step.
     *
     * After the first step of this process, the polymer becomes NCNBCHB.
     *
     * Here are the results of a few steps using the above rules:
     *
     * Template:     NNCB
     * After step 1: NCNBCHB
     * After step 2: NBCCNBBBCBHCB
     * After step 3: NBBBCNCCNBBNBNBBCHBHHBCHB
     * After step 4: NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB
     *
     * This polymer grows quickly. After step 5, it has length 97; After step 10,
     * it has length 3073. After step 10, B occurs 1749 times, C occurs 298 times,
     * H occurs 161 times, and N occurs 865 times; taking the quantity of the most
     * common element (B, 1749) and subtracting the quantity of the least common
     * element (H, 161) produces 1749 - 161 = 1588.
     *
     * Apply 10 steps of pair insertion to the polymer template and find the most
     * and least common elements in the result. What do you get if you take the
     * quantity of the most common element and subtract the quantity of the least
     * common element?
     *
     * Your puzzle answer was 3411.
     *
     * --- Part Two ---
     *
     * The resulting polymer isn't nearly strong enough to reinforce the
     * submarine. You'll need to run more steps of the pair insertion process; a
     * total of 40 steps should do it.
     *
     * In the above example, the most common element is B (occurring 2192039569602
     * times) and the least common element is H (occurring 3849876073 times);
     * subtracting these produces 2188189693529.
     *
     * Apply 40 steps of pair insertion to the polymer template and find the most
     * and least common elements in the result. What do you get if you take the
     * quantity of the most common element and subtract the quantity of the least
     * common element?
     */
    public static long extendedPolymerization(Scanner scanner, int steps) {
        Pattern pattern = Pattern.compile("(\\w+) -> (\\w)");

        Object2CharMap<CharPair> rules = new Object2CharOpenHashMap<>();
        Object2LongMap<CharPair> template = new Object2LongOpenHashMap<>();

        Char2LongMap frequency = new Char2LongOpenHashMap();

        boolean readRule = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (StringUtils.isBlank(line)) {
                readRule = true;
                continue;
            }

            if (readRule) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    String left = matcher.group(1);
                    String right = matcher.group(2);
                    rules.put(CharPair.of(left.charAt(0), left.charAt(1)), right.charAt(0));
                }
            } else {
                char[] chars = line.toCharArray();
                for (int i = 1; i < chars.length; i++) {
                    template.mergeLong(CharPair.of(chars[i - 1], chars[i]), 1L, Long::sum);
                }

                frequency.mergeLong(chars[0], 1L, Long::sum);
                frequency.mergeLong(chars[chars.length - 1], 1L, Long::sum);
            }
        }

        LOGGER.info("Template: {}", template);
        LOGGER.info("Rules: {}", rules);

        for (int step = 1; step <= steps; step++) {
            template = extendedPolymerization(template, rules);
            LOGGER.debug("After step {}: {}", step, template);
        }

        for (Object2LongMap.Entry<CharPair> entry : template.object2LongEntrySet()) {
            CharPair pair = entry.getKey();
            long count = entry.getLongValue();
            frequency.mergeLong(pair.left(), count, Long::sum);
            frequency.mergeLong(pair.right(), count, Long::sum);
        }

        frequency = new Char2LongOpenHashMap(frequency.char2LongEntrySet().stream().collect(Collectors.toMap(Char2LongMap.Entry::getCharKey, e -> e.getLongValue() / 2)));

        LOGGER.info("frequency = {}", frequency);

        long max = frequency.values().longStream().max().orElseThrow();
        long min = frequency.values().longStream().min().orElseThrow();

        return max - min;
    }
}
