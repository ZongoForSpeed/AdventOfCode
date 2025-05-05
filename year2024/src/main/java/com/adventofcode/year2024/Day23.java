package com.adventofcode.year2024;

import com.adventofcode.common.utils.IntSets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day23 {

    private static final Pattern PATTERN = Pattern.compile("(\\w+)-(\\w+)");
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23.class);

    private Day23() {
        // No-Op
    }

    /**
     * --- Day 23: LAN Party ---
     * <p>
     * As The Historians wander around a secure area at Easter Bunny HQ, you come
     * across posters for a LAN party scheduled for today! Maybe you can find it;
     * you connect to a nearby datalink port and download a map of the local
     * network (your puzzle input).
     * <p>
     * The network map provides a list of every connection between two computers.
     * For example:
     * <p>
     * kh-tc
     * qp-kh
     * de-cg
     * ka-co
     * yn-aq
     * qp-ub
     * cg-tb
     * vc-aq
     * tb-ka
     * wh-tc
     * yn-cg
     * kh-ub
     * ta-co
     * de-co
     * tc-td
     * tb-wq
     * wh-td
     * ta-ka
     * td-qp
     * aq-cg
     * wq-ub
     * ub-vc
     * de-ta
     * wq-aq
     * wq-vc
     * wh-yn
     * ka-de
     * kh-ta
     * co-tc
     * wh-qp
     * tb-vc
     * td-yn
     * <p>
     * Each line of text in the network map represents a single connection; the
     * line kh-tc represents a connection between the computer named kh and the
     * computer named tc. Connections aren't directional; tc-kh would mean exactly
     * the same thing.
     * <p>
     * LAN parties typically involve multiplayer games, so maybe you can locate it
     * by finding groups of connected computers. Start by looking for sets of
     * three computers where each computer in the set is connected to the other
     * two computers.
     * <p>
     * In this example, there are 12 such sets of three inter-connected computers:
     * <p>
     * aq,cg,yn
     * aq,vc,wq
     * co,de,ka
     * co,de,ta
     * co,ka,ta
     * de,ka,ta
     * kh,qp,ub
     * qp,td,wh
     * tb,vc,wq
     * tc,td,wh
     * td,wh,yn
     * ub,vc,wq
     * <p>
     * If the Chief Historian is here, and he's at the LAN party, it would be best
     * to know that right away. You're pretty sure his computer's name starts with
     * t, so consider only sets of three computers where at least one computer's
     * name starts with t. That narrows the list down to 7 sets of three inter-
     * connected computers:
     * <p>
     * co,de,ta
     * co,ka,ta
     * de,ka,ta
     * qp,td,wh
     * tb,vc,wq
     * tc,td,wh
     * td,wh,yn
     * <p>
     * Find all the sets of three inter-connected computers. How many contain at
     * least one computer with a name that starts with t?
     */
    public static Set<Set<String>> partOne(Scanner scanner) {
        Map<String, Set<String>> graph = readGraph(scanner);

        Set<Set<String>> triplets = new HashSet<>();

        for (Map.Entry<String, Set<String>> entry : graph.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith("t") && entry.getValue().size() > 1) {
                ArrayList<String> nodes = new ArrayList<>(entry.getValue());
                for (int i = 0, nodesSize = nodes.size(); i < nodesSize; i++) {
                    String node1 = nodes.get(i);
                    for (int j = i + 1; j < nodesSize; ++j) {
                        String node2 = nodes.get(j);
                        if (Objects.requireNonNull(graph.get(node1)).contains(node2)) {
                            triplets.add(Set.of(key, node1, node2));
                        }
                    }
                }
            }
        }

        LOGGER.trace("triplets: {}", triplets);
        return triplets;
    }

    private static Map<String, Set<String>> readGraph(Scanner scanner) {
        Map<String, Set<String>> graph = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                String group1 = matcher.group(1);
                String group2 = matcher.group(2);
                graph.computeIfAbsent(group1, ignore -> new HashSet<>()).add(group2);
                graph.computeIfAbsent(group2, ignore -> new HashSet<>()).add(group1);
            } else {
                throw new IllegalStateException("Cannot parse line: " + line);
            }
        }

        LOGGER.trace("graph: {}", graph);
        return graph;
    }


    /**
     * --- Part Two ---
     * <p>
     * There are still way too many results to go through them all. You'll have to
     * find the LAN party another way and go there yourself.
     * <p>
     * Since it doesn't seem like any employees are around, you figure they must
     * all be at the LAN party. If that's true, the LAN party will be the largest
     * set of computers that are all connected to each other. That is, for each
     * computer at the LAN party, that computer will have a connection to every
     * other computer at the LAN party.
     * <p>
     * In the above example, the largest set of computers that are all connected
     * to each other is made up of co, de, ka, and ta. Each computer in this set
     * has a connection to every other computer in the set:
     * <p>
     * ka-co
     * ta-co
     * de-co
     * ta-ka
     * de-ta
     * ka-de
     * <p>
     * The LAN party posters say that the password to get into the LAN party is
     * the name of every computer at the LAN party, sorted alphabetically, then
     * joined together with commas. (The people running the LAN party are clearly
     * a bunch of nerds.) In this example, the password would be co,de,ka,ta.
     * <p>
     * What is the password to get into the LAN party?
     */
    public static String partTwo(Scanner scanner) {
        Map<String, Set<String>> graph = readGraph(scanner);

        List<String> vertices = graph.keySet().stream().sorted().toList();
        Map<String, Integer> ids = new HashMap<>();
        List<BitSet> intGraph = new ArrayList<>(vertices.size());
        for (int i = 0; i < vertices.size(); i++) {
            ids.put(vertices.get(i), i);
            intGraph.add(new BitSet());
        }

        for (Map.Entry<String, Set<String>> entry : graph.entrySet()) {
            Integer key = ids.get(entry.getKey());
            BitSet nv = intGraph.get(key);
            Objects.requireNonNull(entry).getValue().stream().mapToInt(ids::get).forEach(nv::set);
        }

        LOGGER.info("intGraph: {}", intGraph);

        List<BitSet> result = new ArrayList<>();
        algorithmBronKerbosch1(intGraph, IntSets.empty(), IntSets.range(0, intGraph.size()), IntSets.empty(), result);

        LOGGER.info("result BronKerbosch: {}", result.size());

        Optional<List<String>> max = result.stream()
                .max(Comparator.comparingInt(BitSet::cardinality))
                .map(s -> s.stream().mapToObj(vertices::get).toList());

        List<String> cliqueMaximum = max.orElseThrow();
        LOGGER.info("cliqueMaximum: {}", cliqueMaximum);
        return String.join(",", cliqueMaximum);
    }

    private static void algorithmBronKerbosch1(List<BitSet> graph, BitSet nodesR, BitSet nodesP, BitSet nodesX, List<BitSet> result) {
        // cf. https://fr.wikipedia.org/wiki/Algorithme_de_Bron-Kerbosch#Version_avec_pivot
        //
        // algorithme BronKerbosch1(R, P, X)
        //    si P et X sont vides alors
        //        déclarer que R est une clique maximale
        //    pour tout sommet v dans P faire
        //        BronKerbosch1(R ⋃ {v}, P ⋂ N(v), X ⋂ N(v))
        //        P := P \ {v}
        //        X := X ⋃ {v}
        if (nodesP.cardinality() == 0 && nodesX.cardinality() == 0) {
            result.add(IntSets.copy(nodesR));
        }
        BitSet copyP = IntSets.copy(nodesP);
        BitSet copyX = IntSets.copy(nodesX);
        for (int v : nodesP.stream().toArray()) {
            BitSet nv = graph.get(v);
            // BronKerbosch1(R ⋃ {v}, P ⋂ N(v), X ⋂ N(v))
            algorithmBronKerbosch1(graph,
                    IntSets.union(nodesR, v),           // R ⋃ {v}
                    IntSets.intersection(copyP, nv),    // P ⋂ N(v)
                    IntSets.intersection(copyX, nv),    // X ⋂ N(v)
                    result
            );
            // P := P \ {v}
            copyP.clear(v);
            // X := X ⋃ {v}
            copyX.set(v);
        }
    }

}
