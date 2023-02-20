package me.zhangjh.share.constant;

/**
 * @author njhxzhangjihong@126.com
 * @date 4:09 PM 2023/2/20
 * @Description
 */
public class BizConstant {

    public static final String PT_EXPRESSION_CONTROLLER = "@within(org.springframework.web.bind.annotation.RestController) || " +
            "@within(org.springframework.stereotype.Controller)";

    public static final String PT_EXPRESSION_SERVICE = "@within(org.apache.dubbo.config.annotation.DubboService) || " +
            "@within(org.springframework.stereotype.Service)";

    public static final String LOGGER_STR = "biz.logger";
}
