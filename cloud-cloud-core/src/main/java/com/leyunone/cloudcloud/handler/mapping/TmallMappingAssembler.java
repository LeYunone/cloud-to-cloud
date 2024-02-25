package com.leyunone.cloudcloud.handler.mapping;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.leyunone.cloudcloud.bean.mapping.TmallProductMapping;
import com.leyunone.cloudcloud.dao.FunctionMappingRepository;
import com.leyunone.cloudcloud.dao.ProductTypeMappingRepository;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.MappingAssemblerFactory;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 * 天猫
 *
 * @Author LeYunone
 * @Date 2024/1/17 16:11
 */
@Service
public class TmallMappingAssembler extends AbstractStrategyMappingAssembler<TmallProductMapping> {

    private final ProductTypeMappingRepository productTypeMappingRepository;
    private final FunctionMappingRepository functionMappingRepository;

    protected TmallMappingAssembler(MappingAssemblerFactory factory, FunctionMappingRepository functionMappingRepository, CacheManager cacheManager, ProductTypeMappingRepository productTypeMappingRepository, FunctionMappingRepository functionMappingRepository1) {
        super(factory, functionMappingRepository, cacheManager);
        this.productTypeMappingRepository = productTypeMappingRepository;
        this.functionMappingRepository = functionMappingRepository1;
    }


    @Override
    protected String getKey() {
        return ThirdPartyCloudEnum.TMALL.name();
    }

    @Override
    protected List<TmallProductMapping> dataGet(List<String> pids) {
        List<FunctionMappingDO> functionMappingDos = functionMappingRepository.selectByProductIdsAndThirdPartyCloud(pids, ThirdPartyCloudEnum.TMALL.name());
        Map<String, List<FunctionMappingDO>> functionMappingMap = functionMappingDos
                .stream()
                .collect(Collectors.groupingBy(FunctionMappingDO::getProductId, Collectors.toList()));
        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByProductIds(pids, ThirdPartyCloudEnum.TMALL.name());
        Map<String, List<ProductTypeMappingDO>> typeMap = productTypeMappingDOS.stream().collect(Collectors.groupingBy(ProductTypeMappingDO::getProductId));
        return pids
                .stream()
                .map(p -> {
                    List<FunctionMappingDO> functionMappings = functionMappingMap.get(p);
                    if (CollectionUtils.isEmpty(functionMappings)) {
                        return null;
                    }
                    List<ProductTypeMappingDO> productTypes = typeMap.get(p);
                    TmallProductMapping productMapping = new TmallProductMapping();
                    productMapping.setProductId(p);
                    productMapping.setThirdProductIds(productTypes.stream().map(ProductTypeMappingDO::getThirdProductId).distinct().collect(Collectors.toList()));
                    productMapping.setThirdPartyCloud(ThirdPartyCloudEnum.TMALL);
                    productMapping.setStatusMappings(convert(functionMappings));
                    /**
                     * TODO 猫精支持的设备别名列表参考：https://open.bot.tmall.com/oauth/api/aliaslist 推荐使用猫精支持的设备别名，否则语音无法识别
                     * 预留处理
                     * @see TmallDevice#deviceName
                     */
                    if (CollectionUtil.isNotEmpty(productTypes)) {
                        productMapping.setThirdProductIds(productTypes.stream().map(ProductTypeMappingDO::getThirdProductId).distinct().collect(Collectors.toList()));
                        productMapping.setProductName(productTypes.stream().filter(t -> StrUtil.isNotBlank(t.getThirdProduct()))
                                .map(ProductTypeMappingDO::getThirdProduct).findFirst().orElse(null));
                        productMapping.setDeviceTypeEnName(productTypes.stream().filter(t -> StrUtil.isNotBlank(t.getThirdProduct2()))
                                .map(ProductTypeMappingDO::getThirdProduct2).findFirst().orElse(null));
                        productMapping.setBrand(productTypes.stream().filter(t -> StrUtil.isNotBlank(t.getThirdBrand()))
                                .map(ProductTypeMappingDO::getThirdBrand).findFirst().orElse(null));
                    }
                    return productMapping;
                })
                .filter(ObjectUtil::isNotNull)
                .collect(Collectors.toList());
    }
}
