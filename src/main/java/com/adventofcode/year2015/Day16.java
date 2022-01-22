package com.adventofcode.year2015;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16.class);
    private static final Pattern PATTERN = Pattern.compile("Sue (\\d+): (.*)");

    public static List<Pair<Integer, Map<Compounds, Integer>>> readInput(Scanner scanner) {
        List<Pair<Integer, Map<Compounds, Integer>>> sues = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                int sueNumber = Integer.parseInt(matcher.group(1));
                Map<Compounds, Integer> collect = Arrays.stream(matcher.group(2).split(", "))
                        .map(s -> s.split(": "))
                        .collect(Collectors.toMap(
                                s -> Compounds.valueOf(s[0]),
                                s -> Integer.parseInt(s[1])
                        ));
                collect = new EnumMap<>(collect);
                LOGGER.debug("Sue {} -> {}", sueNumber, collect);
                sues.add(Pair.of(sueNumber, collect));

            } else {
                LOGGER.error("Cannot parse line: {}", line);
            }
        }
        return sues;
    }

    /**
     * --- Day 16: Aunt Sue ---
     *
     * Your Aunt Sue has given you a wonderful gift, and you'd like to send her a
     * thank you card. However, there's a small problem: she signed it "From, Aunt
     * Sue".
     *
     * You have 500 Aunts named "Sue".
     *
     * So, to avoid sending the card to the wrong person, you need to figure out
     * which Aunt Sue (which you conveniently number 1 to 500, for sanity) gave
     * you the gift. You open the present and, as luck would have it, good ol'
     * Aunt Sue got you a My First Crime Scene Analysis Machine! Just what you
     * wanted. Or needed, as the case may be.
     *
     * The My First Crime Scene Analysis Machine (MFCSAM for short) can detect a
     * few specific compounds in a given sample, as well as how many distinct
     * kinds of those compounds there are. According to the instructions, these
     * are what the MFCSAM can detect:
     *
     *   - children, by human DNA age analysis.
     *   - cats. It doesn't differentiate individual breeds.
     *   - Several seemingly random breeds of dog: samoyeds, pomeranians, akitas,
     *     and vizslas.
     *   - goldfish. No other kinds of fish.
     *   - trees, all in one group.
     *   - cars, presumably by exhaust or gasoline or something.
     *   - perfumes, which is handy, since many of your Aunts Sue wear a few
     *     kinds.
     *
     * In fact, many of your Aunts Sue have many of these. You put the wrapping
     * from the gift into the MFCSAM. It beeps inquisitively at you a few times
     * and then prints out a message on ticker tape:
     *
     * children: 3
     * cats: 7
     * samoyeds: 2
     * pomeranians: 3
     * akitas: 0
     * vizslas: 0
     * goldfish: 5
     * trees: 3
     * cars: 2
     * perfumes: 1
     *
     * You make a list of the things you can remember about each Aunt Sue. Things
     * missing from your list aren't zero - you simply don't remember the value.
     *
     * What is the number of the Sue that got you the gift?
     *
     * Your puzzle answer was 103.
     */
    public static Integer findAuntSuePartOne(Scanner scanner) {
        AuntSue sue = new AuntSue();
        List<Pair<Integer, Map<Compounds, Integer>>> sues = readInput(scanner);

        Integer auntSue = null;
        for (Pair<Integer, Map<Compounds, Integer>> pair : sues) {
            boolean found = true;
            for (Map.Entry<Compounds, Integer> entry : pair.getRight().entrySet()) {
                Integer integer = sue.sue.get(entry.getKey());
                if (!Objects.equals(entry.getValue(), integer)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                auntSue = pair.getLeft();
                LOGGER.info("Found matching sue: {}", pair);
            }
        }
        return auntSue;
    }

    /**
     * --- Part Two ---
     *
     * As you're about to send the thank you note, something in the MFCSAM's
     * instructions catches your eye. Apparently, it has an outdated
     * retroencabulator, and so the output from the machine isn't exact values -
     * some of them indicate ranges.
     *
     * In particular, the cats and trees readings indicates that there are greater
     * than that many (due to the unpredictable nuclear decay of cat dander and
     * tree pollen), while the pomeranians and goldfish readings indicate that
     * there are fewer than that many (due to the modial interaction of
     * magnetoreluctance).
     *
     * What is the number of the real Aunt Sue?
     *
     * Your puzzle answer was 405.
     */
    public static Integer findAuntSuePartTwo(Scanner scanner) {
        AuntSue sue = new AuntSue();
        List<Pair<Integer, Map<Compounds, Integer>>> sues = readInput(scanner);

        Integer auntSue = null;
        for (Pair<Integer, Map<Compounds, Integer>> pair : sues) {
            boolean found = true;
            for (Map.Entry<Compounds, Integer> entry : pair.getRight().entrySet()) {
                Integer integer = sue.sue.get(entry.getKey());
                if (!entry.getKey().compare(entry.getValue(), integer)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                auntSue = pair.getLeft();
                LOGGER.info("Found matching sue: {}", pair);
            }
        }
        return auntSue;
    }

    public enum Compounds {
        children(0),
        cats(1),
        samoyeds(0),
        pomeranians(-1),
        akitas(0),
        vizslas(0),
        goldfish(-1),
        trees(1),
        cars(0),
        perfumes(0);

        private final int comparator;

        Compounds(int comparator) {
            this.comparator = comparator;
        }

        boolean compare(Integer a, Integer b) {
            if (a == null || b == null) {
                return false;
            }
            return Integer.compare(a, b) == comparator;
        }
    }

    public static class AuntSue {
        private final Map<Compounds, Integer> sue;

        private AuntSue() {
            try (Scanner scannerSue = new Scanner("""
                    children: 3
                    cats: 7
                    samoyeds: 2
                    pomeranians: 3
                    akitas: 0
                    vizslas: 0
                    goldfish: 5
                    trees: 3
                    cars: 2
                    perfumes: 1""")) {
                sue = new EnumMap<>(Compounds.class);
                while (scannerSue.hasNextLine()) {
                    String[] split = scannerSue.nextLine().split(": ");
                    sue.put(Compounds.valueOf(split[0]), Integer.parseInt(split[1]));
                }
            }
        }
    }
}
