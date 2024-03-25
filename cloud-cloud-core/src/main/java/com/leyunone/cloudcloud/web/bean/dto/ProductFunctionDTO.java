package com.leyunone.cloudcloud.web.bean.dto;

import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2024/3/26 15:14
 */
@Getter
@Setter
public class ProductFunctionDTO {

    private String productId;

    private List<FunctionMapping> functionMappings;

    private ThirdPartyCloudEnum thirdPartyCloud;

    @Getter
    @Setter
    public static class FunctionMapping {
        
        private String productId;
        
        private ThirdPartyCloudEnum thirdPartyCloud;
        
        private Integer id;

        private String signCode;

        private String functionId;

        private String thirdPartyCode;

        private Integer valueOf;

        private String valueMapping;

        private ConvertFunctionEnum convertFunction;

        private Integer capabilityConfigId;

        private String remark;
    }
}
