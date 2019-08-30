package com.zst.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/08/28 15:36
 * @version: V1.0
 */
class ReactorTest {

    @Test
    @DisplayName("测试 Flux 功能")
    void fluxDemo() {
        Flux<List<Integer>> buffer = Flux.range(5, 3)
                                         .map(i -> i + 3)
                                         .filter(i -> i % 2 == 0)
                                         .buffer();
        buffer.blockFirst().forEach(i -> System.out.println(i + ","));

    }

}