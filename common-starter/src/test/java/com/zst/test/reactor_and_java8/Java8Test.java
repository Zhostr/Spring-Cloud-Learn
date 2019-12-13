package com.zst.test.reactor_and_java8;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
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
class Java8Test {

    @Test
    @DisplayName("IntStream 测试")
    void intStreamTest() {
        //从 0 开始，以步长为 1，生成 1000 个数
        IntStream range = IntStream.range(0, 1000);
        List<Integer> collect = range.boxed().collect(Collectors.toList());
        collect.forEach(System.out::println);

    }

    @Test
    @DisplayName("Splitter 测试")
    void splitterTest() {
        List<String> nameList = Splitter.on(",").trimResults().splitToList("小王,     小李   ,小红,小张");
        nameList.stream().forEach(System.out::println);
    }

}