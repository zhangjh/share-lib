package me.zhangjh.share.config;

import me.zhangjh.share.aspect.ExceptionAspect;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static me.zhangjh.share.constant.BizConstant.PT_EXPRESSION_CONTROLLER;
import static me.zhangjh.share.constant.BizConstant.PT_EXPRESSION_SERVICE;

/**
 * @author njhxzhangjihong@126.com
 * @date 4:00 PM 2023/2/20
 * @Description
 */
@Configuration
@EnableAspectJAutoProxy
public class ExceptionConfig {

    @Bean
    @ConditionalOnMissingBean(value = ExceptionAspect.class)
    public DefaultPointcutAdvisor exceptionAdvisor() {
        ExceptionAspect exceptionAspect = new ExceptionAspect();
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // cut the Controller & Service method
        pointcut.setExpression(PT_EXPRESSION_CONTROLLER  + PT_EXPRESSION_SERVICE);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(exceptionAspect);
        return advisor;
    }
}
