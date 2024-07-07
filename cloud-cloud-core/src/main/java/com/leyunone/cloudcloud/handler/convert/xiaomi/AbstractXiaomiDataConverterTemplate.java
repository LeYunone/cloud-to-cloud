package com.leyunone.cloudcloud.handler.convert.xiaomi;

import com.leyunone.cloudcloud.handler.convert.AbstractDataConvertHandler;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;

/**
 * @author LeYunone
 * @date 2023-12-08 16:30:14
 **/
public abstract class AbstractXiaomiDataConverterTemplate<R, P> extends AbstractDataConvertHandler<R, P> {

    protected AbstractXiaomiDataConverterTemplate(ProductMappingService productMappingService) {
        super(productMappingService);
    }
}
