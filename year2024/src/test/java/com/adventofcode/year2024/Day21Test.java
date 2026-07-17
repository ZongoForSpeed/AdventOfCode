package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test extends AbstractTest {


    @Test
    void inputExample1() {
        var input = "029A";

        var complexity = Day21.findComplexity(input, 3);

        assertThat(complexity).isEqualTo(1972);
    }

    @Test
    void inputExample2() {
        var input = "179A";

        var complexity = Day21.findComplexity(input, 3);

        assertThat(complexity).isEqualTo(12172);
    }

    @Test
    void inputExample3() {
        var input = """
                029A
                980A
                179A
                456A
                379A""";

        try (var scanner = new Scanner(input)) {
            var complexity = Day21.partOne(scanner);
            assertThat(complexity).isEqualTo(126384);
        }
    }

    protected Day21Test() {
        super(2024, 21);
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        var complexity = Day21.partOne(scanner);
        assertThat(complexity).isEqualTo(125742);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        var complexity = Day21.partTwo(scanner);
        assertThat(complexity).isEqualTo(157055032722640L);
    }
}
