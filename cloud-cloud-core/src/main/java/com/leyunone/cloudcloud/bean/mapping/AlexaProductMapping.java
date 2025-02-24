package com.leyunone.cloudcloud.bean.mapping;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/31 11:32
 */
@Data
@NoArgsConstructor
public class AlexaProductMapping extends ProductMapping {

    /**
     * 技能表
     */
    private List<Capability> capabilityList;
    
    private List<AlexaFunctionMapping> alexaFunctionMappings;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Capability implements Serializable {

        /**
         * 实例名
         */
        private String instance;

        /**
         * 接口名 = alexa中的namespace
         */
        private String thirdActionCode;

        /**
         * https://developer.amazon.com/en-US/docs/alexa/device-apis/resources-and-assets.html#capability-resources
         * https://developer.amazon.com/en-US/docs/alexa/device-apis/alexa-modecontroller.html#semantics
         */
        /**
         * 能力语义
         */
        private JSONObject capabilityResources;
        /**
         * 能力语言配置
         */
        private JSONObject configuration;

        /**
         * 值映射语义
         */
        private JSONObject semantics;

        /**
         * 支持的属性值
         */
        private List<String> supportAttr;

        /**
         * 技能表映射
         * key = thirdSignCode alexa中的name
         */
        private Map<String, ActionMapping> capabilityMapping = new HashMap<>();
    }
}
