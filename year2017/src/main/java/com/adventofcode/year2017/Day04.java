package com.adventofcode.year2017;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public final class Day04 {
    private Day04() {
        // No-Op
    }

    static boolean validPassphrasePartOne(String input) {
        Set<String> words = new HashSet<>();
        for (String s : input.split(" ")) {
            if (!words.add(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * --- Day 4: High-Entropy Passphrases ---
     *
     * A new system policy has been put in place that requires all accounts to use
     * a passphrase instead of simply a password. A passphrase consists of a
     * series of words (lowercase letters) separated by spaces.
     *
     * To ensure security, a valid passphrase must contain no duplicate words.
     *
     * For example:
     *
     *   - aa bb cc dd ee is valid.
     *   - aa bb cc dd aa is not valid - the word aa appears more than once.
     *   - aa bb cc dd aaa is valid - aa and aaa count as different words.
     *
     * The system's full passphrase list is available as your puzzle input. How
     * many passphrases are valid?
     *
     * Your puzzle answer was 438.
     */
    static int validPassphrasePartOne(Scanner scanner) {
        int count = 0;
        while (scanner.hasNextLine()) {
            if (validPassphrasePartOne(scanner.nextLine())) {
                count++;
            }
        }
        return count;
    }

    static boolean validPassphrasePartTwo(String input) {
        Set<String> words = new HashSet<>();
        for (String s : input.split(" ")) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            if (!words.add(new String(chars))) {
                return false;
            }
        }
        return true;
    }

    /**
     * --- Part Two ---
     *
     * For added security, yet another system policy has been put in place. Now, a
     * valid passphrase must contain no two words that are anagrams of each other
     * - that is, a passphrase is invalid if any word's letters can be rearranged
     * to form any other word in the passphrase.
     *
     * For example:
     *
     *   - abcde fghij is a valid passphrase.
     *   - abcde xyz ecdab is not valid - the letters from the third word can be rearranged to form the first word.
     *   - a ab abc abd abf abj is a valid passphrase, because all letters need to be used when forming another word.
     *   - iiii oiii ooii oooi oooo is valid.
     *   - oiii ioii iioi iiio is not valid - any of these words can be rearranged to form any other word.
     *
     * Under this new system policy, how many passphrases are valid?
     *
     * Your puzzle answer was 113.
     */
    static int validPassphrasePartTwo(Scanner scanner) {
        int count = 0;
        while (scanner.hasNextLine()) {
            if (validPassphrasePartTwo(scanner.nextLine())) {
                count++;
            }
        }
        return count;
    }
}
