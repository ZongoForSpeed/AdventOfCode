package com.adventofcode.year2022;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.Position2D;
import com.adventofcode.common.point.map.CharMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day22 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day22.class);

    private static final Map<Position2D, Position2D> WRAPPER_CUBE = Map.ofEntries(
            Map.entry(Position2D.of(1, 0, Direction.UP), Position2D.of(0, 3, Direction.RIGHT)),     //
            Map.entry(Position2D.of(1, 0, Direction.LEFT), Position2D.of(0, 2, Direction.RIGHT)),   //
            Map.entry(Position2D.of(2, 0, Direction.UP), Position2D.of(0, 3, Direction.UP)),        //
            Map.entry(Position2D.of(2, 0, Direction.RIGHT), Position2D.of(1, 2, Direction.LEFT)),   //
            Map.entry(Position2D.of(2, 0, Direction.DOWN), Position2D.of(1, 1, Direction.LEFT)),    //
            Map.entry(Position2D.of(1, 1, Direction.RIGHT), Position2D.of(2, 0, Direction.UP)),     //
            Map.entry(Position2D.of(1, 1, Direction.LEFT), Position2D.of(0, 2, Direction.DOWN)),    //
            Map.entry(Position2D.of(0, 2, Direction.UP), Position2D.of(1, 1, Direction.RIGHT)),     //
            Map.entry(Position2D.of(0, 2, Direction.LEFT), Position2D.of(1, 0, Direction.RIGHT)),   //
            Map.entry(Position2D.of(1, 2, Direction.RIGHT), Position2D.of(2, 0, Direction.LEFT)),   //
            Map.entry(Position2D.of(1, 2, Direction.DOWN), Position2D.of(0, 3, Direction.LEFT)),    //
            Map.entry(Position2D.of(0, 3, Direction.RIGHT), Position2D.of(1, 2, Direction.UP)),     //
            Map.entry(Position2D.of(0, 3, Direction.DOWN), Position2D.of(2, 0, Direction.DOWN)),    //
            Map.entry(Position2D.of(0, 3, Direction.LEFT), Position2D.of(1, 0, Direction.DOWN))     //
    );

    private static final Map<Position2D, Position2D> WRAPPER_EXAMPLE = Map.ofEntries(
            Map.entry(Position2D.of(2, 0, Direction.UP), Position2D.of(0, 1, Direction.DOWN)),      //
            Map.entry(Position2D.of(2, 0, Direction.LEFT), Position2D.of(1, 1, Direction.DOWN)),    //
            Map.entry(Position2D.of(2, 0, Direction.RIGHT), Position2D.of(3, 2, Direction.LEFT)),   //
            Map.entry(Position2D.of(0, 1, Direction.UP), Position2D.of(2, 0, Direction.DOWN)),      //
            Map.entry(Position2D.of(0, 1, Direction.DOWN), Position2D.of(2, 2, Direction.UP)),      //
            Map.entry(Position2D.of(0, 1, Direction.LEFT), Position2D.of(3, 2, Direction.UP)),      //
            Map.entry(Position2D.of(1, 1, Direction.UP), Position2D.of(2, 0, Direction.RIGHT)),     //
            Map.entry(Position2D.of(1, 1, Direction.DOWN), Position2D.of(2, 2, Direction.LEFT)),    //
            Map.entry(Position2D.of(2, 1, Direction.RIGHT), Position2D.of(3, 2, Direction.DOWN)),   //
            Map.entry(Position2D.of(2, 2, Direction.DOWN), Position2D.of(0, 1, Direction.UP)),      //
            Map.entry(Position2D.of(2, 2, Direction.LEFT), Position2D.of(1, 1, Direction.UP)),      //
            Map.entry(Position2D.of(3, 2, Direction.UP), Position2D.of(2, 1, Direction.LEFT)),      //
            Map.entry(Position2D.of(3, 2, Direction.DOWN), Position2D.of(0, 1, Direction.RIGHT)),   //
            Map.entry(Position2D.of(3, 2, Direction.RIGHT), Position2D.of(2, 0, Direction.LEFT))    //
    );

    private Day22() {
        // No-Op
    }

    /**
     * --- Day 22: Monkey Map ---
     * <p>
     * The monkeys take you on a surprisingly easy trail through the jungle.
     * They're even going in roughly the right direction according to your
     * handheld device's Grove Positioning System.
     * <p>
     * As you walk, the monkeys explain that the grove is protected by a force
     * field. To pass through the force field, you have to enter a password; doing
     * so involves tracing a specific path on a strangely-shaped board.
     * <p>
     * At least, you're pretty sure that's what you have to do; the elephants
     * aren't exactly fluent in monkey.
     * <p>
     * The monkeys give you notes that they took when they last saw the password
     * entered (your puzzle input).
     * <p>
     * For example:
     * <p>
     * <pre>
     *         ...#
     *         .#..
     *         #...
     *         ....
     * ...#.......#
     * ........#...
     * ..#....#....
     * ..........#.
     *         ...#....
     *         .....#..
     *         .#......
     *         ......#.
     *
     * 10R5L5R10L4R5L5
     * </pre>
     * <p>
     * The first half of the monkeys' notes is a map of the board. It is comprised
     * of a set of open tiles (on which you can move, drawn .) and solid walls
     * (tiles which you cannot enter, drawn #).
     * <p>
     * The second half is a description of the path you must follow. It consists
     * of alternating numbers and letters:
     * <p>
     * - A number indicates the number of tiles to move in the direction you
     * are facing. If you run into a wall, you stop moving forward and
     * continue with the next instruction.
     * - A letter indicates whether to turn 90 degrees clockwise (R) or
     * counterclockwise (L). Turning happens in-place; it does not change
     * your current tile.
     * <p>
     * So, a path like 10R5 means "go forward 10 tiles, then turn clockwise 90
     * degrees, then go forward 5 tiles".
     * <p>
     * You begin the path in the leftmost open tile of the top row of tiles.
     * Initially, you are facing to the right (from the perspective of how the map
     * is drawn).
     * <p>
     * If a movement instruction would take you off of the map, you wrap around to
     * the other side of the board. In other words, if your next tile is off of
     * the board, you should instead look in the direction opposite of your
     * current facing as far as you can until you find the opposite edge of the
     * board, then reappear there.
     * <p>
     * For example, if you are at A and facing to the right, the tile in front of
     * you is marked B; if you are at C and facing down, the tile in front of you
     * is marked D:
     * <p>
     * <pre>
     *         ...#
     *         .#..
     *         #...
     *         ....
     * ...#.D.....#
     * ........#...
     * B.#....#...A
     * .....C....#.
     *         ...#....
     *         .....#..
     *         .#......
     *         ......#.
     * </pre>
     * <p>
     * It is possible for the next tile (after wrapping around) to be a wall; this
     * still counts as there being a wall in front of you, and so movement stops
     * before you actually wrap to the other side of the board.
     * <p>
     * By drawing the last facing you had with an arrow on each tile you visit,
     * the full path taken by the above example looks like this:
     * <p>
     * <pre>
     *         >>v#
     *         .#v.
     *         #.v.
     *         ..v.
     * ...#...v..v#
     * >>>v...>#.>>
     * ..#v...#....
     * ...>>>>v..#.
     *         ...#....
     *         .....#..
     *         .#......
     *         ......#.
     * </pre>
     * <p>
     * To finish providing the password to this strange input device, you need to
     * determine numbers for your final row, column, and facing as your final
     * position appears from the perspective of the original map. Rows start from
     * 1 at the top and count downward; columns start from 1 at the left and count
     * rightward. (In the above example, row 1, column 1 refers to the empty space
     * with no tile on it in the top-left corner.) Facing is 0 for right (>), 1
     * for down (v), 2 for left (<), and 3 for up (^). The final password is the
     * sum of 1000 times the row, 4 times the column, and the facing.
     * <p>
     * In the above example, the final row is 6, the final column is 8, and the
     * final facing is 0. So, the final password is 1000 * 6 + 4 * 8 + 0: 6032.
     * <p>
     * Follow the path given in the monkeys' notes. What is the final password?
     */
    public static int partOne(Scanner scanner) {
        CharMap map = CharMap.read(scanner, c -> c != ' ', true);

        LOGGER.debug("map:\n{}", map);

        String line = scanner.nextLine();
        LOGGER.debug("line: {}", line);

        List<Point2D> points = map.points();

        int x = 0;
        int y = points.stream().filter(p -> p.x() == 0).mapToInt(Point2D::y).min().orElseThrow();
        int xMax = points.stream().mapToInt(Point2D::x).max().orElseThrow();
        int yMax = points.stream().mapToInt(Point2D::y).max().orElseThrow();

        Position2D position = Position2D.of(x, y, Direction.RIGHT);

        Pattern pattern = Pattern.compile("(L|R|\\d+)");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group(1);
            LOGGER.trace("group = {}", group);
            if ("R".equals(group)) {
                LOGGER.debug("turning right");
                position = position.right();
            } else if ("L".equals(group)) {
                LOGGER.debug("turning left");
                position = position.left();
            } else {
                int moves = Integer.parseInt(group);
                LOGGER.debug("moving forward {}", moves);
                for (int move = 0; move < moves; ++move) {
                    Position2D moveLoop = position.moveLoop(xMax, yMax);
                    while (map.get(moveLoop.p()) == ' ') {
                        moveLoop = moveLoop.moveLoop(xMax, yMax);
                    }
                    char c = map.get(moveLoop.p());
                    if (c == '#') {
                        break;
                    }
                    position = moveLoop;
                }
            }
        }

        LOGGER.info("Position = {}", position);

        return password(position);
    }

    /**
     * --- Part Two ---
     * <p>
     * As you reach the force field, you think you hear some Elves in the
     * distance. Perhaps they've already arrived?
     * <p>
     * You approach the strange input device, but it isn't quite what the monkeys
     * drew in their notes. Instead, you are met with a large cube; each of its
     * six faces is a square of 50x50 tiles.
     * <p>
     * To be fair, the monkeys' map does have six 50x50 regions on it. If you were
     * to carefully fold the map, you should be able to shape it into a cube!
     * <p>
     * In the example above, the six (smaller, 4x4) faces of the cube are:
     * <p>
     * <pre>
     *         1111
     *         1111
     *         1111
     *         1111
     * 222233334444
     * 222233334444
     * 222233334444
     * 222233334444
     *         55556666
     *         55556666
     *         55556666
     *         55556666
     * </pre>
     * <p>
     * You still start in the same position and with the same facing as before,
     * but the wrapping rules are different. Now, if you would walk off the board,
     * you instead proceed around the cube. From the perspective of the map, this
     * can look a little strange. In the above example, if you are at A and move
     * to the right, you would arrive at B facing down; if you are at C and move
     * down, you would arrive at D facing up:
     * <p>
     * <pre>
     *         ...#
     *         .#..
     *         #...
     *         ....
     * ...#.......#
     * ........#..A
     * ..#....#....
     * .D........#.
     *         ...#..B.
     *         .....#..
     *         .#......
     *         ..C...#.
     * </pre>
     * <p>
     * Walls still block your path, even if they are on a different face of the
     * cube. If you are at E facing up, your movement is blocked by the wall
     * marked by the arrow:
     * <p>
     * <pre>
     *        ...#
     *        .#..
     *     -->#...
     *        ....
     * ..#..E....#
     * .......#...
     * .#....#....
     * .........#.
     *        ...#....
     *        .....#..
     *        .#......
     *        ......#.
     * </pre>
     * Using the same method of drawing the last facing you had with an arrow on
     * each tile you visit, the full path taken by the above example now looks
     * like this:
     * <p>
     * <pre>
     *         >>v#
     *         .#v.
     *         #.v.
     *         ..v.
     * ...#..^...v#
     * .>>>>>^.#.>>
     * .^#....#....
     * .^........#.
     *         ...#..v.
     *         .....#v.
     *         .#v<<<<.
     *         ..v...#.
     * </pre>
     * <p>
     * The final password is still calculated from your final position and facing
     * from the perspective of the map. In this example, the final row is 5, the
     * final column is 7, and the final facing is 3, so the final password is 1000
     * * 5 + 4 * 7 + 3 = 5031.
     * <p>
     * Fold the map into a cube, then follow the path given in the monkeys' notes.
     * What is the final password?
     */
    public static int partTwo(Scanner scanner) {
        return monkeyCube(scanner, WRAPPER_CUBE, 50);
    }

    public static int partTwoExample(Scanner scanner) {
        return monkeyCube(scanner, WRAPPER_EXAMPLE, 4);
    }

    private static int monkeyCube(Scanner scanner, Map<Position2D, Position2D> wrapper, int size) {
        CharMap map = CharMap.read(scanner, c -> c != ' ', true);

        LOGGER.debug("map:\n{}", map);

        String line = scanner.nextLine();
        LOGGER.debug("line: {}", line);

        List<Point2D> points = map.points();

        int x = points.stream().filter(p -> p.y() == 0).mapToInt(Point2D::x).min().orElseThrow();
        int y = 0;

        Position2D position = Position2D.of(x, y, Direction.RIGHT);

        CharMap debugMap = new CharMap(map);

        Pattern pattern = Pattern.compile("(L|R|\\d+)");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group(1);
            if ("R".equals(group)) {
                LOGGER.debug("turning right");
                position = position.right();
            } else if ("L".equals(group)) {
                LOGGER.debug("turning left");
                position = position.left();
            } else {
                int moves = Integer.parseInt(group);
                LOGGER.debug("moving forward {}", moves);
                for (int move = 0; move < moves; ++move) {
                    debugMap.set(position.p(), switch (position.direction()) {
                        case UP -> '^';
                        case DOWN -> 'v';
                        case LEFT -> '<';
                        case RIGHT -> '>';
                    });
                    Position2D moveLoop = position.move();
                    if (moveLoop.x() < 0 || moveLoop.y() < 0 || map.get(moveLoop.p()) == ' ') {
                        Position2D wrapCube = wrapCube(position, wrapper, size);
                        char c = map.get(wrapCube.p());
                        if (c == '#') {
                            break;
                        }
                        position = wrapCube;

                    } else {
                        char c = map.get(moveLoop.p());
                        if (c == '#') {
                            break;
                        }
                        position = moveLoop;
                    }
                }
                LOGGER.trace("debugMap:\n{}", debugMap);
            }
        }

        LOGGER.info("Position = {}", position);

        return password(position);
    }

    private static int password(Position2D position) {
        int column = position.x() + 1;
        int row = position.y() + 1;
        int facing = switch (position.direction()) {
            case RIGHT -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case UP -> 3;
        };
        return 1000 * row + 4 * column + facing;
    }

    private static Position2D wrapCube(Position2D position, Map<Position2D, Position2D> wrapper, int size) {
        Position2D current = Position2D.of(position.x() / size, position.y() / size, position.direction());
        Position2D next = wrapper.get(current);
        if (next == null) {
            throw new IllegalStateException("Unknown case '" + current + "'");
        }
        int x = position.x() % size;
        int y = position.y() % size;

        int delta = switch (position.direction()) {
            case UP -> x;
            case LEFT -> size - 1 - y;
            case DOWN -> size - 1 - x;
            case RIGHT -> y;
        };
        Point2D relative = switch (next.direction()) {
            case UP -> Point2D.of(delta, size - 1);
            case LEFT -> Point2D.of(size - 1, size - 1 - delta);
            case DOWN -> Point2D.of(size - 1 - delta, 0);
            case RIGHT -> Point2D.of(0, delta);
        };

        return Position2D.of(next.x() * size + relative.x(), next.y() * size + relative.y(), next.direction());
    }

}
