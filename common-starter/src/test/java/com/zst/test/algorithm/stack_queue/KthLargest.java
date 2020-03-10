package com.zst.test.algorithm.stack_queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/kth-largest-element-in-a-stream/submissions/
 * @description: 找出第 K 个最大的数
 * @author: Zhoust
 * @date: 2020/02/17 上午9:45
 * @version: V1.0
 */
public class KthLargest {

    /** 小顶堆 **/
    private PriorityQueue<Integer> minHeap;

    private Integer kth;

    public KthLargest(int kth, int[] valueArray) {
        this.kth = kth;
        minHeap = new PriorityQueue<>(this.kth);
        //正确
        for (Integer x : valueArray) {
            this.add(x);
        }
        //this.minHeap.addAll(Arrays.asList(valueArray));//错误
    }

    public int add(Integer value) {
        if (minHeap.size() < kth) {
            minHeap.add(value);
            return minHeap.peek();
        }
        if (value > minHeap.peek()) {
            minHeap.remove();
            minHeap.add(value);
        }
        return minHeap.peek();
    }

}