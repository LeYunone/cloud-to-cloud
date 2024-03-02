package com.leyunone.cloudcloud.bean.enums;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.cloudcloud.bean.mapping.ActionMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/28 11:05
 */
public enum GoogleActionValueEnum {

    DEFAULT("default");

    private static final Map<String, GoogleActionValueEnum> ENUM_MAP = new HashMap<>();

    static {
        for (GoogleActionValueEnum myEnum : GoogleActionValueEnum.values()) {
            ENUM_MAP.put(myEnum.getName(), myEnum);
        }
    }

    /**
     * 提供默认枚举
     *
     * @param enumName
     * @return
     */
    public static GoogleActionValueEnum getByEnumName(String enumName) {
        return ENUM_MAP.getOrDefault(enumName, DEFAULT);
    }

    GoogleActionValueEnum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    /**
     * 执行顺序：
     * 1、拿到理想值
     * 2、转换函数
     * 3、映射关系
     *
     * @param value
     * @param actionMapping
     * @return
     */
    public String valueConvert(Object value, ActionMapping actionMapping) {
        value = getValue(value, actionMapping);
        if (ObjectUtil.isNotNull(actionMapping.getConvertFunction())) {
            value = actionMapping.getConvertFunction().convert(value.toString());
        }
        if (actionMapping.getValueOf()) {
            //转化
            value = actionMapping.getValueMapping().get(String.valueOf(value)).toString();
        }
        return value.toString();
    }

    /**
     * 当不满意三方值时重写
     *
     * @param value
     * @return
     */
    Object getValue(Object value, ActionMapping actionMapping) {
        if (ObjectUtil.isNull(value)) {
            //直接的指令打过来时
            Map<String, Object> valueMapping = actionMapping.getValueMapping();
            if (CollectionUtil.isNotEmpty(valueMapping)) {
                Object first = CollectionUtil.getFirst(valueMapping.values());
                value = first.toString();
            } else {
                value = actionMapping.getDefaultValue();
            }
        }
        return value;
    }
}
