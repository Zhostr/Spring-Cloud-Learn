package com.zst.test.algorithm.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/16 上午6:30
 * @version: V1.0
 */
@Slf4j
public class Sort {

    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int temp = array[low];
            int l = low;
            int h = high;
            while (l < h) {
                while (array[h] > temp && l < h) {
                    h--;
                }
                array[l] = array[h];
                while (array[l] <= temp && l < h) {
                    l++;
                }
                array[h] = array[l];
                log.info("array = {}", array);
            }
            array[l] = temp;
            log.info("array = {}", array);
            quickSort(array, low, l - 1);
            quickSort(array, h + 1, h);
        }
    }


    public static void main(String[] args) {
        int[] array = new int[]{1,4,4,4,12,78,8,45,67,39,20,90,65,34,34,6,7,88,9,1,-3,-88,-2};
        quickSort(array, 0, array.length - 1);
    }

}