package com.leyunone.cloudcloud.handler.convert;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.enums.ActionValueEnum;
import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.bean.mapping.ActionMapping;
import com.leyunone.cloudcloud.bean.mapping.StatusMapping;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;

import java.util.Map;

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
        this.productMappingService = productMappingService;
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
                default:
                    break;
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

    protected Object getControlValue(JSONObject params, ActionMapping actionMapping) {
        if(ObjectUtil.isNull(actionMapping)) return null;
        String command = actionMapping.getThirdActionCode();
        String[] codes = actionMapping.getThirdSignCode().split("#");
        //最终值
        Object value = actionMapping.getDefaultValue();
        for (int i = 0; i < codes.length; i++) {
            if (i == codes.length - 1) {
                value = params.get(codes[i]);
                break;
            }
            params = (JSONObject) params.get(codes[i]);
        }
        ActionValueEnum byEnumName = ActionValueEnum.getByEnumName(command);
        return byEnumName.valueConvert(value, actionMapping);
    }
}
