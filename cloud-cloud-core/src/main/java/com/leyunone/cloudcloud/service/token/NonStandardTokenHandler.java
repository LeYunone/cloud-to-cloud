package com.leyunone.cloudcloud.service.token;


import com.leyunone.cloudcloud.bean.ClientAccessTokenModel;
import com.leyunone.cloudcloud.strategy.StrategyComponent;

/**
 * :)
 * 非标准令牌执行器
 *
 * @Author LeYunone
 * @Date 2024/9/2 14:17
 */
public interface NonStandardTokenHandler extends StrategyComponent {

    ClientAccessTokenModel get(String clientId);

    /**
     * 投递循环
     * @param clientId
     * @param time
     */
    void deliveryLoop(String clientId, Long time);
}
