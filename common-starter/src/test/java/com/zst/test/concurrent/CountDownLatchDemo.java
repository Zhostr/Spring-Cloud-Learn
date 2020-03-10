package com.zst.test.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 用来解决一个线程等待多个线程的场景，类比旅游团长要等待所有游客到达后才能去下一个景点
 * CountDownLatch 使一组（个）线程等待一组事件的发生
 * countDown 方法表示已经有一个事件发生
 * await 方法等待计数器到达 0（表示需要等待的事件都已发生），如果计数器的值非零，await 方法会一直阻塞
 * CountDownLatch 的计数器是不能循环利用的，也就是说一旦计数器减到 0，再有线程调用 await()，该线程会直接通过
 *
 * @author: Zhoust
 * @date: 2020/01/16 下午2:21
 * @version: V1.0
 */
@Slf4j
public class CountDownLatchDemo {

    private CountDownLatch startCountDown = new CountDownLatch(1);

    private CountDownLatch endCountDown;

    public CountDownLatchDemo(int threadNum) {
        endCountDown = new CountDownLatch(threadNum);
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Task(i, countDownLatchDemo.startCountDown, countDownLatchDemo.endCountDown)).start();
        }
        log.info("开始执行...");
        //startCountDown.countDown() 使得 startCountDown 数量是 0
        countDownLatchDemo.startCountDown.countDown();
        countDownLatchDemo.endCountDown.await();
        log.info("执行结束");
    }

    static class Task implements Runnable {

        private Integer number;

        private CountDownLatch start;

        private CountDownLatch end;

        public Task(Integer number, CountDownLatch start, CountDownLatch end) {
            this.number = number;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            try {
                //等待 start 数量变成 0
                this.start.await();
                log.info("[{}] number is {}", Thread.currentThread(), this.number);
                this.end.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}