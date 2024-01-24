package com.leyunone.cloudcloud.annotate;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Strategist {

    @AliasFor("strategy")
    String value() default "";

    @AliasFor("value")
    String strategy() default "";
}
