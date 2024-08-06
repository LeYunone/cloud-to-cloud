package com.leyunone.cloudcloud.bean.enums;

import java.io.Serializable;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/24 13:26
 */
public enum  ProtocolCommandEnum implements Serializable {

    /**
     * 协议指令
     */
    QUERY("DeviceQuery"),
    CONTROL("DeviceControl"),
    DISCOVERY("DeviceDiscovery"),
    UNBIND("DeviceUnbind"),
    SYNCINFO("DeviceSync")
    
    ;
    
    private String key;

    public String getKey() {
        return key;
    }

    ProtocolCommandEnum(String key) {
        this.key = key;
    }
}
