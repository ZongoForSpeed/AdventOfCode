package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test extends AbstractTest {
    Day25Test() {
        super(2023, 25);
    }

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

        var result = Day25.PartOne.minimumCut(scanner);
        assertThat(result).isEqualTo(54);
    }

    @Override
    public void partOne(Scanner scanner) {
        var result = Day25.PartOne.minimumCut(scanner);
        assertThat(result).isEqualTo(546804);
    }

    @Override
    public void partTwo(Scanner scanner) {
        // No-Op
    }
}
