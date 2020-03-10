package com.zst.test.rx_java;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * Observable 不支持 Backpressure，Flowable 支持
 *
 * @author: Zhoust
 * @date: 2020/01/17 下午5:21
 * @version: V1.0
 */
@Slf4j
public class BaseDemo {

    Observer observer = new Observer<String>() {
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
    };

    @Test
    @DisplayName("使用 create 方法创建 Observable 对象")
    void createBaseObservable() {
        Observable<String> observable = Observable.create(producer -> {
            producer.onNext("产生数据" + Math.random() * 100);
            producer.onComplete();
        });

        observable.subscribe(observer);
    }

    @Test
    @DisplayName("使用 just，自动调用 onNext 方法发送数据")
    void useJust() {
        Observable<? extends Serializable> just = Observable.just("1", 2, "3", 4, 5);
        just.subscribe(new Consumer<Serializable>() {
            @Override
            public void accept(Serializable serializable) throws Exception {
                if (serializable instanceof String) {
                    log.info("消费 String 类型数据 {}", (String)serializable);
                }
                else if (serializable instanceof Integer) {
                    log.info("消费 Integer 类型数据 {}", (Integer)serializable);
                }
            }
        });
    }

    @Test
    void useDefer() {
        Observable<String> deferObservable = Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                log.info("产生数据");
                return Observable.just("sd");
            }
        });
        deferObservable.subscribe(observer);
    }

    @Test
    void flowTest() {
        Flowable<Integer> flowable = Flowable.range(1, 100)
                .map(i -> i * i)
                .filter(i -> {
                    System.out.println("filter 执行");
                    return i % 2 == 0;
                });
        //flowable.subscribe(System.out::println);
    }
}