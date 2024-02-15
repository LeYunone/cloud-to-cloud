package com.leyunone.cloudcloud.util;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.Celsius;

public class ValueUnitUtils {

    public static String celsiusUnit(String value) {
        return JSONObject.toJSONString(new Celsius(Float.parseFloat(value)));
    }
}
