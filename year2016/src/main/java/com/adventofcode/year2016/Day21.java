package com.adventofcode.year2016;

import com.adventofcode.common.maths.Permutations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day21 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21.class);
    private static final Pattern SWAP_POSITION = Pattern.compile("swap position (\\d+) with position (\\d+)");
    private static final Pattern SWAP_LETTER = Pattern.compile("swap letter (\\w+) with letter (\\w+)");
    private static final Pattern ROTATE_LEFT_RIGHT = Pattern.compile("rotate (left|right) (\\d+) step.*");
    private static final Pattern ROTATE_POSITION = Pattern.compile("rotate based on position of letter (\\w+)");
    private static final Pattern REVERSE = Pattern.compile("reverse positions (\\d+) through (\\d+)");
    private static final Pattern MOVE = Pattern.compile("move position (\\d+) to position (\\d+)");

    private Day21() {
        // No-Op
    }

    private static void rotateLeft(List<String> message, int steps) {
        for (int step = 0; step < steps; ++step) {
            message.addLast(message.removeFirst());
        }
    }

    private static void rotateRight(List<String> message, int steps) {
        for (int step = 0; step < steps; ++step) {
            message.addFirst(message.removeLast());
        }
    }

    private static void swap(List<String> message, int position1, int position2) {
        String letter1 = message.get(position1);
        String letter2 = message.get(position2);
        message.set(position1, letter2);
        message.set(position2, letter1);
    }

    private static String scramblingFunction(List<String> operations, String letters) {
        List<String> message = new ArrayList<>();
        for (int i = 0; i < letters.length(); i++) {
            message.add("" + letters.charAt(i));
        }

        LOGGER.trace("Before all operations: {}", message);

        for (String operation : operations) {
            Matcher matcher = SWAP_POSITION.matcher(operation);
            if (matcher.matches()) {
                int position1 = Integer.parseInt(matcher.group(1));
                int position2 = Integer.parseInt(matcher.group(2));
                LOGGER.trace("Swap position {}, {}", position1, position2);
                swap(message, position1, position2);
                LOGGER.trace("After operation: {}", message);
                continue;
            }

            matcher = SWAP_LETTER.matcher(operation);
            if (matcher.matches()) {
                String letter1 = matcher.group(1);
                String letter2 = matcher.group(2);
                LOGGER.trace("Swap position {}, {}", letter1, letter2);
                swap(message, message.indexOf(letter1), message.indexOf(letter2));
                LOGGER.trace("After operation: {}", message);
                continue;
            }

            matcher = ROTATE_LEFT_RIGHT.matcher(operation);
            if (matcher.matches()) {
                String order = matcher.group(1);
                int steps = Integer.parseInt(matcher.group(2));
                LOGGER.trace("Rotate {}, {}", order, steps);
                switch (order) {
                    case "left" -> rotateLeft(message, steps);
                    case "right" -> rotateRight(message, steps);
                    default -> throw new IllegalStateException("Unknown order");
                }
                LOGGER.trace("After operation: {}", message);
                continue;
            }

            matcher = ROTATE_POSITION.matcher(operation);
            if (matcher.matches()) {
                String letter = matcher.group(1);
                LOGGER.trace("Rotate position {}", letter);
                int position = message.indexOf(letter);
                rotateRight(message, 1);
                rotateRight(message, position);
                if (position >= 4) {
                    rotateRight(message, 1);
                }
                LOGGER.trace("After operation: {}", message);
                continue;
            }

            matcher = REVERSE.matcher(operation);
            if (matcher.matches()) {
                int position1 = Integer.parseInt(matcher.group(1));
                int position2 = Integer.parseInt(matcher.group(2));
                LOGGER.trace("Reverse {}, {}", position1, position2);
                Collections.reverse(message.subList(position1, position2 + 1));
                LOGGER.trace("After operation: {}", message);
                continue;
            }

            matcher = MOVE.matcher(operation);
            if (matcher.matches()) {
                int position1 = Integer.parseInt(matcher.group(1));
                int position2 = Integer.parseInt(matcher.group(2));
                LOGGER.trace("Move {}, {}", position1, position2);
                String letter = message.remove(position1);
                message.add(position2, letter);
                LOGGER.trace("After operation: {}", message);
                continue;
            }

            throw new IllegalStateException("Cannot parse line: " + operation);
        }

        LOGGER.trace("Message is {}", message);

        return String.join("", message);
    }

    /**
     * --- Day 21: Scrambled Letters and Hash ---
     * <p>
     * The computer system you're breaking into uses a weird scrambling function
     * to store its passwords. It shouldn't be much trouble to create your own
     * scrambled password so you can add it to the system; you just have to
     * implement the scrambler.
     * <p>
     * The scrambling function is a series of operations (the exact list is
     * provided in your puzzle input). Starting with the password to be scrambled,
     * apply each operation in succession to the string. The individual operations
     * behave as follows:
     * <p>
     * - swap position X with position Y means that the letters at indexes X
     * and Y (counting from 0) should be swapped.
     * - swap letter X with letter Y means that the letters X and Y should be
     * swapped (regardless of where they appear in the string).
     * - rotate left/right X steps means that the whole string should be
     * rotated; for example, one right rotation would turn abcd into dabc.
     * - rotate based on position of letter X means that the whole string
     * should be rotated to the right based on the index of letter X
     * (counting from 0) as determined before this instruction does any
     * rotations. Once the index is determined, rotate the string to the
     * right one time, plus a number of times equal to that index, plus one
     * additional time if the index was at least 4.
     * - reverse positions X through Y means that the span of letters at
     * indexes X through Y (including the letters at X and Y) should be
     * reversed in order.
     * - move position X to position Y means that the letter which is at index
     * X should be removed from the string, then inserted such that it ends
     * up at index Y.
     * <p>
     * For example, suppose you start with abcde and perform the following operations:
     * <p>
     * - swap position 4 with position 0 swaps the first and last letters,
     * producing the input for the next step, ebcda.
     * - swap letter d with letter b swaps the positions of d and b: edcba.
     * - reverse positions 0 through 4 causes the entire string to be reversed,
     * producing abcde.
     * - rotate left 1 step shifts all letters left one position, causing the
     * first letter to wrap to the end of the string: bcdea.
     * - move position 1 to position 4 removes the letter at position 1 (c),
     * then inserts it at position 4 (the end of the string): bdeac.
     * - move position 3 to position 0 removes the letter at position 3 (a),
     * then inserts it at position 0 (the front of the string): abdec.
     * - rotate based on position of letter b finds the index of letter b (1),
     * then rotates the string right once plus a number of times equal to
     * that index (2): ecabd.
     * - rotate based on position of letter d finds the index of letter d (4),
     * then rotates the string right once, plus a number of times equal to
     * that index, plus an additional time because the index was at least 4,
     * for a total of 6 right rotations: decab.
     * <p>
     * After these steps, the resulting scrambled password is decab.
     * <p>
     * Now, you just need to generate a new scrambled password and you can access
     * the system. Given the list of scrambling operations in your puzzle input,
     * what is the result of scrambling abcdefgh?
     * <p>
     * Your puzzle answer was gcedfahb.
     */
    static String scramblingFunction(Scanner scanner, String letters) {
        List<String> operations = readOperations(scanner);

        return scramblingFunction(operations, letters);
    }

    /**
     * --- Part Two ---
     * <p>
     * You scrambled the password correctly, but you discover that you
     * can't actually modify the password file on the system. You'll need to un-
     * scramble one of the existing passwords by reversing the scrambling process.
     * <p>
     * What is the un-scrambled version of the scrambled password fbgdceah?
     */
    static String unscramblingFunction(Scanner scanner, String password) {
        List<String> operations = readOperations(scanner);

        List<String> permutations = Permutations.permutations(password);
        for (String permutation : permutations) {
            String scrambling = scramblingFunction(operations, permutation);
            if (scrambling.equals(password)) {
                return permutation;
            }
        }

        throw new IllegalStateException("Cannot find password");
    }

    private static List<String> readOperations(Scanner scanner) {
        List<String> operations = new ArrayList<>();
        while (scanner.hasNextLine()) {
            operations.add(scanner.nextLine());
        }
        return operations;
    }
}
