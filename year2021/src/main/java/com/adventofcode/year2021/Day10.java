package com.adventofcode.year2021;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Scanner;

public final class Day10 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day10.class);

    /**
     * --- Day 10: Syntax Scoring ---
     * <p>
     * You ask the submarine to determine the best route out of the deep-sea cave,
     * but it only replies:
     * <p>
     * Syntax error in navigation subsystem on line: all of them
     * <p>
     * All of them?! The damage is worse than you thought. You bring up a copy of
     * the navigation subsystem (your puzzle input).
     * <p>
     * The navigation subsystem syntax is made of several lines containing
     * chunks. There are one or more chunks on each line, and chunks contain zero
     * or more other chunks. Adjacent chunks are not separated by any delimiter;
     * if one chunk stops, the next chunk (if any) can immediately start. Every
     * chunk must open and close with one of four legal pairs of matching
     * characters:
     * <p>
     * - If a chunk opens with (, it must close with ).
     * - If a chunk opens with [, it must close with ].
     * - If a chunk opens with {, it must close with }.
     * - If a chunk opens with <, it must close with >.
     * <p>
     * So, () is a legal chunk that contains no other chunks, as is []. More
     * complex but valid chunks include ([]), {()()()}, <([{}])>,
     * [<>({}){}[([])<>]], and even (((((((((()))))))))).
     * <p>
     * Some lines are incomplete, but others are corrupted. Find and discard the
     * corrupted lines first.
     * <p>
     * A corrupted line is one where a chunk closes with the wrong character -
     * that is, where the characters it opens and closes with do not form one of
     * the four legal pairs listed above.
     * <p>
     * Examples of corrupted chunks include (], {()()()>, (((()))}, and
     * <([]){()}[{}]). Such a chunk can appear anywhere within a line, and its
     * presence causes the whole line to be considered corrupted.
     * <p>
     * For example, consider the following navigation subsystem:
     * <p>
     * [({(<(())[]>[[{[]{<()<>>
     * [(()[<>])]({[<{<<[]>>(
     * {([(<{}[<>[]}>{[]{[(<()>
     * (((({<>}<{<{<>}{[]{[]{}
     * [[<[([]))<([[{}[[()]]]
     * [{[{({}]{}}([{[{{{}}([]
     * {<[[]]>}<{[{[{[]{()[[[]
     * [<(<(<(<{}))><([]([]()
     * <{([([[(<>()){}]>(<<{{
     * <{([{{}}[<[[[<>{}]]]>[]]
     * <p>
     * Some of the lines aren't corrupted, just incomplete; you can ignore these
     * lines for now. The remaining five lines are corrupted:
     * <p>
     * - {([(<{}[<>[]}>{[]{[(<()> - Expected ], but found } instead.
     * - [[<[([]))<([[{}[[()]]] - Expected ], but found ) instead.
     * - [{[{({}]{}}([{[{{{}}([] - Expected ), but found ] instead.
     * - [<(<(<(<{}))><([]([]() - Expected >, but found ) instead.
     * - <{([([[(<>()){}]>(<<{{ - Expected ], but found > instead.
     * <p>
     * Stop at the first incorrect closing character on each corrupted line.
     * <p>
     * Did you know that syntax checkers actually have contests to see who can get
     * the high score for syntax errors in a file? It's true! To calculate the
     * syntax error score for a line, take the first illegal character on the line
     * and look it up in the following table:
     * <p>
     * - ): 3 points.
     * - ]: 57 points.
     * - }: 1197 points.
     * - >: 25137 points.
     * <p>
     * In the above example, an illegal ) was found twice (2*3 = 6 points), an
     * illegal ] was found once (57 points), an illegal } was found once (1197
     * points), and an illegal > was found once (25137 points). So, the total
     * syntax error score for this file is 6+57+1197+25137 = 26397 points!
     * <p>
     * Find the first illegal character in each corrupted line of the navigation
     * subsystem. What is the total syntax error score for those errors?
     * <p>
     * Your puzzle answer was 339411.
     */
    static int validateSyntax(Scanner scanner) {
        int totalScore = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int score = validateLineSyntax(line);
            LOGGER.info("Syntax line {}: {}", line, score);
            totalScore += score;
        }
        return totalScore;
    }

    private static int validateLineSyntax(String line) {
        Deque<Brackets> openedBrackets = new ArrayDeque<>();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            switch (c) {
                case '(':
                    openedBrackets.addLast(Brackets.ROUND);
                    break;
                case ')':
                    if (openedBrackets.isEmpty() || openedBrackets.removeLast() != Brackets.ROUND) {
                        return 3;
                    }
                    break;
                case '[':
                    openedBrackets.addLast(Brackets.SQUARE);
                    break;
                case ']':
                    if (openedBrackets.isEmpty() || openedBrackets.removeLast() != Brackets.SQUARE) {
                        return 57;
                    }
                    break;
                case '{':
                    openedBrackets.addLast(Brackets.CURLY);
                    break;
                case '}':
                    if (openedBrackets.isEmpty() || openedBrackets.removeLast() != Brackets.CURLY) {
                        return 1197;
                    }
                    break;
                case '<':
                    openedBrackets.addLast(Brackets.ANGLE);
                    break;
                case '>':
                    if (openedBrackets.isEmpty() || openedBrackets.removeLast() != Brackets.ANGLE) {
                        return 25137;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown character: '" + c + "'");
            }
        }

        return 0;
    }

    /**
     * --- Part Two ---
     * <p>
     * Now, discard the corrupted lines. The remaining lines are incomplete.
     * <p>
     * Incomplete lines don't have any incorrect characters - instead, they're
     * missing some closing characters at the end of the line. To repair the
     * navigation subsystem, you just need to figure out the sequence of closing
     * characters that complete all open chunks in the line.
     * <p>
     * You can only use closing characters (), ], }, or >), and you must add them
     * in the correct order so that only legal pairs are formed and all chunks end
     * up closed.
     * <p>
     * In the example above, there are five incomplete lines:
     * <p>
     * - [({(<(())[]>[[{[]{<()<>> - Complete by adding }}]])})].
     * - [(()[<>])]({[<{<<[]>>( - Complete by adding )}>]}).
     * - (((({<>}<{<{<>}{[]{[]{} - Complete by adding }}>}>)))).
     * - {<[[]]>}<{[{[{[]{()[[[] - Complete by adding ]]}}]}]}>.
     * - <{([{{}}[<[[[<>{}]]]>[]] - Complete by adding ])}>.
     * <p>
     * Did you know that autocomplete tools also have contests? It's true! The
     * score is determined by considering the completion string character-by
     * -character. Start with a total score of 0. Then, for each character,
     * multiply the total score by 5 and then increase the total score by the
     * point value given for the character in the following table:
     * <p>
     * - ): 1 point.
     * - ]: 2 points.
     * - }: 3 points.
     * - >: 4 points.
     * <p>
     * So, the last completion string above - ])}> - would be scored as follows:
     * <p>
     * - Start with a total score of 0.
     * - Multiply the total score by 5 to get 0, then add the value of ] (2) to
     * get a new total score of 2.
     * - Multiply the total score by 5 to get 10, then add the value of ) (1)
     * to get a new total score of 11.
     * - Multiply the total score by 5 to get 55, then add the value of } (3)
     * to get a new total score of 58.
     * - Multiply the total score by 5 to get 290, then add the value of > (4)
     * to get a new total score of 294.
     * <p>
     * The five lines' completion strings have total scores as follows:
     * <p>
     * - }}]])})] - 288957 total points.
     * - )}>]}) - 5566 total points.
     * - }}>}>)))) - 1480781 total points.
     * - ]]}}]}]}> - 995444 total points.
     * - ])}> - 294 total points.
     * <p>
     * Autocomplete tools are an odd bunch: the winner is found by sorting all of
     * the scores and then taking the middle score. (There will always be an odd
     * number of scores to consider.) In this example, the middle score is 288957
     * because there are the same number of scores smaller and larger than it.
     * <p>
     * Find the completion string for each incomplete line, score the completion
     * strings, and sort the scores. What is the middle score?
     */
    static long completeSyntax(Scanner scanner) {
        LongList scores = new LongArrayList();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            long score = completeLineSyntax(line);
            LOGGER.info("Syntax line {}: {}", line, score);
            if (score > 0) {
                scores.add(score);
            }
        }

        Collections.sort(scores);
        LOGGER.info("Scores: {}", scores);
        return scores.getLong(scores.size() / 2);
    }

    private static long completeLineSyntax(String line) {
        Deque<Brackets> openedBrackets = new ArrayDeque<>();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            switch (c) {
                case '(':
                    openedBrackets.addLast(Brackets.ROUND);
                    break;
                case ')':
                    if (openedBrackets.isEmpty() || openedBrackets.removeLast() != Brackets.ROUND) {
                        return 0;
                    }
                    break;
                case '[':
                    openedBrackets.addLast(Brackets.SQUARE);
                    break;
                case ']':
                    if (openedBrackets.isEmpty() || openedBrackets.removeLast() != Brackets.SQUARE) {
                        return 0;
                    }
                    break;
                case '{':
                    openedBrackets.addLast(Brackets.CURLY);
                    break;
                case '}':
                    if (openedBrackets.isEmpty() || openedBrackets.removeLast() != Brackets.CURLY) {
                        return 0;
                    }
                    break;
                case '<':
                    openedBrackets.addLast(Brackets.ANGLE);
                    break;
                case '>':
                    if (openedBrackets.isEmpty() || openedBrackets.removeLast() != Brackets.ANGLE) {
                        return 0;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown character: '" + c + "'");
            }
        }

        long score = 0;
        while (!openedBrackets.isEmpty()) {
            Brackets brackets = openedBrackets.removeLast();
            score *= 5;
            score += switch (brackets) {
                case ROUND -> 1;
                case SQUARE -> 2;
                case CURLY -> 3;
                case ANGLE -> 4;
            };
        }

        return score;
    }

    private enum Brackets {
        ROUND,
        SQUARE,
        CURLY,
        ANGLE
    }
}
