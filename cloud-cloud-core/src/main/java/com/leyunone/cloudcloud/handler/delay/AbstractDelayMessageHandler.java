package com.leyunone.cloudcloud.handler.delay;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.handler.factory.DelayMessageHandlerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/18 10:46
 */
public abstract class AbstractDelayMessageHandler<T> implements DelayMessageHandler, InitializingBean {

    private final DelayMessageHandlerFactory delayMessageHandlerFactory;
    private Class<T> pClass;

    public AbstractDelayMessageHandler(DelayMessageHandlerFactory delayMessageHandlerFactory) {
        this.delayMessageHandlerFactory = delayMessageHandlerFactory;
        Class<?> clazzz = getClass();
        Type generic = clazzz.getGenericSuperclass();
        if (generic instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) generic).getActualTypeArguments();
            //DO
            pClass = (Class<T>) actualTypeArguments[0];
        }
    }

    @Override
    public void handle(String message) {
        this.handle1(JSONObject.parseObject(message, pClass));
    }

    public abstract void handle1(T t);

    abstract String getKey();

    @Override
    public void afterPropertiesSet() throws Exception {
        delayMessageHandlerFactory.register(getKey(), this);
    }
}
