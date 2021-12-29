package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day08Test {
    private static int decode(String line) {
        if (line.startsWith("\"") && line.endsWith("\"")) {
            line = line.substring(1, line.length() - 1);
        }
        PrimitiveIterator.OfInt iterator = line.chars().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            int c = iterator.nextInt();
            if (c == '\\') {
                c = iterator.nextInt();
                if (c == 'x') {
                    iterator.nextInt();
                    iterator.nextInt();
                }
            }
            count++;
        }

        return count;
    }

    private static int encode(String line) {
        int count = 2;
        for (char c : line.toCharArray()) {
            if (c == '\\' || c == '"') {
                count++;
            }
            count++;
        }

        return count;
    }

    private static int memoryDecode(String line) {
        return line.length() - decode(line);
    }

    private static int memoryEncode(String line) {
        return encode(line) - line.length();
    }

    private static int memoryDecode(Scanner scanner) {
        int memoryCharacters = 0;
        while (scanner.hasNextLine()) {
            memoryCharacters += memoryDecode(scanner.nextLine());
        }
        return memoryCharacters;
    }

    private static int memoryEncode(Scanner scanner) {
        int memoryCharacters = 0;
        while (scanner.hasNextLine()) {
            memoryCharacters += memoryEncode(scanner.nextLine());
        }
        return memoryCharacters;
    }

    @Test
    void inputExample() {
        assertThat(decode("\"\"")).isZero();
        assertThat(decode("\"abc\"")).isEqualTo(3);
        assertThat(decode("\"aaa\\\"aaa\"")).isEqualTo(7);
        assertThat(decode("\"\\x27\"")).isEqualTo(1);
        assertThat(memoryDecode("\"\"")).isEqualTo(2);
        assertThat(memoryDecode("\"abc\"")).isEqualTo(2);
        assertThat(memoryDecode("\"aaa\\\"aaa\"")).isEqualTo(3);
        assertThat(memoryDecode("\"\\x27\"")).isEqualTo(5);
        assertThat(encode("\"\"")).isEqualTo(6);
        assertThat(encode("\"abc\"")).isEqualTo(9);
        assertThat(encode("\"aaa\\\"aaa\"")).isEqualTo(16);
        assertThat(encode("\"\\x27\"")).isEqualTo(11);

        String input = """
                ""
                "abc"
                "aaa\\"aaa"
                "\\x27"
                """;

        assertThat(memoryDecode(new Scanner(input))).isEqualTo(12);
        assertThat(memoryEncode(new Scanner(input))).isEqualTo(19);
    }

    /**
     * --- Day 8: Matchsticks ---
     *
     * Space on the sleigh is limited this year, and so Santa will be bringing his
     * list as a digital copy. He needs to know how much space it will take up
     * when stored.
     *
     * It is common in many programming languages to provide a way to escape
     * special characters in strings. For example, C, JavaScript, Perl, Python,
     * and even PHP handle special characters in very similar ways.
     *
     * However, it is important to realize the difference between the number of
     * characters in the code representation of the string literal and the number
     * of characters in the in-memory string itself.
     *
     * For example:
     *
     *   - "" is 2 characters of code (the two double quotes), but the string
     *     contains zero characters.
     *   - "abc" is 5 characters of code, but 3 characters in the string data.
     *   - "aaa\"aaa" is 10 characters of code, but the string itself contains
     *     six "a" characters and a single, escaped quote character, for a total
     *     of 7 characters in the string data.
     *   - "\x27" is 6 characters of code, but the string itself contains just
     *     one - an apostrophe ('), escaped using hexadecimal notation.
     *
     * Santa's list is a file that contains many double-quoted string literals,
     * one on each line. The only escape sequences used are \\ (which represents a
     * single backslash), \" (which represents a lone double-quote character), and
     * \x plus two hexadecimal characters (which represents a single character
     * with that ASCII code).
     *
     * Disregarding the whitespace in the file, what is the number of characters
     * of code for string literals minus the number of characters in memory for
     * the values of the strings in total for the entire file?
     *
     * For example, given the four strings above, the total number of characters
     * of string code (2 + 5 + 10 + 6 = 23) minus the total number of characters
     * in memory for string values (0 + 3 + 7 + 1 = 11) is 23 - 11 = 12.
     *
     * Your puzzle answer was 1371.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day08Test.class.getResourceAsStream("/2015/day/8/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(memoryDecode(scanner)).isEqualTo(1371);
        }
    }

    /**
     * --- Part Two ---
     *
     * Now, let's go the other way. In addition to finding the number of
     * characters of code, you should now encode each code representation as a new
     * string and find the number of characters of the new encoded representation,
     * including the surrounding double quotes.
     *
     * For example:
     *
     *   - "" encodes to "\"\"", an increase from 2 characters to 6.
     *   - "abc" encodes to "\"abc\"", an increase from 5 characters to 9.
     *   - "aaa\"aaa" encodes to "\"aaa\\\"aaa\"", an increase from 10 characters
     *     to 16.
     *   - "\x27" encodes to "\"\\x27\"", an increase from 6 characters to 11.
     *
     * Your task is to find the total number of characters to represent the newly
     * encoded strings minus the number of characters of code in each original
     * string literal. For example, for the strings above, the total encoded
     * length (6 + 9 + 16 + 11 = 42) minus the characters in the original code
     * representation (23, just like in the first part of this puzzle) is
     * 42 - 23 = 19.
     *
     * Your puzzle answer was 2117.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day08Test.class.getResourceAsStream("/2015/day/8/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(memoryEncode(scanner)).isEqualTo(2117);

        }
    }
}
