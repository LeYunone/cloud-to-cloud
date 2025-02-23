package com.leyunone.cloudcloud.handler.convert.alexa;

import com.leyunone.cloudcloud.handler.convert.AbstractDataConvertHandler;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/31 10:53
 */
public abstract class AbstractAlexaDataConverterTemplate<R, P> extends AbstractDataConvertHandler<R, P> {

    public AbstractAlexaDataConverterTemplate(ProductMappingService productMappingManager) {
        super( productMappingManager);
    }

}
