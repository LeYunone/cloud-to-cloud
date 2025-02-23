package com.leyunone.cloudcloud.handler.convert.xiaomi;

import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.bean.third.xiaomi.XiaomiDevice;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author LeYunone
 * @date 2023-12-25 16:15:43
 **/
@Service
public class XiaomiDeviceInfoConverter extends AbstractXiaomiDataConverterTemplate<List<XiaomiDevice>, List<DeviceInfo>> {


    protected XiaomiDeviceInfoConverter(ProductMappingService productMappingService) {
        super(productMappingService);
    }

    @Override
    public List<XiaomiDevice> convert(List<DeviceInfo> r) {
        List<String> pids = r.stream().map(DeviceInfo::getProductId).distinct().collect(Collectors.toList());
        List<ProductMapping> productMappings = productMappingService.getMapping(pids, ThirdPartyCloudEnum.XIAOMI);
        Map<String, ProductMapping> productMappingMap = productMappings
                .stream()
                .collect(Collectors.toMap(ProductMapping::getProductId, v -> v, (v1, v2) -> v2));
        return r.stream()
                .map(d -> {
                    ProductMapping productMapping = productMappingMap.get(d.getProductId());
                    if (null == productMapping) {
                        return null;
                    }
                    Optional<String> first = productMapping.getThirdProductIds().stream().findFirst();
                    return first.map(s -> XiaomiDevice.builder()
                            .did(d.getDeviceId())
                            .type(s)
                            .name(d.getDeviceName())
                            .build()).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
