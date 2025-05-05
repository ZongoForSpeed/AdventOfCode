package com.adventofcode.year2016;

import com.adventofcode.common.utils.IntegerPair;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day10 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day10.class);
    private static final Pattern PATTERN_VALUE = Pattern.compile("value (\\d+) goes to bot (\\d+)");
    private static final Pattern PATTERN_BOT = Pattern.compile("bot (\\d+) gives low to (\\w+) (\\d+) and high to (\\w+) (\\d+)");

    private Day10() {
        // No-Op
    }

    /**
     * --- Day 10: Balance Bots ---
     *
     * You come upon a factory in which many robots are zooming around handing
     * small microchips to each other.
     *
     * Upon closer examination, you notice that each bot only proceeds when it has
     * two microchips, and once it does, it gives each one to a different bot or
     * puts it in a marked "output" bin. Sometimes, bots take microchips from
     * "input" bins, too.
     *
     * Inspecting one of the microchips, it seems like they each contain a single
     * number; the bots must use some logic to decide what to do with each chip.
     * You access the local control computer and download the bots' instructions
     * (your puzzle input).
     *
     * Some of the instructions specify that a specific-valued microchip should be
     * given to a specific bot; the rest of the instructions indicate what a given
     * bot should do with its lower-value or higher-value chip.
     *
     * For example, consider the following instructions:
     *
     * value 5 goes to bot 2
     * bot 2 gives low to bot 1 and high to bot 0
     * value 3 goes to bot 1
     * bot 1 gives low to output 1 and high to bot 0
     * bot 0 gives low to output 2 and high to output 0
     * value 2 goes to bot 2
     *
     * - Initially, bot 1 starts with a value-3 chip, and bot 2 starts with a
     * value-2 chip and a value-5 chip.
     * - Because bot 2 has two microchips, it gives its lower one (2) to bot 1
     * and its higher one (5) to bot 0.
     * - Then, bot 1 has two microchips; it puts the value-2 chip in output 1
     * and gives the value-3 chip to bot 0.
     * - Finally, bot 0 has two microchips; it puts the 3 in output 2 and the 5
     * in output 0.
     *
     * In the end, output bin 0 contains a value-5 microchip, output bin 1
     * contains a value-2 microchip, and output bin 2 contains a value-3
     * microchip. In this configuration, bot number 2 is responsible for comparing
     * value-5 microchips with value-2 microchips.
     *
     * Based on your instructions, what is the number of the bot that is
     * responsible for comparing value-61 microchips with value-17 microchips?
     *
     * Your puzzle answer was 56.
     */
    static int findChips(Scanner scanner, IntegerPair pair) {
        BalanceBots balanceBots = new BalanceBots();
        balanceBots.runBalanceBots(scanner);
        return balanceBots.findChips(pair);
    }

    /**
     * --- Part Two ---
     *
     * What do you get if you multiply together the values of one chip in each of
     * outputs 0, 1, and 2?
     *
     * Your puzzle answer was 7847.
     */
    static int getOutput012(Scanner scanner) {
        BalanceBots balanceBots = new BalanceBots();
        balanceBots.runBalanceBots(scanner);
        return balanceBots.getOutput012();
    }

    static class BalanceBots {
        private final Map<Integer, Bot> bots = new HashMap<>();
        private final Map<Integer, Output> outputs = new HashMap<>();

        private IntConsumer createConsumer(String type, int number) {
            return switch (type) {
                case "output" -> this.outputs.computeIfAbsent(number, Output::new);
                case "bot" -> createBot(number);
                default -> throw new IllegalStateException("Cannot manage type: " + type);
            };
        }

        private Bot createBot(int number) {
            return this.bots.computeIfAbsent(number, Bot::new);
        }

        public void runBalanceBots(Scanner scanner) {
            List<Pair<Integer, Bot>> operations = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = PATTERN_VALUE.matcher(line);
                if (matcher.matches()) {
                    int value = Integer.parseInt(matcher.group(1));
                    Bot bot = createBot(Integer.parseInt(matcher.group(2)));
                    LOGGER.debug("Value {} to {}", value, bot);
                    operations.add(Pair.of(value, bot));
                    continue;
                }

                matcher = PATTERN_BOT.matcher(line);
                if (matcher.matches()) {
                    Bot bot = createBot(Integer.parseInt(matcher.group(1)));
                    String outputLow = matcher.group(2);
                    int numberLow = Integer.parseInt(matcher.group(3));
                    String outputHigh = matcher.group(4);
                    int numberHigh = Integer.parseInt(matcher.group(5));
                    IntConsumer consumerLow = createConsumer(outputLow, numberLow);
                    IntConsumer consumerHigh = createConsumer(outputHigh, numberHigh);
                    bot.setConsumers(consumerLow, consumerHigh);
                    LOGGER.debug("{} to {} and {}", bot, consumerLow, consumerHigh);
                    continue;
                }

                throw new IllegalStateException("Cannot parse line: " + line);
            }

            for (Pair<Integer, Bot> operation : operations) {
                LOGGER.info("Sending {} to {}", operation.left(), operation.right());
                operation.right().accept(operation.left());
            }
        }

        public int findChips(IntegerPair chips) {
            for (Bot bot : bots.values()) {
                if (bot.operations.contains(chips)) {
                    LOGGER.info("{} manage {}", bot, chips);
                    return bot.number;
                }
            }

            return -1;
        }

        public Map<Integer, Integer> getOutputs() {
            return outputs.values().stream().collect(Collectors.toMap(t -> t.number, t -> t.chip));
        }

        public int getOutput012() {
            return outputs.values().stream().filter(t -> t.number >= 0 && t.number <= 2).map(t -> t.chip).reduce(1, (a, b) -> a * b);
        }
    }

    private static class Bot implements IntConsumer {
        private final int number;
        private final IntList chips;
        private final Set<IntegerPair> operations;

        @Nullable
        private IntConsumer lowConsumer;
        @Nullable
        private IntConsumer highConsumer;

        private Bot(int number) {
            this.number = number;
            this.chips = new IntArrayList();
            operations = new HashSet<>();
        }

        public void setConsumers(IntConsumer lowConsumer, IntConsumer highConsumer) {
            if (this.lowConsumer != null || this.highConsumer != null) {
                throw new IllegalStateException("Cannot set consumers twice");
            }
            this.lowConsumer = lowConsumer;
            this.highConsumer = highConsumer;
        }

        @Override
        public void accept(int value) {
            chips.add(value);
            if (chips.size() == 2) {
                chips.sort(IntComparators.NATURAL_COMPARATOR);
                int lowValue = chips.getInt(0);
                int highValue = chips.getInt(1);
                IntegerPair pair = IntegerPair.of(lowValue, highValue);
                operations.add(pair);
                LOGGER.info("{}: sending {} to {} and {} to {}", this, lowValue, lowConsumer, highValue, highConsumer);
                chips.clear();
                if (lowConsumer == null || highConsumer == null) {
                    throw new IllegalStateException("Cannot call null consumers");
                }
                lowConsumer.accept(lowValue);
                highConsumer.accept(highValue);
            }
        }

        @Override
        public String toString() {
            return "Bot{" +
                   number +
                   '}';
        }
    }

    private static class Output implements IntConsumer {
        private final int number;
        private int chip;

        private Output(int number) {
            this.number = number;
        }

        @Override
        public void accept(int value) {
            LOGGER.info("{} is receiving {}-value chip", this, value);
            chip = value;
        }

        @Override
        public String toString() {
            return "Output{" +
                   number +
                   '}';
        }
    }
}
