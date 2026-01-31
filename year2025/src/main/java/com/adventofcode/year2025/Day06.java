package com.adventofcode.year2025;

import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class Day06 {

    /**
     * --- Day 6: Trash Compactor ---
     * <p>
     * After helping the Elves in the kitchen, you were taking a break and helping
     * them re-enact a movie scene when you over-enthusiastically jumped into the
     * garbage chute!
     * <p>
     * A brief fall later, you find yourself in a garbage smasher. Unfortunately,
     * the door's been magnetically sealed.
     * <p>
     * As you try to find a way out, you are approached by a family of
     * cephalopods! They're pretty sure they can get the door open, but it will
     * take some time. While you wait, they're curious if you can help the
     * youngest cephalopod with her math homework.
     * <p>
     * Cephalopod math doesn't look that different from normal math. The math
     * worksheet (your puzzle input) consists of a list of problems; each problem
     * has a group of numbers that need to be either added (+) or multiplied (*)
     * together.
     * <p>
     * However, the problems are arranged a little strangely; they seem to be
     * presented next to each other in a very long horizontal list. For example:
     * <p>
     * 123 328  51 64
     * 45 64  387 23
     * 6 98  215 314
     * +   *   +
     * <p>
     * Each problem's numbers are arranged vertically; at the bottom of the
     * problem is the symbol for the operation that needs to be performed.
     * Problems are separated by a full column of only spaces. The left/right
     * alignment of numbers within each problem can be ignored.
     * <p>
     * So, this worksheet contains four problems:
     * <p>
     * 123 * 45 * 6 = 33210
     * 328 + 64 + 98 = 490
     * 51 * 387 * 215 = 4243455
     * 64 + 23 + 314 = 401
     * <p>
     * To check their work, cephalopod students are given the grand total of
     * adding together all of the answers to the individual problems. In this
     * worksheet, the grand total is 33210 + 490 + 4243455 + 401 = 4277556.
     * <p>
     * Of course, the actual worksheet is much wider. You'll need to make sure to
     * unroll it completely so that you can read the problems clearly.
     * <p>
     * Solve the problems on the math worksheet. What is the grand total found by
     * adding together all of the answers to the individual problems?
     * <p>
     * --- Part Two ---
     * <p>
     * The big cephalopods come back to check on how things are going. When they
     * see that your grand total doesn't match the one expected by the worksheet,
     * they realize they forgot to explain how to read cephalopod math.
     * <p>
     * Cephalopod math is written right-to-left in columns. Each number is given
     * in its own column, with the most significant digit at the top and the least
     * significant digit at the bottom. (Problems are still separated with a
     * column consisting only of spaces, and the symbol at the bottom of the
     * problem is still the operator to use.)
     * <p>
     * Here's the example worksheet again:
     * <p>
     * 123 328  51 64
     * 45 64  387 23
     * 6 98  215 314
     * +   *   +
     * <p>
     * Reading the problems right-to-left one column at a time, the problems are
     * now quite different:
     * <p>
     * The rightmost problem is 4 + 431 + 623 = 1058
     * The second problem from the right is 175 * 581 * 32 = 3253600
     * The third problem from the right is 8 + 248 + 369 = 625
     * Finally, the leftmost problem is 356 * 24 * 1 = 8544
     * <p>
     * Now, the grand total is 1058 + 3253600 + 625 + 8544 = 3263827.
     * <p>
     * Solve the problems on the math worksheet again. What is the grand total
     * found by adding together all of the answers to the individual problems?
     */
    private Day06() {
    }

    public static long partOne(Scanner scanner) {
        List<LongList> numbers = new ArrayList<>();
        List<String> operations = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) continue;
            if (line.contains("*") || line.contains("+")) {
                Collections.addAll(operations, line.trim().split("\\s+"));
            } else {
                LongList x = new LongArrayList();
                for (String s : Splitter.on(Pattern.compile("\\s+")).split(line.trim())) {
                    x.add(Long.parseLong(s));
                }
                numbers.add(x);
            }
        }

        long result = 0;
        for (int i = 0; i < operations.size(); i++) {
            String o = operations.get(i);
            final int index = i;
            if (o.equals("+")) {
                result += numbers.stream().mapToLong(n -> n.getLong(index)).sum();
            } else if (o.equals("*")) {
                result += numbers.stream().mapToLong(n -> n.getLong(index)).reduce(1, (a, b) -> a * b);
            }
        }
        return result;
    }

    public static long partTwo(Scanner scanner) {
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }

        List<String> revLines = lines.stream()
                .map(s -> new StringBuilder(s).reverse().toString())
                .toList();

        int maxLen = revLines.stream().mapToInt(String::length).max().orElse(0);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < maxLen; i++) {
            for (String l : revLines) {
                if (i < l.length()) {
                    builder.append(l.charAt(i));
                }
            }
        }

        LongList queue = new LongArrayList();
        long result = 0;
        for (String x : Splitter.on(Pattern.compile("\\s+")).split(builder.toString())) {
            if (x.isEmpty()) continue;
            if (x.endsWith("*")) {
                String valStr = x.substring(0, x.length() - 1);
                if (!valStr.isEmpty()) {
                    queue.addLast(Long.parseLong(valStr));
                }
                result += queue.longStream().reduce(1L, (a, b) -> a * b);
                queue.clear();
            } else if (x.endsWith("+")) {
                String valStr = x.substring(0, x.length() - 1);
                if (!valStr.isEmpty()) {
                    queue.addLast(Long.parseLong(valStr));
                }
                result += queue.longStream().sum();
                queue.clear();
            } else {
                queue.addLast(Long.parseLong(x));
            }
        }
        return result;
    }
}
