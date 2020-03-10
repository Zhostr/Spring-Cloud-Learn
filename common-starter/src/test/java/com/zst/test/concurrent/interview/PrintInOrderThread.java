package com.zst.test.concurrent.interview;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 把交替打印奇偶数改一下就行了
 * @description: 多线程按顺序输出 123...100
 * @author: Zhoust
 * @date: 2020/02/02 下午9:02
 * @version: V1.0
 */
@Slf4j
public class PrintInOrderThread {

    /**
     * 两个线程交替打印奇偶数
     * 使用 Lock + Condition 实现
     */
    static class TwoThreadsAlternatelyPrintOddAndEvenNumbersWithLock {

        Lock lock = new ReentrantLock();

        //是否为奇数
        Condition conditionOdd = lock.newCondition();

        //是否为偶数
        Condition conditionEven = lock.newCondition();

        private Integer value = 1;

        public void printOdd() {
            while (true) {
                lock.lock();

                try {
                    if (value > 100) {
                        break;
                    }
                    if (value % 2 != 1) {
                        conditionOdd.await();
                    }
                    log.info("奇线程：{}", value);
                    value ++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    conditionEven.signal();
                    lock.unlock();
                }
            }
        }

        public void printEven() {
            while (true) {
                lock.lock();
                try {
                    if (value > 100) {
                        break;
                    }
                    if (value % 2 != 0) {
                        conditionEven.await();
                    }
                    log.info("偶线程：{}", value);
                    value ++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    conditionOdd.signal();
                    lock.unlock();
                }
            }
        }

        public static void main(String[] args) {
            TwoThreadsAlternatelyPrintOddAndEvenNumbersWithLock demo = new TwoThreadsAlternatelyPrintOddAndEvenNumbersWithLock();

            Thread thread2 = new Thread(() -> demo.printOdd());
            Thread thread1 = new Thread(() -> demo.printEven());
            thread1.start();
            thread2.start();
        }

    }

    /**
     * 使用 synchronized 实现
     */
    static class TwoThreadsAlternatelyPrintOddAndEvenNumbersWithSynchronized {

        private static final Object obj = new Object();

        private Integer value = 1;

        /**
         * @param remainder 余数是 0 打印偶数，余数是 1 打印奇数，因此
         */
        public void print(int remainder) {
            synchronized (obj) {
                while (value <= 100) {
                    while (value % 3 != remainder) {
                        if (value > 100) {
                            break;
                        }
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (value > 100) {
                        obj.notifyAll();
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + " :" + value);
                    value++;
                    obj.notifyAll();
                }
            }
        }

        public static void main(String[] args) {
            TwoThreadsAlternatelyPrintOddAndEvenNumbersWithSynchronized bean = new TwoThreadsAlternatelyPrintOddAndEvenNumbersWithSynchronized();
            Thread thread1 = new Thread(() -> bean.print(1), "Thread1");
            Thread thread2 = new Thread(() -> bean.print(2), "Thread2");
            Thread thread3 = new Thread(() -> bean.print(0), "Thread3");
            thread3.start();
            thread1.start();
            thread2.start();
        }

    }



}