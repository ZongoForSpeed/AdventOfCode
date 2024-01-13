package com.adventofcode.year2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test {

    @Test
    void inputExample() {
        String input = """
                jqt: rhn xhk nvd
                rsh: frs pzl lsr
                xhk: hfx
                cmg: qnr nvd lhk bvb
                rhn: xhk bvb hfx
                bvb: xhk hfx
                pzl: lsr hfx nvd
                qnr: nvd
                ntq: jqt hfx bvb xhk
                nvd: lhk
                lsr: lhk
                rzs: qnr cmg lsr rsh
                frs: qnr lhk lsr""";
        Scanner scanner = new Scanner(input);

        int result = Day25.PartOne.minimumCut(scanner);
        assertThat(result).isEqualTo(54);
    }


    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day25Test.class.getResourceAsStream("/2023/day/25/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            int result = Day25.PartOne.minimumCut(scanner);
            assertThat(result).isEqualTo(546804);
        }
    }
}
