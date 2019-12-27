package com.zst.test;

import java.util.stream.IntStream;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/12/27 上午9:00
 * @version: V1.0
 */
public class ConcurrentTest {

    private long count = 0;

    private void add10k() {
        IntStream.range(0, 10000).forEach(i -> count++);
    }

    public static void main(String[] args) throws InterruptedException {
        ConcurrentTest concurrentTest = new ConcurrentTest();
        Thread thread1 = new Thread(concurrentTest::add10k);
        Thread thread2 = new Thread(concurrentTest::add10k);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(concurrentTest.count);
    }

}
