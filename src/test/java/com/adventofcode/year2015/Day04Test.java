package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day04Test {

    @Test
    void inputExample() {
        assertThat(Day04.adventCoinsHashPartOne("abcdef")).isEqualTo(609043);
        assertThat(Day04.adventCoinsHashPartOne("pqrstuv")).isEqualTo(1048970);
    }

    @Test
    void inputPartOne() {
        assertThat(Day04.adventCoinsHashPartOne("yzbqklnj")).isEqualTo(282749);
    }

    @Test
    void inputPartTwo() {
        assertThat(Day04.adventCoinsHashPartTwo("yzbqklnj")).isEqualTo(9962624);
    }
}
