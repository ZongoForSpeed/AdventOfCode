package com.adventofcode.year2016;

import com.adventofcode.common.point.Point2D;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day22Test extends AbstractTest {
    Day22Test() {
        super(2016, 22);
    }

    @Test
    void inputExample() {
        String input = """
                Filesystem            Size  Used  Avail  Use%
                /dev/grid/node-x0-y0   10T    8T     2T   80%
                /dev/grid/node-x0-y1   11T    6T     5T   54%
                /dev/grid/node-x0-y2   32T   28T     4T   87%
                /dev/grid/node-x1-y0    9T    7T     2T   77%
                /dev/grid/node-x1-y1    8T    0T     8T    0%
                /dev/grid/node-x1-y2   11T    7T     4T   63%
                /dev/grid/node-x2-y0   10T    6T     4T   60%
                /dev/grid/node-x2-y1    9T    8T     1T   88%
                /dev/grid/node-x2-y2    9T    6T     3T   66%""";

        Scanner scanner = new Scanner(input);
        long steps = Day22.countSteps(scanner, Point2D.of(2, 0));

        assertThat(steps).isEqualTo(7);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day22.countViablePairs(scanner)).isEqualTo(1034);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day22.countSteps(scanner, Point2D.of(37, 0))).isEqualTo(261);
    }

}
