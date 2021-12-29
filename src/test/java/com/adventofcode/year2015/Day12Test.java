package com.adventofcode.year2015;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;


class Day12Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12Test.class);

    private static long computeSum(Object object, Predicate<Object> filter) {
        if (object instanceof List list) {
            return computeSum(list, filter);
        } else if (object instanceof Map map) {
            return computeSum(map, filter);
        } else if (object instanceof String) {
            return 0;
        } else if (object instanceof Number n) {
            return n.longValue();
        } else {
            LOGGER.error("Cannot manage type: {}", object.getClass());
            throw new IllegalStateException();
        }
    }

    private static long computeSum(List<?> list, Predicate<Object> filter) {
        long sum = 0;
        for (Object item : list) {
            sum += computeSum(item, filter);
        }
        return sum;
    }

    private static long computeSum(Map<?, ?> map, Predicate<Object> filter) {
        long sum = 0;
        for (Object value : map.values()) {
            if (filter != null && filter.test(value)) {
                return 0;
            }
            sum += computeSum(value, filter);
        }
        return sum;
    }


    /**
     * --- Day 12: JSAbacusFramework.io ---
     *
     * Santa's Accounting-Elves need help balancing the books after a recent
     * order. Unfortunately, their accounting software uses a peculiar storage
     * format. That's where you come in.
     *
     * They have a JSON document which contains a variety of things: arrays
     * ([1,2,3]), objects ({"a":1, "b":2}), numbers, and strings. Your first job is
     * to simply find all of the numbers throughout the document and add them
     * together.
     *
     * For example:
     *
     *   - [1,2,3] and {"a":2,"b":4} both have a sum of 6.
     *   - [[[3]]] and {"a":{"b":4},"c":-1} both have a sum of 3.
     *   - {"a":[-1,1]} and [-1,{"a":1}] both have a sum of 0.
     *   - [] and {} both have a sum of 0.
     *
     * You will not encounter any strings containing numbers.
     *
     * What is the sum of all numbers in the document?
     *
     * Your puzzle answer was 156366.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day12Test.class.getResourceAsStream("/2015/day/12/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            List<?> list = new Gson().fromJson(scanner.nextLine(), List.class);
            assertThat(computeSum(list, null)).isEqualTo(156366);
        }
    }

    /**
     * --- Part Two ---
     *
     * Uh oh - the Accounting-Elves have realized that they double-counted
     * everything red.
     *
     * Ignore any object (and all of its children) which has any property with the
     * value "red". Do this only for objects ({...}), not arrays ([...]).
     *
     *   - [1,2,3] still has a sum of 6.
     *   - [1,{"c":"red","b":2},3] now has a sum of 4, because the middle object
     *     is ignored.
     *   - {"d":"red","e":[1,2,3,4],"f":5} now has a sum of 0, because the entire
     *     structure is ignored.
     *   - [1,"red",5] has a sum of 6, because "red" in an array has no effect.
     *
     * Your puzzle answer was 96852.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day12Test.class.getResourceAsStream("/2015/day/12/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            List<?> list = new Gson().fromJson(scanner.nextLine(), List.class);
            assertThat(computeSum(list, "red"::equals)).isEqualTo(96852);
        }
    }
}
