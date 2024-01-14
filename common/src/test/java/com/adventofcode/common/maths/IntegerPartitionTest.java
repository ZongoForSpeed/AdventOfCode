package com.adventofcode.common.maths;

import com.adventofcode.common.maths.IntegerPartition;
import it.unimi.dsi.fastutil.ints.IntList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class IntegerPartitionTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(IntegerPartitionTest.class);

    @Test
    void testPartition_10_3() {
        List<IntList> partition = IntegerPartition.partitions(10, 3);
        partition.forEach(p -> LOGGER.info("{}", p));
        Assertions.assertThat(partition)
                .containsExactlyInAnyOrder(
                        IntList.of(8, 1, 1),
                        IntList.of(7, 2, 1),
                        IntList.of(6, 3, 1),
                        IntList.of(5, 4, 1),
                        IntList.of(4, 5, 1),
                        IntList.of(3, 6, 1),
                        IntList.of(2, 7, 1),
                        IntList.of(1, 8, 1),
                        IntList.of(7, 1, 2),
                        IntList.of(6, 2, 2),
                        IntList.of(5, 3, 2),
                        IntList.of(4, 4, 2),
                        IntList.of(3, 5, 2),
                        IntList.of(2, 6, 2),
                        IntList.of(1, 7, 2),
                        IntList.of(6, 1, 3),
                        IntList.of(5, 2, 3),
                        IntList.of(4, 3, 3),
                        IntList.of(3, 4, 3),
                        IntList.of(2, 5, 3),
                        IntList.of(1, 6, 3),
                        IntList.of(5, 1, 4),
                        IntList.of(4, 2, 4),
                        IntList.of(3, 3, 4),
                        IntList.of(2, 4, 4),
                        IntList.of(1, 5, 4),
                        IntList.of(4, 1, 5),
                        IntList.of(3, 2, 5),
                        IntList.of(2, 3, 5),
                        IntList.of(1, 4, 5),
                        IntList.of(3, 1, 6),
                        IntList.of(2, 2, 6),
                        IntList.of(1, 3, 6),
                        IntList.of(2, 1, 7),
                        IntList.of(1, 2, 7),
                        IntList.of(1, 1, 8)
                );
    }


    @Test
    void testPartition_4_4() {
        List<IntList> partition = IntegerPartition.partitions(4, 4);
        partition.forEach(p -> LOGGER.info("{}", p));
        Assertions.assertThat(partition)
                .containsExactlyInAnyOrder(
                        IntList.of(1, 1, 1, 1)
                );
    }

    @Test
    void testPartition_5_4() {
        List<IntList> partition = IntegerPartition.partitions(5, 4);
        partition.forEach(p -> LOGGER.info("{}", p));
        Assertions.assertThat(partition)
                .containsExactlyInAnyOrder(
                        IntList.of(2, 1, 1, 1),
                        IntList.of(1, 2, 1, 1),
                        IntList.of(1, 1, 2, 1),
                        IntList.of(1, 1, 1, 2)
                );
    }

    @Test
    void testPartition_10_4() {
        List<IntList> partition = IntegerPartition.partitions(10, 4);
        partition.forEach(p -> LOGGER.info("{}", p));
        Assertions.assertThat(partition).hasSize(84);
    }
}