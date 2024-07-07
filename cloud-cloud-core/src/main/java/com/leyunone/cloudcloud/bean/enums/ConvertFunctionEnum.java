package com.leyunone.cloudcloud.bean.enums;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.HSVColor;
import com.leyunone.cloudcloud.bean.RGBColor;
import com.leyunone.cloudcloud.bean.RGBWColor;
import com.leyunone.cloudcloud.util.FunctionMethodUtils;
import com.leyunone.cloudcloud.util.ValueUnitUtils;

/**
 * :)
 * 转换函数
 *
 * @Author LeYunone
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
    INT_TO_RGB {
        @Override
        public Object convert(String value) {
            return FunctionMethodUtils.intToRgb(value);
        }
    },
    RGB_TO_INT {
        @Override
        public Object convert(String value) {
            RGBColor rgbColor = JSONObject.parseObject(value, RGBColor.class);
            return FunctionMethodUtils.rgbToInt(rgbColor.getR(), rgbColor.getG(), rgbColor.getB());
        }
    },
    RGBW_TO_HSV {
        @Override
        public Object convert(String value) {
            RGBWColor rgbwColor = JSONObject.parseObject(value, RGBWColor.class);
            return FunctionMethodUtils.rgbwToHSV(rgbwColor.getR(), rgbwColor.getG(), rgbwColor.getB(), rgbwColor.getW());
        }
    },
    HSV_TO_RGBW {
        @Override
        public Object convert(String value) {
            HSVColor hsvColor = JSONObject.parseObject(value, HSVColor.class);
            return FunctionMethodUtils.hsvToRgbw(hsvColor.getHue(), hsvColor.getSaturation(), hsvColor.getValue());
        }
    },
    //线性映射
    LINEAR_1000_TO_10000 {
        @Override
        public Object convert(String value) {
            return FunctionMethodUtils.linearMapping1000To10000(value);
        }
    },
    //摄氏单位赋予
    CELSIUS_UNIT {
        @Override
        public Object convert(String value) {
            return ValueUnitUtils.celsiusUnit(value);
        }
    },

    //把1-100的值反转
    INT_VALUE_REBEL_BY_100 {
        @Override
        public Object convert(String value) {
            int v = Integer.parseInt(value);
            int max = 100;
            return max - v;
        }
    },


    RGB_INT_CONVERTER {
        @Override
        public Object convert(String value) {
            boolean typeJSON = JSONUtil.isTypeJSON(value);
            if (typeJSON) {
                RGBColor rgbColor = JSONObject.parseObject(value, RGBColor.class);
                return FunctionMethodUtils.rgbToInt(rgbColor.getR(), rgbColor.getG(), rgbColor.getB());
            } else {
                return FunctionMethodUtils.intToRgb(value);
            }
        }
    },

    STRING_TO_INT {
        @Override
        public Object convert(String value) {
            int a;
            try {
                a = Integer.parseInt(value);
            } catch (Exception e) {
                a = (int) Double.parseDouble(value);
            }
            return a;
        }

        @Override
        public FunctionOrderEnum order() {
            return FunctionOrderEnum.MAPPING_AFTER;
        }
    },
    STRING_TO_DOUBLE {
        @Override
        public Object convert(String value) {
            return Double.valueOf(value);
        }

        @Override
        public FunctionOrderEnum order() {
            return FunctionOrderEnum.MAPPING_AFTER;
        }
    };

    /**
     * @return
     */
    public FunctionOrderEnum order() {
        return FunctionOrderEnum.MAPPING;
    }


    public abstract Object convert(String value);
}
