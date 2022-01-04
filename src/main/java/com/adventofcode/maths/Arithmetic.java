package com.adventofcode.maths;

import org.apache.commons.lang3.tuple.Triple;

import java.math.BigInteger;

public final class Arithmetic {
    private Arithmetic() {
    }

    public static long chineseRemainderTheorem(long[] modulos, long[] reminders, long[] primes) {
        if (modulos.length != reminders.length) {
            throw new IllegalStateException("modulos and reminders should have the same size !");
        }

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

    public static long sigma(long n, long[] primes) {
        long result = 1;
        for (long p : primes) {
            if (p * p > n) {
                break;
            }
            if (n % p == 0) {
                int counter = 0;
                while (n % p == 0) {
                    n /= p;
                    ++counter;
                }
                result *= power(p, counter + 1) - 1;
                result /= p - 1;
            }
        }
        if (n > 1) {
            result *= (n + 1);
        }
        return result;
    }

    public static long power(long b, int e) {
        return BigInteger.valueOf(b).pow(e).longValue();
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
        Triple<Long, Long, Long> result = bezout(a, n);
        inverse = result.getMiddle();
        if (inverse < 0)
            return inverse + n;
        else
            return inverse;
    }

    public static Triple<Long, Long, Long> bezout(long a, long b) {
        // https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm#Pseudocode
        long s = 0;
        long oldS = 1;
        long t = 1;
        long oldT = 0;
        long r = b;
        long oldR = a;
        while (r != 0) {
            long quotient = oldR / r;
            long newR = oldR - quotient * r;
            long newS = oldS - quotient * s;
            long newT = oldT - quotient * t;

            oldR = r;
            oldS = s;
            oldT = t;

            r = newR;
            s = newS;
            t = newT;
        }

        return Triple.of(oldR, oldS, oldT);
    }


}
