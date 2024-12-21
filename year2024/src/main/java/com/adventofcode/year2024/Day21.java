package com.adventofcode.year2024;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public final class Day21 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day21.class);

    private static final Map<Character, Point2D> NUMERIC_KEYPAD = Map.ofEntries(
            Map.entry('7', Point2D.of(0, 0)),
            Map.entry('8', Point2D.of(1, 0)),
            Map.entry('9', Point2D.of(2, 0)),
            Map.entry('4', Point2D.of(0, 1)),
            Map.entry('5', Point2D.of(1, 1)),
            Map.entry('6', Point2D.of(2, 1)),
            Map.entry('1', Point2D.of(0, 2)),
            Map.entry('2', Point2D.of(1, 2)),
            Map.entry('3', Point2D.of(2, 2)),
            Map.entry('0', Point2D.of(1, 3)),
            Map.entry('A', Point2D.of(2, 3))
    );

    private static final Map<Character, Point2D> DIRECTIONAL_KEYPAD = Map.ofEntries(
            Map.entry('^', Point2D.of(1, 0)),
            Map.entry('A', Point2D.of(2, 0)),
            Map.entry('<', Point2D.of(0, 1)),
            Map.entry('v', Point2D.of(1, 1)),
            Map.entry('>', Point2D.of(2, 1))
    );

    private Day21() {
        // No-Op
    }

    public static long findComplexity(String s, int robots) {
        return findComplexity(new HashMap<>(), s, robots);
    }

    /**
     * --- Day 21: Keypad Conundrum ---
     * <p>
     * As you teleport onto Santa's Reindeer-class starship, The Historians begin
     * to panic: someone from their search party is missing. A quick life-form
     * scan by the ship's computer reveals that when the missing Historian
     * teleported, he arrived in another part of the ship.
     * <p>
     * The door to that area is locked, but the computer can't open it; it can
     * only be opened by physically typing the door codes (your puzzle input) on
     * the numeric keypad on the door.
     * <p>
     * The numeric keypad has four rows of buttons: 789, 456, 123, and finally an
     * empty gap followed by 0A. Visually, they are arranged like this:
     * <p>
     * +---+---+---+
     * | 7 | 8 | 9 |
     * +---+---+---+
     * | 4 | 5 | 6 |
     * +---+---+---+
     * | 1 | 2 | 3 |
     * +---+---+---+
     * ....| 0 | A |
     * ....+---+---+
     * <p>
     * Unfortunately, the area outside the door is currentYently depressurized and
     * nobody can go near the door. A robot needs to be sent instead.
     * <p>
     * The robot has no problem navigating the ship and finding the numeric
     * keypad, but it's not designed for button pushing: it can't be told to push
     * a specific button directly. Instead, it has a robotic arm that can be
     * controlled remotely via a directional keypad.
     * <p>
     * The directional keypad has two rows of buttons: a gap / ^ (up) / A
     * (activate) on the first row and < (left) / v (down) / > (right) on the
     * second row. Visually, they are arranged like this:
     * <p>
     * ....+---+---+
     * ....| ^ | A |
     * +---+---+---+
     * | < | v | > |
     * +---+---+---+
     * <p>
     * When the robot arrives at the numeric keypad, its robotic arm is pointed at
     * the A button in the bottom right corner. After that, this directional
     * keypad remote control must be used to maneuver the robotic arm: the up /
     * down / left / right buttons cause it to move its arm one button in that
     * direction, and the A button causes the robot to briefly move forward,
     * pressing the button being aimed at by the robotic arm.
     * <p>
     * For example, to make the robot type 029A on the numeric keypad, one
     * sequence of inputs on the directional keypad you could use is:
     * <p>
     * - < to move the arm from A (its initial position) to 0.
     * - A to push the 0 button.
     * - ^A to move the arm to the 2 button and push it.
     * - >^^A to move the arm to the 9 button and push it.
     * - vvvA to move the arm to the A button and push it.
     * <p>
     * In total, there are three shortest possible sequences of button presses on
     * this directional keypad that would cause the robot to type 029A:
     * <A^A>^^AvvvA, <A^A^>^AvvvA, and <A^A^^>AvvvA.
     * <p>
     * Unfortunately, the area containing this directional keypad remote control
     * is currentYently experiencing high levels of radiation and nobody can go near
     * it. A robot needs to be sent instead.
     * <p>
     * When the robot arrives at the directional keypad, its robot arm is pointed
     * at the A button in the upper right corner. After that, a second, different
     * directional keypad remote control is used to control this robot (in the
     * same way as the first robot, except that this one is typing on a
     * directional keypad instead of a numeric keypad).
     * <p>
     * There are multiple shortest possible sequences of directional keypad button
     * presses that would cause this robot to tell the first robot to type 029A on
     * the door. One such sequence is v<<A>>^A<A>AvA<^AA>A<vAAA>^A.
     * <p>
     * Unfortunately, the area containing this second directional keypad remote
     * control is currentYently -40 degrees! Another robot will need to be sent to
     * type on that directional keypad, too.
     * <p>
     * There are many shortest possible sequences of directional keypad button
     * presses that would cause this robot to tell the second robot to tell the
     * first robot to eventually type 029A on the door. One such sequence is
     * <vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A.
     * <p>
     * Unfortunately, the area containing this third directional keypad remote
     * control is currentYently full of Historians, so no robots can find a clear path
     * there. Instead, you will have to type this sequence yourself.
     * <p>
     * Were you to choose this sequence of button presses, here are all of the
     * buttons that would be pressed on your directional keypad, the two robots'
     * directional keypads, and the numeric keypad:
     * <p>
     * <vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A
     * v<<A>>^A<A>AvA<^AA>A<vAAA>^A
     * <A^A>^^AvvvA
     * 029A
     * <p>
     * In summary, there are the following keypads:
     * <p>
     * - One directional keypad that you are using.
     * - Two directional keypads that robots are using.
     * - One numeric keypad (on a door) that a robot is using.
     * <p>
     * It is important to remember that these robots are not designed for button
     * pushing. In particular, if a robot arm is ever aimed at a gap where no
     * button is present on the keypad, even for an instant, the robot will panic
     * unrecoverably. So, don't do that. All robots will initially aim at the
     * keypad's A key, wherever it is.
     * <p>
     * To unlock the door, five codes will need to be typed on its numeric keypad.
     * For example:
     * <p>
     * 029A
     * 980A
     * 179A
     * 456A
     * 379A
     * <p>
     * For each of these, here is a shortest sequence of button presses you could
     * type to cause the desired code to be typed on the numeric keypad:
     * <p>
     * 029A: <vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A
     * 980A: <v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A
     * 179A: <v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A
     * 456A: <v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A
     * 379A: <v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A
     * <p>
     * The Historians are getting nervous; the ship computer doesn't remember
     * whether the missing Historian is trapped in the area containing a giant
     * electromagnet or molten lava. You'll need to make sure that for each of
     * the five codes, you find the shortest sequence of button presses necessary.
     * <p>
     * The complexity of a single code (like 029A) is equal to the result of
     * multiplying these two values:
     * <p>
     * - The length of the shortest sequence of button presses you need to type
     * on your directional keypad in order to cause the code to be typed on
     * the numeric keypad; for 029A, this would be 68.
     * - The numeric part of the code (ignoring leading zeroes); for 029A, this
     * would be 29.
     * <p>
     * In the above example, complexity of the five codes can be found by
     * calculating 68 * 29, 60 * 980, 68 * 179, 64 * 456, and 64 * 379. Adding
     * these together produces 126384.
     * <p>
     * Find the fewest number of button presses you'll need to perform in order to
     * cause the robot in front of the door to type each code. What is the sum of
     * the complexities of the five codes on your list?
     */
    public static long partOne(Scanner scanner) {
        Map<Key, Long> cache = new HashMap<>();
        long complexity = 0;
        while (scanner.hasNextLine()) {
            complexity += findComplexity(cache, scanner.nextLine(), 3);
        }
        return complexity;
    }

    /**
     * --- Part Two ---
     * <p>
     * Just as the missing Historian is released, The Historians realize that a
     * second member of their search party has also been missing this entire time!
     * <p>
     * A quick life-form scan reveals the Historian is also trapped in a locked
     * area of the ship. Due to a variety of hazards, robots are once again
     * dispatched, forming another chain of remote control keypads managing
     * robotic-arm-wielding robots.
     * <p>
     * This time, many more robots are involved. In summary, there are the
     * following keypads:
     * <p>
     * - One directional keypad that you are using.
     * - 25 directional keypads that robots are using.
     * - One numeric keypad (on a door) that a robot is using.
     * <p>
     * The keypads form a chain, just like before: your directional keypad
     * controls a robot which is typing on a directional keypad which controls a
     * robot which is typing on a directional keypad... and so on, ending with the
     * robot which is typing on the numeric keypad.
     * <p>
     * The door codes are the same this time around; only the number of robots and
     * directional keypads has changed.
     * <p>
     * Find the fewest number of button presses you'll need to perform in order to
     * cause the robot in front of the door to type each code. What is the sum of
     * the complexities of the five codes on your list?
     */
    public static long partTwo(Scanner scanner) {
        Map<Key, Long> cache = new HashMap<>();
        long complexity = 0;
        while (scanner.hasNextLine()) {
            complexity += findComplexity(cache, scanner.nextLine(), 26);
        }
        return complexity;
    }

    private static long findComplexity(Map<Key, Long> cache, String input, int robots) {
        long length = KeyPad.NUMERIC.cheapest(cache, input, robots);
        int numericPart = Integer.parseInt(input.replace("A", ""));
        LOGGER.info("'{}' -> {} * {}", input, length, numericPart);
        return length * numericPart;
    }

    enum KeyPad {
        NUMERIC(NUMERIC_KEYPAD, Point2D.of(0, 3)),
        DIRECTIONAL(DIRECTIONAL_KEYPAD, Point2D.of(0, 0));

        final Map<Character, Point2D> keys;
        final Point2D exclusion;

        KeyPad(Map<Character, Point2D> keys, Point2D exclusion) {
            this.keys = keys;
            this.exclusion = exclusion;
        }

        long cheapest(Map<Key, Long> cache, String presses, int robots) {
            if (robots == 0)
                return presses.length();

            long result = 0;

            Point2D current = keys.get('A');
            for (int i = 0; i < presses.length(); i++) {
                Point2D next = keys.get(presses.charAt(i));
                result += doCheapest(cache, current, next, robots);
                current = next;
            }
            return result;
        }

        private long doCheapest(Map<Key, Long> cache, Point2D current, Point2D destination, int robots) {
            Key key = new Key(current, destination, robots);
            Long value = cache.get(key);
            if (value != null) {
                return value;
            }

            long result = Long.MAX_VALUE;
            Queue<State> queue = new ArrayDeque<>();
            queue.add(new State(current, ""));
            while (!queue.isEmpty()) {
                State state = queue.poll();
                if (destination.equals(state.p())) {
                    long rec = DIRECTIONAL.cheapest(cache, state.presses + "A", robots - 1);
                    result = Math.min(result, rec);
                    continue;
                }
                if (!exclusion.equals(state.p())) {
                    if (state.y() < destination.y()) {
                        queue.add(state.move(Direction.DOWN));
                    } else if (state.y() > destination.y()) {
                        queue.add(state.move(Direction.UP));
                    }
                    if (state.x() < destination.x()) {
                        queue.add(state.move(Direction.RIGHT));
                    } else if (state.x() > destination.x()) {
                        queue.add(state.move(Direction.LEFT));
                    }
                }
            }

            cache.put(key, result);
            return result;
        }

    }

    record Key(Point2D current, Point2D destination, int robots) {
    }

    record State(Point2D p, String presses) {

        int x() {
            return p.x();
        }

        int y() {
            return p.y();
        }

        State move(Direction d) {
            return switch (d) {
                case UP -> new State(p.move(Direction.UP), presses + "^");
                case DOWN -> new State(p.move(Direction.DOWN), presses + "v");
                case LEFT -> new State(p.move(Direction.LEFT), presses + "<");
                case RIGHT -> new State(p.move(Direction.RIGHT), presses + ">");
            };
        }
    }
}
