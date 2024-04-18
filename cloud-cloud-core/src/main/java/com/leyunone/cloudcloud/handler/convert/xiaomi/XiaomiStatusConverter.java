package com.leyunone.cloudcloud.handler.convert.xiaomi;

import com.leyunone.cloudcloud.bean.XiaomiResultCode;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.bean.mapping.XiaomiStatusMapping;
import com.leyunone.cloudcloud.bean.third.xiaomi.XiaomiProperties;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LeYunone
 * @date 2024-03-05 13:45:19
 **/
@Slf4j
@Service
public class XiaomiStatusConverter extends AbstractXiaomiDataConverterTemplate<List<XiaomiProperties>, List<DeviceInfo>> {


    protected XiaomiStatusConverter(ProductMappingService productMappingService) {
        super(productMappingService);
    }

    @Override
    public List<XiaomiProperties> convert(List<DeviceInfo> devicesStatus) {
        List<String> productIds = devicesStatus.stream().map(DeviceInfo::getProductId).collect(Collectors.toList());
        List<ProductMapping> productMappings = productMappingService.getMapping(productIds, ThirdPartyCloudEnum.XIAOMI);
        Map<String, ProductMapping> productMappingMap = productMappings.stream().collect(Collectors.toMap(ProductMapping::getProductId, v -> v, (v1, v2) -> v2));
        return devicesStatus
                .stream()
                .flatMap(d -> {
                    String productId = d.getProductId();
                    ProductMapping productMapping = productMappingMap.get(productId);
                    Map<String, DeviceFunctionDTO> deviceFunctionMap = d.getDeviceFunctions().stream().collect(Collectors.toMap(DeviceFunctionDTO::getSignCode, v -> v));
                    return productMapping
                            .getStatusMappings()
                            .stream()
                            .map(m -> {
                                DeviceFunctionDTO deviceFunction = deviceFunctionMap.get(m.getSignCode());
                                Object deviceValue = deviceFunction.getValue();
                                Object value = valueOf(deviceValue.toString(), m);
                                if (null == value) {
                                    value = "";
                                }
                                XiaomiStatusMapping functionMapping = (XiaomiStatusMapping) m;
                                XiaomiProperties properties = XiaomiProperties.builder()
                                        .did(d.getDeviceId())
                                        .piid(functionMapping.getPiid())
                                        .siid(functionMapping.getSiid())
                                        .value(value.toString())
                                        .build();
                                properties.setStatus(XiaomiResultCode.SUCCESS.getCode());
                                return properties;
                            });
                })
                .collect(Collectors.toList());
    }
}
