package me.zhangjh.share.constant;

import lombok.Getter;

/**
 * @author njhxzhangjihong@126.com
 * @date 4:38 PM 2023/2/20
 * @Description
 */
@Getter
public enum ErrorEnum {
    BIZ_EX("1001", "biz exception msg placeholder, replace me at service layer"),
    RUNTIME_EX("1002", "runtime exception msg placeholder, replace me at service layer"),
    UNKNOW_EX("1003", "unknow exception msg placeholder, replace it at service layer"),

    ;

    private String code;

    private String msg;

    ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
