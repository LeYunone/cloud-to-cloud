package com.leyunone.cloudcloud.bean.third.alexa;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/26
 */
@Data
public class AlexaDeviceReportBean {

    private Event event;

    private Context context;

    public AlexaDeviceReportBean(Event event, Context context) {
        this.event = event;
        this.context = context;
    }

    @Data
    @Builder
    public static class Event {

        private AlexaHeader header;

        private AlexaEndpoint endpoint;

        private Payload payload;
    }

    @Data
    public static class Payload {
        private Change change;

        public Payload(Change change){
            this.change = change;
        }
    }
    
    @Data
    @Builder
    public static class Change {
        
        private Cause cause;
        
        private List<AlexaDeviceProperty> properties;
    }
    
    @Data
    @Builder
    public static class Cause{
        
        private String type;
    }

    @Data
    @Builder
    public static class Context {

        private List<AlexaDeviceProperty> properties;
    }
}
