package com.adventofcode.year2020;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.function.BinaryOperator;

public final class Day18 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day18.class);

    private Day18() {
        // No-Op
    }

    private static long evalExpressionLR(Iterator<Character> iterator) {
        Deque<Long> stack = new ArrayDeque<>();
        BinaryOperator<Long> operation = null;
        while (iterator.hasNext()) {
            char currentToken = iterator.next();
            switch (currentToken) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> stack.add((long) (currentToken - '0'));
                case '(' -> stack.add(evalExpressionLR(iterator));
                case ')' -> {
                    if (operation != null) {
                        stack.add(operation.apply(stack.removeLast(), stack.removeLast()));
                    }

                    return stack.removeLast();
                }
                case '+' -> {
                    if (operation != null) {
                        stack.add(operation.apply(stack.removeLast(), stack.removeLast()));
                    }
                    operation = Long::sum;
                }
                case '*' -> {
                    if (operation != null) {
                        stack.add(operation.apply(stack.removeLast(), stack.removeLast()));
                    }
                    operation = (a, b) -> a * b;
                }
            }
        }

        if (operation != null) {
            stack.add(operation.apply(stack.removeLast(), stack.removeLast()));
        }

        return stack.removeLast();
    }

    /**
     * --- Day 18: Operation Order ---
     * <p>
     * As you look out the window and notice a heavily-forested continent slowly
     * appear over the horizon, you are interrupted by the child sitting next to
     * you. They're curious if you could help them with their math homework.
     * <p>
     * Unfortunately, it seems like this "math" follows different rules than you
     * remember.
     * <p>
     * The homework (your puzzle input) consists of a series of expressions that
     * consist of addition (+), multiplication (*), and parentheses ((...)). Just
     * like normal math, parentheses indicate that the expression inside must be
     * evaluated before it can be used by the surrounding expression. Addition
     * still finds the sum of the numbers on both sides of the operator, and
     * multiplication still finds the product.
     * <p>
     * However, the rules of operator precedence have changed. Rather than
     * evaluating multiplication before addition, the operators have the same
     * precedence, and are evaluated left-to-right regardless of the order in
     * which they appear.
     * <p>
     * For example, the steps to evaluate the expression 1 + 2 * 3 + 4 * 5 + 6 are
     * as follows:
     * <p>
     * 1 + 2 * 3 + 4 * 5 + 6
     * 3   * 3 + 4 * 5 + 6
     * 9   + 4 * 5 + 6
     * 13   * 5 + 6
     * 65   + 6
     * 71
     * <p>
     * Parentheses can override this order; for example, here is what happens if
     * parentheses are added to form 1 + (2 * 3) + (4 * (5 + 6)):
     * <p>
     * 1 + (2 * 3) + (4 * (5 + 6))
     * 1 +    6    + (4 * (5 + 6))
     * 7      + (4 * (5 + 6))
     * 7      + (4 *   11   )
     * 7      +     44
     * 51
     * <p>
     * Here are a few more examples:
     * <p>
     * 2 * 3 + (4 * 5) becomes 26.
     * 5 + (8 * 3 + 9 + 3 * 4 * 3) becomes 437.
     * 5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)) becomes 12240.
     * ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2 becomes 13632.
     * <p>
     * Before you can help with the homework, you need to understand it yourself.
     * Evaluate the expression on each line of the homework; what is the sum of
     * the resulting values?
     * <p>
     * --- Part Two ---
     * <p>
     * You manage to answer the child's questions and they finish part 1 of their
     * homework, but get stuck when they reach the next section: advanced math.
     * <p>
     * Now, addition and multiplication have different precedence levels, but
     * they're not the ones you're familiar with. Instead, addition is evaluated
     * before multiplication.
     * <p>
     * For example, the steps to evaluate the expression 1 + 2 * 3 + 4 * 5 + 6 are
     * now as follows:
     * <p>
     * 1 + 2 * 3 + 4 * 5 + 6
     * 3   * 3 + 4 * 5 + 6
     * 3   *   7   * 5 + 6
     * 3   *   7   *  11
     * 21       *  11
     * 231
     * Here are the other examples from above:
     * <p>
     * 1 + (2 * 3) + (4 * (5 + 6)) still becomes 51.
     * 2 * 3 + (4 * 5) becomes 46.
     * 5 + (8 * 3 + 9 + 3 * 4 * 3) becomes 1445.
     * 5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)) becomes 669060.
     * ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2 becomes 23340.
     * <p>
     * What do you get if you add up the results of evaluating the homework
     * problems using these new rules?
     */
    static long evalExpressionLR(String expression) {
        List<Character> characterList = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            characterList.add(expression.charAt(i));
        }

        long eval = evalExpressionLR(characterList.iterator());
        LOGGER.info("{} becomes {}.", expression, eval);
        return eval;
    }

    private static long evalExpressionAdditionFirst(Iterator<Character> iterator) {
        Deque<Long> stack = new ArrayDeque<>();
        BinaryOperator<Long> operation = null;
        while (iterator.hasNext()) {
            char currentToken = iterator.next();
            switch (currentToken) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> stack.add((long) (currentToken - '0'));
                case '(' -> stack.add(evalExpressionAdditionFirst(iterator));
                case ')' -> {
                    if (operation != null) {
                        stack.add(operation.apply(stack.removeLast(), stack.removeLast()));
                    }

                    return stack.stream().mapToLong(t -> t).reduce(1, (a, b) -> a * b);
                }
                case '+' -> {
                    if (operation != null) {
                        stack.add(operation.apply(stack.removeLast(), stack.removeLast()));
                    }
                    operation = Long::sum;
                }
                case '*' -> {
                    if (operation != null) {
                        stack.add(operation.apply(stack.removeLast(), stack.removeLast()));
                    }
                    operation = null;
                }
            }
        }

        if (operation != null) {
            stack.add(operation.apply(stack.removeLast(), stack.removeLast()));
        }

        return stack.stream().mapToLong(t -> t).reduce(1, (a, b) -> a * b);
    }

    static long evalExpressionAdditionFirst(String expression) {
        List<Character> characterList = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            characterList.add(expression.charAt(i));
        }

        long eval = evalExpressionAdditionFirst(characterList.iterator());
        LOGGER.info("{} becomes {}.", expression, eval);
        return eval;
    }
}
