package com.leyunone.cloudcloud.handler.mapping;

import com.leyunone.cloudcloud.bean.mapping.BaiduProductMapping;
import com.leyunone.cloudcloud.dao.ActionMappingRepository;
import com.leyunone.cloudcloud.dao.FunctionMappingRepository;
import com.leyunone.cloudcloud.dao.ProductTypeMappingRepository;
import com.leyunone.cloudcloud.dao.entity.ActionMappingDO;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.MappingAssemblerFactory;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;

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
        List<ActionMappingDO> actionMappingDos = actionMappingRepository.selectByProductIds(pids, getKey());
        Map<String, List<FunctionMappingDO>> statusMappingMap = CollectionFunctionUtils.groupTo(functionMappingDos, FunctionMappingDO::getProductId);
        Map<String, List<ProductTypeMappingDO>> productMappingMap = CollectionFunctionUtils.groupTo(productTypeMappingDOS, ProductTypeMappingDO::getProductId);
        Map<String, List<ActionMappingDO>> actionMappingMap = CollectionFunctionUtils.groupTo(actionMappingDos, ActionMappingDO::getProductId);
        return pids
                .stream()
                .map(p -> {
                    List<FunctionMappingDO> functionMappings = statusMappingMap.get(p);
                    List<ActionMappingDO> actionMappings = actionMappingMap.get(p);
                    List<ProductTypeMappingDO> productTypeMappings = productMappingMap.get(p);
                    if (CollectionUtils.isEmpty(functionMappings) || CollectionUtils.isEmpty(productTypeMappings)) {
                        return null;
                    }
                    BaiduProductMapping baiduProductMapping = new BaiduProductMapping();
                    baiduProductMapping.setProductId(p);
                    baiduProductMapping.setThirdProductIds(productTypeMappings.stream().map(ProductTypeMappingDO::getThirdProductId).collect(Collectors.toList()));
                    baiduProductMapping.setThirdPartyCloud(ThirdPartyCloudEnum.BAIDU);
                    baiduProductMapping.setStatusMappings(super.convert(functionMappings));
                    baiduProductMapping.setActionMappings(super.convertActionMapping(actionMappings));
                    return baiduProductMapping;
                })
                .filter(ObjectUtil::isNotNull)
                .collect(Collectors.toList());
    }

    @Override
    protected String getKey() {
        return ThirdPartyCloudEnum.BAIDU.name();
    }

}
