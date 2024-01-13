package com.adventofcode.year2018;

import com.adventofcode.point.range.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day03.class);
    private static final Pattern PATTERN = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");

    private static List<Claim> readClaims(Scanner scanner) {
        List<Claim> claims = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                int id = Integer.parseInt(matcher.group(1));
                int x = Integer.parseInt(matcher.group(2));
                int y = Integer.parseInt(matcher.group(3));
                int dx = Integer.parseInt(matcher.group(4));
                int dy = Integer.parseInt(matcher.group(5));

                LOGGER.trace("elf {}, {},{} : {}x{}", id, x, y, dx, dy);
                Rectangle rectangle = Rectangle.of(x, x + dx - 1, y, y + dy - 1);
                LOGGER.debug("rectangle: {}", rectangle);
                claims.add(Claim.of(id, rectangle));
            }
        }
        return claims;
    }

    /**
     * --- Day 3: No Matter How You Slice It ---
     *
     * The Elves managed to locate the chimney-squeeze prototype fabric for
     * Santa's suit (thanks to someone who helpfully wrote its box IDs on the wall
     * of the warehouse in the middle of the night). Unfortunately, anomalies are
     * still affecting them - nobody can even agree on how to cut the fabric.
     *
     * The whole piece of fabric they're working on is a very large square - at
     * least 1000 inches on each side.
     *
     * Each Elf has made a claim about which area of fabric would be ideal for
     * Santa's suit. All claims have an ID and consist of a single rectangle with
     * edges parallel to the edges of the fabric. Each claim's rectangle is
     * defined as follows:
     *
     *   - The number of inches between the left edge of the fabric and the left
     *     edge of the rectangle.
     *   - The number of inches between the top edge of the fabric and the top
     *     edge of the rectangle.
     *   - The width of the rectangle in inches.
     *   - The height of the rectangle in inches.
     *
     * A claim like #123 @ 3,2: 5x4 means that claim ID 123 specifies a rectangle
     * 3 inches from the left edge, 2 inches from the top edge, 5 inches wide, and
     * 4 inches tall. Visually, it claims the square inches of fabric represented
     * by # (and ignores the square inches of fabric represented by .) in the
     * diagram below:
     *
     * ...........
     * ...........
     * ...#####...
     * ...#####...
     * ...#####...
     * ...#####...
     * ...........
     * ...........
     * ...........
     *
     * The problem is that many of the claims overlap, causing two or more claims
     * to cover part of the same areas. For example, consider the following
     * claims:
     *
     * #1 @ 1,3: 4x4
     * #2 @ 3,1: 4x4
     * #3 @ 5,5: 2x2
     *
     * Visually, these claim the following areas:
     *
     * ........
     * ...2222.
     * ...2222.
     * .11XX22.
     * .11XX22.
     * .111133.
     * .111133.
     * ........
     *
     * The four square inches marked with X are claimed by both 1 and 2. (Claim 3,
     * while adjacent to the others, does not overlap either of them.)
     *
     * If the Elves all proceed with their own plans, none of them will have
     * enough fabric. How many square inches of fabric are within two or more
     * claims?
     *
     * Your puzzle answer was 110383.
     */
    static long overlapArea(Scanner scanner) {
        List<Claim> claims = readClaims(scanner);

        List<Rectangle> intersections = new ArrayList<>();

        for (int i = 0; i < claims.size(); i++) {
            Claim claim1 = claims.get(i);
            for (int j = i + 1; j < claims.size(); j++) {
                Claim claim2 = claims.get(j);
                Optional<Rectangle> intersection = Rectangle.intersection(claim1.fabric(), claim2.fabric());
                intersection.ifPresent(intersections::add);
            }
        }

        LOGGER.trace("intersections={}", intersections);

        List<Rectangle> union = new ArrayList<>();
        for (Rectangle rectangle : intersections) {
            if (union.isEmpty()) {
                union.add(rectangle);
            } else {
                List<Rectangle> newUnion = new ArrayList<>();
                newUnion.add(rectangle);
                for (Rectangle r : union) {
                    if (Rectangle.intersect(r, rectangle)) {
                        List<Rectangle> difference = Rectangle.difference(r, rectangle);
                        newUnion.addAll(difference);
                    } else {
                        newUnion.add(r);
                    }
                }
                union = newUnion;
            }
        }

        LOGGER.trace("union={}", union);
        return union.stream().mapToLong(Rectangle::size).sum();
    }

    /**
     * --- Part Two ---
     *
     * Amidst the chaos, you notice that exactly one claim doesn't overlap by even
     * a single square inch of fabric with any other claim. If you can somehow
     * draw attention to it, maybe the Elves will be able to make Santa's suit
     * after all!
     *
     * For example, in the claims above, only claim 3 is intact after all claims
     * are made.
     *
     * What is the ID of the only claim that doesn't overlap?
     */
    static int findClaimId(Scanner scanner) {
        List<Claim> claims = readClaims(scanner);

        BitSet set = new BitSet(claims.size());
        set.set(0, claims.size());
        for (int i = 0; i < claims.size(); i++) {
            Claim claim1 = claims.get(i);
            for (int j = i + 1; j < claims.size(); j++) {
                Claim claim2 = claims.get(j);
                if (Rectangle.intersect(claim1.fabric(), claim2.fabric())) {
                    set.clear(i);
                    set.clear(j);
                }
            }
        }

        LOGGER.info("Set: {}", set);
        return set.stream().mapToObj(claims::get).mapToInt(Claim::id).findFirst().orElseThrow();
    }

    record Claim(int id, Rectangle fabric) {
        private static Claim of(int id, Rectangle fabric) {
            return new Claim(id, fabric);
        }
    }
}
