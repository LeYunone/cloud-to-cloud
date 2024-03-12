package com.leyunone.cloudcloud.handler.convert.tmall;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.bean.mapping.TmallProductMapping;
import com.leyunone.cloudcloud.bean.tmall.TmallDevice;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.ConvertHandlerFactory;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/17 16:11
 */
@Service
public class TmallDeviceInfoConverter extends AbstractTmallDataConverterTemplate<List<TmallDevice>, List<DeviceInfo>> {

    private final ProductMappingService productMappingService;

    protected TmallDeviceInfoConverter(ProductMappingService productMappingService, ProductMappingService productMappingService1) {
        super( productMappingService);
        this.productMappingService = productMappingService1;
    }

    @Override
    public List<TmallDevice> convert(List<DeviceInfo> deviceInfos) {
        List<String> productIds = deviceInfos
                .stream()
                .map(DeviceInfo::getProductId).distinct().collect(Collectors.toList());
        List<ProductMapping> productMappings = productMappingService.getMapping(productIds, ThirdPartyCloudEnum.TMALL);
        Map<String, TmallProductMapping> tmallProductMappingMap = convertToMapByProductId(productMappings);

        return deviceInfos.stream().map(deviceInfo -> {
            TmallProductMapping tmallProductMapping = tmallProductMappingMap.get(deviceInfo.getProductId());
            if (ObjectUtil.isNull(tmallProductMapping)) return null;
            Map<String, Object> extensions = new HashMap<>();
            extensions.put("productId", deviceInfo.getProductId());
            TmallDevice tmallDevice = new TmallDevice();
            tmallDevice.setDeviceId(String.valueOf(deviceInfo.getDeviceId()));
            tmallDevice.setDeviceName(deviceInfo.getDeviceName());
            tmallDevice.setDeviceType(tmallProductMapping.getDeviceTypeEnName());
            //必填
            tmallDevice.setBrand(tmallProductMapping.getBrand());
            //TODO 创建产品时类型设置为产品id
            tmallDevice.setModel(deviceInfo.getProductId());
            tmallDevice.setZone(deviceInfo.getGroupName());
            tmallDevice.setStatus(convert(tmallProductMapping.getStatusMappings(), deviceInfo.getDeviceFunctions()));
            tmallDevice.setExtensions(extensions);
            return tmallDevice;
        })
                .filter(ObjectUtil::isNotNull)
                .collect(Collectors.toList());
    }
}
