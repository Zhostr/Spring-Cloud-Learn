package com.zst.test.algorithm.look_back;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 回溯最适合用递归实现
 * @description: https://leetcode-cn.com/problems/n-queens/
 * @author: Zhoust
 * @date: 2020/03/06 下午7:53
 * @version: V1.0
 */
public class NQueens {

    public static class SolutionA {
        public List<List<String>> solveNQueens(int n) {
            List<List<String>> result = new ArrayList<>();
            //注意这里的 pie 大小是 2n
            DFS(new int[n][n], 0, new boolean[n], new boolean[2 * n], new boolean[2 * n], result);
            return result;
        }

        /**
         * 最开始 columns、pies、naList 都是 List，但是 list.remove(x) 是按照下标来的，add(x) 是直接放到末尾的，remove(x) 会报错，因为下超限
         * 之后都改成 Set，避免 remove(x) 异常
         * 看了别人的解析之后，将前两个改成了 boolean[]
         * @param array
         * @param level
         * @param columns           纵轴不能有重复的
         * @param pies              撇
         * @param nas               捺（注意这里不能用 Math.abs(level-i) 会攻击到不是这一捺的别的位置，就是跟这一捺对称位置的也不能放置
         * @param resultList
         */
        public void DFS(int[][] array, int level, boolean[] columns, boolean[] pies, boolean[] nas, List<List<String>> resultList) {
            if (level == array.length) {
                //找到一个符合条件的了
                List<String> list = new ArrayList<>();
                for (int i = 0; i < array.length; i++) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < array.length; j++) {
                        if (array[i][j] == 1) {
                            sb.append("Q");
                        } else {
                            sb.append(".");
                        }
                    }
                    list.add(sb.toString());
                }
                resultList.add(list);
                return;
            }

            //遍历第 level 行，挨个找能放进去的位置，(level, i)
            for (int i = 0; i < array.length; i++) {
                if (!columns[i] && !pies[level + i] && !nas[level - i + array.length - 1]) {
                    //注意这里递归调用之后，要清理之前占的位置
                    columns[i] = true;
                    pies[level + i] = true;
                    nas[level - i + array.length - 1] = true;
                    array[level][i] = 1;
                    DFS(array, level + 1, columns, pies, nas, resultList);
                    array[level][i] = 0;
                    columns[i] = false;
                    pies[level + i] = false;
                    nas[level - i + array.length - 1] = false;
                }
            }
        }
    }

    public static class SolutionB {

        //存放每一行皇后的位置
        int[] eachLevelIndex;

        public void solveNQueens(int n) {
            eachLevelIndex = new int[n];
            nQueens(0, 4);
        }

        public void nQueens(int level, int n) {
            if (level == n) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < n; j++) {
                        if (eachLevelIndex[i] == j) {
                            sb.append("Q");
                        } else {
                            sb.append("*");
                        }
                    }
                    list.add(sb.toString());
                }
                list.stream().forEach(System.out::println);
                System.out.println();
                return;
            }

            for (int i = 0; i < n; i++) {
                //判断第 level 行 i 列能不能放置
                boolean canSet = true;
                int leftColumn = i - 1, rightColumn= i + 1;
                for (int j = level - 1; j >= 0; j--) {
                    if (eachLevelIndex[j] == i) {
                        canSet = false;
                        break;
                    }
                    if (leftColumn >= 0 && eachLevelIndex[j] == leftColumn) {
                        canSet = false;
                        break;
                    }
                    if (rightColumn < n && eachLevelIndex[j] == rightColumn) {
                        canSet = false;
                        break;
                    }
                    leftColumn--; rightColumn++;
                }

                if (canSet) {
                    /**
                     * 理解一下，这里设置值之后无需清理掉，因为上面判断 (level, i) 位置能否放置元素是比较之前的行
                     */
                    eachLevelIndex[level] = i;
                    nQueens(level + 1, n);
                }
            }
        }

        public static void main(String[] args) {
            SolutionB solutionB = new SolutionB();
            solutionB.solveNQueens(4);
        }

    }



}