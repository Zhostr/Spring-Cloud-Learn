package com.zst.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/01/09 下午7:15
 * @version: V1.0
 */
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


}