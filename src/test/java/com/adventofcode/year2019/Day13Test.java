package com.adventofcode.year2019;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {

    @Test
    void testBlockTiles() throws IOException {
        String line = FileUtils.readLine("/2019/day/13/input");
        long count = Day13.arkanoidBlockTiles(line);

        assertThat(count).isEqualTo(348);
    }

    @Test
    void testGame() throws IOException {
        String line = FileUtils.readLine("/2019/day/13/input");
        Day13.Arkanoid game = Day13.gamePartTwo(line);
        assertThat(game.getScore()).isEqualTo(16999);
    }


}
