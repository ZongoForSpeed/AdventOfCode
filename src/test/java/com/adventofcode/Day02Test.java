package com.adventofcode;

import com.adventofcode.utils.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class Day02Test {

    private static Pair<Integer, Integer> divePartOne(List<String> input) {
        int position = 0;
        int depth = 0;

        List<Pair<String, Integer>> pairs = input.stream().map(s -> s.split(" ", 2))
                .map(s -> Pair.of(s[0], Integer.valueOf(s[1])))
                .collect(Collectors.toList());

        for (Pair<String, Integer> pair : pairs) {
            switch (pair.getLeft()) {
                case "forward":
                    position += pair.getRight();
                    break;
                case "down":
                    depth += pair.getRight();
                    break;
                case "up":
                    depth -= pair.getRight();
                    break;
                default:
                    throw new IllegalStateException("Unknown command: " + pair.getLeft());
            }
        }

        return Pair.of(position, depth);
    }

    private static Triple<Integer, Integer, Integer> divePartTwo(List<String> input) {
        int position = 0;
        int depth = 0;
        int aim = 0;

        List<Pair<String, Integer>> pairs = input.stream().map(s -> s.split(" ", 2))
                .map(s -> Pair.of(s[0], Integer.valueOf(s[1])))
                .collect(Collectors.toList());

        for (Pair<String, Integer> pair : pairs) {
            switch (pair.getLeft()) {
                case "forward":
                    position += pair.getRight();
                    depth += aim * pair.getRight();
                    break;
                case "down":
                    aim += pair.getRight();
                    break;
                case "up":
                    aim -= pair.getRight();
                    break;
                default:
                    throw new IllegalStateException("Unknown command: " + pair.getLeft());
            }
        }

        return Triple.of(position, depth, aim);
    }

    @Test
    void inputExample() {
        List<String> strings = List.of("forward 5",
                "down 5",
                "forward 8",
                "up 3",
                "down 8",
                "forward 2");

        Pair<Integer, Integer> p = divePartOne(strings);

        assertThat(p.getLeft()).isEqualTo(15);
        assertThat(p.getRight()).isEqualTo(10);

        Triple<Integer, Integer, Integer> triple = divePartTwo(strings);
        assertThat(triple).isEqualTo(Triple.of(15, 60, 10));

    }

    /**
     * --- Day 2: Dive! ---
     * Now, you need to figure out how to pilot this thing.
     * <p>
     * It seems like the submarine can take a series of commands like forward 1,
     * down 2, or up 3:
     * <p>
     * forward X increases the horizontal position by X units.
     * down X increases the depth by X units.
     * up X decreases the depth by X units.
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
     * forward 5 adds 5 to your horizontal position, a total of 5.
     * down 5 adds 5 to your depth, resulting in a value of 5.
     * forward 8 adds 8 to your horizontal position, a total of 13.
     * up 3 decreases your depth by 3, resulting in a value of 2.
     * down 8 adds 8 to your depth, resulting in a value of 10.
     * forward 2 adds 2 to your horizontal position, a total of 15.
     * <p>
     * After following these instructions, you would have a horizontal position of
     * 15 and a depth of 10. (Multiplying these together produces 150.)
     * <p>
     * Calculate the horizontal position and depth you would have after following
     * the planned course. What do you get if you multiply your final horizontal
     * position by your final depth?
     */
    @Test
    void inputPartOne() throws IOException {
        List<String> input = FileUtils.readLines("/day/2/input");

        Pair<Integer, Integer> p = divePartOne(input);

        assertThat(p.getLeft() * p.getRight()).isEqualTo(1561344);
    }

    /**
     * --- Part Two ---
     * Based on your calculations, the planned course doesn't seem to make any
     * sense. You find the submarine manual and discover that the process is
     * actually slightly more complicated.
     * <p>
     * In addition to horizontal position and depth, you'll also need to track
     * third value, aim, which also starts at 0. The commands also mean something
     * entirely different than you first thought:
     * <p>
     * down X increases your aim by X units.
     * up X decreases your aim by X units.
     * forward X does two things:
     * It increases your horizontal position by X units.
     * It increases your depth by your aim multiplied by X.
     * <p>
     * Again note that since you're on a submarine, down and up do the opposite of
     * what you might expect: "down" means aiming in the positive direction.
     * <p>
     * Now, the above example does something different:
     * <p>
     * forward 5 adds 5 to your horizontal position, a total of 5. Because your aim is 0, your depth does not change.
     * down 5 adds 5 to your aim, resulting in a value of 5.
     * forward 8 adds 8 to your horizontal position, a total of 13. Because your aim is 5, your depth increases by 8*5=40.
     * up 3 decreases your aim by 3, resulting in a value of 2.
     * down 8 adds 8 to your aim, resulting in a value of 10.
     * forward 2 adds 2 to your horizontal position, a total of 15. Because your aim is 10, your depth increases by 2*10=20 to a total of 60.
     * <p>
     * After following these new instructions, you would have a horizontal
     * position of 15 and a depth of 60. (Multiplying these produces 900.)
     * <p>
     * Using this new interpretation of the commands, calculate the horizontal position and depth you would have after following the planned course. What do you get if you multiply your final horizontal position by your final depth?
     */
    @Test
    void inputPartTwo() throws IOException {
        List<String> input = FileUtils.readLines("/day/2/input");

        Triple<Integer, Integer, Integer> triple = divePartTwo(input);
        assertThat(triple).isEqualTo(Triple.of(2033, 909225, 768));
        assertThat(triple.getLeft() * triple.getMiddle()).isEqualTo(1848454425);
    }

}
