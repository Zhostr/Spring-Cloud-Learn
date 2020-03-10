package com.zst.test.algorithm.stack_queue;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/sliding-window-maximum/
 * @description: 滑动窗口问题，每移动一次都输出最大的值
 * @author: Zhoust
 * @date: 2020/02/17 下午5:05
 * @version: V1.0
 */
public class SlidingWindow {

    /**
     * 大顶堆实现
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        //少了判断
        if(nums == null || nums.length == 0) {
            return new int[0];
        }
        //大顶堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, (a, b) -> b.compareTo(a));
        //先构造这个数组，并把数组中的前 k 个都放进去
        int[] result = new int[nums.length - k + 1];
        for (int i = 0; i < k; i++) {
            maxHeap.add(nums[i]);
        }

        int index = 0;
        for (int i = k; i < nums.length; i++) {
            result[index] = maxHeap.peek();
            maxHeap.remove(nums[index]);
            maxHeap.add(nums[i]);
            index++;
        }
        //少了这里
        result[index] = maxHeap.peek();
        return result;
    }

    /**
     * 双端队列实现 TODO
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindowDeque(int[] nums, int k) {
//        Deque<Integer> deque = new ArrayDeque<>(k);
//        for (int i = 0; i < k; i++) {
//            int value = nums[i];
//            if (deque.size() == 0) {
//                deque.addLast(value);
//            }
//            else {
//                Integer first = deque.peekFirst();
//                if (first < value) {
//                    deque.removeFirst();
//                }
//                deque.addLast(value);
//            }
//        }
//
//        int[] result = new int[nums.length - k + 1];
//        int index = 0;
//        for (int i = k; i < nums.length; i++) {
//            result[index] = deque.peekFirst();
//
//            if (nums[i] > )
//        }
//        return result;
        return nums;
    }

}