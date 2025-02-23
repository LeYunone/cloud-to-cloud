package com.leyunone.cloudcloud.handler.mapping;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.bean.mapping.StatusMapping;
import com.leyunone.cloudcloud.bean.mapping.XiaomiStatusMapping;
import com.leyunone.cloudcloud.dao.ActionMappingRepository;
import com.leyunone.cloudcloud.dao.FunctionMappingRepository;
import com.leyunone.cloudcloud.dao.ProductTypeMappingRepository;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.MappingAssemblerFactory;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LeYunone
 * @date 2023-12-08 11:22:16
 **/
@Service
public class XiaomiMappingAssembler extends AbstractStrategyMappingAssembler<ProductMapping> {

    private final ProductTypeMappingRepository productTypeMappingRepository;
    private final ActionMappingRepository actionMappingRepository;

    protected XiaomiMappingAssembler(MappingAssemblerFactory factory, FunctionMappingRepository functionMappingRepository, CacheManager cacheManager, ProductTypeMappingRepository productTypeMappingRepository, ActionMappingRepository actionMappingRepository) {
        super(factory, functionMappingRepository, cacheManager);
        this.productTypeMappingRepository = productTypeMappingRepository;
        this.actionMappingRepository = actionMappingRepository;
    }

    @Override
    protected String getKey() {
        return ThirdPartyCloudEnum.XIAOMI.name();
    }

    @Override
    protected List<ProductMapping> dataGet(List<String> pids) {
        List<FunctionMappingDO> functionMappingDos = functionMappingRepository.selectByProductIdsAndThirdPartyCloud(pids, getKey());
        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByProductIds(pids, getKey());
        Map<String, List<FunctionMappingDO>> functionMappingMap = functionMappingDos
                .stream()
                .collect(Collectors.groupingBy(FunctionMappingDO::getProductId, Collectors.toList()));
        Map<String, List<ProductTypeMappingDO>> productMappingMap = productTypeMappingDOS
                .stream()
                .collect(Collectors.groupingBy(ProductTypeMappingDO::getProductId, Collectors.toList()));
        return pids
                .stream()
                .map(p -> {
                    List<FunctionMappingDO> functionMappings = functionMappingMap.get(p);
                    List<ProductTypeMappingDO> productTypeMappings = productMappingMap.get(p);
                    if (CollectionUtils.isEmpty(functionMappings) || CollectionUtil.isEmpty(productTypeMappings)) {
                        return null;
                    }
                    ProductMapping productMapping = new ProductMapping();
                    productMapping.setProductId(p);
                    productMapping.setThirdProductIds(productTypeMappings.stream().map(ProductTypeMappingDO::getThirdProductId).distinct().collect(Collectors.toList()));
                    productMapping.setThirdPartyCloud(ThirdPartyCloudEnum.XIAOMI);
                    productMapping.setStatusMappings(convert(functionMappings));
                    return productMapping;
                }).filter(ObjectUtil::isNotNull)
                .collect(Collectors.toList());
    }

    @Override
    protected List<StatusMapping> convert(List<FunctionMappingDO> functionMappingDos) {
        if (CollectionUtil.isEmpty(functionMappingDos)) {
            return new ArrayList<>();
        }
        return functionMappingDos
                .stream()
                .map(fm -> {
                    XiaomiStatusMapping xiaomiFunctionMapping = new XiaomiStatusMapping();
                    BeanUtil.copyProperties(fm, xiaomiFunctionMapping);
                    String valueMapping = fm.getValueMapping();
                    xiaomiFunctionMapping.setValueMapping(new HashMap<>());
                    if (!StringUtils.isEmpty(valueMapping)) {
                        JSONObject jsonObject = JSON.parseObject(valueMapping);
                        xiaomiFunctionMapping.setValueMapping(jsonObject.getInnerMap());
                    }
                    //小米一个功能对应一个sid和一个pid
                    xiaomiFunctionMapping.setSiid(Integer.valueOf(xiaomiFunctionMapping.getThirdSignCode()));
                    xiaomiFunctionMapping.setPiid(Integer.valueOf(xiaomiFunctionMapping.getThirdActionCode()));
                    return xiaomiFunctionMapping;
                })
                .collect(Collectors.toList());
    }
}
