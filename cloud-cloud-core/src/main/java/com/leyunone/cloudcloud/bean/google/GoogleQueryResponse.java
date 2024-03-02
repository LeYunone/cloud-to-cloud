package com.leyunone.cloudcloud.bean.google;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/27 18:05
 */
@Getter
@Setter
public class GoogleQueryResponse {

    private String requestId;

    private Payload payload;

    public GoogleQueryResponse(String requestId, Payload payload) {
        this.requestId = requestId;
        this.payload = payload;
    }

    @Builder
    @Setter
    @Getter
    public static class Payload {

        /**
         * deviceId: {
         * attr:attrValue
         * }
         */
        private Map<String, Object> devices;
    }
}
