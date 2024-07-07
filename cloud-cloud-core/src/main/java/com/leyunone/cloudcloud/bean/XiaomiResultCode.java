package com.leyunone.cloudcloud.bean;


/**
 *
 * @author LeYunone
 * @date 2023-12-27 16:12:48
**/
public enum XiaomiResultCode {

    /**
     * 成功
     */
    SUCCESS(0, "success"),

    /**
     * 设备接受到指令，但未执行完成。类似 (HTTP/1.1 201 Accept)
     */
    ACCEPT(1, "Accept"),

    /**
     * 设备不存在
     */
    DEVICE_NOT_FOUND(-1, "device not found"),

    /**
     * 服务不存在
     */
    SERVICE_NOT_FOUND(-2, "service not found"),

    /**
     * 属性不存在
     */
    PROPERTY_NOT_FOUND(-3, "property not found"),

    /**
     * 设备离线
     */
    DEVICE_OFF_LINE(-18, "device off line"),


    ;

    private final Integer code;

    private final String message;
    

    XiaomiResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
