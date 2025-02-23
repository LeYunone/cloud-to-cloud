package com.leyunone.cloudcloud.bean.mapping;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;


/**
 * :)
 *  谷歌产品映射
 * @Author LeYunone
 * @Date 2024/2/23 16:31
 */
@Getter
@Setter
public class GoogleProductMapping extends ProductMapping {

    /**
     * 属性配置
     * 语义+属性内容
     * key:属性实例名
     * value:配置
     */
    private Map<String,Object> attributes;

    /**
     * 技能表
     */
    private List<String> traits; 

    private List<ActionMapping> actionMappings;
}
