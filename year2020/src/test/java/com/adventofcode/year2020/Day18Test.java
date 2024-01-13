package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test {

    @Test
    void testReadExpression() {
        assertThat(Day18.evalExpressionLR("1 + 2 * 3 + 4 * 5 + 6")).isEqualTo(71);
        assertThat(Day18.evalExpressionLR("1 + (2 * 3) + (4 * (5 + 6))")).isEqualTo(51);
        assertThat(Day18.evalExpressionLR("2 * 3 + (4 * 5)")).isEqualTo(26);
        assertThat(Day18.evalExpressionLR("5 + (8 * 3 + 9 + 3 * 4 * 3)")).isEqualTo(437);
        assertThat(Day18.evalExpressionLR("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")).isEqualTo(12240);
        assertThat(Day18.evalExpressionLR("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")).isEqualTo(13632);

        assertThat(Day18.evalExpressionAdditionFirst("1 + 2 * 3 + 4 * 5 + 6")).isEqualTo(231);
        assertThat(Day18.evalExpressionAdditionFirst("1 + (2 * 3) + (4 * (5 + 6))")).isEqualTo(51);
        assertThat(Day18.evalExpressionAdditionFirst("2 * 3 + (4 * 5)")).isEqualTo(46);
        assertThat(Day18.evalExpressionAdditionFirst("5 + (8 * 3 + 9 + 3 * 4 * 3)")).isEqualTo(1445);
        assertThat(Day18.evalExpressionAdditionFirst("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")).isEqualTo(669060);
        assertThat(Day18.evalExpressionAdditionFirst("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")).isEqualTo(23340);
    }

    @Test
    void inputReadExpression() throws IOException {
        List<String> lines = FileUtils.readLines("/2020/day/18/input");
        assertThat(lines.stream().mapToLong(Day18::evalExpressionLR).sum()).isEqualTo(654686398176L);
        assertThat(lines.stream().mapToLong(Day18::evalExpressionAdditionFirst).sum()).isEqualTo(8952864356993L);
    }
}
