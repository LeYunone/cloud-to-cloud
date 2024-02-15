package com.leyunone.cloudcloud.util;

import com.alibaba.fastjson.JSON;
import com.leyunone.cloudcloud.bean.RGBColor;

import java.awt.*;

/**
 * :)
 * 函数方法
 *
 * @Author leyunone
 * @Date 2024/2/2 15:24
 */
public class FunctionMethodUtils {

    public static String hsbToRgb(float hue, float saturation, float brightness) {
        Color value = Color.getHSBColor(hue, saturation, brightness);
        int red = value.getRed();
        int green = value.getGreen();
        int blue = value.getBlue();
        RGBColor rgbColor = new RGBColor(red, green, blue);
        return JSON.toJSONString(rgbColor);
    }

    public static String rgbToHsb(int r, int g, int b) {
        float[] floats = Color.RGBtoHSB(r, g, b, null);
//        DeviceControlRequest.Color hsbColor = DeviceControlRequest.Color
//                .builder().hue(floats[0] * 360).saturation(floats[1]).brightness(floats[2]).build();
        return null;
    }

    /**
     * 线性映射1000-10000
     *
     * @param value
     * @return
     */
    public static String linearMapping1000To10000(String value) {
        int minW = 0;
        int maxW = 6500;

        int minTarget = 1000;
        int maxTarget = 10000;

        int convertedW = (int) (((float) (Integer.parseInt(value) - minW) / (maxW - minW)) * (maxTarget - minTarget) + minTarget);
        return String.valueOf(convertedW);
    }


}
