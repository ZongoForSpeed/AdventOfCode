package com.adventofcode.year2016;

import com.adventofcode.common.Assembunny;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day12Test extends AbstractTest {
    Day12Test() {
        super(2016, 12);
    }

    @Test
    void inputExample() {
        String input = """
                cpy 41 a
                inc a
                inc a
                dec a
                jnz a 2
                dec a""";

        Scanner scanner = new Scanner(input);
        Map<String, Integer> register = Assembunny.run(scanner);
        assertThat(register).hasSize(1).containsEntry("a", 42);
    }

    /**
     * --- Day 12: Leonardo's Monorail ---
     * <p>
     * You finally reach the top floor of this building: a garden with a slanted
     * glass ceiling. Looks like there are no more stars to be had.
     * <p>
     * While sitting on a nearby bench amidst some tiger lilies, you manage to
     * decrypt some of the files you extracted from the servers downstairs.
     * <p>
     * According to these documents, Easter Bunny HQ isn't just this building -
     * it's a collection of buildings in the nearby area. They're all connected by
     * a local monorail, and there's another building not far from here!
     * Unfortunately, being night, the monorail is currently not operating.
     * <p>
     * You remotely connect to the monorail control systems and discover that the
     * boot sequence expects a password. The password-checking logic (your puzzle
     * input) is easy to extract, but the code it uses is strange: it's assembunny
     * code designed for the new computer you just assembled. You'll have to
     * execute the code and get the password.
     * <p>
     * The assembunny code you've extracted operates on four registers (a, b, c,
     * and d) that start at 0 and can hold any integer. However, it seems to make
     * use of only a few instructions:
     * <p>
     * - cpy x y copies x (either an integer or the value of a register) into
     * register y.
     * - inc x increases the value of register x by one.
     * - dec x decreases the value of register x by one.
     * - jnz x y jumps to an instruction y away (positive means forward;
     * negative means backward), but only if x is not zero.
     * <p>
     * The jnz instruction moves relative to itself: an offset of -1 would
     * continue at the previous instruction, while an offset of 2 would skip over
     * the next instruction.
     * <p>
     * For example:
     * <p>
     * cpy 41 a
     * inc a
     * inc a
     * dec a
     * jnz a 2
     * dec a
     * <p>
     * The above code would set register a to 41, increase its value by 2,
     * decrease its value by 1, and then skip the last dec a (because a is not
     * zero, so the jnz a 2 skips it), leaving register a at 42. When you move
     * past the last instruction, the program halts.
     * <p>
     * After executing the assembunny code in your puzzle input, what value is
     * left in register a?
     * <p>
     * Your puzzle answer was 318117.
     */
    @Override
    public void partOne(Scanner scanner) {
        assertThat(Assembunny.run(scanner)).containsEntry("a", 318117);
    }

    /**
     * --- Part Two ---
     * <p>
     * As you head down the fire escape to the monorail, you notice it didn't
     * start; register c needs to be initialized to the position of the ignition
     * key.
     * <p>
     * If you instead initialize register c to be 1, what value is now left in
     * register a?
     * <p>
     * Your puzzle answer was 9227771.
     */
    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Assembunny.run(scanner, Map.of("c", 1))).containsEntry("a", 9227771);
    }
}
