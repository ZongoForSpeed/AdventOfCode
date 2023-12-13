package com.adventofcode.year2015;

import com.adventofcode.maths.IntegerPartition;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class Day15 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day15.class);

    private Day15() {
        // No-Op
    }

    public static Map<Property, Long> compose(Map<Property, Long> currentComposition, Map<Property, Long> ingredient, int teaspoons) {
        Map<Property, Long> composition = new EnumMap<>(currentComposition);
        for (Map.Entry<Property, Long> entry : ingredient.entrySet()) {
            composition.merge(entry.getKey(), entry.getValue() * teaspoons, Long::sum);
        }

        return composition;
    }

    public static Map<Property, Long> compose(List<Pair<String, Map<Property, Long>>> ingredients, IntList partition) {
        Map<Property, Long> properties = new EnumMap<>(Property.class);
        for (int i = 0; i < partition.size(); i++) {
            int quantity = partition.getInt(i);
            Pair<String, Map<Property, Long>> ingredient = ingredients.get(i);
            properties = compose(properties, ingredient.right(), quantity);
        }

        return properties;
    }

    public static long cookieScore(Map<Property, Long> composition, boolean checkCalories) {
        if (checkCalories) {
            long calories = composition.getOrDefault(Property.calories, 0L);
            if (calories != 500) {
                return 0;
            }
        }
        long score = 1;
        for (Property property : Property.values()) {
            if (property.isScore()) {
                long c = composition.getOrDefault(property, 0L);
                if (c <= 0) {
                    return 0;
                } else {
                    score *= c;
                }
            }
        }
        return score;
    }

    public static Map<Property, Long> highestScoringCookie(List<Pair<String, Map<Property, Long>>> ingredients, int teaspoons, boolean checkCalories) {
        Map<Property, Long> bestComposition = null;
        long maxScore = Long.MIN_VALUE;

        List<IntList> partitions = IntegerPartition.partitions(teaspoons, ingredients.size());
        for (IntList partition : partitions) {
            Map<Property, Long> cookieComposition = compose(ingredients, partition);
            long score = cookieScore(cookieComposition, checkCalories);
            if (score > maxScore) {
                LOGGER.debug("Found better composition: {}", cookieComposition);
                maxScore = score;
                bestComposition = cookieComposition;
            }
        }
        return bestComposition;
    }

    /**
     * --- Day 15: Science for Hungry People ---
     *
     * Today, you set out on the task of perfecting your milk-dunking cookie
     * recipe. All you have to do is find the right balance of ingredients.
     *
     * Your recipe leaves room for exactly 100 teaspoons of ingredients. You make
     * a list of the remaining ingredients you could use to finish the recipe
     * (your puzzle input) and their properties per teaspoon:
     *
     *   - capacity (how well it helps the cookie absorb milk)
     *   - durability (how well it keeps the cookie intact when full of milk)
     *   - flavor (how tasty it makes the cookie)
     *   - texture (how it improves the feel of the cookie)
     *   - calories (how many calories it adds to the cookie)
     *
     * You can only measure ingredients in whole-teaspoon amounts accurately, and
     * you have to be accurate so you can reproduce your results in the future.
     * The total score of a cookie can be found by adding up each of the
     * properties (negative totals become 0) and then multiplying together
     * everything except calories.
     *
     * For instance, suppose you have these two ingredients:
     *
     * Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
     * Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
     *
     * Then, choosing to use 44 teaspoons of butterscotch and 56 teaspoons of
     * cinnamon (because the amounts of each ingredient must add up to 100) would
     * result in a cookie with the following properties:
     *
     *   - A capacity of 44*-1 + 56*2 = 68
     *   - A durability of 44*-2 + 56*3 = 80
     *   - A flavor of 44*6 + 56*-2 = 152
     *   - A texture of 44*3 + 56*-1 = 76
     *
     * Multiplying these together (68 * 80 * 152 * 76, ignoring calories for now)
     * results in a total score of 62842880, which happens to be the best score
     * possible given these ingredients. If any properties had produced a negative
     * total, it would have instead become zero, causing the whole score to
     * multiply to zero.
     *
     * Given the ingredients in your kitchen and their properties, what is the
     * total score of the highest-scoring cookie you can make?
     *
     * Your puzzle answer was 222870.
     *
     * --- Part Two ---
     *
     * Your cookie recipe becomes wildly popular! Someone asks if you can make
     * another recipe that has exactly 500 calories per cookie (so they can use it
     * as a meal replacement). Keep the rest of your award-winning process the
     * same (100 teaspoons, same ingredients, same scoring system).
     *
     * For example, given the ingredients above, if you had instead selected 40
     * teaspoons of butterscotch and 60 teaspoons of cinnamon (which still adds to
     * 100), the total calorie count would be 40*8 + 60*3 = 500. The total score
     * would go down, though: only 57600000, the best you can do in such trying
     * circumstances.
     *
     * Given the ingredients in your kitchen and their properties, what is the
     * total score of the highest-scoring cookie you can make with a calorie
     * total of 500?
     *
     * Your puzzle answer was 117936.
     */
    public static long highestCookieScore(Scanner scanner, boolean checkCalories) {
        List<Pair<String, Map<Property, Long>>> ingredients = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] split = line.split(": ");
            String name = split[0];
            Map<Property, Long> collect = Arrays.stream(split[1].split(", ")).map(s -> s.split(" ")).collect(Collectors.toMap(s -> Property.valueOf(s[0]), s -> Long.parseLong(s[1])));
            LOGGER.debug("Ingredient {}: {}", name, collect);
            ingredients.add(Pair.of(name, collect));
        }

        Map<Property, Long> highestScoringCookie = highestScoringCookie(ingredients, 100, checkCalories);
        LOGGER.info("highestScoringCookie : {}", highestScoringCookie);

        return cookieScore(highestScoringCookie, checkCalories);
    }

    public enum Property {
        capacity(true), // (how well it helps the cookie absorb milk)
        durability(true), // (how well it keeps the cookie intact when full of milk)
        flavor(true), // (how tasty it makes the cookie)
        texture(true), // (how it improves the feel of the cookie)
        calories(false); // (how many calories it adds to the cookie)

        private final boolean score;

        Property(boolean score) {
            this.score = score;
        }

        public boolean isScore() {
            return score;
        }
    }
}
