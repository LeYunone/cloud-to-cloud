package com.leyunone.cloudcloud.service.mapping;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.MappingAssemblerFactory;
import com.leyunone.cloudcloud.handler.mapping.MappingAssembler;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
@Service
public class ProductMappingServiceImpl implements ProductMappingService {

    private final MappingAssemblerFactory mappingAssemblerFactory;

    public ProductMappingServiceImpl(MappingAssemblerFactory mappingAssemblerFactory) {
        this.mappingAssemblerFactory = mappingAssemblerFactory;
    }

    @Override
    public List<ProductMapping> getMapping(List<String> productIs, ThirdPartyCloudEnum cloud) {
        MappingAssembler strategy = mappingAssemblerFactory.getStrategy(cloud.name(), MappingAssembler.class);
        return strategy.assembler(productIs);
    }

    @Override
    public List<ProductMapping> getMapping(String productId, ThirdPartyCloudEnum cloud) {
        return this.getMapping(CollectionUtil.newArrayList(productId), cloud);
    }
}
