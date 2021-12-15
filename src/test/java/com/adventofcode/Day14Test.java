package com.adventofcode;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class Day14Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day14Test.class);

    private static Map<Pair<Character, Character>, Long> extendedPolymerization(Map<Pair<Character, Character>, Long> template, Map<Pair<Character, Character>, Character> rules) {
        Map<Pair<Character, Character>, Long> newTemplate = new HashMap<>();
        for (Map.Entry<Pair<Character, Character>, Long> entry : template.entrySet()) {
            Pair<Character, Character> pair = entry.getKey();
            Long count = entry.getValue();
            Character character = rules.get(pair);
            if (character != null) {
                newTemplate.compute(Pair.of(pair.getLeft(), character), (ignore, value) -> value == null ? count : value + count);
                newTemplate.compute(Pair.of(character, pair.getRight()), (ignore, value) -> value == null ? count : value + count);
            } else {
                newTemplate.compute(Pair.of(pair.getLeft(), pair.getRight()), (ignore, value) -> value == null ? count : value + count);
            }
        }

        return newTemplate;
    }

    private static long extendedPolymerization(Scanner scanner, int steps) {
        Pattern pattern = Pattern.compile("(\\w+) -> (\\w)");

        Map<Pair<Character, Character>, Character> rules = new HashMap<>();
        Map<Pair<Character, Character>, Long> template = new HashMap<>();

        Map<Character, Long> frequency = new HashMap<>();

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
                    rules.put(Pair.of(left.charAt(0), left.charAt(1)), right.charAt(0));
                }
            } else {
                char[] chars = line.toCharArray();
                for (int i = 1; i < chars.length; i++) {
                    template.compute(Pair.of(chars[i - 1], chars[i]), (ignore, value) -> value == null ? 1 : value + 1);
                }

                frequency.compute(chars[0], (ignore, value) -> value == null ? 1 : value + 1);
                frequency.compute(chars[chars.length - 1], (ignore, value) -> value == null ? 1 : value + 1);
            }
        }

        LOGGER.info("Template: {}", template);
        LOGGER.info("Rules: {}", rules);

        for (int step = 1; step <= steps; step++) {
            template = extendedPolymerization(template, rules);
            LOGGER.debug("After step {}: {}", step, template);
        }

        for (Map.Entry<Pair<Character, Character>, Long> entry : template.entrySet()) {
            Pair<Character, Character> pair = entry.getKey();
            Long count = entry.getValue();
            frequency.compute(pair.getLeft(), (ignore, value) -> value == null ? count : value + count);
            frequency.compute(pair.getRight(), (ignore, value) -> value == null ? count : value + count);
        }

        frequency = frequency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() / 2));

        LOGGER.info("frequency = {}", frequency);

        long max = frequency.values().stream().mapToLong(t -> t).max().orElseThrow();
        long min = frequency.values().stream().mapToLong(t -> t).min().orElseThrow();

        return max - min;
    }

    @Test
    void inputExample() {
        String input = """
                NNCB

                CH -> B
                HH -> N
                CB -> H
                NH -> C
                HB -> C
                HC -> B
                HN -> C
                NN -> C
                BH -> H
                NC -> B
                NB -> B
                BN -> B
                BB -> N
                BC -> B
                CC -> N
                CN -> C""";


        assertThat(extendedPolymerization(new Scanner(input), 10)).isEqualTo(1588);
        assertThat(extendedPolymerization(new Scanner(input), 40)).isEqualTo(2188189693529L);
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
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day14Test.class.getResourceAsStream("/day/14/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(extendedPolymerization(scanner, 10)).isEqualTo(3411);
        }
    }

    /**
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
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day14Test.class.getResourceAsStream("/day/14/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(extendedPolymerization(scanner, 40)).isEqualTo(7477815755570L);
        }
    }

}
