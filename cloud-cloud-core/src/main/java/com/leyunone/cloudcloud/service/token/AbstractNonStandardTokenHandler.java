package com.leyunone.cloudcloud.service.token;

import com.leyunone.cloudcloud.handler.factory.NoOauth2GetTokenHandlerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * :)
 * 抽象初始化方法
 *
 * @Author LeYunone
 * @Date 2024/9/2 14:23
 */
public abstract class AbstractNonStandardTokenHandler implements NonStandardTokenHandler, InitializingBean {

    private final NoOauth2GetTokenHandlerFactory factory;

    protected AbstractNonStandardTokenHandler(NoOauth2GetTokenHandlerFactory factory) {
        this.factory = factory;
    }

    @Override
    public void deliveryLoop(String clientId, Long time) {

    }

    abstract String getKey();

    @Override
    public void afterPropertiesSet() {
        factory.register(getKey(), this);
    }
}
