package com.adventofcode.common.matrix;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Matrix2DTest {

    @Test
    void testMultiply() {
        var m1 = new Matrix2D(1, 2, 3, 4);
        var m2 = new Matrix2D(5, 6, 7, 8);
        var modulus = 1000L;

        // [1 2] * [5 6] = [1*5+2*7 1*6+2*8] = [19 22]
        // [3 4]   [7 8]   [3*5+4*7 3*6+4*8]   [43 50]
        Matrix2D result = m1.multiply(m2, modulus);

        assertThat(result).isEqualTo(new Matrix2D(19, 22, 43, 50));
    }

    @Test
    void testMultiplyMod() {
        var m1 = new Matrix2D(10, 20, 30, 40);
        var m2 = new Matrix2D(5, 6, 7, 8);
        var modulus = 17L;

        // [10 20] * [5 6] = [10*5+20*7 10*6+20*8] = [190 220]
        // [30 40]   [7 8]   [30*5+40*7 30*6+40*8]   [430 500]
        // 190 % 17 = 3 (17*11=187)
        // 220 % 17 = 16 (17*12=204, 17*13=221)
        // 430 % 17 = 5 (17*25=425)
        // 500 % 17 = 7 (17*29=493)
        Matrix2D result = m1.multiply(m2, modulus);

        assertThat(result).isEqualTo(new Matrix2D(3, 16, 5, 7));
    }

    @Test
    void testPower() {
        var base = new Matrix2D(1, 1, 1, 0);
        var modulus = 1000L;

        // Fib: [1 1]^n = [F(n+1) F(n)]
        //      [1 0]     [F(n)   F(n-1)]
        // n=1: [1 1]
        // n=2: [2 1]
        //      [1 1]
        // n=3: [3 2]
        //      [2 1]
        // n=4: [5 3]
        //      [3 2]
        // n=5: [8 5]
        //      [5 3]
        // n=6: [13 8]
        //      [8 5]
        Matrix2D result = Matrix2D.power(base, 6, modulus);

        assertThat(result).isEqualTo(new Matrix2D(13, 8, 8, 5));
    }

    @Test
    void testPowerLarge() {
        var base = new Matrix2D(1, 1, 1, 0);
        var modulus = 1000000007L;

        // F(10) = 55
        Matrix2D result = Matrix2D.power(base, 10, modulus);
        assertThat(result).isEqualTo(new Matrix2D(89, 55, 55, 34));
    }

    @Test
    void testMultiplyLargeValues() {
        var large = Integer.MAX_VALUE + 100L;
        var m1 = new Matrix2D(large, 0, 0, large);
        var m2 = new Matrix2D(large, 0, 0, large);
        var modulus = 1000000007L;

        // large % mod = 2147483647 + 100 = 2147483747
        // 2147483747 % 1000000007 = 147483733
        // result = 147483733 * 147483733 % 1000000007
        // 147483733^2 = 21751451500000000 (roughly)
        // BigInteger should handle this in multiplyMod

        Matrix2D result = m1.multiply(m2, modulus);
        var expected = (147483733L * 147483733L) % modulus;
        assertThat(result).isEqualTo(new Matrix2D(expected, 0, 0, expected));
    }
}
