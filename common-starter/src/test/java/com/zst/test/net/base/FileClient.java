package com.zst.test.net.base;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 资源上传方法 ClassroomResourceServiceImpl#uploadResource
 * 都是 .zip 压缩文件，最大的 0.7MB
 *
 * 本地定时任务写文件 ScheduleTask#writeLocalFile，从 db 中查到需要保存的数据，其中有文件名、vos 地址，如果文件不存在则从 vos 下载到本地
 * @description:
 * @author: Zhoust
 * @date: 2020/02/19 上午10:50
 * @version: V1.0
 */
@Slf4j
public class FileClient {

    public static void main(String[] args) {
        File file = new File("/Users/zhoust/Desktop/851519_65355170002767898.zip");
        FileInputStream fileInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        //用于读取服务器端返回内容
        BufferedReader responseReader;

        Socket socket = null;
        try {
            socket = new Socket("localhost", 9090);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            responseReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            //先把磁盘中的文件，读进内存
            fileInputStream = new FileInputStream(file);
            objectOutputStream.writeUTF(file.getName());
            objectOutputStream.writeLong(file.length());
            byte[] bytes = new byte[1024];
            int length;
            //这里一定要加 length，最后一次读取没把 bytes 数组写完，并且一定要 flush
            while ((length = fileInputStream.read(bytes)) != -1) {
                objectOutputStream.write(bytes, 0, length);
            }
            //TODO 查资料
            objectOutputStream.flush();

            String lineStr;
            while ((lineStr = responseReader.readLine()) != null) {
                /**
                 * 如果服务器端关闭了与客户端相连接的 Socket，会产生 Connection reset 异常
                 * java.net.SocketException: Connection reset
                 * 	at java.net.SocketInputStream.read(SocketInputStream.java:210)
                 * 	at java.net.SocketInputStream.read(SocketInputStream.java:141)
                 * 	at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
                 * 	at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
                 * 	at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
                 * 	at java.io.InputStreamReader.read(InputStreamReader.java:184)
                 * 	at java.io.BufferedReader.fill(BufferedReader.java:161)
                 * 	at java.io.BufferedReader.readLine(BufferedReader.java:324)
                 * 	at java.io.BufferedReader.readLine(BufferedReader.java:389)
                 * 	at com.zst.test.net.base.FileClient.main(FileClient.java:53)
                 */
                log.info("服务器端返回响应：{}", lineStr);
            }

            //发送方如果不将输出流进行关闭，接收方就会认为输入流没有结束，直到超时
            //效果就是 server 端会一直阻塞在 inputStream.read(bytes) 方法
            //可以先确认此次传输数据的大小
            log.info("Client 端输送完毕");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeInputStream(fileInputStream);
            //Closing this socket will also close the socket's InputStream and OutputStream
            StreamUtil.close(socket);
        }
    }

}