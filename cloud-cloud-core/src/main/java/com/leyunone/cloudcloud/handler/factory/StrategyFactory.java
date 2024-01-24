package com.leyunone.cloudcloud.handler.factory;

import com.leyunone.cloudcloud.strategy.StrategyComponent;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public interface StrategyFactory {

    void register(String key, StrategyComponent t);

    StrategyComponent getStrategy(String key);
}
