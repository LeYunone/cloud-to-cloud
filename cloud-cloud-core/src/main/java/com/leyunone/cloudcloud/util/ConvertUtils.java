package com.leyunone.cloudcloud.util;

import com.leyunone.cloudcloud.bean.mapping.ProductMapping;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
public class ConvertUtils {

    public static <T extends ProductMapping> Map<String, T> convertToMapByProductId(List<ProductMapping> productMappings) {
        return productMappings
                .stream()
                .filter(Objects::nonNull)
                .map(p -> (T) p)
                .collect(Collectors.toMap(ProductMapping::getProductId, v -> v, (v1, v2) -> v2));
    }
}
