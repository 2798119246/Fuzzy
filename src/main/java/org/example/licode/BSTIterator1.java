package org.example.licode;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉搜索树迭代器,递归解法
 */
class BSTIterator1 {

    private List<Integer> cacheList = new ArrayList<>();
    private int index;

    public BSTIterator1(TreeNode root) {
        this.cacheList = inOrder(root, cacheList);
        this.index = 0;
    }

    public int next() {
        return cacheList.get(index++);
    }

    public boolean hasNext() {
        return index < cacheList.size();
    }

    private List<Integer> inOrder(TreeNode root, List<Integer> cacheList) {
        if (root == null) {
            return cacheList;
        }
        inOrder(root.left, cacheList);
        cacheList.add(root.val);
        inOrder(root.right, cacheList);
        return cacheList;
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
