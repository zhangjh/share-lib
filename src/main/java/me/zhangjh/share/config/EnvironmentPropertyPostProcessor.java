package me.zhangjh.share.config;

import me.zhangjh.share.util.PropertyUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author njhxzhangjihong@126.com
 * @date 4:50 PM 2023/2/20
 * @Description
 */
public class EnvironmentPropertyPostProcessor implements EnvironmentPostProcessor, Ordered {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        PropertyUtil.initEnvironment(environment);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
