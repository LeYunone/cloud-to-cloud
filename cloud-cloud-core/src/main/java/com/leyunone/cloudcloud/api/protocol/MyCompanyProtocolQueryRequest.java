package com.leyunone.cloudcloud.api.protocol;

import lombok.*;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/29 16:10
 */
@Getter
@Setter
public class MyCompanyProtocolQueryRequest {

    private Payload payload;
    
    private MyCompanyHeader header;

    public MyCompanyProtocolQueryRequest(MyCompanyHeader header, Payload payload) {
        this.header = header;
        this.payload = payload;
    }


    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Payload{
        private String accessToken;
        private List<DeviceInfo> devices;
    }
    
    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class DeviceInfo{
        private String deviceId;
        private String homeId;
        private String groupId;
        private String additionalAppliance;
    }
}
