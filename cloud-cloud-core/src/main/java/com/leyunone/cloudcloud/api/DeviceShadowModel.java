package com.leyunone.cloudcloud.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/27 17:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceShadowModel {

    private String deviceId;

    private String productId;

    private String deviceName;

    private String mac;

    private String version;

    private String modelNumber;

    private Boolean online;

    private List<DeviceFunction> status;

    /**
     * 组名
     */
    private String groupName;
}
