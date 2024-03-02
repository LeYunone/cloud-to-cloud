package com.leyunone.cloudcloud.bean.enums;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.mapping.StatusMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * :)
 * 谷歌的属性值处理
 *
 * @Author LeYunone
 * @Date 2024/2/28 11:05
 */
public enum GoogleStatusValueEnum {

    CARBONDIOXIDELEVEL("CarbonDioxideLevel") {
        @Override
        public Object valueConvert(String value, StatusMapping mapping) {
            JSONObject data = new JSONObject();
            data.put("name", "CarbonDioxideLevel");
            data.put("rawValue", value);
            return CollectionUtil.newArrayList(data);
        }
    },
    PM25("PM2.5"){
        @Override
        public Object valueConvert(String value, StatusMapping mapping) {
            JSONObject data = new JSONObject();
            data.put("name", "PM2.5");
            data.put("rawValue", value);
            return CollectionUtil.newArrayList(data);
        }
    }
    ;

    private static final Map<String, GoogleStatusValueEnum> ENUM_MAP = new HashMap<>();

    static {
        for (GoogleStatusValueEnum myEnum : GoogleStatusValueEnum.values()) {
            ENUM_MAP.put(myEnum.getName(), myEnum);
        }
    }

    /**
     * 提供默认枚举
     *
     * @param enumName
     * @return
     */
    public static GoogleStatusValueEnum getByEnumName(String enumName) {
        return ENUM_MAP.get(enumName);
    }

    GoogleStatusValueEnum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public abstract Object valueConvert(String value, StatusMapping mapping);
}
