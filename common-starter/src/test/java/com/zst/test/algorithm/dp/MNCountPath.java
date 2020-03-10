package com.zst.test.algorithm.dp;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/26 上午8:45
 * @version: V1.0
 */
public class MNCountPath {

    /**
     * 递推方法
     * @param board     棋盘（如果有障碍物，board[i][j] = 1）
     * @param m
     * @param n
     * @return
     */
    public static int countPath(int[][]board, int m, int n) {
        int length = board[0].length;
        int wide = board.length;
        if (m >= length || n >= wide) {
            //表明越界了
            return 0;
        }
        if (board[m][n] == 1) {
            //有障碍物
            return 0;
        }
        if (m == length - 1 && n == wide - 1)
            return 1;
        return countPath(board, m + 1, n) + countPath(board, m, n + 1);
    }


    public static int countPathDP(int[][]board, int m, int n) {
        int length = board[0].length;
        int wide = board.length;
        int[][] swap = new int[wide][length];

        for (int i = wide - 1; i >= 0; i--) {
            for (int j = length - 1; j >= 0; j--) {

            }
        }
        return swap[0][0];
    }


    public static void main(String[] args) {
        int[][] board = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 1, 0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        };

        System.out.println(countPath(board, 0, 0));

    }

}