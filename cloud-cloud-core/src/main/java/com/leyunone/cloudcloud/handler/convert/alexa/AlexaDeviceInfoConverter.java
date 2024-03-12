package com.leyunone.cloudcloud.handler.convert.alexa;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.alexa.AlexaDevice;
import com.leyunone.cloudcloud.bean.alexa.AlexaDeviceCapability;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.mapping.AlexaProductMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.ConvertHandlerFactory;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 * alexa设备
 *
 * @Author LeYunone
 * @Date 2024/1/31 11:24
 */
@Service
public class AlexaDeviceInfoConverter extends AbstractAlexaDataConverterTemplate<List<AlexaDevice>, List<DeviceInfo>> {

    public AlexaDeviceInfoConverter(ProductMappingService productMappingService) {
        super(productMappingService);
    }


    @Override
    public List<AlexaDevice> convert(List<DeviceInfo> r) {
        List<String> pids = r.stream().map(DeviceInfo::getProductId).collect(Collectors.toList());
        List<ProductMapping> mapping = productMappingService.getMapping(pids, ThirdPartyCloudEnum.ALEXA);
        Map<String, AlexaProductMapping> productMappings = super.convertToMapByProductId(mapping);

        return r.stream().filter(deviceInfo -> productMappings.containsKey(deviceInfo.getProductId())).map(deviceInfo -> {
            String pid = deviceInfo.getProductId();
            AlexaProductMapping productMapping = productMappings.get(pid);

            Map<String, String> additionalAttributes = new HashMap<>();
            AlexaDevice alexaDevice = new AlexaDevice();
            alexaDevice.setEndpointId(String.valueOf(deviceInfo.getDeviceId()));
            alexaDevice.setManufacturerName("DEV");
            alexaDevice.setDescription("");
            alexaDevice.setFriendlyName(deviceInfo.getDeviceName());
            alexaDevice.setAdditionalAttributes(additionalAttributes);
            alexaDevice.setDisplayCategories(productMapping.getThirdProductIds());
            Map<String, String> cookie = new HashMap<>();
            cookie.put("productId", deviceInfo.getProductId());
            alexaDevice.setCookie(cookie);

            /**
             * alexa设备 技能包含：涉及属性
             *                  接口名
             *                  实例名
             *                  语义和语义配置
             *  functionMapping 一个signCode 一个技能                
             */
            List<AlexaDeviceCapability> deviceSkills = productMapping.getCapabilityList().stream().map(capability -> {
                return AlexaDeviceCapability.builder()
                        .interfaceStr(capability.getThirdActionCode())
                        .type("AlexaInterface")
                        .version("3")
                        .instance(capability.getInstance())
                        .capabilityResources(capability.getCapabilityResources())
                        .semantics(capability.getSemantics())
                        .configuration(capability.getConfiguration())
                        .properties(AlexaDeviceCapability.Property
                                .builder()
                                .supported(capability.getSupportAttr().stream().map(attr ->
                                        AlexaDeviceCapability.Supported.builder()
                                                .name(attr).build()).collect(Collectors.toList()))
                                .proactivelyReported(true)
                                .retrievable(true).build())
                        .build();
            }).collect(Collectors.toList());
            /**
             * alexa设备默认全部配置 EndpointHealth 技能 ：设备上下线属性
             */
            deviceSkills.add(
                    AlexaDeviceCapability.builder()
                            .type("AlexaInterface")
                            .interfaceStr("Alexa.EndpointHealth")
                            .version("3.1")
                            .properties(AlexaDeviceCapability.Property
                                    .builder()
                                    .supported(CollectionUtil.newArrayList(AlexaDeviceCapability.Supported.builder().name("connectivity").build()))
                                    .proactivelyReported(true)
                                    .retrievable(true)
                                    .build())
                            .build()
            );
            deviceSkills.add(
                    AlexaDeviceCapability.builder()
                            .type("AlexaInterface")
                            .interfaceStr("Alexa")
                            .version("3")
                            .build()
            );
            alexaDevice.setCapabilities(deviceSkills);
            return alexaDevice;
        }).collect(Collectors.toList());
    }
}
