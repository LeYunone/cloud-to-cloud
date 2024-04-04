package com.leyunone.cloudcloud.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("m_custom_mapping")
public class CustomMappingDO  {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 映射模板  $标识表示开始执行代码
     */
    private String mappingTemplate;

    /**
     * 第三方功能标识
     */
    private String thirdSignCode;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 语音平台  0：百度
     */
    private ThirdPartyCloudEnum thirdPartyCloud;


}
