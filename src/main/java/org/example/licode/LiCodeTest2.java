package org.example.licode;

import java.util.*;

public class LiCodeTest2 {


    public static void main(String[] args) {
        List<List<String>> equations = new ArrayList<>();
        equations.add(new ArrayList<>(List.of(new String[]{"a", "b"})));
        equations.add(new ArrayList<>(List.of(new String[]{"b", "c"})));

        double[] values = {2.0, 3.0};
        List<List<String>> queries = new ArrayList<>();
        queries.add(new ArrayList<>(List.of(new String[]{"a", "c"})));
        queries.add(new ArrayList<>(List.of(new String[]{"b", "a"})));
        queries.add(new ArrayList<>(List.of(new String[]{"a", "e"})));
        queries.add(new ArrayList<>(List.of(new String[]{"a", "a"})));
        queries.add(new ArrayList<>(List.of(new String[]{"x", "x"})));

        System.out.println(Arrays.toString(calcEquation(equations, values, queries)));
    }

    /**
     * 被围绕的区域 :
     * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' 组成，捕获 所有 被围绕的区域：
     * 连接：一个单元格与水平或垂直方向上相邻的单元格连接。
     * 区域：连接所有 'O' 的单元格来形成一个区域。
     * 围绕：如果一个区域中的所有 'O' 单元格都不在棋盘的边缘，则该区域被包围。这样的区域 完全 被 'X' 单元格包围。
     * 通过 原地 将输入矩阵中的所有 'O' 替换为 'X' 来 捕获被围绕的区域。你不需要返回任何值。
     *
     * @param board
     */
    public void solve(char[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == 'O' && (r == 0 || c == 0 || r == rows - 1 || c == cols - 1)) {
                    // 从边界处的 O 一个个查找未被包围区域并标记，
                    solveDfs(board, r, c);
                    // solveBfs(board, r, c);
                }
            }
        }
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                //未被标记的即是被围绕的区域
                if (board[r][c] == 'O') {
                    board[r][c] = 'X';
                }
                if (board[r][c] == 'F') {
                    board[r][c] = 'O';

                }
            }
        }

    }

    /**
     * 深度优先遍历解法
     *
     * @param board
     * @param r
     * @param c
     */
    private void solveDfs(char[][] board, int r, int c) {
        if (r < 0 || c < 0
                || r >= board.length || c >= board[0].length
                || board[r][c] != 'O')
            return;
        // 把统计的未被包围的地方标记为已经访问
        board[r][c] = 'F';
        solveDfs(board, r + 1, c);
        solveDfs(board, r - 1, c);
        solveDfs(board, r, c + 1);
        solveDfs(board, r, c - 1);
    }

    /**
     * 广度优先遍历解法
     *
     * @param board
     * @param r
     * @param c
     */
    private void solveBfs(char[][] board, int r, int c) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r, c});
        int rows = board.length;
        int cols = board[0].length;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int tmpR = cur[0];
            int tmpC = cur[1];
            // 注意这个写法细节一定要会，先统一判断边界条件
            if (tmpR >= 0 && tmpC >= 0
                    && tmpR < rows && tmpC < cols
                    && board[tmpR][tmpC] == 'O') {
                board[tmpR][tmpC] = 'F';
                // 这里无论合规不合规都一股脑丢进去，由下层循环来判断
                queue.offer(new int[]{tmpR + 1, tmpC});
                queue.offer(new int[]{tmpR - 1, tmpC});
                queue.offer(new int[]{tmpR, tmpC + 1});
                queue.offer(new int[]{tmpR, tmpC - 1});
            }
        }
    }


    public static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    Map<Node, Node> visited = new HashMap<>();

    /**
     * 克隆图 深度优先遍历
     *
     * @param node
     * @return
     */
    public Node cloneGraph(Node node) {
        // 要递归就要有终止条件，有两个，一是遇到null，
        if (node == null) {
            return node;
        }
        // 二就是遇到已经访问的节点 ,直接返回它对应的克隆节点
        if (visited.containsKey(node)) {
            return visited.get(node);
        }
        // 创建克隆节点
        Node cloneNode = new Node(node.val, new ArrayList<>());
        visited.put(node, cloneNode);
        for (Node neighbor : node.neighbors) {
            // 递归构建邻接点
            cloneNode.neighbors.add(cloneGraph(neighbor));
        }
        return cloneNode;
    }

    /**
     * 克隆图 广度优先遍历
     *
     * @param node
     * @return
     */
    public Node cloneGraph2(Node node) {
        if (node == null) {
            return node;
        }
        visited.put(node, new Node(node.val, new ArrayList<>()));
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Node neighbor : cur.neighbors) {
                if (!visited.containsKey(neighbor)) {
                    // 未访问的节点，克隆后放入map中
                    visited.put(neighbor, new Node(neighbor.val, new ArrayList<>()));
                    queue.offer(neighbor);
                }
                // 不论有没有访问过，都要去更新list，前面的判断只是防止死循环，重复添加节点而已
                visited.get(cur).neighbors.add(visited.get(neighbor));
            }
        }
        // 头节点对应的克隆节点就是新的头节点
        return visited.get(node);
    }

    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Integer> variableToId = new HashMap<>();
        for (List<String> equation : equations) {
            for (String s : equation) {
                variableToId.putIfAbsent(s, variableToId.size());
            }
        }

        UnionFind uf = new UnionFind(variableToId.size());
        for (int i = 0; i < equations.size(); i++) {
            List<String> equation = equations.get(i);
            uf.merge(variableToId.get(equation.get(1)), variableToId.get(equation.get(0)), values[i]);
        }

        double[] ans = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            List<String> query = queries.get(i);
            Integer c = variableToId.get(query.get(0));
            Integer d = variableToId.get(query.get(1));
            if (c != null && d != null && uf.same(c, d)) {
                ans[i] = uf.mul[d] / uf.mul[c];
            } else {
                ans[i] = -1;
            }
        }

        return ans;
    }

    /**
     * 蛇梯棋, 关键在于计算目标位置二维数组的坐标.
     *
     * @param board
     * @return
     */
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        // 因为下标是从0开始的,但是我们的位置是从1开始的,所以直接扩大一位就能全部存下来了
        boolean[] vis = new boolean[n * n + 1];
        Queue<int[]> queue = new LinkedList<int[]>();
        // int[0] 是位置 int[1] 是步数，起始位置是1，初始步数为 0
        queue.offer(new int[]{1, 0});
        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            // 每次只能走1-6步，直接遍历所有可能位置,把这些位置存入队列
            for (int i = 1; i <= 6; ++i) {
                int nxt = p[0] + i; // 目标位置
                if (nxt > n * n) { // 目标位置超出边界
                    break;
                }
                int[] rc = id2rc(nxt, n); //计算下一步的行列
                if (board[rc[0]][rc[1]] > 0) { // 存在蛇或梯子跳到蛇和梯子的目标位置
                    nxt = board[rc[0]][rc[1]];
                }
                if (nxt == n * n) { // 到达终点
                    return p[1] + 1;
                }
                if (!vis[nxt]) {
                    vis[nxt] = true;
                    // nxt作为下一个起点，步数加1
                    queue.offer(new int[]{nxt, p[1] + 1}); // 扩展新状态
                }
            }
        }
        return -1;
    }

    public int[] id2rc(int id, int n) {
        int r = (id - 1) / n; //计算从下往上数第几行
        int c = (id - 1) % n;
        // 奇数列要从后往前
        if (r % 2 == 1) {
            c = n - 1 - c;
        }
        // 行数要上下翻转，因为矩阵是反过来的
        return new int[]{n - 1 - r, c};
    }

}
