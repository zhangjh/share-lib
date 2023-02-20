package me.zhangjh.share.util;

import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author njhxzhangjihong@126.com
 * @date 4:52 PM 2023/2/20
 * @Description
 */
public class PropertyUtil {
    private static ConfigurableEnvironment environment;

    public static void initEnvironment(ConfigurableEnvironment env){
        environment = env;
    }

    public static String getProperty(String key){
        return environment.getProperty(key, "");
    }

    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

}
