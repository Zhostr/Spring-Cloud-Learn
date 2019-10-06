package com.zst.commons.util.builder;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/09/27 16:39
 * @version: V1.0
 */
@ConfigurationProperties("spring.http-client")
public class HttpClientProperties {

    /**
     * TCP 连接建立的超时时间
     * This usually happens if the remote machine does not answer.
     * This means that the server has been shut down, you used the wrong IP/DNS name, wrong port or the network connection to the server is down.
     */
    private static final long DEFAULT_CONNECTION_TIMEOUT = 500;

    /**
     * socket 的超时时间（建立连接后，获取响应的超时时间）
     */
    private static final long DEFAULT_SOCKET_TIMEOUT = 1000;

    /**
     * 从连接池中获取连接的超时时间
     */
    private static final long DEFAULT_CONNECTION_REQUEST_TIMEOUT = 1000;

    /** 最大连接数 **/
    private static final int DEFAULT_MAX_CONNECTIONS = 1024;

    /**
     * 连接池中的连接被保活的时长
     */
    private static final long DEFAULT_KEEP_ALIVE_TIME = 6000;

    /**
     * 请求异常时，重试的次数（默认为 0，不重试）
     */
    private static final int DEFAULT_HTTP_RETRY_TIMES = 0;

    /** 是否重试 **/
    private static final boolean DEFAULT_HTTP_RETRY_ON_FAILURE = false;

    private long connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;

    private long socketTimeout = DEFAULT_SOCKET_TIMEOUT;

    private long connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT;

    private int maxConnections = DEFAULT_MAX_CONNECTIONS;

    private long keepAliveTime = DEFAULT_KEEP_ALIVE_TIME;

    private int httpRetryTimes = DEFAULT_HTTP_RETRY_TIMES;

    private boolean httpRetryOnFailure = DEFAULT_HTTP_RETRY_ON_FAILURE;

    public Long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Long getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(long socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Long getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(long connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public int getHttpRetryTimes() {
        return httpRetryTimes;
    }

    public void setHttpRetryTimes(int httpRetryTimes) {
        this.httpRetryTimes = httpRetryTimes;
    }

    public boolean getHttpRetryOnFailure() {
        return httpRetryOnFailure;
    }

    public void setHttpRetryOnFailure(boolean httpRetryOnFailure) {
        this.httpRetryOnFailure = httpRetryOnFailure;
    }

}