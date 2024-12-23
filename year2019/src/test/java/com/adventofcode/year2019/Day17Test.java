package com.adventofcode.year2019;

import com.adventofcode.common.Intcode;
import com.adventofcode.test.AbstractTest;
import it.unimi.dsi.fastutil.Pair;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test extends AbstractTest {
    Day17Test() {
        super(2019, 17);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Day17Test.class);

    @Test
    void testExample() {
        String example = """
                ..#..........
                ..#..........
                #######...###
                #.#...#...#.#
                #############
                ..#...#...#..
                ..#####...^..""";
        int sum = Day17.computeSumAlignmentParameters(example);
        assertThat(sum).isEqualTo(76);
    }

    @Test
    void testPath() {
        String input = """
                #######...#####
                #.....#...#...#
                #.....#...#...#
                ......#...#...#
                ......#...###.#
                ......#.....#.#
                ^########...#.#
                ......#.#...#.#
                ......#########
                ........#...#..
                ....#########..
                ....#...#......
                ....#...#......
                ....#...#......
                ....#####......""";

        List<Pair<Character, Integer>> commands = Day17.findPath(input);

        String collect = commands.stream().map(p -> p.left() + "," + p.right()).collect(Collectors.joining(","));
        assertThat(collect).isEqualTo("R,8,R,8,R,4,R,4,R,8,L,6,L,2,R,4,R,4,R,8,R,8,R,8,L,6,L,2");
    }

    @Override
    public void partOne(Scanner scanner) {
        String line = scanner.nextLine();
        StringBuilder sb = new StringBuilder();
        Intcode.intcode(line, () -> 0, i -> sb.append((char) i));

        String map = sb.toString();
        LOGGER.debug("Map:\n{}", map);

        assertThat(Day17.computeSumAlignmentParameters(map)).isEqualTo(7280);
    }

    @Override
    public void partTwo(Scanner scanner) {
        String line = scanner.nextLine();
        StringBuilder sb = new StringBuilder();
        Intcode.intcode(line, () -> 0, (i) -> sb.append((char) i));
        LOGGER.debug("{}", sb);
        List<Pair<Character, Integer>> commands = Day17.findPath(sb.toString());

        LOGGER.info("{}", commands);

        String collect = commands.stream().map(p -> p.left() + "," + p.right()).collect(Collectors.joining(","));
        assertThat(collect).isEqualTo("R,10,L,8,R,10,R,4,L,6,L,6,R,10,R,10,L,8,R,10,R,4,L,6,R,12,R,12,R,10,L,6,L,6,R,10,L,6,R,12,R,12,R,10,R,10,L,8,R,10,R,4,L,6,L,6,R,10,R,10,L,8,R,10,R,4,L,6,R,12,R,12,R,10");
        LOGGER.info("{}", collect);

        // (R,10), (L,8), (R,10), (R,4) ==> A
        // (L,6), (L,6), (R,10) ==> B
        // (L,6), (R,12), (R,12), (R,10) ==> C
        //[A, B, A, C, B, C, A, B, A, C]

        String inputCommand = """
                A,B,A,C,B,C,A,B,A,C
                R,10,L,8,R,10,R,4
                L,6,L,6,R,10
                L,6,R,12,R,12,R,10
                n
                """;

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        inputCommand.chars().mapToLong(t -> t).forEach(input::add);

        StringBuilder out = new StringBuilder();
        AtomicLong dust = new AtomicLong();

        Intcode.intcode("2" + line.substring(1), Intcode.take(input), (long o) -> {
            if (o < 255) {
                out.append((char) o);
            } else {
                dust.set(o);
            }
        });

        LOGGER.info("Out: {}", out);
        assertThat(dust).hasValue(1045393);
    }
}
