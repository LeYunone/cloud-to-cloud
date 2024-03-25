package com.leyunone.cloudcloud.handler.mapping;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.mapping.GoogleProductMapping;
import com.leyunone.cloudcloud.dao.ActionMappingRepository;
import com.leyunone.cloudcloud.dao.DeviceCapabilityRepository;
import com.leyunone.cloudcloud.dao.FunctionMappingRepository;
import com.leyunone.cloudcloud.dao.ProductTypeMappingRepository;
import com.leyunone.cloudcloud.dao.entity.ActionMappingDO;
import com.leyunone.cloudcloud.dao.entity.DeviceCapabilityDO;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.MappingAssemblerFactory;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/26 10:16
 */
@Service
public class GoogleMappingAssembler extends AbstractStrategyMappingAssembler<GoogleProductMapping> {

    private final ProductTypeMappingRepository productTypeMappingRepository;
    private final DeviceCapabilityRepository deviceCapabilityRepository;
    private final ActionMappingRepository actionMappingRepository;
    private final FunctionMappingRepository functionMappingRepository;

    protected GoogleMappingAssembler(MappingAssemblerFactory factory, FunctionMappingRepository functionMappingRepository, CacheManager cacheManager, ProductTypeMappingRepository productTypeMappingRepository, DeviceCapabilityRepository deviceCapabilityRepository, ActionMappingRepository actionMappingRepository, FunctionMappingRepository functionMappingRepository1) {
        super(factory, functionMappingRepository, cacheManager);
        this.productTypeMappingRepository = productTypeMappingRepository;
        this.deviceCapabilityRepository = deviceCapabilityRepository;
        this.actionMappingRepository = actionMappingRepository;
        this.functionMappingRepository = functionMappingRepository1;
    }

    @Override
    protected String getKey() {
        return ThirdPartyCloudEnum.GOOGLE.name();
    }

    @Override
    protected List<GoogleProductMapping> dataGet(List<String> pids) {
        List<FunctionMappingDO> functionMappingDos = functionMappingRepository.selectByProductIdsAndThirdPartyCloud(pids, ThirdPartyCloudEnum.GOOGLE.name());
        List<ActionMappingDO> actionMappingDOS = actionMappingRepository.selectByProductIds(pids, ThirdPartyCloudEnum.GOOGLE.name());
        List<DeviceCapabilityDO> deviceCapabilityDOS = deviceCapabilityRepository.selectByCloud(ThirdPartyCloudEnum.GOOGLE);
        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByProductIds(pids, ThirdPartyCloudEnum.GOOGLE.name());

        Map<String, List<ActionMappingDO>> actionMappingMap = CollectionFunctionUtils.groupTo(actionMappingDOS, ActionMappingDO::getProductId);
        Map<String, List<FunctionMappingDO>> statusMappingMap = CollectionFunctionUtils.groupTo(functionMappingDos, FunctionMappingDO::getProductId);
        Map<Integer, DeviceCapabilityDO> capabilityMap = CollectionFunctionUtils.mapTo(deviceCapabilityDOS, DeviceCapabilityDO::getId);
        Map<String, List<ProductTypeMappingDO>> typeMap = productTypeMappingDOS.stream().collect(Collectors.groupingBy(ProductTypeMappingDO::getProductId));
        return pids
                .stream()
                .map(p -> {
                    List<FunctionMappingDO> functionMappings = statusMappingMap.get(p);
                    List<ProductTypeMappingDO> productTypes = typeMap.get(p);
                    if (CollectionUtils.isEmpty(functionMappings) || CollectionUtil.isEmpty(productTypes)) {
                        return null;
                    }
                    List<ActionMappingDO> actionMaps = actionMappingMap.get(p);
                    GoogleProductMapping productMapping = new GoogleProductMapping();
                    productMapping.setProductId(p);
                    productMapping.setThirdPartyCloud(ThirdPartyCloudEnum.GOOGLE);
                    productMapping.setStatusMappings(super.convert(functionMappings));
                    productMapping.setActionMappings(super.convertActionMapping(actionMaps));
                    productMapping.setTraits(CollectionUtil.newArrayList(functionMappings.stream().map(FunctionMappingDO::getThirdActionCode).collect(Collectors.toSet())));
                    productMapping.setAttributes(this.buildConfig(functionMappings, capabilityMap));
                    productMapping.setThirdProductIds(productTypes.stream().map(ProductTypeMappingDO::getThirdProductId).distinct().collect(Collectors.toList()));
                    return productMapping;
                })
                .filter(ObjectUtil::isNotNull)
                .collect(Collectors.toList());
    }

    private Map<String, Object> buildConfig(List<FunctionMappingDO> functionMappingDOS, Map<Integer, DeviceCapabilityDO> capabilityMap) {
        Map<String, Object> result = new HashMap<>();
        functionMappingDOS.stream().filter(t -> ObjectUtil.isNotNull(t.getCapabilityConfigId()))
                .forEach(functionMappingDO -> {
                    for (String id : functionMappingDO.getCapabilityConfigId().split(",")) {
                        DeviceCapabilityDO deviceCapabilityDO = capabilityMap.get(Integer.parseInt(id));
                        if (ObjectUtil.isNull(deviceCapabilityDO)) continue;
                        Object value;
                        //json->array->string
                        Object o = result.get(deviceCapabilityDO.getInstanceName());
                        try {
                            value = JSONObject.parseObject(deviceCapabilityDO.getCapabilityConfiguration());
                        } catch (JSONException jsonException) {
                            try {
                                value = JSONObject.parseArray(deviceCapabilityDO.getCapabilityConfiguration());
                                if (ObjectUtil.isNotNull(o)) {
                                    /**
                                     * 数组
                                     */
                                    List list = (List) o;
                                    list.addAll((Collection) value);
                                    value = list;
                                }
                            } catch (JSONException jsonException1) {
                                value = deviceCapabilityDO.getCapabilityConfiguration();
                            }
                        }
                        result.put(deviceCapabilityDO.getInstanceName(), value);
                    }
                });
        return result;
    }

}
