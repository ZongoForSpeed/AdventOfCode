package com.adventofcode.year2018;

import com.adventofcode.common.utils.IntegerPair;
import org.apache.commons.lang3.ArrayUtils;
import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day04 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day04.class);
    private static final Pattern PATTERN = Pattern.compile("\\[(.*)] (.*)");
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm[ss][.SSS]");

    private Day04() {
        // No-Op
    }

    /**
     * --- Day 4: Repose Record ---
     *
     * You've sneaked into another supply closet - this time, it's across from the
     * prototype suit manufacturing lab. You need to sneak inside and fix the
     * issues with the suit, but there's a guard stationed outside the lab, so
     * this is as close as you can safely get.
     *
     * As you search the closet for anything that might help, you discover that
     * you're not the first person to want to sneak in. Covering the walls,
     * someone has spent an hour starting every midnight for the past few months
     * secretly observing this guard post! They've been writing down the ID of the
     * one guard on duty that night - the Elves seem to have decided that one
     * guard was enough for the overnight shift - as well as when they fall asleep
     * or wake up while at their post (your puzzle input).
     *
     * For example, consider the following records, which have already been
     * organized into chronological order:
     *
     * [1518-11-01 00:00] Guard #10 begins shift
     * [1518-11-01 00:05] falls asleep
     * [1518-11-01 00:25] wakes up
     * [1518-11-01 00:30] falls asleep
     * [1518-11-01 00:55] wakes up
     * [1518-11-01 23:58] Guard #99 begins shift
     * [1518-11-02 00:40] falls asleep
     * [1518-11-02 00:50] wakes up
     * [1518-11-03 00:05] Guard #10 begins shift
     * [1518-11-03 00:24] falls asleep
     * [1518-11-03 00:29] wakes up
     * [1518-11-04 00:02] Guard #99 begins shift
     * [1518-11-04 00:36] falls asleep
     * [1518-11-04 00:46] wakes up
     * [1518-11-05 00:03] Guard #99 begins shift
     * [1518-11-05 00:45] falls asleep
     * [1518-11-05 00:55] wakes up
     *
     * Timestamps are written using year-month-day hour:minute format. The guard
     * falling asleep or waking up is always the one whose shift most recently
     * started. Because all asleep/awake times are during the midnight hour (00:00
     * - 00:59), only the minute portion (00 - 59) is relevant for those events.
     *
     * Visually, these records show that the guards are asleep at these times:
     *
     * Date   ID   Minute
     *             000000000011111111112222222222333333333344444444445555555555
     *             012345678901234567890123456789012345678901234567890123456789
     * 11-01  #10  .....####################.....#########################.....
     * 11-02  #99  ........................................##########..........
     * 11-03  #10  ........................#####...............................
     * 11-04  #99  ....................................##########..............
     * 11-05  #99  .............................................##########.....
     *
     * The columns are Date, which shows the month-day portion of the relevant
     * day; ID, which shows the guard on duty that day; and Minute, which shows
     * the minutes during which the guard was asleep within the midnight hour.
     * (The Minute column's header shows the minute's ten's digit in the first row
     * and the one's digit in the second row.) Awake is shown as ., and asleep is
     * shown as #.
     *
     * Note that guards count as asleep on the minute they fall asleep, and they
     * count as awake on the minute they wake up. For example, because Guard #10
     * wakes up at 00:25 on 1518-11-01, minute 25 is marked as awake.
     *
     * If you can figure out the guard most likely to be asleep at a specific
     * time, you might be able to trick that guard into working tonight so you can
     * have the best chance of sneaking in. You have two strategies for choosing
     * the best guard/minute combination.
     *
     * Strategy 1: Find the guard that has the most minutes asleep. What minute
     * does that guard spend asleep the most?
     *
     * In the example above, Guard #10 spent the most minutes asleep, a total of
     * 50 minutes (20+25+5), while Guard #99 only slept for a total of 30 minutes
     * (10+10+10). Guard #10 was asleep most during minute 24 (on two days,
     * whereas any other minute the guard was asleep was only seen on one day).
     *
     * While this example listed the entries in chronological order, your entries
     * are in the order you found them. You'll need to organize them before they
     * can be analyzed.
     *
     * What is the ID of the guard you chose multiplied by the minute you chose?
     * (In the above example, the answer would be 10 * 24 = 240.)
     *
     * Your puzzle answer was 94040.
     */
    static int findStrategy1(Scanner scanner) {
        Map<Integer, List<LocalDateTime>> sleepingGuards = readSleepingGuards(scanner);

        Duration maxDuration = Duration.ZERO;
        Integer worstGuard = null;

        LOGGER.info("sleepingGuards={}", sleepingGuards);
        for (Map.Entry<Integer, List<LocalDateTime>> entry : sleepingGuards.entrySet()) {
            List<LocalDateTime> value = entry.getValue();
            Duration duration = Duration.ZERO;
            for (int i = 1; i < value.size(); i += 2) {
                duration = duration.plus(Duration.between(value.get(i - 1), value.get(i)));
            }

            LOGGER.info("Guard {}: slept {}", entry.getKey(), duration);
            if (maxDuration.compareTo(duration) < 0) {
                maxDuration = duration;
                worstGuard = entry.getKey();
            }
        }

        if (worstGuard == null) {
            throw new IllegalStateException("Found null worstGuard");
        }

        LOGGER.info("Worst guard is {}: {}", worstGuard, maxDuration);
        List<LocalDateTime> timeList = sleepingGuards.get(worstGuard);
        int[] minutes = getMinutes(timeList);
        int max = Arrays.stream(minutes).max().orElseThrow();
        int indexOf1 = ArrayUtils.indexOf(minutes, max);
        IntegerPair indexOf = IntegerPair.of(indexOf1, max);
        return indexOf.left() * worstGuard;
    }

    /**
     * --- Part Two ---
     *
     * Strategy 2: Of all guards, which guard is most frequently asleep on the
     * same minute?
     *
     * In the example above, Guard #99 spent minute 45 asleep more than any other
     * guard or minute - three times in total. (In all other cases, any guard
     * spent any minute asleep at most twice.)
     *
     * What is the ID of the guard you chose multiplied by the minute you chose?
     * (In the above example, the answer would be 99 * 45 = 4455.)
     */
    static int findStrategy2(Scanner scanner) {
        Map<Integer, List<LocalDateTime>> sleepingGuards = readSleepingGuards(scanner);

        int maxSleeping = 0;
        int worstMinute = -1;
        Integer worstGuard = null;

        LOGGER.info("sleepingGuards={}", sleepingGuards);
        for (Map.Entry<Integer, List<LocalDateTime>> entry : sleepingGuards.entrySet()) {

            int[] minutes = getMinutes(entry.getValue());
            int max = Arrays.stream(minutes).max().orElseThrow();

            if (max > maxSleeping) {
                worstMinute = ArrayUtils.indexOf(minutes, max);
                worstGuard = entry.getKey();
                maxSleeping = max;
            }
        }

        if (worstGuard == null) {
            throw new IllegalStateException("Found null worstGuard");
        }

        LOGGER.info("Worst guard is {}: {}", worstGuard, worstMinute);
        return worstMinute * worstGuard;
    }

    private static int[] getMinutes(List<LocalDateTime> timeList) {
        int[] minutes = new int[60];
        for (int i = 1; i < timeList.size(); i += 2) {
            LocalDateTime start = timeList.get(i - 1);
            LocalDateTime end = timeList.get(i);

            for (LocalDateTime t = start; t.isBefore(end); t = t.plusMinutes(1)) {
                minutes[t.getMinute()]++;
            }
        }

        LOGGER.info("minutes = {}", minutes);
        return minutes;
    }

    private static Map<Integer, List<LocalDateTime>> readSleepingGuards(Scanner scanner) {
        List<Pair<LocalDateTime, String>> records = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                String date = matcher.group(1);
                String group = matcher.group(2);

                LocalDateTime time = LocalDateTime.parse(date, DATETIME_FORMAT);
                LOGGER.info("Time = {}, group = '{}'", time, group);
                records.add(Pair.of(time, group));
            }
        }

        records.sort(Comparator.comparing(Pair::left));

        LOGGER.info("Records: {}", records);
        Map<Integer, List<LocalDateTime>> sleepingGuards = new HashMap<>();

        int guard = 0;
        for (Pair<LocalDateTime, String> pair : records) {
            LocalDateTime time = pair.left();
            String group = pair.right();
            if (group.startsWith("Guard ")) {
                guard = Integer.parseInt(group.split(" ")[1].substring(1));
            } else if (group.equalsIgnoreCase("falls asleep")) {
                sleepingGuards.computeIfAbsent(guard, ignore -> new ArrayList<>()).add(time);
            } else if (group.equalsIgnoreCase("wakes up")) {
                sleepingGuards.computeIfAbsent(guard, ignore -> new ArrayList<>()).add(time);
            }
        }
        return sleepingGuards;
    }
}
