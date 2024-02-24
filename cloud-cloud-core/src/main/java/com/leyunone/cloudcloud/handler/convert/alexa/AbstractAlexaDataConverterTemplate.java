package com.leyunone.cloudcloud.handler.convert.alexa;

import com.leyunone.cloudcloud.bean.mapping.AlexaProductMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.handler.convert.AbstractDataConvertHandler;
import com.leyunone.cloudcloud.handler.factory.ConvertHandlerFactory;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/31 10:53
 */
public abstract class AbstractAlexaDataConverterTemplate<R, P> extends AbstractDataConvertHandler<R, P> {

    public AbstractAlexaDataConverterTemplate(ConvertHandlerFactory strategyFactory, ProductMappingService productMappingManager) {
        super(strategyFactory, productMappingManager);
    }

    protected Map<String, AlexaProductMapping> convertToMapByProductId(List<ProductMapping> productMappings) {
        return productMappings
                .stream()
                .filter(p -> p instanceof AlexaProductMapping)
                .map(p -> (AlexaProductMapping) p)
                .collect(Collectors.toMap(ProductMapping::getProductId, v -> v, (v1, v2) -> v2));
    }

}
