package com.leyunone.cloudcloud.service.mapping;

import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
public interface ProductMappingService {

    /**
     * 获取产品映射关系
     *
     * @param productIs
     * @param cloud
     * @return
     */
    List<ProductMapping> getMapping(List<String> productIs, ThirdPartyCloudEnum cloud);

    List<ProductMapping> getMapping(String productId, ThirdPartyCloudEnum cloud);

}
