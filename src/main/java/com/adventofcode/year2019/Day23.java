package com.adventofcode.year2019;

import com.adventofcode.Intcode;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Day23 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23.class);

    /**
     * --- Day 23: Category Six ---
     * The droids have finished repairing as much of the ship as they can. Their report indicates that this was a
     * Category 6 disaster - not because it was that bad, but because it destroyed the stockpile of Category 6 network
     * cables as well as most of the ship's network infrastructure.
     *
     * You'll need to rebuild the network from scratch.
     *
     * The computers on the network are standard Intcode computers that communicate by sending packets to each other.
     * There are 50 of them in total, each running a copy of the same Network Interface Controller (NIC) software
     * (your puzzle input). The computers have network addresses 0 through 49; when each computer boots up, it will
     * request its network address via a single input instruction. Be sure to give each computer a unique network address.
     *
     * Once a computer has received its network address, it will begin doing work and communicating over the network by
     * sending and receiving packets. All packets contain two values named X and Y. Packets sent to a computer are queued
     * by the recipient and read in the order they are received.
     *
     * To send a packet to another computer, the NIC will use three output instructions that provide the destination
     * address of the packet followed by its X and Y values. For example, three output instructions that provide the
     * values 10, 20, 30 would send a packet with X=20 and Y=30 to the computer with address 10.
     *
     * To receive a packet from another computer, the NIC will use an input instruction. If the incoming packet queue is
     * empty, provide -1. Otherwise, provide the X value of the next packet; the computer will then use a second input
     * instruction to receive the Y value for the same packet. Once both values of the packet are read in this way, the
     * packet is removed from the queue.
     *
     * Note that these input and output instructions never block. Specifically, output instructions do not wait for the
     * sent packet to be received - the computer might send multiple packets before receiving any. Similarly, input
     * instructions do not wait for a packet to arrive - if no packet is waiting, input instructions should receive -1.
     *
     * Boot up all 50 computers and attach them to your network. What is the Y value of the first packet sent to address 255?
     */
    static long runNetworkInterfaceController(String program) throws InterruptedException {
        try (NetworkInterfaceController controller = new NetworkInterfaceController(program)) {
            controller.start();
            return controller.runNetworkInterfaceController();
        }
    }

    /**
     * --- Part Two ---
     * Packets sent to address 255 are handled by a device called a NAT (Not Always Transmitting). The NAT is responsible
     * for managing power consumption of the network by blocking certain packets and watching for idle periods in the
     * computers.
     *
     * If a packet would be sent to address 255, the NAT receives it instead. The NAT remembers only the last packet it
     * receives; that is, the data in each packet it receives overwrites the NAT's packet memory with the new packet's
     * X and Y values.
     *
     * The NAT also monitors all computers on the network. If all computers have empty incoming packet queues and are
     * continuously trying to receive packets without sending packets, the network is considered idle.
     *
     * Once the network is idle, the NAT sends only the last packet it received to address 0; this will cause the
     * computers on the network to resume activity. In this way, the NAT can throttle power consumption of the network
     * when the ship needs power in other areas.
     *
     * Monitor packets released to the computer at address 0 by the NAT. What is the first Y value delivered by the NAT
     * to the computer at address 0 twice in a row?
     */
    public static long runNotAlwaysTransmitting(String program) throws InterruptedException {
        try (NetworkInterfaceController controller = new NetworkInterfaceController(program)) {
            controller.start();
            return controller.runNotAlwaysTransmitting();
        }
    }

    static class NetworkInterfaceController implements AutoCloseable {
        private final NetworkComputer[] computers = new NetworkComputer[50];

        public NetworkInterfaceController(String program) {
            for (int i = 0; i < 50; i++) {
                computers[i] = new NetworkComputer(program, i);
            }
        }

        public void start() {
            for (NetworkComputer computer : computers) {
                computer.start();
            }
        }

        public long runNetworkInterfaceController() throws InterruptedException {
            while (true) {
                List<Packet> packets = new ArrayList<>();
                for (NetworkComputer computer : computers) {
                    computer.outputQueue().drainTo(packets);
                }

                for (Packet packet : packets) {
                    if (packet.address() == 255) {
                        return packet.y();
                    } else if (packet.address() < 50) {
                        computers[(int) packet.address()].inputQueue().put(packet.x());
                        computers[(int) packet.address()].inputQueue().put(packet.y());
                    }
                }

                for (NetworkComputer computer : computers) {
                    if (computer.inputQueue().isEmpty()) {
                        computer.inputQueue().put(-1L);
                    }
                    computer.run();
                }
            }
        }

        public long runNotAlwaysTransmitting() throws InterruptedException {
            Packet natPacket = null;
            Long prevY = null;
            while (true) {
                boolean idle = Arrays.stream(computers).filter(c -> c.inputQueue().isEmpty() && c.outputQueue().isEmpty()).count() == 50;
                if (idle && natPacket != null) {
                    computers[0].inputQueue().put(natPacket.x());
                    computers[0].inputQueue().put(natPacket.y());
                    if (Objects.equals(prevY, natPacket.y())) {
                        return prevY;
                    }

                    prevY = natPacket.y();
                }

                List<Packet> packets = new ArrayList<>();
                for (NetworkComputer computer : computers) {
                    computer.outputQueue().drainTo(packets);
                }

                for (Packet packet : packets) {
                    if (packet.address() == 255) {
                        natPacket = packet;
                    } else if (packet.address() < 50) {
                        computers[(int) packet.address()].inputQueue().put(packet.x());
                        computers[(int) packet.address()].inputQueue().put(packet.y());
                    }
                }

                for (NetworkComputer computer : computers) {
                    if (computer.inputQueue().isEmpty()) {
                        computer.inputQueue().put(-1L);
                    }
                    computer.run();
                }
            }
        }

        @Override
        public void close() {
            for (NetworkComputer computer : computers) {
                if (computer != null) {
                    computer.close();
                }
            }
        }
    }

    static class NetworkComputer implements AutoCloseable {
        private final ExecutorService executor;
        private final long address;
        private final BlockingQueue<Long> queue;
        private final LongList receivedPackets;
        private final BlockingQueue<Packet> outputQueue = new LinkedBlockingQueue<>();

        NetworkComputer(String program, long address) {
            this.address = address;
            this.executor = Executors.newSingleThreadExecutor();
            this.queue = new LinkedBlockingQueue<>();
            this.receivedPackets = new LongArrayList();
            executor.submit(() -> {
                Intcode.intcode(program, this::input, this::output);
            });
        }

        public BlockingQueue<Long> inputQueue() {
            return queue;
        }

        public BlockingQueue<Packet> outputQueue() {
            return outputQueue;
        }

        public void start() {
            synchronized (queue) {
                queue.add(address);
            }
        }

        public void run() {
            synchronized (queue) {
                queue.notifyAll();
            }
        }

        private long input() {
            synchronized (queue) {
                while (true) {
                    Long head = queue.poll();
                    if (head != null) {
                        return head;
                    }

                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }

        private void output(long l) {
            receivedPackets.add(l);
            if (receivedPackets.size() == 3) {
                Packet packet = new Packet(receivedPackets.getLong(0), receivedPackets.getLong(1), receivedPackets.getLong(2));

                LOGGER.info("Computer {} received {}", address, packet);
                outputQueue.add(packet);
                receivedPackets.clear();
            }
        }

        @Override
        public void close() {
            executor.shutdown();
        }
    }

    record Packet(long address, long x, long y) {
    }
}
