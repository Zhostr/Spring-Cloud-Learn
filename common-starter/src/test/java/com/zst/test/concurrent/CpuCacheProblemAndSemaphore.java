package com.zst.test.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * Semaphore —— 信号量，用于控制同时访问某个资源的操作数量
 * Semaphore 内部维护了一组许可（permit）
 * 如果没有许可，acquire() 会一直阻塞直到有许可
 * release() 会释放一个许可
 *
 * @see CountDownLatchDemo
 * @description: cpu 缓存导致可见性问题，用 synchronized 或 Semaphore 解决，或者 CAS
 * @author: Zhoust
 * @date: 2019/12/30 下午12:48
 * @version: V1.0
 */
@Slf4j
public class CpuCacheProblemAndSemaphore {

    private int count;

    private Semaphore semaphore = new Semaphore(1);

    public void add10k() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10000; i++) {
            count++;
        }
        semaphore.release();
    }

    //TODO
    public static void main(String[] args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                log.info("thread = {}, has exc", t, e);
            }
        });
        new Thread( () -> {int i = 10/0;}, "ChildThread").start();
//        CpuCacheProblemAndSemaphore bean = new CpuCacheProblemAndSemaphore();
//        Thread thread1 = new Thread(() -> bean.add10k());
//        Thread thread2 = new Thread(() -> bean.add10k());
//        thread1.start();
//        thread2.start();
//
//        thread1.join();
//        thread2.join();
//        log.info("count = {}", bean.count);
    }

}