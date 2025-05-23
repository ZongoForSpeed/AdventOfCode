package com.adventofcode.common.point.map;

import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.utils.TriConsumer;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CharMap {
    private final char defaultValue;
    private char[][] map;

    public CharMap() {
        this(0, 0, ' ');
    }

    public CharMap(int sizeX, int sizeY, char defaultValue) {
        this.map = new char[sizeY][sizeX];
        this.defaultValue = defaultValue;
        for (char[] line : map) {
            Arrays.fill(line, 0, sizeX, defaultValue);
        }
    }

    public CharMap(CharMap o) {
        this.defaultValue = o.defaultValue;
        this.map = Arrays.copyOf(o.map, o.map.length);
        for (int i = 0; i < map.length; i++) {
            map[i] = Arrays.copyOf(o.map[i], o.map[i].length);
        }
    }

    public static CharMap read(String input, Predicate<Character> predicate) {
        return read(new Scanner(input), predicate);
    }

    public static CharMap read(Scanner scanner, Predicate<Character> predicate) {
        return read(scanner, predicate, false);
    }

    public static CharMap read(Scanner scanner, Predicate<Character> predicate, boolean stopWhenBlank) {
        CharMap map = new CharMap();
        int j = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (stopWhenBlank && StringUtils.isBlank(line)) {
                break;
            }
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (predicate.test(c)) {
                    map.set(i, j, c);
                }
            }
            j++;
        }
        return map;
    }

    public char get(int x, int y) {
        if (y < 0 || x < 0) {
            return defaultValue;
        }
        if (y < map.length && x < map[y].length) {
            return map[y][x];
        }
        return defaultValue;
    }

    public char get(Point2D p) {
        return get(p.x(), p.y());
    }

    public void set(int x, int y, char value) {
        reserve(x, y);

        map[y][x] = value;
    }

    public void set(Point2D p, char value) {
        set(p.x(), p.y(), value);
    }

    public void reset(Point2D p) {
        reset(p.x(), p.y());
    }

    public void reset(int x, int y) {
        set(x, y, defaultValue);
    }

    public void reserve(int x, int y) {
        if (y >= map.length) {
            int length = map.length;
            map = Arrays.copyOf(map, y + 1);
            Arrays.fill(map, length, y + 1, new char[0]);
        }
        if (x >= map[y].length) {
            int length = map[y].length;
            map[y] = Arrays.copyOf(map[y], x + 1);
            Arrays.fill(map[y], length, x + 1, defaultValue);
        }
    }

    public void trim() {
        int iMax = -1;
        for (int i = 0; i < map.length; i++) {
            int jMax = -1;
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != defaultValue) {
                    jMax = j;
                }
            }
            if (jMax >= 0) {
                iMax = i;
                map[i] = Arrays.copyOf(map[i], jMax + 1);
            } else {
                map[i] = new char[0];
            }
        }

        if (iMax >= 0) {
            map = Arrays.copyOf(map, iMax + 1);
        }
    }

    public int xMax() {
        int max = 0;
        for (char[] line : map) {
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
            char[] line = map[y];
            for (char c : line) {
                if (c != defaultValue) {
                    max = Math.max(y, max);
                }
            }
        }
        return max;
    }

    public void foreach(TriConsumer<Integer, Integer, Character> consumer) {
        for (int y = 0; y < map.length; y++) {
            char[] line = map[y];
            for (int x = 0; x < line.length; x++) {
                if (line[x] != defaultValue) {
                    consumer.accept(x, y, line[x]);
                }
            }
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(map).map(String::valueOf).collect(Collectors.joining("\n"));
    }

    public List<String> values() {
        return Arrays.stream(map).map(String::valueOf).toList();
    }

    public List<Point2D> points() {
        List<Point2D> points = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            char[] line = map[y];
            for (int x = 0; x < line.length; x++) {
                if (line[x] != defaultValue) {
                    points.add(Point2D.of(x, y));
                }
            }
        }
        return points;
    }

    public List<ObjectCharPair<Point2D>> entries() {
        List<ObjectCharPair<Point2D>> entries = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            char[] line = map[y];
            for (int x = 0; x < line.length; x++) {
                if (line[x] != defaultValue) {
                    entries.add(ObjectCharPair.of(Point2D.of(x, y), line[x]));
                }
            }
        }
        return entries;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CharMap charMap)) return false;
        return Objects.deepEquals(map, charMap.map);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(map);
    }

    public CharMap subMap(int xMin, int xMax, int yMin, int yMax) {
        CharMap subMap = new CharMap(xMax - xMin - 1, yMax - yMin - 1, defaultValue);
        for (int x = xMin; x < xMax; ++x) {
            for (int y = yMin; y < yMax; ++y) {
                subMap.set(x - xMin, y - yMin, get(x, y));
            }
        }
        return subMap;
    }

    public void insertLine(int line, char c) {
        int length = map.length;
        map = Arrays.copyOf(map, length + 1);
        System.arraycopy(map, line,
                map, line + 1,
                length - line);

        int lineLength = map[line].length;
        map[line] = new char[lineLength];
        Arrays.fill(map[line], c);
    }

    public void insertColumn(int column, char c) {
        for (int i = 0; i < map.length; i++) {
            int length = map[i].length;
            map[i] = Arrays.copyOf(map[i], length + 1);
            System.arraycopy(map[i], column,
                    map[i], column + 1,
                    length - column);
            map[i][column] = c;
        }
    }

}
