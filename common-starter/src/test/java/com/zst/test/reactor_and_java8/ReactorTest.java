package com.zst.test.reactor_and_java8;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @see Java8Test
 * @description:
 * @author: Zhoust
 * @date: 2019/10/30 下午4:27
 * @version: V1.0
 */
@Slf4j
public class ReactorTest {

    //============= Flux 测试 =============
    @Test
    void test01() {
        String fullUrl = "/interactive-teacher/api/student/class/report";
        List<String> list = Lists.newArrayList("/interactive-teacher/api/student/class/report", "/interactive-teacher/api/student/class/groupRanking");
        Mono<Boolean> containEvenNumber = Flux.fromIterable(list).all(url -> !fullUrl.startsWith(url));
        log.info("reactor 代码：containEvenNumber ? {}", containEvenNumber.block());
        log.info("Java8 stream 代码： {}", list.stream().anyMatch(i -> fullUrl.startsWith(i)));
    }

    @Test
    void earnMoney() {
        int x = 21000;
        int upstreamEarn = 1200;
        for (int i = 0; i < 2; i++) {
            int myEarn = x < 20000 ? 50 : 100;
            log.info("原价 {}, 上游挣的钱 {}, 雁过拔毛 {}, 最终手续费 {}, 买家最低省 {}", x, upstreamEarn, myEarn, upstreamEarn + myEarn, x*0.15-(upstreamEarn + myEarn));
            x += 1000;
            upstreamEarn += 200;
        }
    }

}