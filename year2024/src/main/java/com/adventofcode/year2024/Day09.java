package com.adventofcode.year2024;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Day09 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day09.class);

    private static long hash(IntList memory) {
        long hash = 0;
        for (int i = 0; i < memory.size(); i++) {
            int id = memory.getInt(i);
            if (id >= 0) {
                hash += (long) id * i;
            }
        }
        return hash;
    }

    private static IntList readInput(String input) {
        IntList memory = new IntArrayList();
        int id = 0;

        for (int i = 0; i < input.length(); ++i) {
            int c = input.charAt(i) - '0';
            if (i % 2 == 1) {
                memory.addAll(Collections.nCopies(c, -1));
            } else {
                memory.addAll(Collections.nCopies(c, id));
                ++id;
            }
        }

        LOGGER.info("memory = {}", memory);
        return memory;
    }

    /**
     * --- Day 9: Disk Fragmenter ---
     * <p>
     * Another push of the button leaves you in the familiar hallways of some
     * friendly amphipods! Good thing you each somehow got your own personal mini
     * submarine. The Historians jet away in search of the Chief, mostly by
     * driving directly into walls.
     * <p>
     * While The Historians quickly figure out how to pilot these things, you
     * notice an amphipod in the corner struggling with his computer. He's trying
     * to make more contiguous free space by compacting all of the files, but his
     * program isn't working; you offer to help.
     * <p>
     * He shows you the disk map (your puzzle input) he's already generated. For
     * example:
     * <p>
     * 2333133121414131402
     * <p>
     * The disk map uses a dense format to represent the layout of files and free
     * space on the disk. The digits alternate between indicating the length of a
     * file and the length of free space.
     * <p>
     * So, a disk map like 12345 would represent a one-block file, two blocks of
     * free space, a three-block file, four blocks of free space, and then a five-
     * block file. A disk map like 90909 would represent three nine-block files in
     * a row (with no free space between them).
     * <p>
     * Each file on disk also has an ID number based on the order of the files as
     * they appear before they are rearranged, starting with ID 0. So, the disk
     * map 12345 has three files: a one-block file with ID 0, a three-block file
     * with ID 1, and a five-block file with ID 2. Using one character for each
     * block where digits are the file ID and . is free space, the disk map 12345
     * represents these individual blocks:
     * <p>
     * 0..111....22222
     * <p>
     * The first example above, 2333133121414131402, represents these individual
     * blocks:
     * <p>
     * 00...111...2...333.44.5555.6666.777.888899
     * <p>
     * The amphipod would like to move file blocks one at a time from the end of
     * the disk to the leftmost free space block (until there are no gaps
     * remaining between file blocks). For the disk map 12345, the process looks
     * like this:
     * <p>
     * 0..111....22222
     * 02.111....2222.
     * 022111....222..
     * 0221112...22...
     * 02211122..2....
     * 022111222......
     * <p>
     * The first example requires a few more steps:
     * <p>
     * 00...111...2...333.44.5555.6666.777.888899
     * 009..111...2...333.44.5555.6666.777.88889.
     * 0099.111...2...333.44.5555.6666.777.8888..
     * 00998111...2...333.44.5555.6666.777.888...
     * 009981118..2...333.44.5555.6666.777.88....
     * 0099811188.2...333.44.5555.6666.777.8.....
     * 009981118882...333.44.5555.6666.777.......
     * 0099811188827..333.44.5555.6666.77........
     * 00998111888277.333.44.5555.6666.7.........
     * 009981118882777333.44.5555.6666...........
     * 009981118882777333644.5555.666............
     * 00998111888277733364465555.66.............
     * 0099811188827773336446555566..............
     * <p>
     * The final step of this file-compacting process is to update the filesystem
     * checksum. To calculate the checksum, add up the result of multiplying each
     * of these blocks' position with the file ID number it contains. The leftmost
     * block is in position 0. If a block contains free space, skip it instead.
     * <p>
     * Continuing the first example, the first few blocks' position multiplied by
     * its file ID number are 0 * 0 = 0, 1 * 0 = 0, 2 * 9 = 18, 3 * 9 = 27,
     * 4 * 8 = 32, and so on. In this example, the checksum is the sum of these,
     * 1928.
     * <p>
     * Compact the amphipod's hard drive using the process he requested. What is
     * the resulting filesystem checksum? (Be careful copy/pasting the input for
     * this puzzle; it is a single, very long line.)
     */
    public static long partOne(String input) {
        IntList memory = readInput(input);

        int realMemory = 0;
        IntPriorityQueue freeSpaces = new IntArrayFIFOQueue();
        for (int i = 0; i < memory.size(); i++) {
            if (memory.getInt(i) < 0) {
                freeSpaces.enqueue(i);
            } else {
                ++realMemory;
            }
        }

        LOGGER.info("freeSpace = {}", freeSpaces);

        while (memory.size() > realMemory) {
            int file = memory.removeInt(memory.size() - 1);
            if (file >= 0) {
                memory.set(freeSpaces.dequeueInt(), file);
                LOGGER.trace("memory = {}", memory);
            }
        }

        LOGGER.info("memory = {}", memory);

        return hash(memory);
    }

    /**
     * --- Part Two ---
     * <p>
     * Upon completion, two things immediately become clear. First, the disk
     * definitely has a lot more contiguous free space, just like the amphipod
     * hoped. Second, the computer is running much more slowly! Maybe introducing
     * all of that file system fragmentation was a bad idea?
     * <p>
     * The eager amphipod already has a new plan: rather than move individual
     * blocks, he'd like to try compacting the files on his disk by moving whole
     * files instead.
     * <p>
     * This time, attempt to move whole files to the leftmost span of free space
     * blocks that could fit the file. Attempt to move each file exactly once in
     * order of decreasing file ID number starting with the file with the highest
     * file ID number. If there is no span of free space to the left of a file
     * that is large enough to fit the file, the file does not move.
     * <p>
     * The first example from above now proceeds differently:
     * <p>
     * 00...111...2...333.44.5555.6666.777.888899
     * 0099.111...2...333.44.5555.6666.777.8888..
     * 0099.1117772...333.44.5555.6666.....8888..
     * 0099.111777244.333....5555.6666.....8888..
     * 00992111777.44.333....5555.6666.....8888..
     * <p>
     * The process of updating the filesystem checksum is the same; now, this
     * example's checksum would be 2858.
     * <p>
     * Start over, now compacting the amphipod's hard drive using this new method
     * instead. What is the resulting filesystem checksum?
     */
    public static long partTwo(String input) {
        List<Block> blocks = new ArrayList<>();
        int id = 0;

        int address = 0;
        for (int i = 0; i < input.length(); ++i) {
            int c = input.charAt(i) - '0';
            if (i % 2 == 1) {
                blocks.add(new Block(-1, c, address, address + c));
            } else {
                blocks.add(new Block(id, c, address, address + c));
                ++id;
            }
            address += c;
        }

        LOGGER.info("blocks = {}", blocks);

        List<Block> freeBlocks = new ArrayList<>();
        for (Block block : blocks) {
            if (block.id() < 0) {
                freeBlocks.add(block);
            }
        }

        IntList memory = new IntArrayList(Collections.nCopies(address, -1));
        for (int i = blocks.size() - 1; i >= 0; i--) {
            Block block = blocks.get(i);
            if (block.id() >= 0) {
                boolean found = false;
                for (int j = 0; j < freeBlocks.size(); j++) {
                    Block freeBlock = freeBlocks.get(j);
                    if (freeBlock.start() > block.start()) {
                        break;
                    }
                    if (freeBlock.size() >= block.size()) {
                        found = true;
                        for (int k = 0; k < block.size(); ++k) {
                            memory.set(freeBlock.start() + k, block.id());
                        }
                        freeBlocks.set(j, new Block(-1, freeBlock.size() - block.size(), freeBlock.start() + block.size(), freeBlock.end()));
                        break;
                    }
                }
                if (!found) {
                    for (int k = 0; k < block.size(); ++k) {
                        memory.set(block.start() + k, block.id());
                    }
                }
            }

            LOGGER.trace("memory = {}", memory);
        }

        LOGGER.info("memory = {}", memory);
        return hash(memory);
    }

    record Block(int id, int size, int start, int end) {
    }
}
