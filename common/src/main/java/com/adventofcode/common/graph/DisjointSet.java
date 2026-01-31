package com.adventofcode.common.graph;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public class DisjointSet {
    private final int[] parent;
    private final int[] size;
    private int count;

    public DisjointSet(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        count = n;
    }

    public int find(int i) {
        if (parent[i] == i) return i;
        return parent[i] = find(parent[i]);
    }

    public boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);
        if (rootI != rootJ) {
            if (size[rootI] < size[rootJ]) {
                parent[rootI] = rootJ;
                size[rootJ] += size[rootI];
            } else {
                parent[rootJ] = rootI;
                size[rootI] += size[rootJ];
            }
            count--;
            return true;
        }
        return false;
    }

    public int getCount() {
        return count;
    }

    public IntList getSizes() {
        IntList sizes = new IntArrayList();
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == i) {
                sizes.add(size[i]);
            }
        }
        return sizes;
    }
}
