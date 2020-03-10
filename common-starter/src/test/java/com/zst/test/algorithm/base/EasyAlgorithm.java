package com.zst.test.algorithm.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/11/25 下午5:15
 * @version: V1.0
 */
@Slf4j
public class EasyAlgorithm {

    public int[] twoSum(int[] nums, int target) {
//        不能先把所有的都 put 进去，如果有重复值会覆盖掉之前的值
//        Key 是 nums[i]，value 是下标

//        Map<Integer, Integer> map = new HashMap<>();
//        for(int i = 0; i < nums.length; i++) {
//            map.put(i, nums[i]);
//        }
//
//        for(Integer x : map.values()) {
//            if(map.containsValue(target - x)) {
//                return new int[]{map.get(x), map.get(target-x)};
//            }
//        }
//        return null;

        //除了暴力两次 for 循环，还可以用 Map 来解决
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i< nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
                //下面这里 Map 中还没有放入 nums[i]
                //return new int[]{map.get(nums[i]), map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    /**
     * https://leetcode.com/problems/3sum/
     * nums 无序，先排序，后遍历。第一层挨个遍历，第二层两个指针，一个从前往后，一个从后往前
     *
     * 如果上来就用 List<List<Integer>> 可能会把重复的也返回
     * 如 [-1,-1,0,1,2,-4]，会输出两次 -1 0 1
     * 然后用 List add 之前调用 contains 判重，会超时
     * 所以得用 Set<List<Integer>>
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        Set<List<Integer>> set = new HashSet<>();

        for(int i = 0; i < nums.length; i++) {
            int first = nums[i];
            for (int j = i + 1, k = nums.length - 1; j < k ;) {
                int target = -first;
                if (nums[j] + nums[k] < target) {
                    j++;
                } else if (nums[j] + nums[k] > target) {
                    k--;
                }
                else {
                    //这个 List 要放在第二个循环内部
                    List<Integer> list = new ArrayList<>(3);
                    list.add(first);
                    list.add(nums[j]);
                    list.add(nums[k]);
                    //用 List 这里显示超时
                    set.add(list);
                    j++;
                    k--;
                }
            }
        }
        return new ArrayList<>(set);
    }

    public boolean isAnagram(String s, String t) {
        Map<Character, Integer> mapS = new HashMap<>();
        Map<Character, Integer> mapT = new HashMap<>();

        for(int i = 0; i < s.length(); i++) {
            if(mapS.containsKey(s.charAt(i))) {
                mapS.put(s.charAt(i), mapS.get(s.charAt(i)) + 1);
            }
            else {
                mapS.put(s.charAt(i), 1);
            }
        }
        for(int i = 0; i < t.length(); i++) {
            if(mapT.containsKey(t.charAt(i))) {
                mapT.put(t.charAt(i), mapT.get(t.charAt(i)) + 1);
            }
            else {
                mapT.put(t.charAt(i), 1);
            }
        }

        //记住这里，别循环对比
        return mapS.equals(mapT);
    }

    /**
     * https://leetcode.com/problems/powx-n/submissions/
     * 计算 x 的 n 次方
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if(n < 0) {
            return 1/pow(x, -n);
        }
        return pow(x, n);
    }

    public double pow(double x, int n) {
        if(n == 0) {
            return 1;
        }

        double y = pow(x, n/2);
        if(n % 2 == 0) {
            return y*y;
        }
        else {
            return x*y*y;
        }
    }







}