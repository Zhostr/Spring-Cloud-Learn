package com.zst.test.concurrent;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/01/16 下午2:21
 * @version: V1.0
 */
@Slf4j
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        DemoTask demoTask = new DemoTask(countDownLatch);
        Runnable runnable = demoTask::task;

        for (int i = 0; i < 5; i ++) {
            new Thread(runnable).start();
        }

        log.info("Main 线程等待子任务执行完成......");
        countDownLatch.await();
        log.info("全部子任务执行完成，主线程退出");
    }

    static class DemoTask {
        CountDownLatch latch;

        public DemoTask(CountDownLatch latch) {
            this.latch = latch;
        }

        @SneakyThrows
        public void task() {
            log.info("线程[{}] 开始", Thread.currentThread().getName());
            TimeUnit.MILLISECONDS.sleep(3000);
            log.info("线程[{}] 结束", Thread.currentThread().getName());
            latch.countDown();
        }

    }

}