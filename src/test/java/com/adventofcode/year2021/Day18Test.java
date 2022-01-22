package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test {

    @Test
    void inputExample() {
        assertThat(Day18.reduce("[[[[[9,8],1],2],3],4]")).hasToString("[[[[0,9],2],3],4]");
        assertThat(Day18.reduce("[7,[6,[5,[4,[3,2]]]]]")).hasToString("[7,[6,[5,[7,0]]]]");
        assertThat(Day18.reduce("[[6,[5,[4,[3,2]]]],1]")).hasToString("[[6,[5,[7,0]]],3]");
        assertThat(Day18.reduce("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")).hasToString("[[3,[2,[8,0]]],[9,[5,[7,0]]]]");
        assertThat(Day18.reduce("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")).hasToString("[[3,[2,[8,0]]],[9,[5,[7,0]]]]");

        assertThat(Day18.Snailfish.add(
                Day18.readSnailfish("[[[[4,3],4],4],[7,[[8,4],9]]]"),
                Day18.readSnailfish("[1,1]")).reduce()).hasToString("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");

        assertThat(Day18.sumPartOne(new Scanner("""
                [1,1]
                [2,2]
                [3,3]
                [4,4]"""))).hasToString("[[[[1,1],[2,2]],[3,3]],[4,4]]");

        assertThat(Day18.sumPartOne(new Scanner("""
                [1,1]
                [2,2]
                [3,3]
                [4,4]
                [5,5]"""))).hasToString("[[[[3,0],[5,3]],[4,4]],[5,5]]");

        assertThat(Day18.sumPartOne(new Scanner("""
                [1,1]
                [2,2]
                [3,3]
                [4,4]
                [5,5]
                [6,6]"""))).hasToString("[[[[5,0],[7,4]],[5,5]],[6,6]]");

        assertThat(Day18.sumPartOne(new Scanner("""
                [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
                [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
                [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
                [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
                [7,[5,[[3,8],[1,4]]]]
                [[2,[2,2]],[8,[8,1]]]
                [2,9]
                [1,[[[9,3],9],[[9,0],[0,7]]]]
                [[[5,[7,4]],7],1]
                [[[[4,2],2],6],[8,7]]"""))).hasToString("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");

        String input = """
                [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
                [[[5,[2,8]],4],[5,[[9,9],0]]]
                [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
                [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
                [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
                [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
                [[[[5,4],[7,7]],8],[[8,3],8]]
                [[9,3],[[9,9],[6,[4,9]]]]
                [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
                [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]""";

        assertThat(Day18.sumPartOne(new Scanner(input))).hasToString("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]");

        assertThat(Day18.sumPartTwo(new Scanner(input))).isEqualTo(3993);

    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day18Test.class.getResourceAsStream("/2021/day/18/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day18.sumPartOne(scanner).magnitude()).isEqualTo(2541);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day18Test.class.getResourceAsStream("/2021/day/18/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day18.sumPartTwo(scanner)).isEqualTo(4647);
        }
    }

}
