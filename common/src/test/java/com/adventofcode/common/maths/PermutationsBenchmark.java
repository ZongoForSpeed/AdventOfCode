package com.adventofcode.common.maths;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@State(Scope.Benchmark)
public class PermutationsBenchmark {

    private static final List<Integer> INPUT = List.of(1, 2, 3, 4, 5);


    @Benchmark
    public void permutation_ArrayList(Blackhole bh) {
        List<Integer> integers = new ArrayList<>(INPUT);
        IntList ouput = new IntArrayList();
        Permutations.permutationHelper(10, integers, ouput);

        bh.consume(ouput);
    }

    @Benchmark
    public void permutation_LinkedList(Blackhole bh) {
        List<Integer> integers = new LinkedList<>(INPUT);
        IntList ouput = new IntArrayList();
        Permutations.permutationHelper(10, integers, ouput);

        bh.consume(ouput);
    }

//   @Benchmark
//   public void permutation_LinkedList(Blackhole bh) {
//       List<Integer> integers = new ArrayDeque<>(INPUT);
//       IntList ouput = new IntArrayList();
//       Permutations.permutationHelper(10, integers, ouput);

//       bh.consume(ouput);
//   }
}