package com.leyunone.cloudcloud.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
     * 产品id
     */
    private String productId;

    /**
     * 能力
     */
    private String capability;

    /**
     * 资源配置
     */
    private String capabilityConfiguration;

    /**
     * 语音平台  
     */
    private String thirdPartyCloud;

    /**
     * 赋予能力接口欧
     */
    private String thirdActionCode;

    /**
     * 实例名
     */
    private String instanceName;

}
