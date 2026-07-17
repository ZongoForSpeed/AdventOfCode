package com.adventofcode.common.maths;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArithmeticTest {

    private static final long[] PRIMES = new long[]{
            2, 3, 5, 7, 11, 13, 17, 19, 23,
            29, 31, 37, 41, 43, 47, 53, 59,
            61, 67, 71, 73, 79, 83, 89, 97
    };

    @Test
    void testChineseRemainderTheorem() {
        assertThat(Arithmetic.chineseRemainderTheorem(new long[]{3, 5, 7}, new long[]{2, 3, 2}, PRIMES)).isEqualTo(23);
        assertThat(Arithmetic.chineseRemainderTheorem(new long[]{3, 4, 5}, new long[]{2, 3, 1}, PRIMES)).isEqualTo(11);
    }

    @Test
    void testChineseRemainderTheoremInvalidInput() {
        long[] modulos = {3, 5};
        long[] remainders = {2};
        assertThatThrownBy(() -> Arithmetic.chineseRemainderTheorem(modulos, remainders, PRIMES))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("modulos and reminders should have the same size !");
    }

    @Test
    void testModularInverse() {
        assertThat(Arithmetic.modularInverse(3, 11, PRIMES)).isEqualTo(4);
        assertThat(Arithmetic.modularInverse(97643, 456753, PRIMES)).isEqualTo(368123);
        assertThat(Arithmetic.modularInverse(107113, 3246999210L, PRIMES)).isEqualTo(180730717L);
    }

    @Test
    void testPhi() {
        assertThat(Arithmetic.phi(3246999210L, PRIMES)).isEqualTo(640120320L);
        assertThat(Arithmetic.phi(496L, PRIMES)).isEqualTo(240);
        assertThat(Arithmetic.phi(10, PRIMES)).isEqualTo(4);
        assertThat(Arithmetic.phi(1, PRIMES)).isEqualTo(1);
    }

    @Test
    void testSigma() {
        assertThat(Arithmetic.sigma(6, PRIMES)).isEqualTo(12);
        assertThat(Arithmetic.sigma(10, PRIMES)).isEqualTo(18);
        assertThat(Arithmetic.sigma(1, PRIMES)).isEqualTo(1);
    }

    @Test
    void testPower() {
        assertThat(Arithmetic.power(2, 10)).isEqualTo(1024);
        assertThat(Arithmetic.power(5, 3)).isEqualTo(125);
        assertThat(Arithmetic.power(10, 0)).isEqualTo(1);
    }

    @Test
    void testPowerMod() {
        assertThat(Arithmetic.powerMod(2, 10, 100)).isEqualTo(24);
        assertThat(Arithmetic.powerMod(97643, 276799, 456753)).isEqualTo(368123);
    }

    @Test
    void testLcm() {
        assertThat(Arithmetic.lcm(4, 6)).isEqualTo(12);
        assertThat(Arithmetic.lcm(4L, 6L)).isEqualTo(12L);
        assertThat(Arithmetic.lcm(4, 6, 8)).isEqualTo(24);
        assertThat(Arithmetic.lcm(4L, 6L, 8L)).isEqualTo(24L);
        assertThat(Arithmetic.lcm(1L, 2L, 3L, 4L, 5L)).isEqualTo(60L);
    }

    @Test
    void testGcd() {
        assertThat(Arithmetic.gcd(12, 18)).isEqualTo(6);
        assertThat(Arithmetic.gcd(12L, 18L)).isEqualTo(6L);
        assertThat(Arithmetic.gcd(12, 18, 24)).isEqualTo(6);
        assertThat(Arithmetic.gcd(12L, 18L, 24L)).isEqualTo(6L);
    }

    @Test
    void testCeil() {
        assertThat(Arithmetic.ceil(10, 3)).isEqualTo(4);
        assertThat(Arithmetic.ceil(10, 5)).isEqualTo(2);
        assertThat(Arithmetic.ceil(10L, 3L)).isEqualTo(4L);
        assertThat(Arithmetic.ceil(10L, 5L)).isEqualTo(2L);
    }

    @Test
    void testInverseModulaire() {
        assertThat(Arithmetic.inverseModulaire(3, 10)).isEqualTo(7);
        assertThat(Arithmetic.inverseModulaire(3, 11)).isEqualTo(4);
    }

    @Test
    void testBezout() {
        var result = Arithmetic.bezout(12, 18);
        assertThat(result.getLeft()).isEqualTo(6L);
        assertThat(12 * result.getMiddle() + 18 * result.getRight()).isEqualTo(6L);

        result = Arithmetic.bezout(101, 13);
        assertThat(result.getLeft()).isEqualTo(1L);
        assertThat(101 * result.getMiddle() + 13 * result.getRight()).isEqualTo(1L);
    }
}