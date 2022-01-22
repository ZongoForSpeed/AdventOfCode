package com.adventofcode.year2017;

import com.adventofcode.map.BooleanMap;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class Day14Test {

    @Test
    void inputExample() {
        assertThat(Day14.toBooleanList("a0c2017")).hasToString("{0, 2, 8, 9, 14, 23, 25, 26, 27}");
        BooleanMap disk = Day14.buildDisk("flqrgnkx");
        assertThat(disk.cardinality()).isEqualTo(8108);
        assertThat(Day14.countRegion(disk)).isEqualTo(1242);
    }

    @Test
    void inputPartOne() throws IOException {
        assertThat(Day14.buildDisk("jxqlasbh").cardinality()).isEqualTo(8140);
    }

    @Test
    void inputPartTwo() throws IOException {
        assertThat(Day14.countRegion(Day14.buildDisk("jxqlasbh"))).isEqualTo(1182);
    }
}
