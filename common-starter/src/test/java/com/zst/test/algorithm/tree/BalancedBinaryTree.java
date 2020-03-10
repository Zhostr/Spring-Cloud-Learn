package com.zst.test.algorithm.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/balanced-binary-tree/
 * @description: 判断是否为平衡二叉树
 * @author: Zhoust
 * @date: 2020/02/21 下午11:03
 * @version: V1.0
 */
public class BalancedBinaryTree {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

    }

    public boolean isBalanced(TreeNode root) {
        if (root == null)
            return true;
        return deep(root) != -1;
    }

    /**
     * 判断某个节点的深度，同时判断该节点处是否是平衡二叉树，如果不是返回 -1，是的话正常返回该节点的深度
     * @param node
     * @return
     */
    int deep(TreeNode node) {
        if (node == null)
            return 0;

        int leftDeep = deep(node.left);
        int rightDeep = deep(node.right);
        if (leftDeep == -1 || rightDeep == -1)
            return -1;
        if (Math.abs(leftDeep-rightDeep) > 1)
            return -1;
        return Math.max(leftDeep, rightDeep) + 1;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

}