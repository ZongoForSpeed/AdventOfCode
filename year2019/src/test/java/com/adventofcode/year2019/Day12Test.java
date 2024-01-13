package com.adventofcode.year2019;

import com.adventofcode.maths.Arithmetic;
import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12Test.class);
    @Test
    void testFirstExample() {
        List<Day12.Moon> moons = Stream.of(
                "<x=-1, y=0, z=2>",
                "<x=2, y=-10, z=-7>",
                "<x=4, y=-8, z=8>",
                "<x=3, y=5, z=-1>").map(Day12.Moon::parse).toList();

        for (Day12.Moon moon : moons) {
            LOGGER.debug("Moon: {}", moon);
        }
        for (long i = 1; i < 11; i++) {
            Day12.Moon.step(moons);
            LOGGER.debug("After {} step(s):\n{}", i, moons);
        }

        long totalEnergy = moons.stream().mapToLong(Day12.Moon::energy).sum();
        assertThat(totalEnergy).isEqualTo(179);
    }

    @Test
    void testSecondExample() {
        List<Day12.Moon> moons = Stream.of(
                "<x=-8, y=-10, z=0>",
                "<x=5, y=5, z=10>",
                "<x=2, y=-7, z=3>",
                "<x=9, y=-8, z=-3>").map(Day12.Moon::parse).toList();

        for (Day12.Moon moon : moons) {
            LOGGER.debug("Moon: {}", moon);
        }

        for (long i = 1; i < 101; i++) {
            Day12.Moon.step(moons);
            if (i % 10 == 0) {
                LOGGER.debug("After {} step(s):\n{}", i, moons);
            }
        }

        long totalEnergy = moons.stream().mapToLong(Day12.Moon::energy).sum();
        assertThat(totalEnergy).isEqualTo(1940);
    }

    @Test
    void testInputPartOne() throws IOException {
        List<Day12.Moon> moons = FileUtils.readLines("/2019/day/12/input").stream().map(Day12.Moon::parse).toList();

        long totalEnergy = Day12.computeTotalEnergy(moons);
        assertThat(totalEnergy).isEqualTo(12053);
    }

    @Test
    void testDriftingFirstExample() {
        List<Day12.Moon> moons = Stream.of(
                "<x=-1, y=0, z=2>",
                "<x=2, y=-10, z=-7>",
                "<x=4, y=-8, z=8>",
                "<x=3, y=5, z=-1>").map(Day12.Moon::parse).toList();

        List<Day12.Moon> moonCopyList = moons.stream().map(Day12.Moon::new).toList();

        long step = 0;
        do {
            step++;
            Day12.Moon.step(moons);
        } while (!moons.equals(moonCopyList));

        assertThat(step).isEqualTo(2772);

        long loopX = Day12.findLoop(moonCopyList, Day12.Moon::getPositionX, Day12.Moon::getVelocityX);
        long loopY = Day12.findLoop(moonCopyList, Day12.Moon::getPositionY, Day12.Moon::getVelocityY);
        long loopZ = Day12.findLoop(moonCopyList, Day12.Moon::getPositionZ, Day12.Moon::getVelocityZ);

        assertThat(loopX).isEqualTo(18);
        assertThat(loopY).isEqualTo(28);
        assertThat(loopZ).isEqualTo(44);

        long result = Arithmetic.lcm(loopX, loopY, loopZ);
        assertThat(result).isEqualTo(2772);
    }

    @Test
    void testDriftingSecondExample() {
        List<Day12.Moon> moons = Stream.of(
                "<x=-8, y=-10, z=0>",
                "<x=5, y=5, z=10>",
                "<x=2, y=-7, z=3>",
                "<x=9, y=-8, z=-3>").map(Day12.Moon::parse).toList();


        long loopX = Day12.findLoop(moons, Day12.Moon::getPositionX, Day12.Moon::getVelocityX);
        long loopY = Day12.findLoop(moons, Day12.Moon::getPositionY, Day12.Moon::getVelocityY);
        long loopZ = Day12.findLoop(moons, Day12.Moon::getPositionZ, Day12.Moon::getVelocityZ);

        assertThat(loopX).isEqualTo(2028);
        assertThat(loopY).isEqualTo(5898);
        assertThat(loopZ).isEqualTo(4702);

        long result = Arithmetic.lcm(loopX, loopY, loopZ);
        assertThat(result).isEqualTo(4686774924L);
    }

    @Test
    void testInputPartTwo() throws IOException {
        List<Day12.Moon> moons = FileUtils.readLines("/2019/day/12/input").stream().map(Day12.Moon::parse).toList();

        long loopX = Day12.findLoop(moons, Day12.Moon::getPositionX, Day12.Moon::getVelocityX);
        long loopY = Day12.findLoop(moons, Day12.Moon::getPositionY, Day12.Moon::getVelocityY);
        long loopZ = Day12.findLoop(moons, Day12.Moon::getPositionZ, Day12.Moon::getVelocityZ);

        assertThat(loopX).isEqualTo(186028L);
        assertThat(loopY).isEqualTo(286332L);
        assertThat(loopZ).isEqualTo(96236L);

        long result = Arithmetic.lcm(loopX, loopY, loopZ);
        assertThat(result).isEqualTo(320380285873116L);
    }

}
