package com.leyunone.cloudcloud.web.bean.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @Author LeyunOne
 * @Date 2024/4/2 9:59
 */
@Getter
@Setter
public class ProductCapabilityDTO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 能力语义
     */
    private String capabilitySemantics;

    /**
     * 资源配置 
     * https://developer.amazon.com/en-US/docs/alexa/device-apis/resources-and-assets.html#capability-resources
     */
    private String capabilityConfiguration;

    /**
     * 语音平台  
     */
    private String thirdPartyCloud;

    /**
     * 实例名
     */
    private String instanceName;

    /**
     * 值语义
     */
    private String valueSemantics;
}
