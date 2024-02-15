package com.leyunone.cloudcloud.handler.mapping;

import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.strategy.StrategyComponent;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface MappingAssembler<R extends ProductMapping> extends StrategyComponent {

    /**
     *
     * @param productIds
     * @return
     */
    List<R> assembler(List<String> productIds);

}
