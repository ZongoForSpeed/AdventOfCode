package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.OptionalInt;
import java.util.Scanner;

class Day20Test extends AbstractTest {
    Day20Test() {
        super(2018, 20);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        OptionalInt max = Day20.PartOne.furthestDistance(scanner.nextLine());
        Assertions.assertThat(max)
                .isPresent()
                .hasValue(3151);
    }

    @Override
    public void partTwo(Scanner scanner) {
        long door1000 = Day20.PartTwo.door1000(scanner.nextLine());
        Assertions.assertThat(door1000).isEqualTo(8784);
    }

}
