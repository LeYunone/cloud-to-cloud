package com.leyunone.cloudcloud.bean.info;

import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.sun.istack.internal.NotNull;
import lombok.*;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceInfo {

    private String deviceId;

    @NotNull
    private String productId;

    private String version;
 
    private List<DeviceFunctionDTO> deviceFunctions;

    private String modelName;
 
    private String deviceName;
    
    private boolean online;
    
    private String groupName;
    
}
