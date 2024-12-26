package com.adventofcode.year2024;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public final class Day06 {

    private Day06() {
        // No-Op
    }

    private static Set<Point2D> findPath(CharMap map) {
        Point2D guardPosition = null;
        for (ObjectCharPair<Point2D> entry : map.entries()) {
            if (entry.rightChar() == '^') {
                guardPosition = entry.left();
                break;
            }
        }

        Set<Point2D> allPoints = new HashSet<>(map.points());
        Set<Point2D> path = new HashSet<>();
        Guard current = new Guard(guardPosition, Direction.UP);
        while (true) {
            path.add(current.position());
            Guard move = current.move();
            if (map.get(move.position()) == '#') {
                current = current.turn();
            } else {
                current = move;
            }
            if (!allPoints.contains(current.position())) {
                break;
            }
        }
        return path;
    }

    /**
     * --- Day 6: Guard Gallivant ---
     * <p>
     * The Historians use their fancy device again, this time to whisk you all
     * away to the North Pole prototype suit manufacturing lab... in the year
     * 1518! It turns out that having direct access to history is very convenient
     * for a group of historians.
     * <p>
     * You still have to be careful of time paradoxes, and so it will be important
     * to avoid anyone from 1518 while The Historians search for the Chief.
     * Unfortunately, a single guard is patrolling this part of the lab.
     * <p>
     * Maybe you can work out where the guard will go ahead of time so that The
     * Historians can search safely?
     * <p>
     * You start by making a map (your puzzle input) of the situation. For
     * example:
     * <p>
     * ....#.....
     * .........#
     * ..........
     * ..#.......
     * .......#..
     * ..........
     * .#..^.....
     * ........#.
     * #.........
     * ......#...
     * <p>
     * The map shows the current position of the guard with ^ (to indicate the
     * guard is currently facing up from the perspective of the map). Any
     * obstructions - crates, desks, alchemical reactors, etc. - are shown as #.
     * <p>
     * Lab guards in 1518 follow a very strict patrol protocol which involves
     * repeatedly following these steps:
     * <p>
     * - If there is something directly in front of you, turn right 90 degrees.
     * - Otherwise, take a step forward.
     * <p>
     * Following the above protocol, the guard moves up several times until she
     * reaches an obstacle (in this case, a pile of failed suit prototypes):
     * <p>
     * ....#.....
     * ....^....#
     * ..........
     * ..#.......
     * .......#..
     * ..........
     * .#........
     * ........#.
     * #.........
     * ......#...
     * <p>
     * Because there is now an obstacle in front of the guard, she turns right
     * before continuing straight in her new facing direction:
     * <p>
     * ....#.....
     * ........>#
     * ..........
     * ..#.......
     * .......#..
     * ..........
     * .#........
     * ........#.
     * #.........
     * ......#...
     * <p>
     * Reaching another obstacle (a spool of several very long polymers), she
     * turns right again and continues downward:
     * <p>
     * ....#.....
     * .........#
     * ..........
     * ..#.......
     * .......#..
     * ..........
     * .#......v.
     * ........#.
     * #.........
     * ......#...
     * <p>
     * This process continues for a while, but the guard eventually leaves the
     * mapped area (after walking past a tank of universal solvent):
     * <p>
     * ....#.....
     * .........#
     * ..........
     * ..#.......
     * .......#..
     * ..........
     * .#........
     * ........#.
     * #.........
     * ......#v..
     * <p>
     * By predicting the guard's route, you can determine which specific positions
     * in the lab will be in the patrol path. Including the guard's starting
     * position, the positions visited by the guard before leaving the area are
     * marked with an X:
     * <p>
     * ....#.....
     * ....XXXXX#
     * ....X...X.
     * ..#.X...X.
     * ..XXXXX#X.
     * ..X.X.X.X.
     * .#XXXXXXX.
     * .XXXXXXX#.
     * #XXXXXXX..
     * ......#X..
     * <p>
     * In this example, the guard will visit 41 distinct positions on your map.
     * <p>
     * Predict the path of the guard. How many distinct positions will the guard
     * visit before leaving the mapped area?
     */
    public static int partOne(Scanner scanner) {
        CharMap map = CharMap.read(scanner, ignore -> true);

        return findPath(map).size();
    }

    /**
     * --- Part Two ---
     * <p>
     * While The Historians begin working around the guard's patrol route, you
     * borrow their fancy device and step outside the lab. From the safety of a
     * supply closet, you time travel through the last few months and record the
     * nightly status of the lab's guard post on the walls of the closet.
     * <p>
     * Returning after what seems like only a few seconds to The Historians, they
     * explain that the guard's patrol area is simply too large for them to safely
     * search the lab without getting caught.
     * <p>
     * Fortunately, they are pretty sure that adding a single new obstruction
     * won't cause a time paradox. They'd like to place the new obstruction in
     * such a way that the guard will get stuck in a loop, making the rest of the
     * lab safe to search.
     * <p>
     * To have the lowest chance of creating a time paradox, The Historians would
     * like to know all of the possible positions for such an obstruction. The new
     * obstruction can't be placed at the guard's starting position - the guard is
     * there right now and would notice.
     * <p>
     * In the above example, there are only 6 different positions where a new
     * obstruction would cause the guard to get stuck in a loop. The diagrams of
     * these six situations use O to mark the new obstruction, | to show a
     * position where the guard moves up/down, - to show a position where the
     * guard moves left/right, and + to show a position where the guard moves both
     * up/down and left/right.
     * <p>
     * Option one, put a printing press next to the guard's starting position:
     * <p>
     * ....#.....
     * ....+---+#
     * ....|...|.
     * ..#.|...|.
     * ....|..#|.
     * ....|...|.
     * .#.O^---+.
     * ........#.
     * #.........
     * ......#...
     * <p>
     * Option two, put a stack of failed suit prototypes in the bottom right
     * quadrant of the mapped area:
     * <p>
     * ....#.....
     * ....+---+#
     * ....|...|.
     * ..#.|...|.
     * ..+-+-+#|.
     * ..|.|.|.|.
     * .#+-^-+-+.
     * ......O.#.
     * #.........
     * ......#...
     * <p>
     * Option three, put a crate of chimney-squeeze prototype fabric next to the
     * standing desk in the bottom right quadrant:
     * <p>
     * ....#.....
     * ....+---+#
     * ....|...|.
     * ..#.|...|.
     * ..+-+-+#|.
     * ..|.|.|.|.
     * .#+-^-+-+.
     * .+----+O#.
     * #+----+...
     * ......#...
     * <p>
     * Option four, put an alchemical retroencabulator near the bottom left
     * corner:
     * <p>
     * ....#.....
     * ....+---+#
     * ....|...|.
     * ..#.|...|.
     * ..+-+-+#|.
     * ..|.|.|.|.
     * .#+-^-+-+.
     * ..|...|.#.
     * #O+---+...
     * ......#...
     * <p>
     * Option five, put the alchemical retroencabulator a bit to the right
     * instead:
     * <p>
     * ....#.....
     * ....+---+#
     * ....|...|.
     * ..#.|...|.
     * ..+-+-+#|.
     * ..|.|.|.|.
     * .#+-^-+-+.
     * ....|.|.#.
     * #..O+-+...
     * ......#...
     * <p>
     * Option six, put a tank of sovereign glue right next to the tank of
     * universal solvent:
     * <p>
     * ....#.....
     * ....+---+#
     * ....|...|.
     * ..#.|...|.
     * ..+-+-+#|.
     * ..|.|.|.|.
     * .#+-^-+-+.
     * .+----++#.
     * #+----++..
     * ......#O..
     * <p>
     * It doesn't really matter what you choose to use as an obstacle so long as
     * you and The Historians can put it into position without the guard noticing.
     * The important thing is having enough options that you can find one that
     * minimizes time paradoxes, and in this example, there are 6 different
     * positions you could choose.
     * <p>
     * You need to get the guard stuck in a loop by adding a single new
     * obstruction. How many different positions could you choose for this
     * obstruction?
     */
    public static int partTwo(Scanner scanner) {
        CharMap map = CharMap.read(scanner, ignore -> true);

        Point2D guardPosition = null;
        for (ObjectCharPair<Point2D> entry : map.entries()) {
            if (entry.rightChar() == '^') {
                guardPosition = entry.left();
                break;
            }
        }

        Guard guard = new Guard(guardPosition, Direction.UP);
        int countCycle = 0;
        for (Point2D inPath : findPath(map)) {
            if (inPath.equals(guardPosition)) {
                continue;
            }
            CharMap modifiedMap = new CharMap(map);
            modifiedMap.set(inPath, '#');

            if (cycle(guard, modifiedMap)) {
                countCycle++;
            }
        }

        return countCycle;
    }

    private static boolean cycle(Guard guard, CharMap map) {
        Set<Point2D> allPoints = new HashSet<>(map.points());
        Guard current = guard;
        Set<Guard> states = new HashSet<>();
        while (true) {
            if (states.add(current)) {
                Guard move = current.move();
                if (map.get(move.position()) == '#') {
                    current = current.turn();
                } else {
                    current = move;
                }
                if (!allPoints.contains(current.position())) {
                    break;
                }
            } else {
                return true;
            }

        }
        return false;
    }

    private record Guard(Point2D position, Direction direction) {
        // TODO replace with Position2D
        Guard move() {
            return new Guard(position.move(direction), direction);
        }

        Guard turn() {
            return new Guard(position, direction.right());
        }
    }
}
