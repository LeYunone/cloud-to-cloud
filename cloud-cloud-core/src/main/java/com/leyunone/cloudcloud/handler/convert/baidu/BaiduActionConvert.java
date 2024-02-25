package com.leyunone.cloudcloud.handler.convert.baidu;

import com.leyunone.cloudcloud.bean.baidu.DeviceControlRequest;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.enums.BaiduCommandEnums;
import com.leyunone.cloudcloud.bean.mapping.ActionMapping;
import com.leyunone.cloudcloud.bean.mapping.BaiduProductMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.ConvertHandlerFactory;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-09
 */
@Service
public class BaiduActionConvert extends AbstractBaiduDataConverterTemplate<DeviceFunctionDTO, DeviceControlRequest> {

    protected BaiduActionConvert( ProductMappingService productMappingService) {
        super(productMappingService);
    }

    @Override
    public DeviceFunctionDTO convert(DeviceControlRequest r) {
        String name = r.getHeader().getName();
        String action = name.replaceAll("Request", "");
        BaiduCommandEnums byName = BaiduCommandEnums.getByName(action);
        String productId = r.getPayload().getAppliance().getAdditionalApplianceDetails().get("productId");
        List<ProductMapping> mapping = productMappingService.getMapping(productId, ThirdPartyCloudEnum.BAIDU);
        Optional<ProductMapping> first = mapping.stream().findFirst();
        if (!first.isPresent()) {
            return null;
        }
        BaiduProductMapping baiduProductMapping = (BaiduProductMapping) first.get();
        List<ActionMapping> actionMappings = baiduProductMapping.getActionMappings();
        Map<String, ActionMapping> actionMap = actionMappings
                .stream()
                .collect(Collectors.toMap(k -> k.getAction().toUpperCase(), v -> v, (v1, v2) -> v2));
        ActionMapping actionMapping = actionMap.get(byName.getName());
        String value = byName.valueConvert(r.getPayload(), actionMapping);
        DeviceFunctionDTO deviceFunctionDTO = new DeviceFunctionDTO();
        deviceFunctionDTO.setSignCode(actionMapping.getSignCode());
        deviceFunctionDTO.setDeviceId(r.getPayload().getAppliance().getApplianceId());
        deviceFunctionDTO.setValue(value);
        return deviceFunctionDTO;
    }
}
