package com.leyunone.cloudcloud.bean.third.baidu;

import lombok.*;

import java.util.Map;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Getter
@Setter
public class DeviceControlRequest {

    private BaiduHeader header;

    private Payload payload;

    @Getter
    @Setter
    public static class Payload{

        private String accessToken;

        private Appliance appliance;

        private Timestamp timestamp;

        private Brightness brightness;

        private DeltaPercentage deltaPercentage;

        private Color color;

        private String colorTemperatureInKelvin;

        private DeltaValue deltaValue;

        private TargetTemperature targetTemperature;

        private FanSpeed fanSpeed;

        private Speed speed;

        private Mode mode;

        private String lockState;

        private Direction direction;

        private String timeInterval;

        private Floor floor;

        private Gear gear;

        private Flow flow;

    }

    @Getter
    @Setter
    public static class Appliance {

        private Map<String,String> additionalApplianceDetails;

        private String applianceId;

    }

    @Getter
    @Setter
    public static class Timestamp {

        private String value;
    }

    @Getter
    @Setter
    public static class Brightness {

        private String value;
    }

    @Getter
    @Setter
    public static class DeltaPercentage {

        private String value;
    }

    @Getter
    @Setter
    public static class DeltaValue {

        private String scale;

        private String value;
    }

    @Getter
    @Setter
    public static class TargetTemperature {
        private String scale;

        private String value;
    }

    @Getter
    @Setter
    public static class FanSpeed {

        private String value;

        private String level;
    }

    @Getter
    @Setter
    public static class Speed {

        private String value;

        private String level;
    }

    @Getter
    @Setter
    public static class Mode {

        private String value;
    }

    @Getter
    @Setter
    public static class Direction {

        private String value;
    }

    @Getter
    @Setter
    public static class Floor {

        private String value;
    }

    @Getter
    @Setter
    public static class Gear {
        private String scale;

        private String value;
    }

    @Getter
    @Setter
    public static class Flow {

        private String action;

        private String select;
    }

    @Getter
    @Setter
    @Builder
    public static class Color{

        private float hue;

        private float saturation;

        private float brightness;

    }

}
