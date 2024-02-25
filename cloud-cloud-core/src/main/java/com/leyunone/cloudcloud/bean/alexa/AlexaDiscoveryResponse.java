package com.leyunone.cloudcloud.bean.alexa;

import lombok.*;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/26
 */
@Getter
@Setter
public class AlexaDiscoveryResponse {
    
    private Event event;

    public AlexaDiscoveryResponse(Event event){
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
    }
    
    @Getter
    @Setter
    public static class Payload {
        
        private List<AlexaDevice> endpoints;
    }
}
