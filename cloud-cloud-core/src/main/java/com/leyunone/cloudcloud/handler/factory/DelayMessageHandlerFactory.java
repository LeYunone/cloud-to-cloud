package com.leyunone.cloudcloud.handler.factory;

import com.leyunone.cloudcloud.strategy.StrategyComponent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/18 10:47
 */
@Component
public class DelayMessageHandlerFactory extends AbstractStrategyFactory {

    private final Map<String, StrategyComponent> stores = new HashMap<>(16);

    @Override
    Map<String, StrategyComponent> strategyStore() {
        return stores;
    }
}
