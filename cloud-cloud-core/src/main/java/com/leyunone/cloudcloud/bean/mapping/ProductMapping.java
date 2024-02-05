package com.leyunone.cloudcloud.bean.mapping;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
@Getter
@Setter
public class ProductMapping {

    private String productId;
    
    private List<StatusMapping> statusMappings;
    
    private List<String> thirdProductIds;
}
