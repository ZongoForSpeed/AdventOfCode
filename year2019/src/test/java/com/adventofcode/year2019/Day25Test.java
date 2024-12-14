package com.adventofcode.year2019;

import com.adventofcode.test.AbstractTest;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test extends AbstractTest {
    Day25Test() {
        super(2019, 25);
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        String line = scanner.nextLine();
        assertThat(Day25.findPassword(line)).isEqualTo("""
                == Pressure-Sensitive Floor ==
                Analyzing...
                
                Doors here lead:
                - north
                
                A loud, robotic voice says "Analysis complete! You may proceed." and you enter the cockpit.
                Santa notices your small droid, looks puzzled for a moment, realizes what has happened, and radios your ship directly.
                "Oh, hello! You should be able to get in by typing 4362 on the keypad at the main airlock.\"""");

    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        // No-Op
    }
}
