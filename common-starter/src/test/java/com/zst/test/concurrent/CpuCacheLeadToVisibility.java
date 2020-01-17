package com.zst.test.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: cpu 缓存导致可见性问题
 * @author: Zhoust
 * @date: 2019/12/30 下午12:48
 * @version: V1.0
 */
@Slf4j
public class CpuCacheLeadToVisibility {

    private int count;

    public synchronized void add10k() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CpuCacheLeadToVisibility bean = new CpuCacheLeadToVisibility();
        Thread thread1 = new Thread(() -> bean.add10k());
        Thread thread2 = new Thread(() -> bean.add10k());
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        log.info("count = {}", bean.count);
    }

}