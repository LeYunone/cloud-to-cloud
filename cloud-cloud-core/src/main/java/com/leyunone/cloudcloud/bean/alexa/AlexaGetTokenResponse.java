package com.leyunone.cloudcloud.bean.alexa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/7 11:04
 */
@Data
public class AlexaGetTokenResponse {

    private Event event;
    
    public AlexaGetTokenResponse(Event event) {
        this.event = event;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Event {
        private AlexaHeader header;
        private Payload payload;
    }

    public static class Payload {

    }
}
