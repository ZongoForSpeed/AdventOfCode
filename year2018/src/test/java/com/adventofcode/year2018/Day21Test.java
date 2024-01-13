package com.adventofcode.year2018;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

class Day21Test {


    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day21Test.class.getResourceAsStream("/2018/day/21/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            int result = Day21.PartOne.run(scanner);
            Assertions.assertThat(result).isEqualTo(16134795);
        }

    }


    @Disabled
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day21Test.class.getResourceAsStream("/2018/day/21/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            int result = Day21.PartTwo.run(scanner);
            Assertions.assertThat(result).isEqualTo(14254292);
        }

    }
}
