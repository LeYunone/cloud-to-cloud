package com.leyunone.cloudcloud.bean.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/22 15:12
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlexaFunctionMapping extends StatusMapping {

    /**
     * 属性语义实例名
     */
    private String instance;

    private String productId;

    private Integer capabilityConfigId;
}
