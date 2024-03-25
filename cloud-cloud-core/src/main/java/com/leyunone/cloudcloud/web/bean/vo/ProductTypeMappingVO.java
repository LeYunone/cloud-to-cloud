package com.leyunone.cloudcloud.web.bean.vo;

import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 产品类型映射关系(MProductTypeMapping)出参
 *
 * @author leyunone
 * @since 2024-03-25 17:51:51
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProductTypeMappingVO implements Serializable {
    private static final long serialVersionUID = 329167320158303817L;
    
    private String productId;

    /**
     * 语音平台
     */
    private ThirdPartyCloudEnum thirdPartyCloud;
    
    private List<ProductType> productTypes;

    @Getter
    @Setter
    public static class ProductType{
        private Integer id;

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
    }
   
}
