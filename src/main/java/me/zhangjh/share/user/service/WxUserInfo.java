package me.zhangjh.share.user.service;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author njhxzhangjihong@126.com
 * @date 15:19 2024/5/17
 * @Description
 */
@Data
public class WxUserInfo {

    @JSONField(name = "open_id")
    private String openId;

    @JSONField(name = "session_key")
    private String sessionKey;
}
