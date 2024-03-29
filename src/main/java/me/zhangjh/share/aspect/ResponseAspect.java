package me.zhangjh.share.aspect;

import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import me.zhangjh.share.util.PropertyUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

import static me.zhangjh.share.constant.BizConstant.LOGGER_STR;

/**
 * @author njhxzhangjihong@126.com
 * @date 4:04 PM 2023/2/20
 * @Description auto logger request、response & exception
 */
@Aspect
public class ResponseAspect implements MethodInterceptor {

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
    @SneakyThrows
    public Object invoke(MethodInvocation invocation) {
        Object[] arguments = invocation.getArguments();
        // request may contains encoded content
        logger.info(invocation.getMethod().getName() + Arrays.toString(arguments));

        try {
            Object proceed = invocation.proceed();
            logger.info("response: {}", JSONObject.toJSONString(proceed));
            return proceed;
        } catch (Throwable t) {
            logger.error("responseAspect invoke exception:", t);
            Class<?> returnType = invocation.getMethod().getReturnType();
            Constructor<?> constructor = returnType.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();
            Method fail = instance.getClass().getMethod("fail", String.class);

            // biz customize exception
            return fail.invoke(instance, t.getMessage());
        }
    }
}
