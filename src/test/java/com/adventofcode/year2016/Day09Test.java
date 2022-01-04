package com.adventofcode.year2016;

import com.adventofcode.utils.IntegerPair;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day09Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day09Test.class);

    private static IntegerPair readCompression(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int read;
        while ((read = reader.read()) != ')') {
            sb.append((char) read);
        }
        String compression = sb.toString();
        int[] array = Arrays.stream(compression.split("x")).mapToInt(Integer::parseInt).toArray();
        LOGGER.debug("Compression: {}", array);
        return IntegerPair.of(array[0], array[1]);
    }

    private static String readChar(Reader reader, int size) throws IOException {
        char[] buf = new char[size];
        reader.read(buf);
        return new String(buf);
    }

    private static String decompressV1(String input) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (StringReader reader = new StringReader(input)) {
            mainloop:
            while (true) {
                int read = reader.read();
                switch (read) {
                    case -1:
                        break mainloop;
                    case '(':
                        IntegerPair pair = readCompression(reader);
                        String toCopy = readChar(reader, pair.left());
                        stringBuilder.append(toCopy.repeat(pair.right()));
                        break;
                    default:
                        stringBuilder.append((char) read);
                        break;
                }
            }
        }
        LOGGER.trace("Result: {}", stringBuilder);
        return stringBuilder.toString();
    }


    private static long decompressV2(String input) throws IOException {
        long result = 0;
        try (StringReader reader = new StringReader(input)) {
            mainloop:
            while (true) {
                int read = reader.read();
                switch (read) {
                    case -1:
                        break mainloop;
                    case '(':
                        IntegerPair pair = readCompression(reader);
                        result += pair.right() * decompressV2(readChar(reader, pair.left()));
                        break;
                    default:
                        result++;
                        break;
                }
            }
        }
        return result;
    }


    @Test
    void inputExample() throws IOException {
        assertThat(decompressV1("ADVENT")).isEqualTo("ADVENT");
        assertThat(decompressV1("A(1x5)BC")).isEqualTo("ABBBBBC");
        assertThat(decompressV1("(3x3)XYZ")).isEqualTo("XYZXYZXYZ");
        assertThat(decompressV1("A(2x2)BCD(2x2)EFG")).isEqualTo("ABCBCDEFEFG");
        assertThat(decompressV1("(6x1)(1x3)A")).isEqualTo("(1x3)A");
        assertThat(decompressV1("X(8x2)(3x3)ABCY")).isEqualTo("X(3x3)ABC(3x3)ABCY");

        assertThat(decompressV2("(3x3)XYZ")).isEqualTo(9);
        assertThat(decompressV2("X(8x2)(3x3)ABCY")).isEqualTo(20);
        assertThat(decompressV2("(27x12)(20x12)(13x14)(7x10)(1x12)A")).isEqualTo(241920);
        assertThat(decompressV2("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")).isEqualTo(445);
    }

    /**
     * --- Day 9: Explosives in Cyberspace ---
     *
     * Wandering around a secure area, you come across a datalink port to a new
     * part of the network. After briefly scanning it for interesting files, you
     * find one file in particular that catches your attention. It's compressed
     * with an experimental format, but fortunately, the documentation for the
     * format is nearby.
     *
     * The format compresses a sequence of characters. Whitespace is ignored. To
     * indicate that some sequence should be repeated, a marker is added to the
     * file, like (10x2). To decompress this marker, take the subsequent 10
     * characters and repeat them 2 times. Then, continue reading the file after
     * the repeated data. The marker itself is not included in the decompressed
     * output.
     *
     * If parentheses or other characters appear within the data referenced by a
     * marker, that's okay - treat it like normal data, not a marker, and then
     * resume looking for markers after the decompressed section.
     *
     * For example:
     *
     *   - ADVENT contains no markers and decompresses to itself with no changes,
     *     resulting in a decompressed length of 6.
     *   - A(1x5)BC repeats only the B a total of 5 times, becoming ABBBBBC for a
     *     decompressed length of 7.
     *   - (3x3)XYZ becomes XYZXYZXYZ for a decompressed length of 9.
     *   - A(2x2)BCD(2x2)EFG doubles the BC and EF, becoming ABCBCDEFEFG for a
     *     decompressed length of 11.
     *   - (6x1)(1x3)A simply becomes (1x3)A - the (1x3) looks like a marker, but
     *     because it's within a data section of another marker, it is not
     *     treated any differently from the A that comes after it. It has a
     *     decompressed length of 6.
     *   - X(8x2)(3x3)ABCY becomes X(3x3)ABC(3x3)ABCY (for a decompressed length
     *     of 18), because the decompressed data from the (8x2) marker (the
     *     (3x3)ABC) is skipped and not processed further.
     *
     * What is the decompressed length of the file (your puzzle input)? Don't
     * count whitespace.
     *
     * Your puzzle answer was 150914.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day09Test.class.getResourceAsStream("/2016/day/9/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(decompressV1(scanner.nextLine())).hasSize(150914);
        }
    }

    /**
     * --- Part Two ---
     *
     * Apparently, the file actually uses version two of the format.
     *
     * In version two, the only difference is that markers within decompressed
     * data are decompressed. This, the documentation explains, provides much more
     * substantial compression capabilities, allowing many-gigabyte files to be
     * stored in only a few kilobytes.
     *
     * For example:
     *
     *   - (3x3)XYZ still becomes XYZXYZXYZ, as the decompressed section contains
     *     no markers.
     *   - X(8x2)(3x3)ABCY becomes XABCABCABCABCABCABCY, because the decompressed
     *     data from the (8x2) marker is then further decompressed, thus
     *     triggering the (3x3) marker twice for a total of six ABC sequences.
     *   - (27x12)(20x12)(13x14)(7x10)(1x12)A decompresses into a string of A
     *     repeated 241920 times.
     *   - (25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN becomes 445
     *     characters long.
     *
     * Unfortunately, the computer you brought probably doesn't have enough memory
     * to actually decompress the file; you'll have to come up with another way to
     * get its decompressed length.
     *
     * Your puzzle answer was 11052855125.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day09Test.class.getResourceAsStream("/2016/day/9/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(decompressV2(scanner.nextLine())).isEqualTo(11052855125L);
        }
    }
}
