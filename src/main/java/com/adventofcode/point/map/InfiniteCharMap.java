package com.adventofcode.point.map;

import com.adventofcode.point.Point2D;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class InfiniteCharMap implements Map<Point2D, Character> {

    private final Map<Point2D, Character> map;

    public InfiniteCharMap() {
        this.map = new HashMap<>();
    }

    public static InfiniteCharMap read(String input, Predicate<Character> predicate) {
        return read(new Scanner(input), predicate);
    }

    public static InfiniteCharMap read(Scanner scanner, Predicate<Character> predicate) {
        return read(scanner, predicate, false);
    }

    public static InfiniteCharMap read(Scanner scanner, Predicate<Character> predicate, boolean stopWhenBlank) {
        InfiniteCharMap map = new InfiniteCharMap();
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
                    map.put(Point2D.of(i, j), c);
                }
            }
            j++;
        }
        return map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Character get(Object key) {
        return map.get(key);
    }

    @Override
    public Character put(Point2D key, Character value) {
        return map.put(key, value);
    }

    @Override
    public Character remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(@Nonnull Map<? extends Point2D, ? extends Character> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Point2D> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Character> values() {
        return map.values();
    }

    @Override
    public Set<Entry<Point2D, Character>> entrySet() {
        return map.entrySet();
    }

    private List<String> print(UnaryOperator<Character> supplier) {
        int maxX = map.keySet().stream().mapToInt(Point2D::x).max().orElse(0);
        int minX = map.keySet().stream().mapToInt(Point2D::x).min().orElse(0);
        int maxY = map.keySet().stream().mapToInt(Point2D::y).max().orElse(0);
        int minY = map.keySet().stream().mapToInt(Point2D::y).min().orElse(0);

        char[][] view = new char[maxY - minY + 1][maxX - minX + 1];
        for (char[] chars : view) {
            Arrays.fill(chars, '.');
        }

        for (Entry<Point2D, Character> entry : map.entrySet()) {
            view[entry.getKey().y() - minY][entry.getKey().x() - minX] = supplier.apply(entry.getValue());
        }

        return Arrays.stream(view).map(String::valueOf).toList();
    }

    @Override
    public String toString() {
        return String.join("\n", print(UnaryOperator.identity()));
    }
}
