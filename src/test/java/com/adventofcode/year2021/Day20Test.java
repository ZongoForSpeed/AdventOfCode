package com.adventofcode.year2021;

import com.adventofcode.map.InfiniteCharMap;
import com.adventofcode.map.Point2D;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Day20Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day20Test.class);

    private static final List<Point2D> NEIGHBORS = List.of(
            Point2D.of(-1, -1), Point2D.of(0, -1), Point2D.of(1, -1),
            Point2D.of(-1, 0), Point2D.of(0, 0), Point2D.of(1, 0),
            Point2D.of(-1, 1), Point2D.of(0, 1), Point2D.of(1, 1)
    );

    private static int pixelCode(InfiniteCharMap charMap, Point2D p) {
        int code = 0;
        for (Point2D neighbor : NEIGHBORS) {
            Point2D move = p.move(neighbor);
            Character character = charMap.get(move);
            code *= 2;
            if (character != null && character == '#') {
                code += 1;
            }
        }

        return code;
    }

    private static long enhanceImage(Scanner scanner, int steps) {
        InfiniteCharMap charMap = new InfiniteCharMap();

        String enhancementAlgorithm = null;
        int j = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            LOGGER.info("Line : {}", line);

            if (StringUtils.isBlank(line)) {
                continue;
            }

            if (enhancementAlgorithm == null) {
                enhancementAlgorithm = line;
            } else {
                char[] chars = line.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char c = chars[i];
                    if (c == '#') {
                        charMap.put(Point2D.of(i, j), c);
                    }
                }
                ++j;
            }
        }

        LOGGER.info("CharMap: \n{}", charMap);

        int xMin = charMap.keySet().stream().mapToInt(Point2D::x).min().orElseThrow();
        int xMax = charMap.keySet().stream().mapToInt(Point2D::x).max().orElseThrow();
        int yMin = charMap.keySet().stream().mapToInt(Point2D::y).min().orElseThrow();
        int yMax = charMap.keySet().stream().mapToInt(Point2D::y).max().orElseThrow();

        charMap.put(Point2D.of(xMin - 2 * steps, yMin - 2 * steps), '.');
        charMap.put(Point2D.of(xMax + 2 * steps, yMax + 2 * steps), '.');

        for (int step = 1; step <= steps; ++step) {
            charMap = enhanceImage(charMap, enhancementAlgorithm);
            LOGGER.trace("CharMap after step {}: \n{}", step, charMap);
        }

        LOGGER.debug("CharMap after step {}: \n{}", steps, charMap);


        return charMap.values().stream().filter(c -> '#' == c).count();


    }

    private static InfiniteCharMap enhanceImage(InfiniteCharMap charMap, String enhancementAlgorithm) {
        int xMin = charMap.keySet().stream().mapToInt(Point2D::x).min().orElseThrow() + 1;
        int xMax = charMap.keySet().stream().mapToInt(Point2D::x).max().orElseThrow() - 1;
        int yMin = charMap.keySet().stream().mapToInt(Point2D::y).min().orElseThrow() + 1;
        int yMax = charMap.keySet().stream().mapToInt(Point2D::y).max().orElseThrow() - 1;

        InfiniteCharMap nextCharMap = new InfiniteCharMap();
        for (int x = xMin; x <= xMax; ++x) {
            for (int y = yMin; y <= yMax; ++y) {
                Point2D point = Point2D.of(x, y);
                int code = pixelCode(charMap, point);
                if (enhancementAlgorithm.charAt(code) == '#') {
                    nextCharMap.put(point, '#');
                } else {
                    nextCharMap.put(point, '.');
                }
            }
        }
        return nextCharMap;
    }

    @Test
    void inputExample() {
        String input = """
                ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
                        
                #..#.
                #....
                ##..#
                ..#..
                ..###""";

        assertThat(enhanceImage(new Scanner(input), 2)).isEqualTo(35);
        assertThat(enhanceImage(new Scanner(input), 50)).isEqualTo(3351);
    }

    /**
     * --- Day 20: Trench Map ---
     *
     * With the scanners fully deployed, you turn their attention to mapping the
     * floor of the ocean trench.
     *
     * When you get back the image from the scanners, it seems to just be random
     * noise. Perhaps you can combine an image enhancement algorithm and the input
     * image (your puzzle input) to clean it up a little.
     *
     * For example:
     *
     * ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##
     * #..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###
     * .######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#.
     * .#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#.....
     * .#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#..
     * ...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.....
     * ..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
     *
     * #..#.
     * #....
     * ##..#
     * ..#..
     * ..###
     *
     * The first section is the image enhancement algorithm. It is normally given
     * on a single line, but it has been wrapped to multiple lines in this example
     * for legibility. The second section is the input image, a two-dimensional
     * grid of light pixels (#) and dark pixels (.).
     *
     * The image enhancement algorithm describes how to enhance an image by
     * simultaneously converting all pixels in the input image into an output
     * image. Each pixel of the output image is determined by looking at a 3x3
     * square of pixels centered on the corresponding input image pixel. So, to
     * determine the value of the pixel at (5,10) in the output image, nine pixels
     * from the input image need to be considered: (4,9), (4,10), (4,11), (5,9),
     * (5,10), (5,11), (6,9), (6,10), and (6,11). These nine input pixels are
     * combined into a single binary number that is used as an index in the image
     * enhancement algorithm string.
     *
     * For example, to determine the output pixel that corresponds to the very
     * middle pixel of the input image, the nine pixels marked by [...] would need
     * to be considered:
     *
     * # . . # .
     * #[. . .].
     * #[# . .]#
     * .[. # .].
     * . . # # #
     *
     * Starting from the top-left and reading across each row, these pixels are
     * ..., then #.., then .#.; combining these forms ...#...#.. By turning dark
     * pixels (.) into 0 and light pixels (#) into 1, the binary number 000100010
     * can be formed, which is 34 in decimal.
     *
     * The image enhancement algorithm string is exactly 512 characters long,
     * enough to match every possible 9-bit binary number. The first few
     * characters of the string (numbered starting from zero) are as follows:
     *
     * 0         10        20        30  34    40        50        60        70
     * |         |         |         |   |     |         |         |         |
     * ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##
     *
     * In the middle of this first group of characters, the character at index 34
     * can be found: #. So, the output pixel in the center of the output image
     * should be #, a light pixel.
     *
     * This process can then be repeated to calculate every pixel of the output
     * image.
     *
     * Through advances in imaging technology, the images being operated on here
     * are infinite in size. Every pixel of the infinite output image needs to be
     * calculated exactly based on the relevant pixels of the input image. The
     * small input image you have is only a small region of the actual infinite
     * input image; the rest of the input image consists of dark pixels (.). For
     * the purposes of the example, to save on space, only a portion of the
     * infinite-sized input and output images will be shown.
     *
     * The starting input image, therefore, looks something like this, with more
     * dark pixels (.) extending forever in every direction not shown here:
     *
     * ...............
     * ...............
     * ...............
     * ...............
     * ...............
     * .....#..#......
     * .....#.........
     * .....##..#.....
     * .......#.......
     * .......###.....
     * ...............
     * ...............
     * ...............
     * ...............
     * ...............
     *
     * By applying the image enhancement algorithm to every pixel simultaneously,
     * the following output image can be obtained:
     *
     * ...............
     * ...............
     * ...............
     * ...............
     * .....##.##.....
     * ....#..#.#.....
     * ....##.#..#....
     * ....####..#....
     * .....#..##.....
     * ......##..#....
     * .......#.#.....
     * ...............
     * ...............
     * ...............
     * ...............
     *
     * Through further advances in imaging technology, the above output image can
     * also be used as an input image! This allows it to be enhanced a second
     * time:
     *
     * ...............
     * ...............
     * ...............
     * ..........#....
     * ....#..#.#.....
     * ...#.#...###...
     * ...#...##.#....
     * ...#.....#.#...
     * ....#.#####....
     * .....#.#####...
     * ......##.##....
     * .......###.....
     * ...............
     * ...............
     * ...............
     *
     * Truly incredible - now the small details are really starting to come
     * through. After enhancing the original input image twice, 35 pixels are lit.
     *
     * Start with the original input image and apply the image enhancement
     * algorithm twice, being careful to account for the infinite size of the
     * images. How many pixels are lit in the resulting image?
     *
     * Your puzzle answer was 5819.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day20Test.class.getResourceAsStream("/2021/day/20/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(enhanceImage(scanner, 2)).isEqualTo(5819);
        }
    }

    /**
     * --- Part Two ---
     *
     * You still can't quite make out the details in the image. Maybe you just
     * didn't enhance it enough.
     *
     * If you enhance the starting input image in the above example a total of 50
     * times, 3351 pixels are lit in the final output image.
     *
     * Start again with the original input image and apply the image enhancement
     * algorithm 50 times. How many pixels are lit in the resulting image?
     *
     * Your puzzle answer was 18516.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day20Test.class.getResourceAsStream("/2021/day/20/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(enhanceImage(scanner, 50)).isEqualTo(18516);
        }
    }

}
