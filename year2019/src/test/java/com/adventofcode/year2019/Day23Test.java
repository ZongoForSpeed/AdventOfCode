package com.adventofcode.year2019;

import com.adventofcode.common.utils.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class Day23Test {

    @Test
    void testNetworkInterfaceController() throws IOException, InterruptedException {
        String program = FileUtils.readLine("/2019/day/23/input");
        assertThat(Day23.runNetworkInterfaceController(program)).isEqualTo(20367);
    }

    @Disabled
    void testNotAlwaysTransmitting() throws IOException, InterruptedException {
        String program = FileUtils.readLine("/2019/day/23/input");
        assertThat(Day23.runNotAlwaysTransmitting(program)).isEqualTo(15080);
    }

}
