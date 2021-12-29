package com.adventofcode.maths;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class IntegerPartition {
    private static final Logger LOGGER = LoggerFactory.getLogger(IntegerPartition.class);

    private IntegerPartition() {
        // No-Op
    }

    public static List<IntList> partitions(int n, int part) {
        if (part == 1) {
            if (n == 0) {
                LOGGER.info("Partition error: {}/{}", n, part);
                throw new IllegalStateException("Partition error");
            } else {
                return List.of(IntList.of(n));
            }
        } else {
            List<IntList> result = new ArrayList<>();
            for (int p = 1; p < n; ++p) {
                List<IntList> partitions = partitions(n - p, part - 1);
                int finalP = p;
                partitions.stream().map(IntArrayList::new).forEach(l -> {
                    l.add(finalP);
                    result.add(l);
                });
            }

            return result;
        }
    }
}
