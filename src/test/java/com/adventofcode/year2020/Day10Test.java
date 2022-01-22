package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import com.adventofcode.utils.IntegerPair;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {

    @Test
    void testAdapterArray1() {
        LongList adapters = LongList.of(
                16L,
                10L,
                15L,
                5L,
                1L,
                11L,
                7L,
                19L,
                6L,
                12L,
                4L
        );

        IntegerPair adapter = Day10.jolterAdapter(adapters);
        assertThat(adapter.left()).isEqualTo(7);
        assertThat(adapter.right()).isEqualTo(5);

        assertThat(Day10.adapterArrangements(adapters)).isEqualTo(8);
    }

    @Test
    void testAdapterArray2() {
        LongList adapters = LongList.of(
                28L,
                33L,
                18L,
                42L,
                31L,
                14L,
                46L,
                20L,
                48L,
                47L,
                24L,
                23L,
                49L,
                45L,
                19L,
                38L,
                39L,
                11L,
                1L,
                32L,
                25L,
                35L,
                8L,
                17L,
                7L,
                9L,
                4L,
                2L,
                34L,
                10L,
                3L
        );

        IntegerPair adapter = Day10.jolterAdapter(adapters);
        assertThat(adapter.left()).isEqualTo(22);
        assertThat(adapter.right()).isEqualTo(10);

        assertThat(Day10.adapterArrangements(adapters)).isEqualTo(19208);
    }

    @Test
    void inputAdapterArray() throws IOException {
        LongList adapters = LongArrayList.toList(
                FileUtils.readLines("/2020/day/10/input").stream().mapToLong(Long::parseLong)
        );

        IntegerPair adapter = Day10.jolterAdapter(adapters);
        assertThat(adapter.left()).isEqualTo(72);
        assertThat(adapter.right()).isEqualTo(31);

        long result = (long) adapter.left() * adapter.right();
        assertThat(result).isEqualTo(2232);

        assertThat(Day10.adapterArrangements(adapters)).isEqualTo(173625106649344L);
    }
}
