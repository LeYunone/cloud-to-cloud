package com.leyunone.cloudcloud.handler.factory;


import com.leyunone.cloudcloud.strategy.StrategyComponent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * :)
 *  非标准流程 获取token的策略工厂
 * @Author LeYunone
 * @Date 2024/9/2 14:14
 */
@Component
public class NoOauth2GetTokenHandlerFactory extends AbstractStrategyFactory {

    private final Map<String,StrategyComponent> stores = new HashMap<>(16);

    @Override
    Map<String, StrategyComponent> strategyStore() {
        return stores;
    }
}
