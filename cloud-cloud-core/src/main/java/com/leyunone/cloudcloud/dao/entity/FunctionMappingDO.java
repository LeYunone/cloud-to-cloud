package com.leyunone.cloudcloud.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * :)
 *  产品功能属性值
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_function_mapping")
public class FunctionMappingDO {

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
     * 功能标识
     */
    private String signCode;

    /**
     * 语音技能枚举 详细请见代码
     */
    private String thirdSignCode;

    private String thirdActionCode;

    /**
     * 语音平台  0：百度
     */
    private ThirdPartyCloudEnum thirdPartyCloud;

    /**
     * 0：透传 1：需要转换 
     */
    private Boolean valueOf;

    /**
     * {"1":"on","0":"off"}
     */
    private String valueMapping;

    /**
     * 描述
     */
    private String remark;

    /**
     * 取值范围
     */
    private String legalValue;
    
    private ConvertFunctionEnum convertFunction;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String capabilityConfigId;

}
