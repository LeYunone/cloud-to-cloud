package com.leyunone.cloudcloud.handler.convert.baidu;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.third.baidu.DeviceControlRequest;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.enums.BaiduCommandEnums;
import com.leyunone.cloudcloud.bean.mapping.ActionMapping;
import com.leyunone.cloudcloud.bean.mapping.BaiduProductMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

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

    protected BaiduActionConvert(ProductMappingService productMappingService) {
        super(productMappingService);
    }

    @Override
    public DeviceFunctionDTO convert(DeviceControlRequest r) {
        String name = r.getHeader().getName();
        String action = name.replaceAll("Request", "");
        DeviceControlRequest.Payload payload = JSONObject.parseObject(r.getPayload().toJSONString(), DeviceControlRequest.Payload.class);
        String productId = payload.getAppliance().getAdditionalApplianceDetails().get("productId");
        List<ProductMapping> mapping = productMappingService.getMapping(productId, ThirdPartyCloudEnum.BAIDU);
        Optional<ProductMapping> first = mapping.stream().findFirst();
        if (!first.isPresent()) {
            return null;
        }
        BaiduProductMapping baiduProductMapping = (BaiduProductMapping) first.get();
        List<ActionMapping> actionMappings = baiduProductMapping.getActionMappings();
        Map<String, ActionMapping> actionMap = actionMappings
                .stream()
                .collect(Collectors.toMap(k -> k.getThirdActionCode().toUpperCase(), v -> v, (v1, v2) -> v2));
        ActionMapping actionMapping = actionMap.get(action);
        Object value = super.getControlValue(r.getPayload(), actionMapping);
        DeviceFunctionDTO deviceFunctionDTO = new DeviceFunctionDTO();
        deviceFunctionDTO.setSignCode(actionMapping.getSignCode());
        deviceFunctionDTO.setDeviceId(payload.getAppliance().getApplianceId());
        deviceFunctionDTO.setValue(value);
        return deviceFunctionDTO;
    }
}
