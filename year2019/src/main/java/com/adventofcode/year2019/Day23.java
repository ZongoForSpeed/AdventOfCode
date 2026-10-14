package com.adventofcode.year2019;

import com.adventofcode.year2019.code.Intcode;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public final class Day23 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23.class);

    private Day23() {
    }

    /**
     * --- Day 23: Category Six ---
     * The droids have finished repairing as much of the ship as they can. Their report indicates that this was a
     * Category 6 disaster - not because it was that bad, but because it destroyed the stockpile of Category 6 network
     * cables as well as most of the ship's network infrastructure.
     * <p>
     * You'll need to rebuild the network from scratch.
     * <p>
     * The computers on the network are standard Intcode computers that communicate by sending packets to each other.
     * There are 50 of them in total, each running a copy of the same Network Interface Controller (NIC) software
     * (your puzzle input). The computers have network addresses 0 through 49; when each computer boots up, it will
     * request its network address via a single input instruction. Be sure to give each computer a unique network address.
     * <p>
     * Once a computer has received its network address, it will begin doing work and communicating over the network by
     * sending and receiving packets. All packets contain two values named X and Y. Packets sent to a computer are queued
     * by the recipient and read in the order they are received.
     * <p>
     * To send a packet to another computer, the NIC will use three output instructions that provide the destination
     * address of the packet followed by its X and Y values. For example, three output instructions that provide the
     * values 10, 20, 30 would send a packet with X=20 and Y=30 to the computer with address 10.
     * <p>
     * To receive a packet from another computer, the NIC will use an input instruction. If the incoming packet queue is
     * empty, provide -1. Otherwise, provide the X value of the next packet; the computer will then use a second input
     * instruction to receive the Y value for the same packet. Once both values of the packet are read in this way, the
     * packet is removed from the queue.
     * <p>
     * Note that these input and output instructions never block. Specifically, output instructions do not wait for the
     * sent packet to be received - the computer might send multiple packets before receiving any. Similarly, input
     * instructions do not wait for a packet to arrive - if no packet is waiting, input instructions should receive -1.
     * <p>
     * Boot up all 50 computers and attach them to your network. What is the Y value of the first packet sent to address 255?
     */
    static long runNetworkInterfaceController(String program) throws InterruptedException {
        try (NetworkInterfaceController controller = new NetworkInterfaceController(program)) {
            return controller.runNetworkInterfaceController();
        }
    }

    /**
     * --- Part Two ---
     * Packets sent to address 255 are handled by a device called a NAT (Not Always Transmitting). The NAT is responsible
     * for managing power consumption of the network by blocking certain packets and watching for idle periods in the
     * computers.
     * <p>
     * If a packet would be sent to address 255, the NAT receives it instead. The NAT remembers only the last packet it
     * receives; that is, the data in each packet it receives overwrites the NAT's packet memory with the new packet's
     * X and Y values.
     * <p>
     * The NAT also monitors all computers on the network. If all computers have empty incoming packet queues and are
     * continuously trying to receive packets without sending packets, the network is considered idle.
     * <p>
     * Once the network is idle, the NAT sends only the last packet it received to address 0; this will cause the
     * computers on the network to resume activity. In this way, the NAT can throttle power consumption of the network
     * when the ship needs power in other areas.
     * <p>
     * Monitor packets released to the computer at address 0 by the NAT. What is the first Y value delivered by the NAT
     * to the computer at address 0 twice in a row?
     */
    public static long runNotAlwaysTransmitting(String program) throws InterruptedException {
        try (NetworkInterfaceController controller = new NetworkInterfaceController(program)) {
            return controller.runNotAlwaysTransmitting();
        }
    }

    /// Runs the 50 computers in lockstep rounds: a computer reading an empty inbox parks until the
    /// controller has seen every computer parked, so each round ends in a consistent network state.
    static class NetworkInterfaceController implements AutoCloseable {
        private static final int SIZE = 50;
        private static final long NAT_ADDRESS = 255;

        private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        private final Semaphore parked = new Semaphore(0);
        private final NetworkComputer[] computers = new NetworkComputer[SIZE];

        NetworkInterfaceController(String program) {
            for (int i = 0; i < SIZE; i++) {
                computers[i] = new NetworkComputer(i, parked);
            }
            for (NetworkComputer computer : computers) {
                var _ = executor.submit(() -> Intcode.intcode(program, computer::input, computer::output));
            }
        }

        long runNetworkInterfaceController() throws InterruptedException {
            while (true) {
                for (Packet packet : nextRound()) {
                    if (packet.address() == NAT_ADDRESS) {
                        return packet.y();
                    }
                    deliver(packet);
                }
                resumeAll();
            }
        }

        long runNotAlwaysTransmitting() throws InterruptedException {
            Packet natPacket = null;
            Long previousY = null;
            while (true) {
                var packets = nextRound();
                for (Packet packet : packets) {
                    if (packet.address() == NAT_ADDRESS) {
                        natPacket = packet;
                    } else {
                        deliver(packet);
                    }
                }

                boolean idle = packets.isEmpty() && Arrays.stream(computers).allMatch(NetworkComputer::isInboxEmpty);
                if (idle && natPacket != null) {
                    if (Objects.equals(previousY, natPacket.y())) {
                        return natPacket.y();
                    }
                    previousY = natPacket.y();
                    deliver(new Packet(0, natPacket.x(), natPacket.y()));
                }
                resumeAll();
            }
        }

        /// Waits until every computer is parked on an empty inbox, then collects what they sent.
        private List<Packet> nextRound() throws InterruptedException {
            parked.acquire(SIZE);
            var packets = new ArrayList<Packet>();
            for (NetworkComputer computer : computers) {
                computer.drainOutput(packets);
            }
            return packets;
        }

        private void deliver(Packet packet) {
            if (packet.address() < 0 || packet.address() >= SIZE) {
                throw new IllegalStateException("Unknown address in " + packet);
            }
            computers[(int) packet.address()].receive(packet);
        }

        private void resumeAll() {
            for (NetworkComputer computer : computers) {
                computer.resume();
            }
        }

        @Override
        public void close() {
            executor.shutdownNow();
        }
    }

    static class NetworkComputer {
        private final long address;
        private final Semaphore parked;
        private final Semaphore resume = new Semaphore(0);
        private final Queue<Long> inbox = new ConcurrentLinkedQueue<>();
        private final Queue<Packet> outbox = new ConcurrentLinkedQueue<>();
        private final LongList pendingOutput = new LongArrayList();

        NetworkComputer(long address, Semaphore parked) {
            this.address = address;
            this.parked = parked;
            inbox.add(address);
        }

        void receive(Packet packet) {
            inbox.add(packet.x());
            inbox.add(packet.y());
        }

        boolean isInboxEmpty() {
            return inbox.isEmpty();
        }

        void drainOutput(List<Packet> packets) {
            for (Packet packet = outbox.poll(); packet != null; packet = outbox.poll()) {
                packets.add(packet);
            }
        }

        void resume() {
            resume.release();
        }

        private long input() {
            var head = inbox.poll();
            if (head != null) {
                return head;
            }

            parked.release();
            try {
                resume.acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
            head = inbox.poll();
            return head != null ? head : -1L;
        }

        private void output(long value) {
            pendingOutput.add(value);
            if (pendingOutput.size() == 3) {
                var packet = new Packet(pendingOutput.getLong(0), pendingOutput.getLong(1), pendingOutput.getLong(2));
                LOGGER.debug("Computer {} sent {}", address, packet);
                outbox.add(packet);
                pendingOutput.clear();
            }
        }
    }

    record Packet(long address, long x, long y) {

    }
}
