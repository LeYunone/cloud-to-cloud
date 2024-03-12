package com.leyunone.cloudcloud.bean.google;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/23 10:39
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleDevice {

    private String id;

    private String type;

    private List<String> traits;

    private Name name;

    private boolean willReportState;

    private Map<String, Object> attributes;

    private String roomHint;

    private DeviceInfo deviceInfo;

    private List<OtherDevice> otherDeviceIds;

    private CustomData customData;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Name {

        private String name;

        private List<String> defaultNames;

        private List<String> nicknames;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceInfo {

        private String manufacturer;

        private String model;

        private String hwVersion;

        private String swVersion;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OtherDevice {
        private String deviceId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomData {
        private String productId;
    }
}
