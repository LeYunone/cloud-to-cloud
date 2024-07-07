package com.leyunone.cloudcloud.handler.convert.google;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.third.google.GoogleDevice;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.mapping.GoogleProductMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import com.leyunone.cloudcloud.util.ConvertUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/23 16:30
 */
@Service
public class GoogleDeviceInfoConvert extends AbstractGoogleDataConverterTemplate<List<GoogleDevice>, List<DeviceInfo>> {

    protected GoogleDeviceInfoConvert(ProductMappingService productMappingService) {
        super(productMappingService);
    }

    @Override
    public List<GoogleDevice> convert(List<DeviceInfo> r) {
        List<String> pids = r.stream().map(DeviceInfo::getProductId).collect(Collectors.toList());
        List<ProductMapping> productMappings = productMappingService.getMapping(pids, ThirdPartyCloudEnum.GOOGLE);
        Map<String, GoogleProductMapping> productMappingMap = ConvertUtils.convertToMapByProductId(productMappings);
        return r.stream().filter(d -> productMappingMap.containsKey(d.getProductId()))
                .map(d -> {
                    GoogleProductMapping googleProductMapping = productMappingMap.get(d.getProductId());
                    GoogleDevice googleDevice = new GoogleDevice();
                    googleDevice.setId(String.valueOf(d.getDeviceId()));
                    //仅一个类型
                    googleDevice.setType(CollectionUtil.getFirst(googleProductMapping.getThirdProductIds()));
                    googleDevice.setTraits(googleProductMapping.getTraits());
                    googleDevice.setName(GoogleDevice.Name.builder()
                            .name(d.getDeviceName())
                            .build());
                    googleDevice.setWillReportState(true);
                    googleDevice.setAttributes(googleProductMapping.getAttributes());
                    googleDevice.setRoomHint(d.getGroupName());
                    googleDevice.setDeviceInfo(GoogleDevice.DeviceInfo.builder()
                            .model(d.getModelName())
                            .swVersion(d.getVersion())
                            .build());
                    googleDevice.setCustomData(GoogleDevice.CustomData.builder().productId(d.getProductId()).build());
                    return googleDevice;
                }).collect(Collectors.toList());
    }
}
