package com.adventofcode.year2016;

import com.adventofcode.point.Direction;
import com.adventofcode.point.Point2D;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public final class Day17 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day17.class);

    public static List<String> algorithm(String passcode, Point2D start, Point2D end) {
        Set<State> closedList = new HashSet<>();
        Queue<State> queue = new PriorityQueue<>(Comparator.comparingLong(State::cost));
        queue.add(new State(start, 0, ""));
        List<String> paths = new ArrayList<>();
        while (!queue.isEmpty()) {
            State state = queue.poll();
            if (state.position().equals(end)) {
                paths.add(state.path());
                continue;
            }
            if (closedList.add(state)) {
                Collection<State> moves = state.nextStates(passcode);
                for (State move : moves) {
                    if (!closedList.contains(move)) {
                        queue.add(move);
                    }
                }
            }
        }

        return paths;
    }

    /**
     * --- Day 17: Two Steps Forward ---
     *
     * You're trying to access a secure vault protected by a 4x4 grid of small
     * rooms connected by doors. You start in the top-left room (marked S), and
     * you can access the vault (marked V) once you reach the bottom-right room:
     *
     * #########
     * #S| | | #
     * #-#-#-#-#
     * # | | | #
     * #-#-#-#-#
     * # | | | #
     * #-#-#-#-#
     * # | | |
     * ####### V
     *
     * Fixed walls are marked with #, and doors are marked with - or |.
     *
     * The doors in your current room are either open or closed (and locked) based
     * on the hexadecimal MD5 hash of a passcode (your puzzle input) followed by a
     * sequence of uppercase characters representing the path you have taken so
     * far (U for up, D for down, L for left, and R for right).
     *
     * Only the first four characters of the hash are used; they represent,
     * respectively, the doors up, down, left, and right from your current
     * position. Any b, c, d, e, or f means that the corresponding door is open;
     * any other character (any number or a) means that the corresponding door is
     * closed and locked.
     *
     * To access the vault, all you need to do is reach the bottom-right room;
     * reaching this room opens the vault and all doors in the maze.
     *
     * For example, suppose the passcode is hijkl. Initially, you have taken no
     * steps, and so your path is empty: you simply find the MD5 hash of hijkl
     * alone. The first four characters of this hash are ced9, which indicate that
     * up is open (c), down is open (e), left is open (d), and right is closed and
     * locked (9). Because you start in the top-left corner, there are no "up" or
     * "left" doors to be open, so your only choice is down.
     *
     * Next, having gone only one step (down, or D), you find the hash of hijklD.
     * This produces f2bc, which indicates that you can go back up, left (but
     * that's a wall), or right. Going right means hashing hijklDR to get 5745 -
     * all doors closed and locked. However, going up instead is worthwhile: even
     * though it returns you to the room you started in, your path would then be
     * DU, opening a different set of doors.
     *
     * After going DU (and then hashing hijklDU to get 528e), only the right door
     * is open; after going DUR, all doors lock. (Fortunately, your actual
     * passcode is not hijkl).
     *
     * Passcodes actually used by Easter Bunny Vault Security do allow access to
     * the vault if you know the right path. For example:
     *
     *   - If your passcode were ihgpwlah, the shortest path would be DDRRRD.
     *   - With kglvqrro, the shortest path would be DDUDRLRRUDRD.
     *   - With ulqzkmiv, the shortest would be DRURDRUDDLLDLUURRDULRLDUUDDDRR.
     *
     * Given your vault's passcode, what is the shortest path (the actual path,
     * not just the length) to reach the vault?
     *
     * Your puzzle input is ioramepc.
     *
     * Your puzzle answer was RDDRULDDRR.
     */
    static String findShortestPath(String passcode) {
        List<String> paths = findAllPaths(passcode);
        return paths.get(0);
    }

    /**
     * --- Part Two ---
     *
     * You're curious how robust this security solution really is, and so you
     * decide to find longer and longer paths which still provide access to the
     * vault. You remember that paths always end the first time they reach the
     * bottom-right room (that is, they can never pass through it, only end in
     * it).
     *
     * For example:
     *
     *   - If your passcode were ihgpwlah, the longest path would take 370 steps.
     *   - With kglvqrro, the longest path would be 492 steps long.
     *   - With ulqzkmiv, the longest path would be 830 steps long.
     *
     * What is the length of the longest path that reaches the vault?
     *
     * Your puzzle answer was 11052855125.
     */
    static String findLongestPath(String passcode) {
        List<String> paths = findAllPaths(passcode);
        return paths.get(paths.size() - 1);
    }

    private static List<String> findAllPaths(String passcode) {
        Point2D start = Point2D.of(1, 1);
        Point2D end = Point2D.of(4, 4);
        List<String> paths = algorithm(passcode, start, end);

        LOGGER.info("Found {} paths between {} and {}", paths.size(), start, end);
        paths.sort(Comparator.comparingLong(String::length));
        return paths;
    }

    record State(Point2D position, long cost, String path) {

        public List<State> nextStates(String passcode) {
            String md5 = DigestUtils.md5Hex(passcode + path());
            // up, down, left, and right
            List<Character> directions = new ArrayList<>();
            if (md5.charAt(0) >= 'b') {
                directions.add('U');
            }
            if (md5.charAt(1) >= 'b') {
                directions.add('D');
            }
            if (md5.charAt(2) >= 'b') {
                directions.add('L');
            }
            if (md5.charAt(3) >= 'b') {
                directions.add('R');
            }
            return directions.stream().map(this::move).filter(State::valid).toList();
        }

        public State move(char direction) {
            return new State(
                    switch (direction) {
                        case 'U' -> position.move(Direction.NORTH);
                        case 'D' -> position.move(Direction.SOUTH);
                        case 'L' -> position.move(Direction.WEST);
                        case 'R' -> position.move(Direction.EAST);
                        default -> throw new IllegalStateException("Unknown direction " + direction);
                    }, cost + 1, path + direction);
        }

        public boolean valid() {
            return position.x() >= 1 && position.x() <= 4
                   && position.y() >= 1 && position.y() <= 4;
        }
    }
}
