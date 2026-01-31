package com.adventofcode.common.maths;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.data.Offset.offset;

class GaussJordanTest {

    @Test
    void testGaussJordanUniqueSolution() {
        double[][] a = {
                {0, 2, 1, 4},
                {1, 1, 2, 6},
                {2, 1, 1, 7}
        };

        // Order of Matrix(n)
        int n = 3;

        GaussJordan gaussJordan = new GaussJordan(a, n);

        double[] solution = gaussJordan.solve();

        assertThat(solution).containsExactly(2.2, 1.4, 1.2);
    }

    @Test
    void testSolveUniqueSolution3x3() {
        // 2x + y - z = 8
        // -3x - y + 2z = -11
        // -2x + y + 2z = -3
        // Solution: x=2, y=3, z=-1
        double[][] matrixA = {
                {2, 1, -1},
                {-3, -1, 2},
                {-2, 1, 2}
        };
        double[] vectorB = {8, -11, -3};

        GaussJordan solver = new GaussJordan(matrixA, vectorB);
        double[] result = solver.solve();

        assertThat(result[0]).isCloseTo(2.0, offset(0.0001));
        assertThat(result[1]).isCloseTo(3.0, offset(0.0001));
        assertThat(result[2]).isCloseTo(-1.0, offset(0.0001));
    }

    @Test
    void testNoSolution() {
        // x + y = 2
        // x + y = 3
        double[][] matrixA = {
                {1, 1},
                {1, 1}
        };
        double[] vectorB = {2, 3};

        GaussJordan solver = new GaussJordan(matrixA, vectorB);
        assertThatThrownBy(solver::solve)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testInfiniteSolutions() {
        // x + y = 2
        // 2x + 2y = 4
        double[][] matrixA = {
                {1, 1},
                {1, 1}
        };
        double[] vectorB = {2, 2};

        GaussJordan solver = new GaussJordan(matrixA, vectorB);
        assertThatThrownBy(solver::solve)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Infinite Solutions Exists");
    }
}