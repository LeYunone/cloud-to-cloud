package com.leyunone.cloudcloud.bean.mapping;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
@Getter
@Setter
public class ActionMapping {

    private String action;

    private Integer functionId;

    private String signCode;

    /**
     * 不为空的时候采用默认值
     */
    private String defaultValue;

    private Boolean valueOf;

    /**
     * 
     * 示例 {"1":"on","0":"off"} 
     */
    private Map<String, Object> valueMapping;
}
