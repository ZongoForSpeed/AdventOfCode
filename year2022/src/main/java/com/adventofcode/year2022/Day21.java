package com.adventofcode.year2022;

import com.adventofcode.common.maths.Arithmetic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class Day21 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21.class);

    /**
     * --- Day 21: Monkey Math ---
     *
     * The monkeys are back! You're worried they're going to try to steal your
     * stuff again, but it seems like they're just holding their ground and making
     * various monkey noises at you.
     *
     * Eventually, one of the elephants realizes you don't speak monkey and comes
     * over to interpret. As it turns out, they overheard you talking about trying
     * to find the grove; they can show you a shortcut if you answer their riddle.
     *
     * Each monkey is given a job: either to yell a specific number or to yell the
     * result of a math operation. All of the number-yelling monkeys know their
     * number from the start; however, the math operation monkeys need to wait for
     * two other monkeys to yell a number, and those two other monkeys might also
     * be waiting on other monkeys.
     *
     * Your job is to work out the number the monkey named root will yell before
     * the monkeys figure it out themselves.
     *
     * For example:
     *
     * root: pppw + sjmn
     * dbpl: 5
     * cczh: sllz + lgvd
     * zczc: 2
     * ptdq: humn - dvpt
     * dvpt: 3
     * lfqf: 4
     * humn: 5
     * ljgn: 2
     * sjmn: drzm * dbpl
     * sllz: 4
     * pppw: cczh / lfqf
     * lgvd: ljgn * ptdq
     * drzm: hmdt - zczc
     * hmdt: 32
     *
     * Each line contains the name of a monkey, a colon, and then the job of that
     * monkey:
     *
     *   - A lone number means the monkey's job is simply to yell that number.
     *   - A job like aaaa + bbbb means the monkey waits for monkeys aaaa and
     *     bbbb to yell each of their numbers; the monkey then yells the sum of
     *     those two numbers.
     *   - aaaa - bbbb means the monkey yells aaaa's number minus bbbb's number.
     *   - Job aaaa * bbbb will yell aaaa's number multiplied by bbbb's number.
     *   - Job aaaa / bbbb will yell aaaa's number divided by bbbb's number.
     *
     * So, in the above example, monkey drzm has to wait for monkeys hmdt and zczc
     * to yell their numbers. Fortunately, both hmdt and zczc have jobs that
     * involve simply yelling a single number, so they do this immediately: 32 and
     * 2. Monkey drzm can then yell its number by finding 32 minus 2: 30.
     *
     * Then, monkey sjmn has one of its numbers (30, from monkey drzm), and
     * already has its other number, 5, from dbpl. This allows it to yell its own
     * number by finding 30 multiplied by 5: 150.
     *
     * This process continues until root yells a number: 152.
     *
     * However, your actual situation involves considerably more monkeys. What
     * number will the monkey named root yell?
     */
    public static long partOne(Scanner scanner) {
        Map<String, Monkey> monkeys = readInput(scanner);

        LOGGER.info("Monkeys = {}", monkeys);
        Monkey root = monkeys.get("root");
        LOGGER.info("Root = {}", root);

        Const expression = (Const) root.shout(new HashMap<>(), monkeys);
        return expression.value;
    }

    /**
     * --- Part Two ---
     *
     * Due to some kind of monkey-elephant-human mistranslation, you seem to have
     * misunderstood a few key details about the riddle.
     *
     * First, you got the wrong job for the monkey named root; specifically, you
     * got the wrong math operation. The correct operation for monkey root should
     * be =, which means that it still listens for two numbers (from the same two
     * monkeys as before), but now checks that the two numbers match.
     *
     * Second, you got the wrong monkey for the job starting with humn:. It isn't
     * a monkey - it's you. Actually, you got the job wrong, too: you need to
     * figure out what number you need to yell so that root's equality check
     * passes. (The number that appears after humn: in your input is now
     * irrelevant.)
     *
     * In the above example, the number you need to yell to pass root's equality
     * test is 301. (This causes root to get the same number, 150, from both of
     * its monkeys.)
     *
     * What number do you yell to pass root's equality test?
     */
    public static long partTwo(Scanner scanner) {
        Map<String, Monkey> monkeys = readInput(scanner);

        monkeys.put("humn", new Human("x"));

        LOGGER.info("Monkeys = {}", monkeys);
        CompositeMonkey root = (CompositeMonkey) monkeys.get("root");
        root = new CompositeMonkey("root", root.leftMonkey, '=', root.rightMonkey);
        LOGGER.info("Root = {}", root);

        Equation expression = (Equation) root.shout(new HashMap<>(), monkeys);
        LOGGER.info("Expression = {}", expression);
        Linear left = (Linear) expression.left;
        Const right = (Const) expression.right;
        long value = right.value;
        value *= left.c;
        value -= left.b;
        value /= left.a;
        return value;
    }

    private static Map<String, Monkey> readInput(Scanner scanner) {
        Map<String, Monkey> monkeys = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(":? ");
            LOGGER.debug("Line: {}", Arrays.toString(split));
            if (split.length == 2) {
                String name = split[0];
                long value = Long.parseLong(split[1]);
                monkeys.put(name, new SimpleMonkey(name, value));
            } else if (split.length == 4) {
                String name = split[0];
                String leftMonkey = split[1];
                char op = split[2].charAt(0);
                String rightMonkey = split[3];
                monkeys.put(name, new CompositeMonkey(name, leftMonkey, op, rightMonkey));
            } else {
                throw new IllegalStateException();
            }
        }
        return monkeys;
    }

    interface Monkey {
        String name();

        Expression shout(Map<String, Expression> cache, Map<String, Monkey> monkeys);
    }

    interface Expression {
        Expression op(char op, Expression right);
    }

    record SimpleMonkey(String name, long value) implements Monkey {

        @Override
        public Expression shout(Map<String, Expression> cache, Map<String, Monkey> monkeys) {
            return new Const(value);
        }
    }

    record Human(String name) implements Monkey {

        @Override
        public Expression shout(Map<String, Expression> cache, Map<String, Monkey> monkeys) {
            return new Linear(1, 0, 1);
        }
    }

    record CompositeMonkey(String name, String leftMonkey, char op, String rightMonkey) implements Monkey {

        @Override
        public Expression shout(Map<String, Expression> cache, Map<String, Monkey> monkeys) {
            Expression value = cache.get(name);
            if (value != null) {
                return value;
            }

            Expression leftShout = monkeys.get(leftMonkey).shout(cache, monkeys);
            Expression rightShout = monkeys.get(rightMonkey).shout(cache, monkeys);
            value = leftShout.op(op, rightShout);
            cache.put(name, value);
            return value;
        }
    }

    record Const(long value) implements Expression {

        @Override
        public String toString() {
            return "" + value;
        }

        @Override
        public Expression op(char op, Expression right) {
            return switch (right) {
                case Linear(long a, long b, long c) -> switch (op) {
                    case '-' -> Linear.build(-a, (value * c) - b, c);
                    case '+' -> Linear.build(a, b + (value * c), c);
                    case '*' -> Linear.build(a * value, b * value, c);
                    case '=' -> new Equation(this, right);
                    default -> throw new IllegalStateException();
                };
                case Const(long constValue) -> switch (op) {
                    case '-' -> new Const(value - constValue);
                    case '+' -> new Const(value + constValue);
                    case '*' -> new Const(value * constValue);
                    case '/' -> new Const(value / constValue);
                    case '=' -> new Equation(this, right);
                    default -> throw new IllegalStateException();
                };
                default -> throw new IllegalStateException();
            };
        }
    }

    record Linear(long a, long b, long c) implements Expression {
        @Override
        public String toString() {
            return "(" + a + "*x+" + b + ")/" + c;
        }

        public static Linear build(long a, long b, long c) {
            long gcd = Arithmetic.gcd(a, b, c);
            long newA = a / gcd;
            long newB = b / gcd;
            long newC = c / gcd;
            if (newC < 0) {
                newA *= -1;
                newB *= -1;
                newC *= -1;
            }
            return new Linear(newA, newB, newC);
        }

        @Override
        public Expression op(char op, Expression right) {
            return switch (right) {
                case Const value -> switch (op) {
                    case '-' -> build(a, b - (value.value * c), c);
                    case '+' -> build(a, b + (value.value * c), c);
                    case '*' -> build(a * value.value, b * value.value, c);
                    case '/' -> build(a, b, c * value.value);
                    case '=' -> new Equation(this, value);
                    default -> throw new IllegalStateException();
                };
                default -> throw new IllegalStateException();
            };
        }
    }

    record Equation(Expression left, Expression right) implements Expression {

        @Override
        public String toString() {
            return left + " = " + right;
        }

        @Override
        public Expression op(char op, Expression right) {
            throw new IllegalStateException();
        }
    }
}
