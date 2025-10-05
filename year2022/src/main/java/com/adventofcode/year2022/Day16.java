package com.adventofcode.year2022;

import com.adventofcode.common.graph.Dijkstra;
import com.adventofcode.common.utils.Bits;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public final class Day16 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day16.class);
    private static final Pattern PATTERN = Pattern.compile("Valve (\\w+) .*=(\\d*); .* valves? (.*)");

    private Day16() {
        // No-Op
    }

    /**
     * --- Day 16: Proboscidea Volcanium ---
     * <p>
     * The sensors have led you to the origin of the distress signal: yet another
     * handheld device, just like the one the Elves gave you. However, you don't
     * see any Elves around; instead, the device is surrounded by elephants! They
     * must have gotten lost in these tunnels, and one of the elephants apparently
     * figured out how to turn on the distress signal.
     * <p>
     * The ground rumbles again, much stronger this time. What kind of cave is
     * this, exactly? You scan the cave with your handheld device; it reports
     * mostly igneous rock, some ash, pockets of pressurized gas, magma... this
     * isn't just a cave, it's a volcano!
     * <p>
     * You need to get the elephants out of here, quickly. Your device estimates
     * that you have 30 minutes before the volcano erupts, so you don't have time
     * to go back out the way you came in.
     * <p>
     * You scan the cave for other options and discover a network of pipes and
     * pressure-release valves. You aren't sure how such a system got into a
     * volcano, but you don't have time to complain; your device produces a report
     * (your puzzle input) of each valve's flow rate if it were opened (in
     * pressure per minute) and the tunnels you could use to move between the
     * valves.
     * <p>
     * There's even a valve in the room you and the elephants are currently
     * standing in labeled AA. You estimate it will take you one minute to open a
     * single valve and one minute to follow any tunnel from one valve to another.
     * What is the most pressure you could release?
     * <p>
     * For example, suppose you had the following scan output:
     * <p>
     * Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
     * Valve BB has flow rate=13; tunnels lead to valves CC, AA
     * Valve CC has flow rate=2; tunnels lead to valves DD, BB
     * Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
     * Valve EE has flow rate=3; tunnels lead to valves FF, DD
     * Valve FF has flow rate=0; tunnels lead to valves EE, GG
     * Valve GG has flow rate=0; tunnels lead to valves FF, HH
     * Valve HH has flow rate=22; tunnel leads to valve GG
     * Valve II has flow rate=0; tunnels lead to valves AA, JJ
     * Valve JJ has flow rate=21; tunnel leads to valve II
     * <p>
     * All of the valves begin closed. You start at valve AA, but it must be
     * damaged or jammed or something: its flow rate is 0, so there's no point in
     * opening it. However, you could spend one minute moving to valve BB and
     * another minute opening it; doing so would release pressure during the
     * remaining 28 minutes at a flow rate of 13, a total eventual pressure
     * release of 28 * 13 = 364. Then, you could spend your third minute moving to
     * valve CC and your fourth minute opening it, providing an additional 26
     * minutes of eventual pressure release at a flow rate of 2, or 52 total
     * pressure released by valve CC.
     * <p>
     * Making your way through the tunnels like this, you could probably open many
     * or all of the valves by the time 30 minutes have elapsed. However, you need
     * to release as much pressure as possible, so you'll need to be methodical.
     * Instead, consider this approach:
     * <p>
     * == Minute 1 ==
     * No valves are open.
     * You move to valve DD.
     * <p>
     * == Minute 2 ==
     * No valves are open.
     * You open valve DD.
     * <p>
     * == Minute 3 ==
     * Valve DD is open, releasing 20 pressure.
     * You move to valve CC.
     * <p>
     * == Minute 4 ==
     * Valve DD is open, releasing 20 pressure.
     * You move to valve BB.
     * <p>
     * == Minute 5 ==
     * Valve DD is open, releasing 20 pressure.
     * You open valve BB.
     * <p>
     * == Minute 6 ==
     * Valves BB and DD are open, releasing 33 pressure.
     * You move to valve AA.
     * <p>
     * == Minute 7 ==
     * Valves BB and DD are open, releasing 33 pressure.
     * You move to valve II.
     * <p>
     * == Minute 8 ==
     * Valves BB and DD are open, releasing 33 pressure.
     * You move to valve JJ.
     * <p>
     * == Minute 9 ==
     * Valves BB and DD are open, releasing 33 pressure.
     * You open valve JJ.
     * <p>
     * == Minute 10 ==
     * Valves BB, DD, and JJ are open, releasing 54 pressure.
     * You move to valve II.
     * <p>
     * == Minute 11 ==
     * Valves BB, DD, and JJ are open, releasing 54 pressure.
     * You move to valve AA.
     * <p>
     * == Minute 12 ==
     * Valves BB, DD, and JJ are open, releasing 54 pressure.
     * You move to valve DD.
     * <p>
     * == Minute 13 ==
     * Valves BB, DD, and JJ are open, releasing 54 pressure.
     * You move to valve EE.
     * <p>
     * == Minute 14 ==
     * Valves BB, DD, and JJ are open, releasing 54 pressure.
     * You move to valve FF.
     * <p>
     * == Minute 15 ==
     * Valves BB, DD, and JJ are open, releasing 54 pressure.
     * You move to valve GG.
     * <p>
     * == Minute 16 ==
     * Valves BB, DD, and JJ are open, releasing 54 pressure.
     * You move to valve HH.
     * <p>
     * == Minute 17 ==
     * Valves BB, DD, and JJ are open, releasing 54 pressure.
     * You open valve HH.
     * <p>
     * == Minute 18 ==
     * Valves BB, DD, HH, and JJ are open, releasing 76 pressure.
     * You move to valve GG.
     * <p>
     * == Minute 19 ==
     * Valves BB, DD, HH, and JJ are open, releasing 76 pressure.
     * You move to valve FF.
     * <p>
     * == Minute 20 ==
     * Valves BB, DD, HH, and JJ are open, releasing 76 pressure.
     * You move to valve EE.
     * <p>
     * == Minute 21 ==
     * Valves BB, DD, HH, and JJ are open, releasing 76 pressure.
     * You open valve EE.
     * <p>
     * == Minute 22 ==
     * Valves BB, DD, EE, HH, and JJ are open, releasing 79 pressure.
     * You move to valve DD.
     * <p>
     * == Minute 23 ==
     * Valves BB, DD, EE, HH, and JJ are open, releasing 79 pressure.
     * You move to valve CC.
     * <p>
     * == Minute 24 ==
     * Valves BB, DD, EE, HH, and JJ are open, releasing 79 pressure.
     * You open valve CC.
     * <p>
     * == Minute 25 ==
     * Valves BB, CC, DD, EE, HH, and JJ are open, releasing 81 pressure.
     * <p>
     * == Minute 26 ==
     * Valves BB, CC, DD, EE, HH, and JJ are open, releasing 81 pressure.
     * <p>
     * == Minute 27 ==
     * Valves BB, CC, DD, EE, HH, and JJ are open, releasing 81 pressure.
     * <p>
     * == Minute 28 ==
     * Valves BB, CC, DD, EE, HH, and JJ are open, releasing 81 pressure.
     * <p>
     * == Minute 29 ==
     * Valves BB, CC, DD, EE, HH, and JJ are open, releasing 81 pressure.
     * <p>
     * == Minute 30 ==
     * Valves BB, CC, DD, EE, HH, and JJ are open, releasing 81 pressure.
     * <p>
     * This approach lets you release the most pressure possible in 30 minutes
     * with this valve layout, 1651.
     * <p>
     * Work out the steps to release the most pressure in 30 minutes. What is
     * the most pressure you can release?
     */
    public static int solvePartOne(Scanner scanner) {
        Problem problem = readProblem(scanner);
        List<List<IntIntPair>> orders = allOrders(problem.size(), problem.links(), problem.rates(), 30);

        LOGGER.info("allOrders = {}", orders.size());

        int solution = orders.stream().mapToInt(order -> flow(order, problem.rates())).max().orElseThrow();
        LOGGER.info("solvePartOne = {}", solution);
        return solution;
    }

    /**
     * --- Part Two ---
     * <p>
     * You're worried that even with an optimal approach, the pressure released
     * won't be enough. What if you got one of the elephants to help you?
     * <p>
     * It would take you 4 minutes to teach an elephant how to open the right
     * valves in the right order, leaving you with only 26 minutes to actually
     * execute your plan. Would having two of you working together be better, even
     * if it means having less time? (Assume that you teach the elephant before
     * opening any valves yourself, giving you both the same full 26 minutes.)
     * <p>
     * In the example above, you could teach the elephant to help you as follows:
     * <p>
     * == Minute 1 ==
     * No valves are open.
     * You move to valve II.
     * The elephant moves to valve DD.
     * <p>
     * == Minute 2 ==
     * No valves are open.
     * You move to valve JJ.
     * The elephant opens valve DD.
     * <p>
     * == Minute 3 ==
     * Valve DD is open, releasing 20 pressure.
     * You open valve JJ.
     * The elephant moves to valve EE.
     * <p>
     * == Minute 4 ==
     * Valves DD and JJ are open, releasing 41 pressure.
     * You move to valve II.
     * The elephant moves to valve FF.
     * <p>
     * == Minute 5 ==
     * Valves DD and JJ are open, releasing 41 pressure.
     * You move to valve AA.
     * The elephant moves to valve GG.
     * <p>
     * == Minute 6 ==
     * Valves DD and JJ are open, releasing 41 pressure.
     * You move to valve BB.
     * The elephant moves to valve HH.
     * <p>
     * == Minute 7 ==
     * Valves DD and JJ are open, releasing 41 pressure.
     * You open valve BB.
     * The elephant opens valve HH.
     * <p>
     * == Minute 8 ==
     * Valves BB, DD, HH, and JJ are open, releasing 76 pressure.
     * You move to valve CC.
     * The elephant moves to valve GG.
     * <p>
     * == Minute 9 ==
     * Valves BB, DD, HH, and JJ are open, releasing 76 pressure.
     * You open valve CC.
     * The elephant moves to valve FF.
     * <p>
     * == Minute 10 ==
     * Valves BB, CC, DD, HH, and JJ are open, releasing 78 pressure.
     * The elephant moves to valve EE.
     * <p>
     * == Minute 11 ==
     * Valves BB, CC, DD, HH, and JJ are open, releasing 78 pressure.
     * The elephant opens valve EE.
     * <p>
     * (At this point, all valves are open.)
     * <p>
     * == Minute 12 ==
     * Valves BB, CC, DD, EE, HH, and JJ are open, releasing 81 pressure.
     * <p>
     * ...
     * <p>
     * == Minute 20 ==
     * Valves BB, CC, DD, EE, HH, and JJ are open, releasing 81 pressure.
     * <p>
     * ...
     * <p>
     * == Minute 26 ==
     * Valves BB, CC, DD, EE, HH, and JJ are open, releasing 81 pressure.
     * <p>
     * With the elephant helping, after 26 minutes, the best you could do would
     * release a total of 1707 pressure.
     * <p>
     * With you and an elephant working together for 26 minutes, what is the most
     * pressure you could release?
     */
    public static int solvePartTwo(Scanner scanner) {
        Problem problem = readProblem(scanner);
        List<List<IntIntPair>> orders = allOrders(problem.size(), problem.links(), problem.rates(), 26);

        LOGGER.info("allOrders = {}", orders.size());

        Long2IntMap flows = new Long2IntOpenHashMap();
        for (List<IntIntPair> order : orders) {
            long maxk = mask(order);
            int flow = flow(order, problem.rates());
            flows.merge(maxk, flow, Math::max);
        }

        LOGGER.info("flows = {}", flows.size());

        int max = 0;
        for (Long2IntMap.Entry entry1 : flows.long2IntEntrySet()) {
            for (Long2IntMap.Entry entry2 : flows.long2IntEntrySet()) {
                if ((entry1.getLongKey() & entry2.getLongKey()) == 0L) {
                    max = Math.max(max, entry1.getIntValue() + entry2.getIntValue());
                }
            }
        }

        LOGGER.info("solvePartTwo = {}", max);
        return max;
    }

    private static int flow(List<IntIntPair> order, Int2IntMap rates) {
        return order.stream().mapToInt(e -> rates.get(e.firstInt()) * e.secondInt()).sum();
    }

    private static long mask(List<IntIntPair> order) {
        IntStream intStream = order.stream().mapToInt(IntIntPair::firstInt).filter(i -> i != 0);
        return Bits.toBitSet(intStream);
    }

    private static List<List<IntIntPair>> allOrders(int size, Int2ObjectMap<IntList> links, Int2IntMap rates, int time) {
        int[][] distances = new int[size][size];

        Map<Integer, List<Pair<Integer, Integer>>> graph = new HashMap<>();
        for (int i = 0; i < size; ++i) {
            IntList link = links.getOrDefault(i, IntList.of());

            for (int j : link) {
                graph.computeIfAbsent(i, _ -> new ArrayList<>()).add(Pair.of(j, 1));
            }
        }
        Dijkstra<Integer> dijkstra = new Dijkstra<>(graph);
        for (int i = 0; i < size; ++i) {
            Object2IntMap<Integer> distance = dijkstra.computeDistance(i);
            for (Object2IntMap.Entry<Integer> entry : distance.object2IntEntrySet()) {
                if (rates.get((int) entry.getKey()) > 0) {
                    distances[i][entry.getKey()] = entry.getIntValue();
                }
            }
        }

        IntStream intStream = rates.int2IntEntrySet()
                .stream()
                .filter(e -> e.getIntValue() > 0)
                .mapToInt(Int2IntMap.Entry::getIntKey);

        long allValves = Bits.toBitSet(intStream);
        List<List<IntIntPair>> result = new ArrayList<>();
        allOrders(result, distances, 0, allValves, List.of(IntIntPair.of(0, time)), time);
        return result;
    }

    private static void allOrders(List<List<IntIntPair>> result, int[][] distances, int currentValve, long valves, List<IntIntPair> valveOrder, int time) {
        for (int nextValve : Bits.convertBitSet(valves)) {
            int cost = distances[currentValve][nextValve] + 1;
            if (cost > 1 && cost < time) {
                long nextTodo = Bits.remove(valves, nextValve);
                List<IntIntPair> nextOrder = new ArrayList<>(valveOrder);
                nextOrder.add(IntIntPair.of(nextValve, time - cost));
                allOrders(result, distances, nextValve, nextTodo, nextOrder, time - cost);
            }
        }
        result.add(valveOrder);
    }

    private static Problem readProblem(Scanner scanner) {
        Int2IntMap rates = new Int2IntOpenHashMap();
        Int2ObjectMap<IntList> links = new Int2ObjectOpenHashMap<>();

        AtomicInteger id = new AtomicInteger(1);
        Map<String, Integer> indexes = new HashMap<>();
        indexes.put("AA", 0);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                int index = indexes.computeIfAbsent(matcher.group(1), _ -> id.getAndIncrement());
                rates.put(index, Integer.parseInt(matcher.group(2)));
                IntStream intStream = Arrays.stream(matcher.group(3).split(", ")).mapToInt(v -> indexes.computeIfAbsent(v, _ -> id.getAndIncrement()));
                links.put(index, IntArrayList.toList(intStream));
            } else {
                LOGGER.error("Cannot parse line '{}'", line);
            }
        }

        LOGGER.info("Rates = {}", rates);
        LOGGER.info("Links = {}", links);

        LOGGER.info("Indexes = {}", indexes);

        int size = indexes.size();
        return new Problem(rates, links, size);
    }

    private record Problem(Int2IntMap rates, Int2ObjectMap<IntList> links, int size) {
    }
}
