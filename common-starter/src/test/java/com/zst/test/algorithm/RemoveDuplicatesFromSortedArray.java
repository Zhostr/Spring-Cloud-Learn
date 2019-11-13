package com.zst.test.algorithm;

/**
 * leetcode 地址：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/
 * @description: 删除排序数组中的重复项
 * @author: Zhoust
 * @date: 2019/11/13 下午11:03
 * @version: V1.0
 */
public class RemoveDuplicatesFromSortedArray {

    public int removeDuplicates(int[] nums) {
        int preIndex = 0;
        int afterIndex = 1;
        int length = nums.length;//
        while(afterIndex < length) {
            while(afterIndex < length && nums[afterIndex] == nums[preIndex]) {
                afterIndex++;
            }
            if(afterIndex > length -1) {
                break;
            }
            nums[++preIndex] = nums[afterIndex];
        }
        return ++preIndex;
    }

}