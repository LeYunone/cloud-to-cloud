package com.leyunone.cloudcloud.enums;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public enum BaiduProtocolEnum {

    NAMESPACE_CONTROL("DuerOS.ConnectedHome.Control");

    private String name;

    public String getName() {
        return name;
    }

    BaiduProtocolEnum(String name) {
        this.name = name;
    }
}
