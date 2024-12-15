package com.adventofcode.year2021;

import com.adventofcode.common.utils.IntegerPair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day02 {
    private Day02() {
        // No-Op
    }

    private static final Pattern PATTERN = Pattern.compile("(\\w+) (-?\\d+)");

    /**
     * --- Day 2: Dive! ---
     * Now, you need to figure out how to pilot this thing.
     * <p>
     * It seems like the submarine can take a series of commands like forward 1,
     * down 2, or up 3:
     * <p>
     * - forward X increases the horizontal position by X units.
     * - down X increases the depth by X units.
     * - up X decreases the depth by X units.
     * <p>
     * Note that since you're on a submarine, down and up affect your depth, and
     * so they have the opposite result of what you might expect.
     * <p>
     * The submarine seems to already have a planned course (your puzzle input).
     * You should probably figure out where it's going. For example:
     * <p>
     * forward 5
     * down 5
     * forward 8
     * up 3
     * down 8
     * forward 2
     * <p>
     * Your horizontal position and depth both start at 0. The steps above would
     * then modify them as follows:
     * <p>
     * - forward 5 adds 5 to your horizontal position, a total of 5.
     * - down 5 adds 5 to your depth, resulting in a value of 5.
     * - forward 8 adds 8 to your horizontal position, a total of 13.
     * - up 3 decreases your depth by 3, resulting in a value of 2.
     * - down 8 adds 8 to your depth, resulting in a value of 10.
     * - forward 2 adds 2 to your horizontal position, a total of 15.
     * <p>
     * After following these instructions, you would have a horizontal position of
     * 15 and a depth of 10. (Multiplying these together produces 150.)
     * <p>
     * Calculate the horizontal position and depth you would have after following
     * the planned course. What do you get if you multiply your final horizontal
     * position by your final depth?
     * <p>
     * Your puzzle answer was 1561344.
     */
    public static IntegerPair divePartOne(Scanner scanner) {
        int position = 0;
        int depth = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                String command = matcher.group(1);
                int n = Integer.parseInt(matcher.group(2));
                switch (command) {
                    case "forward" -> position += n;
                    case "down" -> depth += n;
                    case "up" -> depth -= n;
                    default -> throw new IllegalStateException("Unknown command: " + command);
                }
            } else {
                throw new IllegalStateException("Cannot parse line: " + line);
            }
        }

        return IntegerPair.of(position, depth);
    }

    /**
     * --- Part Two ---
     * <p>
     * Based on your calculations, the planned course doesn't seem to make any
     * sense. You find the submarine manual and discover that the process is
     * actually slightly more complicated.
     * <p>
     * In addition to horizontal position and depth, you'll also need to track
     * third value, aim, which also starts at 0. The commands also mean something
     * entirely different than you first thought:
     * <p>
     * - down X increases your aim by X units.
     * - up X decreases your aim by X units.
     * - forward X does two things:
     * - It increases your horizontal position by X units.
     * - It increases your depth by your aim multiplied by X.
     * <p>
     * Again note that since you're on a submarine, down and up do the opposite of
     * what you might expect: "down" means aiming in the positive direction.
     * <p>
     * Now, the above example does something different:
     * <p>
     * - forward 5 adds 5 to your horizontal position, a total of 5. Because
     * your aim is 0, your depth does not change.
     * - down 5 adds 5 to your aim, resulting in a value of 5.
     * - forward 8 adds 8 to your horizontal position, a total of 13. Because
     * your aim is 5, your depth increases by 8*5=40.
     * - up 3 decreases your aim by 3, resulting in a value of 2.
     * - down 8 adds 8 to your aim, resulting in a value of 10.
     * - forward 2 adds 2 to your horizontal position, a total of 15. Because
     * your aim is 10, your depth increases by 2*10=20 to a total of 60.
     * <p>
     * After following these new instructions, you would have a horizontal
     * position of 15 and a depth of 60. (Multiplying these produces 900.)
     * <p>
     * Using this new interpretation of the commands, calculate the horizontal
     * position and depth you would have after following the planned course. What
     * do you get if you multiply your final horizontal position by your final
     * depth?
     * <p>
     * Your puzzle answer was 1848454425.
     */
    public static Triple<Integer, Integer, Integer> divePartTwo(Scanner scanner) {
        int position = 0;
        int depth = 0;
        int aim = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                String command = matcher.group(1);
                int n = Integer.parseInt(matcher.group(2));
                switch (command) {
                    case "forward" -> {
                        position += n;
                        depth += aim * n;
                    }
                    case "down" -> aim += n;
                    case "up" -> aim -= n;
                    default -> throw new IllegalStateException("Unknown command: " + command);
                }
            } else {
                throw new IllegalStateException("Cannot parse line: " + line);
            }
        }

        return Triple.of(position, depth, aim);
    }
}
