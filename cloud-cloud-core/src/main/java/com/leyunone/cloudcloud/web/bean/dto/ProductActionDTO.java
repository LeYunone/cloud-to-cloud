package com.leyunone.cloudcloud.web.bean.dto;

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

        private String thirdSignCode;

        private String thirdActionCode;

        private boolean valueOf;

        private List<ValueMap> valueMapping;

        private String convertFunction;
    }

    @Getter
    @Setter
    public static class ValueMap {

        private String key;

        private Object value;
    }
}
