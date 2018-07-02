package com.zhaojun.sell.utils;

import java.util.Random;

/**
 * @author zhaojun0193
 * @create 2018/7/1
 */
public class KeyUtil {

    /**
     * 生产唯一的组件
     * 格式： 时间 + 随机数
     * @return
     */
    public static synchronized String genUniquKey(){
        Random random = new Random();

        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
