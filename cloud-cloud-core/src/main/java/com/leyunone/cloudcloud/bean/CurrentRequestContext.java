package com.leyunone.cloudcloud.bean;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/12 10:15
 */
public class CurrentRequestContext {

    private static final ThreadLocal<Object> current = new ThreadLocal<>();

    public static void remove() {
        current.remove();
    }
}

