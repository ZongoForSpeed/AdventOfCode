package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2023, day = 25)
class Day25Test {
    @Test
    void inputExample() {
        var input = """
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
        var scanner = new Scanner(input);

        int result = Day25.PartOne.minimumCut(scanner);
        assertThat(result).isEqualTo(54);
    }

    @Test
    void partOne(Scanner scanner) {
        int result = Day25.PartOne.minimumCut(scanner);
        assertThat(result).isEqualTo(546804);
    }

    @Test
    void partTwo(Scanner scanner) {
        // No-Op
    }
}
