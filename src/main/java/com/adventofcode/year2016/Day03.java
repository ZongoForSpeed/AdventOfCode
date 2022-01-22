package com.adventofcode.year2016;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Scanner;

public class Day03 {
    public static final Logger LOGGER = LoggerFactory.getLogger(Day03.class);

    private Day03() {
        // No-Op
    }

    /**
     * --- Day 3: Squares With Three Sides ---
     *
     * Now that you can think clearly, you move deeper into the labyrinth of
     * hallways and office furniture that makes up this part of Easter Bunny HQ.
     * This must be a graphic design department; the walls are covered in
     * specifications for triangles.
     *
     * Or are they?
     *
     * The design document gives the side lengths of each triangle it describes,
     * but... 5 10 25? Some of these aren't triangles. You can't help but mark the
     * impossible ones.
     *
     * In a valid triangle, the sum of any two sides must be larger than the
     * remaining side. For example, the "triangle" given above is impossible,
     * because 5 + 10 is not larger than 25.
     *
     * In your puzzle input, how many of the listed triangles are possible?
     *
     * Your puzzle answer was 1032.
     */
    public static int countTrianglesPartOne(Scanner scanner) {
        int count = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int[] array = Arrays.stream(line.split(" ")).filter(StringUtils::isNotBlank).mapToInt(Integer::parseInt).sorted().toArray();
            LOGGER.trace("Array: {}", array);
            if (array[0] + array[1] > array[2]) {
                count++;
            }
        }
        return count;
    }

    /**
     * --- Part Two ---
     *
     * Now that you've helpfully marked up their design documents, it occurs to
     * you that triangles are specified in groups of three vertically. Each set of
     * three numbers in a column specifies a triangle. Rows are unrelated.
     *
     * For example, given the following specification, numbers with the same
     * hundreds digit would be part of the same triangle:
     *
     * 101 301 501
     * 102 302 502
     * 103 303 503
     * 201 401 601
     * 202 402 602
     * 203 403 603
     *
     * In your puzzle input, and instead reading by columns, how many of the
     * listed triangles are possible?
     *
     * Your puzzle answer was 1838.
     */
    public static int countTrianglesPartTwo(Scanner scanner) {
        IntList column1 = new IntArrayList();
        IntList column2 = new IntArrayList();
        IntList column3 = new IntArrayList();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int[] array = Arrays.stream(line.split(" ")).filter(StringUtils::isNotBlank).mapToInt(Integer::parseInt).toArray();
            LOGGER.trace("Array: {}", array);
            column1.add(array[0]);
            column2.add(array[1]);
            column3.add(array[2]);
        }

        IntList allColumns = new IntArrayList();
        allColumns.addAll(column1);
        allColumns.addAll(column2);
        allColumns.addAll(column3);

        int count = 0;
        for (int j = 2; j < allColumns.size(); j += 3) {
            IntList subList = allColumns.subList(j - 2, j + 1);
            subList.sort(IntComparators.NATURAL_COMPARATOR);
            LOGGER.trace("subList = {}", subList);
            if (subList.getInt(0) + subList.getInt(1) > subList.getInt(2)) {
                count++;
            }
        }


        return count;
    }
}
