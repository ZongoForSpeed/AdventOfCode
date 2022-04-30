package com.adventofcode.year2019;

import com.adventofcode.Intcode;
import com.adventofcode.point.Direction;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day25 {
    /**
     * --- Day 25: Cryostasis ---
     * As you approach Santa's ship, your sensors report two important details:
     *
     * First, that you might be too late: the internal temperature is -40 degrees.
     *
     * Second, that one faint life signature is somewhere on the ship.
     *
     * The airlock door is locked with a code; your best option is to send in a small droid to investigate the situation.
     * You attach your ship to Santa's, break a small hole in the hull, and let the droid run in before you seal it up
     * again. Before your ship starts freezing, you detach your ship and set it to automatically stay within range of
     * Santa's ship.
     *
     * This droid can follow basic instructions and report on its surroundings; you can communicate with it through an
     * Intcode program (your puzzle input) running on an ASCII-capable computer.
     *
     * As the droid moves through its environment, it will describe what it encounters. When it says Command?, you can
     * give it a single instruction terminated with a newline (ASCII code 10). Possible instructions are:
     *
     * Movement via north, south, east, or west.
     * To take an item the droid sees in the environment, use the command take <name of item>. For example, if the droid
     * reports seeing a red ball, you can pick it up with take red ball.
     * To drop an item the droid is carrying, use the command drop <name of item>. For example, if the droid is carrying
     * a green ball, you can drop it with drop green ball.
     * To get a list of all of the items the droid is currently carrying, use the command inv (for "inventory").
     * Extra spaces or other characters aren't allowed - instructions must be provided precisely.
     *
     * Santa's ship is a Reindeer-class starship; these ships use pressure-sensitive floors to determine the identity of
     * droids and crew members. The standard configuration for these starships is for all droids to weigh exactly the
     * same amount to make them easier to detect. If you need to get past such a sensor, you might be able to reach the
     * correct weight by carrying items from the environment.
     *
     * Look around the ship and see if you can find the password for the main airlock.
     */
    static String findPassword(String program) {
        Droid droid = new Droid(Set.of("molten lava", "infinite loop", "giant electromagnet", "photons", "escape pod"));
        Position position = droid.start(program);
        Deque<Direction> path = new LinkedList<>();
        for (Direction direction : position.directions()) {
            droid.walk(position, direction, path);
        }
        List<Direction> directions = droid.paths.get("Security Checkpoint");
        for (Direction direction : directions) {
            droid.doCommand(direction.name().toLowerCase());
        }

        List<String> items = new ArrayList<>(droid.items);
        for (String item : items) {
            droid.doCommand("drop " + item);
        }

        for (long l = 0; l < (1L << items.size()); l++) {
            BitSet bitSet = BitSet.valueOf(new long[]{l});
            List<String> localItems = IntStream.range(0, items.size()).filter(bitSet::get).mapToObj(items::get).toList();
            localItems.forEach(i -> droid.doCommand("take " + i));

            Position doCommand = droid.doCommand(droid.exit.name().toLowerCase());
            if ("Security Checkpoint".equals(doCommand.position())) {
                localItems.forEach(i -> droid.doCommand("drop " + i));
            } else {
                return doCommand.message().trim();
            }

        }

        throw new IllegalStateException("Cannot find password!");
    }

    public static class Droid {
        private final BlockingQueue<Long> instructions = new LinkedBlockingQueue<>();
        private final BlockingQueue<String> consoleOutput = new LinkedBlockingQueue<>();
        private final StringBuilder stringBuilder = new StringBuilder();
        private final ExecutorService executor;
        private final Set<String> forbiddenItems;
        private final Set<String> items = new HashSet<>();
        private final Set<Position> seenPosition = new HashSet<>();
        private final Map<String, List<Direction>> paths = new HashMap<>();
        private Direction exit;

        public Droid(Set<String> forbiddenItems) {
            this.forbiddenItems = forbiddenItems;
            executor = Executors.newSingleThreadExecutor();
        }

        private static Position parseOutput(String output) {
            if (output.contains("You take the ") || output.contains("You drop the ")) {
                return null;
            }
            int indexOf = output.indexOf("== ");
            if (indexOf == -1) {
                throw new IllegalStateException(output);
            }
            String position = null;
            String description = null;
            boolean readDoors = false;
            boolean readItems = false;
            List<Direction> directions = new ArrayList<>();
            List<String> items = new ArrayList<>();
            for (String line : output.split("\n")) {
                if (line.startsWith("== ")) {
                    position = line.replace("==", "").trim();
                } else if (line.startsWith("- ")) {
                    String trim = line.replace("- ", "").trim();
                    if (readDoors) {
                        directions.add(Direction.valueOf(trim.toUpperCase()));
                    } else if (readItems) {
                        items.add(trim);
                    }
                } else if (line.startsWith("Doors here lead:")) {
                    readDoors = true;
                    readItems = false;
                } else if (line.startsWith("Items here:")) {
                    readDoors = false;
                    readItems = true;
                } else {
                    if (position != null && description == null) {
                        description = line;
                    }
                    readDoors = false;
                    readItems = false;
                }
            }

            return new Position(position, description, directions, items, output);
        }

        public Position start(String program) {
            executor.submit(() -> {
                Intcode.intcode(program, this::input, this::output);
                consoleOutput.put(stringBuilder.toString());
                stringBuilder.setLength(0);
                return null;
            });
            return getConsoleOutput();
        }

        private long input() {
            try {
                return instructions.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }

        private void output(long c) {
            try {
                if (c < 255) {
                    stringBuilder.append((char) c);
                } else {
                    stringBuilder.append(c);
                }

                if (stringBuilder.toString().contains("Command?\n")) {
                    consoleOutput.put(stringBuilder.toString());

                    stringBuilder.setLength(0);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }

        private Position doCommand(String input) {
            input.chars().mapToLong(t -> t).forEach(instructions::add);
            instructions.add(10L);

            return getConsoleOutput();
        }

        private Position getConsoleOutput() {
            try {
                return parseOutput(consoleOutput.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }

        public void walk(Position currentPosition, Direction direction, Deque<Direction> path) {
            Position position = doCommand(direction.name().toLowerCase());
            path.addLast(direction);
            if (seenPosition.add(position)) {
                for (String item : position.items()) {
                    if (!forbiddenItems.contains(item)) {
                        items.add(item);
                        doCommand("take " + item);
                    }
                }
                for (Direction positionDirection : position.directions()) {
                    walk(position, positionDirection, path);
                }
            }


            if (!currentPosition.equals(position)) {
                paths.put(position.position, new ArrayList<>(path));
                doCommand(direction.reverse().name().toLowerCase());
            } else {
                exit = direction;
            }

            path.pollLast();
        }
    }

    private record Position(String position, String description,
                            List<Direction> directions,
                            List<String> items, String message) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position1 = (Position) o;
            return position.equals(position1.position);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("== ").append(position).append(" ==\n").append(description).append('\n');
            if (!directions.isEmpty()) {
                sb.append('\n');
                sb.append("Doors here lead:\n").append(directions.stream().map(Enum::name).map(s -> "- " + s).collect(Collectors.joining("\n")));
            }

            if (!items.isEmpty()) {
                sb.append('\n');
                sb.append("Items here:\n").append(items.stream().map(Objects::toString).map(s -> "- " + s).collect(Collectors.joining("\n")));
            }

            sb.append('\n');

            return sb.toString();
        }
    }
}
