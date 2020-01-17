package com.zst.test.rx_java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

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
        Observable<String> sender = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello RxJava");
                subscriber.onNext("Hello Again");
                subscriber.onCompleted();//发射完成需要手动调用这个方法，才会触发观察者的 onCompleted() 方法
            }
        });

        Observer<String> receiver = new Observer<String>() {
            @Override
            public void onCompleted() {
                log.info("数据接收完成");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                //正常接收到数据时调用
                log.info("接收到字符串是 {}", s);
            }
        };

        sender.subscribe(receiver);
    }


}