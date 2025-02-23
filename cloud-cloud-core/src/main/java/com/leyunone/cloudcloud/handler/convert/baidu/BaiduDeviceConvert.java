package com.leyunone.cloudcloud.handler.convert.baidu;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.mapping.ActionMapping;
import com.leyunone.cloudcloud.bean.mapping.BaiduProductMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduAttributes;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduDevice;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import com.leyunone.cloudcloud.util.ConvertUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
@Service
public class BaiduDeviceConvert extends AbstractBaiduDataConverterTemplate<List<BaiduDevice>, List<DeviceInfo>> {

    protected BaiduDeviceConvert( ProductMappingService productMappingService) {
        super( productMappingService);
    }

    @Override
    public List<BaiduDevice> convert(List<DeviceInfo> r) {
        if(CollectionUtil.isEmpty(r)) return new ArrayList<>();
        List<String> productIds = r
                .stream()
                .map(DeviceInfo::getProductId).distinct().collect(Collectors.toList());
        List<ProductMapping> productMappings = productMappingService.getMapping(productIds, ThirdPartyCloudEnum.BAIDU);
        Map<String, BaiduProductMapping> baiduProductMappingMap = ConvertUtils.convertToMapByProductId(productMappings);
        return r
                .stream()
                .map(d -> {
                    String productId = d.getProductId();
                    Map<String, String> additionalApplianceDetails = new HashMap<>(16);
                    additionalApplianceDetails.put("productId", d.getProductId());
                    BaiduProductMapping baiduProductMapping = baiduProductMappingMap.get(productId);
                    if (null == baiduProductMapping) {
                        return null;
                    }
                    List<ActionMapping> actionMappings = baiduProductMapping.getActionMappings();
                    List<String> actions = actionMappings.stream().map(ActionMapping::getThirdActionCode).filter(Objects::nonNull).distinct().collect(Collectors.toList());
                    List<BaiduAttributes> baiduAttributes = convert(baiduProductMapping.getStatusMappings(), d.getDeviceFunctions());

                    return BaiduDevice.builder()
                            .applianceId(d.getDeviceId())
                            .applianceTypes(baiduProductMapping.getThirdProductIds())
                            .modelName(d.getModelName())
                            .version(d.getVersion())
                            .friendlyName(d.getDeviceName())
                            .friendlyDescription("Team")
                            .isReachable(String.valueOf(d.isOnline()))
                            .actions(actions)
                            .additionalApplianceDetails(additionalApplianceDetails)
                            //TODO 配置信息
                            .manufacturerName("")
                            .attributes(baiduAttributes)
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
