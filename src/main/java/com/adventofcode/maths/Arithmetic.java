package com.adventofcode.maths;

import org.apache.commons.lang3.tuple.Triple;

import java.math.BigInteger;

public final class Arithmetic {
    private Arithmetic() {
    }

    public static long chineseRemainderTheorem(long[] modulos, long[] reminders, long[] primes) {
        assert (modulos.length == reminders.length);

        long n = 1;
        for (long modulo : modulos) {
            n *= modulo;
        }

        long result = 0;
        for (int i = 0; i < reminders.length; i++) {
            long modulo = modulos[i];
            long reminder = reminders[i];

            long r = n / modulo;
            result += r * modularInverse(r, modulo, primes) * reminder;
            result %= n;
        }

        return result;

    }

    public static long modularInverse(long a, long n, long[] primes) {
        long phi = phi(n, primes);
        return powerMod(a, phi - 1, n);
    }

    public static long phi(long n, long[] primes) {
        long result = n;
        for (long p : primes) {
            if (p * p > n) {
                break;
            }
            if (n % p == 0) {
                result = result - result / p;
                while (n % p == 0) {
                    n /= p;
                }
            }
        }
        if (n > 1) {
            result = result - result / n;
        }
        return result;
    }

    public static long powerMod(long b, long e, long modulo) {
        return BigInteger.valueOf(b).modPow(BigInteger.valueOf(e), BigInteger.valueOf(modulo)).longValue();
    }

    public static int lcm(int a, int b, int c) {
        int lcm = lcm(a, b);
        return c * (lcm / gcd(lcm, c));
    }

    public static long lcm(long a, long b, long c) {
        long lcm = lcm(a, b);
        return c * (lcm / gcd(lcm, c));
    }

    public static int gcd(int a, int b, int c) {
        return gcd(a, gcd(b, c));
    }

    public static long gcd(long a, long b, long c) {
        return gcd(a, gcd(b, c));
    }

    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    public static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    public static int gcd(int a, int b) {
        if (a == 0)
            return b;
        if (b == 0)
            return a;

        int gcd;
        while (true) {
            gcd = a % b;
            if (gcd == 0) {
                gcd = b;
                break;
            }
            a = b;
            b = gcd;
        }
        return gcd;
    }

    public static long gcd(long a, long b) {
        if (a == 0)
            return b;
        if (b == 0)
            return a;

        long gcd;
        while (true) {
            gcd = a % b;
            if (gcd == 0) {
                gcd = b;
                break;
            }
            a = b;
            b = gcd;
        }
        return gcd;
    }

    public static long ceil(long n, long d) {
        return (n + d - 1) / d;
    }

    public static int ceil(int n, int d) {
        return (n + d - 1) / d;
    }

    public static long inverseModulaire(long a, long n) {
        long inverse;
        Triple<Long, Long, Long> result = Bezout(a, n);
        inverse = result.getMiddle();
        if (inverse < 0)
            return inverse + n;
        else
            return inverse;
    }

    public static Triple<Long, Long, Long> Bezout(long a, long b) {
        // https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm#Pseudocode
        long s = 0, old_s = 1;
        long t = 1, old_t = 0;
        long r = b, old_r = a;
        while (r != 0) {
            long quotient = old_r / r;
            long new_r = old_r - quotient * r;
            long new_s = old_s - quotient * s;
            long new_t = old_t - quotient * t;

            old_r = r;
            old_s = s;
            old_t = t;

            r = new_r;
            s = new_s;
            t = new_t;
        }

        return Triple.of(old_r, old_s, old_t);
    }


}
