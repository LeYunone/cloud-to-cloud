package com.leyunone.cloudcloud.handler.delay;


import com.leyunone.cloudcloud.strategy.StrategyComponent;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/18 10:45
 */
public interface DelayMessageHandler extends StrategyComponent {

    /**
     * 延时消息处理
     * @param message
     */
    void handle(String message);
}
