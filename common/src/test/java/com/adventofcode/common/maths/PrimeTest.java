package com.adventofcode.common.maths;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PrimeTest {

    @Test
    void sieve() {
        long[] primes = Prime.sieve(100);
        assertThat(primes)
                .containsExactly(2, 3, 5, 7, 11, 13, 17, 19, 23,
                        29, 31, 37, 41, 43, 47, 53, 59,
                        61, 67, 71, 73, 79, 83, 89, 97);
    }


    @Test
    void sieve2() {
        long[] primes = Prime.sieve2(100);
        assertThat(primes)
                .containsExactly(2, 3, 5, 7, 11, 13, 17, 19, 23,
                        29, 31, 37, 41, 43, 47, 53, 59,
                        61, 67, 71, 73, 79, 83, 89, 97);
    }

    @Test
    void testSieveIncludesLastNumber() {
        assertThat(Prime.sieve(8)).containsExactly(2, 3, 5, 7);
        assertThat(Prime.sieve(14)).containsExactly(2, 3, 5, 7, 11, 13);
        assertThat(Prime.sieve(1000)).containsExactly(Prime.sieve2(1000));
    }
}
