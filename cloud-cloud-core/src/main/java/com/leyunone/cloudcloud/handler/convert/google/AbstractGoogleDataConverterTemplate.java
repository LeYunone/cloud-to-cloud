package com.leyunone.cloudcloud.handler.convert.google;

import com.leyunone.cloudcloud.handler.convert.AbstractDataConvertHandler;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;

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

}
