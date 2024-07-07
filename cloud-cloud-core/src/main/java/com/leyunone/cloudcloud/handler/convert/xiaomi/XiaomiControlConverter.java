package com.leyunone.cloudcloud.handler.convert.xiaomi;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.bean.mapping.XiaomiStatusMapping;
import com.leyunone.cloudcloud.bean.third.xiaomi.XiaomiProperties;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author LeYunone
 * @date 2024-03-05 15:12:10
 **/
@Service
public class XiaomiControlConverter extends AbstractXiaomiDataConverterTemplate<List<DeviceFunctionDTO>, List<XiaomiProperties>> {

    private final DeviceRelationManager deviceRelationManager;

    protected XiaomiControlConverter(ProductMappingService productMappingService, DeviceRelationManager deviceRelationManager) {
        super(productMappingService);
        this.deviceRelationManager = deviceRelationManager;
    }

    @Override
    public List<DeviceFunctionDTO> convert(List<XiaomiProperties> r) {
        List<String> deviceIds = r
                .stream()
                .map(XiaomiProperties::getDid)
                .distinct()
                .collect(Collectors.toList());
        List<DeviceCloudInfo> deviceEntities = deviceRelationManager.selectByDeviceIds(deviceIds);
        Map<String, DeviceCloudInfo> deviceEntityMap = CollectionFunctionUtils.mapTo(deviceEntities, DeviceCloudInfo::getDeviceId);
        List<String> productIds = deviceEntities.stream().map(DeviceCloudInfo::getProductId).collect(Collectors.toList());
        List<ProductMapping> productMappings = productMappingService.getMapping(productIds, ThirdPartyCloudEnum.XIAOMI);
        Map<String, ProductMapping> productMappingMap = CollectionFunctionUtils.mapTo(productMappings, ProductMapping::getProductId);
        return r.stream()
                .collect(Collectors.groupingBy(XiaomiProperties::getDid))
                .entrySet()
                .stream()
                .flatMap(dg -> {
                    String did = dg.getKey();
                    List<XiaomiProperties> xiaomiProperties = dg.getValue();
                    DeviceCloudInfo deviceEntity = deviceEntityMap.get(did);
                    ProductMapping productMapping;
                    Map<String, XiaomiStatusMapping> functionMappingMap = new HashMap<>();
                    if (null != deviceEntity) {
                        productMapping = productMappingMap.get(deviceEntity.getProductId());
                        if (null != productMapping) {
                            functionMappingMap = productMapping.getStatusMappings().stream().map(f ->
                                    (XiaomiStatusMapping) f).collect(Collectors.toMap(f -> f.getSiid() + "_" + f.getPiid(), v -> v, (v1, v2) -> v2));
                        }
                    }
                    Map<String, XiaomiStatusMapping> finalFunctionMappingMap = functionMappingMap;
                    return xiaomiProperties.stream().map(xp -> {
                        XiaomiStatusMapping xiaomiFunctionMapping = finalFunctionMappingMap.get(xp.getSiid() + "_" + xp.getPiid());
                        if (null == xiaomiFunctionMapping) {
                            return null;
                        }
                        Map<String, Object> valueMapping = xiaomiFunctionMapping.getValueMapping().entrySet().stream().collect(Collectors.toMap(k -> k.getValue().toString(), Map.Entry::getKey, (v1, v2) -> v2));
                        xiaomiFunctionMapping.setValueMapping(valueMapping);
                        Object value = valueOf(xp.getValue(), xiaomiFunctionMapping);
                        if (value == null) {
                            value = "";
                        }
                        DeviceCloudInfo device = deviceEntityMap.get(did);
                        DeviceFunctionDTO functionCodeCommand = new DeviceFunctionDTO();
                        functionCodeCommand.setDeviceId(did);
                        if(ObjectUtil.isNotNull(device)){
                            functionCodeCommand.setProductId(device.getProductId());
                        }
                        functionCodeCommand.setFunctionId(xiaomiFunctionMapping.getFunctionId());
                        functionCodeCommand.setSignCode(xiaomiFunctionMapping.getSignCode());
                        functionCodeCommand.setValue(value.toString());
                        return functionCodeCommand;
                    });
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
