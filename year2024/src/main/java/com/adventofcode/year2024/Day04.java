package com.adventofcode.year2024;

import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public final class Day04 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day04.class);

    private static final List<Point2D> DIRECTIONS = List.of(
            Point2D.of(1, 1),   //
            Point2D.of(1, 0),   //
            Point2D.of(1, -1),  //
            Point2D.of(0, 1),   //
            Point2D.of(0, -1),  //
            Point2D.of(-1, 1),  //
            Point2D.of(-1, 0),  //
            Point2D.of(-1, -1)  //
    );
    private static final List<Point2D> DIAGONALS = List.of(
            Point2D.of(1, 1),   //
            Point2D.of(1, -1),  //
            Point2D.of(-1, 1),  //
            Point2D.of(-1, -1)  //
    );
    private static final Set<String> PATTERNS = Set.of(
            "SSMM", //
            "MMSS", //
            "MSMS", //
            "SMSM"  //
    );

    private Day04() {
        // No-Op
    }

    /**
     * --- Day 4: Ceres Search ---
     * <p>
     * "Looks like the Chief's not here. Next!" One of The Historians pulls out a
     * device and pushes the only button on it. After a brief flash, you recognize
     * the interior of the Ceres monitoring station!
     * <p>
     * As the search for the Chief continues, a small Elf who lives on the station
     * tugs on your shirt; she'd like to know if you could help her with her word
     * search (your puzzle input). She only has to find one word: XMAS.
     * <p>
     * This word search allows words to be horizontal, vertical, diagonal, written
     * backwards, or even overlapping other words. It's a little unusual, though,
     * as you don't merely need to find one instance of XMAS - you need to find
     * all of them. Here are a few ways XMAS might appear, where irrelevant
     * characters have been replaced with .:
     * <p>
     * ..X...
     * .SAMX.
     * .A..A.
     * XMAS.S
     * .X....
     * <p>
     * The actual word search will be full of letters instead. For example:
     * <p>
     * MMMSXXMASM
     * MSAMXMSMSA
     * AMXSXMAAMM
     * MSAMASMSMX
     * XMASAMXAMM
     * XXAMMXXAMA
     * SMSMSASXSS
     * SAXAMASAAA
     * MAMMMXMMMM
     * MXMXAXMASX
     * <p>
     * In this word search, XMAS occurs a total of 18 times; here's the same word
     * search again, but where letters not involved in any XMAS have been replaced
     * with .:
     * <p>
     * ....XXMAS.
     * .SAMXMS...
     * ...S..A...
     * ..A.A.MS.X
     * XMASAMX.MM
     * X.....XA.A
     * S.S.S.S.SS
     * .A.A.A.A.A
     * ..M.M.M.MM
     * .X.X.XMASX
     * <p>
     * Take a look at the little Elf's word search. How many times does XMAS
     * appear?
     */
    public static int xmasPartOne(Scanner scanner) {
        CharMap map = CharMap.read(scanner, _ -> true);
        LOGGER.trace("Map: \n{}", map);

        int count = 0;

        for (ObjectCharPair<Point2D> entry : map.entries()) {
            if (entry.rightChar() == 'X') {
                Point2D start = entry.left();
                for (Point2D direction : DIRECTIONS) {
                    Point2D move1 = start.move(direction);
                    Point2D move2 = move1.move(direction);
                    Point2D move3 = move2.move(direction);
                    char c1 = map.get(move1);
                    char c2 = map.get(move2);
                    char c3 = map.get(move3);
                    if (c1 == 'M' && c2 == 'A' && c3 == 'S') {
                        LOGGER.info("{} -> {} : X{}{}{}", start, direction, c1, c2, c3);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * --- Part Two ---
     * <p>
     * The Elf looks quizzically at you. Did you misunderstand the assignment?
     * <p>
     * Looking for the instructions, you flip over the word search to find that
     * this isn't actually an XMAS puzzle; it's an X-MAS puzzle in which you're
     * supposed to find two MAS in the shape of an X. One way to achieve that is like this:
     * <p>
     * M.S
     * .A.
     * M.S
     * <p>
     * Irrelevant characters have again been replaced with . in the above diagram.
     * Within the X, each MAS can be written forwards or backwards.
     * <p>
     * Here's the same example from before, but this time all of the X-MASes have
     * been kept instead:
     * <p>
     * .M.S......
     * ..A..MSMS.
     * .M.S.MAA..
     * ..A.ASMSM.
     * .M.S.M....
     * ..........
     * S.S.S.S.S.
     * .A.A.A.A..
     * M.M.M.M.M.
     * ..........
     * <p>
     * In this example, an X-MAS appears 9 times.
     * <p>
     * Flip the word search from the instructions back over to the word search
     * side and try again. How many times does an X-MAS appear?
     */
    public static int xmasPartTwo(Scanner scanner) {
        CharMap map = CharMap.read(scanner, _ -> true);
        LOGGER.trace("Map: \n{}", map);

        int count = 0;

        for (ObjectCharPair<Point2D> entry : map.entries()) {
            if (entry.rightChar() == 'A') {
                Point2D start = entry.left();
                String pattern = DIAGONALS.stream().map(start::move).map(map::get).map(Object::toString).collect(Collectors.joining());
                if (PATTERNS.contains(pattern)) {
                    LOGGER.info("Found X-MAX in {}", start);
                    count++;
                }
            }
        }
        return count;
    }
}
