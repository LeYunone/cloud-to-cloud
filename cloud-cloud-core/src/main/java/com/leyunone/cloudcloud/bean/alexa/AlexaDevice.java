package com.leyunone.cloudcloud.bean.alexa;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/26
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AlexaDevice {
    /**
     * 设备id
     */
    private String endpointId;
    /**
     * 设备名
     */
    private String manufacturerName;
    /**
     * 备注
     */
    private String description;
    /**
     * 房间名
     */
    private String friendlyName;
    /**
     * 附加属性
     */
    private Map<String, String> additionalAttributes;
    /**
     * 设备类型
     */
    private List<String> displayCategories;
    /**
     * json字符串
     */
    private String cookie;
    /**
     * 技能配置
     */
    private List<AlexaDeviceCapability> capabilities;
}
