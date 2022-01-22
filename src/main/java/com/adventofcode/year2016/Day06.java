package com.adventofcode.year2016;

import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.Char2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

public final class Day06 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day06.class);

    private Day06() {
        // No-Op
    }

    private static Int2ObjectMap<Char2IntMap> getFrequencies(Scanner scanner) {
        Int2ObjectMap<Char2IntMap> frequencies = new Int2ObjectOpenHashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            char[] chars = line.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                frequencies
                        .computeIfAbsent(i, ignore -> new Char2IntOpenHashMap())
                        .mergeInt(chars[i], 1, Integer::sum);
            }
        }

        LOGGER.info("Frequencies: {}", frequencies);
        return frequencies;
    }

    /**
     * --- Day 6: Signals and Noise ---
     *
     * Something is jamming your communications with Santa. Fortunately, your
     * signal is only partially jammed, and protocol in situations like this is to
     * switch to a simple repetition code to get the message through.
     *
     * In this model, the same message is sent repeatedly. You've recorded the
     * repeating message signal (your puzzle input), but the data seems quite
     * corrupted - almost too badly to recover. Almost.
     *
     * All you need to do is figure out which character is most frequent for each
     * position. For example, suppose you had recorded the following messages:
     *
     * eedadn
     * drvtee
     * eandsr
     * raavrd
     * atevrs
     * tsrnev
     * sdttsa
     * rasrtv
     * nssdts
     * ntnada
     * svetve
     * tesnvt
     * vntsnd
     * vrdear
     * dvrsen
     * enarar
     *
     * The most common character in the first column is e; in the second, a; in
     * the third, s, and so on. Combining these characters returns the error-
     * corrected message, easter.
     *
     * Given the recording in your puzzle input, what is the error-corrected
     * version of the message being sent?
     *
     * Your puzzle answer was mlncjgdg.
     */
    static String decodePasswordMostCommon(Scanner scanner) {
        Int2ObjectMap<Char2IntMap> frequencies = getFrequencies(scanner);

        int max = frequencies.keySet().intStream().max().orElseThrow();
        char[] password = new char[max + 1];
        for (int position = 0; position <= max; ++position) {
            Char2IntMap integerMap = frequencies.get(position);
            char character = integerMap
                    .char2IntEntrySet()
                    .stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElseThrow();
            LOGGER.info("character at position {} : {}", position, character);
            password[position] = character;
        }
        return String.valueOf(password);
    }

    /**
     * --- Part Two ---
     *
     * Of course, that would be the message - if you hadn't agreed to use a
     * modified repetition code instead.
     *
     * In this modified code, the sender instead transmits what looks like random
     * data, but for each character, the character they actually want to send is
     * slightly less likely than the others. Even after signal-jamming noise, you
     * can look at the letter distributions in each column and choose the least
     * common letter to reconstruct the original message.
     *
     * In the above example, the least common character in the first column is a;
     * in the second, d, and so on. Repeating this process for the remaining
     * characters produces the original message, advent.
     *
     * Given the recording in your puzzle input and this new decoding methodology,
     * what is the original message that Santa is trying to send?
     *
     * Your puzzle answer was bipjaytb.
     */
    static String decodePasswordLeastCommon(Scanner scanner) {
        Int2ObjectMap<Char2IntMap> frequencies = getFrequencies(scanner);

        int max = frequencies.keySet().intStream().max().orElseThrow();
        char[] password = new char[max + 1];
        for (int position = 0; position <= max; ++position) {
            Char2IntMap integerMap = frequencies.get(position);
            char character = integerMap
                    .char2IntEntrySet()
                    .stream()
                    .min(Comparator.comparingInt(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElseThrow();
            LOGGER.info("character at position {} : {}", position, character);
            password[position] = character;
        }
        return String.valueOf(password);
    }
}
