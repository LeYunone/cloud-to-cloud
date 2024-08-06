package com.leyunone.cloudcloud.api.protocol;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/10 11:40
 */
@Getter
@Setter
public class MyCompanyProtocolDeviceInfo implements Serializable {

    private String productId;
    
    //第三方分组id
    private String groupId;
    
    //第三方家庭id
    private String homeId;

    /**
     * 第三方设备唯一标识
     */
    private String deviceId;

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
    
    private String additionalAppliance;
    
    private List<MyCompanyAttribute> attributes;
    
    //是否授权 false否  true是
    private boolean authorized;
    
    private String myComDeviceId;
    
    private String myComGroupId;
}
