package com.adventofcode.year2015;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Day14 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day14.class);
    private static final Pattern PATTERN = Pattern.compile("(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds\\.");

    public static IntList race(Reindeer reindeer, int duration) {
        IntList positions = new IntArrayList(duration + 1);
        int flyWithRest = reindeer.flyDuration() + reindeer.restDuration();
        int cycles = duration / flyWithRest;
        int position = 0;
        positions.add(position);
        for (int cycle = 0; cycle < cycles; ++cycle) {
            final int finalPosition = position;
            IntStream.range(1, reindeer.flyDuration() + 1).map(i -> i * reindeer.speed() + finalPosition).forEach(positions::add);
            position = positions.getInt(positions.size() - 1);
            positions.addAll(Collections.nCopies(reindeer.restDuration(), position));
        }
        int remaining = duration % flyWithRest;
        final int finalPosition = position;
        if (remaining >= reindeer.flyDuration()) {
            IntStream.range(1, reindeer.flyDuration() + 1).map(i -> i * reindeer.speed() + finalPosition).forEach(positions::add);
            position = positions.getInt(positions.size() - 1);
            positions.addAll(Collections.nCopies(remaining - reindeer.flyDuration(), position));
        } else {
            IntStream.range(1, remaining + 1).map(i -> i * reindeer.speed() + finalPosition).forEach(positions::add);
        }

        return positions;
    }

    /**
     * --- Day 14: Reindeer Olympics ---
     *
     * This year is the Reindeer Olympics! Reindeer can fly at high speeds, but
     * must rest occasionally to recover their energy. Santa would like to know
     * which of his reindeer is fastest, and so he has them race.
     *
     * Reindeer can only either be flying (always at their top speed) or resting
     * (not moving at all), and always spend whole seconds in either state.
     *
     * For example, suppose you have the following Reindeer:
     *
     *   - Comet can fly 14 km/s for 10 seconds, but then must rest for 127
     *     seconds.
     *   - Dancer can fly 16 km/s for 11 seconds, but then must rest for 162
     *     seconds.
     *
     * After one second, Comet has gone 14 km, while Dancer has gone 16 km. After
     * ten seconds, Comet has gone 140 km, while Dancer has gone 160 km. On the
     * eleventh second, Comet begins resting (staying at 140 km), and Dancer
     * continues on for a total distance of 176 km. On the 12th second, both
     * reindeer are resting. They continue to rest until the 138th second, when
     * Comet flies for another ten seconds. On the 174th second, Dancer flies for
     * another 11 seconds.
     *
     * In this example, after the 1000th second, both reindeer are resting, and
     * Comet is in the lead at 1120 km (poor Dancer has only gotten 1056 km by
     * that point). So, in this situation, Comet would win (if the race ended at
     * 1000 seconds).
     *
     * Given the descriptions of each reindeer (in your puzzle input), after
     * exactly 2503 seconds, what distance has the winning reindeer traveled?
     *
     * Your puzzle answer was 2696.
     */
    public static long raceReindeerPartOne(Scanner scanner, int duration) {
        List<Reindeer> reindeerList = readReindeerList(scanner);

        long maxDistance = Long.MIN_VALUE;
        for (Reindeer reindeer : reindeerList) {
            int distance = race(reindeer, duration).getInt(duration);
            LOGGER.debug("{} is at {} km", reindeer, distance);
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }
        return maxDistance;
    }

    /**
     * --- Part Two ---
     *
     * Seeing how reindeer move in bursts, Santa decides he's not pleased with the
     * old scoring system.
     *
     * Instead, at the end of each second, he awards one point to the reindeer
     * currently in the lead. (If there are multiple reindeer tied for the lead,
     * they each get one point.) He keeps the traditional 2503 second time limit,
     * of course, as doing otherwise would be entirely ridiculous.
     *
     * Given the example reindeer from above, after the first second, Dancer is in
     * the lead and gets one point. He stays in the lead until several seconds
     * into Comet's second burst: after the 140th second, Comet pulls into the
     * lead and gets his first point. Of course, since Dancer had been in the lead
     * for the 139 seconds before that, he has accumulated 139 points by the 140th
     * second.
     *
     * After the 1000th second, Dancer has accumulated 689 points, while poor
     * Comet, our old champion, only has 312. So, with the new scoring system,
     * Dancer would win (if the race ended at 1000 seconds).
     *
     * Again given the descriptions of each reindeer (in your puzzle input),
     * after exactly 2503 seconds, how many points does the winning reindeer have?
     *
     * Your puzzle answer was 1084.
     */
    public static Map<String, Integer> raceReindeerPartTwo(Scanner scanner, int duration) {
        List<Reindeer> reindeerList = readReindeerList(scanner);

        Map<String, IntList> reindeerPosition = new HashMap<>();
        for (Reindeer reindeer : reindeerList) {
            reindeerPosition.put(reindeer.name(), race(reindeer, duration));
        }

        Map<String, Integer> reindeerPoints = new HashMap<>();
        for (int second = 1; second <= duration; ++second) {
            int finalSecond = second;
            Map<String, Integer> positions = reindeerPosition.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getInt(finalSecond)));

            int max = positions.values().stream().mapToInt(i -> i).max().orElseThrow();
            for (Map.Entry<String, Integer> entry : positions.entrySet()) {
                if (entry.getValue() == max) {
                    reindeerPoints.merge(entry.getKey(), 1, Integer::sum);
                }
            }
        }
        return reindeerPoints;
    }

    public static List<Reindeer> readReindeerList(Scanner scanner) {
        List<Reindeer> reindeerList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                String name = matcher.group(1);
                int speed = Integer.parseInt(matcher.group(2));
                int flyDuration = Integer.parseInt(matcher.group(3));
                int restDuration = Integer.parseInt(matcher.group(4));
                Reindeer reindeer = new Reindeer(name, speed, flyDuration, restDuration);
                LOGGER.info("{}", reindeer);
                reindeerList.add(reindeer);
            }
        }
        return reindeerList;
    }

    public record Reindeer(String name, int speed, int flyDuration, int restDuration) {
    }
}
