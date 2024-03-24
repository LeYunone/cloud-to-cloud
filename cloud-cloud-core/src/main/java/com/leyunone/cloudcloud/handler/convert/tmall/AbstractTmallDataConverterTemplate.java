package com.leyunone.cloudcloud.handler.convert.tmall;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.bean.mapping.StatusMapping;
import com.leyunone.cloudcloud.bean.mapping.TmallProductMapping;
import com.leyunone.cloudcloud.handler.convert.AbstractDataConvertHandler;
import com.leyunone.cloudcloud.handler.factory.ConvertHandlerFactory;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/16 17:20
 */
public abstract class AbstractTmallDataConverterTemplate<R, P> extends AbstractDataConvertHandler<R, P> {

    protected AbstractTmallDataConverterTemplate( ProductMappingService productMappingService) {
        super(productMappingService);
    }

    protected Map<String, TmallProductMapping> convertToMapByProductId(List<ProductMapping> productMappings) {
        return productMappings
                .stream()
                .filter(p -> p instanceof TmallProductMapping)
                .map(p -> (TmallProductMapping) p)
                .collect(Collectors.toMap(ProductMapping::getProductId, v -> v, (v1, v2) -> v2));
    }

    protected Map<String, Object> convert(List<StatusMapping> functionMappings, List<DeviceFunctionDTO> status) {
        if (CollectionUtil.isEmpty(functionMappings) || CollectionUtil.isEmpty(status)) {
            return new HashMap<>();
        }
        Map<String, StatusMapping> statusMappingMap = functionMappings
                .stream()
                .collect(Collectors.toMap(StatusMapping::getSignCode, v -> v, (v1, v2) -> v2));
        return status
                .stream()
                .filter(t -> statusMappingMap.containsKey(t.getSignCode()))
                .collect(Collectors.toMap(t -> {
                    StatusMapping functionMapping = statusMappingMap.get(t.getSignCode());
                    return functionMapping.getSignCode();
                }, DeviceFunctionDTO::getValue));
    }
}
