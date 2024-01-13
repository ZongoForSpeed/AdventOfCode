package com.adventofcode.year2019;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test {
    @Test
    void testFindPassword() throws IOException {
        String line = FileUtils.readLine("/2019/day/25/input");
        assertThat(Day25.findPassword(line)).isEqualTo("""
                == Pressure-Sensitive Floor ==
                Analyzing...

                Doors here lead:
                - north

                A loud, robotic voice says "Analysis complete! You may proceed." and you enter the cockpit.
                Santa notices your small droid, looks puzzled for a moment, realizes what has happened, and radios your ship directly.
                "Oh, hello! You should be able to get in by typing 4362 on the keypad at the main airlock.\"""");
    }

}
