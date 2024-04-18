package com.leyunone.cloudcloud.strategy;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.annotate.Strategist;
import com.leyunone.cloudcloud.handler.factory.StrategyFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.List;
import java.util.Objects;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public abstract class AbstractStrategyAutoRegisterComponent implements InitializingBean, StrategyComponent {

    public final StrategyFactory factory;

    protected AbstractStrategyAutoRegisterComponent(StrategyFactory factory) {
        this.factory = factory;
    }

    /**
     * @return 策略key
     */
    protected String getKey() {
        Strategist annotation = AnnotationUtils.getAnnotation(this.getClass(), Strategist.class);
        Objects.requireNonNull(annotation, "handler not set handler strategy");
        return annotation.strategy();
    }

    protected List<String> getKeys() {
        return CollectionUtil.newArrayList(getKey());
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        getKeys().forEach(key -> factory.register(key, this));
    }
}
