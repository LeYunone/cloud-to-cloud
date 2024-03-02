package com.leyunone.cloudcloud.handler.convert.google;

import com.leyunone.cloudcloud.bean.mapping.GoogleProductMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.handler.convert.AbstractDataConvertHandler;
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
public abstract class AbstractGoogleDataConverterTemplate<R, P> extends AbstractDataConvertHandler<R, P> {

    protected AbstractGoogleDataConverterTemplate(ProductMappingService productMappingService) {
        super(productMappingService);
    }

    protected Map<String, GoogleProductMapping> convertToMapByProductId(List<ProductMapping> productMappings) {
        return productMappings
                .stream()
                .filter(p -> p instanceof GoogleProductMapping)
                .map(p -> (GoogleProductMapping) p)
                .collect(Collectors.toMap(ProductMapping::getProductId, v -> v, (v1, v2) -> v2));
    }

}
