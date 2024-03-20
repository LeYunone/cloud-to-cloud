package com.leyunone.cloudcloud.bean.third.alexa;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/26
 */
@Data
@Builder
public class AlexaDeviceCapability {

    /**
     *
     */
    private String type;
    /**
     * 版本
     */
    private String version;

    /**
     * 技能名
     * 原名interface
     */
    private String interfaceStr;

    /**
     * 属性配置
     */
    private Property properties;

    private String instance;

    /**
     * 技能语义资源
     */
    private Object capabilityResources;
    /**
     * 技能语言配置
     */
    private Object configuration;

    private Object semantics;

    @Data
    @Builder
    public static class Property {

        private List<Supported> supported;
        /**
         * 可上报
         */
        private boolean proactivelyReported;
        /**
         * 可检索
         */
        private boolean retrievable;
        /**
         * 可修改
         */
        private boolean nonControllable;
    }

    @Data
    @Builder
    public static class Supported {

        private String name;
    }
}

