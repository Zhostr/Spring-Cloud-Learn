package com.zst.client.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/10/30 下午9:10
 * @version: V1.0
 */
public class TestHttpClient {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:9090/router");
        for (int i = 0; i < 10; i++) {
            HttpResponse response = closeableHttpClient.execute(httpGet);
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }

}