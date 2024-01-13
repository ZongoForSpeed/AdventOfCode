package com.adventofcode.year2018;

import com.adventofcode.point.Direction;
import com.adventofcode.point.Point2D;
import com.adventofcode.point.map.CharMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public final class Day13 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13.class);

    /**
     * --- Day 13: Mine Cart Madness ---
     *
     * A crop of this size requires significant logistics to transport produce,
     * soil, fertilizer, and so on. The Elves are very busy pushing things around
     * in carts on some kind of rudimentary system of tracks they've come up with.
     *
     * Seeing as how cart-and-track systems don't appear in recorded history for
     * another 1000 years, the Elves seem to be making this up as they go along.
     * They haven't even figured out how to avoid collisions yet.
     *
     * You map out the tracks (your puzzle input) and see where you can help.
     *
     * Tracks consist of straight paths (| and -), curves (/ and \), and
     * intersections (+). Curves connect exactly two perpendicular pieces of track;
     * for example, this is a closed loop:
     *
     * /----\
     * |    |
     * |    |
     * \----/
     *
     * Intersections occur when two perpendicular paths cross. At an intersection,
     * a cart is capable of turning left, turning right, or continuing straight.
     * Here are two loops connected by two intersections:
     *
     * /-----\
     * |     |
     * |  /--+--\
     * |  |  |  |
     * \--+--/  |
     *    |     |
     *    \-----/
     *
     * Several carts are also on the tracks. Carts always face either up (^), down
     * (v), left (<), or right (>). (On your initial map, the track under each
     * cart is a straight path matching the direction the cart is facing.)
     *
     * Each time a cart has the option to turn (by arriving at any intersection),
     * it turns left the first time, goes straight the second time, turns right
     * the third time, and then repeats those directions starting again with left
     * the fourth time, straight the fifth time, and so on. This process is
     * independent of the particular intersection at which the cart has arrived -
     * that is, the cart has no per-intersection memory.
     *
     * Carts all move at the same speed; they take turns moving a single step at a
     * time. They do this based on their current location: carts on the top row
     * move first (acting from left to right), then carts on the second row move
     * (again from left to right), then carts on the third row, and so on. Once
     * each cart has moved one step, the process repeats; each of these loops is
     * called a tick.
     *
     * For example, suppose there are two carts on a straight track:
     *
     * |  |  |  |  |
     * v  |  |  |  |
     * |  v  v  |  |
     * |  |  |  v  X
     * |  |  ^  ^  |
     * ^  ^  |  |  |
     * |  |  |  |  |
     *
     * First, the top cart moves. It is facing down (v), so it moves down one
     * square. Second, the bottom cart moves. It is facing up (^), so it moves up
     * one square. Because all carts have moved, the first tick ends. Then, the
     * process repeats, starting with the first cart. The first cart moves down,
     * then the second cart moves up - right into the first cart, colliding with
     * it! (The location of the crash is marked with an X.) This ends the second
     * and last tick.
     *
     * Here is a longer example:
     *
     * /->-\
     * |   |  /----\
     * | /-+--+-\  |
     * | | |  | v  |
     * \-+-/  \-+--/
     *   \------/
     *
     * /-->\
     * |   |  /----\
     * | /-+--+-\  |
     * | | |  | |  |
     * \-+-/  \->--/
     *   \------/
     *
     * /---v
     * |   |  /----\
     * | /-+--+-\  |
     * | | |  | |  |
     * \-+-/  \-+>-/
     *   \------/
     *
     * /---\
     * |   v  /----\
     * | /-+--+-\  |
     * | | |  | |  |
     * \-+-/  \-+->/
     *   \------/
     *
     * /---\
     * |   |  /----\
     * | /->--+-\  |
     * | | |  | |  |
     * \-+-/  \-+--^
     *   \------/
     *
     * /---\
     * |   |  /----\
     * | /-+>-+-\  |
     * | | |  | |  ^
     * \-+-/  \-+--/
     *   \------/
     *
     * /---\
     * |   |  /----\
     * | /-+->+-\  ^
     * | | |  | |  |
     * \-+-/  \-+--/
     *   \------/
     *
     * /---\
     * |   |  /----<
     * | /-+-->-\  |
     * | | |  | |  |
     * \-+-/  \-+--/
     *   \------/
     *
     * /---\
     * |   |  /---<\
     * | /-+--+>\  |
     * | | |  | |  |
     * \-+-/  \-+--/
     *   \------/
     *
     * /---\
     * |   |  /--<-\
     * | /-+--+-v  |
     * | | |  | |  |
     * \-+-/  \-+--/
     *   \------/
     *
     * /---\
     * |   |  /-<--\
     * | /-+--+-\  |
     * | | |  | v  |
     * \-+-/  \-+--/
     *   \------/
     *
     * /---\
     * |   |  /<---\
     * | /-+--+-\  |
     * | | |  | |  |
     * \-+-/  \-<--/
     *   \------/
     *
     * /---\
     * |   |  v----\
     * | /-+--+-\  |
     * | | |  | |  |
     * \-+-/  \<+--/
     *   \------/
     *
     * /---\
     * |   |  /----\
     * | /-+--v-\  |
     * | | |  | |  |
     * \-+-/  ^-+--/
     *   \------/
     *
     * /---\
     * |   |  /----\
     * | /-+--+-\  |
     * | | |  X |  |
     * \-+-/  \-+--/
     *   \------/
     *
     * After following their respective paths for a while, the carts eventually
     * crash. To help prevent crashes, you'd like to know the location of the
     * first crash. Locations are given in X,Y coordinates, where the furthest
     * left column is X=0 and the furthest top row is Y=0:
     *
     *            111
     *  0123456789012
     * 0/---\
     * 1|   |  /----\
     * 2| /-+--+-\  |
     * 3| | |  X |  |
     * 4\-+-/  \-+--/
     * 5  \------/
     *
     * In this example, the location of the first crash is 7,3.
     */
    public static Point2D findCrashPosition(Scanner scanner) {
        CharMap charMap = CharMap.read(scanner, c -> ' ' != c);

        LOGGER.info("full char map =\n{}", charMap);

        List<Cart> carts = findCarts(charMap);

        while (true) {
            carts.sort(Comparator.comparing(Cart::position, Comparator.comparingInt(Point2D::y).thenComparingInt(Point2D::x)));

            for (int i = 0; i < carts.size(); i++) {
                Cart cart = carts.get(i);
                Cart newCart = cart.move(charMap);
                Optional<Cart> first = carts.stream()
                        .filter(c -> c.position().equals(newCart.position()))
                        .findFirst();

                if (first.isPresent()) {
                    LOGGER.info("Collision of cart {} & {}", newCart, first.get());
                    return newCart.position();
                }

                carts.set(i, newCart);
            }


        }
    }

    private static List<Cart> findCarts(CharMap charMap) {
        List<Cart> carts = new ArrayList<>();

        int id = 0;

        for (Point2D point : charMap.points()) {
            char c = charMap.get(point);
            switch (c) {
                case '>' -> {
                    charMap.set(point, '-');
                    carts.add(new Cart(++id, point, Direction.RIGHT, 0));
                }
                case '<' -> {
                    charMap.set(point, '-');
                    carts.add(new Cart(++id, point, Direction.LEFT, 0));
                }
                case '^' -> {
                    charMap.set(point, '|');
                    carts.add(new Cart(++id, point, Direction.UP, 0));
                }
                case 'v' -> {
                    charMap.set(point, '|');
                    carts.add(new Cart(++id, point, Direction.DOWN, 0));
                }
                default -> {
                    // Nothing
                }
            }
        }

        LOGGER.info("char map =\n{}", charMap);
        LOGGER.info("carts =\n{}", carts);
        return carts;
    }

    /**
     * --- Part Two ---
     *
     * There isn't much you can do to prevent crashes in this ridiculous system.
     * However, by predicting the crashes, the Elves know where to be in advance
     * and instantly remove the two crashing carts the moment any crash occurs.
     *
     * They can proceed like this for a while, but eventually, they're going to
     * run out of carts. It could be useful to figure out where the last cart that
     * hasn't crashed will end up.
     *
     * For example:
     *
     * />-<\
     * |   |
     * | /<+-\
     * | | | v
     * \>+</ |
     *   |   ^
     *   \<->/
     *
     * /---\
     * |   |
     * | v-+-\
     * | | | |
     * \-+-/ |
     *   |   |
     *   ^---^
     *
     * /---\
     * |   |
     * | /-+-\
     * | v | |
     * \-+-/ |
     *   ^   ^
     *   \---/
     *
     * /---\
     * |   |
     * | /-+-\
     * | | | |
     * \-+-/ ^
     *   |   |
     *   \---/
     *
     * After four very expensive crashes, a tick ends with only one cart
     * remaining; its final location is 6,4.
     *
     * What is the location of the last cart at the end of the first tick where it
     * is the only cart left?
     */
    public static Point2D findLastCart(Scanner scanner) {
        CharMap charMap = CharMap.read(scanner, c -> ' ' != c);

        LOGGER.info("full char map =\n{}", charMap);

        List<Cart> carts = findCarts(charMap);

        while (carts.size() > 1) {
            carts.sort(Comparator.comparing(Cart::position, Comparator.comparingInt(Point2D::y).thenComparingInt(Point2D::x)));

            IntSet crashedCarts = new IntOpenHashSet();

            for (int i = 0; i < carts.size(); i++) {
                Cart cart = carts.get(i);
                if (crashedCarts.contains(cart.id())) {
                    continue;
                }
                Cart newCart = cart.move(charMap);
                Optional<Cart> first = carts.stream().filter(c -> !crashedCarts.contains(c.id()))
                        .filter(c -> c.position().equals(newCart.position()))
                        .findFirst();

                if (first.isPresent()) {
                    LOGGER.info("Collision of cart {} & {}", newCart, first.get());
                    crashedCarts.add(newCart.id());
                    crashedCarts.add(first.get().id());
                } else {
                    carts.set(i, newCart);
                }
            }

            carts = new ArrayList<>(carts.stream().filter(c -> !crashedCarts.contains(c.id())).toList());
        }

        return carts.get(0).position();
    }

    record Cart(int id, Point2D position, Direction direction, int state) {

        public Cart move(CharMap map) {
            char c = map.get(position);
            switch (c) {
                case '|', '-':
                    return new Cart(id, position.move(direction), direction, state);
                case '\\': {
                    Direction nextDirection = switch (direction) {
                        case UP -> Direction.LEFT;
                        case DOWN -> Direction.RIGHT;
                        case LEFT -> Direction.UP;
                        case RIGHT -> Direction.DOWN;
                    };
                    return new Cart(id, position.move(nextDirection), nextDirection, state);
                }
                case '/': {
                    Direction nextDirection = switch (direction) {
                        case UP -> Direction.RIGHT;
                        case DOWN -> Direction.LEFT;
                        case LEFT -> Direction.DOWN;
                        case RIGHT -> Direction.UP;
                    };
                    return new Cart(id, position.move(nextDirection), nextDirection, state);
                }
                case '+': {
                    Direction nextDirection = switch (state % 3) {
                        case 0 -> direction.left();
                        case 1 -> direction;
                        case 2 -> direction.right();
                        default -> throw new IllegalStateException("Cannot happen !");
                    };
                    return new Cart(id, position.move(nextDirection), nextDirection, state + 1);
                }
                default:
                    throw new IllegalStateException("Cannot manage tile of type '" + c + "'");
            }
        }
    }
}
