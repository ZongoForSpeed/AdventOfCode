package com.adventofcode.year2016;

import com.adventofcode.common.graph.AStar;
import com.adventofcode.common.maths.Combinations;
import com.adventofcode.common.utils.Bits;
import com.adventofcode.common.utils.LongPair;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

public final class Day11 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11.class);

    public static long example() {
        // The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
        // The second floor contains a hydrogen generator.
        // The third floor contains a lithium generator.
        // The fourth floor contains nothing relevant.

        // hydrogen 1
        // lithium 3
        State start = State.of(1,
                Floor.of(IntList.of(1, 3), IntList.of()),
                Floor.of(IntList.of(), IntList.of(1)),
                Floor.of(IntList.of(), IntList.of(3)),
                Floor.of(IntList.of(), IntList.of())
        );

        State endState = State.of(4,
                Floor.of(IntList.of(), IntList.of()),
                Floor.of(IntList.of(), IntList.of()),
                Floor.of(IntList.of(), IntList.of()),
                Floor.of(IntList.of(1, 3), IntList.of(1, 3))
        );

        Floor currentFloor = start.getFloor();
        IntList microchips = currentFloor.getMicrochips();
        IntList generators1 = currentFloor.getGenerators();

        List<Item> items = Stream.concat(microchips.intStream().mapToObj(i -> Item.of(i, true)), generators1.intStream().mapToObj(i -> Item.of(i, false))).toList();

        List<LongPair> movedItems = Stream.concat(items.stream().map(List::of), Combinations.generate(items, 2)).map(m -> {
            long movedMicrochips = Bits.toBitSet(m.stream().filter(Item::type).mapToInt(Item::number));
            long movedGenerators = Bits.toBitSet(m.stream().filter(i -> !i.type()).mapToInt(Item::number));
            return LongPair.of(movedMicrochips, movedGenerators);
        }).filter(p -> currentFloor.validIfRemove(p.left(), p.right())).toList();

        List<AStar.Move<State>> nextStates = Stream.of(start.elevator - 1, start.elevator + 1)
                .filter(i -> i > 0 && i < 5)
                .flatMap(destination -> movedItems.stream().map(p -> start.nextState(destination, p)))
                .filter(State::valid)
                .map(AStar.Move::of)
                .toList();
        nextStates.forEach(s -> LOGGER.info("State: {}", s));

        RadioisotopeThermoelectricGenerators generators = new RadioisotopeThermoelectricGenerators(endState);
        return generators.algorithm(start, endState);
    }

    private static final class RadioisotopeThermoelectricGenerators extends AStar<State> {

        private final State end;

        private RadioisotopeThermoelectricGenerators(State end) {
            this.end = end;
        }

        @Override
        public Iterable<Move<State>> next(State current) {
            Floor currentFloor = current.getFloor();
            IntList microchips = currentFloor.getMicrochips();
            IntList generators = currentFloor.getGenerators();

            List<Item> items = Stream.concat(microchips.intStream().mapToObj(i -> Item.of(i, true)), generators.intStream().mapToObj(i -> Item.of(i, false))).toList();

            List<LongPair> movedItems = Stream.concat(items.stream().map(List::of), Combinations.generate(items, 2)).map(m -> {
                long movedMicrochips = Bits.toBitSet(m.stream().filter(Item::type).mapToInt(Item::number));
                long movedGenerators = Bits.toBitSet(m.stream().filter(i -> !i.type()).mapToInt(Item::number));
                return LongPair.of(movedMicrochips, movedGenerators);
            }).filter(p -> currentFloor.validIfRemove(p.left(), p.right())).toList();

            return Stream.of(current.elevator() - 1, current.elevator() + 1)
                    .filter(i -> i > 0 && i < 5)
                    .flatMap(destination -> movedItems.stream().map(p -> current.nextState(destination, p)))
                    .filter(State::valid)
                    .map(AStar.Move::of)
                    .toList();
        }

        @Override
        public long heuristic(State node) {
            return State.heuristic(node, end);
        }
    }

    /**
     * --- Day 11: Radioisotope Thermoelectric Generators ---
     *
     * You come upon a column of four floors that have been entirely sealed off
     * from the rest of the building except for a small dedicated lobby. There are
     * some radiation warnings and a big sign which reads "Radioisotope Testing
     * Facility".
     *
     * According to the project status board, this facility is currently being
     * used to experiment with Radioisotope Thermoelectric Generators (RTGs, or
     * simply "generators") that are designed to be paired with specially-
     * constructed microchips. Basically, an RTG is a highly radioactive rock that
     * generates electricity through heat.
     *
     * The experimental RTGs have poor radiation containment, so they're
     * dangerously radioactive. The chips are prototypes and don't have normal
     * radiation shielding, but they do have the ability to generate an
     * electromagnetic radiation shield when powered. Unfortunately, they can only
     * be powered by their corresponding RTG. An RTG powering a microchip is still
     * dangerous to other microchips.
     *
     * In other words, if a chip is ever left in the same area as another RTG, and
     * it's not connected to its own RTG, the chip will be fried. Therefore, it is
     * assumed that you will follow procedure and keep chips connected to their
     * corresponding RTG when they're in the same room, and away from other RTGs
     * otherwise.
     *
     * These microchips sound very interesting and useful to your current
     * activities, and you'd like to try to retrieve them. The fourth floor of the
     * facility has an assembling machine which can make a self-contained,
     * shielded computer for you to take with you - that is, if you can bring it
     * all of the RTGs and microchips.
     *
     * Within the radiation-shielded part of the facility (in which it's safe to
     * have these pre-assembly RTGs), there is an elevator that can move between
     * the four floors. Its capacity rating means it can carry at most yourself
     * and two RTGs or microchips in any combination. (They're rigged to some
     * heavy diagnostic equipment - the assembling machine will detach it for
     * you.) As a security measure, the elevator will only function if it contains
     * at least one RTG or microchip. The elevator always stops on each floor to
     * recharge, and this takes long enough that the items within it and the items
     * on that floor can irradiate each other. (You can prevent this if a
     * Microchip and its Generator end up on the same floor in this way, as they
     * can be connected while the elevator is recharging.)
     *
     * You make some notes of the locations of each component of interest (your
     * puzzle input). Before you don a hazmat suit and start moving things around,
     * you'd like to have an idea of what you need to do.
     *
     * When you enter the containment area, you and the elevator will start on the
     * first floor.
     *
     * For example, suppose the isolated area has the following arrangement:
     *
     * The first floor contains a hydrogen-compatible microchip and a lithium-
     * compatible microchip.
     * The second floor contains a hydrogen generator.
     * The third floor contains a lithium generator.
     * The fourth floor contains nothing relevant.
     *
     * As a diagram (F# for a Floor number, E for Elevator, H for Hydrogen, L for
     * Lithium, M for Microchip, and G for Generator), the initial state looks
     * like this:
     *
     * F4 .  .  .  .  .
     * F3 .  .  .  LG .
     * F2 .  HG .  .  .
     * F1 E  .  HM .  LM
     *
     * Then, to get everything up to the assembling machine on the fourth floor,
     * the following steps could be taken:
     *
     * - Bring the Hydrogen-compatible Microchip to the second floor, which is
     * safe because it can get power from the Hydrogen Generator:
     *
     * F4 .  .  .  .  .
     * F3 .  .  .  LG .
     * F2 E  HG HM .  .
     * F1 .  .  .  .  LM
     *
     * - Bring both Hydrogen-related items to the third floor, which is safe
     * because the Hydrogen-compatible microchip is getting power from its
     * generator:
     *
     * F4 .  .  .  .  .
     * F3 E  HG HM LG .
     * F2 .  .  .  .  .
     * F1 .  .  .  .  LM
     *
     * - Leave the Hydrogen Generator on floor three, but bring the Hydrogen-
     * compatible Microchip back down with you so you can still use the
     * elevator:
     *
     * F4 .  .  .  .  .
     * F3 .  HG .  LG .
     * F2 E  .  HM .  .
     * F1 .  .  .  .  LM
     *
     * - At the first floor, grab the Lithium-compatible Microchip, which is
     * safe because Microchips don't affect each other:
     *
     * F4 .  .  .  .  .
     * F3 .  HG .  LG .
     * F2 .  .  .  .  .
     * F1 E  .  HM .  LM
     *
     * - Bring both Microchips up one floor, where there is nothing to fry
     * them:
     *
     * F4 .  .  .  .  .
     * F3 .  HG .  LG .
     * F2 E  .  HM .  LM
     * F1 .  .  .  .  .
     *
     * - Bring both Microchips up again to floor three, where they can be
     * temporarily connected to their corresponding generators while the
     * elevator recharges, preventing either of them from being fried:
     *
     * F4 .  .  .  .  .
     * F3 E  HG HM LG LM
     * F2 .  .  .  .  .
     * F1 .  .  .  .  .
     *
     * - Bring both Microchips to the fourth floor:
     *
     * F4 E  .  HM .  LM
     * F3 .  HG .  LG .
     * F2 .  .  .  .  .
     * F1 .  .  .  .  .
     *
     * - Leave the Lithium-compatible microchip on the fourth floor, but bring the
     * Hydrogen-compatible one so you can still use the elevator; this is
     * safe because although the Lithium Generator is on the destination
     * floor, you can connect Hydrogen-compatible microchip to the Hydrogen
     * Generator there:
     *
     * F4 .  .  .  .  LM
     * F3 E  HG HM LG .
     * F2 .  .  .  .  .
     * F1 .  .  .  .  .
     *
     * - Bring both Generators up to the fourth floor, which is safe because
     * you can connect the Lithium-compatible Microchip to the Lithium
     * Generator upon arrival:
     *
     * F4 E  HG .  LG LM
     * F3 .  .  HM .  .
     * F2 .  .  .  .  .
     * F1 .  .  .  .  .
     *
     * - Bring the Lithium Microchip with you to the third floor so you can use
     * the elevator:
     *
     * F4 .  HG .  LG .
     * F3 E  .  HM .  LM
     * F2 .  .  .  .  .
     * F1 .  .  .  .  .
     *
     * - Bring both Microchips to the fourth floor:
     *
     * F4 E  HG HM LG LM
     * F3 .  .  .  .  .
     * F2 .  .  .  .  .
     * F1 .  .  .  .  .
     *
     * In this arrangement, it takes 11 steps to collect all of the objects at the
     * fourth floor for assembly. (Each elevator stop counts as one step, even if
     * nothing is added to or removed from it.)
     *
     * In your situation, what is the minimum number of steps required to bring
     * all of the objects to the fourth floor?
     *
     * Your puzzle answer was 33.
     */
    static long partOne() {
        // The first floor contains a promethium generator and a promethium-compatible microchip.
        // The second floor contains a cobalt generator, a curium generator, a ruthenium generator, and a plutonium
        // generator.
        // The third floor contains a cobalt-compatible microchip, a curium-compatible microchip, a ruthenium-compatible
        // microchip, and a plutonium-compatible microchip.
        // The fourth floor contains nothing relevant.

        // cobalt 1
        // curium 2
        // plutonium 3
        // promethium 4
        // ruthenium 5
        State start = State.of(1,
                Floor.of(IntList.of(4), IntList.of(4)),
                Floor.of(IntList.of(), IntList.of(1, 2, 3, 5)),
                Floor.of(IntList.of(1, 2, 3, 5), IntList.of()),
                Floor.of(IntList.of(), IntList.of())
        );

        State endState = State.of(4,
                Floor.of(IntList.of(), IntList.of()),
                Floor.of(IntList.of(), IntList.of()),
                Floor.of(IntList.of(), IntList.of()),
                Floor.of(IntList.of(1, 2, 3, 4, 5), IntList.of(1, 2, 3, 4, 5))
        );

        RadioisotopeThermoelectricGenerators generators = new RadioisotopeThermoelectricGenerators(endState);
        return generators.algorithm(start, endState);
    }

    /**
     * --- Part Two ---
     *
     * You step into the cleanroom separating the lobby from the isolated area and
     * put on the hazmat suit.
     *
     * Upon entering the isolated containment area, however, you notice some extra
     * parts on the first floor that weren't listed on the record outside:
     *
     * - An elerium generator.
     * - An elerium-compatible microchip.
     * - A dilithium generator.
     * - A dilithium-compatible microchip.
     *
     * These work just like the other generators and microchips. You'll have to
     * get them up to assembly as well.
     *
     * What is the minimum number of steps required to bring all of the objects,
     * including these four new ones, to the fourth floor?
     *
     * Your puzzle answer was 57.
     */
    static long partTwo() {
        // The first floor contains a promethium generator and a promethium-compatible microchip.
        // The second floor contains a cobalt generator, a curium generator, a ruthenium generator, and a plutonium
        // generator.
        // The third floor contains a cobalt-compatible microchip, a curium-compatible microchip, a ruthenium-compatible
        // microchip, and a plutonium-compatible microchip.
        // The fourth floor contains nothing relevant.

        // cobalt 1
        // curium 2
        // plutonium 3
        // promethium 4
        // ruthenium 5
        // dilithium 6
        // elerium 7
        State start = State.of(1,
                Floor.of(IntList.of(4, 6, 7), IntList.of(4, 6, 7)),
                Floor.of(IntList.of(), IntList.of(1, 2, 3, 5)),
                Floor.of(IntList.of(1, 2, 3, 5), IntList.of()),
                Floor.of(IntList.of(), IntList.of())
        );

        State endState = State.of(4,
                Floor.of(IntList.of(), IntList.of()),
                Floor.of(IntList.of(), IntList.of()),
                Floor.of(IntList.of(), IntList.of()),
                Floor.of(IntList.of(1, 2, 3, 4, 5, 6, 7), IntList.of(1, 2, 3, 4, 5, 6, 7))
        );

        RadioisotopeThermoelectricGenerators generators = new RadioisotopeThermoelectricGenerators(endState);
        return generators.algorithm(start, endState);
    }

    record Floor(long microchips, long generators) {
        public static Floor of(long microchips, long generators) {
            return new Floor(microchips, generators);
        }

        public static Floor of(IntList microchips, IntList generators) {
            return new Floor(Bits.toBitSet(microchips), Bits.toBitSet(generators));
        }

        private static boolean valid(long microchips, long generators) {
            if (generators == 0) {
                return true;
            }

            return (microchips & generators) == microchips;
        }

        private static long heuristic(long a, long b) {
            long diff = (a & ~b) | (~a & b);
            return Long.bitCount(diff);
        }

        public static long heuristic(Floor a, Floor b) {
            return heuristic(a.microchips(), b.microchips()) + heuristic(a.generators(), b.generators());
        }

        public boolean validIfRemove(long microchips, long generators) {
            return valid(this.microchips & ~microchips, this.generators & ~generators);
        }

        public boolean valid() {
            return valid(microchips, generators);
        }

        public IntList getMicrochips() {
            return Bits.convertBitSet(microchips);
        }

        public IntList getGenerators() {
            return Bits.convertBitSet(generators);
        }

        public Floor addItems(long microchips, long generators) {
            return new Floor(this.microchips | microchips, this.generators | generators);
        }

        public Floor removeItems(long microchips, long generators) {
            return new Floor(this.microchips & ~microchips, this.generators & ~generators);
        }

        @Override
        public String toString() {
            return "Floor{" +
                   "microchips=" + getMicrochips() +
                   ", generators=" + getGenerators() +
                   '}';
        }
    }

    record Item(int number, boolean type) {
        public static Item of(int number, boolean type) {
            return new Item(number, type);
        }
    }

    record State(int elevator, Floor first, Floor second, Floor third, Floor fourth) {
        public static State of(int elevator, Floor first, Floor second, Floor third, Floor fourth) {
            return new State(elevator, first, second, third, fourth);
        }

        public static long heuristic(State current, State end) {
            return 4 * Floor.heuristic(current.first(), end.first())
                   + 3 * Floor.heuristic(current.second(), end.second())
                   + 2 * Floor.heuristic(current.third(), end.third())
                   + Floor.heuristic(current.fourth(), end.fourth());
        }

        public Floor getFloor(int floor) {
            return switch (floor) {
                case 1 -> first;
                case 2 -> second;
                case 3 -> third;
                case 4 -> fourth;
                default -> throw new IllegalStateException("Invalid floor: " + floor);
            };
        }

        public Floor getFloor() {
            return getFloor(elevator);
        }

        private State nextState(int destination, LongPair moved) {
            Floor newDestination = getFloor(destination).addItems(moved.left(), moved.right());
            Floor newCurrent = getFloor().removeItems(moved.left(), moved.right());
            return new State(
                    destination,
                    floor(destination, 1, newDestination, newCurrent, first),
                    floor(destination, 2, newDestination, newCurrent, second),
                    floor(destination, 3, newDestination, newCurrent, third),
                    floor(destination, 4, newDestination, newCurrent, fourth));
        }

        private Floor floor(int destination, int x, Floor newDestination, Floor newCurrent, Floor first) {
            return destination == x ? newDestination : elevator == x ? newCurrent : first;
        }

        public boolean valid() {
            return first.valid() && second.valid() && third.valid() && fourth.valid();
        }
    }
}
