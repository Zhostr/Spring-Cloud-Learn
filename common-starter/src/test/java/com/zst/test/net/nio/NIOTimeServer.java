package com.zst.test.net.nio;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 一般情况下,我们会在TCP的基础上再封装一层协议,用户长连接的传输.协议的信息包,也分包头和包体两个部分.
 * 包体,主要就是我们要传输的信息.(维持连接的信息包,包体可为空)
 * 包头,一般分为三个部分.
 *      第一部分是信息包的长度(长度一般是指整个信息包的长度);
 *      第二部分是包体信息的类型(在这里指出是否是维持连接包);
 *      第三部分是信息包的序列号,一般情况下,这个序列号要确保在传输过程中唯一标识该信息包.
 * 如果为了安全起见,还可以在包体后添加包尾,包尾数据用于对包体数据的验证
 * 这样,通信双方就可以根据包长来判断一次接收的操作是否结束了.
 *
 * NIO 数据从 Channel 读到 Buffer 里，从 Buffer 写入到 Channel
 * @description:
 * @author: Zhoust
 * @date: 2020/02/20 下午4:23
 * @version: V1.0
 */
@Slf4j
public class NIOTimeServer implements Runnable {

    private Selector selector;

    //与 Selector 配合使用时，Channel 必须处于非阻塞模式下
    //不能将 FileChannel 与 Selector 一起使用，因为 FileChannel 不能切换到非阻塞模式，而套接字通道都可以。
    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    public NIOTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);

            //将 ServerSocketChannel 注册到 Selector 上，并监听 Accept 事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("The time server is start at in port {}", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                //Selects a set of keys whose corresponding channels are ready for I/O operations.
                int readyOperationNum = selector.select(1000);
                if (readyOperationNum == 0)
                    continue;

                //选择准备就绪的 key
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        key.cancel();
                        if (key.channel() != null) {
                            key.channel().close();
                        }
                    }
                    //TODO Selector 不会从 selectedKeys 中移除 SelectionKey 实例，必须自己移除，下次该通道可用时，Selector 会将其继续放在已就绪的 key 中
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * SelectionKey
     * 包含 interest 集合、ready 集合、Channel、Selector、附加对象
     * ready 集合：
     * 1、Accept：一个 ServerSocketChannel 准备好
     * @param key
     * @throws IOException
     */
    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                log.info("key.isAcceptable() == true");
                //接收新请求
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
//                if (sc != null) { 添加了 removeKey 的操作，这里的就不会是 null 了
                    sc.configureBlocking(false);
                    //将新的客户端 Connection 注册到 Selector，监听读操作
                    sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//                }
            }

            if (key.isConnectable()) {
                log.info("key.isConnectable() == true");
            }

            if (key.isWritable()) {
                log.info("key.isWritable() == true");
            }

            if (key.isReadable()) {
                log.info("key.isReadable() == true");
                //读取客户端发来的请求数据
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(readBuffer);

                if (readBytes > 0) {
                    //由写模式 -> 读模式
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    log.info("Time server receive {}", body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD REQUEST";
                    doWrite(socketChannel, currentTime);
                } else if (readBytes < 0) {
                    key.cancel();
                    socketChannel.close();
                } else {
                    ;//读到 0 字节，忽略
                }
            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String response) throws IOException {
        if (StringUtils.isNotEmpty(response)) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }
    }

    public static void main(String[] args) {
        new Thread(new NIOTimeServer(9090), "NIO-TimeServer").start();
    }

}