package com.leyunone.cloudcloud.bean.third.yingshi;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/8/29 9:24
 */

public class YingshiResponse {

    private String code = "200";

    public String getCode() {
        return code;
    }

    public YingshiResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public YingshiResponse(String code) {
        this.code = code;
    }
}
