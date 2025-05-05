package com.adventofcode.year2023;

import com.adventofcode.common.point.range.Range;
import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.Pair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day19 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day19.class);
    private static final Set<String> END_LABELS = Set.of("A", "R");
    private static final Pattern PATTERN_LABEL = Pattern.compile("(\\w+)\\{(.*)\\}");
    private static final Pattern PATTERN_PART = Pattern.compile("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}");
    private static final Pattern PATTERN_RULE = Pattern.compile("(\\w)([><])(\\d+):(\\w+)");

    private Day19() {
        // No-Op
    }

    /**
     * --- Day 19: Aplenty ---
     * <p>
     * The Elves of Gear Island are thankful for your help and send you on your
     * way. They even have a hang glider that someone stole from Desert Island;
     * since you're already going that direction, it would help them a lot if you
     * would use it to get down there and return it to them.
     * <p>
     * As you reach the bottom of the relentless avalanche of machine parts, you
     * discover that they're already forming a formidable heap. Don't worry,
     * though - a group of Elves is already here organizing the parts, and they
     * have a system.
     * <p>
     * To start, each part is rated in each of four categories:
     * <p>
     * - x: Extremely cool looking
     * - m: Musical (it makes a noise when you hit it)
     * - a: Aerodynamic
     * - s: Shiny
     * <p>
     * Then, each part is sent through a series of workflows that will ultimately
     * accept or reject the part. Each workflow has a name and contains a list of
     * rules; each rule specifies a condition and where to send the part if the
     * condition is true. The first rule that matches the part being considered is
     * applied immediately, and the part moves on to the destination described by
     * the rule. (The last rule in each workflow has no condition and always
     * applies if reached.)
     * <p>
     * Consider the workflow ex{x>10:one,m<20:two,a>30:R,A}. This workflow is
     * named ex and contains four rules. If workflow ex were considering a
     * specific part, it would perform the following steps in order:
     * <p>
     * - Rule "x>10:one": If the part's x is more than 10, send the part to the
     * workflow named one.
     * - Rule "m<20:two": Otherwise, if the part's m is less than 20, send the
     * part to the workflow named two.
     * - Rule "a>30:R": Otherwise, if the part's a is more than 30, the part is
     * immediately rejected (R).
     * - Rule "A": Otherwise, because no other rules matched the part, the part
     * is immediately accepted (A).
     * <p>
     * If a part is sent to another workflow, it immediately switches to the start
     * of that workflow instead and never returns. If a part is accepted (sent to
     * A) or rejected (sent to R), the part immediately stops any further
     * processing.
     * <p>
     * The system works, but it's not keeping up with the torrent of weird metal
     * shapes. The Elves ask if you can help sort a few parts and give you the
     * list of workflows and some part ratings (your puzzle input). For example:
     * <p>
     * px{a<2006:qkq,m>2090:A,rfg}
     * pv{a>1716:R,A}
     * lnx{m>1548:A,A}
     * rfg{s<537:gd,x>2440:R,A}
     * qs{s>3448:A,lnx}
     * qkq{x<1416:A,crn}
     * crn{x>2662:A,R}
     * in{s<1351:px,qqz}
     * qqz{s>2770:qs,m<1801:hdj,R}
     * gd{a>3333:R,R}
     * hdj{m>838:A,pv}
     * <p>
     * {x=787,m=2655,a=1222,s=2876}
     * {x=1679,m=44,a=2067,s=496}
     * {x=2036,m=264,a=79,s=2244}
     * {x=2461,m=1339,a=466,s=291}
     * {x=2127,m=1623,a=2188,s=1013}
     * <p>
     * The workflows are listed first, followed by a blank line, then the ratings
     * of the parts the Elves would like you to sort. All parts begin in the
     * workflow named in. In this example, the five listed parts go through the
     * following workflows:
     * <p>
     * - {x=787,m=2655,a=1222,s=2876}: in -> qqz -> qs -> lnx -> A
     * - {x=1679,m=44,a=2067,s=496}: in -> px -> rfg -> gd -> R
     * - {x=2036,m=264,a=79,s=2244}: in -> qqz -> hdj -> pv -> A
     * - {x=2461,m=1339,a=466,s=291}: in -> px -> qkq -> crn -> R
     * - {x=2127,m=1623,a=2188,s=1013}: in -> px -> rfg -> A
     * <p>
     * Ultimately, three parts are accepted. Adding up the x, m, a, and s rating
     * for each of the accepted parts gives 7540 for the part with x=787, 4623 for
     * the part with x=2036, and 6951 for the part with x=2127. Adding all of the
     * ratings for all of the accepted parts gives the sum total of 19114.
     * <p>
     * Sort through all of the parts you've been given; what do you get if you add
     * together all of the rating numbers for all of the parts that ultimately get
     * accepted?
     */
    public static final class PartOne {
        private PartOne() {
            // No-Op
        }

        public static long sumAccepted(Scanner scanner) {
            Map<String, Label> labels = new HashMap<>();
            List<Part> parts = new ArrayList<>();

            readInput(scanner, labels, parts);

            return sumAccepted(parts, labels);
        }

        private static long sumAccepted(List<Part> parts, Map<String, Label> labels) {
            long sum = 0;

            for (Part part : parts) {
                String nameLabel = "in";
                while (!END_LABELS.contains(nameLabel)) {
                    Label currentLabel = Objects.requireNonNull(labels.get(nameLabel));
                    nameLabel = currentLabel.matches(part);
                }

                LOGGER.info("Part {} -> {}", part, nameLabel);
                if ("A".equals(nameLabel)) {
                    sum += part.sum();
                }
            }
            return sum;
        }
    }

    /**
     * --- Part Two ---
     * <p>
     * Even with your help, the sorting process still isn't fast enough.
     * <p>
     * One of the Elves comes up with a new plan: rather than sort parts
     * individually through all of these workflows, maybe you can figure out in
     * advance which combinations of ratings will be accepted or rejected.
     * <p>
     * Each of the four ratings (x, m, a, s) can have an integer value ranging
     * from a minimum of 1 to a maximum of 4000. Of all possible distinct
     * combinations of ratings, your job is to figure out which ones will be
     * accepted.
     * <p>
     * In the above example, there are 167409079868000 distinct combinations of
     * ratings that will be accepted.
     * <p>
     * Consider only your list of workflows; the list of part ratings that the
     * Elves wanted you to sort is no longer relevant. How many distinct
     * combinations of ratings will be accepted by the Elves' workflows?
     */
    public static final class PartTwo {
        private PartTwo() {
            // No-Op
        }

        public static long combinations(Scanner scanner) {
            PartRange range = new PartRange(new Range(1, 4000),
                    new Range(1, 4000),
                    new Range(1, 4000),
                    new Range(1, 4000));

            Map<String, Label> labels = new HashMap<>();
            List<Part> parts = new ArrayList<>();

            readInput(scanner, labels, parts);

            Map<PartRange, String> ranges = Map.of(range, "in");
            while (!END_LABELS.containsAll(ranges.values())) {
                Map<PartRange, String> newRanges = new HashMap<>();
                for (Map.Entry<PartRange, String> entry : ranges.entrySet()) {
                    String value = entry.getValue();
                    if (END_LABELS.contains(value)) {
                        newRanges.put(entry.getKey(), value);
                    } else {
                        Map<PartRange, String> matches = Objects.requireNonNull(labels.get(value)).matches(entry.getKey());
                        newRanges.putAll(matches);
                    }
                }

                ranges = newRanges;
                LOGGER.info("after ranges = {}", ranges);

            }

            LOGGER.info("Ranges = {}", ranges);
            long combinations = 0;
            for (Map.Entry<PartRange, String> entry : ranges.entrySet()) {
                if ("A".equals(entry.getValue())) {
                    combinations += entry.getKey().combinations();
                }
            }
            return combinations;
        }
    }

    record Part(int x, int m, int a, int s) {
        public long sum() {
            return (long) x + m + a + s;
        }
    }

    record Label(String name, List<Rule> rules, String defaultLabel) {
        public String matches(Part part) {
            for (Rule rule : rules) {
                if (rule.matches(part)) {
                    return rule.label();
                }
            }
            return defaultLabel;
        }

        public Map<PartRange, String> matches(PartRange partRange) {
            Map<PartRange, String> result = new HashMap<>();
            List<PartRange> current = List.of(partRange);
            for (Rule rule : rules) {
                List<PartRange> newRanges = new ArrayList<>();
                current.stream().map(rule::matches).flatMap(Collection::stream).forEach(p -> {
                    if (p.right()) {
                        result.put(p.left(), rule.label());
                    } else {
                        newRanges.add(p.left());
                    }
                });
                current = newRanges;
            }
            if (!current.isEmpty()) {
                for (PartRange range : current) {
                    result.put(range, defaultLabel);
                }
            }
            return result;
        }
    }

    record PartRange(Range x, Range m, Range a, Range s) {
        public long combinations() {
            return x.size() * m.size() * a.size() * s.size();
        }
    }

    record Rule(Category category, int value, boolean greater, String label) {
        boolean matches(Part part) {
            int partValue = category.value(part);
            return (greater && partValue > value) || (!greater && partValue < value);
        }

        boolean matches(Range range) {
            return (greater && range.lower() > value) || (!greater && range.upper() < value);
        }

        List<Pair<PartRange, Boolean>> matches(PartRange part) {
            Range range = category.value(part);

            List<Pair<PartRange, Boolean>> parts = new ArrayList<>();
            for (Range split : split(range, value)) {
                PartRange newPart = switch (category) {
                    case X -> new PartRange(split, part.m(), part.a(), part.s());
                    case M -> new PartRange(part.x(), split, part.a(), part.s());
                    case A -> new PartRange(part.x(), part.m(), split, part.s());
                    case S -> new PartRange(part.x(), part.m(), part.a(), split);
                };

                parts.add(Pair.of(newPart, matches(split)));
            }
            return parts;
        }
    }

    private static void readInput(Scanner scanner, Map<String, Label> labels, List<Part> parts) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (StringUtils.isBlank(line)) {
                continue;
            }
            Matcher matcher = PATTERN_LABEL.matcher(line);
            if (matcher.matches()) {
                String nameLabel = matcher.group(1);
                String rules = matcher.group(2);
                Iterable<String> split = Splitter.on(',').split(rules);
                Optional<String> defaultLabel = Optional.empty();
                List<Rule> ruleList = new ArrayList<>();
                for (String r : split) {
                    matcher = PATTERN_RULE.matcher(r);
                    if (matcher.matches()) {
                        Category category = Category.valueOf(matcher.group(1).toUpperCase());
                        boolean greater = ">".equals(matcher.group(2));
                        int value = Integer.parseInt(matcher.group(3));
                        String output = matcher.group(4);
                        ruleList.add(new Rule(category, value, greater, output));
                    } else {
                        defaultLabel = Optional.of(r);
                    }
                }
                labels.put(nameLabel, new Label(nameLabel, ruleList, defaultLabel.orElseThrow()));
                continue;
            }
            matcher = PATTERN_PART.matcher(line);
            if (matcher.matches()) {
                int x = Integer.parseInt(matcher.group(1));
                int m = Integer.parseInt(matcher.group(2));
                int a = Integer.parseInt(matcher.group(3));
                int s = Integer.parseInt(matcher.group(4));
                parts.add(new Part(x, m, a, s));
            }
        }

        LOGGER.info("Parts: {}", parts);
        LOGGER.info("Labels: {}", labels);
    }

    private static List<Range> split(Range range, int value) {
        if (range.lower() > value || range.upper() < value) {
            return List.of(range);
        }

        Range valueRange = new Range(value, value);
        return Range.fullUnion(range, valueRange);
    }

    enum Category {
        X {
            @Override
            public int value(Part part) {
                return part.x();
            }

            @Override
            public Range value(PartRange part) {
                return part.x();
            }
        },
        M {
            @Override
            public int value(Part part) {
                return part.m();
            }

            @Override
            public Range value(PartRange part) {
                return part.m();
            }
        },
        A {
            @Override
            public int value(Part part) {
                return part.a();
            }

            @Override
            public Range value(PartRange part) {
                return part.a();
            }
        },
        S {
            @Override
            public int value(Part part) {
                return part.s();
            }

            @Override
            public Range value(PartRange part) {
                return part.s();
            }
        };

        public abstract int value(Part part);

        public abstract Range value(PartRange part);
    }

}
