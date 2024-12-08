package com.adventofcode.year2023;

import com.google.common.base.Splitter;
import org.apache.commons.collections4.SetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day25 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day25.class);

    private Day25() {
        // No-Op
    }

    /**
     * --- Day 25: Snowverload ---
     * <p>
     * Still somehow without snow, you go to the last place you haven't checked:
     * the center of Snow Island, directly below the waterfall.
     * <p>
     * Here, someone has clearly been trying to fix the problem. Scattered
     * everywhere are hundreds of weather machines, almanacs, communication
     * modules, hoof prints, machine parts, mirrors, lenses, and so on.
     * <p>
     * Somehow, everything has been wired together into a massive snow-producing
     * apparatus, but nothing seems to be running. You check a tiny screen on one
     * of the communication modules: Error 2023. It doesn't say what Error 2023
     * means, but it does have the phone number for a support line printed on it.
     * <p>
     * "Hi, you've reached Weather Machines And So On, Inc. How can I help you?"
     * You explain the situation.
     * <p>
     * "Error 2023, you say? Why, that's a power overload error, of course! It
     * means you have too many components plugged in. Try unplugging some
     * components and--" You explain that there are hundreds of components here
     * and you're in a bit of a hurry.
     * <p>
     * "Well, let's see how bad it is; do you see a big red reset button
     * somewhere? It should be on its own module. If you push it, it probably
     * won't fix anything, but it'll report how overloaded things are." After a
     * minute or two, you find the reset button; it's so big that it takes two
     * hands just to get enough leverage to push it. Its screen then displays:
     * <p>
     * SYSTEM OVERLOAD!
     * <p>
     * Connected components would require
     * power equal to at least 100 stars!
     * <p>
     * "Wait, how many components did you say are plugged in? With that much
     * equipment, you could produce snow for an entire--" You disconnect the call.
     * <p>
     * You have nowhere near that many stars - you need to find a way to
     * disconnect at least half of the equipment here, but it's already Christmas!
     * You only have time to disconnect three wires.
     * <p>
     * Fortunately, someone left a wiring diagram (your puzzle input) that shows
     * how the components are connected. For example:
     * <p>
     * jqt: rhn xhk nvd
     * rsh: frs pzl lsr
     * xhk: hfx
     * cmg: qnr nvd lhk bvb
     * rhn: xhk bvb hfx
     * bvb: xhk hfx
     * pzl: lsr hfx nvd
     * qnr: nvd
     * ntq: jqt hfx bvb xhk
     * nvd: lhk
     * lsr: lhk
     * rzs: qnr cmg lsr rsh
     * frs: qnr lhk lsr
     * <p>
     * Each line shows the name of a component, a colon, and then a list of other
     * components to which that component is connected. Connections aren't
     * directional; abc: xyz and xyz: abc both represent the same configuration.
     * Each connection between two components is represented only once, so some
     * components might only ever appear on the left or right side of a colon.
     * <p>
     * In this example, if you disconnect the wire between hfx/pzl, the wire
     * between bvb/cmg, and the wire between nvd/jqt, you will divide the
     * components into two separate, disconnected groups:
     * <p>
     * - 9 components: cmg, frs, lhk, lsr, nvd, pzl, qnr, rsh, and rzs.
     * - 6 components: bvb, hfx, jqt, ntq, rhn, and xhk.
     * <p>
     * Multiplying the sizes of these groups together produces 54.
     * <p>
     * Find the three wires you need to disconnect in order to divide the
     * components into two separate groups. What do you get if you multiply the
     * sizes of these two groups together?
     */
    public static final class PartOne {
        private PartOne() {
            // No-Op
        }

        private static final Pattern PATTERN = Pattern.compile("^(\\w+): (.*)$");

        public static int minimumCut(@Nonnull Scanner scanner) {
            Map<String, Set<String>> graph = new HashMap<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                Matcher matcher = PATTERN.matcher(line);
                if (matcher.find()) {
                    String v = matcher.group(1);
                    for (String u : Splitter.on(' ').split(matcher.group(2))) {
                        graph.computeIfAbsent(u, ignore -> new HashSet<>()).add(v);
                        graph.computeIfAbsent(v, ignore -> new HashSet<>()).add(u);
                    }
                }
            }

            LOGGER.info("Graph: {}", graph);

            Set<String> cut = new HashSet<>(graph.keySet());

            while (count(graph, cut) != 3) {
                String bestV = null;
                int bestCount = Integer.MIN_VALUE;
                for (String v : cut) {
                    int count = count(graph, cut, v);
                    if (count > bestCount) {
                        bestCount = count;
                        bestV = v;
                    }
                }

                if (bestV == null) {
                    LOGGER.error("Cannot find cut to add");
                    break;
                }

                LOGGER.trace("Removing {} from cut", bestV);
                cut.remove(bestV);
            }

            SetUtils.SetView<String> difference = SetUtils.difference(graph.keySet(), cut);
            return difference.size() * cut.size();
        }

        static int count(Map<String, Set<String>> graph, @Nonnull Set<String> cut) {
            int count = 0;
            for (String v : cut) {
                count += count(graph, cut, v);
            }
            return count;
        }

        static int count(@Nonnull Map<String, Set<String>> graph, Set<String> cut, String v) {
            return SetUtils.difference(graph.get(v), cut).size();
        }
    }

    /**
     * --- Part Two ---
     * <p>
     * You climb over weather machines, under giant springs, and narrowly avoid a
     * pile of pipes as you find and disconnect the three wires.
     * <p>
     * A moment after you disconnect the last wire, the big red reset button
     * module makes a small ding noise:
     * <p>
     * System overload resolved!
     * Power required is now 50 stars.
     * <p>
     * Out of the corner of your eye, you notice goggles and a loose-fitting hard
     * hat peeking at you from behind an ultra crucible. You think you see a faint
     * glow, but before you can investigate, you hear another small ding:
     * <p>
     * Power required is now 49 stars.
     * <p>
     * Please supply the necessary stars and
     * push the button to restart the system.
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

    }
}
