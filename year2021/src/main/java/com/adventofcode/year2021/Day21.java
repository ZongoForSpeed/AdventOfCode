package com.adventofcode.year2021;

import com.adventofcode.common.utils.LongPair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day21 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21.class);
    private static final Pattern PLAYERS_PATTERN = Pattern.compile("Player (\\d+) starting position: (\\d+)");

    private static Map<String, Player> readPlayer(Scanner scanner) {
        Map<String, Player> players = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PLAYERS_PATTERN.matcher(line);
            if (matcher.find()) {
                String name = matcher.group(1);
                players.put(name, new Player(name, Integer.parseInt(matcher.group(2))));
            }
        }
        return players;
    }

    /**
     * --- Day 21: Dirac Dice ---
     *
     * There's not much to do as you slowly descend to the bottom of the ocean.
     * The submarine computer challenges you to a nice game of Dirac Dice.
     *
     * This game consists of a single die, two pawns, and a game board with a
     * circular track containing ten spaces marked 1 through 10 clockwise. Each
     * player's starting space is chosen randomly (your puzzle input). Player 1
     * goes first.
     *
     * Players take turns moving. On each player's turn, the player rolls the die
     * three times and adds up the results. Then, the player moves their pawn that
     * many times forward around the track (that is, moving clockwise on spaces in
     * order of increasing value, wrapping back around to 1 after 10). So, if a
     * player is on space 7 and they roll 2, 2, and 1, they would move forward 5
     * times, to spaces 8, 9, 10, 1, and finally stopping on 2.
     *
     * After each player moves, they increase their score by the value of the
     * space their pawn stopped on. Players' scores start at 0. So, if the first
     * player starts on space 7 and rolls a total of 5, they would stop on space 2
     * and add 2 to their score (for a total score of 2). The game immediately
     * ends as a win for any player whose score reaches at least 1000.
     *
     * Since the first game is a practice game, the submarine opens a compartment
     * labeled deterministic dice and a 100-sided die falls out. This die always
     * rolls 1 first, then 2, then 3, and so on up to 100, after which it starts
     * over at 1 again. Play using this die.
     *
     * For example, given these starting positions:
     *
     * Player 1 starting position: 4
     * Player 2 starting position: 8
     *
     * This is how the game would go:
     *
     * - Player 1 rolls 1+2+3 and moves to space 10 for a total score of 10.
     * - Player 2 rolls 4+5+6 and moves to space 3 for a total score of 3.
     * - Player 1 rolls 7+8+9 and moves to space 4 for a total score of 14.
     * - Player 2 rolls 10+11+12 and moves to space 6 for a total score of 9.
     * - Player 1 rolls 13+14+15 and moves to space 6 for a total score of 20.
     * - Player 2 rolls 16+17+18 and moves to space 7 for a total score of 16.
     * - Player 1 rolls 19+20+21 and moves to space 6 for a total score of 26.
     * - Player 2 rolls 22+23+24 and moves to space 6 for a total score of 22.
     *
     * ...after many turns...
     *
     * - Player 2 rolls 82+83+84 and moves to space 6 for a total score of 742.
     * - Player 1 rolls 85+86+87 and moves to space 4 for a total score of 990.
     * - Player 2 rolls 88+89+90 and moves to space 3 for a total score of 745.
     * - Player 1 rolls 91+92+93 and moves to space 10 for a final score, 1000.
     *
     * Since player 1 has at least 1000 points, player 1 wins and the game ends.
     * At this point, the losing player had 745 points and the die had been rolled
     * a total of 993 times; 745 * 993 = 739785.
     *
     * Play a practice game using the deterministic 100-sided die. The moment
     * either player wins, what do you get if you multiply the score of the losing
     * player by the number of times the die was rolled during the game?
     *
     * Your puzzle answer was 503478.
     */
    static long playPartOne(Scanner scanner) {
        Map<String, Player> players = readPlayer(scanner);

        Player player1 = Objects.requireNonNull(players.get("1"));
        Player player2 = Objects.requireNonNull(players.get("2"));

        long result;

        DeterministicDice dice = new DeterministicDice();
        while (true) {
            player1 = player1.play(dice.next(), dice.next(), dice.next());
            if (player1.score() >= 1000) {
                LOGGER.warn("Player 1 wins");
                result = (long) dice.getRolls() * player2.score();
                break;
            }

            player2 = player2.play(dice.next(), dice.next(), dice.next());
            if (player2.score() >= 1000) {
                LOGGER.warn("Player 2 wins");
                result = (long) dice.getRolls() * player1.score();
                break;
            }
        }
        return result;
    }

    /**
     * --- Part Two ---
     *
     * Now that you're warmed up, it's time to play the real game.
     *
     * A second compartment opens, this time labeled Dirac dice. Out of it falls a
     * single three-sided die.
     *
     * As you experiment with the die, you feel a little strange. An informational
     * brochure in the compartment explains that this is a quantum die: when you
     * roll it, the universe splits into multiple copies, one copy for each
     * possible outcome of the die. In this case, rolling the die always splits
     * the universe into three copies: one where the outcome of the roll was 1,
     * one where it was 2, and one where it was 3.
     *
     * The game is played the same as before, although to prevent things from
     * getting too far out of hand, the game now ends when either player's score
     * reaches at least 21.
     *
     * Using the same starting positions as in the example above, player 1 wins in
     * 444356092776315 universes, while player 2 merely wins in 341960390180808
     * universes.
     *
     * Using your given starting positions, determine every possible outcome. Find
     * the player that wins in more universes; in how many universes does that
     * player win?
     *
     * Your puzzle answer was 716241959649754.
     */
    static long playPartTwo(Scanner scanner) {
        Map<String, Player> players = readPlayer(scanner);

        Player player1 = Objects.requireNonNull(players.get("1"));
        Player player2 = Objects.requireNonNull(players.get("2"));

        LongPair result = playPartTwo(new HashMap<>(), true, player1, player2);
        return Math.max(result.left(), result.right());
    }

    private static LongPair playPartTwo(Map<Triple<Boolean, Player, Player>, LongPair> cache, boolean turn, Player player1, Player player2) {
        Triple<Boolean, Player, Player> key = Triple.of(turn, player1, player2);
        LongPair cachedValue = cache.get(key);
        if (cachedValue != null) {
            return cachedValue;
        }

        long player1wins = 0;
        long player2wins = 0;

        if (turn) {
            for (int d1 = 1; d1 <= 3; ++d1) {
                for (int d2 = 1; d2 <= 3; ++d2) {
                    for (int d3 = 1; d3 <= 3; ++d3) {
                        Player newPlayer1 = player1.play(d1, d2, d3);
                        if (newPlayer1.score() >= 21) {
                            player1wins++;
                        } else {
                            LongPair pair = playPartTwo(cache, false, newPlayer1, player2);
                            player1wins += pair.left();
                            player2wins += pair.right();
                        }
                    }
                }
            }
        } else {
            for (int d1 = 1; d1 <= 3; ++d1) {
                for (int d2 = 1; d2 <= 3; ++d2) {
                    for (int d3 = 1; d3 <= 3; ++d3) {
                        Player newPlayer2 = player2.play(d1, d2, d3);
                        if (newPlayer2.score() >= 21) {
                            player2wins++;
                        } else {
                            LongPair pair = playPartTwo(cache, true, player1, newPlayer2);
                            player1wins += pair.left();
                            player2wins += pair.right();
                        }
                    }
                }
            }
        }

        cachedValue = LongPair.of(player1wins, player2wins);
        cache.put(key, cachedValue);
        return cachedValue;
    }

    private static class DeterministicDice {
        private final AtomicInteger dice;
        private int rolls;

        private DeterministicDice() {
            this.dice = new AtomicInteger(1);
            this.rolls = 0;
        }

        private int next() {
            ++rolls;
            return dice.getAndUpdate(i -> {
                i++;
                if (i > 100) {
                    i = 1;
                }
                return i;
            });
        }

        public int getRolls() {
            return rolls;
        }
    }

    record Player(String name, int position, int score) {

        public Player(String name, int startingPosition) {
            this(name, startingPosition, 0);
        }

        public Player play(int d1, int d2, int d3) {
            int totalDice = d1 + d2 + d3;
            int nextPosition = position + totalDice;
            while (nextPosition > 10) {
                nextPosition -= 10;
            }

            int nextScore = score + nextPosition;
            LOGGER.trace("Player {} rolls {}+{}+{} and moves to space {} for a total score of {}", name, d1, d2, d3, nextPosition, nextScore);
            return new Player(name, nextPosition, nextScore);
        }
    }
}
