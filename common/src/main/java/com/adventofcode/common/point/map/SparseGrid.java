package com.adventofcode.common.point.map;

import com.adventofcode.common.point.Point2D;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/// An unbounded grid storing a value for each point that has been set.
///
/// `toString()` draws the bounding box of the set points, using `toChar` for each value and `background` for the
/// empty cells.
public class SparseGrid<V> extends AbstractMap<Point2D, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SparseGrid.class);

    private final Map<Point2D, V> map = new HashMap<>();
    private final Function<? super V, Character> toChar;
    private final char background;

    public SparseGrid() {
        this(v -> String.valueOf(v).charAt(0), ' ');
    }

    public SparseGrid(Function<? super V, Character> toChar, char background) {
        this.toChar = toChar;
        this.background = background;
    }

    /// Draws the bounding box of the points of `map`, one string per line.
    public static <V> List<String> render(Map<Point2D, ? extends V> map, Function<? super V, Character> toChar, char background) {
        int minX = map.keySet().stream().mapToInt(Point2D::x).min().orElse(0);
        int maxX = map.keySet().stream().mapToInt(Point2D::x).max().orElse(0);
        int minY = map.keySet().stream().mapToInt(Point2D::y).min().orElse(0);
        int maxY = map.keySet().stream().mapToInt(Point2D::y).max().orElse(0);

        var view = new char[maxY - minY + 1][maxX - minX + 1];
        for (char[] line : view) {
            Arrays.fill(line, background);
        }
        map.forEach((p, v) -> view[p.y() - minY][p.x() - minX] = toChar.apply(v));
        return Arrays.stream(view).map(String::valueOf).toList();
    }

    /// Draws the grid with a custom `toChar`, and logs it at debug level.
    @CanIgnoreReturnValue
    public List<String> print(Function<? super V, Character> toChar) {
        var lines = render(map, toChar, background);
        lines.forEach(line -> LOGGER.debug("{}", line));
        return lines;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public @Nullable V get(Object key) {
        return map.get(key);
    }

    @Override
    @CanIgnoreReturnValue
    public @Nullable V put(Point2D key, V value) {
        return map.put(key, value);
    }

    @Override
    @CanIgnoreReturnValue
    public @Nullable V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Entry<Point2D, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public String toString() {
        return String.join("\n", render(map, toChar, background));
    }
}
