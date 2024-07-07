package com.leyunone.cloudcloud.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.HSVColor;
import com.leyunone.cloudcloud.bean.RGBColor;
import com.leyunone.cloudcloud.bean.RGBWColor;
import com.leyunone.cloudcloud.bean.third.baidu.DeviceControlRequest;

import java.awt.*;

/**
 * :)
 * 函数方法
 * 规则：
 * TODO 所有出参：String = JSON字符串
 * Object = 必须实现其toString()方法
 *
 * @Author LeYunone
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

    public static DeviceControlRequest.Color rgbToHsb(int r, int g, int b) {
        float[] floats = Color.RGBtoHSB(r, g, b, null);
        DeviceControlRequest.Color hsbColor = DeviceControlRequest.Color.builder().hue(floats[0] * 360).saturation(floats[1]).brightness(floats[2]).build();
        return hsbColor;
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

    public static String intToRgb(Object value) {
        int rgbValue = Integer.parseInt(value.toString());
        RGBColor rgbColor = new RGBColor();
        rgbColor.setR((rgbValue & 0xff0000) >> 16);
        rgbColor.setG((rgbValue & 0xff00) >> 8);
        rgbColor.setB(rgbValue & 0xff);
        return JSONObject.toJSONString(rgbColor);
    }

    public static String rgbToInt(int r, int g, int b) {
        return String.valueOf((r << 16) | (g << 8) | b);
    }

    public static HSVColor rgbwToHSV(int red, int green, int blue, int white) {
        float r = red / 255.0f;
        float g = green / 255.0f;
        float b = blue / 255.0f;
        float w = white / 255.0f;
        float max = Math.max(Math.max(Math.max(r, g), b), w);
        float min = Math.min(Math.min(Math.min(r, g), b), w);

        float h = 0, s = 0, v = max;
        float d = max - min;
        s = max == 0 ? 0 : d / max;

        if (max != min) {
            if (max == r) h = (g - b) / d + (g < b ? 6 : 0);
            else if (max == g) h = (b - r) / d + 2;
            else if (max == b) h = (r - g) / d + 4;
            else if (max == w) h = 0;
            h /= 6;
        }

        return HSVColor.builder().hue(h).saturation(s).value(v).build();
    }

    /**
     * ChatCPT
     * @param h
     * @param s
     * @param v
     * @return
     */
    public static String hsvToRgbw(float h, float s, float v) {
        float c = v * s;
        float x = c * (1 - Math.abs((h / 60) % 2 - 1));
        float m = v - c;

        float[] rgb = new float[4];
        switch ((int) h / 60) {
            case 0:
                rgb[0] = c;rgb[1] = x;rgb[2] = 0;rgb[3] = m;
                break;
            case 1:
                rgb[0] = x;rgb[1] = c;rgb[2] = 0;rgb[3] = m;
                break;
            case 2:
                rgb[0] = 0;rgb[1] = c;rgb[2] = x;rgb[3] = m;
                break;
            case 3:
                rgb[0] = 0;rgb[1] = x;rgb[2] = c;rgb[3] = m;
                break;
            case 4:
                rgb[0] = x;rgb[1] = 0;rgb[2] = c;rgb[3] = m;
                break;
            default:
                rgb[0] = c;rgb[1] = 0;rgb[2] = x;rgb[3] = m;
                break;
        }

        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = Math.round((rgb[i] + m) * 255);
        }
        RGBWColor rgbColor = new RGBWColor(result[0], result[1], result[2], result[3]);
        return JSONObject.toJSONString(rgbColor);
    }
}
