package com.leyunone.cloudcloud.handler.factory;

import com.leyunone.cloudcloud.strategy.StrategyComponent;

import java.util.Map;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public abstract class AbstractStrategyFactory implements StrategyFactory {

    abstract Map<String, StrategyComponent> strategyStore();

    @Override
    public void register(String key, StrategyComponent t) {
        strategyStore().put(key, t);
    }

    @Override
    public <T>T getStrategy(String key, Class<T> tClass) {
        return (T)strategyStore().get(key);
    }
}
