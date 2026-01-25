package com.adventofcode.common.point.map;

import com.adventofcode.common.point.Point2D;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.LongFunction;

public class Map2D implements Map<Point2D, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Map2D.class);
    private final Map<Point2D, Long> map;

    public Map2D() {
        this.map = new HashMap<>();
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
    public @Nullable Long get(Object key) {
        return map.get(key);
    }

    @Override
    @CanIgnoreReturnValue
    public @Nullable Long put(Point2D key, Long value) {
        return map.put(key, value);
    }

    @Override
    @CanIgnoreReturnValue
    public @Nullable Long remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends Point2D, ? extends Long> m) {
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
    public Collection<Long> values() {
        return map.values();
    }

    @Override
    public Set<Map.Entry<Point2D, Long>> entrySet() {
        return map.entrySet();
    }

    @CanIgnoreReturnValue
    public List<String> print(char[][] view, LongFunction<Character> supplier) {
        for (Map.Entry<Point2D, Long> entry : map.entrySet()) {
            view[entry.getKey().y()][entry.getKey().x()] = supplier.apply(entry.getValue());
        }

        for (char[] chars : view) {
            LOGGER.debug("{}", chars);
        }
        return Arrays.stream(view).map(String::valueOf).toList();
    }

    @CanIgnoreReturnValue
    public List<String> print(LongFunction<Character> supplier) {
        int maxX = map.keySet().stream().mapToInt(Point2D::x).max().orElse(0);
        int minX = map.keySet().stream().mapToInt(Point2D::x).min().orElse(0);
        int maxY = map.keySet().stream().mapToInt(Point2D::y).max().orElse(0);
        int minY = map.keySet().stream().mapToInt(Point2D::y).min().orElse(0);

        char[][] view = new char[maxY - minY + 1][maxX - minX + 1];
        for (char[] chars : view) {
            Arrays.fill(chars, ' ');
        }

        for (Map.Entry<Point2D, Long> entry : map.entrySet()) {
            view[entry.getKey().y() - minY][entry.getKey().x() - minX] = supplier.apply(entry.getValue());
        }

        for (char[] chars : view) {
            LOGGER.debug("{}", chars);
        }
        return Arrays.stream(view).map(String::valueOf).toList();
    }
}
