package com.adventofcode.year2020;

import com.adventofcode.common.point.Point2D;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Day24Test extends AbstractTest {
    Day24Test() {
        super(2020, 24);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Day24Test.class);

    @Test
    void hexGrid() {
        LOGGER.info("Move esenee: {}", Day24.move("esenee"));

        Point2D nwwswee = Day24.move("nwwswee");
        LOGGER.info("Move nwwswee: {}", nwwswee);
        assertThat(nwwswee).isEqualTo(Point2D.of(0, 0));
    }

    @Test
    void largerExample() {
        String input = """
                sesenwnenenewseeswwswswwnenewsewsw
                neeenesenwnwwswnenewnwwsewnenwseswesw
                seswneswswsenwwnwse
                nwnwneseeswswnenewneswwnewseswneseene
                swweswneswnenwsewnwneneseenw
                eesenwseswswnenwswnwnwsewwnwsene
                sewnenenenesenwsewnenwwwse
                wenwwweseeeweswwwnwwe
                wsweesenenewnwwnwsenewsenwwsesesenwne
                neeswseenwwswnwswswnw
                nenwswwsewswnenenewsenwsenwnesesenew
                enewnwewneswsewnwswenweswnenwsenwsw
                sweneswneswneneenwnewenewwneswswnese
                swwesenesewenwneswnwwneseswwne
                enesenwswwswneneswsenwnewswseenwsese
                wnwnesenesenenwwnenwsewesewsesesew
                nenewswnwewswnenesenwnesewesw
                eneswnwswnwsenenwnwnwwseeswneewsenese
                neswnwewnwnwseenwseesewsenwsweewe
                wseweeenwnesenwwwswnew""";

        Map<Point2D, Boolean> grid = Day24.hexGrid(new Scanner(input));
        long count = grid.values().stream().filter(v -> v).count();
        assertThat(count).isEqualTo(10);

        Set<Point2D> blackTiles = Day24.runLobbyLayout(grid, 100);
        assertThat(blackTiles).hasSize(2208);
    }

    @Override
    public void partOne(Scanner scanner) {

        Map<Point2D, Boolean> grid = Day24.hexGrid(scanner);
        long count = grid.values().stream().filter(v -> v).count();
        assertThat(count).isEqualTo(244);
    }

    @Override
    public void partTwo(Scanner scanner) {
        Map<Point2D, Boolean> grid = Day24.hexGrid(scanner);

        Set<Point2D> blackTiles = Day24.runLobbyLayout(grid, 100);
        assertThat(blackTiles).hasSize(3665);
    }
}
