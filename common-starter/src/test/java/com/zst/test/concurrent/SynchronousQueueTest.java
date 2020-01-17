package com.zst.test.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/01/16 下午2:04
 * @version: V1.0
 */
public class SynchronousQueueTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        AtomicInteger shardState = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Runnable producer = () -> {
            Integer producedElement = ThreadLocalRandom.current().nextInt();
            shardState.set(producedElement);
            countDownLatch.countDown();
        };

        Runnable consumer = () -> {
            try {
                countDownLatch.await();
                int i = shardState.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        executorService.execute(producer);
        executorService.execute(consumer);

        executorService.awaitTermination(500, TimeUnit.MILLISECONDS);
        executorService.shutdown();
        System.out.println(countDownLatch.getCount());
    }

}