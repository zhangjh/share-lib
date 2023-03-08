package me.zhangjh.share.util;

import java.util.Random;

/**
 * @author zhangjh
 * @date 1:42 PM 2023/3/8
 * @Description
 */
public class BizUtil {

    /**
     * 生成n位包含数字、大小写字母的随机字符串
     * */
    public static String generateRandom(int n) {
        Random random = new Random();
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int len = characters.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(characters.charAt(random.nextInt(len)));
        }
        return sb.toString();
    }
}
