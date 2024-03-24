package com.leyunone.cloudcloud.handler.convert.alexa;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.mapping.ActionMapping;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaControlRequest;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.enums.AlexaActionValueEnum;
import com.leyunone.cloudcloud.bean.mapping.AlexaProductMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/1 17:31
 */
@Service
public class AlexaActionConverter extends AbstractAlexaDataConverterTemplate<List<DeviceFunctionDTO>, AlexaControlRequest> {

    public AlexaActionConverter(ProductMappingService productMappingService) {
        super(productMappingService);
    }

    /**
     * alexa云动作 转换我方云指令属性
     *
     * @param r
     * @return
     */
    @Override
    public List<DeviceFunctionDTO> convert(AlexaControlRequest r) {
        JSONObject cookie = (JSONObject) r.getDirective().getEndpoint().getCookie();
        String productId = cookie.getString("productId");
        if (StrUtil.isBlank(productId)) {
            //TODO 发现设备时未填充，或cookie丢失
        }
        List<ProductMapping> mapping = productMappingService.getMapping(productId, ThirdPartyCloudEnum.ALEXA);
        Map<String, AlexaProductMapping> productMappingMap = super.convertToMapByProductId(mapping);
        AlexaProductMapping alexaProductMapping = productMappingMap.get(productId);
        List<AlexaProductMapping.Capability> capabilityList = alexaProductMapping.getCapabilityList();
        String namespace = r.getDirective().getHeader().getNamespace();
        String name = r.getDirective().getHeader().getName();

        Map<String, List<AlexaProductMapping.Capability>> capabilityMap = CollectionFunctionUtils.groupTo(capabilityList.stream().filter(t -> t.getThirdActionCode().equals(namespace)).collect(Collectors.toList()),
                AlexaProductMapping.Capability::getThirdActionCode);
        List<DeviceFunctionDTO> codeCommands = new ArrayList<>();
        /**
         * name未配置，不支持控制
         */
        if (capabilityMap.containsKey(namespace)) {
            codeCommands = capabilityMap.get(namespace).stream().map(capability -> {
                DeviceFunctionDTO codeCommand = new DeviceFunctionDTO();
                //FIXME 出现value为空
                Object value = null;
                if (CollectionUtil.isNotEmpty(capability.getCapabilityMapping()) && capability.getCapabilityMapping().containsKey(name)) {
                    ActionMapping capabilityMapping = capability.getCapabilityMapping().get(name);
                    value = super.getControlValue(r.getDirective().getPayload(), capabilityMapping);
                    codeCommand.setOperation(capabilityMapping.getOperation());
                    codeCommand.setSignCode(capabilityMapping.getSignCode());
                    codeCommand.setValue(value);
                    codeCommand.setFunctionId(capabilityMapping.getFunctionId());
                }
                codeCommand.setDeviceId(r.getDirective().getEndpoint().getEndpointId());
                return codeCommand;
            }).collect(Collectors.toList());
        }
        return codeCommands;
    }


}
