package com.adventofcode.year2018;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day04Test extends AbstractTest {
    Day04Test() {
        super(2018, 4);
    }

    @Test
    void inputExample() {
        String input = """
                [1518-11-01 00:00] Guard #10 begins shift
                [1518-11-01 00:05] falls asleep
                [1518-11-01 00:25] wakes up
                [1518-11-01 00:30] falls asleep
                [1518-11-01 00:55] wakes up
                [1518-11-01 23:58] Guard #99 begins shift
                [1518-11-02 00:40] falls asleep
                [1518-11-02 00:50] wakes up
                [1518-11-03 00:05] Guard #10 begins shift
                [1518-11-03 00:24] falls asleep
                [1518-11-03 00:29] wakes up
                [1518-11-04 00:02] Guard #99 begins shift
                [1518-11-04 00:36] falls asleep
                [1518-11-04 00:46] wakes up
                [1518-11-05 00:03] Guard #99 begins shift
                [1518-11-05 00:45] falls asleep
                [1518-11-05 00:55] wakes up""";

        assertThat(Day04.findStrategy1(new Scanner(input))).isEqualTo(240);

        assertThat(Day04.findStrategy2(new Scanner(input))).isEqualTo(4455);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day04.findStrategy1(scanner)).isEqualTo(94040);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day04.findStrategy2(scanner)).isEqualTo(39940);
    }

}
