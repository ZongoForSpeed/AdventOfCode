package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day25Test {

    @Test
    void inputExample() {
        assertThat(Day25.findCode(6, 6)).isEqualTo(27995004);
    }

    @Test
    void inputPartOne() {
        // To continue, please consult the code grid in the manual.  Enter the code at row 2978, column 3083.
        assertThat(Day25.findCode(2978, 3083)).isEqualTo(2650453);
    }
}
