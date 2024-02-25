package com.leyunone.cloudcloud.bean.enums;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.RGBColor;
import com.leyunone.cloudcloud.util.FunctionMethodUtils;
import com.leyunone.cloudcloud.util.ValueUnitUtils;

/**
 * :)
 * 转换函数
 *
 * @Author leyunone
 * @Date 2024/2/3 10:16
 */
public enum ConvertFunctionEnum {
    //颜色
    RGB_TO_HEX {
        @Override
        public Object convert(String value) {
            RGBColor rgbColor = JSONObject.parseObject(value, RGBColor.class);
            return FunctionMethodUtils.rgbToHsb(rgbColor.getR(), rgbColor.getG(), rgbColor.getB());
        }
    },
    HEX_TO_RGB {
        @Override
        public Object convert(String value) {
            JSONObject jsonObject = JSONObject.parseObject(value);
            return FunctionMethodUtils.hsbToRgb(jsonObject.getFloat("hue"), jsonObject.getFloat("saturation"), jsonObject.getFloat("brightness"));
        }
    },
    //线性映射
    LINEAR_1000_TO_10000 {
        @Override
        public Object convert(String value) {
            return FunctionMethodUtils.linearMapping1000To10000(value);
        }
    },


    //单位赋予
    CELSIUS_UNIT {
        @Override
        public Object convert(String value) {
            return ValueUnitUtils.celsiusUnit(value);
        }
    };


    public abstract Object convert(String value);
}
