package com.leyunone.cloudcloud.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 第三平台能力映射表
 * </p>
 *
 * @Author leyunone
 * @Date 2024/1/31 11:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("d_device_capability")
public class DeviceCapabilityDO  {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    private ThirdPartyCloudEnum thirdPartyCloud;

    /**
     * 实例名
     */
    private String instanceName;

    /**
     * 值语义
     */
    private String valueSemantics;


    private String description;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
