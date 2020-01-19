package com.zst.test.rx_java;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


/**
 * @description:
 * @author: Zhoust
 * @date: 2020/01/17 下午5:21
 * @version: V1.0
 */
@Slf4j
public class ADemo {

    @Test
    void justTest() {
        Observable<String> observable = Observable.create(producer -> {
            producer.onNext("产生数据" + Math.random() * 100);
            producer.onComplete();
        });

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                log.info("subscribe 事件发生");
            }

            @Override
            public void onNext(String s) {
                log.info("接收到数据 {}", s);
                //int x = 3/0;
            }

            @Override
            public void onError(Throwable e) {
                log.error("产生异常", e);
            }

            @Override
            public void onComplete() {
                log.info("消费完成");
            }
        });



    }


}