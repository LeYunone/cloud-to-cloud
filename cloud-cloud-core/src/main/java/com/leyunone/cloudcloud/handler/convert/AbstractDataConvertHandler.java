package com.leyunone.cloudcloud.handler.convert;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.bean.mapping.StatusMapping;
import com.leyunone.cloudcloud.handler.factory.ConvertHandlerFactory;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import com.leyunone.cloudcloud.strategy.AbstractStrategyAutoRegisterComponent;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
public abstract class AbstractDataConvertHandler<R, P> implements ConvertHandler<R, P> {

    protected ProductMappingService productMappingService;

    protected AbstractDataConvertHandler(ProductMappingService productMappingService) {
    }

    protected Object valueOf(String value, StatusMapping mapping) {
        /**
         * 需要进行函数转换的属性
         */
        if (ObjectUtil.isNotNull(mapping.getConvertFunction())) {
            ConvertFunctionEnum convertFunctionEnum = mapping.getConvertFunction();
            switch (convertFunctionEnum.order()) {
                case MAPPING:
                    return convertFunctionEnum.convert(value);
                case MAPPING_BEFORE:
                    value = convertFunctionEnum.convert(value).toString();
                    break;
                case MAPPING_AFTER:
                    Object o = this.valueMapping(value, mapping);
                    return convertFunctionEnum.convert(o.toString());
            }
        }
        return this.valueMapping(value, mapping);
    }

    private Object valueMapping(String value, StatusMapping mapping) {
        Object newValue = value;
        if (mapping.isValueOf()) {
            Map<String, Object> valueMappingsMap = mapping
                    .getValueMapping();
            if (CollectionUtil.isNotEmpty(valueMappingsMap)) {
                Object thirdPartyValue = valueMappingsMap.get(value);
                if (ObjectUtil.isNotNull(thirdPartyValue)) {
                    newValue = thirdPartyValue;
                } else {
                    newValue = valueMappingsMap.get("defaultValue").toString();
                }
            }
        }
        return newValue;
    }
}
