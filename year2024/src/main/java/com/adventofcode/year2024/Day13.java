package com.adventofcode.year2024;

import it.unimi.dsi.fastutil.Pair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day13 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day13.class);
    private static final Pattern BUTTON_PATTERN = Pattern.compile("Button \\w: X\\+(\\d+), Y\\+(\\d+)");
    private static final Pattern PRIZE_PATTERN = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");

    private static long solve(List<Pair<Matrix2D, Point2D>> claws, Point2D point2D) {
        long cost = 0;

        for (Pair<Matrix2D, Point2D> claw : claws) {
            Matrix2D m = claw.first();
            Point2D prize = claw.second().move(point2D);
            Optional<Matrix2D> inverse = m.inverse();
            if (inverse.isPresent()) {
                Optional<Point2D> result = inverse.get().multiply(prize);
                LOGGER.info("Result = {}", result);
                if (result.isPresent()) {
                    Point2D p = result.get();
                    cost += 3L * p.x() + p.y();
                }
            }
        }
        return cost;
    }

    private static List<Pair<Matrix2D, Point2D>> readInput(Scanner scanner) {
        List<Pair<Matrix2D, Point2D>> claws = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line1 = scanner.nextLine();
            if (StringUtils.isBlank(line1)) {
                continue;
            }

            Matcher matcher1 = BUTTON_PATTERN.matcher(line1);
            if (!matcher1.matches()) {
                throw new IllegalStateException("Cannot parse line :" + line1);
            }
            String line2 = scanner.nextLine();
            Matcher matcher2 = BUTTON_PATTERN.matcher(line2);
            if (!matcher2.matches()) {
                throw new IllegalStateException("Cannot parse line :" + line2);
            }
            String line3 = scanner.nextLine();
            Matcher matcher3 = PRIZE_PATTERN.matcher(line3);
            if (!matcher3.matches()) {
                throw new IllegalStateException("Cannot parse line :" + line3);
            }

            long dxA = Long.parseLong(matcher1.group(1));
            long dyA = Long.parseLong(matcher1.group(2));
            long dxB = Long.parseLong(matcher2.group(1));
            long dyB = Long.parseLong(matcher2.group(2));

            int priceX = Integer.parseInt(matcher3.group(1));
            int prizeY = Integer.parseInt(matcher3.group(2));

            LOGGER.info("dxA = {}, dyA = {}, dxB = {}, dyB = {}", dxA, dyA, dxB, dyB);
            LOGGER.info("X = {}, Y = {}", priceX, prizeY);

            Matrix2D m = new Matrix2D(dxA, dyA, dxB, dyB);
            Point2D prize = new Point2D(priceX, prizeY);

            claws.add(Pair.of(m, prize));
        }
        return claws;
    }

    /**
     * --- Day 13: Claw Contraption ---
     * <p>
     * Next up: the lobby of a resort on a tropical island. The Historians take a
     * moment to admire the hexagonal floor tiles before spreading out.
     * <p>
     * Fortunately, it looks like the resort has a new arcade! Maybe you can win
     * some prizes from the claw machines?
     * <p>
     * The claw machines here are a little unusual. Instead of a joystick or
     * directional buttons to control the claw, these machines have two buttons
     * labeled A and B. Worse, you can't just put in a token and play; it costs 3
     * tokens to push the A button and 1 token to push the B button.
     * <p>
     * With a little experimentation, you figure out that each machine's buttons
     * are configured to move the claw a specific amount to the right (along the X
     * axis) and a specific amount forward (along the Y axis) each time that
     * button is pressed.
     * <p>
     * Each machine contains one prize; to win the prize, the claw must be
     * positioned exactly above the prize on both the X and Y axes.
     * <p>
     * You wonder: what is the smallest number of tokens you would have to spend
     * to win as many prizes as possible? You assemble a list of every machine's
     * button behavior and prize location (your puzzle input). For example:
     * <p>
     * Button A: X+94, Y+34
     * Button B: X+22, Y+67
     * Prize: X=8400, Y=5400
     * <p>
     * Button A: X+26, Y+66
     * Button B: X+67, Y+21
     * Prize: X=12748, Y=12176
     * <p>
     * Button A: X+17, Y+86
     * Button B: X+84, Y+37
     * Prize: X=7870, Y=6450
     * <p>
     * Button A: X+69, Y+23
     * Button B: X+27, Y+71
     * Prize: X=18641, Y=10279
     * <p>
     * This list describes the button configuration and prize location of four
     * different claw machines.
     * <p>
     * For now, consider just the first claw machine in the list:
     * <p>
     * - Pushing the machine's A button would move the claw 94 units along the
     * X axis and 34 units along the Y axis.
     * - Pushing the B button would move the claw 22 units along the X axis and
     * 67 units along the Y axis.
     * - The prize is located at X=8400, Y=5400; this means that from the
     * claw's initial position, it would need to move exactly 8400 units
     * along the X axis and exactly 5400 units along the Y axis to be
     * perfectly aligned with the prize in this machine.
     * <p>
     * The cheapest way to win the prize is by pushing the A button 80 times and
     * the B button 40 times. This would line up the claw along the X axis
     * (because 80*94 + 40*22 = 8400) and along the Y axis (because
     * 80*34 + 40*67 = 5400). Doing this would cost 80*3 tokens for the A presses
     * and 40*1 for the B presses, a total of 280 tokens.
     * <p>
     * For the second and fourth claw machines, there is no combination of A and B
     * presses that will ever win a prize.
     * <p>
     * For the third claw machine, the cheapest way to win the prize is by pushing
     * the A button 38 times and the B button 86 times. Doing this would cost a
     * total of 200 tokens.
     * <p>
     * So, the most prizes you could possibly win is two; the minimum tokens you
     * would have to spend to win all (two) prizes is 480.
     * <p>
     * You estimate that each button would need to be pressed no more than 100
     * times to win a prize. How else would someone be expected to play?
     * <p>
     * Figure out how to win as many prizes as possible. What is the fewest tokens
     * you would have to spend to win all possible prizes?
     */
    public static long partOne(Scanner scanner) {
        List<Pair<Matrix2D, Point2D>> claws = readInput(scanner);

        return solve(claws, new Point2D(0, 0));
    }

    /**
     * --- Part Two ---
     * <p>
     * As you go to win the first prize, you discover that the claw is nowhere
     * near where you expected it would be. Due to a unit conversion error in your
     * measurements, the position of every prize is actually 10000000000000 higher
     * on both the X and Y axis!
     * <p>
     * Add 10000000000000 to the X and Y position of every prize. After making
     * this change, the example above would now look like this:
     * <p>
     * Button A: X+94, Y+34
     * Button B: X+22, Y+67
     * Prize: X=10000000008400, Y=10000000005400
     * <p>
     * Button A: X+26, Y+66
     * Button B: X+67, Y+21
     * Prize: X=10000000012748, Y=10000000012176
     * <p>
     * Button A: X+17, Y+86
     * Button B: X+84, Y+37
     * Prize: X=10000000007870, Y=10000000006450
     * <p>
     * Button A: X+69, Y+23
     * Button B: X+27, Y+71
     * Prize: X=10000000018641, Y=10000000010279
     * <p>
     * Now, it is only possible to win a prize on the second and fourth claw
     * machines. Unfortunately, it will take many more than 100 presses to do so.
     * <p>
     * Using the corrected prize coordinates, figure out how to win as many prizes
     * as possible. What is the fewest tokens you would have to spend to win all
     * possible prizes?
     */
    public static long partTwo(Scanner scanner) {
        List<Pair<Matrix2D, Point2D>> claws = readInput(scanner);

        return solve(claws, new Point2D(10000000000000L, 10000000000000L));
    }

    private record Matrix2D(long a11, long a12, long a21, long a22, long denominator) {
        long det() {
            return a11 * a22 - a12 * a21;
        }

        Matrix2D(long a11, long a12, long a21, long a22) {
            this(a11, a12, a21, a22, 1L);
        }

        Optional<Matrix2D> inverse() {
            long det = det();

            switch (Long.signum(det)) {
                case 0 -> {
                    return Optional.empty();
                }
                case 1 -> {
                    return Optional.of(new Matrix2D(a22, -a21, -a12, a11, det));
                }
                case -1 -> {
                    return Optional.of(new Matrix2D(-a22, a21, a12, -a11, -det));
                }
                default -> throw new IllegalStateException();
            }
        }

        Optional<Point2D> multiply(Point2D v) {
            BigInteger x = multiply(v.x(), a11).add(multiply(v.y(), a12));
            BigInteger y = multiply(v.x(), a21).add(multiply(v.y(), a22));

            BigInteger denom = BigInteger.valueOf(this.denominator);
            if (x.mod(denom).equals(BigInteger.ZERO) && y.mod(denom).equals(BigInteger.ZERO)) {
                x = x.divide(denom);
                y = y.divide(denom);
                return Optional.of(new Point2D(x.longValue(), y.longValue()));
            }

            return Optional.empty();
        }

        private static BigInteger multiply(long a, long b) {
            return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b));
        }

    }

    private record Point2D(long x, long y) {
        Point2D move(Point2D d) {
            return new Point2D(x + d.x, y + d.y);
        }
    }
}
