package com.adventofcode.common.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CliqueTest {

    @Test
    void bronKerbosch() {
        Map<String, Set<String>> graph = Map.ofEntries(
                Map.entry("de", Set.of("cg", "ka", "co", "ta")),
                Map.entry("cg", Set.of("aq", "de", "yn", "tb")),
                Map.entry("co", Set.of("de", "ka", "ta", "tc")),
                Map.entry("ta", Set.of("de", "ka", "co", "kh")),
                Map.entry("vc", Set.of("aq", "wq", "ub", "tb")),
                Map.entry("ub", Set.of("wq", "kh", "vc", "qp")),
                Map.entry("tb", Set.of("cg", "ka", "wq", "vc")),
                Map.entry("tc", Set.of("td", "wh", "co", "kh")),
                Map.entry("td", Set.of("wh", "yn", "tc", "qp")),
                Map.entry("aq", Set.of("cg", "yn", "wq", "vc")),
                Map.entry("wh", Set.of("td", "yn", "tc", "qp")),
                Map.entry("yn", Set.of("aq", "td", "wh", "cg")),
                Map.entry("ka", Set.of("de", "co", "ta", "tb")),
                Map.entry("wq", Set.of("aq", "ub", "vc", "tb")),
                Map.entry("kh", Set.of("ub", "ta", "tc", "qp")),
                Map.entry("qp", Set.of("td", "wh", "kh", "ub"))
        );

        List<Set<String>> result = new ArrayList<>();
        Clique.algorithmBronKerbosch(graph, Set.of(), graph.keySet(), Set.of(), result);

        assertThat(result).contains(Set.of("co", "de", "ka", "ta"));
    }

}