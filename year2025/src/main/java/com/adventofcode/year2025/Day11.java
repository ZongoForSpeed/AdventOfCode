package com.adventofcode.year2025;

import java.util.*;

public final class Day11 {
    /**
     * --- Day 11: Reactor ---
     * <p>
     * You hear some loud beeping coming from a hatch in the floor of the factory,
     * so you decide to check it out. Inside, you find several large electrical
     * conduits and a ladder.
     * <p>
     * Climbing down the ladder, you discover the source of the beeping: a large,
     * toroidal reactor which powers the factory above. Some Elves here are
     * hurriedly running between the reactor and a nearby server rack, apparently
     * trying to fix something.
     * <p>
     * One of the Elves notices you and rushes over. "It's a good thing you're
     * here! We just installed a new server rack, but we aren't having any luck
     * getting the reactor to communicate with it!" You glance around the room and
     * see a tangle of cables and devices running from the server rack to the
     * reactor. She rushes off, returning a moment later with a list of the
     * devices and their outputs (your puzzle input).
     * <p>
     * For example:
     * <p>
     * aaa: you hhh
     * you: bbb ccc
     * bbb: ddd eee
     * ccc: ddd eee fff
     * ddd: ggg
     * eee: out
     * fff: out
     * ggg: out
     * hhh: ccc fff iii
     * iii: out
     * <p>
     * Each line gives the name of a device followed by a list of the devices to
     * which its outputs are attached. So, bbb: ddd eee means that device bbb has
     * two outputs, one leading to device ddd and the other leading to device eee.
     * <p>
     * The Elves are pretty sure that the issue isn't due to any specific device,
     * but rather that the issue is triggered by data following some specific path
     * through the devices. Data only ever flows from a device through its
     * outputs; it can't flow backwards.
     * <p>
     * After dividing up the work, the Elves would like you to focus on the
     * devices starting with the one next to you (an Elf hastily attaches a label
     * which just says you) and ending with the main output to the reactor (which
     * is the device with the label out).
     * <p>
     * To help the Elves figure out which path is causing the issue, they need you
     * to find every path from you to out.
     * <p>
     * In this example, these are all of the paths from you to out:
     * <p>
     * Data could take the connection from you to bbb, then from bbb to ddd,
     * then from ddd to ggg, then from ggg to out.
     * Data could take the connection to bbb, then to eee, then to out.
     * Data could go to ccc, then ddd, then ggg, then out.
     * Data could go to ccc, then eee, then out.
     * Data could go to ccc, then fff, then out.
     * <p>
     * In total, there are 5 different paths leading from you to out.
     * <p>
     * How many different paths lead from you to out?
     * <p>
     * --- Part Two ---
     * <p>
     * Thanks in part to your analysis, the Elves have figured out a little bit
     * about the issue. They now know that the problematic data path passes
     * through both dac (a digital-to-analog converter) and fft (a device which
     * performs a fast Fourier transform).
     * <p>
     * They're still not sure which specific path is the problem, and so they now
     * need you to find every path from svr (the server rack) to out. However, the
     * paths you find must all also visit both dac and fft (in any order).
     * <p>
     * For example:
     * <p>
     * svr: aaa bbb
     * aaa: fft
     * fft: ccc
     * bbb: tty
     * tty: ccc
     * ccc: ddd eee
     * ddd: hub
     * hub: fff
     * eee: dac
     * dac: fff
     * fff: ggg hhh
     * ggg: out
     * hhh: out
     * <p>
     * This new list of devices contains many paths from svr to out:
     * <p>
     * svr,aaa,fft,ccc,ddd,hub,fff,ggg,out
     * svr,aaa,fft,ccc,ddd,hub,fff,hhh,out
     * svr,aaa,fft,ccc,eee,dac,fff,ggg,out
     * svr,aaa,fft,ccc,eee,dac,fff,hhh,out
     * svr,bbb,tty,ccc,ddd,hub,fff,ggg,out
     * svr,bbb,tty,ccc,ddd,hub,fff,hhh,out
     * svr,bbb,tty,ccc,eee,dac,fff,ggg,out
     * svr,bbb,tty,ccc,eee,dac,fff,hhh,out
     * <p>
     * However, only 2 paths from svr to out visit both dac and fft.
     * <p>
     * Find all of the paths that lead from svr to out. How many of those paths
     * visit both dac and fft?
     */
    private Day11() {
    }

    static long reactorPartOne(Scanner scanner) {
        Map<String, List<String>> nextServers = readInput(scanner);
        return findPaths(nextServers, "you", "out");
    }

    static long reactorPartTwo(Scanner scanner) {
        Map<String, List<String>> nextServers = readInput(scanner);

        long dacToFft = findPaths(nextServers, "dac", "fft");
        long fftToDac = findPaths(nextServers, "fft", "dac");

        if (dacToFft > 0) {
            return findPaths(nextServers, "svr", "dac")
                    * dacToFft
                    * findPaths(nextServers, "fft", "out");
        } else {
            return findPaths(nextServers, "svr", "fft")
                    * fftToDac
                    * findPaths(nextServers, "dac", "out");
        }
    }

    private static Map<String, List<String>> readInput(Scanner scanner) {
        Map<String, List<String>> nextServers = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                continue;
            }
            String[] splitted = line.trim().split("\\s+", -1);
            if (splitted.length == 0) {
                continue;
            }
            String server = splitted[0].substring(0, splitted[0].length() - 1);
            List<String> outputs = new ArrayList<>(Arrays.asList(splitted).subList(1, splitted.length));
            nextServers.put(server, outputs);
        }
        return nextServers;
    }

    private static long findPaths(Map<String, List<String>> graph, String start, String end) {
        Map<String, Long> cache = new HashMap<>();
        cache.put(end, 1L);
        return visit(cache, graph, start);
    }

    private static long visit(Map<String, Long> cache, Map<String, List<String>> graph, String serverName) {
        if (cache.containsKey(serverName)) {
            return cache.get(serverName);
        }
        if ("out".equals(serverName)) {
            return 0;
        }

        List<String> neighbors = graph.get(serverName);
        if (neighbors == null) {
            return 0;
        }

        long res = 0;
        for (String nextServer : neighbors) {
            res += visit(cache, graph, nextServer);
        }

        cache.put(serverName, res);
        return res;
    }
}
