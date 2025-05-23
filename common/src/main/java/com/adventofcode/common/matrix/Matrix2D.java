package com.adventofcode.common.matrix;

import com.google.errorprone.annotations.Immutable;

import java.math.BigInteger;

@Immutable
public record Matrix2D(long a11, long a12, long a21, long a22) {

    private static long multiplyMod(long a, long b, long modulus) {
        return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(modulus)).longValue();
    }

    public static Matrix2D power(Matrix2D base, long exponent, long modulus) {
        Matrix2D result = new Matrix2D(1, 0, 0, 1);
        while (exponent > 0) {
            if (exponent % 2 != 0)
                result = result.multiply(base, modulus);
            exponent /= 2;
            base = base.multiply(base, modulus);
        }
        return result;
    }

    public Matrix2D multiply(Matrix2D o, long modulus) {
        return new Matrix2D(
                (multiplyMod(a11, o.a11, modulus) + multiplyMod(a12, o.a21, modulus)) % modulus,
                (multiplyMod(a11, o.a12, modulus) + multiplyMod(a12, o.a22, modulus)) % modulus,
                (multiplyMod(a21, o.a11, modulus) + multiplyMod(a22, o.a21, modulus)) % modulus,
                (multiplyMod(a21, o.a12, modulus) + multiplyMod(a22, o.a22, modulus)) % modulus
        );
    }
}
