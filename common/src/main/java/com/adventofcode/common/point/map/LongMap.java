package com.adventofcode.common.point.map;

import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.utils.TriConsumer;
import it.unimi.dsi.fastutil.objects.ObjectLongPair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;

public class LongMap {
    private final long defaultValue;
    private long[][] map;

    public LongMap() {
        this(0, 0, 0);
    }

    public LongMap(int sizeX, int sizeY, long defaultValue) {
        this.map = new long[sizeY][sizeX];
        this.defaultValue = defaultValue;
        for (long[] line : map) {
            Arrays.fill(line, 0, sizeX, defaultValue);
        }
    }

    public LongMap(LongMap m) {
        defaultValue = m.defaultValue;
        map = Arrays.copyOf(m.map, m.map.length);
        for (int i = 0; i < map.length; i++) {
            map[i] = Arrays.copyOf(m.map[i], m.map[i].length);
        }
    }

    public static LongMap read(Scanner scanner) {
        LongMap map = new LongMap(0, 0, -1);
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

    public long get(int x, int y) {
        if (y < map.length && x < map[y].length) {
            return map[y][x];
        }
        return defaultValue;
    }

    public long get(Point2D p) {
        return get(p.x(), p.y());
    }

    public void set(Point2D p, long value) {
        set(p.x(), p.y(), value);
    }

    public void set(int x, int y, long value) {
        ensure(x, y);

        map[y][x] = value;
    }

    public long increment(Point2D p, int value) {
        return increment(p.x(), p.y(), value);
    }

    public long increment(int x, int y, int value) {
        ensure(x, y);

        map[y][x] += value;
        return map[y][x];
    }

    private void ensure(int x, int y) {
        if (y >= map.length) {
            int length = map.length;
            map = Arrays.copyOf(map, y + 1);
            Arrays.fill(map, length, y + 1, new long[0]);
        }
        if (x >= map[y].length) {
            int length = map[y].length;
            map[y] = Arrays.copyOf(map[y], x + 1);
            Arrays.fill(map[y], length, x + 1, defaultValue);
        }
    }

    public long compute(Point2D p, LongUnaryOperator operation) {
        return compute(p.x(), p.y(), operation);
    }

    public long compute(int x, int y, LongUnaryOperator operation) {
        ensure(x, y);

        map[y][x] = operation.applyAsLong(map[y][x]);
        return map[y][x];
    }

    @Override
    public String toString() {
        return Arrays.stream(map)
                .map(Arrays::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void clear() {
        this.map = new long[0][0];
    }

    public List<Point2D> points() {
        List<Point2D> points = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            long[] line = map[y];
            for (int x = 0; x < line.length; x++) {
                if (line[x] != defaultValue) {
                    points.add(Point2D.of(x, y));
                }
            }
        }
        return points;
    }

    public List<ObjectLongPair<Point2D>> entries() {
        List<ObjectLongPair<Point2D>> entries = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            long[] line = map[y];
            for (int x = 0; x < line.length; x++) {
                if (line[x] != defaultValue) {
                    entries.add(ObjectLongPair.of(Point2D.of(x, y), line[x]));
                }
            }
        }
        return entries;
    }

    public int xMax() {
        int max = 0;
        for (long[] line : map) {
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
            long[] line = map[y];
            for (long c : line) {
                if (c != defaultValue) {
                    max = Math.max(y, max);
                }
            }
        }
        return max;
    }

    public void foreach(TriConsumer<Integer, Integer, Long> consumer) {
        for (int y = 0; y < map.length; y++) {
            long[] line = map[y];
            for (int x = 0; x < line.length; x++) {
                if (line[x] != defaultValue) {
                    consumer.accept(x, y, line[x]);
                }
            }
        }
    }

    public LongMap subMap(int xMin, int xMax, int yMin, int yMax) {
        LongMap subMap = new LongMap(xMax - xMin - 1, yMax - yMin - 1, defaultValue);
        for (int x = xMin; x < xMax; ++x) {
            for (int y = yMin; y < yMax; ++y) {
                subMap.set(x - xMin, y - yMin, get(x, y));
            }
        }
        return subMap;
    }

}
