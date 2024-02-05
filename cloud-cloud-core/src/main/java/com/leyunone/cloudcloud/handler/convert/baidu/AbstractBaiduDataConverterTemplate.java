package com.leyunone.cloudcloud.handler.convert.baidu;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.cloudcloud.bean.baidu.BaiduAttributes;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.mapping.StatusMapping;
import com.leyunone.cloudcloud.handler.convert.AbstractDataConvertHandler;
import com.leyunone.cloudcloud.handler.factory.ConvertHandlerFactory;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;

import java.util.ArrayList;
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
public abstract class AbstractBaiduDataConverterTemplate<R, P> extends AbstractDataConvertHandler<R, P> {


    protected AbstractBaiduDataConverterTemplate(ConvertHandlerFactory factory, ProductMappingService productMappingService) {
        super(factory, productMappingService);
    }

    protected List<BaiduAttributes> convert(List<StatusMapping> statusMappings, List<DeviceFunctionDTO> status) {
        if (CollectionUtil.isEmpty(statusMappings) || CollectionUtil.isEmpty(status)) {
            return new ArrayList<>();
        }
        Map<String, StatusMapping> functionMappingMap = statusMappings
                .stream()
                .collect(Collectors.toMap(StatusMapping::getSignCode, v -> v, (v1, v2) -> v2));
        return status
                .stream()
                .map(s -> {
                    String signCode = s.getSignCode();
                    StatusMapping statusMapping = functionMappingMap.get(signCode);
                    if (null == statusMapping) {
                        return null;
                    }
                    String value = s.getValue();
                    value = valueOf(value, statusMapping);
                    return BaiduAttributes
                            .builder()
                            .name(statusMapping.getSignCode())
                            .value(value)
                            .timestampOfSample(ObjectUtil.isNotNull(s.getTimestamp()) ? s.getTimestamp() : System.currentTimeMillis())
                            .uncertaintyInMilliseconds(1000)
                            .scale("")
                            .legalValue(statusMapping.getLegalValue())
                            .build();
                })
                .filter(ObjectUtil::isNotNull)
                .collect(Collectors.toList());
    }


}
