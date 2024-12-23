package com.adventofcode.year2021;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.LongStream;

public final class Day16 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16.class);

    private Day16() {
        // No-Op
    }

    private static List<Packet> decodePackets(Deque<Integer> data) {
        return decodePackets(data, Integer.MAX_VALUE);
    }

    private static List<Packet> decodePackets(Deque<Integer> data, int count) {
        List<Packet> packets = new ArrayList<>();
        int remaining = count;
        while (!data.isEmpty() && remaining > 0) {
            packets.add(decodePacket(data));
            remaining--;
        }

        return packets;
    }

    private static int readInteger(Deque<Integer> data, int size) {
        int result = 0;
        for (int s = 0; s < size; ++s) {
            result *= 2;
            result += data.removeFirst();
        }

        return result;
    }

    private static IntList readBits(Deque<Integer> data, int size) {
        IntList result = new IntArrayList();
        for (int s = 0; s < size; ++s) {
            result.add(data.removeFirst().intValue());
        }

        return result;
    }

    private static Packet decodePacket(Deque<Integer> data) {
        int version = readInteger(data, 3);
        int type = readInteger(data, 3);

        if (type == 4) {
            LOGGER.info("Data {}", data);
            IntList allBits = new IntArrayList();
            while (data.removeFirst() == 1) {
                allBits.addAll(readBits(data, 4));
            }

            allBits.addAll(readBits(data, 4));

            long value = allBits.intStream().mapToLong(t -> t).reduce(0L, (a, v) -> 2L * a + v);
            return new LiteralPacket(version, type, value);
        }

        int lengthType = readInteger(data, 1);
        if (lengthType == 0) {
            LOGGER.info("Data {}", data);
            int totalLength = readInteger(data, 15);
            Deque<Integer> subData = new ArrayDeque<>(readBits(data, totalLength));
            return new OperatorPacket(version, type, decodePackets(subData));
        } else {
            int count = readInteger(data, 11);
            return new OperatorPacket(version, type, decodePackets(data, count));
        }
    }

    private static IntList readBits(String input) {
        IntList bytes = new IntArrayList();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
                case '0' -> bytes.addAll(IntList.of(0, 0, 0, 0));
                case '1' -> bytes.addAll(IntList.of(0, 0, 0, 1));
                case '2' -> bytes.addAll(IntList.of(0, 0, 1, 0));
                case '3' -> bytes.addAll(IntList.of(0, 0, 1, 1));
                case '4' -> bytes.addAll(IntList.of(0, 1, 0, 0));
                case '5' -> bytes.addAll(IntList.of(0, 1, 0, 1));
                case '6' -> bytes.addAll(IntList.of(0, 1, 1, 0));
                case '7' -> bytes.addAll(IntList.of(0, 1, 1, 1));
                case '8' -> bytes.addAll(IntList.of(1, 0, 0, 0));
                case '9' -> bytes.addAll(IntList.of(1, 0, 0, 1));
                case 'A' -> bytes.addAll(IntList.of(1, 0, 1, 0));
                case 'B' -> bytes.addAll(IntList.of(1, 0, 1, 1));
                case 'C' -> bytes.addAll(IntList.of(1, 1, 0, 0));
                case 'D' -> bytes.addAll(IntList.of(1, 1, 0, 1));
                case 'E' -> bytes.addAll(IntList.of(1, 1, 1, 0));
                case 'F' -> bytes.addAll(IntList.of(1, 1, 1, 1));
            }
        }
        LOGGER.info("Bytes: {}", bytes);
        return bytes;
    }

    /**
     * --- Day 16: Packet Decoder ---
     * <p>
     * As you leave the cave and reach open waters, you receive a transmission
     * from the Elves back on the ship.
     * <p>
     * The transmission was sent using the Buoyancy Interchange Transmission
     * System (BITS), a method of packing numeric expressions into a binary
     * sequence. Your submarine's computer has saved the transmission in
     * hexadecimal (your puzzle input).
     * <p>
     * The first step of decoding the message is to convert the hexadecimal
     * representation into binary. Each character of hexadecimal corresponds to
     * four bits of binary data:
     * <p>
     * 0 = 0000
     * 1 = 0001
     * 2 = 0010
     * 3 = 0011
     * 4 = 0100
     * 5 = 0101
     * 6 = 0110
     * 7 = 0111
     * 8 = 1000
     * 9 = 1001
     * A = 1010
     * B = 1011
     * C = 1100
     * D = 1101
     * E = 1110
     * F = 1111
     * <p>
     * The BITS transmission contains a single packet at its outermost layer which
     * itself contains many other packets. The hexadecimal representation of this
     * packet might encode a few extra 0 bits at the end; these are not part of
     * the transmission and should be ignored.
     * <p>
     * Every packet begins with a standard header: the first three bits encode the
     * packet version, and the next three bits encode the packet type ID. These
     * two values are numbers; all numbers encoded in any packet are represented
     * as binary with the most significant bit first. For example, a version
     * encoded as the binary sequence 100 represents the number 4.
     * <p>
     * Packets with type ID 4 represent a literal value. Literal value packets
     * encode a single binary number. To do this, the binary number is padded with
     * leading zeroes until its length is a multiple of four bits, and then it is
     * broken into groups of four bits. Each group is prefixed by a 1 bit except
     * the last group, which is prefixed by a 0 bit. These groups of five bits
     * immediately follow the packet header. For example, the hexadecimal string
     * D2FE28 becomes:
     * <p>
     * 110100101111111000101000
     * VVVTTTAAAAABBBBBCCCCC
     * <p>
     * Below each bit is a label indicating its purpose:
     * <p>
     * - The three bits labeled V (110) are the packet version, 6.
     * - The three bits labeled T (100) are the packet type ID, 4, which means
     * the packet is a literal value.
     * - The five bits labeled A (10111) start with a 1 (not the last group,
     * keep reading) and contain the first four bits of the number, 0111.
     * - The five bits labeled B (11110) start with a 1 (not the last group,
     * keep reading) and contain four more bits of the number, 1110.
     * - The five bits labeled C (00101) start with a 0 (last group, end of
     * packet) and contain the last four bits of the number, 0101.
     * - The three unlabeled 0 bits at the end are extra due to the hexadecimal
     * representation and should be ignored.
     * <p>
     * So, this packet represents a literal value with binary representation
     * 011111100101, which is 2021 in decimal.
     * <p>
     * Every other type of packet (any packet with a type ID other than 4)
     * represent an operator that performs some calculation on one or more sub-
     * packets contained within. Right now, the specific operations aren't
     * important; focus on parsing the hierarchy of sub-packets.
     * <p>
     * An operator packet contains one or more packets. To indicate which
     * subsequent binary data represents its sub-packets, an operator packet can
     * use one of two modes indicated by the bit immediately after the packet
     * header; this is called the length type ID:
     * <p>
     * - If the length type ID is 0, then the next 15 bits are a number that
     * represents the total length in bits of the sub-packets contained by
     * this packet.
     * - If the length type ID is 1, then the next 11 bits are a number that
     * represents the number of sub-packets immediately contained by this
     * packet.
     * <p>
     * Finally, after the length type ID bit and the 15-bit or 11-bit field, the
     * sub-packets appear.
     * <p>
     * For example, here is an operator packet (hexadecimal string 38006F45291200)
     * with length type ID 0 that contains two sub-packets:
     * <p>
     * 00111000000000000110111101000101001010010001001000000000
     * VVVTTTILLLLLLLLLLLLLLLAAAAAAAAAAABBBBBBBBBBBBBBBB
     * <p>
     * - The three bits labeled V (001) are the packet version, 1.
     * - The three bits labeled T (110) are the packet type ID, 6, which means
     * the packet is an operator.
     * - The bit labeled I (0) is the length type ID, which indicates that the
     * length is a 15-bit number representing the number of bits in the sub-
     * packets.
     * - The 15 bits labeled L (000000000011011) contain the length of the sub-
     * packets in bits, 27.
     * - The 11 bits labeled A contain the first sub-packet, a literal value
     * representing the number 10.
     * - The 16 bits labeled B contain the second sub-packet, a literal value
     * representing the number 20.
     * <p>
     * After reading 11 and 16 bits of sub-packet data, the total length indicated in
     * L (27) is reached, and so parsing of this packet stops.
     * <p>
     * As another example, here is an operator packet (hexadecimal string
     * EE00D40C823060) with length type ID 1 that contains three sub-packets:
     * <p>
     * 11101110000000001101010000001100100000100011000001100000
     * VVVTTTILLLLLLLLLLLAAAAAAAAAAABBBBBBBBBBBCCCCCCCCCCC
     * <p>
     * - The three bits labeled V (111) are the packet version, 7.
     * - The three bits labeled T (011) are the packet type ID, 3, which means
     * the packet is an operator.
     * - The bit labeled I (1) is the length type ID, which indicates that the
     * length is a 11-bit number representing the number of sub-packets.
     * - The 11 bits labeled L (00000000011) contain the number of sub-packets,
     * 3.
     * - The 11 bits labeled A contain the first sub-packet, a literal value
     * representing the number 1.
     * - The 11 bits labeled B contain the second sub-packet, a literal value
     * representing the number 2.
     * - The 11 bits labeled C contain the third sub-packet, a literal value
     * representing the number 3.
     * <p>
     * After reading 3 complete sub-packets, the number of sub-packets indicated
     * in L (3) is reached, and so parsing of this packet stops.
     * <p>
     * For now, parse the hierarchy of the packets throughout the transmission and
     * add up all of the version numbers.
     * <p>
     * Here are a few more examples of hexadecimal-encoded transmissions:
     * <p>
     * - 8A004A801A8002F478 represents an operator packet (version 4) which
     * contains an operator packet (version 1) which contains an operator
     * packet (version 5) which contains a literal value (version 6); this
     * packet has a version sum of 16.
     * - 620080001611562C8802118E34 represents an operator packet (version 3)
     * which contains two sub-packets; each sub-packet is an operator packet
     * that contains two literal values. This packet has a version sum of 12.
     * - C0015000016115A2E0802F182340 has the same structure as the previous
     * example, but the outermost packet uses a different length type ID.
     * This packet has a version sum of 23.
     * - A0016C880162017C3686B18A3D4780 is an operator packet that contains an
     * operator packet that contains an operator packet that contains five
     * literal values; it has a version sum of 31.
     * <p>
     * Decode the structure of your hexadecimal-encoded BITS transmission; what do
     * you get if you add up the version numbers in all packets?
     */
    static int version(String string) {
        LOGGER.info("Decode string {}", string);
        Deque<Integer> data = new ArrayDeque<>(readBits(string));
        Packet packet = decodePacket(data);

        int version = packet.computeVersion();
        LOGGER.info("Version: {}", version);
        return version;
    }

    /**
     * --- Part Two ---
     * <p>
     * Now that you have the structure of your transmission decoded, you can
     * calculate the value of the expression it represents.
     * <p>
     * Literal values (type ID 4) represent a single number as described above.
     * The remaining type IDs are more interesting:
     * <p>
     * - Packets with type ID 0 are sum packets - their value is the sum of the
     * values of their sub-packets. If they only have a single sub-packet,
     * their value is the value of the sub-packet.
     * - Packets with type ID 1 are product packets - their value is the result
     * of multiplying together the values of their sub-packets. If they only
     * have a single sub-packet, their value is the value of the sub-packet.
     * - Packets with type ID 2 are minimum packets - their value is the
     * minimum of the values of their sub-packets.
     * - Packets with type ID 3 are maximum packets - their value is the
     * maximum of the values of their sub-packets.
     * - Packets with type ID 5 are greater than packets - their value is 1 if
     * the value of the first sub-packet is greater than the value of the
     * second sub-packet; otherwise, their value is 0. These packets always
     * have exactly two sub-packets.
     * - Packets with type ID 6 are less than packets - their value is 1 if the
     * value of the first sub-packet is less than the value of the second
     * sub-packet; otherwise, their value is 0. These packets always have
     * exactly two sub-packets.
     * - Packets with type ID 7 are equal to packets - their value is 1 if the
     * value of the first sub-packet is equal to the value of the second sub-
     * packet; otherwise, their value is 0. These packets always have exactly
     * two sub-packets.
     * <p>
     * Using these rules, you can now work out the value of the outermost packet
     * in your BITS transmission.
     * <p>
     * For example:
     * <p>
     * - C200B40A82 finds the sum of 1 and 2, resulting in the value 3.
     * - 04005AC33890 finds the product of 6 and 9, resulting in the value 54.
     * - 880086C3E88112 finds the minimum of 7, 8, and 9, resulting in the
     * value 7.
     * - CE00C43D881120 finds the maximum of 7, 8, and 9, resulting in the
     * value 9.
     * - D8005AC2A8F0 produces 1, because 5 is less than 15.
     * - F600BC2D8F produces 0, because 5 is not greater than 15.
     * - 9C005AC2F8F0 produces 0, because 5 is not equal to 15.
     * - 9C0141080250320F1802104A08 produces 1, because 1 + 3 = 2 * 2.
     * <p>
     * What do you get if you evaluate the expression represented by your hexadecimal-
     * encoded BITS transmission?
     */
    static long evaluate(String string) {
        LOGGER.info("Decode string {}", string);
        Deque<Integer> data = new ArrayDeque<>(readBits(string));
        Packet packet = decodePacket(data);

        long evaluate = packet.evaluate();
        LOGGER.info("Evaluate: {}", evaluate);
        return evaluate;
    }

    abstract static class Packet {
        private final int version;
        private final int type;

        Packet(int version, int type) {
            this.version = version;
            this.type = type;
        }

        public int version() {
            return version;
        }

        public int type() {
            return type;
        }

        public abstract int computeVersion();

        public abstract long evaluate();

        @Override
        public String toString() {
            return "Packet{" +
                   "version=" + version +
                   ", type=" + type +
                   '}';
        }
    }

    static class LiteralPacket extends Packet {
        final long value;

        LiteralPacket(int version, int type, long value) {
            super(version, type);
            this.value = value;
        }

        long value() {
            return value;
        }

        @Override
        public int computeVersion() {
            return super.version();
        }

        @Override
        public long evaluate() {
            return value;
        }

        @Override
        public String toString() {
            return "LiteralPacket{" +
                   "version=" + super.version() +
                   ", type=" + type() +
                   ", value=" + value() +
                   '}';
        }
    }

    static class OperatorPacket extends Packet {
        private final List<Packet> subpackets;

        OperatorPacket(int version, int type, List<Packet> subpackets) {
            super(version, type);
            this.subpackets = subpackets;
        }

        @Override
        public int computeVersion() {
            int sum = subpackets.stream().mapToInt(Packet::computeVersion).sum();
            return sum + super.version();
        }

        @Override
        public long evaluate() {
            LongStream longStream = subpackets.stream().mapToLong(Packet::evaluate);
            long[] longs;
            return switch (type()) {
                case 0 -> longStream.sum();
                case 1 -> longStream.reduce(1, (a, b) -> a * b);
                case 2 -> longStream.min().orElseThrow();
                case 3 -> longStream.max().orElseThrow();
                case 5 -> {
                    longs = longStream.toArray();
                    yield longs[0] > longs[1] ? 1 : 0;
                }
                case 6 -> {
                    longs = longStream.toArray();
                    yield longs[0] < longs[1] ? 1 : 0;
                }
                case 7 -> {
                    longs = longStream.toArray();
                    yield longs[0] == longs[1] ? 1 : 0;
                }
                default -> throw new IllegalStateException("Unexpected value: " + type());
            };
        }
    }
}
