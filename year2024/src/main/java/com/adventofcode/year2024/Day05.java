package com.adventofcode.year2024;

import com.adventofcode.common.utils.IntegerPair;
import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day05 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05.class);
    private static final Pattern RULES_PATTERN = Pattern.compile("(\\d+)\\|(\\d+)");

    private Day05() {
        // No-Op
    }

    private static OptionalInt correctlyOrdered(IntList book, List<IntegerPair> rules) {
        if (book.isEmpty()) {
            return OptionalInt.empty();
        }

        Int2IntMap map = new Int2IntOpenHashMap();
        for (int i = 0; i < book.size(); i++) {
            int page = book.getInt(i);
            map.put(page, i);
        }

        for (IntegerPair rule : rules) {
            int positionLeft = map.getOrDefault(rule.left(), -1);
            int positionRight = map.getOrDefault(rule.right(), -1);
            if (positionLeft == -1 || positionRight == -1) {
                continue;
            }
            if (positionLeft > positionRight) {
                return OptionalInt.empty();
            }
        }

        return OptionalInt.of(book.getInt(book.size() / 2));
    }

    /**
     * --- Day 5: Print Queue ---
     * <p>
     * Satisfied with their search on Ceres, the squadron of scholars suggests
     * subsequently scanning the stationery stacks of sub-basement 17.
     * <p>
     * The North Pole printing department is busier than ever this close to
     * Christmas, and while The Historians continue their search of this
     * historically significant facility, an Elf operating a very familiar printer
     * beckons you over.
     * <p>
     * The Elf must recognize you, because they waste no time explaining that the
     * new sleigh launch safety manual updates won't print correctly. Failure to
     * update the safety manuals would be dire indeed, so you offer your services.
     * <p>
     * Safety protocols clearly indicate that new pages for the safety manuals
     * must be printed in a very specific order. The notation X|Y means that if
     * both page number X and page number Y are to be produced as part of an
     * update, page number X must be printed at some point before page number Y.
     * <p>
     * The Elf has for you both the page ordering rules and the pages to produce
     * in each update (your puzzle input), but can't figure out whether each
     * update has the pages in the right order.
     * <p>
     * For example:
     * <p>
     * 47|53
     * 97|13
     * 97|61
     * 97|47
     * 75|29
     * 61|13
     * 75|53
     * 29|13
     * 97|29
     * 53|29
     * 61|53
     * 97|53
     * 61|29
     * 47|13
     * 75|47
     * 97|75
     * 47|61
     * 75|61
     * 47|29
     * 75|13
     * 53|13
     * <p>
     * 75,47,61,53,29
     * 97,61,53,29,13
     * 75,29,13
     * 75,97,47,61,53
     * 61,13,29
     * 97,13,75,29,47
     * <p>
     * The first section specifies the page ordering rules, one per line. The
     * first rule, 47|53, means that if an update includes both page number 47 and
     * page number 53, then page number 47 must be printed at some point before
     * page number 53. (47 doesn't necessarily need to be immediately before 53;
     * other pages are allowed to be between them.)
     * <p>
     * The second section specifies the page numbers of each update. Because most
     * safety manuals are different, the pages needed in the updates are different
     * too. The first update, 75,47,61,53,29, means that the update consists of
     * page numbers 75, 47, 61, 53, and 29.
     * <p>
     * To get the printers going as soon as possible, start by identifying which
     * updates are already in the right order.
     * <p>
     * In the above example, the first update (75,47,61,53,29) is in the right
     * order:
     * <p>
     * - 75 is correctly first because there are rules that put each other page
     * after it: 75|47, 75|61, 75|53, and 75|29.
     * - 47 is correctly second because 75 must be before it (75|47) and every
     * other page must be after it according to 47|61, 47|53, and 47|29.
     * - 61 is correctly in the middle because 75 and 47 are before it (75|61
     * and 47|61) and 53 and 29 are after it (61|53 and 61|29).
     * - 53 is correctly fourth because it is before page number 29 (53|29).
     * - 29 is the only page left and so is correctly last.
     * <p>
     * Because the first update does not include some page numbers, the ordering
     * rules involving those missing page numbers are ignored.
     * <p>
     * The second and third updates are also in the correct order according to the
     * rules. Like the first update, they also do not include every page number,
     * and so only some of the ordering rules apply - within each update, the
     * ordering rules that involve missing page numbers are not used.
     * <p>
     * The fourth update, 75,97,47,61,53, is not in the correct order: it would
     * print 75 before 97, which violates the rule 97|75.
     * <p>
     * The fifth update, 61,13,29, is also not in the correct order, since it
     * breaks the rule 29|13.
     * <p>
     * The last update, 97,13,75,29,47, is not in the correct order due to
     * breaking several rules.
     * <p>
     * For some reason, the Elves also need to know the middle page number of each
     * update being printed. Because you are currently only printing the
     * correctly-ordered updates, you will need to find the middle page number of
     * each correctly-ordered update. In the above example, the correctly-ordered
     * updates are:
     * <p>
     * 75,47,61,53,29
     * 97,61,53,29,13
     * 75,29,13
     * <p>
     * These have middle page numbers of 61, 53, and 29 respectively. Adding these
     * page numbers together gives 143.
     * <p>
     * Of course, you'll need to be careful: the actual list of page ordering
     * rules is bigger and more complicated than the above example.
     * <p>
     * Determine which updates are already in the correct order. What do you get
     * if you add up the middle page number from those correctly-ordered updates?
     */
    public static int partOne(Scanner scanner) {
        Input result = readInput(scanner);


        int middle = 0;
        for (IntList book : result.books()) {
            OptionalInt order = correctlyOrdered(book, result.rules());
            if (order.isPresent()) {
                middle += order.getAsInt();
            }
        }
        return middle;
    }

    private static Input readInput(Scanner scanner) {
        List<IntegerPair> rules = new ArrayList<>();
        List<IntList> books = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = RULES_PATTERN.matcher(line);
            if (matcher.find()) {
                int page1 = Integer.parseInt(matcher.group(1));
                int page2 = Integer.parseInt(matcher.group(2));
                rules.add(IntegerPair.of(page1, page2));
                continue;
            }
            if (line.isEmpty()) {
                continue;
            }
            int[] pages = Splitter.on(',').splitToStream(line).mapToInt(Integer::parseInt).toArray();
            books.add(IntList.of(pages));
        }

        LOGGER.trace("Rules: {}", rules);
        LOGGER.trace("Books: {}", books);
        return new Input(rules, books);
    }

    /**
     * --- Part Two ---
     * <p>
     * While the Elves get to work printing the correctly-ordered updates, you
     * have a little time to fix the rest of them.
     * <p>
     * For each of the incorrectly-ordered updates, use the page ordering rules to
     * put the page numbers in the right order. For the above example, here are
     * the three incorrectly-ordered updates and their correct orderings:
     * <p>
     * - 75,97,47,61,53 becomes 97,75,47,61,53.
     * - 61,13,29 becomes 61,29,13.
     * - 97,13,75,29,47 becomes 97,75,47,29,13.
     * <p>
     * After taking only the incorrectly-ordered updates and ordering them
     * correctly, their middle page numbers are 47, 29, and 47. Adding these
     * together produces 123.
     * <p>
     * Find the updates which are not in the correct order. What do you get if you
     * add up the middle page numbers after correctly ordering just those updates?
     */
    public static int partTwo(Scanner scanner) {
        Input result = readInput(scanner);

        Set<IntegerPair> validRules = new HashSet<>(result.rules());

        int middle = 0;
        for (IntList book : result.books()) {
            OptionalInt order = correctlyOrdered(book, result.rules());
            if (order.isEmpty()) {
                IntList newBook = new IntArrayList(book);
                newBook.sort((k1, k2) -> {
                    if (validRules.contains(IntegerPair.of(k1, k2))) {
                        return -1;
                    } else if (validRules.contains(IntegerPair.of(k2, k1))) {
                        return 1;
                    }
                    return 0;
                });
                LOGGER.info("{} become {}", book, newBook);
                middle += newBook.getInt(newBook.size() / 2);
            }
        }
        return middle;
    }

    private record Input(List<IntegerPair> rules, List<IntList> books) {
    }
}
