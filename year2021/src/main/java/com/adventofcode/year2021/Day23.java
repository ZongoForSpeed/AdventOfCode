package com.adventofcode.year2021;

import com.adventofcode.common.graph.AStar;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class Day23 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23.class);
    private static final IntSet ENTRIES = IntSet.of(2, 4, 6, 8);

    private static List<AStar.Move<State>> getPossibleMoves(Map<State, List<AStar.Move<State>>> cache, int size, State state) {
        List<AStar.Move<State>> moves = cache.get(state);
        if (moves != null) {
            return moves;
        }
        moves = new ArrayList<>();
        String hallway = state.hallway();
        for (int i : ENTRIES) {
            String hole = state.get(i / 2 - 1);
            if (StringUtils.isBlank(hole)) {
                continue;
            }
            hole = hole.trim();
            char amphipod = hole.charAt(0);
            String newHole = StringUtils.leftPad(hole.substring(1), size);
            int holeLength = hole.length() - 1;
            for (int target : state.availablePositions(i)) {
                if (ENTRIES.contains(target)) {
                    continue;
                }
                char[] data = hallway.toCharArray();
                data[target] = amphipod;
                State next = state.next(data, i / 2 - 1, newHole);
                long cost = (long) Math.abs(target - i) + (size - holeLength);
                moves.add(AStar.Move.of(next, cost * computeCost(amphipod)));
            }
        }

        for (int position = 0; position < hallway.length(); position++) {
            char amphipod = hallway.charAt(position);
            if (amphipod < 'A' || amphipod > 'D') {
                continue;
            }

            int holeNumber = amphipod - 'A';

            if (state.availablePositions(position)
                    .intStream()
                    .filter(ENTRIES::contains)
                    .map(j -> j / 2 - 1)
                    .noneMatch(j -> j == holeNumber)) {
                continue;
            }
            String hole = state.get(holeNumber).trim();
            if (!StringUtils.containsOnly(hole, amphipod)) {
                continue;
            }

            char[] data = hallway.toCharArray();
            data[position] = ' ';
            String newHole = StringUtils.leftPad(amphipod + hole, size);
            State next = state.next(data, holeNumber, newHole);
            long cost = (long) Math.abs(position - (holeNumber + 1) * 2) + (size - hole.length());
            moves.add(AStar.Move.of(next, cost * computeCost(amphipod)));
        }

        cache.put(state, moves);
        return moves;
    }

    private static long computeCost(char c) {
        return switch (c) {
            case 'A' -> 1;
            case 'B' -> 10;
            case 'C' -> 100;
            case 'D' -> 1000;
            default -> throw new IllegalStateException("Invalid cost " + c);
        };
    }

    private static long computeCost(State start, State end, int size) {
        Map<State, List<AStar.Move<State>>> cache = new HashMap<>();
        return AStar.algorithm(state -> getPossibleMoves(cache, size, state), start, end);
    }

    private static String readInput(Scanner scanner) {
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c >= 'A' && c <= 'D') {
                    sb.append(c);
                }
            }
        }
        LOGGER.info("{}", sb);
        return sb.toString();
    }

    /**
     * --- Day 23: Amphipod ---
     * <p>
     * A group of amphipods notice your fancy submarine and flag you down. "With
     * such an impressive shell," one amphipod says, "surely you can help us with
     * a question that has stumped our best scientists."
     * <p>
     * They go on to explain that a group of timid, stubborn amphipods live in a
     * nearby burrow. Four types of amphipods live there: Amber (A), Bronze (B),
     * Copper (C), and Desert (D). They live in a burrow that consists of a
     * hallway and four side rooms. The side rooms are initially full of
     * amphipods, and the hallway is initially empty.
     * <p>
     * They give you a diagram of the situation (your puzzle input), including
     * locations of each amphipod (A, B, C, or D, each of which is occupying an
     * otherwise open space), walls (#), and open space (.).
     * <p>
     * For example:
     * <p>
     * #############
     * #...........#
     * ###B#C#B#D###
     * #A#D#C#A#
     * #########
     * <p>
     * The amphipods would like a method to organize every amphipod into side
     * rooms so that each side room contains one type of amphipod and the types
     * are sorted A-D going left to right, like this:
     * <p>
     * #############
     * #...........#
     * ###A#B#C#D###
     * #A#B#C#D#
     * #########
     * <p>
     * Amphipods can move up, down, left, or right so long as they are moving into
     * an unoccupied open space. Each type of amphipod requires a different amount
     * of energy to move one step: Amber amphipods require 1 energy per step,
     * Bronze amphipods require 10 energy, Copper amphipods require 100, and
     * Desert ones require 1000. The amphipods would like you to find a way to
     * organize the amphipods that requires the least total energy.
     * <p>
     * However, because they are timid and stubborn, the amphipods have some extra
     * rules:
     * <p>
     * - Amphipods will never stop on the space immediately outside any room.
     * They can move into that space so long as they immediately continue
     * moving. (Specifically, this refers to the four open spaces in the
     * hallway that are directly above an amphipod starting position.)
     * - Amphipods will never move from the hallway into a room unless that
     * room is their destination room and that room contains no amphipods
     * which do not also have that room as their own destination. If an
     * amphipod's starting room is not its destination room, it can stay in
     * that room until it leaves the room. (For example, an Amber amphipod
     * will not move from the hallway into the right three rooms, and will
     * only move into the leftmost room if that room is empty or if it only
     * contains other Amber amphipods.)
     * - Once an amphipod stops moving in the hallway, it will stay in that
     * spot until it can move into a room. (That is, once any amphipod starts
     * moving, any other amphipods currently in the hallway are locked in
     * place and will not move again until they can move fully into a room.)
     * <p>
     * In the above example, the amphipods can be organized using a minimum of 12521 energy. One way to do this is shown below.
     * <p>
     * Starting configuration:
     * <p>
     * #############
     * #...........#
     * ###B#C#B#D###
     * #A#D#C#A#
     * #########
     * <p>
     * One Bronze amphipod moves into the hallway, taking 4 steps and using 40
     * energy:
     * <p>
     * #############
     * #...B.......#
     * ###B#C#.#D###
     * #A#D#C#A#
     * #########
     * <p>
     * The only Copper amphipod not in its side room moves there, taking 4 steps
     * and using 400 energy:
     * <p>
     * #############
     * #...B.......#
     * ###B#.#C#D###
     * #A#D#C#A#
     * #########
     * <p>
     * A Desert amphipod moves out of the way, taking 3 steps and using 3000
     * energy, and then the Bronze amphipod takes its place, taking 3 steps and
     * using 30 energy:
     * <p>
     * #############
     * #.....D.....#
     * ###B#.#C#D###
     * #A#B#C#A#
     * #########
     * <p>
     * The leftmost Bronze amphipod moves to its room using 40 energy:
     * <p>
     * #############
     * #.....D.....#
     * ###.#B#C#D###
     * #A#B#C#A#
     * #########
     * <p>
     * Both amphipods in the rightmost room move into the hallway, using 2003
     * energy in total:
     * <p>
     * #############
     * #.....D.D.A.#
     * ###.#B#C#.###
     * #A#B#C#.#
     * #########
     * <p>
     * Both Desert amphipods move into the rightmost room using 7000 energy:
     * <p>
     * #############
     * #.........A.#
     * ###.#B#C#D###
     * #A#B#C#D#
     * #########
     * <p>
     * Finally, the last Amber amphipod moves into its room, using 8 energy:
     * <p>
     * #############
     * #...........#
     * ###A#B#C#D###
     * #A#B#C#D#
     * #########
     * <p>
     * What is the least energy required to organize the amphipods?
     * <p>
     * Your puzzle answer was 15111.
     */
    static long computeCostPartOne(Scanner scanner) {
        String input = readInput(scanner);
        String a = "" + input.charAt(0) + input.charAt(4);
        String b = "" + input.charAt(1) + input.charAt(5);
        String c = "" + input.charAt(2) + input.charAt(6);
        String d = "" + input.charAt(3) + input.charAt(7);
        State start = State.of(a, b, c, d);
        State end = State.of("AA", "BB", "CC", "DD");
        long cost = computeCost(start, end, 2);
        LOGGER.info("Cost {}", cost);
        return cost;
    }

    /**
     * --- Part Two ---
     * As you prepare to give the amphipods your solution, you notice that the
     * diagram they handed you was actually folded up. As you unfold it, you
     * discover an extra part of the diagram.
     * <p>
     * Between the first and second lines of text that contain amphipod starting
     * positions, insert the following lines:
     * <p>
     * #D#C#B#A#
     * #D#B#A#C#
     * <p>
     * So, the above example now becomes:
     * <p>
     * #############
     * #...........#
     * ###B#C#B#D###
     * #D#C#B#A#
     * #D#B#A#C#
     * #A#D#C#A#
     * #########
     * <p>
     * The amphipods still want to be organized into rooms similar to before:
     * <p>
     * #############
     * #...........#
     * ###A#B#C#D###
     * #A#B#C#D#
     * #A#B#C#D#
     * #A#B#C#D#
     * #########
     * <p>
     * In this updated example, the least energy required to organize these
     * amphipods is 44169:
     * <p>
     * #############
     * #...........#
     * ###B#C#B#D###
     * #D#C#B#A#
     * #D#B#A#C#
     * #A#D#C#A#
     * #########
     * <p>
     * #############
     * #..........D#
     * ###B#C#B#.###
     * #D#C#B#A#
     * #D#B#A#C#
     * #A#D#C#A#
     * #########
     * <p>
     * #############
     * #A.........D#
     * ###B#C#B#.###
     * #D#C#B#.#
     * #D#B#A#C#
     * #A#D#C#A#
     * #########
     * <p>
     * #############
     * #A........BD#
     * ###B#C#.#.###
     * #D#C#B#.#
     * #D#B#A#C#
     * #A#D#C#A#
     * #########
     * <p>
     * #############
     * #A......B.BD#
     * ###B#C#.#.###
     * #D#C#.#.#
     * #D#B#A#C#
     * #A#D#C#A#
     * #########
     * <p>
     * #############
     * #AA.....B.BD#
     * ###B#C#.#.###
     * #D#C#.#.#
     * #D#B#.#C#
     * #A#D#C#A#
     * #########
     * <p>
     * #############
     * #AA.....B.BD#
     * ###B#.#.#.###
     * #D#C#.#.#
     * #D#B#C#C#
     * #A#D#C#A#
     * #########
     * <p>
     * #############
     * #AA.....B.BD#
     * ###B#.#.#.###
     * #D#.#C#.#
     * #D#B#C#C#
     * #A#D#C#A#
     * #########
     * <p>
     * #############
     * #AA...B.B.BD#
     * ###B#.#.#.###
     * #D#.#C#.#
     * #D#.#C#C#
     * #A#D#C#A#
     * #########
     * <p>
     * #############
     * #AA.D.B.B.BD#
     * ###B#.#.#.###
     * #D#.#C#.#
     * #D#.#C#C#
     * #A#.#C#A#
     * #########
     * <p>
     * #############
     * #AA.D...B.BD#
     * ###B#.#.#.###
     * #D#.#C#.#
     * #D#.#C#C#
     * #A#B#C#A#
     * #########
     * <p>
     * #############
     * #AA.D.....BD#
     * ###B#.#.#.###
     * #D#.#C#.#
     * #D#B#C#C#
     * #A#B#C#A#
     * #########
     * <p>
     * #############
     * #AA.D......D#
     * ###B#.#.#.###
     * #D#B#C#.#
     * #D#B#C#C#
     * #A#B#C#A#
     * #########
     * <p>
     * #############
     * #AA.D......D#
     * ###B#.#C#.###
     * #D#B#C#.#
     * #D#B#C#.#
     * #A#B#C#A#
     * #########
     * <p>
     * #############
     * #AA.D.....AD#
     * ###B#.#C#.###
     * #D#B#C#.#
     * #D#B#C#.#
     * #A#B#C#.#
     * #########
     * <p>
     * #############
     * #AA.......AD#
     * ###B#.#C#.###
     * #D#B#C#.#
     * #D#B#C#.#
     * #A#B#C#D#
     * #########
     * <p>
     * #############
     * #AA.......AD#
     * ###.#B#C#.###
     * #D#B#C#.#
     * #D#B#C#.#
     * #A#B#C#D#
     * #########
     * <p>
     * #############
     * #AA.......AD#
     * ###.#B#C#.###
     * #.#B#C#.#
     * #D#B#C#D#
     * #A#B#C#D#
     * #########
     * <p>
     * #############
     * #AA.D.....AD#
     * ###.#B#C#.###
     * #.#B#C#.#
     * #.#B#C#D#
     * #A#B#C#D#
     * #########
     * <p>
     * #############
     * #A..D.....AD#
     * ###.#B#C#.###
     * #.#B#C#.#
     * #A#B#C#D#
     * #A#B#C#D#
     * #########
     * <p>
     * #############
     * #...D.....AD#
     * ###.#B#C#.###
     * #A#B#C#.#
     * #A#B#C#D#
     * #A#B#C#D#
     * #########
     * <p>
     * #############
     * #.........AD#
     * ###.#B#C#.###
     * #A#B#C#D#
     * #A#B#C#D#
     * #A#B#C#D#
     * #########
     * <p>
     * #############
     * #..........D#
     * ###A#B#C#.###
     * #A#B#C#D#
     * #A#B#C#D#
     * #A#B#C#D#
     * #########
     * <p>
     * #############
     * #...........#
     * ###A#B#C#D###
     * #A#B#C#D#
     * #A#B#C#D#
     * #A#B#C#D#
     * #########
     * <p>
     * Using the initial configuration from the full diagram, what is the least
     * energy required to organize the amphipods?
     * <p>
     * Your puzzle answer was 47625.
     */
    static long computeCostPartTwo(Scanner scanner) {
        String input = readInput(scanner);
        String a = input.charAt(0) + "DD" + input.charAt(4);
        String b = input.charAt(1) + "CB" + input.charAt(5);
        String c = input.charAt(2) + "BA" + input.charAt(6);
        String d = input.charAt(3) + "AC" + input.charAt(7);
        State start = State.of(a, b, c, d);
        State end = State.of("AAAA", "BBBB", "CCCC", "DDDD");
        long cost = computeCost(start, end, 4);
        LOGGER.info("Cost {}", cost);
        return cost;
    }

    record State(String hallway, String a, String b, String c, String d) {
        public static State of(String a, String b, String c, String d) {
            return new State("           ", a, b, c, d);
        }

        public State next(char[] hall, int i, String hole) {
            return new State(
                    new String(hall),
                    i == 0 ? hole : this.a,
                    i == 1 ? hole : this.b,
                    i == 2 ? hole : this.c,
                    i == 3 ? hole : this.d
            );
        }

        public IntList availablePositions(int i) {
            IntList result = new IntArrayList();
            for (int p = i - 1; p >= 0; --p) {
                if (hallway.charAt(p) == ' ') {
                    result.add(p);
                } else {
                    break;
                }
            }

            for (int p = i + 1; p < hallway.length(); ++p) {
                if (hallway.charAt(p) == ' ') {
                    result.add(p);
                } else {
                    break;
                }
            }

            return result;
        }

        public String get(int i) {
            return switch (i) {
                case 0 -> a;
                case 1 -> b;
                case 2 -> c;
                case 3 -> d;
                default -> throw new IllegalStateException("Invalid type " + i);
            };
        }
    }
}
