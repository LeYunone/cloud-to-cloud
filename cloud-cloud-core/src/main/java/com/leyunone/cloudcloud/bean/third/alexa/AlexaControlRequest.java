package com.leyunone.cloudcloud.bean.third.alexa;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/26
 */
@Getter
@Setter
public class AlexaControlRequest {

    private Directive directive;

    @Getter
    @Setter
    public static class Directive {

        private AlexaHeader header;

        private AlexaEndpoint endpoint;

        private JSONObject payload;
    }

    @Getter
    @Setter
    @Deprecated
    public static class Payload {

        private Color color;

        private Integer colorTemperatureInKelvin;

        private Integer brightness;

        private Integer brightnessDelta;

        private Integer percentage;

        private Integer percentageDelta;

        private Integer powerLevel;

        private Integer powerLevelDelta;

        private Integer rangeValue;

        private Integer rangeValueDelta;

        private Integer volume;

        private boolean volumeDefault;

        private boolean mute;

        private TargetSetpoint targetSetpoint;

        private TargetSetpointDelta targetSetpointDelta;
        
        private ThermostatMode thermostatMode;
        
        private String mode;
    }

    @Getter
    @Setter
    public static class Color {

        private float hue;

        private float saturation;

        private float brightness;
    }

    @Getter
    @Setter
    public static class TargetSetpoint {

        private float value;

        private String scale;
    }

    @Getter
    @Setter
    public static class TargetSetpointDelta {

        private float value;

        private String scale;
    }

    @Getter
    @Setter
    public static class ThermostatMode {

        private String value;
    }
}
