package me.zhangjh.share.util;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author njhxzhangjihong@126.com
 * @date 2:07 PM 2023/2/21
 * @Description
 */
@Data
public class HttpRequest {

    @NotNull
    private String url;

    /**
     * only support get/post request
     */
    private String method = HttpMethod.POST.name();

    private Map<String, String> bizHeaderMap = new HashMap<>();

    /**
     * method: post
     * data body, only support raw data of json format now
     * */
    private String reqData;
}
