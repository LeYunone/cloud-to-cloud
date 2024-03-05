package com.leyunone.cloudcloud.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 产品类型映射关系
 * </p>
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_product_type_mapping")
public class ProductTypeMappingDO implements Serializable {

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
     * 第三方产品名
     */
    private String thirdProduct;

    /**
     * 第三方产品名 - 第二形式
     */
    private String thirdProduct2;

    /**
     * 第三方产品id
     */
    private String thirdProductId;

    /**
     * 第三方品牌
     */
    private String thirdBrand;
    
    /**
     * 语音平台
     */
    private ThirdPartyCloudEnum thirdPartyCloud;


}
