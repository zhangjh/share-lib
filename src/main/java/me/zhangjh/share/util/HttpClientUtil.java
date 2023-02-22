package me.zhangjh.share.util;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author njhxzhangjihong@126.com
 * @date 2:04 PM 2023/2/21
 * @Description
 */
@Slf4j
public class HttpClientUtil {

    private static final RequestConfig DEFAULT_REQUEST_CONFIG = RequestConfig.custom()
            .setConnectTimeout(5000)
            // there may be many content returned, set long socket time out
            .setSocketTimeout(500000)
            .setConnectionRequestTimeout(5000)
            .build();

    private static RequestConfig requestConfig = DEFAULT_REQUEST_CONFIG;

    /**
     * set specific config by user
     * */
    public static void setRequestConfig(RequestConfig config) {
        requestConfig = config;
    }

    /** 10 connections per domain
     *  100 total
     */
    private static final CloseableHttpClient HTTPCLIENT = HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .setMaxConnPerRoute(10)
            .setMaxConnTotal(100)
            .build();

    public static String sendHttp(HttpRequest request) {
        StringBuilder url = new StringBuilder(request.getUrl());
        String method = request.getMethod();
        String body = request.getBody();
        Map<String, String> headerMap = request.getHeaderMap();
        if(CollectionUtils.isEmpty(headerMap)) {
            headerMap = new HashMap<>();
        }

        switch (method.toLowerCase()) {
            case "get":
                if(StringUtils.isNotEmpty(body)) {
                    url.append("?");
                    Map<String, String> params = JSONObject.parseObject(body, Map.class);
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                    }
                    url.deleteCharAt(url.length() -1);
                }
                HttpGet httpGet = new HttpGet(url.toString());
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
                return sendHttp(httpGet);
            case "post":
                HttpPost httpPost = new HttpPost(url.toString());
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
                StringEntity entity = new StringEntity(body,
                        ContentType.create(ContentType.APPLICATION_JSON.getMimeType(),
                                Charset.defaultCharset()));
                httpPost.setEntity(entity);
                return sendHttp(httpPost);
            default:
                throw new RuntimeException("Not support method:" + method);
        }
    }

    /**
     * get request and no body data
     */
    public static String sendHttp(String url) {
        HttpRequest request = new HttpRequest();
        request.setUrl(url);
        request.setMethod("get");
        return sendHttp(request);
    }

    private static String sendHttp(HttpRequestBase httpRequest) {
        CloseableHttpResponse response = null;
        try {
            response = HTTPCLIENT.execute(httpRequest);
            log.info("http request: {}, response{}", httpRequest, response);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity());
            }
            throw new HttpResponseException(response.getStatusLine().getStatusCode(),
                    response.getStatusLine().getReasonPhrase());
        } catch (Throwable t) {
            log.error("sendHttp exception");
            throw new RuntimeException(t);
        } finally {
            if(null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ignored) {
                }
            }
        }
    }
}
