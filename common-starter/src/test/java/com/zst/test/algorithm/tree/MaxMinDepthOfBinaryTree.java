package com.zst.test.algorithm.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode.com/problems/maximum-depth-of-binary-tree/
 * https://leetcode.com/problems/minimum-depth-of-binary-tree/
 * @description: 二叉树最大最小深度
 * @author: Zhoust
 * @date: 2020/02/21 上午8:34
 * @version: V1.0
 */
public class MaxMinDepthOfBinaryTree {

    /**
     * 使用 BFS 计算最大深度
     * @param root
     * @return
     */
    public int maxDepthUseBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.addLast(root);
        int result = 0;
        while (!deque.isEmpty()) {
            result++;
            int num = deque.size();
            for(int i = 0; i < num; i++) {
                TreeNode node = deque.removeFirst();
                if (node.left != null)
                    deque.addLast(node.left);
                if (node.right != null)
                    deque.addLast(node.right);
            }
        }
        return result;
    }

    public int maxDepthUseDFS(TreeNode root) {
        if (root == null)
            return 0;

        int leftMaxDepth = maxDepthUseDFS(root.left);
        int rightMaxDepth = maxDepthUseDFS(root.right);
        return Math.max(leftMaxDepth, rightMaxDepth) + 1;
    }


    public int minDepthUseDFS(TreeNode root) {
        if (root == null)
            return 0;

        int left = minDepthUseDFS(root.left);
        int right = minDepthUseDFS(root.right);
        //注意这个 0 左右子树其中又一个为空，需要返回不为空子树的深度
        return (left == 0 || right == 0) ? left + right + 1 : Math.min(left, right) + 1;
    }

    public int minDepth(TreeNode root) {
        if(root == null) {
            return 0;
        }

        //如果左子树为空，找右子树的最小深度 + 1
        if(root.left == null) {
            return minDepth(root.right) + 1;
        }

        //如果右子树为空，找左子树的最小深度 + 1
        if(root.right == null) {
            return minDepth(root.left) + 1;
        }

        //左右两棵子树都不为空，选择深度最小的加 1
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        return Math.min(left, right) + 1;
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

}