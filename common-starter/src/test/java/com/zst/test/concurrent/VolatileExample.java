package com.zst.test.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/01/31 上午10:41
 * @version: V1.0
 */
@Slf4j
public class VolatileExample {

    private int x = 0;

    private boolean v = false;

    public void writer() {
        x = 42;
        v = true;
    }

    public void reader() {
        while (true) {
            if (v == true) {
                log.info("x = {}", x);
                break;
            }
        }
    }

    public static void main(String[] args) {
        VolatileExample volatileExample = new VolatileExample();
        Thread thread1 = new Thread(() -> volatileExample.writer());
        Thread thread2 = new Thread(() -> volatileExample.reader());

        thread2.start();
        thread1.start();

    }

}