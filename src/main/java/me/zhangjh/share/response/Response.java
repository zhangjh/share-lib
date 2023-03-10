package me.zhangjh.share.response;

import lombok.Data;

/**
 * @author njhxzhangjihong@126.com
 * @date 4:31 PM 2023/2/20
 * @Description
 */
@Data
public class Response<T> extends BaseResponse {
    private T data;

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    public static<T> Response<T> fail(String errorMsg) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorMsg(errorMsg);
        return response;
    }

    public static <T> Response<T> fail(String errorCode,  String errorMsg) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setErrorMsg(errorMsg);
        return response;
    }
}
