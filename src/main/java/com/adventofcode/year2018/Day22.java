package com.adventofcode.year2018;

import com.adventofcode.point.Direction;
import com.adventofcode.point.Point2D;
import com.adventofcode.point.map.EnumMap2D;
import com.adventofcode.point.map.IntegerMap;
import com.adventofcode.point.map.LongMap;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;
import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day22.class);
    private static final Pattern DEPTH_PATTERN = Pattern.compile("depth: (\\d+)");
    private static final Pattern TARGET_PATTERN = Pattern.compile("target: (\\d+),(\\d+)");

    /**
     * --- Day 22: Mode Maze ---
     *
     * This is it, your final stop: the year -483. It's snowing and dark outside;
     * the only light you can see is coming from a small cottage in the distance.
     * You make your way there and knock on the door.
     *
     * A portly man with a large, white beard answers the door and invites you
     * inside. For someone living near the North Pole in -483, he must not get
     * many visitors, but he doesn't act surprised to see you. Instead, he offers
     * you some milk and cookies.
     *
     * After talking for a while, he asks a favor of you. His friend hasn't come
     * back in a few hours, and he's not sure where he is. Scanning the region
     * briefly, you discover one life signal in a cave system nearby; his friend
     * must have taken shelter there. The man asks if you can go there to retrieve
     * his friend.
     *
     * The cave is divided into square regions which are either dominantly rocky,
     * narrow, or wet (called its type). Each region occupies exactly one
     * coordinate in X,Y format where X and Y are integers and zero or greater.
     * (Adjacent regions can be the same type.)
     *
     * The scan (your puzzle input) is not very detailed: it only reveals the
     * depth of the cave system and the coordinates of the target. However, it
     * does not reveal the type of each region. The mouth of the cave is at 0,0.
     *
     * The man explains that due to the unusual geology in the area, there is a
     * method to determine any region's type based on its erosion level. The
     * erosion level of a region can be determined from its geologic index. The
     * geologic index can be determined using the first rule that applies from the
     * list below:
     *
     *   - The region at 0,0 (the mouth of the cave) has a geologic index of 0.
     *   - The region at the coordinates of the target has a geologic index of 0.
     *   - If the region's Y coordinate is 0, the geologic index is its X
     *     coordinate times 16807.
     *   - If the region's X coordinate is 0, the geologic index is its Y
     *     coordinate times 48271.
     *   - Otherwise, the region's geologic index is the result of multiplying
     *     the erosion levels of the regions at X-1,Y and X,Y-1.
     *
     * A region's erosion level is its geologic index plus the cave system's
     * depth, all modulo 20183. Then:
     *
     *   - If the erosion level modulo 3 is 0, the region's type is rocky.
     *   - If the erosion level modulo 3 is 1, the region's type is wet.
     *   - If the erosion level modulo 3 is 2, the region's type is narrow.
     *
     * For example, suppose the cave system's depth is 510 and the target's
     * coordinates are 10,10. Using % to represent the modulo operator, the cavern
     * would look as follows:
     *
     *   - At 0,0, the geologic index is 0. The erosion level is
     *     (0 + 510) % 20183 = 510. The type is 510 % 3 = 0, rocky.
     *   - At 1,0, because the Y coordinate is 0, the geologic index is
     *     1 * 16807 = 16807. The erosion level is (16807 + 510) % 20183 = 17317.
     *     The type is 17317 % 3 = 1, wet.
     *   - At 0,1, because the X coordinate is 0, the geologic index is
     *     1 * 48271 = 48271. The erosion level is (48271 + 510) % 20183 = 8415.
     *     The type is 8415 % 3 = 0, rocky.
     *   - At 1,1, neither coordinate is 0 and it is not the coordinate of the
     *     target, so the geologic index is the erosion level of 0,1 (8415) times
     *     the erosion level of 1,0 (17317), 8415 * 17317 = 145722555. The
     *     erosion level is (145722555 + 510) % 20183 = 1805. The type is
     *     1805 % 3 = 2, narrow.
     *   - At 10,10, because they are the target's coordinates, the geologic
     *     index is 0. The erosion level is (0 + 510) % 20183 = 510. The type is
     *     510 % 3 = 0, rocky.
     *
     * Drawing this same cave system with rocky as ., wet as =, narrow as |, the
     * mouth as M, the target as T, with 0,0 in the top-left corner, X increasing
     * to the right, and Y increasing downward, the top-left corner of the map
     * looks like this:
     *
     * M=.|=.|.|=.|=|=.
     * .|=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||...|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Before you go in, you should determine the risk level of the area. For the
     * rectangle that has a top-left corner of region 0,0 and a bottom-right
     * corner of the region containing the target, add up the risk level of each
     * individual region: 0 for rocky regions, 1 for wet regions, and 2 for narrow
     * regions.
     *
     * In the cave system above, because the mouth is at 0,0 and the target is at
     * 10,10, adding up the risk level of all regions with an X coordinate from 0
     * to 10 and a Y coordinate from 0 to 10, this total is 114.
     *
     * What is the total risk level for the smallest rectangle that includes 0,0
     * and the target's coordinates?
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static int computeRiskLevel(Scanner scanner) {
            ModeMaze modeMaze = readInput(scanner);

            Point2D mouth = Point2D.of(0, 0);
            Point2D target = modeMaze.target();

            EnumMap2D<Region> mazeMap = buildMazeMap(modeMaze, target, mouth);

            LOGGER.debug("maze map: \n{}", mazeMap);

            int riskLevel = 0;
            for (Map.Entry<Point2D, Region> entry : mazeMap.entrySet()) {
                Point2D p = entry.getKey();
                if (p.x() <= target.x() && p.y() <= target.y()) {
                    riskLevel += entry.getValue().riskLevel();
                }
            }
            return riskLevel;
        }

    }

    /**
     * --- Part Two ---
     *
     * Okay, it's time to go rescue the man's friend.
     *
     * As you leave, he hands you some tools: a torch and some climbing gear. You
     * can't equip both tools at once, but you can choose to use neither.
     *
     * Tools can only be used in certain regions:
     *
     *   - In rocky regions, you can use the climbing gear or the torch. You
     *     cannot use neither (you'll likely slip and fall).
     *   - In wet regions, you can use the climbing gear or neither tool. You
     *     cannot use the torch (if it gets wet, you won't have a light source).
     *   - In narrow regions, you can use the torch or neither tool. You cannot
     *     use the climbing gear (it's too bulky to fit).
     *
     * You start at 0,0 (the mouth of the cave) with the torch equipped and must
     * reach the target coordinates as quickly as possible. The regions with
     * negative X or Y are solid rock and cannot be traversed. The fastest route
     * might involve entering regions beyond the X or Y coordinate of the target.
     *
     * You can move to an adjacent region (up, down, left, or right; never
     * diagonally) if your currently equipped tool allows you to enter that
     * region. Moving to an adjacent region takes one minute. (For example, if you
     * have the torch equipped, you can move between rocky and narrow regions, but
     * cannot enter wet regions.)
     *
     * You can change your currently equipped tool or put both away if your new
     * equipment would be valid for your current region. Switching to using the
     * climbing gear, torch, or neither always takes seven minutes, regardless of
     * which tools you start with. (For example, if you are in a rocky region, you
     * can switch from the torch to the climbing gear, but you cannot switch to
     * neither.)
     *
     * Finally, once you reach the target, you need the torch equipped before you
     * can find him in the dark. The target is always in a rocky region, so if you
     * arrive there with climbing gear equipped, you will need to spend seven
     * minutes switching to your torch.
     *
     * For example, using the same cave system as above, starting in the top left
     * corner (0,0) and moving to the bottom right corner (the target, 10,10) as
     * quickly as possible, one possible route is as follows, with your current
     * position marked X:
     *
     * Initially:
     * X=.|=.|.|=.|=|=.
     * .|=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||...|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Down:
     * M=.|=.|.|=.|=|=.
     * X|=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||...|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Right:
     * M=.|=.|.|=.|=|=.
     * .X=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||...|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Switch from using the torch to neither tool:
     * M=.|=.|.|=.|=|=.
     * .X=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||...|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Right 3:
     * M=.|=.|.|=.|=|=.
     * .|=|X|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||...|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Switch from using neither tool to the climbing gear:
     * M=.|=.|.|=.|=|=.
     * .|=|X|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||...|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Down 7:
     * M=.|=.|.|=.|=|=.
     * .|=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..X==..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||...|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Right:
     * M=.|=.|.|=.|=|=.
     * .|=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..=X=..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||...|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Down 3:
     * M=.|=.|.|=.|=|=.
     * .|=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||.X.|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Right:
     * M=.|=.|.|=.|=|=.
     * .|=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||..X|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Down:
     * M=.|=.|.|=.|=|=.
     * .|=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||...|==..|=.|
     * =.=|=.X..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Right 4:
     * M=.|=.|.|=.|=|=.
     * .|=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===T===||
     * =|||...|==..|=.|
     * =.=|=.=..=X||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Up 2:
     * M=.|=.|.|=.|=|=.
     * .|=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===X===||
     * =|||...|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * Switch from using the climbing gear to the torch:
     * M=.|=.|.|=.|=|=.
     * .|=|=|||..|.=...
     * .==|....||=..|==
     * =.|....|.==.|==.
     * =|..==...=.|==..
     * =||.=.=||=|=..|=
     * |.=.===|||..=..|
     * |..==||=.|==|===
     * .=..===..=|.|||.
     * .======|||=|=.|=
     * .===|=|===X===||
     * =|||...|==..|=.|
     * =.=|=.=..=.||==|
     * ||=|=...|==.=|==
     * |=.=||===.|||===
     * ||.|==.|.|.||=||
     *
     * This is tied with other routes as the fastest way to reach the target: 45
     * minutes. In it, 21 minutes are spent switching tools (three times, seven
     * minutes each) and the remaining 24 minutes are spent moving.
     *
     * What is the fewest number of minutes you can take to reach the target?
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        public static int findFastestWay(Scanner scanner) {
            ModeMaze modeMaze = readInput(scanner);

            Point2D mouth = Point2D.of(0, 0);
            Point2D target = modeMaze.target();

            EnumMap2D<Region> mapRegion = buildMazeMap(modeMaze, target, mouth);

            PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(State::heuristic));
            queue.add(new State(0, mouth, Tool.TORCH, Point2D.manhattanDistance(mouth, target)));

            int duration = 0;

            Set<Pair<Point2D, Tool>> seen = new HashSet<>();

            while (!queue.isEmpty()) {
                State current = queue.poll();
                seen.add(Pair.of(current.position(), current.currentTool()));
                if (target.equals(current.position()) && current.currentTool() == Tool.TORCH) {
                    LOGGER.info("State: {}", current);
                    duration = current.time();
                    break;
                }
                List<State> moves = current.possibleMoves(mapRegion, target);
                for (State move : moves) {
                    if (!seen.contains(Pair.of(move.position(), move.currentTool()))) {
                        queue.add(move);
                    }
                }
            }
            return duration;
        }
    }


    private static EnumMap2D<Region> buildMazeMap(ModeMaze modeMaze, Point2D target, Point2D mouth) {
        LOGGER.info("depth: {}, target: {}", modeMaze.depth(), target);
        int max = target.x() + target.y() + 1;

        LongMap geologicIndex = new LongMap(max, max, -1);
        IntegerMap erosionLevels = new IntegerMap(max, max, -1);
        geologicIndex.set(mouth, 0);

        for (int x = 1; x <= max; ++x) {
            long value = (16807L * x);
            geologicIndex.set(x, 0, value);
            erosionLevels.set(x, 0, (int) ((value + modeMaze.depth()) % 20183));
        }
        for (int y = 1; y <= max; ++y) {
            long value = (48271L * y);
            geologicIndex.set(0, y, value);
            erosionLevels.set(0, y, (int) ((value + modeMaze.depth()) % 20183));
        }
        for (int x = 1; x <= max; ++x) {
            for (int y = 1; y <= max; ++y) {
                long value = (long) erosionLevels.get(x - 1, y) * erosionLevels.get(x, y - 1);
                geologicIndex.set(x, y, value);
                erosionLevels.set(x, y, (int) ((value + modeMaze.depth()) % 20183));
            }
        }

        LOGGER.trace("geologicIndex = {}", geologicIndex);

        EnumMap2D<Region> mapRegion = new EnumMap2D<>(Region::toChar);

        for (ObjectIntPair<Point2D> entry : erosionLevels.entries()) {
            Point2D p = entry.left();
            int level = entry.rightInt();

            switch (level % 3) {
                case 0 -> mapRegion.put(p, Region.ROCKY);
                case 1 -> mapRegion.put(p, Region.WET);
                case 2 -> mapRegion.put(p, Region.NARROW);
                default -> throw new IllegalStateException();
            }
        }

        mapRegion.put(mouth, Region.MOUTH);
        mapRegion.put(target, Region.TARGET);

        LOGGER.trace("Draw: \n{}", mapRegion);
        return mapRegion;
    }

    private static ModeMaze readInput(Scanner scanner) {
        int depth = 0;
        Point2D target = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = DEPTH_PATTERN.matcher(line);
            if (matcher.matches()) {
                depth = Integer.parseInt(matcher.group(1));
            }

            matcher = TARGET_PATTERN.matcher(line);
            if (matcher.matches()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                target = Point2D.of(x, y);
            }
        }
        if (target == null) {
            throw new IllegalStateException();
        }
        return new ModeMaze(depth, target);
    }

    enum Tool {
        CLIMBING_GEAR, // rocky, wet
        TORCH, // rocky, narrow
        NEITHER; // narrow, wet
    }

    enum Region {
        MOUTH(0) {
            public char toChar() {
                return 'M';
            }

            public boolean accept(Tool tool) {
                return true;
            }

        },
        ROCKY(0) {
            public char toChar() {
                return '.';
            }

            public boolean accept(Tool tool) {
                return tool == Tool.CLIMBING_GEAR || tool == Tool.TORCH;
            }
        },
        WET(1) {
            public char toChar() {
                return '=';
            }

            public boolean accept(Tool tool) {
                return tool == Tool.CLIMBING_GEAR || tool == Tool.NEITHER;
            }
        },
        NARROW(2) {
            public char toChar() {
                return '|';
            }

            public boolean accept(Tool tool) {
                return tool == Tool.NEITHER || tool == Tool.TORCH;
            }
        },
        TARGET(0) {
            public char toChar() {
                return 'T';
            }

            public boolean accept(Tool tool) {
                return true;
            }
        };

        private final int riskLevel;

        Region(int riskLevel) {
            this.riskLevel = riskLevel;
        }

        public abstract boolean accept(Tool tool);

        public abstract char toChar();

        public int riskLevel() {
            return riskLevel;
        }
    }

    private record ModeMaze(int depth, Point2D target) {
    }

    record State(int time, Point2D position, Tool currentTool, int heuristic) {
        List<State> possibleMoves(Map<Point2D, Region> mazeMap, Point2D target) {
            Region region = mazeMap.get(position);
            List<State> moves = new ArrayList<>();
            for (Tool newTool : Tool.values()) {
                if (region.accept(newTool)) {
                    moves.add(new State(time + 7, position, newTool, time + 7 + Point2D.manhattanDistance(position, target)));
                }
            }

            for (Direction value : Direction.values()) {
                Point2D move = position.move(value);
                Region newRegion = mazeMap.get(move);
                if (newRegion == null) {
                    continue;
                }

                if (newRegion == Region.TARGET) {
                    moves.add(new State(time + 1, move, currentTool, time + 1));
                    continue;
                }

                if (newRegion.accept(currentTool)) {
                    moves.add(new State(time + 1, move, currentTool, time + 1 + Point2D.manhattanDistance(move, target)));
                }
            }

            return moves;
        }
    }
}
