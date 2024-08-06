package com.leyunone.cloudcloud.api;

import lombok.*;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/27 17:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceShadowModel extends SceneModel {

    private Long deviceId;

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
