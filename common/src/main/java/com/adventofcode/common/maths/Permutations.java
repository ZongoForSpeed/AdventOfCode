package com.adventofcode.common.maths;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class Permutations {
    private static final long[] FACTORIALS;

    static {
        long[] factorials = new long[21];
        factorials[0] = 1;
        factorials[1] = 1;
        for (int i = 2; i < 21; ++i) {
            factorials[i] = factorials[i - 1] * i;
        }
        FACTORIALS = factorials;
    }

    private Permutations() {
        // No-Op
    }

    public static List<String> permutations(String str) {
        List<String> result = new ArrayList<>();
        permutations("", str, result);
        return result;
    }

    private static void permutations(String prefix, String str, List<String> result) {
        int n = str.length();
        if (n == 0) {
            result.add(prefix);
        } else {
            for (int i = 0; i < n; i++)
                permutations(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), result);
        }
    }

    public static long factorial(int n) {
        if (n > 20 || n < 0) throw new IllegalArgumentException(n + " is out of range");
        return FACTORIALS[n];
    }

    public static <T> List<T> permutation(long no, List<T> items) {
        return permutationHelper(no,
                new LinkedList<>(Objects.requireNonNull(items)),
                new ArrayList<>());
    }

    public static LongList permutation(long no, LongList items) {
        return permutationHelper(no,
                new LinkedList<>(Objects.requireNonNull(items)),
                new LongArrayList());
    }

    public static IntList permutation(long no, IntList items) {
        return permutationHelper(no,
                new LinkedList<>(Objects.requireNonNull(items)),
                new IntArrayList());
    }

    private static <T> List<T> permutationHelper(long no, List<T> in, List<T> out) {
        if (in.isEmpty()) return out;
        long subFactorial = factorial(in.size() - 1);
        out.add(in.remove((int) (no / subFactorial)));
        return permutationHelper(no % subFactorial, in, out);
    }

    private static LongList permutationHelper(long no, List<Long> in, LongList out) {
        if (in.isEmpty()) return out;
        long subFactorial = factorial(in.size() - 1);
        out.add(in.remove((int) (no / subFactorial)).longValue());
        return permutationHelper(no % subFactorial, in, out);
    }

    private static IntList permutationHelper(long no, List<Integer> in, IntList out) {
        if (in.isEmpty()) return out;
        long subFactorial = factorial(in.size() - 1);
        out.add(in.remove((int) (no / subFactorial)).intValue());
        return permutationHelper(no % subFactorial, in, out);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a List from an array is safe
    public static <T> Stream<List<T>> of(T... items) {
        List<T> itemList = Arrays.asList(items);
        return LongStream.range(0, factorial(items.length))
                .mapToObj(no -> permutation(no, itemList));
    }

    public static <T> Stream<List<T>> of(Collection<T> items) {
        List<T> itemList = new ArrayList<>(items);
        return LongStream.range(0, factorial(items.size()))
                .mapToObj(no -> permutation(no, itemList));
    }

    public static Stream<LongList> of(long... items) {
        LongList itemList = LongList.of(items);
        return LongStream.range(0, factorial(items.length))
                .mapToObj(no -> permutation(no, itemList));
    }

    public static Stream<IntList> of(int... items) {
        IntList itemList = IntList.of(items);
        return LongStream.range(0, factorial(items.length))
                .mapToObj(no -> permutation(no, itemList));
    }
}
