package com.adventofcode.common.maths;

import com.adventofcode.common.maths.GaussJordan;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

class GaussJordanTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GaussJordanTest.class);
    public static void main(String[] args) {
        double[][] a = {
                {0, 2, 1, 4},
                {1, 1, 2, 6},
                {2, 1, 1, 7}
        };

        // Order of Matrix(n)
        int n = 3;

        GaussJordan gaussJordan = new GaussJordan(a, n);

        double[] solution = gaussJordan.solve();

        LOGGER.info("Solution: {}", Arrays.toString(solution));

        Assertions.assertThat(solution).containsExactly(2.2, 1.4, 1.2);
    }

}