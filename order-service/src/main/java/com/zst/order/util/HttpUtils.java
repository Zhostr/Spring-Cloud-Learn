package com.zst.order.util;

import com.google.common.base.Stopwatch;
import com.zst.commons.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.apache.http.Consts.UTF_8;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.apache.http.entity.ContentType.MULTIPART_FORM_DATA;

/**
 * @description: HTTP 请求客户端
 * @author: Zhoust
 * @date: 2019/05/07 16:08
 * @version: V1.0
 */
@Slf4j
@Component
public class HttpUtils {

    private static final String authorization = "Authorization";
    private static final String contentType = "Content-Type";
    private static final String applicationJson = "application/json";

    @Value("${http.max.total:20}")
    private Integer httpMaxTotal;

    @Value("${http.max.preRoute:5}")
    private Integer httpMaxPerRoute;

    // 设置连接池
    private static PoolingHttpClientConnectionManager poolConnManager = null;

    @PostConstruct
    public void init() {
        try {
            log.info("初始化HttpClient连接池");
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry =
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", new PlainConnectionSocketFactory())
                            .register("https", new SSLConnectionSocketFactory(SSLContext.getDefault())).build();
            // 初始化连接管理器
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 同时最多连接数
            poolConnManager.setMaxTotal(httpMaxTotal);
            // 设置单路由最大连接数
            poolConnManager.setDefaultMaxPerRoute(httpMaxPerRoute);
            log.info("初始化HttpClient连接池完成");
        } catch (Exception e) {
            log.error("初始化HttpClient连接池异常",e);
        }
    }

    /** 静态请求配置
     *  设置连接超时和读取数据超时时间为 3s
     * **/
    private static RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setConnectionRequestTimeout(1000);

    /**
     * get 请求
     * @param url       url 包含参数
     * @return
     */
    public static String get(String url) {
        return send(url, HttpMethod.GET, null, null, null);
    }

    /**
     *
     * @param url   url不包含参数，param为参数
     * @param param
     * @return
     */
    public static String get(String url,Map<String,Object> param){
        return get(url.concat("?").concat(getParameter(param)));

    }

    /**
     * 带 auth 头的 Get 请求
     * @param url       url 包含参数
     * @param auth
     * @return
     */
    public static String getWithAuth(String url, String auth) {
        Header[] headers = {new BasicHeader(authorization, auth)};
        return send(url, HttpMethod.GET, null, headers, null);
    }

    /**
     * post form 请求
     * @param url       url 不包含参数
     * @param map
     * @return
     */
    public static String postForm(String url, Map<String, Object> map) {
        return send(url, HttpMethod.POST, map, null, MULTIPART_FORM_DATA);
    }

    /**
     * 带 auth 头的 post 请求
     * @param url
     * @param map
     * @param auth
     * @return
     */
    public static String postFormWithAuth(String url, Map<String, Object> map, String auth) {
        Header[] headers = {new BasicHeader(authorization, auth)};
        return send(url, HttpMethod.POST, map, headers, MULTIPART_FORM_DATA);
    }

    /**
     * post json 请求
     * @param url
     * @param jsonObject
     * @return
     */
    public static String postJson(String url, Object jsonObject) {
        Header[] headers = {new BasicHeader(contentType, applicationJson)};
        Map<String, Object> params = new HashMap<>(1);
        params.put("json", jsonObject);
        return send(url, HttpMethod.POST, params, headers, APPLICATION_JSON);
    }

    /**
     * post json 请求
     * @param url
     * @param jsonObject
     * @param headerMap     Header 内容
     * @return
     */
    public static String postJsonWithHeaders(String url, Object jsonObject, Map<String, String> headerMap) {
        Header[] headers;
        if (headerMap != null) {
            headers = new Header[headerMap.size() + 1];
            int index = 0;
            for (String headerName : headerMap.keySet()) {
                headers[index] = new BasicHeader(headerName, headerMap.get(headerName));
                index++;
            }
        }
        else {
            headers = new Header[1];
        }
        headers[headers.length-1] = new BasicHeader(contentType, applicationJson);
        Map<String, Object> params = new HashMap<>(1);
        params.put("json", jsonObject);
        return send(url, HttpMethod.POST, params, headers, APPLICATION_JSON);
    }

    /**
     * 带 auth 头的 post json 类型请求
     * @param url
     * @param jsonObject
     * @return
     */
    public static String postJsonWithAuth(String url, Object jsonObject, String auth) {
        Header[] headers = new Header[2];
        headers[0] = new BasicHeader(contentType, applicationJson);
        headers[1] = new BasicHeader(authorization, auth);
        Map<String, Object> params = new HashMap<>(1);
        params.put("json", jsonObject);
        return send(url, HttpMethod.POST, params, headers, APPLICATION_JSON);
    }

    /**
     * 核心方法，发送请求并处理响应结果
     * @param url               Get 请求包含参数，post 请求不包含参数（都放在 params 中）
     * @param httpMethod
     * @param params            对 post json 请求，将 json 内容放到了 Map 中，key = json
     * @param headers
     * @param contentType
     * @return
     */
    public static String send(final String url, HttpMethod httpMethod, Map<String, Object> params, Header[] headers, ContentType contentType) {
        try {
            Assert.notNull(url, "url must not be null!");
            RequestConfig requestConfig = requestConfigBuilder.setConnectTimeout(3000).setSocketTimeout(3000).build();

            String urlPrefix = "";

            //log.info("HTTP调用 url = \"{}\",urlPrefix = {},httpMethod = {},socketTimeout = {},connectTimeout = {}",
                    //url,urlPrefix,httpMethod.name(),requestConfig.getConnectTimeout(),requestConfig.getSocketTimeout());

            CloseableHttpClient closeableHttpClient = HttpClients.custom()
                    .setConnectionManager(poolConnManager)
                    .setDefaultRequestConfig(requestConfig).build();
            HttpRequestBase request = getRequestByHttpMethod(url, httpMethod);
            String param = null;

            //设置请求头
            if (headers != null && headers.length != 0) {
                request.setHeaders(headers);
            }
            //设置请求体
            if (request instanceof HttpEntityEnclosingRequestBase) {
                AbstractHttpEntity abstractHttpEntity = null;
                if (contentType.equals(MULTIPART_FORM_DATA)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    //form 表单提交
                    List<NameValuePair> nameValuePairList = new ArrayList<>(params.size());
                    params.forEach((k,v)->{
                        nameValuePairList.add(new BasicNameValuePair(k, v.toString()));
                        stringBuilder.append(k).append("=").append(v.toString()).append(",");
                    });
                    param = stringBuilder.toString().substring(0, stringBuilder.length()-1);
                    abstractHttpEntity = new UrlEncodedFormEntity(nameValuePairList, UTF_8);
                }
                else if (contentType.equals(APPLICATION_JSON)) {
                    //json 方式提交
                    param = JsonUtil.toJson(params.get("json"));
                    abstractHttpEntity = new StringEntity(param, UTF_8);
                }
                ((HttpEntityEnclosingRequestBase) request).setEntity(abstractHttpEntity);
            }

            //param 为 null，表示是 Get 请求，从 url 中提取参数
            if (param == null) {
                int index = url.indexOf("?");
                if (index != -1) {
                    param = url.substring(index+1);
                }
                else {
                    param = "";
                }
            }
            return execute(url, closeableHttpClient, request, param);
        }
        catch (Exception e) {
            log.error("Something went wrong!", e);
            throw e;
        }
    }

    /**
     * 执行请求、打印、返回响应内容并释放资源
     * @param closeableHttpClient
     * @param requestBase
     * @param param
     * @return
     */
    private static String execute(String url, CloseableHttpClient closeableHttpClient, HttpRequestBase requestBase, String param) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String result = "";
        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(requestBase);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, UTF_8);
            }
            StatusLine statusLine = response.getStatusLine();
            int responseStatusCode = statusLine.getStatusCode();
            String reasonPhrase = statusLine.getReasonPhrase();
            if (!isRightResponse(responseStatusCode)) {
                throw new IllegalArgumentException(" Response Code Error! [" + responseStatusCode + " " + reasonPhrase + "]");
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error("request " + url + e.getMessage(), e);
        } finally {
            long millis = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            //log.info("request url={}, param={}, time={}ms, result={}", url, param, millis, result);
//            HttpClientUtils.closeQuietly(closeableHttpClient);
            HttpClientUtils.closeQuietly(response);
        }
        return result;
    }

    /**
     * 响应码是否正确
     * 2xx、3xx 为正常
     * @param responseCode
     * @return
     */
    private static Boolean isRightResponse(int responseCode) {
        int firstNumber = responseCode/100;
        switch (firstNumber) {
            case 2:
                return true;
            case 3:
                return true;
            default:
                return false;
        }
    }

    /**
     * 根据 HTTP 请求类型，生成对应的请求对象
     * @param url
     * @param httpMethod
     * @return
     */
    private static HttpRequestBase getRequestByHttpMethod(String url, HttpMethod httpMethod) {
        switch (httpMethod) {
            case GET:
                return new HttpGet(url);
            case POST:
                return new HttpPost(url);
            default:
                return new HttpPost(url);
        }
    }

    /**
     * 由 map 对象拼接为 url 后面的参数，用于 post 原生表单提交或者 get 请求参数的拼接
     * @param map
     * @return
     */
    public static String map2String(Map<String, Object> map) {
        if (map == null || !map.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("?");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String s = stringBuilder.toString();
        return s.substring(0, s.length()-1);
    }


    public static String getParameter(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        if (map == null || map.isEmpty()) {
            return sb.toString();
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (StringUtils.isNotEmpty(entry.getKey())) {
                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        if (sb.length() > 0) {
            return sb.substring(1);
        }
        return sb.toString();

    }

}