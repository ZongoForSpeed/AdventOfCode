package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day24Test extends AbstractTest {
    Day24Test() {
        super(2018, 24);
    }

    @Test
    void inputExample() {
        String input = """
                Immune System:
                17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2
                989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3
                
                Infection:
                801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1
                4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4""";

        {
            Scanner scanner = new Scanner(input);
            Integer battle = Day24.PartOne.immuneSystemSimulator(scanner);
            Assertions.assertThat(battle).isEqualTo(5216);
        }

        {
            Scanner scanner = new Scanner(input);
            Integer battle = Day24.PartTwo.immuneSystemSimulator(scanner);
            Assertions.assertThat(battle).isEqualTo(51);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        Integer battle = Day24.PartOne.immuneSystemSimulator(scanner);
        Assertions.assertThat(battle).isEqualTo(15392);
    }

    @Override
    public void partTwo(Scanner scanner) {
        Integer battle = Day24.PartTwo.immuneSystemSimulator(scanner);
        Assertions.assertThat(battle).isEqualTo(1092);
    }

}
