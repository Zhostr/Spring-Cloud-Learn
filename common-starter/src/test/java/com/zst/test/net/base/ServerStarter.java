package com.zst.test.net.base;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: 同步阻塞式，一个服务器端线程连接一个客户端线程
 * @author: Zhoust
 * @date: 2020/02/18 下午2:08
 * @version: V1.0
 */
@Slf4j
public class ServerStarter {

    public static void main(String[] args) {
        int port = 9090;
        try {
            log.info("Time server is start in port {}", port);
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new FileServerHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
