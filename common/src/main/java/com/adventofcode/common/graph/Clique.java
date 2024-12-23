package com.adventofcode.common.graph;

import org.apache.commons.collections4.SetUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Clique {

    /**
     * cf. https://fr.wikipedia.org/wiki/Algorithme_de_Bron-Kerbosch#Version_avec_pivot
     *
     * algorithme BronKerbosch1(R, P, X)
     *    si P et X sont vides alors
     *        déclarer que R est une clique maximale
     *    pour tout sommet v dans P faire
     *        BronKerbosch1(R ⋃ {v}, P ⋂ N(v), X ⋂ N(v))
     *        P := P \ {v}
     *        X := X ⋃ {v}
     */
    public static <T> void algorithmBronKerbosch(Map<T, Set<T>> graph, Set<T> nodesR, Set<T> nodesP, Set<T> nodesX, List<Set<T>> result) {
        if (nodesP.isEmpty() && nodesX.isEmpty()) {
            result.add(Set.copyOf(nodesR));
        }
        Set<T> copyP = new HashSet<>(nodesP);
        Set<T> copyX = new HashSet<>(nodesX);
        for (T v : nodesP) {
            Set<T> nv = graph.get(v);
            algorithmBronKerbosch(graph,
                    SetUtils.union(nodesR, Set.of(v)),
                    SetUtils.intersection(copyP, nv),
                    SetUtils.intersection(copyX, nv),
                    result
            );
            copyP.remove(v);
            copyX.add(v);
        }
    }

}
