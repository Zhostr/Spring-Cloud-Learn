package com.zst.test.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description: 模拟死锁
 * @author: Zhoust
 * @date: 2020/02/01 下午9:03
 * @version: V1.0
 */
@Slf4j
public class DeadlockDemo {

    private Object lock1 = new Object();

    private Object lock2 = new Object();

    private Integer number;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public DeadlockDemo(Integer num) {
        this.number = num;
    }

    public Integer calculateSum() {
        synchronized (lock1) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("InterruptedException ", e);
                Thread.currentThread().interrupt();
            }
            synchronized (lock2) {
                Integer result = 0;
                for (int i = 1; i <= number; i++) {
                    result += i;
                }
                return result;
            }
        }
    }

    public Integer calculateMultiplication() {
        synchronized (lock2) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("InterruptedException ", e);
                Thread.currentThread().interrupt();
            }
            synchronized (lock1) {
                Integer result = 1;
                for (int i = 1; i <= number; i++) {
                    result *= i;
                }
                return result;
            }
        }
    }

    public static void main(String[] args) {
        Executors.newFixedThreadPool(100);
        DeadlockDemo deadlockDemo = new DeadlockDemo(10);
        Thread thread1 = new Thread(() -> System.out.println(deadlockDemo.calculateSum()));
        Thread thread2 = new Thread(() -> System.out.println(deadlockDemo.calculateMultiplication()));
        thread1.start();
        thread2.start();
        try {
            //这里得让主线程休眠一段时间，在这里执行 jstack 显示主线程处于 TIMED_WAITING (sleeping) 状态，thread1、thread2 都处于 WAITING (parking) 状态（调用了 LockSupport.park 方法）
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        deadlockDemo.countDownLatch.countDown();
    }

}