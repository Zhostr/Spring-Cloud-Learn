package com.zst.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/08/28 15:36
 * @version: V1.0
 */
@Slf4j
class ReactorTest {

    @Test
    @DisplayName("测试 Flux 功能")
    void fluxDemo() {
        //Flux 和 Mono 都可以发出元素值、错误信号、完成信号（错误信号和完成信号不能共存）
        Flux<List<Integer>> buffer = Flux.range(5, 3) //[start, start + count]
                                         .map(i -> i + 3)
                                         .filter(i -> i % 2 == 0)
                                         .buffer();
        //只有调用 subscribe 方法时，才会触发操作
        buffer.subscribe(
                i -> System.out.println(i + ","),           //对正常数据如何处理
                e -> log.error("something went wrong!", e), //对错误信号如何处理
                () -> System.out.println("完成处理")          //对完成信号的处理
                );

    }

}