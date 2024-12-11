package com.adventofcode.year2015;

import it.unimi.dsi.fastutil.Pair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day19 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day19.class);
    private static final Pattern REPLACEMENTS_PATTERN = Pattern.compile("^(.*) => (.*)$");

    private Day19() {
        // No-Op
    }

    public static List<String> readMolecule(String molecule) {
        List<String> chain = new ArrayList<>();
        StringBuilder currentElement = new StringBuilder();
        for (int i = 0; i < molecule.length(); ++i) {
            char aChar = molecule.charAt(i);
            if (Character.isLowerCase(aChar)) {
                currentElement.append(aChar);
            } else if (!currentElement.isEmpty()) {
                chain.add(currentElement.toString());
                currentElement.setLength(0);
                currentElement.append(aChar);
            } else {
                currentElement.append(aChar);
            }
        }
        chain.add(currentElement.toString());
        return chain;
    }

    /**
     * --- Day 19: Medicine for Rudolph ---
     * <p>
     * Rudolph the Red-Nosed Reindeer is sick! His nose isn't shining very
     * brightly, and he needs medicine.
     * <p>
     * Red-Nosed Reindeer biology isn't similar to regular reindeer biology;
     * Rudolph is going to need custom-made medicine. Unfortunately, Red-Nosed
     * Reindeer chemistry isn't similar to regular reindeer chemistry, either.
     * <p>
     * The North Pole is equipped with a Red-Nosed Reindeer nuclear fusion/fission
     * plant, capable of constructing any Red-Nosed Reindeer molecule you need. It
     * works by starting with some input molecule and then doing a series of
     * replacements, one per step, until it has the right molecule.
     * <p>
     * However, the machine has to be calibrated before it can be used.
     * Calibration involves determining the number of molecules that can be
     * generated in one step from a given starting point.
     * <p>
     * For example, imagine a simpler machine that supports only the following
     * replacements:
     * <p>
     * H => HO
     * H => OH
     * O => HH
     * <p>
     * Given the replacements above and starting with HOH, the following molecules
     * could be generated:
     * <p>
     * - HOOH (via H => HO on the first H).
     * - HOHO (via H => HO on the second H).
     * - OHOH (via H => OH on the first H).
     * - HOOH (via H => OH on the second H).
     * - HHHH (via O => HH).
     * <p>
     * So, in the example above, there are 4 distinct molecules (not five, because
     * HOOH appears twice) after one replacement from HOH. Santa's favorite
     * molecule, HOHOHO, can become 7 distinct molecules (over nine replacements:
     * six from H, and three from O).
     * <p>
     * The machine replaces without regard for the surrounding characters. For
     * example, given the string H2O, the transition H => OO would result in OO2O.
     * <p>
     * Your puzzle input describes all of the possible replacements and, at the
     * bottom, the medicine molecule for which you need to calibrate the machine.
     * How many distinct molecules can be created after all the different ways you
     * can do one replacement on the medicine molecule?
     * <p>
     * Your puzzle answer was 535.
     */
    public static Set<List<String>> findReplacements(Scanner scanner) {
        Map<String, List<List<String>>> replacements = new HashMap<>();
        List<String> medicineMolecule = null;
        boolean readMolecule = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (StringUtils.isBlank(line)) {
                readMolecule = true;
                continue;
            }

            if (!readMolecule) {
                Matcher matcher = REPLACEMENTS_PATTERN.matcher(line);
                if (matcher.find()) {
                    replacements.computeIfAbsent(matcher.group(1), ignore -> new ArrayList<>()).add(readMolecule(matcher.group(2)));
                } else {
                    LOGGER.error("Cannot parse line '{}'", line);
                }
            } else {
                medicineMolecule = readMolecule(line);
            }
        }

        Objects.requireNonNull(medicineMolecule);

        LOGGER.info("Replacements: {}", replacements);
        LOGGER.info("medicineMolecule: {}", medicineMolecule);
        return findNextMolecules(replacements, medicineMolecule);
    }

    private static final Pattern PATTERN = Pattern.compile("^(\\w+) => (\\w+)$");

    /**
     * --- Part Two ---
     * <p>
     * Now that the machine is calibrated, you're ready to begin molecule
     * fabrication.
     * <p>
     * Molecule fabrication always begins with just a single electron, e, and
     * applying replacements one at a time, just like the ones during calibration.
     * <p>
     * For example, suppose you have the following replacements:
     * <p>
     * e => H
     * e => O
     * H => HO
     * H => OH
     * O => HH
     * <p>
     * If you'd like to make HOH, you start with e, and then make the following
     * replacements:
     * <p>
     * - e => O to get O
     * - O => HH to get HH
     * - H => OH (on the second H) to get HOH
     * <p>
     * So, you could make HOH after 3 steps. Santa's favorite molecule, HOHOHO,
     * can be made in 6 steps.
     * <p>
     * How long will it take to make the medicine? Given the available
     * replacements and the medicine molecule in your puzzle input, what is the
     * fewest number of steps to go from e to the medicine molecule?
     * <p>
     * Your puzzle answer was 212.
     */
    public static long findMedicine(Scanner scanner) {
        List<Pair<String, String>> rules = new ArrayList<>();
        String medicineMolecule = null;
        boolean readMolecule = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (StringUtils.isBlank(line)) {
                readMolecule = true;
                continue;
            }

            if (!readMolecule) {
                Matcher matcher = PATTERN.matcher(line);
                if (matcher.find()) {
                    rules.add(Pair.of(matcher.group(1), matcher.group(2)));
                } else {
                    throw new IllegalStateException("Cannot parse line: " + line);
                }
            } else {
                medicineMolecule = line;
            }
        }

        Objects.requireNonNull(medicineMolecule);

        Collections.reverse(rules);

        int count = 0;
        while (!"e".equals(medicineMolecule)) {
            for (Pair<String, String> rule : rules) {
                if ("e".equals(rule.left())) {
                    if (rule.right().equals(medicineMolecule)) {
                        medicineMolecule = "e";
                        count++;
                    }
                    continue;
                }
                if (medicineMolecule.contains(rule.right())) {
                    int position = medicineMolecule.lastIndexOf(rule.right());
                    medicineMolecule = medicineMolecule.substring(0, position) + rule.left() + medicineMolecule.substring(position + rule.right().length());
                    count++;
                }
            }
        }
        return count;
    }

    public static Set<List<String>> findNextMolecules(Map<String, List<List<String>>> replacements, List<String> medicineMolecule) {
        Set<List<String>> molecules = new HashSet<>();
        for (int i = 0; i < medicineMolecule.size(); i++) {
            String element = medicineMolecule.get(i);
            List<List<String>> replacement = replacements.get(element);
            if (replacement != null) {
                List<String> prefix = medicineMolecule.subList(0, i);
                List<String> suffix = medicineMolecule.subList(i + 1, medicineMolecule.size());
                for (List<String> r : replacement) {
                    List<String> newMolecule = new ArrayList<>();
                    newMolecule.addAll(prefix);
                    newMolecule.addAll(r);
                    newMolecule.addAll(suffix);
                    molecules.add(newMolecule);
                }
            }
        }
        return molecules;
    }
}
