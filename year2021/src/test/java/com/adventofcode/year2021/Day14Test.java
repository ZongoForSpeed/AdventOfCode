package com.adventofcode.year2021;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2021, day = 14)
class Day14Test {
    @Test
    void inputExample() {
        var input = """
                NNCB
                
                CH -> B
                HH -> N
                CB -> H
                NH -> C
                HB -> C
                HC -> B
                HN -> C
                NN -> C
                BH -> H
                NC -> B
                NB -> B
                BN -> B
                BB -> N
                BC -> B
                CC -> N
                CN -> C""";


        assertThat(Day14.extendedPolymerization(new Scanner(input), 10)).isEqualTo(1588);
        assertThat(Day14.extendedPolymerization(new Scanner(input), 40)).isEqualTo(2188189693529L);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day14.extendedPolymerization(scanner, 10)).isEqualTo(3411);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day14.extendedPolymerization(scanner, 40)).isEqualTo(7477815755570L);
    }

}
