package com.leyunone.cloudcloud.handler.mapping;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.mapping.ActionMapping;
import com.leyunone.cloudcloud.bean.mapping.BaiduProductMapping;
import com.leyunone.cloudcloud.dao.ActionMappingRepository;
import com.leyunone.cloudcloud.dao.FunctionMappingRepository;
import com.leyunone.cloudcloud.dao.ProductTypeMappingRepository;
import com.leyunone.cloudcloud.dao.entity.ActionMappingDO;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.enums.OperationEnum;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.MappingAssemblerFactory;
import com.leyunone.cloudcloud.handler.factory.StrategyFactory;
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
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Service
public class BaiduMappingAssembler extends AbstractStrategyMappingAssembler<BaiduProductMapping> {

    private final ActionMappingRepository actionMappingRepository;
    private final ProductTypeMappingRepository productTypeMappingRepository;

    protected BaiduMappingAssembler(MappingAssemblerFactory factory, FunctionMappingRepository functionMappingRepository, CacheManager cacheManager, ActionMappingRepository actionMappingRepository, ProductTypeMappingRepository productTypeMappingRepository) {
        super(factory, functionMappingRepository, cacheManager);
        this.actionMappingRepository = actionMappingRepository;
        this.productTypeMappingRepository = productTypeMappingRepository;
    }

    @Override
    protected List<BaiduProductMapping> dataGet(List<String> pids) {
        List<FunctionMappingDO> functionMappingDos = functionMappingRepository.selectByProductIdsAndThirdPartyCloud(pids, getKey());
        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByProductIds(pids, getKey());
        Map<String, List<FunctionMappingDO>> functionMappingMap = functionMappingDos
                .stream()
                .collect(Collectors.groupingBy(FunctionMappingDO::getProductId, Collectors.toList()));
        Map<String, List<ProductTypeMappingDO>> productMappingMap = productTypeMappingDOS.stream().collect(Collectors.groupingBy(ProductTypeMappingDO::getThirdProductId));

        List<ActionMappingDO> actionMappingDos = actionMappingRepository.selectByProductIds(pids, getKey());
        Map<String, List<ActionMappingDO>> actionMappingMap = actionMappingDos
                .stream()
                .collect(Collectors.groupingBy(ActionMappingDO::getProductId, Collectors.toList()));
        return pids
                .stream()
                .map(p -> {
                    List<FunctionMappingDO> functionMappings = functionMappingMap.get(p);
                    List<ActionMappingDO> actionMappings = actionMappingMap.get(p);
                    List<ProductTypeMappingDO> productTypeMappings = productMappingMap.get(p);
                    if (CollectionUtils.isEmpty(functionMappings)) {
                        return null;
                    }
                    BaiduProductMapping baiduProductMapping = new BaiduProductMapping();
                    baiduProductMapping.setProductId(p);
                    baiduProductMapping.setThirdProductIds(productTypeMappings.stream().map(ProductTypeMappingDO::getThirdProductId).distinct().collect(Collectors.toList()));
                    baiduProductMapping.setThirdPartyCloud(ThirdPartyCloudEnum.BAIDU);
                    baiduProductMapping.setStatusMappings(convert(functionMappings));
                    baiduProductMapping.setActionMappings(convertActionMapping(actionMappings));
                    return baiduProductMapping;
                })
                .filter(ObjectUtil::isNotNull)
                .collect(Collectors.toList());
    }

    @Override
    protected String getKey() {
        return ThirdPartyCloudEnum.BAIDU.name();
    }

    private List<ActionMapping> convertActionMapping(List<ActionMappingDO> actionMappingDos) {
        if (CollectionUtils.isEmpty(actionMappingDos)) {
            return new ArrayList<>();
        }
        return actionMappingDos
                .stream()
                .map(am -> {
                    ActionMapping actionMapping = new ActionMapping();
                    BeanUtil.copyProperties(am, actionMapping);
                    String valueMapping = am.getValueMapping();
                    String operation = am.getOperation();
                    if (!StringUtils.isEmpty(operation)) {
                        OperationEnum functionOperation = OperationEnum.valueOf(operation);
                        actionMapping.setOperation(functionOperation);
                    }
                    if (!StringUtils.isEmpty(valueMapping)) {
                        JSONObject jsonObject = JSON.parseObject(valueMapping);
                        actionMapping.setValueMapping(jsonObject.getInnerMap());
                    } else {
                        actionMapping.setValueMapping(new HashMap<>());
                    }
                    return actionMapping;
                })
                .collect(Collectors.toList());
    }


}
