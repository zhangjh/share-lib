package me.zhangjh.share.exception;

/**
 * @author njhxzhangjihong@126.com
 * @date 4:27 PM 2023/2/20
 * @Description
 */
public class BizException extends RuntimeException {

    public BizException(String error) {
        super(error);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}
