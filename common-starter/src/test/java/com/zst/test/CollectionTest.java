package com.zst.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/01/09 下午7:15
 * @version: V1.0
 */
@Slf4j
public class CollectionTest {

    @Test
    @DisplayName("set 测试")
    void setTest() {
        Set<Long> courseIdSet = new HashSet<>();
        courseIdSet.add(124L);
        courseIdSet.add(123L);
        courseIdSet.add(123L);
        courseIdSet.add(125L);

        courseIdSet.forEach(System.out::println);
        System.out.println("xxx");
        Set<Long> sortedCourseIdSet = new TreeSet<>((o1, o2) -> {
            if (o1.equals(125L)) {
                return -1;
            }else {
                return o1.compareTo(o2);
            }
        });
        sortedCourseIdSet.addAll(courseIdSet);
        sortedCourseIdSet.forEach(System.out::println);
    }

    public static void main(String[] args) {
        File file = new File("/Users/zhoust/Desktop/README.txt");
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        Integer[] array = new Integer[]{10, 3, 5, 7,9,6};
        Arrays.sort(array, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        for (int i : array) {
            log.info("{}", i);
        }
    }


}