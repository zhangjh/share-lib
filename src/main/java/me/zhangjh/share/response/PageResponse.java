package me.zhangjh.share.response;

import lombok.Data;
import me.zhangjh.share.constant.ErrorEnum;

import java.util.List;

/**
 * @author njhxzhangjihong@126.com
 * @date 4:32 PM 2023/2/20
 * @Description
 */
@Data
public class PageResponse<T> extends BaseResponse {

    private List<T> data;

    private Integer total;

    public static<T> PageResponse<T> success(List<T> data) {
        PageResponse<T> response = new PageResponse<>();
        response.setData(data);
        return response;
    }

    public static<T> PageResponse<T> fail(String errorMsg) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(false);
        response.setErrorMsg(errorMsg);
        return response;
    }

    public static<T> PageResponse<T> fail(String errorCode, String errorMsg) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setErrorMsg(errorMsg);
        return response;
    }

    public static <T> PageResponse<T> fail(ErrorEnum errorEnum) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(false);
        response.setErrorCode(errorEnum.getCode());
        response.setErrorMsg(errorEnum.getMsg());
        return response;
    }
}
