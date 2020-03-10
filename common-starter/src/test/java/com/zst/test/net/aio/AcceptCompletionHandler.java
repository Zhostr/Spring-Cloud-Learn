package com.zst.test.net.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/21 下午4:44
 * @version: V1.0
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        attachment.assChannel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {

    }

}