package com.leyunone.cloudcloud.handler.mapping;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.mapping.ActionMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.bean.mapping.StatusMapping;
import com.leyunone.cloudcloud.dao.FunctionMappingRepository;
import com.leyunone.cloudcloud.dao.entity.ActionMappingDO;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import com.leyunone.cloudcloud.handler.factory.MappingAssemblerFactory;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.strategy.AbstractStrategyAutoRegisterComponent;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public abstract class AbstractStrategyMappingAssembler<R extends ProductMapping> extends AbstractStrategyAutoRegisterComponent implements MappingAssembler<R> {

    protected final static String PRODUCT_MAPPING_CACHE_KEY_PREFIX = "product_mapping_cache";

    protected final FunctionMappingRepository functionMappingRepository;

    private final CacheManager cacheManager;

    protected AbstractStrategyMappingAssembler(MappingAssemblerFactory factory, FunctionMappingRepository functionMappingRepository, CacheManager cacheManager) {
        super(factory);
        this.functionMappingRepository = functionMappingRepository;
        this.cacheManager = cacheManager;
    }


    protected List<StatusMapping> convert(List<FunctionMappingDO> functionMappingDos) {
        if (CollectionUtil.isEmpty(functionMappingDos)) {
            return new ArrayList<>();
        }
        return functionMappingDos
                .stream()
                .map(fm -> {
                    StatusMapping statusMapping = new StatusMapping();
                    BeanUtil.copyProperties(fm,statusMapping);
                    String valueMapping = fm.getValueMapping();
                    Map<String, Object> map = new HashMap<>();
                    if (StrUtil.isNotBlank(valueMapping)) {
                        JSONObject jsonObject = JSON.parseObject(valueMapping);
                        map = jsonObject.getInnerMap();
                    }
                    statusMapping.setValueMapping(map);
                    return statusMapping;
                })
                .collect(Collectors.toList());
    }

    protected String generateProductMappingKey(String productId, String cloud) {
        return String.join("_", PRODUCT_MAPPING_CACHE_KEY_PREFIX, cloud, productId);
    }

    @Override
    public List<R> assembler(List<String> productIds) {
        if (CollectionUtils.isEmpty(productIds)) {
            return new ArrayList<>();
        }

        List<Object> cacheKeys = productIds
                .stream()
                .distinct()
                .map(pid -> generateProductMappingKey(pid, getKey()))
                .collect(Collectors.toList());

        Optional<List<R>> rs = cacheManager.getData(cacheKeys,
                null,
                (hit) -> {
                    List<String> missPidList = new ArrayList<>();
                    if (CollectionUtils.isEmpty(hit)) {
                        missPidList = productIds;
                    } else {
                        Map<String, R> productMappingMap = hit.stream().collect(Collectors.toMap(ProductMapping::getProductId, v -> v, (v1, v2) -> v2));
                        Set<String> hitPidList = productMappingMap.keySet();
                        if (hitPidList.size() != productIds.size()) {
                            missPidList = productIds.stream().filter(t -> !productMappingMap.containsKey(t)).collect(Collectors.toList());
                        }
                    }
                    if (!CollectionUtils.isEmpty(missPidList)) {
                        return dataGet(missPidList);
                    } else {
                        return new ArrayList<>();
                    }
                },
                (data) -> generateProductMappingKey(data.getProductId(), getKey()));

        return rs.orElse(new ArrayList<>());
    }

    protected List<ActionMapping> convertActionMapping(List<ActionMappingDO> actionMappingDos) {
        if (CollectionUtils.isEmpty(actionMappingDos)) {
            return new ArrayList<>();
        }
        return actionMappingDos
                .stream()
                .map(am -> {
                    ActionMapping actionMapping = ActionMapping.Converter.INSTANCE.convert(am);
                    String valueMapping = am.getValueMapping();
                    if (!org.springframework.util.StringUtils.isEmpty(valueMapping)) {
                        JSONObject jsonObject = JSON.parseObject(valueMapping);
                        actionMapping.setValueMapping(jsonObject.getInnerMap());
                    } else {
                        actionMapping.setValueMapping(new HashMap<>());
                    }
                    return actionMapping;
                })
                .collect(Collectors.toList());
    }

    protected abstract List<R> dataGet(List<String> pids);

}
