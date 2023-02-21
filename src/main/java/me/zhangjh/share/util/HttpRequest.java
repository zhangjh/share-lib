package me.zhangjh.share.util;

import lombok.Data;

import java.util.Map;

/**
 * @author njhxzhangjihong@126.com
 * @date 2:07 PM 2023/2/21
 * @Description
 */
@Data
public class HttpRequest {

    private String url;

    /**
     * only support get/post request
     */
    private String method;

    private Map<String, String> headerMap;

    /**
     * method: post
     * data body, only support raw data of json format now
     * */
    private String body;
}
