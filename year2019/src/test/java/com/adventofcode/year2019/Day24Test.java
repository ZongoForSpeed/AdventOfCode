package com.adventofcode.year2019;

import com.adventofcode.common.utils.FileUtils;
import com.adventofcode.common.utils.IntegerPair;
import com.adventofcode.test.AbstractTest;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class Day24Test extends AbstractTest {
    Day24Test() {
        super(2019, 24);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Day24Test.class);

    @Test
    void testSimpleExample() {
        List<IntList> adjacent = Day24.buildAdjacent(5);
        LOGGER.info("adjacent: {}", adjacent);

        String initialState = """
                ....#
                #..#.
                #..##
                ..#..
                #....""";
        BitSet bugs = Day24.parseLayout(initialState);

        LOGGER.info("Initial state:");
        assertThat(Day24.printLayout(bugs)).isEqualTo("""
                ....#
                #..#.
                #..##
                ..#..
                #....""");
        bugs = Day24.nextState(bugs, adjacent);

        LOGGER.info("After 1 minute:");
        assertThat(Day24.printLayout(bugs)).isEqualTo("""
                #..#.
                ####.
                ###.#
                ##.##
                .##..""");
        bugs = Day24.nextState(bugs, adjacent);

        LOGGER.info("After 2 minute:");
        assertThat(Day24.printLayout(bugs)).isEqualTo("""
                #####
                ....#
                ....#
                ...#.
                #.###""");
        bugs = Day24.nextState(bugs, adjacent);

        LOGGER.info("After 3 minute:");
        assertThat(Day24.printLayout(bugs)).isEqualTo("""
                #....
                ####.
                ...##
                #.##.
                .##.#""");
        bugs = Day24.nextState(bugs, adjacent);

        LOGGER.info("After 4 minute:");
        assertThat(Day24.printLayout(bugs)).isEqualTo("""
                ####.
                ....#
                ##..#
                .....
                ##...""");
    }

    @Test
    void testFindRepeatingLayout() {
        List<IntList> adjacent = Day24.buildAdjacent(5);
        String initialState = """
                ....#
                #..#.
                #..##
                ..#..
                #....""";
        BitSet bugs = Day24.parseLayout(initialState);

        LongSet layouts = new LongOpenHashSet();
        while (layouts.add(bugs.toLongArray()[0])) {
            bugs = Day24.nextState(bugs, adjacent);
        }

        assertThat(Day24.printLayout(bugs)).isEqualTo("""
                .....
                .....
                .....
                #....
                .#...""");
        assertThat(Day24.biodiversityPoints(bugs)).isEqualTo(2129920);
    }

    @Test
    void testBuildDepthAdjacent() {
        List<List<IntegerPair>> adjacent = Day24.buildDepthAdjacent(5);
        assertThat(adjacent.get(18)).hasSize(4).contains(
                IntegerPair.of(13, 0),
                IntegerPair.of(23, 0),
                IntegerPair.of(17, 0),
                IntegerPair.of(19, 0)
        );
        assertThat(adjacent.get(13)).hasSize(8).contains(
                IntegerPair.of(8, 0),
                IntegerPair.of(18, 0),
                IntegerPair.of(4, 1),
                IntegerPair.of(9, 1),
                IntegerPair.of(14, 1),
                IntegerPair.of(19, 1),
                IntegerPair.of(24, 1),
                IntegerPair.of(14, 0)
        );
        assertThat(adjacent.get(3)).hasSize(4).contains(
                IntegerPair.of(7, -1),
                IntegerPair.of(8, 0),
                IntegerPair.of(2, 0),
                IntegerPair.of(4, 0)
        );

        for (int i = 0; i < adjacent.size(); i++) {
            List<IntegerPair> pairs = adjacent.get(i);
            LOGGER.info("{} ==> {}", i + 1, pairs.stream().map(t -> IntegerPair.of(t.left() + 1, t.right())).map(Objects::toString).collect(Collectors.joining(", ")));
        }
    }

    @Test
    void testDepthExample() {
        List<List<IntegerPair>> adjacent = Day24.buildDepthAdjacent(5);
        String layout = """
                ....#
                #..#.
                #.?##
                ..#..
                #....""";

        BitSet bugs = Day24.parseLayout(layout);
        Int2ObjectMap<BitSet> depthBugs = new Int2ObjectOpenHashMap<>();
        depthBugs.put(0, bugs);

        for (int minutes = 0; minutes < 10; minutes++) {
            depthBugs = Day24.nextState(depthBugs, adjacent);
        }

        assertThat(Day24.printLayout(depthBugs)).isEqualTo("""
                Depth -5:
                ..#..
                .#.#.
                ..?.#
                .#.#.
                ..#..
                
                Depth -4:
                ...#.
                ...##
                ..?..
                ...##
                ...#.
                
                Depth -3:
                #.#..
                .#...
                ..?..
                .#...
                #.#..
                
                Depth -2:
                .#.##
                ....#
                ..?.#
                ...##
                .###.
                
                Depth -1:
                #..##
                ...##
                ..?..
                ...#.
                .####
                
                Depth 0:
                .#...
                .#.##
                .#?..
                .....
                .....
                
                Depth 1:
                .##..
                #..##
                ..?.#
                ##.##
                #####
                
                Depth 2:
                ###..
                ##.#.
                #.?..
                .#.##
                #.#..
                
                Depth 3:
                ..###
                .....
                #.?..
                #....
                #...#
                
                Depth 4:
                .###.
                #..#.
                #.?..
                ##.#.
                .....
                
                Depth 5:
                ####.
                #..#.
                #.?#.
                ####.
                .....
                
                """);
        long totalBugs = depthBugs.values().stream().mapToInt(BitSet::cardinality).sum();
        assertThat(totalBugs).isEqualTo(99);
    }

    @Test
    void testInputPartTwo() throws IOException {
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        List<IntList> adjacent = Day24.buildAdjacent(5);

        List<String> lines = FileUtils.readLines(scanner);
        String layout = String.join("\n", lines);

        BitSet bugs = Day24.parseLayout(layout);

        LongSet layouts = new LongOpenHashSet();
        while (layouts.add(bugs.toLongArray()[0])) {
            bugs = Day24.nextState(bugs, adjacent);
        }

        assertThat(Day24.printLayout(bugs)).isEqualTo("""
                ....#
                ....#
                ##..#
                ##..#
                .#..#""");

        assertThat(Day24.biodiversityPoints(bugs)).isEqualTo(19516944);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        List<String> lines = FileUtils.readLines(scanner);
        assertThat(Day24.getTotalBugsPartTwo(lines)).isEqualTo(2006);
    }
}
