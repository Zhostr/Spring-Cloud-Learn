package com.zst.test.net.base;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/19 上午11:11
 * @version: V1.0
 */
@Slf4j
public class FileServerHandler implements Runnable {

    private Socket socket;

    public FileServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //用于读取 socket inputStream 并存为本地文件
        ObjectInputStream objectInputStream = null;
        FileOutputStream fileOutputStream = null;

        BufferedWriter bufferedWriter;

        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //Client 端传输文件之前设置的文件名跟文件大小（byte），文件大小尤为重要，Server 端用来判断何时停止从输入流中读取数据（没有这个字段，read 方法会一直阻塞下去）
            String readUTF = objectInputStream.readUTF();
            long fileLength = objectInputStream.readLong();
            File file = new File("/Users/zhoust/Desktop/test/" + readUTF);
            if (!file.exists()) {
                fileOutputStream = new FileOutputStream(file);

                byte[] bytes = new byte[1024];

                int length;
                long totalWrittenLength = 0;
                log.info("server 端开始写入数据");
                while ((length = objectInputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, length);
                    totalWrittenLength += length;
                    if (totalWrittenLength == fileLength)
                        break;
                }
                fileOutputStream.flush();
                log.info("server 端写入数据完成");
            }

            bufferedWriter.write("SUCCESS");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeOutputStream(fileOutputStream);
            StreamUtil.close(socket);
        }
    }

}