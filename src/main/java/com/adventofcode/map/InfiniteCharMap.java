package com.adventofcode.map;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InfiniteCharMap implements Map<Point2D, Character> {
    private final Map<Point2D, Character> map;

    public InfiniteCharMap() {
        this.map = new HashMap<>();
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Character get(Object key) {
        return map.get(key);
    }

    public Character put(Point2D key, Character value) {
        return map.put(key, value);
    }

    public Character remove(Object key) {
        return map.remove(key);
    }

    public void putAll(Map<? extends Point2D, ? extends Character> m) {
        map.putAll(m);
    }

    public void clear() {
        map.clear();
    }

    public Set<Point2D> keySet() {
        return map.keySet();
    }

    public Collection<Character> values() {
        return map.values();
    }

    public Set<Entry<Point2D, Character>> entrySet() {
        return map.entrySet();
    }

    private List<String> print(Function<Character, Character> supplier) {
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

        return Arrays.stream(view).map(String::valueOf).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.join("\n", print(Function.identity()));
    }
}
