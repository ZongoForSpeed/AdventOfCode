package com.adventofcode.common.point.map;

import com.adventofcode.common.point.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class EnumMap2D<E extends Enum<E>> implements Map<Point2D, E> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnumMap2D.class);
    private final Map<Point2D, E> map;
    private final Function<E, Character> toCharFunction;

    public EnumMap2D() {
        this.map = new HashMap<>();
        this.toCharFunction = ignore -> ' ';
    }

    public EnumMap2D(Function<E, Character> toCharFunction) {
        this.map = new HashMap<>();
        this.toCharFunction = toCharFunction;
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
    public E get(Object key) {
        return map.get(key);
    }

    @Override
    public E put(Point2D key, E value) {
        return map.put(key, value);
    }

    @Override
    public E remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(@Nonnull Map<? extends Point2D, ? extends E> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    @Nonnull
    public Set<Point2D> keySet() {
        return map.keySet();
    }

    @Override
    @Nonnull
    public Collection<E> values() {
        return map.values();
    }

    @Override
    @Nonnull
    public Set<Entry<Point2D, E>> entrySet() {
        return map.entrySet();
    }

    public List<String> print(char[][] view, Function<E, Character> supplier) {
        for (Entry<Point2D, E> entry : map.entrySet()) {
            view[entry.getKey().y()][entry.getKey().x()] = supplier.apply(entry.getValue());
        }

        for (char[] chars : view) {
            LOGGER.debug("{}", chars);
        }
        return Arrays.stream(view).map(String::valueOf).toList();
    }

    @Override
    public String toString() {
        List<String> print = print(toCharFunction);
        return String.join(System.lineSeparator(), print);
    }

    public List<String> print(Function<E, Character> supplier) {
        int maxX = map.keySet().stream().mapToInt(Point2D::x).max().orElse(0);
        int minX = map.keySet().stream().mapToInt(Point2D::x).min().orElse(0);
        int maxY = map.keySet().stream().mapToInt(Point2D::y).max().orElse(0);
        int minY = map.keySet().stream().mapToInt(Point2D::y).min().orElse(0);

        char[][] view = new char[maxY - minY + 1][maxX - minX + 1];
        for (char[] chars : view) {
            Arrays.fill(chars, ' ');
        }

        for (Entry<Point2D, E> entry : map.entrySet()) {
            view[entry.getKey().y() - minY][entry.getKey().x() - minX] = supplier.apply(entry.getValue());
        }

        for (char[] chars : view) {
            LOGGER.debug("{}", chars);
        }
        return Arrays.stream(view).map(String::valueOf).toList();
    }
}
