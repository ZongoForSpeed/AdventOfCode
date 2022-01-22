package com.adventofcode.year2017;

import com.adventofcode.utils.IntegerPair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day24 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24.class);

    private Day24() {
        // No-Op
    }

    public static long strength(List<IntegerPair> bridge) {
        return bridge.stream().mapToLong(c -> c.left() + c.right()).sum();
    }

    private static void findBridges(int current, Int2ObjectMap<IntList> components, List<IntegerPair> bridge, Set<List<IntegerPair>> out) {
        IntList outPorts = components.get(current);
        if (outPorts == null) {
            return;
        }

        for (int port : outPorts) {
            IntegerPair pair = current < port ? IntegerPair.of(current, port) : IntegerPair.of(port, current);
            if (bridge.contains(pair)) {
                continue;
            }

            List<IntegerPair> newBridge = new ArrayList<>(bridge);
            newBridge.add(pair);

            if (out.add(newBridge)) {
                findBridges(port, components, newBridge, out);
            }
        }

    }

    private static Set<List<IntegerPair>> findAllBridges(Scanner scanner) {
        Int2ObjectMap<IntList> components = new Int2ObjectOpenHashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int[] array = Arrays.stream(line.split("/")).mapToInt(Integer::parseInt).toArray();
            components.computeIfAbsent(array[0], ignore -> new IntArrayList()).add(array[1]);
            components.computeIfAbsent(array[1], ignore -> new IntArrayList()).add(array[0]);
        }

        LOGGER.info("components: {}", components);
        Set<List<IntegerPair>> allBridges = new HashSet<>();
        findBridges(0, components, List.of(), allBridges);

        LOGGER.info("bridges: {}", allBridges.size());
        LOGGER.trace("bridges: {}", allBridges);
        return allBridges;
    }

    /**
     * --- Day 24: Electromagnetic Moat ---
     *
     * The CPU itself is a large, black building surrounded by a bottomless pit.
     * Enormous metal tubes extend outward from the side of the building at
     * regular intervals and descend down into the void. There's no way to cross,
     * but you need to get inside.
     *
     * No way, of course, other than building a bridge out of the magnetic
     * components strewn about nearby.
     *
     * Each component has two ports, one on each end. The ports come in all
     * different types, and only matching types can be connected. You take an
     * inventory of the components by their port types (your puzzle input). Each
     * port is identified by the number of pins it uses; more pins mean a stronger
     * connection for your bridge. A 3/7 component, for example, has a type-3 port
     * on one side, and a type-7 port on the other.
     *
     * Your side of the pit is metallic; a perfect surface to connect a magnetic,
     * zero-pin port. Because of this, the first port you use must be of type 0.
     * It doesn't matter what type of port you end with; your goal is just to make
     * the bridge as strong as possible.
     *
     * The strength of a bridge is the sum of the port types in each component.
     * For example, if your bridge is made of components 0/3, 3/7, and 7/4, your
     * bridge has a strength of 0+3 + 3+7 + 7+4 = 24.
     *
     * For example, suppose you had the following components:
     *
     * 0/2
     * 2/2
     * 2/3
     * 3/4
     * 3/5
     * 0/1
     * 10/1
     * 9/10
     *
     * With them, you could make the following valid bridges:
     *
     *   - 0/1
     *   - 0/1--10/1
     *   - 0/1--10/1--9/10
     *   - 0/2
     *   - 0/2--2/3
     *   - 0/2--2/3--3/4
     *   - 0/2--2/3--3/5
     *   - 0/2--2/2
     *   - 0/2--2/2--2/3
     *   - 0/2--2/2--2/3--3/4
     *   - 0/2--2/2--2/3--3/5
     *
     * (Note how, as shown by 10/1, order of ports within a component doesn't
     * matter. However, you may only use each port on a component once.)
     *
     * Of these bridges, the strongest one is 0/1--10/1--9/10; it has a strength
     * of 0+1 + 1+10 + 10+9 = 31.
     *
     * What is the strength of the strongest bridge you can make with the
     * components you have available?
     *
     * Your puzzle answer was 2006.
     */
    public static List<IntegerPair> findStrongestBridgePartOne(Scanner scanner) {
        Set<List<IntegerPair>> allBridges = findAllBridges(scanner);

        List<IntegerPair> strongestBridge = allBridges.stream().max(Comparator.comparingLong(Day24::strength)).orElseThrow();

        LOGGER.info("strongestBridge: {}", strongestBridge);
        return strongestBridge;
    }

    /**
     * --- Part Two ---
     *
     * The bridge you've built isn't long enough; you can't jump the rest of the
     * way.
     *
     * In the example above, there are two longest bridges:
     *
     *   - 0/2--2/2--2/3--3/4
     *   - 0/2--2/2--2/3--3/5
     *
     * Of them, the one which uses the 3/5 component is stronger; its strength is
     * 0+2 + 2+2 + 2+3 + 3+5 = 19.
     *
     * What is the strength of the longest bridge you can make? If you can make
     * multiple bridges of the longest length, pick the strongest one.
     *
     * Your puzzle answer was 1994.
     */
    public static List<IntegerPair> findStrongestBridgePartTwo(Scanner scanner) {
        Set<List<IntegerPair>> allBridges = findAllBridges(scanner);

        Comparator<List<IntegerPair>> comparator = Comparator.comparingInt(List::size);
        comparator = comparator.thenComparingLong(Day24::strength);

        List<IntegerPair> strongestBridge = allBridges.stream().max(comparator).orElseThrow();

        LOGGER.info("strongestBridge: {}", strongestBridge);
        return strongestBridge;
    }
}
