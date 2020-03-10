package com.zst.test.net.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/20 下午10:38
 * @version: V1.0
 */
@Slf4j
public class TimeClientHandler implements Runnable {

    private String host;

    private Integer port;

    private Selector selector;

    private SocketChannel socketChannel;

    private volatile boolean stop;

    public TimeClientHandler(String host, Integer port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (selector != null) {
            try {
                //会自动注销与此 Selector 关联的 Channel，以及释放与此多路复用器关联的资源
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                //处于连接状态，说明服务器端已经返回 ACK，要对连接结果进行判断
                if (socketChannel.finishConnect()) {
                    //表示连接成功，将 SocketChannel 注册到 Selector 上，监听网络读操作
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    //发消息给服务端
                    doWrite(socketChannel);
                }
                else {
                    System.exit(1);
                }
            }
            if (key.isReadable()) {
                //接收消息
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    log.info("Now is {}", body);
                    stop = true;
                }
                else if (readBytes < 0) {
                    key.cancel();
                    socketChannel.close();
                }
                else {
                    //读到 0 字节，忽略
                    ;
                }
            }
        }
    }

    private void doConnect() throws IOException {
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            //将 SocketChannel 注册到多路复用器 Selector 上
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        }
        else {
            //没有连接成功，说明服务器端没有返回 TCP 握手应答消息，但不代表连接失败
            //需要将 SocketChannel 注册到 OP_CONNECTION 上，当服务器端返回 syn-ack 后，Selector 就能轮训到这个 SocketChannel 处于连接就绪状态
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel sc) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            log.info("Send order to server success!");
        }
    }

    public static void main(String[] args) {
        new Thread(new TimeClientHandler("localhost", 9090)).start();
//        new Thread(new TimeClientHandler("localhost", 9090)).start();
//        new Thread(new TimeClientHandler("localhost", 9090)).start();
//        new Thread(new TimeClientHandler("localhost", 9090)).start();
//        new Thread(new TimeClientHandler("localhost", 9090)).start();
    }

}