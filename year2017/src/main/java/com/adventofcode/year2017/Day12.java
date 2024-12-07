package com.adventofcode.year2017;

import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayPriorityQueue;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public final class Day12 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12.class);

    private Day12() {
        // No-Op
    }

    /**
     * --- Day 12: Digital Plumber ---
     * <p>
     * Walking along the memory banks of the stream, you find a small village that
     * is experiencing a little confusion: some programs can't communicate with
     * each other.
     * <p>
     * Programs in this village communicate using a fixed system of pipes.
     * Messages are passed between programs using these pipes, but most programs
     * aren't connected to each other directly. Instead, programs pass messages
     * between each other until the message reaches the intended recipient.
     * <p>
     * For some reason, though, some of these messages aren't ever reaching their
     * intended recipient, and the programs suspect that some pipes are missing.
     * They would like you to investigate.
     * <p>
     * You walk through the village and record the ID of each program and the IDs
     * with which it can communicate directly (your puzzle input). Each program
     * has one or more programs with which it can communicate, and these pipes are
     * bidirectional; if 8 says it can communicate with 11, then 11 will say it
     * can communicate with 8.
     * <p>
     * You need to figure out how many programs are in the group that contains
     * program ID 0.
     * <p>
     * For example, suppose you go door-to-door like a travelling salesman and
     * record the following list:
     * <p>
     * 0 <-> 2
     * 1 <-> 1
     * 2 <-> 0, 3, 4
     * 3 <-> 2, 4
     * 4 <-> 2, 3, 6
     * 5 <-> 6
     * 6 <-> 4, 5
     * <p>
     * In this example, the following programs are in the group that contains
     * program ID 0:
     * <p>
     * - Program 0 by definition.
     * - Program 2, directly connected to program 0.
     * - Program 3 via program 2.
     * - Program 4 via program 2.
     * - Program 5 via programs 6, then 4, then 2.
     * - Program 6 via programs 4, then 2.
     * <p>
     * Therefore, a total of 6 programs are in this group; all but program 1,
     * which has a pipe that connects it to itself.
     * <p>
     * How many programs are in the group that contains program ID 0?
     * <p>
     * Your puzzle answer was 130.
     */
    static IntSet digitalPlumberPartOne(Scanner scanner) {
        Int2ObjectMap<IntList> programs = readPrograms(scanner);
        return findGroup(programs, 0);
    }

    private static Int2ObjectMap<IntList> readPrograms(Scanner scanner) {
        Int2ObjectMap<IntList> programs = new Int2ObjectOpenHashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            List<String> split = Splitter.on(" <-> ").splitToList(line);
            int left = Integer.parseInt(split.get(0));
            int[] array = Splitter.on(", ").splitToStream(split.get(1)).mapToInt(Integer::parseInt).toArray();
            for (int i : array) {
                programs.computeIfAbsent(left, k -> new IntArrayList()).add(i);
            }
        }

        LOGGER.info("Programs: {}", programs);
        return programs;
    }

    private static IntSet findGroup(Int2ObjectMap<IntList> programs, int group) {
        IntSet visited = new IntOpenHashSet();
        IntPriorityQueue queue = new IntArrayPriorityQueue();
        queue.enqueue(group);

        while (!queue.isEmpty()) {
            int program = queue.dequeueInt();
            if (visited.add(program)) {
                IntList list = programs.get(program);
                if (list != null) {
                    list.forEach(queue::enqueue);
                }
            }
        }

        LOGGER.info("Visited: {}", visited);
        return visited;
    }

    /**
     * --- Part Two ---
     * <p>
     * There are more programs than just the ones in the group containing program
     * ID 0. The rest of them have no way of reaching that group, and still might
     * have no way of reaching each other.
     * <p>
     * A group is a collection of programs that can all communicate via pipes
     * either directly or indirectly. The programs you identified just a moment
     * ago are all part of the same group. Now, they would like you to determine
     * the total number of groups.
     * <p>
     * In the example above, there were 2 groups: one consisting of programs
     * 0,2,3,4,5,6, and the other consisting solely of program 1.
     * <p>
     * How many groups are there in total?
     * <p>
     * Your puzzle answer was 189.
     */
    static IntSet digitalPlumberPartTwo(Scanner scanner) {
        Int2ObjectMap<IntList> programs = readPrograms(scanner);
        IntSet groups = new IntOpenHashSet();
        IntSet keys = new IntOpenHashSet(programs.keySet());
        while (!keys.isEmpty()) {
            int first = keys.intStream().findFirst().orElseThrow();
            IntSet group = findGroup(programs, first);
            groups.add(first);
            keys.removeAll(group);
        }

        LOGGER.info("Groups: {}", groups);
        return groups;
    }
}
