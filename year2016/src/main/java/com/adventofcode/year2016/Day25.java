package com.adventofcode.year2016;

import com.adventofcode.common.Assembunny;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class Day25 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day25.class);

    private Day25() {
        // No-Op
    }

    /**
     * --- Day 25: Clock Signal ---
     *
     * You open the door and find yourself on the roof. The city sprawls away from
     * you for miles and miles.
     *
     * There's not much time now - it's already Christmas, but you're nowhere near
     * the North Pole, much too far to deliver these stars to the sleigh in time.
     *
     * However, maybe the huge antenna up here can offer a solution. After all,
     * the sleigh doesn't need the stars, exactly; it needs the timing data they
     * provide, and you happen to have a massive signal generator right here.
     *
     * You connect the stars you have to your prototype computer, connect that to
     * the antenna, and begin the transmission.
     *
     * Nothing happens.
     *
     * You call the service number printed on the side of the antenna and quickly
     * explain the situation. "I'm not sure what kind of equipment you have
     * connected over there," he says, "but you need a clock signal." You try to
     * explain that this is a signal for a clock.
     *
     * "No, no, a clock signal - timing information so the antenna computer knows
     * how to read the data you're sending it. An endless, alternating pattern of
     * 0, 1, 0, 1, 0, 1, 0, 1, 0, 1...." He trails off.
     *
     * You ask if the antenna can handle a clock signal at the frequency you would
     * need to use for the data from the stars. "There's no way it can! The only
     * antenna we've installed capable of that is on top of a top-secret Easter
     * Bunny installation, and you're definitely not-" You hang up the phone.
     *
     * You've extracted the antenna's clock signal generation assembunny code
     * (your puzzle input); it looks mostly compatible with code you worked on
     * just recently.
     *
     * This antenna code, being a signal generator, uses one extra instruction:
     *
     *   - out x transmits x (either an integer or the value of a register) as
     *     the next value for the clock signal.
     *
     * The code takes a value (via register a) that describes the signal to
     * generate, but you're not sure how it's used. You'll have to find the input
     * to produce the right signal through experimentation.
     *
     * What is the lowest positive integer that can be used to initialize register
     * a and cause the code to output a clock signal of 0, 1, 0, 1... repeating
     * forever?
     *
     * Your puzzle answer was 470.
     *
     * --- Part Two ---
     *
     * The antenna is ready. Now, all you need is the fifty stars required to
     * generate the signal for the sleigh, but you don't have enough.
     *
     * You look toward the sky in desperation... suddenly noticing that a lone
     * star has been installed at the top of the antenna! Only 49 more to go.
     *
     */
    static int findClockSignal(Scanner scanner) {
        List<String[]> commands = new ArrayList<>();
        while (scanner.hasNextLine()) {
            commands.add(scanner.nextLine().split(" "));
        }

        for (int a = 1; ; ++a) {
            IntList output = new IntArrayList();
            Assembunny.run(commands, Map.of("a", a), out -> {
                output.add(out);
                return output.size() > 20;
            });
            if (checkOutput(output)) {
                LOGGER.info("Output with a={} is {}", a, output);
                return a;
            }
        }
    }

    private static boolean checkOutput(IntList output) {
        int previous = output.getInt(0);
        for (int i = 1; i < output.size(); i++) {
            int current = output.getInt(i);
            if (current == previous) {
                return false;
            }
            previous = current;
        }
        return true;
    }
}
