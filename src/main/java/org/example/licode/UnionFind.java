package org.example.licode;

import java.util.Arrays;

public class UnionFind {
    // 父节点（普通并查集都有）
    private final int[] fa;
    // 权重：当前节点 / 父节点 =?
    public final double[] mul;

    UnionFind(int n) {
        fa = new int[n];
        for (int i = 0; i < n; i++) {
            fa[i] = i;
        }

        mul = new double[n];
        Arrays.fill(mul, 1);
    }

    private int find(int x) {
        if (fa[x] != x) {
            int root = find(fa[x]);
            mul[x] *= mul[fa[x]];
            fa[x] = root;
        }
        return fa[x];
    }

    public boolean same(int x, int y) {
        return find(x) == find(y);
    }

    public void merge(int from, int to, double value) {
        int x = find(from);
        int y = find(to);
        if (x == y) {
            return;
        }
        mul[x] = mul[to] * value / mul[from];
        fa[x] = y;
    }
}
