package me.zhangjh.share.response;

import lombok.Data;

/**
 * @author njhxzhangjihong@126.com
 * @date 4:31 PM 2023/2/20
 * @Description
 */
@Data
public class BaseResponse {

    private Boolean success = true;

    private String errorCode;

    private String errorMsg;
}
