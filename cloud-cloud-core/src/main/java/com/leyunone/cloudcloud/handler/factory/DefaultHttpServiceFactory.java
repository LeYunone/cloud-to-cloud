package com.leyunone.cloudcloud.handler.factory;

import com.leyunone.cloudcloud.strategy.StrategyComponent;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/5 14:54
 */
@Service
public class DefaultHttpServiceFactory extends AbstractStrategyFactory {

    private final Map<String, StrategyComponent> CACHE = new ConcurrentHashMap<>(16);

    @Override
    Map<String, StrategyComponent> strategyStore() {
        return CACHE;
    }
}
