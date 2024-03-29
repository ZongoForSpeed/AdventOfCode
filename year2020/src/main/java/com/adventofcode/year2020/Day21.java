package com.adventofcode.year2020;

import org.apache.commons.collections4.SetUtils;
import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day21 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21.class);
    private static final Pattern FOOD_PATTERN = Pattern.compile("(.*) \\(contains (.*)\\)");

    private Day21() {
        // Nop
    }

    /**
     * --- Day 21: Allergen Assessment ---
     * You reach the train's last stop and the closest you can get to your
     * vacation island without getting wet. There aren't even any boats here, but
     * nothing can stop you now: you build a raft. You just need a few days' worth
     * of food for your journey.
     *
     * You don't speak the local language, so you can't read any ingredients
     * lists. However, sometimes, allergens are listed in a language you do
     * understand. You should be able to use this information to determine which
     * ingredient contains which allergen and work out which foods are safe to
     * take with you on your trip.
     *
     * You start by compiling a list of foods (your puzzle input), one food per
     * line. Each line includes that food's ingredients list followed by some or
     * all of the allergens the food contains.
     *
     * Each allergen is found in exactly one ingredient. Each ingredient contains
     * zero or one allergen. Allergens aren't always marked; when they're listed
     * (as in (contains nuts, shellfish) after an ingredients list), the
     * ingredient that contains each listed allergen will be somewhere in the
     * corresponding ingredients list. However, even if an allergen isn't listed,
     * the ingredient that contains that allergen could still be present: maybe
     * they forgot to label it, or maybe it was labeled in a language you don't
     * know.
     *
     * For example, consider the following list of foods:
     *
     * mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
     * trh fvjkl sbzzf mxmxvkd (contains dairy)
     * sqjhc fvjkl (contains soy)
     * sqjhc mxmxvkd sbzzf (contains fish)
     *
     * The first food in the list has four ingredients (written in a language you
     * don't understand): mxmxvkd, kfcds, sqjhc, and nhms. While the food might
     * contain other allergens, a few allergens the food definitely contains are
     * listed afterward: dairy and fish.
     *
     * The first step is to determine which ingredients can't possibly contain any
     * of the allergens in any food in your list. In the above example, none of
     * the ingredients kfcds, nhms, sbzzf, or trh can contain an allergen.
     * Counting the number of times any of these ingredients appear in any
     * ingredients list produces 5: they all appear once each except sbzzf, which
     * appears twice.
     *
     * Determine which ingredients cannot possibly contain any of the allergens in
     * your list. How many times do any of those ingredients appear?
     *
     * --- Part Two ---
     *
     * Now that you've isolated the inert ingredients, you should have enough
     * information to figure out which ingredient contains which allergen.
     *
     * In the above example:
     *
     * mxmxvkd contains dairy.
     * sqjhc contains fish.
     * fvjkl contains soy.
     *
     * Arrange the ingredients alphabetically by their allergen and separate them
     * by commas to produce your canonical dangerous ingredient list. (There
     * should not be any spaces in your canonical dangerous ingredient list.) In
     * the above example, this would be mxmxvkd,sqjhc,fvjkl.
     *
     * Time to stock your raft with supplies. What is your canonical dangerous
     * ingredient list?
     */
    static Pair<Long, String> findAllergenAssessment(Scanner scanner) {

        List<Pair<Set<String>, Set<String>>> foodIngredientsList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            Matcher matcher = FOOD_PATTERN.matcher(line);
            if (matcher.find()) {
                String[] ingredients = matcher.group(1).split(" ");
                String[] allergens = matcher.group(2).split(", ");

                LOGGER.debug("ingredients: {}, allergens: {}", ingredients, allergens);
                foodIngredientsList.add(Pair.of(Set.of(allergens), Set.of(ingredients)));
            }
        }
        LOGGER.info("foodIngredientsList: {}", foodIngredientsList);

        Set<String> allAllergens = foodIngredientsList.stream().map(Pair::left).flatMap(Set::stream).collect(Collectors.toSet());
        LOGGER.info("allAllergens: {}", allAllergens);

        Set<String> allIngredients = foodIngredientsList.stream().map(Pair::right).flatMap(Set::stream).collect(Collectors.toSet());
        LOGGER.info("allIngredients: {}", allIngredients);

        Map<String, Set<String>> possibleAllergen = new HashMap<>();
        for (String allergen : allAllergens) {
            Optional<Set<String>> intersection = foodIngredientsList.stream().filter(p -> p.left().contains(allergen))
                    .map(Pair::right)
                    .reduce(SetUtils::intersection);
            intersection.ifPresent(i -> possibleAllergen.put(allergen, new HashSet<>(i)));
        }

        LOGGER.info("possibleAllergen: {}", possibleAllergen);

        while (possibleAllergen.values().stream().anyMatch(t -> t.size() > 1)) {
            List<String> ingredients = possibleAllergen.values().stream()
                    .filter(s -> s.size() == 1)
                    .flatMap(Collection::stream)
                    .toList();
            for (Map.Entry<String, Set<String>> entry : possibleAllergen.entrySet()) {
                if (entry.getValue().size() > 1) {
                    ingredients.forEach(entry.getValue()::remove);
                }
            }
        }

        LOGGER.info("possibleAllergen: {}", possibleAllergen);

        Set<String> allergenicIngredients = possibleAllergen.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
        Set<String> nonAllergenicIngredients = SetUtils.difference(allIngredients, allergenicIngredients);

        LOGGER.info("allergenicIngredients: {}", allergenicIngredients);
        LOGGER.info("nonAllergenicIngredients: {}", nonAllergenicIngredients);

        long occurrence = foodIngredientsList.stream()
                .map(Pair::right)
                .flatMap(Set::stream)
                .filter(nonAllergenicIngredients::contains)
                .count();

        String canonical = possibleAllergen.entrySet().stream().map(p -> Pair.of(p.getKey(), p.getValue().iterator().next()))
                .sorted(Comparator.comparing(Pair::left))
                .map(Pair::right)
                .collect(Collectors.joining(","));

        return Pair.of(occurrence, canonical);
    }
}
