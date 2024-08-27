package com.adventofcode.year2022;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Day17 {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Day17.class);

    private static final List<Rock> ROCKS = List.of(
            new Rock(IntList.of(
                    0b0011110), 0),
            new Rock(IntList.of(
                    0b0001000,
                    0b0011100,
                    0b0001000), 1),
            new Rock(IntList.of(
                    0b0000100,
                    0b0000100,
                    0b0011100), 2),
            new Rock(IntList.of(
                    0b0010000,
                    0b0010000,
                    0b0010000,
                    0b0010000), 3),
            new Rock(IntList.of(
                    0b0011000,
                    0b0011000), 4)
    );

    /**
     * --- Day 17: Pyroclastic Flow ---
     *
     * Your handheld device has located an alternative exit from the cave for you
     * and the elephants. The ground is rumbling almost continuously now, but the
     * strange valves bought you some time. It's definitely getting warmer in
     * here, though.
     *
     * The tunnels eventually open into a very tall, narrow chamber. Large, oddly-
     * shaped rocks are falling into the chamber from above, presumably due to all
     * the rumbling. If you can't work out where the rocks will fall next, you
     * might be crushed!
     *
     * The five types of rocks have the following peculiar shapes, where # is rock
     * and . is empty space:
     *
     * ####
     *
     * .#.
     * ###
     * .#.
     *
     * ..#
     * ..#
     * ###
     *
     * #
     * #
     * #
     * #
     *
     * ##
     * ##
     *
     * The rocks fall in the order shown above: first the - shape, then the +
     * shape, and so on. Once the end of the list is reached, the same order
     * repeats: the - shape falls first, sixth, 11th, 16th, etc.
     *
     * The rocks don't spin, but they do get pushed around by jets of hot gas
     * coming out of the walls themselves. A quick scan reveals the effect the
     * jets of hot gas will have on the rocks as they fall (your puzzle input).
     *
     * For example, suppose this was the jet pattern in your cave:
     *
     * >>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
     *
     * In jet patterns, < means a push to the left, while > means a push to the
     * right. The pattern above means that the jets will push a falling rock
     * right, then right, then right, then left, then left, then right, and so on.
     * If the end of the list is reached, it repeats.
     *
     * The tall, vertical chamber is exactly seven units wide. Each rock appears
     * so that its left edge is two units away from the left wall and its bottom
     * edge is three units above the highest rock in the room (or the floor, if
     * there isn't one).
     *
     * After a rock appears, it alternates between being pushed by a jet of hot
     * gas one unit (in the direction indicated by the next symbol in the jet
     * pattern) and then falling one unit down. If any movement would cause any
     * part of the rock to move into the walls, floor, or a stopped rock, the
     * movement instead does not occur. If a downward movement would have caused a
     * falling rock to move into the floor or an already-fallen rock, the falling
     * rock stops where it is (having landed on something) and a new rock
     * immediately begins falling.
     *
     * Drawing falling rocks with @ and stopped rocks with #, the jet pattern in
     * the example above manifests as follows:
     *
     * The first rock begins falling:
     * |..@@@@.|
     * |.......|
     * |.......|
     * |.......|
     * +-------+
     *
     * Jet of gas pushes rock right:
     * |...@@@@|
     * |.......|
     * |.......|
     * |.......|
     * +-------+
     *
     * Rock falls 1 unit:
     * |...@@@@|
     * |.......|
     * |.......|
     * +-------+
     *
     * Jet of gas pushes rock right, but nothing happens:
     * |...@@@@|
     * |.......|
     * |.......|
     * +-------+
     *
     * Rock falls 1 unit:
     * |...@@@@|
     * |.......|
     * +-------+
     *
     * Jet of gas pushes rock right, but nothing happens:
     * |...@@@@|
     * |.......|
     * +-------+
     *
     * Rock falls 1 unit:
     * |...@@@@|
     * +-------+
     *
     * Jet of gas pushes rock left:
     * |..@@@@.|
     * +-------+
     *
     * Rock falls 1 unit, causing it to come to rest:
     * |..####.|
     * +-------+
     *
     * A new rock begins falling:
     * |...@...|
     * |..@@@..|
     * |...@...|
     * |.......|
     * |.......|
     * |.......|
     * |..####.|
     * +-------+
     *
     * Jet of gas pushes rock left:
     * |..@....|
     * |.@@@...|
     * |..@....|
     * |.......|
     * |.......|
     * |.......|
     * |..####.|
     * +-------+
     *
     * Rock falls 1 unit:
     * |..@....|
     * |.@@@...|
     * |..@....|
     * |.......|
     * |.......|
     * |..####.|
     * +-------+
     *
     * Jet of gas pushes rock right:
     * |...@...|
     * |..@@@..|
     * |...@...|
     * |.......|
     * |.......|
     * |..####.|
     * +-------+
     *
     * Rock falls 1 unit:
     * |...@...|
     * |..@@@..|
     * |...@...|
     * |.......|
     * |..####.|
     * +-------+
     *
     * Jet of gas pushes rock left:
     * |..@....|
     * |.@@@...|
     * |..@....|
     * |.......|
     * |..####.|
     * +-------+
     *
     * Rock falls 1 unit:
     * |..@....|
     * |.@@@...|
     * |..@....|
     * |..####.|
     * +-------+
     *
     * Jet of gas pushes rock right:
     * |...@...|
     * |..@@@..|
     * |...@...|
     * |..####.|
     * +-------+
     *
     * Rock falls 1 unit, causing it to come to rest:
     * |...#...|
     * |..###..|
     * |...#...|
     * |..####.|
     * +-------+
     *
     * A new rock begins falling:
     * |....@..|
     * |....@..|
     * |..@@@..|
     * |.......|
     * |.......|
     * |.......|
     * |...#...|
     * |..###..|
     * |...#...|
     * |..####.|
     * +-------+
     *
     * The moment each of the next few rocks begins falling, you would see this:
     *
     * |..@....|
     * |..@....|
     * |..@....|
     * |..@....|
     * |.......|
     * |.......|
     * |.......|
     * |..#....|
     * |..#....|
     * |####...|
     * |..###..|
     * |...#...|
     * |..####.|
     * +-------+
     *
     * |..@@...|
     * |..@@...|
     * |.......|
     * |.......|
     * |.......|
     * |....#..|
     * |..#.#..|
     * |..#.#..|
     * |#####..|
     * |..###..|
     * |...#...|
     * |..####.|
     * +-------+
     *
     * |..@@@@.|
     * |.......|
     * |.......|
     * |.......|
     * |....##.|
     * |....##.|
     * |....#..|
     * |..#.#..|
     * |..#.#..|
     * |#####..|
     * |..###..|
     * |...#...|
     * |..####.|
     * +-------+
     *
     * |...@...|
     * |..@@@..|
     * |...@...|
     * |.......|
     * |.......|
     * |.......|
     * |.####..|
     * |....##.|
     * |....##.|
     * |....#..|
     * |..#.#..|
     * |..#.#..|
     * |#####..|
     * |..###..|
     * |...#...|
     * |..####.|
     * +-------+
     *
     * |....@..|
     * |....@..|
     * |..@@@..|
     * |.......|
     * |.......|
     * |.......|
     * |..#....|
     * |.###...|
     * |..#....|
     * |.####..|
     * |....##.|
     * |....##.|
     * |....#..|
     * |..#.#..|
     * |..#.#..|
     * |#####..|
     * |..###..|
     * |...#...|
     * |..####.|
     * +-------+
     *
     * |..@....|
     * |..@....|
     * |..@....|
     * |..@....|
     * |.......|
     * |.......|
     * |.......|
     * |.....#.|
     * |.....#.|
     * |..####.|
     * |.###...|
     * |..#....|
     * |.####..|
     * |....##.|
     * |....##.|
     * |....#..|
     * |..#.#..|
     * |..#.#..|
     * |#####..|
     * |..###..|
     * |...#...|
     * |..####.|
     * +-------+
     *
     * |..@@...|
     * |..@@...|
     * |.......|
     * |.......|
     * |.......|
     * |....#..|
     * |....#..|
     * |....##.|
     * |....##.|
     * |..####.|
     * |.###...|
     * |..#....|
     * |.####..|
     * |....##.|
     * |....##.|
     * |....#..|
     * |..#.#..|
     * |..#.#..|
     * |#####..|
     * |..###..|
     * |...#...|
     * |..####.|
     * +-------+
     *
     * |..@@@@.|
     * |.......|
     * |.......|
     * |.......|
     * |....#..|
     * |....#..|
     * |....##.|
     * |##..##.|
     * |######.|
     * |.###...|
     * |..#....|
     * |.####..|
     * |....##.|
     * |....##.|
     * |....#..|
     * |..#.#..|
     * |..#.#..|
     * |#####..|
     * |..###..|
     * |...#...|
     * |..####.|
     * +-------+
     *
     * To prove to the elephants your simulation is accurate, they want to know
     * how tall the tower will get after 2022 rocks have stopped (but before the
     * 2023rd rock begins falling). In this example, the tower of rocks will be
     * 3068 units tall.
     *
     * How many units tall will the tower of rocks be after 2022 rocks have
     * stopped falling?
     *
     * --- Part Two ---
     *
     * The elephants are not impressed by your simulation. They demand to know how
     * tall the tower will be after 1000000000000 rocks have stopped! Only then
     * will they feel confident enough to proceed through the cave.
     *
     * In the example above, the tower would be 1514285714288 units tall!
     *
     * How tall will the tower be after 1000000000000 rocks have stopped?
     */
    public static long pyroclasticFlow(String input, long turns, boolean debug) {
        IntList pile = new IntArrayList(new int[10000]);
        int top = pile.size();

        List<Jet> jets = readInput(input);
        CyclicIterator<Rock> rockIterator = new CyclicIterator<>(ROCKS);
        CyclicIterator<Jet> jetIterator = new CyclicIterator<>(jets);

        Map<State, Previous> states = new HashMap<>();
        Jet jet;
        for (long turn = 0; turn < turns; ++turn) {
            Rock rock = rockIterator.next();
            int count = top - rock.length() - 3;
            for (int y = count; ; ++y) {
                jet = jetIterator.next();
                if (rock.shiftable(jet)) {
                    Rock shifted = rock.shift(jet);
                    if (!shifted.overlaps(pile.subList(y, pile.size()))) {
                        rock = shifted;
                    }
                }
                if (rock.length() + y >= pile.size()
                    || rock.overlaps(pile.subList(y + 1, pile.size()))) {
                    for (int i = 0; i < rock.shape.size(); i++) {
                        pile.set(y + i, pile.getInt(y + i) | rock.shape.getInt(i));
                    }
                    top = Math.min(top, y);
                    break;
                }
            }
            if (debug) {
                printPile(turn, pile);
            }

            int height = pile.size() - top;

            State state = new State(jet.index, rock.index, rock.position);
            Previous previous = states.get(state);
            if (previous != null) {
                long turnCycle = turn - previous.turn;
                long heightCycle = height - previous.height;
                long diff = turns - turn - 1;
                if (diff % turnCycle == 0) {
                    return heightCycle * (diff / turnCycle) + height;
                }
            } else {
                states.put(state, new Previous(turn, height));
            }
        }

        return pile.size() - top;
    }

    private static List<Jet> readInput(String input) {
        List<Jet> jets = new ArrayList<>(input.length());
        for (int i = 0; i < input.length(); ++i) {
            char direction = input.charAt(i);
            Shift shift = switch (direction) {
                case '<' -> Shift.LEFT_SHIFT;
                case '>' -> Shift.RIGHT_SHIFT;
                default -> throw new IllegalStateException("Unknown direction : '" + direction + "'");
            };
            jets.add(new Jet(shift, i));
        }
        return jets;
    }

    private static void printPile(long turn, IntList pile) {
        StringBuilder stringBuilder = new StringBuilder();
        int mask = 0b10000000;
        boolean found = false;
        for (int line : pile) {
            if (line != 0) {
                found = true;
            }
            if (found) {
                String binaryString = Integer.toBinaryString(mask | line);
                binaryString = binaryString.replaceFirst("1", "|");
                stringBuilder.append(binaryString).append("|").append(System.lineSeparator());
            }
        }
        stringBuilder.append("+-------+");
        LOGGER.info("Pile after {} turn(s) is:\n{}", turn + 1, stringBuilder);
    }

    enum Shift {
        LEFT_SHIFT(0b1000000) {
            @Override
            int shift(int line) {
                return line << 1;
            }
        },
        RIGHT_SHIFT(0b0000001) {
            @Override
            int shift(int line) {
                return line >> 1;
            }
        };

        private final int edge;

        abstract int shift(int line);

        boolean shiftable(int line) {
            return (line & edge) == 0;
        }

        Shift(int edge) {
            this.edge = edge;
        }
    }

    record State(int jet, int rock, int position) {
    }

    record Previous(long turn, long height) {
    }

    record Jet(Shift shift, int index) {
    }

    record Rock(IntList shape, int index, int position) {
        Rock(IntList shape, int index) {
            this(shape, index, 0b0010000);
        }

        int length() {
            return shape.size();
        }

        boolean shiftable(Jet jet) {
            return shape.intStream().allMatch(jet.shift::shiftable);
        }

        Rock shift(Jet jet) {
            Shift shift = jet.shift;
            return new Rock(
                    IntArrayList.toList(shape.intStream().map(shift::shift)),
                    index,
                    shift.shift(position)
            );
        }

        boolean overlaps(IntList pile) {
            IntListIterator iterator1 = shape.iterator();
            IntListIterator iterator2 = pile.iterator();
            while (iterator1.hasNext() && iterator2.hasNext()) {
                if ((iterator1.nextInt() & iterator2.nextInt()) != 0) {
                    return true;
                }
            }

            return false;
        }
    }

    private static final class CyclicIterator<T> implements Iterator<T> {
        private int position;
        private final int size;
        private final List<T> list;

        CyclicIterator(List<T> list) {
            this.list = list;
            this.size = list.size();
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public T next() {
            return list.get((position++) % size);
        }
    }
}
