package com.leyunone.cloudcloud.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/31 9:47
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MyCompanyIotDeviceInfo implements Serializable {

    private String productId;

    /**
     * 设备唯一标识
     */
    private Long deviceId;

    /**
     * 设备型号
     */
    private String modelName;

    /**
     * 设备版本
     */
    private String version;

    private String deviceName;

    /**
     * mac地址
     */
    private String mac;
}
