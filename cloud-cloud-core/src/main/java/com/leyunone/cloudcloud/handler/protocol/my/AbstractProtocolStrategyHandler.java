package com.leyunone.cloudcloud.handler.protocol.my;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.api.protocol.MyCompanyHeader;
import com.leyunone.cloudcloud.handler.factory.MyProtocolHandlerFactory;
import com.leyunone.cloudcloud.handler.protocol.MyCloudProtocolHandler;
import com.leyunone.cloudcloud.strategy.AbstractStrategyAutoRegisterComponent;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.UUID;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/11 11:38
 */
@Deprecated
public abstract class AbstractProtocolStrategyHandler<R, P> extends AbstractStrategyAutoRegisterComponent implements MyCloudProtocolHandler<R>, InitializingBean {

    private Class<P> pClass;

    protected AbstractProtocolStrategyHandler(MyProtocolHandlerFactory factory) {
        super(factory);
        Class<?> clazzz = getClass();
        Type generic = clazzz.getGenericSuperclass();
        if (generic instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) generic).getActualTypeArguments();
            pClass = (Class<P>) actualTypeArguments[1];
        }
    }

    protected MyCompanyHeader buildHeader(String namespace) {
        MyCompanyHeader myComHeader = new MyCompanyHeader();
        myComHeader.setNamespace(namespace);
        myComHeader.setMessageId(UUID.randomUUID().toString());
        myComHeader.setTimestamp(System.currentTimeMillis());
        return myComHeader;
    }

    @Override
    public R action(String request) {
        return this.handler(JSONObject.parseObject(request, pClass));
    }

    public abstract R handler(P p);
}
