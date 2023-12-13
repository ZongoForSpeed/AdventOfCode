package com.adventofcode.year2020;

import org.apache.commons.collections4.CollectionUtils;
import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day07 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07.class);
    private static final Pattern PATTERN = Pattern.compile("(\\d+) (.*) bag");

    private Day07() {
        // No-Op
    }

    static Pair<String, List<Pair<String, Integer>>> readRule(String rule) {
        String[] splitContain = rule.replace(".", "").split(" contain ");
        String leftRule = splitContain[0];
        String rightRule = splitContain[1];

        String color = leftRule.replace(" bags", "");
        String[] splitComma = rightRule.split(", ");

        List<Pair<String, Integer>> contain = new ArrayList<>();
        for (String s : splitComma) {
            Matcher matcher = PATTERN.matcher(s);
            if (matcher.find()) {
                contain.add(Pair.of(matcher.group(2), Integer.valueOf(matcher.group(1))));
            }
        }

        return Pair.of(color, contain);
    }

    /**
     * --- Day 7: Handy Haversacks ---
     * You land at the regional airport in time for your next flight. In fact, it
     * looks like you'll even have time to grab some food: all flights are
     * currently delayed due to issues in luggage processing.
     *
     * Due to recent aviation regulations, many rules (your puzzle input) are
     * being enforced about bags and their contents; bags must be color-coded and
     * must contain specific quantities of other color-coded bags. Apparently,
     * nobody responsible for these regulations considered how long they would
     * take to enforce!
     *
     * For example, consider the following rules:
     *
     * light red bags contain 1 bright white bag, 2 muted yellow bags.
     * dark orange bags contain 3 bright white bags, 4 muted yellow bags.
     * bright white bags contain 1 shiny gold bag.
     * muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
     * shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
     * dark olive bags contain 3 faded blue bags, 4 dotted black bags.
     * vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
     * faded blue bags contain no other bags.
     * dotted black bags contain no other bags.
     *
     * These rules specify the required contents for 9 bag types. In this example,
     * every faded blue bag is empty, every vibrant plum bag contains 11 bags (5
     * faded blue and 6 dotted black), and so on.
     *
     * You have a shiny gold bag. If you wanted to carry it in at least one other
     * bag, how many different bag colors would be valid for the outermost bag?
     * (In other words: how many colors can, eventually, contain at least one
     * shiny gold bag?)
     *
     * In the above rules, the following options would be available to you:
     *
     * - A bright white bag, which can hold your shiny gold bag directly.
     * - A muted yellow bag, which can hold your shiny gold bag directly, plus
     * some other bags.
     * - A dark orange bag, which can hold bright white and muted yellow bags,
     * either of which could then hold your shiny gold bag.
     * - A light red bag, which can hold bright white and muted yellow bags,
     * either of which could then hold your shiny gold bag.
     *
     * So, in this example, the number of bag colors that can eventually contain
     * at least one shiny gold bag is 4.
     *
     * How many bag colors can eventually contain at least one shiny gold bag?
     * (The list of rules is quite long; make sure you get all of it.)
     *
     * --- Part Two ---
     * It's getting pretty expensive to fly these days - not because of ticket
     * prices, but because of the ridiculous number of bags you need to buy!
     *
     * Consider again your shiny gold bag and the rules from the above example:
     *
     * - faded blue bags contain 0 other bags.
     * - dotted black bags contain 0 other bags.
     * - vibrant plum bags contain 11 other bags: 5 faded blue bags and 6
     * dotted black bags.
     * - dark olive bags contain 7 other bags: 3 faded blue bags and 4
     * dotted black bags.
     *
     * So, a single shiny gold bag must contain 1 dark olive bag (and the 7 bags within it) plus 2 vibrant plum bags (and the 11 bags within each of those): 1 + 1*7 + 2 + 2*11 = 32 bags!
     *
     * Of course, the actual rules have a small chance of going several levels deeper than this example; be sure to count all of the bags, even if the nesting becomes topologically impractical!
     *
     * Here's another example:
     *
     * shiny gold bags contain 2 dark red bags.
     * dark red bags contain 2 dark orange bags.
     * dark orange bags contain 2 dark yellow bags.
     * dark yellow bags contain 2 dark green bags.
     * dark green bags contain 2 dark blue bags.
     * dark blue bags contain 2 dark violet bags.
     * dark violet bags contain no other bags.
     * In this example, a single shiny gold bag must contain 126 other bags.
     *
     * How many individual bags are required inside your single shiny gold bag?
     */
    public static long countShinyGold(List<String> rules) {
        Map<String, List<String>> bags = new HashMap<>();
        rules.stream()
                .map(Day07::readRule)
                .forEach(r -> {
                    String color = r.left();
                    r.right().stream().map(Pair::left).forEach(c -> bags.computeIfAbsent(c, ignore -> new ArrayList<>()).add(color));
                });

        LOGGER.info("Bags {}", bags);
        Queue<String> queue = new ArrayDeque<>();
        queue.add("shiny gold");

        Set<String> set = new HashSet<>();

        while (!queue.isEmpty()) {
            String color = queue.remove();

            List<String> colors = bags.get(color);
            if (CollectionUtils.isNotEmpty(colors)) {
                for (String c : colors) {
                    if (set.add(c)) {
                        queue.add(c);
                    }
                }
            }

        }
        return set.size();
    }

    private static long handyHaversacks(Map<String, List<Pair<String, Integer>>> bags, String color, Map<String, Long> cache) {
        Long value = cache.get(color);
        if (value != null) {
            return value;
        }

        long count = 1;
        List<Pair<String, Integer>> colors = bags.get(color);
        if (CollectionUtils.isNotEmpty(colors)) {
            for (Pair<String, Integer> pair : colors) {
                count += pair.right() * handyHaversacks(bags, pair.left(), cache);
            }
        }

        cache.put(color, count);
        return count;
    }

    public static long countHandyHaversacks(List<String> rules) {
        Map<String, List<Pair<String, Integer>>> bags = rules.stream().map(Day07::readRule).collect(Collectors.toMap(Pair::left, Pair::right));

        return handyHaversacks(bags, "shiny gold", new HashMap<>()) - 1;
    }
}
