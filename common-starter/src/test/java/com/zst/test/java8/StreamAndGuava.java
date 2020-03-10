package com.zst.test.java8;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/10/22 上午11:03
 * @version: V1.0
 */
@Slf4j
public class StreamAndGuava {

    @Test
    @DisplayName("IntStream 测试")
    void intStreamTest() {
        //从 0 开始，以步长为 1，生成 1000 个数
        IntStream range = IntStream.range(0, 1000);
        List<Integer> collect = range.boxed().collect(Collectors.toList());
        collect.forEach(i -> {
            if (i < 500) {
                //跳过小于 500 的情况，不是跳出整个循环
                return;
            }
            else {
                System.out.println(i);
            }
        });
    }

    @Test
    @DisplayName("Splitter 测试")
    void splitterTest() {
        List<String> nameList = Splitter.on(",").trimResults().splitToList("小王,     小李   ,小红,小张");
        nameList.stream().forEach(System.out::println);
    }


    @Test
    void testStream() {
        List<Student> list = new ArrayList<>(4);
        list.add(Student.builder().id(1L).name("学生1").score(10).build());
        list.add(Student.builder().id(1L).name("学生2").score(40).build());
        list.add(Student.builder().id(2L).name("学生1").score(60).build());
        list.add(Student.builder().id(3L).name("学生1").score(10).build());

        //这里有两个 id = 1 的记录，如果只用 Collectors.toMap(Student::getId) 会报错，最后一个参数的意思是后一个代替掉前一个
        Map<Long, Student> collect = list.stream().collect(Collectors.toMap(Student::getId, Function.identity(), (v1, v2) -> v2));
        for (Long id : collect.keySet()) {
            System.out.println(collect.get(id));
        }

        Map<Long, List<Student>> collect1 = list.stream().collect(Collectors.groupingBy(Student::getId));
        for (Long studentId : collect1.keySet()) {
            log.debug("studentId = {}, val = {}", studentId, collect1.get(studentId));
        }

        //只有当 list 为空时，才会导致 getAsLong() 抛异常 java.util.NoSuchElementException: No value present
        long asLong = list.stream().mapToLong(Student::getId).reduce(Long::sum).getAsLong();
        log.debug("total score = {}", asLong);

        Integer minScore = list.stream().map(Student::getScore).min(Integer::compareTo).get();
        log.info("minScore = {}", minScore);

        Integer maxScore = list.stream().map(Student::getScore).max(Integer::compareTo).get();
        log.info("maxScore = {}", maxScore);
    }

}