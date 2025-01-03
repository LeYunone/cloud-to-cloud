package com.leyunone.cloudcloud.web.bean.vo;

import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductActionMappingVO {

    private String productId;

    private List<ActionMapping> productActions;
    
    private List<String> thirdProductIds;

    private ThirdPartyCloudEnum thirdPartyCloud;

    @Getter
    @Setter
    public static class ActionMapping {
        
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

        private String remark;
    }
    
    @Getter
    @Setter
    public static class ValueMap{
        
        private String key;
        
        private Object value;
    }
}