package com.leyunone.cloudcloud.handler.convert.tmall;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.convert.AbstractDataConvertHandler;
import com.leyunone.cloudcloud.handler.factory.ConvertHandlerFactory;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/18 17:24
 */
@Service
public class TmallStatusConverter extends AbstractTmallDataConverterTemplate<Map<String, Object>, DeviceInfo> {

    private final ProductMappingService productMappingService;

    protected TmallStatusConverter( ProductMappingService productMappingService, ProductMappingService productMappingService1) {
        super( productMappingService);
        this.productMappingService = productMappingService1;
    }

    @Override
    public Map<String, Object> convert(DeviceInfo deviceInfo) {
        List<ProductMapping> mapping = productMappingService.getMapping(deviceInfo.getProductId(), ThirdPartyCloudEnum.TMALL);
        List<DeviceFunctionDTO> deviceFunctions = deviceInfo.getDeviceFunctions();
        Map<String, Object> res = new HashMap<>();
        if (CollectionUtil.isNotEmpty(mapping)) {
            mapping.forEach(m -> {
                res.putAll(convert(m.getStatusMappings(), deviceFunctions));
            });
        }
        return res;
    }
}
