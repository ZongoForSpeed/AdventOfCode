package com.adventofcode.year2020;

import com.adventofcode.common.point.map.BooleanMap;
import com.adventofcode.common.point.map.CharMap;
import com.adventofcode.test.AbstractTest;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day20Test extends AbstractTest {
    Day20Test() {
        super(2020, 20);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Day20Test.class);

    @Test
    void testJurassicJigsaw() {
        Scanner scanner = new Scanner(
                """
                        Tile 2311:
                        ..##.#..#.
                        ##..#.....
                        #...##..#.
                        ####.#...#
                        ##.##.###.
                        ##...#.###
                        .#.#.#..##
                        ..#....#..
                        ###...#.#.
                        ..###..###
                        
                        Tile 1951:
                        #.##...##.
                        #.####...#
                        .....#..##
                        #...######
                        .##.#....#
                        .###.#####
                        ###.##.##.
                        .###....#.
                        ..#.#..#.#
                        #...##.#..
                        
                        Tile 1171:
                        ####...##.
                        #..##.#..#
                        ##.#..#.#.
                        .###.####.
                        ..###.####
                        .##....##.
                        .#...####.
                        #.##.####.
                        ####..#...
                        .....##...
                        
                        Tile 1427:
                        ###.##.#..
                        .#..#.##..
                        .#.##.#..#
                        #.#.#.##.#
                        ....#...##
                        ...##..##.
                        ...#.#####
                        .#.####.#.
                        ..#..###.#
                        ..##.#..#.
                        
                        Tile 1489:
                        ##.#.#....
                        ..##...#..
                        .##..##...
                        ..#...#...
                        #####...#.
                        #..#.#.#.#
                        ...#.#.#..
                        ##.#...##.
                        ..##.##.##
                        ###.##.#..
                        
                        Tile 2473:
                        #....####.
                        #..#.##...
                        #.##..#...
                        ######.#.#
                        .#...#.#.#
                        .#########
                        .###.#..#.
                        ########.#
                        ##...##.#.
                        ..###.#.#.
                        
                        Tile 2971:
                        ..#.#....#
                        #...###...
                        #.#.###...
                        ##.##..#..
                        .#####..##
                        .#..####.#
                        #..#.#..#.
                        ..####.###
                        ..#.#.###.
                        ...#.#.#.#
                        
                        Tile 2729:
                        ...#.#.#.#
                        ####.#....
                        ..#.#.....
                        ....#..#.#
                        .##..##.#.
                        .#.####...
                        ####.#.#..
                        ##.####...
                        ##..#.##..
                        #.##...##.
                        
                        Tile 3079:
                        #.#.#####.
                        .#..######
                        ..#.......
                        ######....
                        ####.#..#.
                        .#...#.##.
                        #.#####.##
                        ..#.###...
                        ..#.......
                        ..#.###...
                        """);

        Int2ObjectMap<List<CharMap>> tiles = new Int2ObjectOpenHashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            line = line.replace(":", "");

            tiles.put(Integer.parseInt(Iterables.get(Splitter.on(' ').split(line), 1)), Day20.directions(Day20.readTile(scanner)));
        }
        LOGGER.info("Read tiles: {}", tiles.size());

        int grid = (int) Math.round(Math.sqrt(tiles.size()));
        LOGGER.info("Grid size: {}", grid);

        CharMap fullGrid = new CharMap();
        long product = Day20.computeProduct(tiles, grid, fullGrid);
        assertThat(product).isEqualTo(20899048083289L);

        LOGGER.info("Solution: \n{}", fullGrid);

        CharMap trim = Day20.removeBorder(fullGrid, grid, 10);

        BooleanMap seaMonster = Day20.readSeaMonster();
        long monster = Day20.findSeaMonster(seaMonster, trim);
        assertThat(monster).isEqualTo(273L);
    }

    @Override
    public void partOne(Scanner scanner) {
        // No-Op
    }

    @Override
    public void partTwo(Scanner scanner) {
        Int2ObjectMap<List<CharMap>> tiles = new Int2ObjectOpenHashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            line = line.replace(":", "");

            tiles.put(Integer.parseInt(Iterables.get(Splitter.on(' ').split(line), 1)), Day20.directions(Day20.readTile(scanner)));
        }
        LOGGER.info("Read tiles: {}", tiles.size());

        int grid = (int) Math.round(Math.sqrt(tiles.size()));
        LOGGER.info("Grid size: {}", grid);

        CharMap fullGrid = new CharMap();
        long product = Day20.computeProduct(tiles, grid, fullGrid);
        assertThat(product).isEqualTo(16937516456219L);

        LOGGER.info("Solution: \n{}", fullGrid);

        CharMap trim = Day20.removeBorder(fullGrid, grid, 10);

        BooleanMap seaMonster = Day20.readSeaMonster();
        long monster = Day20.findSeaMonster(seaMonster, trim);
        assertThat(monster).isEqualTo(1858);
    }
}
