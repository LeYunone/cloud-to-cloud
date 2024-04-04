package com.leyunone.cloudcloud.web.bean.dto;

import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeyunOne
 * @Date 2024/3/26 15:14
 */
@Getter
@Setter
public class ProductActionDTO {

    private String productId;

    private List<FunctionMapping> productActions;

    private ThirdPartyCloudEnum thirdPartyCloud;

    @Getter
    @Setter
    public static class FunctionMapping {

        private String productId;

        private ThirdPartyCloudEnum thirdPartyCloud;

        private Integer id;

        private String signCode;

        private Integer functionId;

        private String thirdSignCode;

        private String thirdActionCode;

        private Integer valueOf;

        private List<ValueMap> valueMapping;

        private ConvertFunctionEnum convertFunction;
    }

    @Getter
    @Setter
    public static class ValueMap {

        private String key;

        private Object value;
    }
}
