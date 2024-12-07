package com.adventofcode.common.point.map;

import com.adventofcode.common.point.Point2D;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BooleanMap {
    private final List<BitSet> bitSets;
    private final char separator;
    int sizeX;
    int sizeY;

    public BooleanMap() {
        this(0, 0, '\n');
    }

    public BooleanMap(int sizeX, int sizeY) {
        this(sizeX, sizeY, '\n');
    }

    public BooleanMap(int sizeX, int sizeY, char separator) {
        this.separator = separator;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.bitSets = new ArrayList<>();
        IntStream.range(0, sizeY).forEach(i -> bitSets.add(new BitSet(sizeX)));
    }

    public static BooleanMap read(String input, Predicate<Character> predicate) {
        return read(new Scanner(input), predicate);
    }

    public static BooleanMap read(Scanner scanner, Predicate<Character> predicate) {
        return read(scanner, predicate, false);
    }

    public static BooleanMap read(Scanner scanner, Predicate<Character> predicate, boolean stopWhenBlank) {
        BooleanMap map = new BooleanMap();
        int j = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (stopWhenBlank && StringUtils.isBlank(line)) {
                break;
            }
            char[] charArray = line.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if (predicate.test(c)) {
                    map.set(i, j);
                } else {
                    map.reset(i, j);
                }
            }
            j++;
        }
        return map;
    }

    public boolean get(int x, int y) {
        if (y < bitSets.size()) {
            return bitSets.get(y).get(x);
        }
        return false;
    }

    public boolean get(Point2D p) {
        return get(p.x(), p.y());
    }

    public void set(int x, int y) {
        reserve(y);

        bitSets.get(y).set(x);
        sizeX = Math.max(x, sizeX);
        sizeY = Math.max(y, sizeY);
    }

    public void set(Point2D p) {
        set(p.x(), p.y());
    }

    public void set(int x, int y, boolean value) {
        if (value) {
            set(x, y);
        } else {
            reset(x, y);
        }
    }

    public void set(Point2D p, boolean value) {
        set(p.x(), p.y(), value);
    }

    public void reset(Point2D p) {
        reset(p.x(), p.y());
    }

    public void reset(int x, int y) {
        if (y < bitSets.size()) {
            bitSets.get(y).clear(x);
        }
        sizeX = Math.max(x, sizeX);
        sizeY = Math.max(y, sizeY);
    }

    public int xMax() {
        return sizeX;
    }

    public int yMax() {
        return sizeY;
    }

    public void reserve(int y) {
        while (bitSets.size() <= y) {
            bitSets.add(new BitSet());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y <= sizeY; ++y) {
            for (int x = 0; x <= sizeX; ++x) {
                if (get(x, y)) {
                    sb.append('#');
                } else {
                    sb.append('.');
                }
            }
            sb.append(separator);
        }
        return sb.toString();
    }

    public Stream<Point2D> points() {
        return IntStream.range(0, bitSets.size()).boxed().flatMap(y -> bitSets.get(y).stream().mapToObj(x -> Point2D.of(x, y)));
    }

    public long cardinality() {
        return bitSets.stream().mapToLong(BitSet::cardinality).sum();
    }

    public void trim() {
        int lastLine = 0;
        for (int i = 0; i < bitSets.size(); i++) {
            BitSet bitSet = bitSets.get(i);
            if (bitSet.cardinality() > 0) {
                lastLine = i;
            }
        }

        while (bitSets.size() > lastLine + 1) {
            bitSets.removeLast();
        }

        sizeY = lastLine + 1;
        sizeX = bitSets.stream().flatMapToInt(BitSet::stream).max().orElseThrow() + 1;
    }

    public BooleanMap subMap(int xMin, int xMax, int yMin, int yMax) {
        BooleanMap subMap = new BooleanMap(xMax - xMin - 1, yMax - yMin - 1, separator);
        for (int x = xMin; x < xMax; ++x) {
            for (int y = yMin; y < yMax; ++y) {
                subMap.set(x - xMin, y - yMin, get(x, y));
            }
        }
        return subMap;
    }

    public BooleanMap rotate() {
        BooleanMap rotate = new BooleanMap(sizeY, sizeX, separator);
        for (int y = 0; y <= sizeY; ++y) {
            for (int x = 0; x <= sizeX; ++x) {
                if (get(x, y)) {
                    rotate.set(sizeY - y, x);
                }
            }
        }
        return rotate;
    }

    public BooleanMap flipX() {
        BooleanMap rotate = new BooleanMap(sizeX, sizeY, separator);
        for (int y = 0; y <= sizeY; ++y) {
            for (int x = 0; x <= sizeX; ++x) {
                if (get(x, y)) {
                    rotate.set(x, sizeY - y);
                }
            }
        }
        return rotate;
    }

    public BooleanMap flipY() {
        BooleanMap rotate = new BooleanMap(sizeX, sizeY, separator);
        for (int y = 0; y <= sizeY; ++y) {
            for (int x = 0; x <= sizeX; ++x) {
                if (get(x, y)) {
                    rotate.set(sizeX - x, y);
                }
            }
        }
        return rotate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BooleanMap that)) return false;
        return Objects.equals(bitSets, that.bitSets);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bitSets);
    }
}
