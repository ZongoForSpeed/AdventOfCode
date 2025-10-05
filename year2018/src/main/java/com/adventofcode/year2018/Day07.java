package com.adventofcode.year2018;

import org.apache.commons.collections4.SetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day07 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07.class);
    private static final Pattern PATTERN = Pattern.compile("Step (\\w+) must be finished before step (\\w+) can begin\\.");

    private Day07() {
        // No-Op
    }

    private static Map<String, List<String>> readSteps(Scanner scanner) {
        Map<String, List<String>> steps = new HashMap<>();

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(s);
            if (matcher.matches()) {
                steps.computeIfAbsent(matcher.group(1), _ -> new ArrayList<>()).add(matcher.group(2));
            }
        }

        LOGGER.info("steps: {}", steps);
        return steps;
    }

    /**
     * --- Day 7: The Sum of Its Parts ---
     *
     * You find yourself standing on a snow-covered coastline; apparently, you
     * landed a little off course. The region is too hilly to see the North Pole
     * from here, but you do spot some Elves that seem to be trying to unpack
     * something that washed ashore. It's quite cold out, so you decide to risk
     * creating a paradox by asking them for directions.
     *
     * "Oh, are you the search party?" Somehow, you can understand whatever Elves
     * from the year 1018 speak; you assume it's Ancient Nordic Elvish. Could the
     * device on your wrist also be a translator? "Those clothes don't look very
     * warm; take this." They hand you a heavy coat.
     *
     * "We do need to find our way back to the North Pole, but we have higher
     * priorities at the moment. You see, believe it or not, this box contains
     * something that will solve all of Santa's transportation problems - at
     * least, that's what it looks like from the pictures in the instructions."
     * It doesn't seem like they can read whatever language it's in, but you can:
     * "Sleigh kit. Some assembly required."
     *
     * "'Sleigh'? What a wonderful name! You must help us assemble this 'sleigh'
     * at once!" They start excitedly pulling more parts out of the box.
     *
     * The instructions specify a series of steps and requirements about which
     * steps must be finished before others can begin (your puzzle input). Each
     * step is designated by a single letter. For example, suppose you have the
     * following instructions:
     *
     * Step C must be finished before step A can begin.
     * Step C must be finished before step F can begin.
     * Step A must be finished before step B can begin.
     * Step A must be finished before step D can begin.
     * Step B must be finished before step E can begin.
     * Step D must be finished before step E can begin.
     * Step F must be finished before step E can begin.
     *
     * Visually, these requirements look like this:
     *
     *   -->A--->B--
     *  /    \      \
     * C      -->D----->E
     *  \           /
     *   ---->F-----
     *
     * Your first goal is to determine the order in which the steps should be
     * completed. If more than one step is ready, choose the step which is first
     * alphabetically. In this example, the steps would be completed as follows:
     *
     *   - Only C is available, and so it is done first.
     *   - Next, both A and F are available. A is first alphabetically, so it is
     *     done next.
     *   - Then, even though F was available earlier, steps B and D are now also
     *     available, and B is the first alphabetically of the three.
     *   - After that, only D and F are available. E is not available because
     *     only some of its prerequisites are complete. Therefore, D is completed
     *     next.
     *   - F is the only choice, so it is done next.
     *   - Finally, E is completed.
     *
     * So, in this example, the correct order is CABDFE.
     *
     * In what order should the steps in your instructions be completed?
     *
     * Your puzzle answer was GNJOCHKSWTFMXLYDZABIREPVUQ.
     */
    static String getCorrectOrder(Scanner scanner) {
        Map<String, List<String>> steps = readSteps(scanner);

        List<String> order = new ArrayList<>();
        Set<String> allSteps = new HashSet<>();

        do {
            Set<String> blockedSteps = new HashSet<>();
            allSteps.addAll(steps.keySet());
            steps.values().stream().flatMap(Collection::stream).forEach(blockedSteps::add);
            allSteps.addAll(blockedSteps);

            LOGGER.debug("blockedSteps = {}", blockedSteps);

            LOGGER.debug("allSteps = {}", allSteps);

            Set<String> difference = new HashSet<>(SetUtils.difference(allSteps, blockedSteps));
            Optional<String> first = difference.stream().sorted().findFirst();
            if (first.isEmpty()) {
                break;
            }
            String s = first.get();
            order.add(s);
            steps.remove(s);
            allSteps.remove(s);
        } while (!allSteps.isEmpty());

        LOGGER.info("Order: {}", order);
        return String.join("", order);
    }

    /**
     * --- Part Two ---
     *
     * As you're about to begin construction, four of the Elves offer to help.
     * "The sun will set soon; it'll go faster if we work together." Now, you need
     * to account for multiple people working on steps simultaneously. If multiple
     * steps are available, workers should still begin them in alphabetical order.
     *
     * Each step takes 60 seconds plus an amount corresponding to its letter: A=1,
     * B=2, C=3, and so on. So, step A takes 60+1=61 seconds, while step Z takes
     * 60+26=86 seconds. No time is required between steps.
     *
     * To simplify things for the example, however, suppose you only have help
     * from one Elf (a total of two workers) and that each step takes 60 fewer
     * seconds (so that step A takes 1 second and step Z takes 26 seconds). Then,
     * using the same instructions as above, this is how each second would be
     * spent:
     *
     * Second   Worker 1   Worker 2   Done
     *    0        C          .
     *    1        C          .
     *    2        C          .
     *    3        A          F       C
     *    4        B          F       CA
     *    5        B          F       CA
     *    6        D          F       CAB
     *    7        D          F       CAB
     *    8        D          F       CAB
     *    9        D          .       CABF
     *   10        E          .       CABFD
     *   11        E          .       CABFD
     *   12        E          .       CABFD
     *   13        E          .       CABFD
     *   14        E          .       CABFD
     *   15        .          .       CABFDE
     *
     * Each row represents one second of time. The Second column identifies how
     * many seconds have passed as of the beginning of that second. Each worker
     * column shows the step that worker is currently doing (or . if they are
     * idle). The Done column shows completed steps.
     *
     * Note that the order of the steps has changed; this is because steps now
     * take time to finish and multiple workers can begin multiple steps
     * simultaneously.
     *
     * In this example, it would take 15 seconds for two workers to complete these
     * steps.
     *
     * With 5 workers and the 60+ second step durations described above, how long
     * will it take to complete all of the steps?
     */
    static int getCompletionTime(Scanner scanner, long workers, int timeShift) {
        Map<String, List<String>> steps = readSteps(scanner);

        Map<String, Integer> times = new HashMap<>();
        {
            Set<String> allSteps = new HashSet<>(steps.keySet());
            steps.values().stream().flatMap(Collection::stream).forEach(allSteps::add);
            for (String step : allSteps) {
                times.put(step, step.charAt(0) - 'A' + 1 + timeShift);
            }
        }

        LOGGER.info("times: {}", times);

        Set<String> allSteps = new HashSet<>();
        Set<String> workingElf = new HashSet<>();

        int second = 0;

        do {
            if (workingElf.size() < workers) {
                Set<String> blockedSteps = new HashSet<>();
                allSteps.addAll(steps.keySet());
                steps.values().stream().flatMap(Collection::stream).forEach(blockedSteps::add);
                allSteps.addAll(blockedSteps);
                allSteps.removeAll(workingElf);

                Set<String> difference = new HashSet<>(SetUtils.difference(allSteps, blockedSteps));
                List<String> availableSteps = difference.stream().sorted().limit(workers - workingElf.size()).toList();
                workingElf.addAll(availableSteps);
            }

            LOGGER.info("Second {}: {}", second, workingElf);

            for (String step : Set.copyOf(workingElf)) {
                Integer newValue = times.compute(step, (_, value) -> value == null ? 0 : value - 1);
                if (newValue == 0) {
                    workingElf.remove(step);
                    steps.remove(step);
                    allSteps.remove(step);
                }
            }
            ++second;

        } while (!allSteps.isEmpty() || !workingElf.isEmpty());

        return second;
    }
}
