package com.leyunone.cloudcloud.bean.third.alexa;

import lombok.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/26
 */
@Getter
@Setter
public class AlexaErrorResponse {

    private Event event;

    public AlexaErrorResponse(Event event) {
        this.event = event;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Event {

        private AlexaHeader header;

        private Payload payload;

        private AlexaEndpoint endpoint;
        
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payload {

        private String type;

        private String message;
        
        private String reason;
    }
}
