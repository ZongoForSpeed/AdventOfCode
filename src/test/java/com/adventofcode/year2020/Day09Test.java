package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.OptionalLong;

import static org.assertj.core.api.Assertions.assertThat;

class Day09Test {

    @Test
    void testEncodingError() {
        LongList codes = LongList.of(
                35L,
                20L,
                15L,
                25L,
                47L,
                40L,
                62L,
                55L,
                65L,
                95L,
                102L,
                117L,
                150L,
                182L,
                127L,
                219L,
                299L,
                277L,
                309L,
                576L
        );

        OptionalLong encodingError = Day09.findEncodingError(codes, 5);
        assertThat(encodingError)
                .isPresent()
                .hasValue(127);

        LongList contiguousSet = Day09.findContiguousSet(codes, 127);
        assertThat(contiguousSet.toLongArray()).containsExactly(15L, 25L, 47L, 40L);
        long weakness = Collections.max(contiguousSet) + Collections.min(contiguousSet);
        assertThat(weakness).isEqualTo(62);
    }

    @Test
    void inputEncodingError() throws IOException {
        LongList codes = LongArrayList.toList(
                FileUtils.readLines("/2020/day/9/input").stream().mapToLong(Long::parseLong)
        );

        OptionalLong encodingError = Day09.findEncodingError(codes, 25);
        assertThat(encodingError)
                .isPresent()
                .hasValue(3199139634L);

        LongList contiguousSet = Day09.findContiguousSet(codes, encodingError.getAsLong());
        assertThat(contiguousSet.toLongArray()).containsExactly(
                122504099L,
                114441245L,
                143793543L,
                205448799L,
                209677440L,
                134686885L,
                168119071L,
                212819308L,
                262089204L,
                145005770L,
                141254137L,
                324118685L,
                155188637L,
                212067968L,
                311912614L,
                167400001L,
                168612228L);

        long weakness = Collections.max(contiguousSet) + Collections.min(contiguousSet);
        assertThat(weakness).isEqualTo(438559930L);
    }
}
