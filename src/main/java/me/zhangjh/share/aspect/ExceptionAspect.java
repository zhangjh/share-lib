package me.zhangjh.share.aspect;

import me.zhangjh.share.constant.ErrorEnum;
import me.zhangjh.share.exception.BizException;
import me.zhangjh.share.response.Response;
import me.zhangjh.share.util.PropertyUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static me.zhangjh.share.constant.BizConstant.LOGGER_STR;

/**
 * @author njhxzhangjihong@126.com
 * @date 4:04 PM 2023/2/20
 * @Description
 */
@Aspect
public class ExceptionAspect implements MethodInterceptor {

    private final Logger logger;

    {
        // 某些应用会将错误日志和普通业务日志区分开打印，这里也提供这样的特性，如果不传的话默认会传类名，在日志打印上会使用应用配置的logback的默认appender
        String loggerStr = PropertyUtil.getProperty(LOGGER_STR);
        if(StringUtils.isEmpty(loggerStr)) {
            loggerStr = this.getClass().getName();
        }
        this.logger = LoggerFactory.getLogger(loggerStr);
    }


    @Override
    public Object invoke(MethodInvocation invocation) {
        Object[] arguments = invocation.getArguments();
        logger.info(Arrays.toString(arguments));

        try {
            Object data = invocation.proceed();
            return Response.success(data);
        } catch (Throwable t) {
            logger.error(t.getMessage());
            if(t.getCause() instanceof BizException) {
                return Response.fail(ErrorEnum.BIZ_EX);
            }
            if(t.getCause() instanceof RuntimeException) {
                return Response.fail(ErrorEnum.RUNTIME_EX);
            }
            return Response.fail(ErrorEnum.UNKNOW_EX);
        }
    }
}