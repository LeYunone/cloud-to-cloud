package com.leyunone.cloudcloud.api.protocol;

import lombok.*;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/31 10:55
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MyCompanyProtocolDiscoveryRequest {

    private Payload payload;

    private MyCompanyHeader header;

    public MyCompanyProtocolDiscoveryRequest(MyCompanyHeader header, Payload payload) {
        this.header = header;
        this.payload = payload;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payload {

        private String accessToken;

        private String myComUid;

        private String additional;
    }
}
