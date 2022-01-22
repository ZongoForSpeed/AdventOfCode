package com.adventofcode.year2017;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day25 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day25.class);
    private static final Pattern PATTERN = Pattern.compile("Perform a diagnostic checksum after (\\d*) steps.");

    private static State readState(Scanner scanner) {
        // In state A:
        //   If the current value is 0:
        //     - Write the value 1.
        //     - Move one slot to the right.
        //     - Continue with state B.
        //   If the current value is 1:
        //     - Write the value 0.
        //     - Move one slot to the left.
        //     - Continue with state B.
        char state = '\0';
        List<Character> chars = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (StringUtils.isBlank(line)) {
                break;
            }

            if (line.startsWith("In state ")) {
                state = line.charAt("In state ".length());
            }

            if (line.startsWith("  If the current value is ")) {
                char c = line.charAt("  If the current value is ".length());
                chars.add(c);
            }

            if (line.startsWith("    - Write the value ")) {
                char c = line.charAt("    - Write the value ".length());
                chars.add(c);
            }

            if (line.startsWith("    - Move one slot to the ")) {
                char c = line.charAt("    - Move one slot to the ".length());
                chars.add(c);
            }

            if (line.startsWith("    - Continue with state ")) {
                char c = line.charAt("    - Continue with state ".length());
                chars.add(c);
            }
        }

        LOGGER.info("State '{}': {}", chars, state);
        return State.of(state, chars);
    }

    /**
     * --- Day 25: The Halting Problem ---
     *
     * Following the twisty passageways deeper and deeper into the CPU, you
     * finally reach the core of the computer. Here, in the expansive central
     * chamber, you find a grand apparatus that fills the entire room, suspended
     * nanometers above your head.
     *
     * You had always imagined CPUs to be noisy, chaotic places, bustling with
     * activity. Instead, the room is quiet, motionless, and dark.
     *
     * Suddenly, you and the CPU's garbage collector startle each other. "It's not
     * often we get many visitors here!", he says. You inquire about the stopped
     * machinery.
     *
     * "It stopped milliseconds ago; not sure why. I'm a garbage collector, not a
     * doctor." You ask what the machine is for.
     *
     * "Programs these days, don't know their origins. That's the Turing machine!
     * It's what makes the whole computer work." You try to explain that Turing
     * machines are merely models of computation, but he cuts you off. "No, see,
     * that's just what they want you to think. Ultimately, inside every CPU,
     * there's a Turing machine driving the whole thing! Too bad this one's
     * broken. We're doomed!"
     *
     * You ask how you can help. "Well, unfortunately, the only way to get the
     * computer running again would be to create a whole new Turing machine from
     * scratch, but there's no way you can-" He notices the look on your face,
     * gives you a curious glance, shrugs, and goes back to sweeping the floor.
     *
     * You find the Turing machine blueprints (your puzzle input) on a tablet in a
     * nearby pile of debris. Looking back up at the broken Turing machine above,
     * you can start to identify its parts:
     *
     *   - A tape which contains 0 repeated infinitely to the left and right.
     *   - A cursor, which can move left or right along the tape and read or
     *     write values at its current position.
     *   - A set of states, each containing rules about what to do based on the
     *     current value under the cursor.
     *
     * Each slot on the tape has two possible values: 0 (the starting value for
     * all slots) and 1. Based on whether the cursor is pointing at a 0 or a 1,
     * the current state says what value to write at the current position of the
     * cursor, whether to move the cursor left or right one slot, and which state
     * to use next.
     *
     * For example, suppose you found the following blueprint:
     *
     * Begin in state A.
     * Perform a diagnostic checksum after 6 steps.
     *
     * In state A:
     *   If the current value is 0:
     *     - Write the value 1.
     *     - Move one slot to the right.
     *     - Continue with state B.
     *   If the current value is 1:
     *     - Write the value 0.
     *     - Move one slot to the left.
     *     - Continue with state B.
     *
     * In state B:
     *   If the current value is 0:
     *     - Write the value 1.
     *     - Move one slot to the left.
     *     - Continue with state A.
     *   If the current value is 1:
     *     - Write the value 1.
     *     - Move one slot to the right.
     *     - Continue with state A.
     *
     * Running it until the number of steps required to take the listed diagnostic
     * checksum would result in the following tape configurations (with the cursor
     * marked in square brackets):
     *
     * ... 0  0  0 [0] 0  0 ... (before any steps; about to run state A)
     * ... 0  0  0  1 [0] 0 ... (after 1 step;     about to run state B)
     * ... 0  0  0 [1] 1  0 ... (after 2 steps;    about to run state A)
     * ... 0  0 [0] 0  1  0 ... (after 3 steps;    about to run state B)
     * ... 0 [0] 1  0  1  0 ... (after 4 steps;    about to run state A)
     * ... 0  1 [1] 0  1  0 ... (after 5 steps;    about to run state B)
     * ... 0  1  1 [0] 1  0 ... (after 6 steps;    about to run state A)
     *
     * The CPU can confirm that the Turing machine is working by taking a
     * diagnostic checksum after a specific number of steps (given in the
     * blueprint). Once the specified number of steps have been executed, the
     * Turing machine should pause; once it does, count the number of times 1
     * appears on the tape. In the above example, the diagnostic checksum is 3.
     *
     * Recreate the Turing machine and save the computer! What is the diagnostic
     * checksum it produces once it's working again?
     */
    static long diagnosticChecksum(Scanner scanner) {
        char currentState = 'A';
        int steps = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Begin in state ")) {
                currentState = line.charAt("Begin in state ".length());
            }

            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                steps = Integer.parseInt(matcher.group(1));
            }

            if (StringUtils.isBlank(line)) {
                break;
            }
        }

        Char2ObjectMap<State> states = new Char2ObjectOpenHashMap<>();

        while (scanner.hasNextLine()) {
            State state = readState(scanner);
            states.put(state.state(), state);
        }

        LOGGER.info("States: {}", states);
        AtomicInteger position = new AtomicInteger(0);
        Int2IntMap tape = new Int2IntOpenHashMap();

        for (int step = 1; step <= steps; ++step) {
            State state = states.get(currentState);
            currentState = state.step(tape, position);

            LOGGER.trace("tape: {}, state: {}, position: {}", tape, currentState, position);
        }

        return tape.values().intStream().filter(i -> i == 1).count();
    }

    enum Move {
        LEFT,
        RIGHT
    }

    record Action(int write, Move move, char next) {
        public char step(Int2IntMap tape, AtomicInteger position) {
            int p = position.get();
            tape.put(p, write);
            if (move == Move.LEFT) {
                position.decrementAndGet();
            } else {
                position.incrementAndGet();
            }
            return next;
        }
    }

    record State(char state, Action value0, Action value1) {
        public static State of(char state, List<Character> chars) {
            Action value0 = new Action(chars.get(1) - '0', chars.get(2) == 'r' ? Move.RIGHT : Move.LEFT, chars.get(3));
            Action value1 = new Action(chars.get(1 + 4) - '0', chars.get(2 + 4) == 'r' ? Move.RIGHT : Move.LEFT, chars.get(3 + 4));
            return new State(state, value0, value1);
        }

        public char step(Int2IntMap tape, AtomicInteger position) {
            int p = position.get();
            if (tape.getOrDefault(p, 0) == 0) {
                return value0.step(tape, position);
            } else {
                return value1.step(tape, position);
            }
        }
    }
}
