package com.adventofcode.year2020;

import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.Pair;
import org.apache.commons.collections4.CollectionUtils;
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

    // light red bags contain 1 bright white bag, 2 muted yellow bags.
    private static final Pattern RULE_PATTERN = Pattern.compile("^(.*) bags contain (.*)\\.$");
    private static final Pattern BAG_PATTERN = Pattern.compile("(\\d+) (.*) bag");

    private Day07() {
        // No-Op
    }


    static Pair<String, List<Pair<String, Integer>>> readRule(String rule) {
        Matcher matcher = RULE_PATTERN.matcher(rule);
        if (!matcher.find()) {
            throw new IllegalStateException("Cannot parse rule: " + rule);
        }
        String color = matcher.group(1);
        String rightRule = matcher.group(2);

        List<Pair<String, Integer>> contain = new ArrayList<>();
        for (String s : Splitter.on(", ").split(rightRule)) {
            matcher = BAG_PATTERN.matcher(s);
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
     * <p>
     * Due to recent aviation regulations, many rules (your puzzle input) are
     * being enforced about bags and their contents; bags must be color-coded and
     * must contain specific quantities of other color-coded bags. Apparently,
     * nobody responsible for these regulations considered how long they would
     * take to enforce!
     * <p>
     * For example, consider the following rules:
     * <p>
     * light red bags contain 1 bright white bag, 2 muted yellow bags.
     * dark orange bags contain 3 bright white bags, 4 muted yellow bags.
     * bright white bags contain 1 shiny gold bag.
     * muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
     * shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
     * dark olive bags contain 3 faded blue bags, 4 dotted black bags.
     * vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
     * faded blue bags contain no other bags.
     * dotted black bags contain no other bags.
     * <p>
     * These rules specify the required contents for 9 bag types. In this example,
     * every faded blue bag is empty, every vibrant plum bag contains 11 bags (5
     * faded blue and 6 dotted black), and so on.
     * <p>
     * You have a shiny gold bag. If you wanted to carry it in at least one other
     * bag, how many different bag colors would be valid for the outermost bag?
     * (In other words: how many colors can, eventually, contain at least one
     * shiny gold bag?)
     * <p>
     * In the above rules, the following options would be available to you:
     * <p>
     * - A bright white bag, which can hold your shiny gold bag directly.
     * - A muted yellow bag, which can hold your shiny gold bag directly, plus
     * some other bags.
     * - A dark orange bag, which can hold bright white and muted yellow bags,
     * either of which could then hold your shiny gold bag.
     * - A light red bag, which can hold bright white and muted yellow bags,
     * either of which could then hold your shiny gold bag.
     * <p>
     * So, in this example, the number of bag colors that can eventually contain
     * at least one shiny gold bag is 4.
     * <p>
     * How many bag colors can eventually contain at least one shiny gold bag?
     * (The list of rules is quite long; make sure you get all of it.)
     * <p>
     * --- Part Two ---
     * It's getting pretty expensive to fly these days - not because of ticket
     * prices, but because of the ridiculous number of bags you need to buy!
     * <p>
     * Consider again your shiny gold bag and the rules from the above example:
     * <p>
     * - faded blue bags contain 0 other bags.
     * - dotted black bags contain 0 other bags.
     * - vibrant plum bags contain 11 other bags: 5 faded blue bags and 6
     * dotted black bags.
     * - dark olive bags contain 7 other bags: 3 faded blue bags and 4
     * dotted black bags.
     * <p>
     * So, a single shiny gold bag must contain 1 dark olive bag (and the 7 bags within it) plus 2 vibrant plum bags (and the 11 bags within each of those): 1 + 1*7 + 2 + 2*11 = 32 bags!
     * <p>
     * Of course, the actual rules have a small chance of going several levels deeper than this example; be sure to count all of the bags, even if the nesting becomes topologically impractical!
     * <p>
     * Here's another example:
     * <p>
     * shiny gold bags contain 2 dark red bags.
     * dark red bags contain 2 dark orange bags.
     * dark orange bags contain 2 dark yellow bags.
     * dark yellow bags contain 2 dark green bags.
     * dark green bags contain 2 dark blue bags.
     * dark blue bags contain 2 dark violet bags.
     * dark violet bags contain no other bags.
     * In this example, a single shiny gold bag must contain 126 other bags.
     * <p>
     * How many individual bags are required inside your single shiny gold bag?
     */
    public static long countShinyGold(List<String> rules) {
        Map<String, List<String>> bags = new HashMap<>();
        rules.stream()
                .map(Day07::readRule)
                .forEach(r -> {
                    String color = r.left();
                    r.right().stream().map(Pair::left).forEach(c -> bags.computeIfAbsent(c, _ -> new ArrayList<>()).add(color));
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
