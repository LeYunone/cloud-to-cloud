package com.leyunone.cloudcloud.bean.third.yingshi;

import lombok.Data;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/8/30 16:20
 */
public class YingshiDeviceInfoResponse extends YingshiCommonInfo {

    private Payload data;

    public Payload getData() {
        return data;
    }

    public YingshiDeviceInfoResponse setData(Payload data) {
        this.data = data;
        return this;
    }

    @Data
    public static class Payload {

        private String deviceSerial;

        private String deviceName;

        private String model;

        private Integer status;

        private Integer defence;

        private Integer isEncrypt;

        private Integer alarmSoundMode;

        private Integer offlineNotify;

        private String category;

        private String netType;

        private String signal;
    }
}
