package com.zst.test.net.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/21 下午4:05
 * @version: V1.0
 */
@Slf4j
public class AsyncTimeServerHandler implements Runnable {

    private int port;

    private CountDownLatch countDownLatch;

    AsynchronousServerSocketChannel assChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            assChannel = AsynchronousServerSocketChannel.open();
            assChannel.bind(new InetSocketAddress(port));
            log.info("Time server is start in port : {}", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);
        doAccept();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doAccept() {
        assChannel.accept(this, new AcceptCompletionHandler());
    }


}