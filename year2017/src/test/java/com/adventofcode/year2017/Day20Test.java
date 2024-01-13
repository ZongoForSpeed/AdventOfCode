package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day20Test {

    @Test
    void inputExample() {
        String input = """
                p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
                p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>""";

        Scanner scanner = new Scanner(input);

        Day20.Particle particle = Day20.nearestParticle(scanner);
        assertThat(particle).extracting(Day20.Particle::id).isEqualTo(0);
    }

    @Test
    void inputExamplePartTwo() {
        String input = """
                p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>
                p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>
                p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>
                p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>""";

        Scanner scanner = new Scanner(input);

        int size = Day20.particlesCollision(scanner);
        assertThat(size).isEqualTo(1);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/20/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day20.nearestParticle(scanner)).extracting(Day20.Particle::id).isEqualTo(125);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/20/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day20.particlesCollision(scanner)).isEqualTo(461);
        }
    }
}
