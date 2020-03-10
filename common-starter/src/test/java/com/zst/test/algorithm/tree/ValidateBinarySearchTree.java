package com.zst.test.algorithm.tree;

/**
 * https://leetcode.com/problems/validate-binary-search-tree/
 * @description: 判断一个树是不是二叉排序树
 * @author: Zhoust
 * @date: 2020/02/18 下午4:04
 * @version: V1.0
 */
public class ValidateBinarySearchTree {


    public boolean isValidBST(TreeNode root) {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValid(TreeNode root, long min, long max) {
        if (root == null) {
            return true;
        }
        if (root.val <= min || root.val >= max) {
            return false;
        }
        //这里记得是 val 不是 val +1/-1
        return isValid(root.left, min, root.val) && isValid(root.right, root.val, max);
    }

    class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) {
          val = x;
      }
    }
}