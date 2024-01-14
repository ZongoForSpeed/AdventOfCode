package com.adventofcode.year2020;

import com.adventofcode.common.point.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day24 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24.class);
    private static final Pattern PATTERN = Pattern.compile("(e|se|sw|w|nw|ne)");

    private Day24() {
        // No-Op
    }

    static Point2D move(String code) {
        Matcher matcher = PATTERN.matcher(code);
        int x = 0;
        int y = 0;

        while (matcher.find()) {
            String direction = matcher.group(1);
            LOGGER.trace("Match: {}", direction);
            switch (direction) {
                case "e" -> x += 2;
                case "se" -> {
                    x += 1;
                    y -= 2;
                }
                case "sw" -> {
                    x -= 1;
                    y -= 2;
                }
                case "w" -> x -= 2;
                case "nw" -> {
                    x -= 1;
                    y += 2;
                }
                case "ne" -> {
                    x += 1;
                    y += 2;
                }
            }
        }

        return Point2D.of(x, y);
    }

    private static List<Point2D> adjacentTiles(Point2D center) {
        int x = center.x();
        int y = center.y();
        return List.of(
                Point2D.of(x + 2, y), // e
                Point2D.of(x + 1, y - 2), // se
                Point2D.of(x - 1, y - 2), // sw
                Point2D.of(x - 2, y), // w
                Point2D.of(x - 1, y + 2), // nw
                Point2D.of(x + 1, y + 2)); // ne
    }

    private static Set<Point2D> conwayHexGrid(Set<Point2D> blackTiles) {
        Set<Point2D> newTiles = new HashSet<>();
        for (Point2D tile : blackTiles) {
            newTiles.add(tile);
            newTiles.addAll(adjacentTiles(tile));
        }

        Set<Point2D> newBlackTiles = new HashSet<>();
        for (Point2D tile : newTiles) {
            long count = adjacentTiles(tile).stream().filter(blackTiles::contains).count();
            if (blackTiles.contains(tile)) {
                if (count == 1 || count == 2) {
                    newBlackTiles.add(tile);
                }
            } else if (count == 2) {
                newBlackTiles.add(tile);
            }
        }

        return newBlackTiles;
    }

    /**
     * --- Day 24: Lobby Layout ---
     * Your raft makes it to the tropical island; it turns out that the small crab
     * was an excellent navigator. You make your way to the resort.
     *
     * As you enter the lobby, you discover a small problem: the floor is being
     * renovated. You can't even reach the check-in desk until they've finished
     * installing the new tile floor.
     *
     * The tiles are all hexagonal; they need to be arranged in a hex grid with a
     * very specific color pattern. Not in the mood to wait, you offer to help
     * figure out the pattern.
     *
     * The tiles are all white on one side and black on the other. They start with
     * the white side facing up. The lobby is large enough to fit whatever pattern
     * might need to appear there.
     *
     * A member of the renovation crew gives you a list of the tiles that need to
     * be flipped over (your puzzle input). Each line in the list identifies a
     * single tile that needs to be flipped by giving a series of steps starting
     * from a reference tile in the very center of the room. (Every line starts
     * from the same reference tile.)
     *
     * Because the tiles are hexagonal, every tile has six neighbors: east,
     * southeast, southwest, west, northwest, and northeast. These directions are
     * given in your list, respectively, as e, se, sw, w, nw, and ne. A tile is
     * identified by a series of these directions with no delimiters; for example,
     * esenee identifies the tile you land on if you start at the reference tile
     * and then move one tile east, one tile southeast, one tile northeast, and
     * one tile east.
     *
     * Each time a tile is identified, it flips from white to black or from black
     * to white. Tiles might be flipped more than once. For example, a line like
     * esew flips a tile immediately adjacent to the reference tile, and a line
     * like nwwswee flips the reference tile itself.
     *
     * Here is a larger example:
     *
     * sesenwnenenewseeswwswswwnenewsewsw
     * neeenesenwnwwswnenewnwwsewnenwseswesw
     * seswneswswsenwwnwse
     * nwnwneseeswswnenewneswwnewseswneseene
     * swweswneswnenwsewnwneneseenw
     * eesenwseswswnenwswnwnwsewwnwsene
     * sewnenenenesenwsewnenwwwse
     * wenwwweseeeweswwwnwwe
     * wsweesenenewnwwnwsenewsenwwsesesenwne
     * neeswseenwwswnwswswnw
     * nenwswwsewswnenenewsenwsenwnesesenew
     * enewnwewneswsewnwswenweswnenwsenwsw
     * sweneswneswneneenwnewenewwneswswnese
     * swwesenesewenwneswnwwneseswwne
     * enesenwswwswneneswsenwnewswseenwsese
     * wnwnesenesenenwwnenwsewesewsesesew
     * nenewswnwewswnenesenwnesewesw
     * eneswnwswnwsenenwnwnwwseeswneewsenese
     * neswnwewnwnwseenwseesewsenwsweewe
     * wseweeenwnesenwwwswnew
     *
     * In the above example, 10 tiles are flipped once (to black), and 5 more are
     * flipped twice (to black, then back to white). After all of these
     * instructions have been followed, a total of 10 tiles are black.
     *
     * Go through the renovation crew's list and determine which tiles they need
     * to flip. After all of the instructions have been followed, how many tiles
     * are left with the black side up?
     *
     * --- Part Two ---
     *
     * The tile floor in the lobby is meant to be a living art exhibit. Every day,
     * the tiles are all flipped according to the following rules:
     *
     * - Any black tile with zero or more than 2 black tiles immediately
     * adjacent to it is flipped to white.
     * - Any white tile with exactly 2 black tiles immediately adjacent to it
     * is flipped to black.
     *
     * Here, tiles immediately adjacent means the six tiles directly touching the
     * tile in question.
     *
     * The rules are applied simultaneously to every tile; put another way, it is
     * first determined which tiles need to be flipped, then they are all flipped
     * at the same time.
     *
     * In the above example, the number of black tiles that are facing up after
     * the given number of days has passed is as follows:
     *
     * Day 1: 15
     * Day 2: 12
     * Day 3: 25
     * Day 4: 14
     * Day 5: 23
     * Day 6: 28
     * Day 7: 41
     * Day 8: 37
     * Day 9: 49
     * Day 10: 37
     *
     * Day 20: 132
     * Day 30: 259
     * Day 40: 406
     * Day 50: 566
     * Day 60: 788
     * Day 70: 1106
     * Day 80: 1373
     * Day 90: 1844
     * Day 100: 2208
     *
     * After executing this process a total of 100 times, there would be 2208
     * black tiles facing up.
     *
     * How many tiles will be black after 100 days?
     */
    static Map<Point2D, Boolean> hexGrid(Scanner scanner) {
        Map<Point2D, Boolean> grid = new HashMap<>();

        while (scanner.hasNextLine()) {
            Point2D move = move(scanner.nextLine());
            grid.compute(move, (p, v) -> v == null || !v);
        }

        LOGGER.trace("Grid: {}", grid);
        return grid;
    }

    static Set<Point2D> runLobbyLayout(Map<Point2D, Boolean> grid, int days) {
        Set<Point2D> blackTiles = grid.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        for (int day = 1; day <= days; ++day) {
            blackTiles = conwayHexGrid(blackTiles);
            LOGGER.debug("Day {}: {}", day, blackTiles.size());
        }

        return blackTiles;
    }
}
