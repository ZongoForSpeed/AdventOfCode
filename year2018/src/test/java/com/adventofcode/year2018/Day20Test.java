package com.adventofcode.year2018;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Scanner;

class Day20Test {

    @Test
    void example() {

        OptionalInt max = Day20.PartOne.furthestDistance("^ENWWW(NEEE|SSE(EE|N))$");
        Assertions.assertThat(max)
                .isPresent()
                .hasValue(10);

        max = Day20.PartOne.furthestDistance("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$");
        Assertions.assertThat(max)
                .isPresent()
                .hasValue(18);

        max = Day20.PartOne.furthestDistance("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$");
        Assertions.assertThat(max)
                .isPresent()
                .hasValue(23);

        max = Day20.PartOne.furthestDistance("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$");
        Assertions.assertThat(max)
                .isPresent()
                .hasValue(31);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day20Test.class.getResourceAsStream("/2018/day/20/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            OptionalInt max = Day20.PartOne.furthestDistance(scanner.nextLine());
            Assertions.assertThat(max)
                    .isPresent()
                    .hasValue(3151);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day20Test.class.getResourceAsStream("/2018/day/20/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            long door1000 = Day20.PartTwo.door1000(scanner.nextLine());
            Assertions.assertThat(door1000).isEqualTo(8784);
        }
    }

}
