package com.adventofcode.common.point.map;

import com.adventofcode.common.point.Point2D;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InfiniteBooleanMap implements Map<Point2D, Boolean> {
    private final Int2ObjectMap<Int2BooleanMap> map;

    public InfiniteBooleanMap() {
        this.map = new Int2ObjectOpenHashMap<>();
    }

    public static InfiniteBooleanMap read(String input, Predicate<Character> predicate) {
        return read(new Scanner(input), predicate);
    }

    public static InfiniteBooleanMap read(Scanner scanner, Predicate<Character> predicate) {
        return read(scanner, predicate, /*stopWhenBlank=*/false);
    }

    public static InfiniteBooleanMap read(Scanner scanner, Predicate<Character> predicate, boolean stopWhenBlank) {
        InfiniteBooleanMap map = new InfiniteBooleanMap();
        int j = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (stopWhenBlank && StringUtils.isBlank(line)) {
                break;
            }
            for (int i = 0; i < line.length(); i++) {
                map.put(Point2D.of(i, j), predicate.test(line.charAt(i)));
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
        if (key instanceof Point2D(int x, int y)) {
            Int2BooleanMap booleanMap = map.get(x);
            if (booleanMap != null) {
                return booleanMap.containsKey(y);
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        if (value instanceof Boolean boolValue) {
            return map.values().stream().anyMatch(e -> e.containsValue(boolValue.booleanValue()));
        } else {
            return false;
        }
    }

    @Override
    public @Nullable Boolean get(Object key) {
        if (key instanceof Point2D(int x, int y)) {
            Int2BooleanMap booleanMap = map.get(x);
            if (booleanMap != null) {
                return booleanMap.get(y);
            }
        }
        return null;
    }

    @Override
    @CanIgnoreReturnValue
    public @Nullable Boolean put(Point2D key, @Nullable Boolean value) {
        if (value == null) {
            return null;
        }
        return map.computeIfAbsent(key.x(), _ -> new Int2BooleanOpenHashMap()).put(key.y(), value.booleanValue());
    }

    @Override
    @CanIgnoreReturnValue
    public @Nullable Boolean remove(Object key) {
        if (key instanceof Point2D(int x, int y)) {
            Int2BooleanMap booleanMap = map.getOrDefault(x, null);
            if (booleanMap != null) {
                boolean remove = booleanMap.remove(y);
                if (booleanMap.isEmpty()) {
                    map.remove(x);
                }
                return remove;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends Point2D, ? extends Boolean> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Point2D> keySet() {
        return map.int2ObjectEntrySet()
                .stream()
                .flatMap(e -> e.getValue().keySet().intStream().mapToObj(i -> Point2D.of(e.getIntKey(), i)))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Boolean> values() {
        return map.values().stream().flatMap(t -> t.values().stream()).toList();
    }

    @Override
    public Set<Entry<Point2D, Boolean>> entrySet() {
        return map.int2ObjectEntrySet()
                .stream()
                .flatMap(e -> e.getValue()
                        .int2BooleanEntrySet()
                        .stream()
                        .map(i -> Map.entry(Point2D.of(e.getIntKey(), i.getIntKey()), i.getBooleanValue())))
                .collect(Collectors.toSet());
    }

    private List<String> print(Function<@Nullable Boolean, Character> supplier) {
        int maxX = map.keySet().intStream().max().orElse(0);
        int minX = map.keySet().intStream().min().orElse(0);
        int maxY = map.values().stream().flatMapToInt(m -> m.keySet().intStream()).max().orElse(0);
        int minY = map.values().stream().flatMapToInt(m -> m.keySet().intStream()).min().orElse(0);

        char[][] view = new char[maxY - minY + 1][maxX - minX + 1];
        for (char[] chars : view) {
            Arrays.fill(chars, '.');
        }

        for (Entry<Point2D, Boolean> entry : entrySet()) {
            view[entry.getKey().y() - minY][entry.getKey().x() - minX] = supplier.apply(entry.getValue());
        }

        return Arrays.stream(view).map(String::valueOf).toList();
    }

    @Override
    public String toString() {
        return String.join("\n", print(b -> Boolean.TRUE.equals(b) ? '#' : '.'));
    }
}
