package com.adventofcode.year2022;

import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day02 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day02.class);
    private static final Pattern PATTERN = Pattern.compile("^(\\w) (\\w)$");

    private Day02() {
        // No-Op
    }

    private static final Map<Pair<RockPaperScissors, RockPaperScissors>, Play> RESULTS = Map.ofEntries(
            Map.entry(Pair.of(RockPaperScissors.ROCK, RockPaperScissors.ROCK), Play.DRAW),
            Map.entry(Pair.of(RockPaperScissors.ROCK, RockPaperScissors.PAPER), Play.LOSE),
            Map.entry(Pair.of(RockPaperScissors.ROCK, RockPaperScissors.SCISSORS), Play.WIN),
            Map.entry(Pair.of(RockPaperScissors.PAPER, RockPaperScissors.ROCK), Play.WIN),
            Map.entry(Pair.of(RockPaperScissors.PAPER, RockPaperScissors.PAPER), Play.DRAW),
            Map.entry(Pair.of(RockPaperScissors.PAPER, RockPaperScissors.SCISSORS), Play.LOSE),
            Map.entry(Pair.of(RockPaperScissors.SCISSORS, RockPaperScissors.ROCK), Play.LOSE),
            Map.entry(Pair.of(RockPaperScissors.SCISSORS, RockPaperScissors.PAPER), Play.WIN),
            Map.entry(Pair.of(RockPaperScissors.SCISSORS, RockPaperScissors.SCISSORS), Play.DRAW)
    );

    enum Play {
        WIN(6),
        LOSE(0),
        DRAW(3);

        private final int value;

        Play(int value) {
            this.value = value;
        }

        static Play read(String i) {
            return switch (i) {
                case "X" -> LOSE;
                case "Y" -> DRAW;
                case "Z" -> WIN;
                default -> throw new IllegalStateException("Unknown input '" + i + "'");
            };
        }
    }

    enum RockPaperScissors {
        ROCK(1),
        PAPER(2),
        SCISSORS(3);

        private final int value;

        RockPaperScissors(int value) {
            this.value = value;
        }

        static RockPaperScissors read(String i) {
            return switch (i) {
                case "A", "X" -> ROCK;
                case "B", "Y" -> PAPER;
                case "C", "Z" -> SCISSORS;
                default -> throw new IllegalStateException("Unknown input '" + i + "'");
            };
        }

        static int score(RockPaperScissors player1, RockPaperScissors player2) {
            Play play = Objects.requireNonNull(RESULTS.get(Pair.of(player2, player1)));
            return play.value + player2.value;
        }

    }

    /**
     * --- Day 2: Rock Paper Scissors ---
     * <p>
     * The Elves begin to set up camp on the beach. To decide whose tent gets to
     * be closest to the snack storage, a giant Rock Paper Scissors tournament is
     * already in progress.
     * <p>
     * Rock Paper Scissors is a game between two players. Each game contains many
     * rounds; in each round, the players each simultaneously choose one of Rock,
     * Paper, or Scissors using a hand shape. Then, a winner for that round is
     * selected: Rock defeats Scissors, Scissors defeats Paper, and Paper defeats
     * Rock. If both players choose the same shape, the round instead ends in a
     * draw.
     * <p>
     * Appreciative of your help yesterday, one Elf gives you an encrypted
     * strategy guide (your puzzle input) that they say will be sure to help you
     * win. "The first column is what your opponent is going to play: A for Rock,
     * B for Paper, and C for Scissors. The second column--" Suddenly, the Elf is
     * called away to help with someone's tent.
     * <p>
     * The second column, you reason, must be what you should play in response: X
     * for Rock, Y for Paper, and Z for Scissors. Winning every time would be
     * suspicious, so the responses must have been carefully chosen.
     * <p>
     * The winner of the whole tournament is the player with the highest score.
     * Your total score is the sum of your scores for each round. The score for a
     * single round is the score for the shape you selected (1 for Rock, 2 for
     * Paper, and 3 for Scissors) plus the score for the outcome of the round (0
     * if you lost, 3 if the round was a draw, and 6 if you won).
     * <p>
     * Since you can't be sure if the Elf is trying to help you or trick you, you
     * should calculate the score you would get if you were to follow the strategy
     * guide.
     * <p>
     * For example, suppose you were given the following strategy guide:
     * <p>
     * A Y
     * B X
     * C Z
     * <p>
     * This strategy guide predicts and recommends the following:
     * <p>
     * - In the first round, your opponent will choose Rock (A), and you should
     * choose Paper (Y). This ends in a win for you with a score of 8 (2
     * because you chose Paper + 6 because you won).
     * - In the second round, your opponent will choose Paper (B), and you
     * should choose Rock (X). This ends in a loss for you with a score of 1
     * (1 + 0).
     * - The third round is a draw with both players choosing Scissors, giving
     * you a score of 3 + 3 = 6.
     * <p>
     * In this example, if you were to follow the strategy guide, you would get a
     * total score of 15 (8 + 1 + 6).
     * <p>
     * What would your total score be if everything goes exactly according to your
     * strategy guide?
     */
    static final class PartOne {

        private PartOne() {
            // No-Op
        }

        static int findScore(Scanner scanner) {
            int score = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                Matcher matcher = PATTERN.matcher(line);
                if (matcher.find()) {
                    RockPaperScissors opponent = RockPaperScissors.read(matcher.group(1));
                    RockPaperScissors elf = RockPaperScissors.read(matcher.group(2));

                    int playScore = RockPaperScissors.score(opponent, elf);
                    LOGGER.info("{} vs {} ==> {} = {}", opponent, elf, RESULTS.get(Pair.of(elf, opponent)), playScore);

                    score += playScore;
                } else {
                    throw new IllegalStateException("Cannot parse line: " + line);
                }
            }
            return score;
        }

    }

    /**
     * --- Part Two ---
     * <p>
     * The Elf finishes helping with the tent and sneaks back over to you.
     * "Anyway, the second column says how the round needs to end: X means you
     * need to lose, Y means you need to end the round in a draw, and Z means you
     * need to win. Good luck!"
     * <p>
     * The total score is still calculated in the same way, but now you need to
     * figure out what shape to choose so the round ends as indicated. The example
     * above now goes like this:
     * <p>
     * - In the first round, your opponent will choose Rock (A), and you need
     * the round to end in a draw (Y), so you also choose Rock. This gives
     * you a score of 1 + 3 = 4.
     * - In the second round, your opponent will choose Paper (B), and you
     * choose Rock so you lose (X) with a score of 1 + 0 = 1.
     * - In the third round, you will defeat your opponent's Scissors with Rock
     * for a score of 1 + 6 = 7.
     * <p>
     * Now that you're correctly decrypting the ultra top secret strategy guide,
     * you would get a total score of 12.
     * <p>
     * Following the Elf's instructions for the second column, what would your
     * total score be if everything goes exactly according to your strategy guide?
     */
    static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        private static final Map<Pair<RockPaperScissors, Play>, RockPaperScissors> STRATEGY = Map.ofEntries(
                Map.entry(Pair.of(RockPaperScissors.ROCK, Play.DRAW), RockPaperScissors.ROCK),
                Map.entry(Pair.of(RockPaperScissors.PAPER, Play.LOSE), RockPaperScissors.ROCK),
                Map.entry(Pair.of(RockPaperScissors.SCISSORS, Play.WIN), RockPaperScissors.ROCK),
                Map.entry(Pair.of(RockPaperScissors.ROCK, Play.WIN), RockPaperScissors.PAPER),
                Map.entry(Pair.of(RockPaperScissors.PAPER, Play.DRAW), RockPaperScissors.PAPER),
                Map.entry(Pair.of(RockPaperScissors.SCISSORS, Play.LOSE), RockPaperScissors.PAPER),
                Map.entry(Pair.of(RockPaperScissors.ROCK, Play.LOSE), RockPaperScissors.SCISSORS),
                Map.entry(Pair.of(RockPaperScissors.PAPER, Play.WIN), RockPaperScissors.SCISSORS),
                Map.entry(Pair.of(RockPaperScissors.SCISSORS, Play.DRAW), RockPaperScissors.SCISSORS)
        );

        static int findScore(Scanner scanner) {
            int score = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                Matcher matcher = PATTERN.matcher(line);
                if (matcher.find()) {
                    RockPaperScissors opponent = RockPaperScissors.read(matcher.group(1));
                    Play outcome = Play.read(matcher.group(2));

                    RockPaperScissors strategy = Objects.requireNonNull(STRATEGY.get(Pair.of(opponent, outcome)));

                    int playScore = RockPaperScissors.score(opponent, strategy);

                    score += playScore;
                } else {
                    throw new IllegalStateException("Cannot parse line: " + line);
                }


            }
            return score;
        }

    }
}
