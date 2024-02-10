package com.leyunone.cloudcloud.bean.enums;

import com.alibaba.fastjson.JSON;
import com.leyunone.cloudcloud.bean.RGBColor;
import com.leyunone.cloudcloud.bean.baidu.DeviceControlRequest;
import com.leyunone.cloudcloud.bean.mapping.ActionMapping;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-09
 */
public enum BaiduCommandEnums {

    /**
     * 开
     */
    TURN_ON("TurnOn") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            return noValueConvert(actionMapping);
        }
    },

    TURN_OFF("TurnOff") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            return noValueConvert(actionMapping);
        }
    },

    PAUSE("Pause") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            return noValueConvert(actionMapping);
        }
    },

    CONTINUE("Continue") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            return noValueConvert(actionMapping);
        }
    },

    STARTUP("StartUp") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            return noValueConvert(actionMapping);
        }
    },

    SET_BRIGHTNESS_PERCENTAGE("SetBrightnessPercentage") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.Brightness brightness = payload.getBrightness();
            String value = brightness.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },

    INCREMENT_BRIGHTNESS_PERCENTAGE("IncrementBrightnessPercentage") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.DeltaPercentage deltaPercentage = payload.getDeltaPercentage();
            String value = deltaPercentage.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },


    DECREMENT_BRIGHTNESS_PERCENTAGE("DecrementBrightnessPercentage") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.DeltaPercentage deltaPercentage = payload.getDeltaPercentage();
            String value = deltaPercentage.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },


    SET_COLOR("SetColor") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.Color color = payload.getColor();
            /**
             * rbg to -> hsb
             * 百度hue范围值 0-360
             */
            Color value = Color.getHSBColor(color.getHue() / 360, color.getSaturation(), color.getBrightness());
            int red = value.getRed();
            int green = value.getGreen();
            int blue = value.getBlue();
            RGBColor rgbColor = new RGBColor(red, green, blue);
            return JSON.toJSONString(rgbColor);
        }
    },

    SET_COLOR_TEMPERATURE("SetColorTemperature") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            String value = payload.getColorTemperatureInKelvin();
            /**
             * 百度 w 1000-10000
             * 转换为
             * iot 0-6500
             */
            int w = Integer.parseInt(value);

            int minW = 1000;
            int maxW = 10000;

            int minTarget = 0;
            int maxTarget = 6500;

            // 线性映射
            int convertedW = (int) (((float) (w - minW) / (maxW - minW)) * (maxTarget - minTarget) + minTarget);
            return String.valueOf(convertedW);
        }

    },


    INCREMENT_COLOR_TEMPERATURE("IncrementColorTemperature") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.DeltaPercentage deltaPercentage = payload.getDeltaPercentage();
            String value = deltaPercentage.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },

    DECREMENT_COLOR_TEMPERATURE("DecrementColorTemperature") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.DeltaPercentage deltaPercentage = payload.getDeltaPercentage();
            String value = deltaPercentage.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },

    INCREMENT_TEMPERATURE("IncrementTemperature") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.DeltaValue deltaValue = payload.getDeltaValue();
            String value = deltaValue.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },

    DECREMENT_TEMPERATURE("DecrementTemperature") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.DeltaValue deltaValue = payload.getDeltaValue();
            String value = deltaValue.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },

    SET_TEMPERATURE("SetTemperature") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.TargetTemperature targetTemperature = payload.getTargetTemperature();
            String value = targetTemperature.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },

    INCREMENT_FAN_SPEED("IncrementFanSpeed") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.DeltaValue deltaValue = payload.getDeltaValue();
            String value = deltaValue.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },

    DECREMENT_FAN_SPEED("DecrementFanSpeed") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.DeltaValue deltaValue = payload.getDeltaValue();
            String value = deltaValue.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },

    SET_FAN_SPEED("SetFanSpeed") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.FanSpeed fanSpeed = payload.getFanSpeed();
            String value = fanSpeed.getValue();
            if (StringUtils.isEmpty(value)) {
                value = fanSpeed.getLevel();
            }
            return valueOfConvert(value, actionMapping);
        }
    },

    Set_Mode("SetMode") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.Mode mode = payload.getMode();
            String value = mode.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },

    INCREMENT_VOLUME("IncrementVolume") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.DeltaValue deltaValue = payload.getDeltaValue();
            String value = deltaValue.getValue();
            return valueOfConvert(value, actionMapping);
        }

    },

    DECREMENT_VOLUME("DecrementVolume") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.DeltaValue deltaValue = payload.getDeltaValue();
            String value = deltaValue.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },

    SET_VOLUME("SetVolume") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            DeviceControlRequest.DeltaValue deltaValue = payload.getDeltaValue();
            String value = deltaValue.getValue();
            return valueOfConvert(value, actionMapping);
        }
    },

    SET_LOCK_STATE("SetLockState") {
        @Override
        public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
            String value = payload.getLockState();
            return valueOfConvert(value, actionMapping);
        }
    };

    private final String name;

    public String getName() {
        return name;
    }

    BaiduCommandEnums(String name) {
        this.name = name;
    }

    public String valueConvert(DeviceControlRequest.Payload payload, ActionMapping actionMapping) {
        return null;
    }

    public String noValueConvert(ActionMapping actionMapping) {
        Map<String, Object> valueMapping = actionMapping.getValueMapping();
        Optional<Object> first = valueMapping.values().stream().findFirst();
        String value = "";
        if (first.isPresent()) {
            value = first.get().toString();
        }
        return value;
    }

    public String valueOfConvert(String value, ActionMapping actionMapping) {
        Map<String, Object> valueMapping = actionMapping.getValueMapping();
        if (actionMapping.getValueOf()) {
            value = valueMapping.get(value).toString();
        }
        return value;
    }
    
    private final static Map<String, BaiduCommandEnums> MAP;


    static {
        MAP = Arrays.stream(BaiduCommandEnums.values()).collect(Collectors.toMap(BaiduCommandEnums::getName, v -> v));
    }

    public static BaiduCommandEnums getByName(String name) {
        return MAP.get(name);
    }
}
