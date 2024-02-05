package com.leyunone.cloudcloud.service.mapping;

import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
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
    @Override
    public List<ProductMapping> getMapping(List<String> productIs, ThirdPartyCloudEnum cloud) {
        return null;
    }

    @Override
    public List<ProductMapping> getMapping(String productId, ThirdPartyCloudEnum cloud) {
        return null;
    }
}
