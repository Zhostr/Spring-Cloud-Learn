package com.zst.test.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/16 下午3:28
 * @version: V1.0
 */
@Slf4j
public class CASTest {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

//    private Integer atomicInteger = 0; 这里就不行了
    private CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) {
        CASTest bean = new CASTest();
        new Thread(() -> {
            bean.printValueAndCASIncrement();
        }, "Thread1").start();
        new Thread(() -> {
            bean.printValueAndCASIncrement();
        }, "Thread2").start();
        try {
            bean.countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        System.out.println(bean.getAtomicInteger().get());
    }

    public void printValueAndCASIncrement() {
        for (int i = 0; i< 10000; i++) {
            int oldValue = atomicInteger.getAndIncrement();
            log.info("oldValue is {}", oldValue);
        }
        countDownLatch.countDown();
    }

    public AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }
}