package com.leyunone.cloudcloud.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.enums.OperationEnum;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_action_mapping")
public class ActionMappingDO {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String productId;

    /**
     * 三方参数
     */
    private String thirdSignCode;

    /**
     * 三方行为标识
     */
    private String thirdActionCode;

    private String signCode;

    private String defaultValue;

    private Boolean valueOf;

    private String valueMapping;

    private ThirdPartyCloudEnum thirdPartyCloud;

    private OperationEnum operation;

    private ConvertFunctionEnum convertFunction;
    
    private String remark;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
