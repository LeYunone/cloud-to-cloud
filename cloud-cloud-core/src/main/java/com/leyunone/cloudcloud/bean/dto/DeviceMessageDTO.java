package com.leyunone.cloudcloud.bean.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-17
 */
@Getter
@Setter
@ToString
public class DeviceMessageDTO {
    
    private String productId;
    
    private String deviceId;
    
    private Long timestamp;

    private boolean online;

    private List<DeviceFunctionDTO> deviceFunctions;

}
