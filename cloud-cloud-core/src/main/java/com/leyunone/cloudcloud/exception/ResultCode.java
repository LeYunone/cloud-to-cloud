package com.leyunone.cloudcloud.exception;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/20 15:42
 */
public enum ResultCode {

    SUCCESS(200,"request is success"),

    ERROR(500,"system fault"),

    FORBIDDEN(403, "forbidden access");

    private Integer code;

    private String desc;

    ResultCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
