package me.zhangjh.share.util;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.BufferedSource;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author njhxzhangjihong@126.com
 * @date 2:04 PM 2023/2/21
 * @Description
 */
@Slf4j
public class HttpClientUtil {

    protected static final OkHttpClient OK_HTTP_CLIENT;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10, TimeUnit.MINUTES);
        builder.callTimeout(10, TimeUnit.MINUTES);
        builder.connectTimeout(10, TimeUnit.MINUTES);
        builder.writeTimeout(10, TimeUnit.MINUTES);
        builder.connectionPool(new ConnectionPool(32,
                5,TimeUnit.MINUTES));
        OK_HTTP_CLIENT = builder.build();
    }

    public static Object sendNormally(HttpRequest httpRequest) {
        Request request = buildRequest(httpRequest);
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            return handleResponse(Objects.requireNonNull(response.body()));
        } catch (IOException e) {
            log.error("sendNormally exception, ", e);
            throw new RuntimeException(e);
        }
    }

    public static void sendAsync(HttpRequest httpRequest, Function<Response, Object> responseCb,
                                        Function<Throwable, Object> failCb) {
        Request request = buildRequest(httpRequest);
        Call call = OK_HTTP_CLIENT.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                failCb.apply(e);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                responseCb.apply(response);
            }
        });
    }

    public static Object get(String url) {
        return get(url, new HashMap<>());
    }

    public static Object get(String url, Map<String, String> bizParams) {
        Request.Builder builder = new Request.Builder().get().url(url);
        for (Map.Entry<String, String> entry : bizParams.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        Request request = builder.build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            return handleResponse(Objects.requireNonNull(response.body()));
        } catch (IOException e) {
            log.error("get exception, ", e);
            throw new RuntimeException(e);
        }
    }

    protected static Request buildRequest(HttpRequest httpRequest) {
        // only support application/json
        try {
            JSONObject.parseObject(httpRequest.getReqData());
        } catch (Exception e) {
            throw new IllegalArgumentException("reqData isn't json format");
        }
        RequestBody requestBody = RequestBody.create(httpRequest.getReqData(),
                MediaType.get("application/json"));
        Request.Builder builder = new Request.Builder();
        builder.url(httpRequest.getUrl())
                .method(httpRequest.getMethod(), requestBody);
        for (Map.Entry<String, String> entry : httpRequest.getBizHeaderMap().entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    protected static Object handleResponse(ResponseBody responseBody) {
        StringBuilder sb = new StringBuilder();
        try (responseBody) {
            BufferedSource source = responseBody.source();
            while (!source.exhausted()) {
                String line = source.readUtf8Line();
                if (StringUtils.isNotEmpty(line)) {
                    sb.append(line);
                }
            }
        } catch (IOException e) {
            log.error("handleResponse exception:", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
