package com.adventofcode.maths;

import java.util.ArrayList;
import java.util.List;

public final class Permutations {
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
        }
        else {
            for (int i = 0; i < n; i++)
                permutations(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n), result);
        }
    }
}
