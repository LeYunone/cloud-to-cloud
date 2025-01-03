package com.leyunone.cloudcloud.web.bean.vo;

import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductFunctionMappingVO {

    private String productId;

    private List<FunctionMapping> productFunctions;
    
    private List<String> thirdProductIds;

    private ThirdPartyCloudEnum thirdPartyCloud;

    @Getter
    @Setter
    public static class FunctionMapping {
        
        private String productId;
        
        private ThirdPartyCloudEnum thirdPartyCloud;
        
        private Integer id;

        private String signCode;

        private String thirdSignCode;
        
        private String thirdActionCode;

        private boolean valueOf;

        private List<ValueMap> valueMapping;

        private ConvertFunctionEnum convertFunction;

        private String capabilityConfigId;

        private List<String> capabilityConfigIds;

        private String remark;
    }
    
    @Getter
    @Setter
    public static class ValueMap{
        
        private String key;
        
        private Object value;
    }
}