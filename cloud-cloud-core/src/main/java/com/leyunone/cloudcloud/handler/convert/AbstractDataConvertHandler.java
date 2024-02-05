package com.leyunone.cloudcloud.handler.convert;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.mapping.StatusMapping;
import com.leyunone.cloudcloud.handler.factory.ConvertHandlerFactory;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import com.leyunone.cloudcloud.strategy.AbstractStrategyAutoRegisterComponent;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
public abstract class AbstractDataConvertHandler<R,P> extends AbstractStrategyAutoRegisterComponent implements ConvertHandler<R,P> {
    
    protected ProductMappingService productMappingService;
    
    protected AbstractDataConvertHandler(ConvertHandlerFactory factory, ProductMappingService productMappingService) {
        super(factory);
    }

    protected String valueOf(String value, StatusMapping mapping){
        String newValue = value;
        if (mapping.isValueOf()) {
            Map<String, Object> valueMappingsMap = mapping
                    .getValueMapping();
            if(CollectionUtil.isNotEmpty(valueMappingsMap)){
                String thirdPartyValue = valueMappingsMap.get(value).toString();
                if (!StringUtils.isEmpty(thirdPartyValue)) {
                    newValue = thirdPartyValue;
                }
            }
        }
        return newValue;
    }
}
