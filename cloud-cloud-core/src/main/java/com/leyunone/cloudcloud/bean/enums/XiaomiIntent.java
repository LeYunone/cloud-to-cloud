package com.leyunone.cloudcloud.bean.enums;


import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author LeYunone
 * @date 2023-12-21 16:25:15
**/
public enum XiaomiIntent {

    /**
     * 获取设备列表
     */
    GET_DEVICES("get-devices"),

    /**
     * 获取设备属性
     */
    GET_PROPERTIES("get-properties"),

    /**
     * 获取设备状态
     */
    GET_DEVICE_STATUS("get-device-status"),

    /**
     * 设置属性
     */
    SET_PROPERTIES("set-properties"),

    /**
     * 订阅设备数据
     */
    SUBSCRIBE("subscribe"),

    /**
     * 取消订阅
     */
    UNSUBSCRIBE("unsubscribe"),

    /**
     * 设备状态变化通知
     */
    DEVICE_STATUS_CHANGED("device-status-changed"),

    /**
     * 设备属性变化通知
     */
    DEVICE_PROPERTIES_CHANGED("device-properties-changed"),



    ;
    private final String intent;

    XiaomiIntent(String intent) {
        this.intent = intent;
    }

    public String getIntent() {
        return intent;
    }

    private final static Map<String, XiaomiIntent> MAP;

    static {
        MAP = Arrays.stream(XiaomiIntent.values()).collect(Collectors.toMap(XiaomiIntent::getIntent, v -> v));
    }

    public static XiaomiIntent getByIntent(String intent){
        return MAP.get(intent);
    }
}
