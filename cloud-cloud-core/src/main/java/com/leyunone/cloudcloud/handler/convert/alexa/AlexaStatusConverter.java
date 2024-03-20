package com.leyunone.cloudcloud.handler.convert.alexa;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaDeviceProperty;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.mapping.AlexaFunctionMapping;
import com.leyunone.cloudcloud.bean.mapping.AlexaProductMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.bean.mapping.StatusMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import com.leyunone.cloudcloud.util.TimeUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 * 我方云属性 转Alexa云属性
 *
 * @Author LeYunone
 * @Date 2024/2/2 17:21
 */
@Service
public class AlexaStatusConverter extends AbstractAlexaDataConverterTemplate<List<AlexaDeviceProperty>, DeviceInfo> {

    public AlexaStatusConverter( ProductMappingService productMappingService) {
        super( productMappingService);
    }

    @Override
    public List<AlexaDeviceProperty> convert(DeviceInfo r) {
        List<ProductMapping> mapping = productMappingService.getMapping(r.getProductId(), ThirdPartyCloudEnum.ALEXA);
        Map<String, AlexaProductMapping> alexaMapping = super.convertToMapByProductId(mapping);
        List<AlexaDeviceProperty> properties = new ArrayList<>();
        mapping.forEach(mp -> {
            properties.addAll(this.statusConvert(alexaMapping.get(mp.getProductId()), r.getDeviceFunctions()));
        });
        return properties;
    }

    private List<AlexaDeviceProperty> statusConvert(AlexaProductMapping productMapping, List<DeviceFunctionDTO> deviceFunctions) {
        /**
         * 我方一码对产商云多码
         */
        Map<String, List<AlexaFunctionMapping>> statusMap = CollectionFunctionUtils.groupTo(productMapping.getAlexaFunctionMappings(), AlexaFunctionMapping::getSignCode);
        List<AlexaDeviceProperty> result = new ArrayList<>();
        deviceFunctions.stream().filter(t -> statusMap.containsKey(t.getSignCode())).forEach(deviceFunction -> {
            List<AlexaFunctionMapping> functionMappings = statusMap.get(deviceFunction.getSignCode());
            result.addAll(functionMappings.stream().map(functionMapping -> AlexaDeviceProperty.builder()
                    .timeOfSample(TimeUtils.getUTCyyyyMMddTHHmmssSSSZ())
                    .uncertaintyInMilliseconds(500L)
                    .instance(functionMapping.getInstance())
                    .namespace(functionMapping.getThirdActionCode())
                    .name(functionMapping.getThirdSignCode())
                    .value(this.valueOf(deviceFunction.getValue(), functionMapping))
                    .build()).collect(Collectors.toList()));
        });
        return result;
    }
    
}
