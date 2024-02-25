package com.leyunone.cloudcloud.bean.enums;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/18 13:38
 */
public enum TmallResultEnum {
    
    SUCCESS("SUCCESS"),

    /**
     * 设备不支持此功能
     */
    DEVICE_NOT_SUPPORT_FUNCTION("device not support"),

    /**
     * 设备已离线
     */
    IOT_DEVICE_OFFLINE("device is offline");

    private final String message;

    TmallResultEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
