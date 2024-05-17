package me.zhangjh.share.user.service;

import lombok.Data;

/**
 * @author njhxzhangjihong@126.com
 * @date 15:19 2024/5/17
 * @Description
 */
@Data
public class AccountRequest {

    private String userId;

    private Integer extType;

    private String nickName;

    private String avatarUrl;

    /**
     * @link ProductTypeEnum
     * */
    private String productType;
}
