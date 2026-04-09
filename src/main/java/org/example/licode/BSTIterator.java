package org.example.licode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 二叉搜索树迭代器,迭代解法
 */
class BSTIterator {

    private Deque<TreeNode> stack = null;
    private TreeNode cur = null;

    public BSTIterator(TreeNode root) {
        this.cur = root;
        this.stack = new LinkedList<>();
    }

    public int next() {
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
        cur = stack.pop();
        int result = cur.val;
        cur = cur.right;
        return result;
    }

    public boolean hasNext() {
        return cur != null || !stack.isEmpty();
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;

        }
    }
}