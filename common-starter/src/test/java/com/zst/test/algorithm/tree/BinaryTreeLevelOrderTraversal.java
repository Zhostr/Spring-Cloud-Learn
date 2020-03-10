package com.zst.test.algorithm.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * https://leetcode.com/problems/binary-tree-level-order-traversal/
 * @description: 按层遍历二叉树
 * @author: Zhoust
 * @date: 2020/02/20 上午9:19
 * @version: V1.0
 */
public class BinaryTreeLevelOrderTraversal {

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return null;
        }

        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.addLast(root);

        List<List<Integer>> result = new ArrayList<>();
        while (!deque.isEmpty()) {
            int levelSize = deque.size();
            List<Integer> levelList = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = deque.removeFirst();
                levelList.add(node.val);
                if (node.left != null)
                    deque.addLast(node.left);
                if (node.right != null)
                    deque.addLast(node.right);
            }
            result.add(levelList);
        }
        return result;
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

}