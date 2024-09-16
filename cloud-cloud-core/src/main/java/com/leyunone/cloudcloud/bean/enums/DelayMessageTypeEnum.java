package com.leyunone.cloudcloud.bean.enums;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/18 10:58
 */
public enum DelayMessageTypeEnum {

    /**
     * oauth2标准刷新令牌
     */
    OAUTH2_REFRESH_TOKEN,
    /**
     * 同上 兼容旧数据
     */
    @Deprecated
    REFRESH_TOKEN,

    /**
     * 非标准循环全局令牌
     */
    LOOP_OVERALL_TOKEN
}
