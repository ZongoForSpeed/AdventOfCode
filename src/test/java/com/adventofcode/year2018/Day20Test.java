package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class Day20Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day20Test.class);

    /**
     * --- Day 20: A Regular Map ---
     *
     * While you were learning about instruction pointers, the Elves made
     * considerable progress. When you look up, you discover that the North Pole
     * base construction project has completely surrounded you.
     *
     * The area you are in is made up entirely of rooms and doors. The rooms are
     * arranged in a grid, and rooms only connect to adjacent rooms when a door is
     * present between them.
     *
     * For example, drawing rooms as ., walls as #, doors as | or -, your current
     * position as X, and where north is up, the area you're in might look like
     * this:
     *
     * #####
     * #.|.#
     * #-###
     * #.|X#
     * #####
     *
     * You get the attention of a passing construction Elf and ask for a map. "I
     * don't have time to draw out a map of this place - it's huge. Instead, I can
     * give you directions to every room in the facility!" He writes down some
     * directions on a piece of parchment and runs off. In the example above, the
     * instructions might have been ^WNE$, a regular expression or "regex" (your
     * puzzle input).
     *
     * The regex matches routes (like WNE for "west, north, east") that will take
     * you from your current room through various doors in the facility. In
     * aggregate, the routes will take you through every door in the facility at
     * least once; mapping out all of these routes will let you build a proper map
     * and find your way around.
     *
     * ^ and $ are at the beginning and end of your regex; these just mean that
     * the regex doesn't match anything outside the routes it describes.
     * (Specifically, ^ matches the start of the route, and $ matches the end of
     * it.) These characters will not appear elsewhere in the regex.
     *
     * The rest of the regex matches various sequences of the characters N
     * (north), S (south), E (east), and W (west). In the example above, ^WNE$
     * matches only one route, WNE, which means you can move west, then north,
     * then east from your current position. Sequences of letters like this always
     * match that exact route in the same order.
     *
     * Sometimes, the route can branch. A branch is given by a list of options
     * separated by pipes (|) and wrapped in parentheses. So, ^N(E|W)N$ contains a
     * branch: after going north, you must choose to go either east or west before
     * finishing your route by going north again. By tracing out the possible
     * routes after branching, you can determine where the doors are and,
     * therefore, where the rooms are in the facility.
     *
     * For example, consider this regex: ^ENWWW(NEEE|SSE(EE|N))$
     *
     * This regex begins with ENWWW, which means that from your current position,
     * all routes must begin by moving east, north, and then west three times, in
     * that order. After this, there is a branch. Before you consider the branch,
     * this is what you know about the map so far, with doors you aren't sure
     * about marked with a ?:
     *
     * #?#?#?#?#
     * ?.|.|.|.?
     * #?#?#?#-#
     *     ?X|.?
     *     #?#?#
     *
     * After this point, there is (NEEE|SSE(EE|N)). This gives you exactly two
     * options: NEEE and SSE(EE|N). By following NEEE, the map now looks like
     * this:
     *
     * #?#?#?#?#
     * ?.|.|.|.?
     * #-#?#?#?#
     * ?.|.|.|.?
     * #?#?#?#-#
     *     ?X|.?
     *     #?#?#
     *
     * Now, only SSE(EE|N) remains. Because it is in the same parenthesized group
     * as NEEE, it starts from the same room NEEE started in. It states that
     * starting from that point, there exist doors which will allow you to move
     * south twice, then east; this ends up at another branch. After that, you can
     * either move east twice or north once. This information fills in the rest of
     * the doors:
     *
     * #?#?#?#?#
     * ?.|.|.|.?
     * #-#?#?#?#
     * ?.|.|.|.?
     * #-#?#?#-#
     * ?.?.?X|.?
     * #-#-#?#?#
     * ?.|.|.|.?
     * #?#?#?#?#
     *
     * Once you've followed all possible routes, you know the remaining unknown
     * parts are all walls, producing a finished map of the facility:
     *
     * #########
     * #.|.|.|.#
     * #-#######
     * #.|.|.|.#
     * #-#####-#
     * #.#.#X|.#
     * #-#-#####
     * #.|.|.|.#
     * #########
     *
     * Sometimes, a list of options can have an empty option, like (NEWS|WNSE|).
     * This means that routes at this point could effectively skip the options in
     * parentheses and move on immediately. For example, consider this regex and
     * the corresponding map:
     *
     * ^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$
     *
     * ###########
     * #.|.#.|.#.#
     * #-###-#-#-#
     * #.|.|.#.#.#
     * #-#####-#-#
     * #.#.#X|.#.#
     * #-#-#####-#
     * #.#.|.|.|.#
     * #-###-###-#
     * #.|.|.#.|.#
     * ###########
     *
     * This regex has one main route which, at three locations, can optionally
     * include additional detours and be valid: (NEWS|), (WNSE|), and (SWEN|).
     * Regardless of which option is taken, the route continues from the position
     * it is left at after taking those steps. So, for example, this regex matches
     * all of the following routes (and more that aren't listed here):
     *
     *   - ENNWSWWSSSEENEENNN
     *   - ENNWSWWNEWSSSSEENEENNN
     *   - ENNWSWWNEWSSSSEENEESWENNNN
     *   - ENNWSWWSSSEENWNSEEENNN
     *
     * By following the various routes the regex matches, a full map of all of the
     * doors and rooms in the facility can be assembled.
     *
     * To get a sense for the size of this facility, you'd like to determine which
     * room is furthest from you: specifically, you would like to find the room
     * for which the shortest path to that room would require passing through the
     * most doors.
     *
     *   - In the first example (^WNE$), this would be the north-east corner 3
     *     doors away.
     *   - In the second example (^ENWWW(NEEE|SSE(EE|N))$), this would be the
     *     south-east corner 10 doors away.
     *   - In the third example (^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$), this
     *     would be the north-east corner 18 doors away.
     *
     * Here are a few more examples:
     *
     * Regex: ^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$
     * Furthest room requires passing 23 doors
     *
     * #############
     * #.|.|.|.|.|.#
     * #-#####-###-#
     * #.#.|.#.#.#.#
     * #-#-###-#-#-#
     * #.#.#.|.#.|.#
     * #-#-#-#####-#
     * #.#.#.#X|.#.#
     * #-#-#-###-#-#
     * #.|.#.|.#.#.#
     * ###-#-###-#-#
     * #.|.#.|.|.#.#
     * #############
     *
     * Regex: ^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$
     * Furthest room requires passing 31 doors
     *
     * ###############
     * #.|.|.|.#.|.|.#
     * #-###-###-#-#-#
     * #.|.#.|.|.#.#.#
     * #-#########-#-#
     * #.#.|.|.|.|.#.#
     * #-#-#########-#
     * #.#.#.|X#.|.#.#
     * ###-#-###-#-#-#
     * #.|.#.#.|.#.|.#
     * #-###-#####-###
     * #.|.#.|.|.#.#.#
     * #-#-#####-#-#-#
     * #.#.|.|.|.#.|.#
     * ###############
     *
     * What is the largest number of doors you would be required to pass through
     * to reach a room? That is, find the room for which the shortest path from
     * your starting location to that room would require passing through the most
     * doors; what is the fewest doors you can pass through to reach it?
     */

    @Test
    void example() {
        String input = "^ENWWW(NEEE|SSE(EE|N))$";

        Pattern pattern = new ChainedPattern(
                new SimplePattern("ENWWW"),
                new ComplexPattern(
                        new SimplePattern("NEEE"),
                        new ChainedPattern(
                                new SimplePattern("SSE"),
                                new ComplexPattern(
                                        new SimplePattern("EE"),
                                        new SimplePattern("N")
                                )
                        )
                )
        );

        List<String> strings = pattern.getStrings();
        for (String string : strings) {
            LOGGER.debug(string);
        }

        // String line = scanner.nextLine();
        // StringBuilder currentPattern = new StringBuilder();
        // List<String> forkPatterns = new ArrayList<>();
        // for (char c : line.toCharArray()) {
        //     switch (c) {
        //         case 'N':
        //         case 'S':
        //         case 'E':
        //         case 'W':
        //             currentPattern.append(c);
        //             break;
        //         case '^':
        //         case '$':
        //             // Ignored
        //             break;
        //         case '(':
        //             break;
        //         case ')':
        //             break;
        //         case '|':
        //             break;
        //         default:
        //             throw new IllegalStateException("Unknown char " + c);
        //     }
        // }
        // while (scanner.hasNextByte()) {
        //     LOGGER.debug("{}", scanner.nextByte());
        // }
    }

    interface Pattern {
        List<String> getStrings();
    }

    record SimplePattern(String pattern) implements Pattern {
        @Override
        public List<String> getStrings() {
            return Collections.singletonList(pattern);
        }
    }

    record ChainedPattern(List<Pattern> patterns) implements Pattern {
        @Override
        public List<String> getStrings() {
            List<String> result = new ArrayList<>();
            result.add("");

            for (Pattern pattern : patterns) {
                List<String> strings = pattern.getStrings();
                result = cartesianProduct(result, strings);
            }

            return result;
        }

        public ChainedPattern(Pattern... patterns) {
            this(List.of(patterns));
        }
    }

    private static List<String> cartesianProduct(List<String> left, List<String> right) {
        List<String> result = new ArrayList<>();
        for (String s1 : left) {
            for (String s2 : right) {
                result.add(s1 + s2);
            }
        }
        return result;
    }

    record ComplexPattern(List<Pattern> patterns) implements Pattern {
        @Override
        public List<String> getStrings() {
            return patterns.stream().map(Pattern::getStrings).flatMap(Collection::stream).toList();
        }

        public ComplexPattern(Pattern... patterns) {
            this(List.of(patterns));
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day16Test.class.getResourceAsStream("/2018/day/20/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day16Test.class.getResourceAsStream("/2018/day/20/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
        }
    }

}
