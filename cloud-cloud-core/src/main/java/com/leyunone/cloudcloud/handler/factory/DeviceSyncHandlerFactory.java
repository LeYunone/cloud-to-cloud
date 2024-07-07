package com.leyunone.cloudcloud.handler.factory;

import com.leyunone.cloudcloud.strategy.StrategyComponent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Service
public class DeviceSyncHandlerFactory extends AbstractStrategyFactory {

    private final Map<String, StrategyComponent> stores = new HashMap<>(16);

    @Override
    Map<String, StrategyComponent> strategyStore() {
        return stores;
    }
}
