package com.leyunone.cloudcloud.util;

import com.leyunone.cloudcloud.bean.mapping.BaiduProductMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
public class ConvertUtils {

    public static <T>Map<String, T> convertToMapByProductId(List<ProductMapping> productMappings, Class<T> clazz) {
//        return productMappings
//                .stream()
//                .filter(p -> p instanceof T)
//                .map(p -> (BaiduProductMapping) p)
//                .collect(Collectors.toMap(ProductMapping::getProductId, v -> v, (v1, v2) -> v2));
        return null;
    }
}
