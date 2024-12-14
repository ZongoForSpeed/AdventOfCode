package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day20Test extends AbstractTest {
    Day20Test() {
        super(2017, 20);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day20.nearestParticle(scanner)).extracting(Day20.Particle::id).isEqualTo(125);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day20.particlesCollision(scanner)).isEqualTo(461);
    }
}
