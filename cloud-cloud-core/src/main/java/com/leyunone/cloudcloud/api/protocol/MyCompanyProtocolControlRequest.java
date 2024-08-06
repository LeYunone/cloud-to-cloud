package com.leyunone.cloudcloud.api.protocol;

import lombok.*;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/29 14:06
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MyCompanyProtocolControlRequest {

    private Payload payload;
    
    private MyCompanyHeader header;

    public MyCompanyProtocolControlRequest(MyCompanyHeader header, Payload payload) {
        this.header = header;
        this.payload = payload;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payload {

        private String accessToken;

        private List<Command> commands;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Command {

        private Long timestamp;

        private String deviceId;
        
        private String groupId;

        private String signCode;

        private String data;

        private String additionalAppliance;
    }
}
