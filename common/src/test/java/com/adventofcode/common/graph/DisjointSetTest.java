package com.adventofcode.common.graph;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class DisjointSetTest {

    @Test
    void testInitialState() {
        var ds = new DisjointSet(5);
        assertThat(ds.getCount()).isEqualTo(5);
        assertThat((Collection<Integer>) ds.getSizes()).containsExactlyInAnyOrder(1, 1, 1, 1, 1);
        for (var i = 0; i < 5; i++) {
            assertThat(ds.find(i)).isEqualTo(i);
        }
    }

    @Test
    void testUnion() {
        var ds = new DisjointSet(5);
        assertThat(ds.union(0, 1)).isTrue();
        assertThat(ds.getCount()).isEqualTo(4);
        assertThat(ds.find(0)).isEqualTo(ds.find(1));
        assertThat((Collection<Integer>) ds.getSizes()).containsExactlyInAnyOrder(2, 1, 1, 1);

        assertThat(ds.union(2, 3)).isTrue();
        assertThat(ds.getCount()).isEqualTo(3);
        assertThat(ds.find(2)).isEqualTo(ds.find(3));
        assertThat((Collection<Integer>) ds.getSizes()).containsExactlyInAnyOrder(2, 2, 1);

        assertThat(ds.union(0, 2)).isTrue();
        assertThat(ds.getCount()).isEqualTo(2);
        assertThat(ds.find(0)).isEqualTo(ds.find(2));
        assertThat((Collection<Integer>) ds.getSizes()).containsExactlyInAnyOrder(4, 1);

        assertThat(ds.union(1, 3)).isFalse(); // already in the same set
        assertThat(ds.getCount()).isEqualTo(2);
    }

    @Test
    void testPathCompression() {
        var ds = new DisjointSet(5);
        assertThat(ds.union(0, 1)).isTrue();
        assertThat(ds.union(1, 2)).isTrue();
        assertThat(ds.union(2, 3)).isTrue();
        
        var root = ds.find(0);
        // After find(0), parent[0] should be root, parent[1] should be root etc.
        // Although parent is private, we can't directly check it, but find should return same root.
        for (var i = 0; i <= 3; i++) {
            assertThat(ds.find(i)).isEqualTo(root);
        }
    }

    @Test
    void testUnionBySize() {
        var ds = new DisjointSet(5);
        assertThat(ds.union(0, 1)).isTrue(); // {0,1}, {2}, {3}, {4}
        assertThat(ds.union(2, 3)).isTrue(); // {0,1}, {2,3}, {4}
        assertThat(ds.union(4, 0)).isTrue(); // {0,1,4}, {2,3}
        
        assertThat(ds.getCount()).isEqualTo(2);
        assertThat((Collection<Integer>) ds.getSizes()).containsExactlyInAnyOrder(3, 2);
    }
}
