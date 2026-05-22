package org.example.licode;

/**
 * 并查集 ，这里是把二维数组坐标转换为一维数组，
 */
public class UnionFind2 {
    // 岛屿数量
    private int count;
    private final int[] parent;
    private final int[] rank;

    // 构造方法
    UnionFind2(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int len = rows * cols;
        this.count = 0;
        this.parent = new int[len];
        this.rank = new int[len];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // 把二维坐标转换为一维数组下标及内容
                int index = row * cols + col;
                if (grid[row][col] == '1') {
                    parent[index] = index;
                    // 先把每个点都视为一个岛屿
                    count++;
                }
                rank[index] = 0;
            }
        }
    }

    // find方法
    public int find(int index) {
        // 递归查找
        if (parent[index] != index)
            parent[index] = find(parent[index]);
        return parent[index];
    }

    // union 方法，每连接一次，就减少一个岛屿数量
    public void union(int x, int y) {
        int rootx = find(x);
        int rooty = find(y);
        // 根节点相同说明他们已经被连接在一起了
        if (rootx != rooty) {
            // 把高的树挂到矮的树后面，保证树不会变高、查找不会变慢。
            if (rank[rootx] > rank[rooty]) {
                parent[rooty] = rootx;
            } else if (rank[rootx] < rank[rooty]) {
                parent[rootx] = rooty;
            } else {
                parent[rooty] = rootx;
                rank[rootx]++;
            }
            count--;
        }
    }

    // 返回当前岛屿数目
    public int getCount() {
        return this.count;
    }
}