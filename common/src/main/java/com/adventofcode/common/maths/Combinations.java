package com.adventofcode.common.maths;

import com.adventofcode.common.utils.IntegerPair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public final class Combinations {
    private static final Map<IntegerPair, List<int[]>> CACHE = new ConcurrentHashMap<>();

    private Combinations() {
        // No-Op
    }

    private static void helper(List<int[]> combinations, int[] data, int start, int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if (start <= end) {
            data[index] = start;
            helper(combinations, data, start + 1, end, index + 1);
            helper(combinations, data, start + 1, end, index);
        }
    }

    public static List<int[]> generate(int n, int r) {
        return CACHE.computeIfAbsent(IntegerPair.of(n, r), _ -> {
            List<int[]> combinations = new ArrayList<>();
            helper(combinations, new int[r], 0, n - 1, 0);
            return combinations;
        });
    }

    public static <T> Stream<List<T>> generate(List<T> list, int r) {
        List<int[]> combinations = generate(list.size(), r);
        return combinations
                .stream()
                .map(c -> Arrays.stream(c).mapToObj(list::get).toList());
    }
}
