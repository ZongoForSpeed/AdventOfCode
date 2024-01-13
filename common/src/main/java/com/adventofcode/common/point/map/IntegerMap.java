package com.adventofcode.point.map;

import com.adventofcode.point.Point2D;
import com.adventofcode.utils.TriConsumer;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

public class IntegerMap {
    private final int defaultValue;
    private int[][] map;

    public IntegerMap() {
        this(0, 0, 0);
    }

    public IntegerMap(int sizeX, int sizeY, int defaultValue) {
        this.map = new int[sizeY][sizeX];
        this.defaultValue = defaultValue;
        for (int[] line : map) {
            Arrays.fill(line, 0, sizeX, defaultValue);
        }
    }

    public IntegerMap(IntegerMap m) {
        defaultValue = m.defaultValue;
        map = Arrays.copyOf(m.map, m.map.length);
        for (int i = 0; i < map.length; i++) {
            map[i] = Arrays.copyOf(m.map[i], m.map[i].length);
        }
    }

    public static IntegerMap read(Scanner scanner) {
        IntegerMap map = new IntegerMap(0, 0, -1);
        int j = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            char[] charArray = line.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                map.set(i, j, charArray[i] - '0');
            }
            ++j;
        }
        return map;
    }

    public int get(int x, int y) {
        if (y < map.length && x < map[y].length) {
            return map[y][x];
        }
        return defaultValue;
    }

    public int get(Point2D p) {
        return get(p.x(), p.y());
    }

    public void set(Point2D p, int value) {
        set(p.x(), p.y(), value);
    }

    public void set(int x, int y, int value) {
        ensure(x, y);

        map[y][x] = value;
    }

    public int increment(Point2D p, int value) {
        return increment(p.x(), p.y(), value);
    }

    public int increment(int x, int y, int value) {
        ensure(x, y);

        map[y][x] += value;
        return map[y][x];
    }

    private void ensure(int x, int y) {
        if (y >= map.length) {
            int length = map.length;
            map = Arrays.copyOf(map, y + 1);
            Arrays.fill(map, length, y + 1, new int[0]);
        }
        if (x >= map[y].length) {
            int length = map[y].length;
            map[y] = Arrays.copyOf(map[y], x + 1);
            Arrays.fill(map[y], length, x + 1, defaultValue);
        }
    }

    public int compute(Point2D p, IntUnaryOperator operation) {
        return compute(p.x(), p.y(), operation);
    }

    public int compute(int x, int y, IntUnaryOperator operation) {
        ensure(x, y);

        map[y][x] = operation.applyAsInt(map[y][x]);
        return map[y][x];
    }

    @Override
    public String toString() {
        return Arrays.stream(map)
                .map(Arrays::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void clear() {
        this.map = new int[0][0];
    }

    public List<Point2D> points() {
        List<Point2D> points = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            int[] line = map[y];
            for (int x = 0; x < line.length; x++) {
                if (line[x] != defaultValue) {
                    points.add(Point2D.of(x, y));
                }
            }
        }
        return points;
    }

    public List<ObjectIntPair<Point2D>> entries() {
        List<ObjectIntPair<Point2D>> entries = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            int[] line = map[y];
            for (int x = 0; x < line.length; x++) {
                if (line[x] != defaultValue) {
                    entries.add(ObjectIntPair.of(Point2D.of(x, y), line[x]));
                }
            }
        }
        return entries;
    }

    public int xMax() {
        int max = 0;
        for (int[] line : map) {
            for (int x = 0; x < line.length; x++) {
                if (line[x] != defaultValue) {
                    max = Math.max(x, max);
                }
            }
        }
        return max;
    }

    public int yMax() {
        int max = 0;
        for (int y = 0; y < map.length; y++) {
            int[] line = map[y];
            for (int c : line) {
                if (c != defaultValue) {
                    max = Math.max(y, max);
                }
            }
        }
        return max;
    }

    public void foreach(TriConsumer<Integer, Integer, Integer> consumer) {
        for (int y = 0; y < map.length; y++) {
            int[] line = map[y];
            for (int x = 0; x < line.length; x++) {
                if (line[x] != defaultValue) {
                    consumer.accept(x, y, line[x]);
                }
            }
        }
    }

    public IntegerMap subMap(int xMin, int xMax, int yMin, int yMax) {
        IntegerMap subMap = new IntegerMap(xMax - xMin - 1, yMax - yMin - 1, defaultValue);
        for (int x = xMin; x < xMax; ++x) {
            for (int y = yMin; y < yMax; ++y) {
                subMap.set(x - xMin, y - yMin, get(x, y));
            }
        }
        return subMap;
    }

}
