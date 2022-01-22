package com.adventofcode.year2015;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Day04 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day04.class);

    private Day04() {
        // No-Op
    }

    /**
     * --- Day 4: The Ideal Stocking Stuffer ---
     *
     * Santa needs help mining some AdventCoins (very similar to bitcoins) to use
     * as gifts for all the economically forward-thinking little girls and boys.
     *
     * To do this, he needs to find MD5 hashes which, in hexadecimal, start with
     * at least five zeroes. The input to the MD5 hash is some secret key (your
     * puzzle input, given below) followed by a number in decimal. To mine
     * AdventCoins, you must find Santa the lowest positive number (no leading
     * zeroes: 1, 2, 3, ...) that produces such a hash.
     *
     * For example:
     *
     *   - If your secret key is abcdef, the answer is 609043, because the MD5
     *     hash of abcdef609043 starts with five zeroes (000001dbbfa...), and
     *     it is the lowest such number to do so.
     *   - If your secret key is pqrstuv, the lowest number it combines with to
     *     make an MD5 hash starting with five zeroes is 1048970; that is, the
     *     MD5 hash of pqrstuv1048970 looks like 000006136ef....
     *
     * Your puzzle input is yzbqklnj.
     *
     * Your puzzle answer was 282749.
     */
    public static long adventCoinsHashPartOne(String secretKey) {
        String pattern = "0".repeat(5);
        for (int i = 1; ; ++i) {
            String hash = DigestUtils.md5Hex(secretKey + i);
            if (hash.startsWith(pattern)) {
                LOGGER.info("hash({}{}) = {}", secretKey, i, hash);
                return i;
            }
        }
    }

    /**
     * --- Part Two ---
     *
     * Now find one that starts with six zeroes.
     */
    public static long adventCoinsHashPartTwo(String secretKey) {
        for (int i = 1; ; ++i) {
            byte[] hash = DigestUtils.md5(secretKey + i);
            if (hash[0] == 0 && hash[1] == 0 && hash[2] == 0) {
                LOGGER.info("hash({}{}) = {}", secretKey, i, Hex.encodeHex(hash));
                return i;
            }
        }
    }

}
