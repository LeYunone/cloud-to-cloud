package com.leyunone.cloudcloud.bean.enums;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.mapping.ActionMapping;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaControlRequest;
import com.leyunone.cloudcloud.bean.mapping.AlexaProductMapping;
import com.leyunone.cloudcloud.util.FunctionMethodUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/1 17:54
 */
public enum AlexaActionValueEnum {

    SET_BRIGHTNESS("SetBrightness") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getBrightness(), capability);
        }
    },

    ADJUST_BRIGHTNESS("AdjustBrightness") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getBrightnessDelta(), capability);
        }
    },

    SET_COLOR("SetColor") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            AlexaControlRequest.Color color = payload.getColor();
            return FunctionMethodUtils.hsbToRgb(color.getHue() / 360, color.getSaturation(), color.getBrightness());
        }
    },


    SET_COLOR_TEMPERATURE("SetColorTemperature") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getColorTemperatureInKelvin(), capability);
        }
    },

    INCREASE_COLOR_TEMPERATURE("IncreaseColorTemperature") {
    },

    DECREASE_COLOR_TEMPERATURE("DecreaseColorTemperature") {
    },

    SET_PERCENTAGE("SetPercentage") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getPercentage(), capability);
        }
    },

    ADJUST_PERCENTAGE("AdjustPercentage") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getPercentageDelta(), capability);
        }
    },

    TURN_ON("TurnOn"),

    TURN_OFF("TurnOff"),

    SET_POWER_LEVEL("SetPowerLevel") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getPowerLevel(), capability);
        }
    },

    ADJUST_POWER_LEVEL("AdjustPowerLevel") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getPowerLevelDelta(), capability);
        }
    },

    SET_RANGE_VALUE("SetRangeValue") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getRangeValue(), capability);
        }
    },

    ADJUST_RANGE_VALUE("AdjustRangeValue") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getRangeValue(), capability);
        }
    },

    SET_VOLUME("SetVolume") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getVolume(), capability);
        }
    },

    ADJUST_VOLUME("AdjustVolume") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getVolume(), capability);
        }
    },

    SET_MUTE("SetMute") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.isMute(), capability);
        }
    },

    SET_TARGET_TEMPERATURE("SetTargetTemperature") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getTargetSetpoint().getValue(), capability);
        }
    },

    ADJUST_TARGET_TEMPERATURE("AdjustTargetTemperature") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getTargetSetpointDelta().getValue(), capability);
        }
    },

    SET_THERMOSTAT_MODE("SetThermostatMode") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getThermostatMode().getValue(), capability);
        }
    },
    SET_MODE("SetMode") {
        @Override
        public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
            return this.valueOfConvert(payload.getMode(), capability);
        }
    };

    private static final Map<String, AlexaActionValueEnum> ENUM_MAP = new HashMap<>();

    static {
        for (AlexaActionValueEnum myEnum : AlexaActionValueEnum.values()) {
            ENUM_MAP.put(myEnum.getName(), myEnum);
        }
    }

    public static AlexaActionValueEnum getByEnumName(String enumName) {
        return ENUM_MAP.get(enumName);
    }

    AlexaActionValueEnum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public String valueConvert(AlexaControlRequest.Payload payload, ActionMapping capability) {
        return this.noValueConvert(capability);
    }

    protected String valueOfConvert(Object value, ActionMapping capability) {
        Map<String, Object> valueMapping = capability.getValueMapping();
        if (capability.getValueOf()) {
            value = valueMapping.get(value).toString();
        }
        return String.valueOf(value);
    }

    protected String noValueConvert(ActionMapping capability) {
        Map<String, Object> valueMapping = capability.getValueMapping();
        String value = "";
        if (CollectionUtil.isNotEmpty(valueMapping)) {
            Object first = CollectionUtil.getFirst(valueMapping.values());
            value = first.toString();
        }else{
            value = capability.getDefaultValue();
        }
        return value;
    }
}
