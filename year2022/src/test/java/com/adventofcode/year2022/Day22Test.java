package com.adventofcode.year2022;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class Day22Test extends AbstractTest {
    Day22Test() {
        super(2022, 22);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Day22Test.class);

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
     * ...#
     * .#..
     * #...
     * ....
     * ...#.......#
     * ........#...
     * ..#....#....
     * ..........#.
     * ...#....
     * .....#..
     * .#......
     * ......#.
     * <p>
     * 10R5L5R10L4R5L5
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
     * ...#
     * .#..
     * #...
     * ....
     * ...#.D.....#
     * ........#...
     * B.#....#...A
     * .....C....#.
     * ...#....
     * .....#..
     * .#......
     * ......#.
     * <p>
     * It is possible for the next tile (after wrapping around) to be a wall; this
     * still counts as there being a wall in front of you, and so movement stops
     * before you actually wrap to the other side of the board.
     * <p>
     * By drawing the last facing you had with an arrow on each tile you visit,
     * the full path taken by the above example looks like this:
     * <p>
     * >>v#
     * .#v.
     * #.v.
     * ..v.
     * ...#...v..v#
     * >>>v...>#.>>
     * ..#v...#....
     * ...>>>>v..#.
     * ...#....
     * .....#..
     * .#......
     * ......#.
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
    private static int monkeyMap(Scanner scanner) {
        CharMap map = CharMap.read(scanner, c -> c != ' ', true);

        LOGGER.debug("map:\n{}", map);

        String line = scanner.nextLine();
        LOGGER.debug("line: {}", line);

        List<Point2D> points = map.points();

        int x = 0;
        int y = points.stream().filter(p -> p.x() == 0).mapToInt(Point2D::y).min().orElseThrow();
        int xMax = points.stream().mapToInt(Point2D::x).max().orElseThrow();
        int yMax = points.stream().mapToInt(Point2D::y).max().orElseThrow();

        Point2D position = Point2D.of(x, y);
        Direction direction = Direction.RIGHT;

        Pattern pattern = Pattern.compile("(L|R|\\d+)");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group(1);
            // LOGGER.debug("group = {}", group);
            if ("R".equals(group)) {
                LOGGER.debug("turning right");
                direction = direction.right();
            } else if ("L".equals(group)) {
                LOGGER.debug("turning left");
                direction = direction.left();
            } else {
                int moves = Integer.parseInt(group);
                LOGGER.debug("moving forward {}", moves);
                for (int move = 0; move < moves; ++move) {
                    Point2D moveLoop = position.moveLoop(direction, xMax, yMax);
                    while (map.get(moveLoop) == ' ') {
                        moveLoop = moveLoop.moveLoop(direction, xMax, yMax);
                    }
                    char c = map.get(moveLoop);
                    if (c == '#') {
                        break;
                    }
                    position = moveLoop;
                }
            }
        }

        LOGGER.info("Position = {}", position);
        LOGGER.info("Direction = {}", direction);

        return password(position, direction);
    }

    @Test
    void testExample() {
        String input = """
                        ...#
                        .#..
                        #...
                        ....
                ...#.......#
                ........#...
                ..#....#....
                ..........#.
                        ...#....
                        .....#..
                        .#......
                        ......#.
                
                10R5L5R10L4R5L5""";

        {
            Scanner scanner = new Scanner(input);
            int password = monkeyMap(scanner);
            assertThat(password).isEqualTo(6032);
        }

        {
            Map<FaceDirection, FaceDirection> moves = Map.ofEntries(
                    Map.entry(FaceDirection.of(1, Direction.UP), FaceDirection.of(2, Direction.DOWN)),
                    Map.entry(FaceDirection.of(1, Direction.LEFT), FaceDirection.of(3, Direction.DOWN)),
                    Map.entry(FaceDirection.of(1, Direction.RIGHT), FaceDirection.of(6, Direction.LEFT)),

                    Map.entry(FaceDirection.of(2, Direction.UP), FaceDirection.of(1, Direction.DOWN)),
                    Map.entry(FaceDirection.of(2, Direction.DOWN), FaceDirection.of(5, Direction.UP)),
                    Map.entry(FaceDirection.of(2, Direction.LEFT), FaceDirection.of(6, Direction.UP)),

                    Map.entry(FaceDirection.of(3, Direction.UP), FaceDirection.of(1, Direction.RIGHT)),
                    Map.entry(FaceDirection.of(3, Direction.DOWN), FaceDirection.of(5, Direction.LEFT)),

                    Map.entry(FaceDirection.of(4, Direction.RIGHT), FaceDirection.of(6, Direction.DOWN)),

                    Map.entry(FaceDirection.of(5, Direction.DOWN), FaceDirection.of(2, Direction.UP)),
                    Map.entry(FaceDirection.of(5, Direction.LEFT), FaceDirection.of(3, Direction.UP)),

                    Map.entry(FaceDirection.of(6, Direction.UP), FaceDirection.of(4, Direction.LEFT)),
                    Map.entry(FaceDirection.of(6, Direction.DOWN), FaceDirection.of(2, Direction.RIGHT)),
                    Map.entry(FaceDirection.of(6, Direction.RIGHT), FaceDirection.of(1, Direction.LEFT))
            );
            Scanner scanner = new Scanner(input);
            int password = monkeyCube(scanner);
            assertThat(password).isEqualTo(6032);
        }
    }

    record FaceDirection(int face, Direction direction) {
        public static FaceDirection of(int face, Direction direction) {
            return new FaceDirection(face, direction);
        }
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
     * 1111
     * 1111
     * 1111
     * 1111
     * 222233334444
     * 222233334444
     * 222233334444
     * 222233334444
     * 55556666
     * 55556666
     * 55556666
     * 55556666
     * <p>
     * You still start in the same position and with the same facing as before,
     * but the wrapping rules are different. Now, if you would walk off the board,
     * you instead proceed around the cube. From the perspective of the map, this
     * can look a little strange. In the above example, if you are at A and move
     * to the right, you would arrive at B facing down; if you are at C and move
     * down, you would arrive at D facing up:
     * <p>
     * ...#
     * .#..
     * #...
     * ....
     * ...#.......#
     * ........#..A
     * ..#....#....
     * .D........#.
     * ...#..B.
     * .....#..
     * .#......
     * ..C...#.
     * <p>
     * Walls still block your path, even if they are on a different face of the
     * cube. If you are at E facing up, your movement is blocked by the wall
     * marked by the arrow:
     * <p>
     * ...#
     * .#..
     * -->#...
     * ....
     * ...#..E....#
     * ........#...
     * ..#....#....
     * ..........#.
     * ...#....
     * .....#..
     * .#......
     * ......#.
     * <p>
     * Using the same method of drawing the last facing you had with an arrow on
     * each tile you visit, the full path taken by the above example now looks
     * like this:
     * <p>
     * >>v#
     * .#v.
     * #.v.
     * ..v.
     * ...#..^...v#
     * .>>>>>^.#.>>
     * .^#....#....
     * .^........#.
     * ...#..v.
     * .....#v.
     * .#v<<<<.
     * ..v...#.
     * <p>
     * The final password is still calculated from your final position and facing
     * from the perspective of the map. In this example, the final row is 5, the
     * final column is 7, and the final facing is 3, so the final password is 1000
     * * 5 + 4 * 7 + 3 = 5031.
     * <p>
     * Fold the map into a cube, then follow the path given in the monkeys' notes.
     * What is the final password?
     */
    private static int monkeyCube(Scanner scanner) {
        CharMap map = CharMap.read(scanner, c -> c != ' ', true);

        LOGGER.debug("map:\n{}", map);

        String line = scanner.nextLine();
        LOGGER.debug("line: {}", line);

        List<Point2D> points = map.points();

        int x = 0;
        int y = points.stream().filter(p -> p.x() == 0).mapToInt(Point2D::y).min().orElseThrow();

        Point2D position = Point2D.of(x, y);
        Direction direction = Direction.RIGHT;

        Pattern pattern = Pattern.compile("(L|R|\\d+)");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group(1);
            // LOGGER.debug("group = {}", group);
            if ("R".equals(group)) {
                LOGGER.debug("turning right");
                direction = direction.right();
            } else if ("L".equals(group)) {
                LOGGER.debug("turning left");
                direction = direction.left();
            } else {
                int moves = Integer.parseInt(group);
                LOGGER.debug("moving forward {}", moves);
                for (int move = 0; move < moves; ++move) {
                    Point2D moveLoop = position.move(direction);
                    if (moveLoop.x() < 0 || moveLoop.y() < 0 || map.get(moveLoop) == ' ') {
                        Position wrapCube = wrapCube(position, direction);
                        char c = map.get(wrapCube.x(), wrapCube.y());
                        if (c == '#') {
                            break;
                        }
                        position = Point2D.of(wrapCube.x(), wrapCube.y());
                        direction = wrapCube.direction();
                    } else {
                        char c = map.get(moveLoop);
                        if (c == '#') {
                            break;
                        }
                        position = moveLoop;
                    }

                }
            }
        }

        LOGGER.info("Position = {}", position);
        LOGGER.info("Direction = {}", direction);

        return password(position, direction);
    }

    private static int password(Point2D position, Direction direction) {
        int column = position.x() + 1;
        int row = position.y() + 1;
        int facing = switch (direction) {
            case UP -> 3;
            case DOWN -> 1;
            case LEFT -> 2;
            case RIGHT -> 0;
        };
        return 1000 * row + 4 * column + facing;
    }


    @Override
    public void partOne(Scanner scanner) {
        assertThat(monkeyMap(scanner)).isEqualTo(117102);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(monkeyCube(scanner)).isEqualTo(117102);
    }

    record Position(int x, int y, Direction direction) {

    }

    private static Position wrapCube(Point2D position, Direction direction) {
        String caseString = direction + "_" + position.x() / 50 + "_" + position.y() / 50;
        return switch (caseString) {
            case "UP_1_0" -> new Position(0, 100 + position.x(), Direction.RIGHT); // to Dir.EAST // 1 -> N
            case "LEFT_1_0" -> new Position(0, 149 - position.y(), Direction.RIGHT); // to Dir.EAST // 1 -> W
            case "UP_2_0" -> new Position(position.x() - 100, 199, Direction.DOWN); // to Dir.NORTH // 2 -> N
            case "RIGHT_2_0" -> new Position(99, 149 - position.y(), Direction.LEFT); // to Dir.WEST // 2 -> E
            case "DOWN_2_0" -> new Position(99, -50 + position.x(), Direction.LEFT); // to Dir.WEST // 2 -> S
            case "RIGHT_1_1" -> new Position(50 + position.y(), 49, Direction.DOWN); // to Dir.NORTH // 3 -> E
            case "LEFT_1_1" -> new Position(position.y() - 50, 100, Direction.UP); // to Dir.SOUTH // 3 -> W
            case "UP_0_2" -> new Position(50, position.x() + 50, Direction.RIGHT); // to Dir.EAST // 4 -> N
            case "LEFT_0_2" -> new Position(50, 149 - position.y(), Direction.RIGHT); // to Dir.EAST // 4 -> W
            case "RIGHT_1_2" -> new Position(149, 149 - position.y(), Direction.LEFT); // to Dir.WEST // 5 -> E
            case "DOWN_1_2" -> new Position(49, 100 + position.x(), Direction.LEFT); // to Dir.WEST // 5 -> S
            case "RIGHT_0_3" -> new Position(position.y() - 100, 149, Direction.DOWN); // to Dir.NORTH // 6 -> E
            case "DOWN_0_3" -> new Position(position.x() + 100, 0, Direction.UP); // to Dir.SOUTH // 6 -> S
            case "LEFT_0_3" -> new Position(position.y() - 100, 0, Direction.UP); // to Dir.SOUTH // 6 -> W
            default -> throw new IllegalStateException("Unknown case '" + caseString + "'");
        };
    }

    // private fun wrapCube(position: Point2D, dir: Dir): Pair<Point2D, Dir> {
    //     return when (Triple(dir, position.x / 50, position.y / 50)) {
    //         Triple(Dir.NORTH, 1, 0) -> Point2D(0, 100 + position.x) to Dir.EAST // 1 -> N
    //         Triple(Dir.WEST, 1, 0) -> Point2D(0, 149 - position.y) to Dir.EAST // 1 -> W
    //         Triple(Dir.NORTH, 2, 0) -> Point2D(position.x - 100, 199) to Dir.NORTH // 2 -> N
    //         Triple(Dir.EAST, 2, 0) -> Point2D(99, 149 - position.y) to Dir.WEST // 2 -> E
    //         Triple(Dir.SOUTH, 2, 0) -> Point2D(99, -50 + position.x) to Dir.WEST // 2 -> S
    //         Triple(Dir.EAST, 1, 1) -> Point2D(50 + position.y, 49) to Dir.NORTH // 3 -> E
    //         Triple(Dir.WEST, 1, 1) -> Point2D(position.y - 50, 100) to Dir.SOUTH // 3 -> W
    //         Triple(Dir.NORTH, 0, 2) -> Point2D(50, position.x + 50) to Dir.EAST // 4 -> N
    //         Triple(Dir.WEST, 0, 2) -> Point2D(50, 149 - position.y) to Dir.EAST // 4 -> W
    //         Triple(Dir.EAST, 1, 2) -> Point2D(149, 149 - position.y) to Dir.WEST // 5 -> E
    //         Triple(Dir.SOUTH, 1, 2) -> Point2D(49, 100 + position.x) to Dir.WEST // 5 -> S
    //         Triple(Dir.EAST, 0, 3) -> Point2D(position.y - 100, 149) to Dir.NORTH // 6 -> E
    //         Triple(Dir.SOUTH, 0, 3) -> Point2D(position.x + 100, 0) to Dir.SOUTH // 6 -> S
    //         Triple(Dir.WEST, 0, 3) -> Point2D(position.y - 100, 0) to Dir.SOUTH // 6 -> W
    //         else -> error("Invalid state")
    //     }
    // }


}
