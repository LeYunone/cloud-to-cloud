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
@Data
public class AlexaStateReportResponse {

    private Event event;

    private Context context;

    public AlexaStateReportResponse(Event event, Context context) {
        this.event = event;
        this.context = context;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Event {

        private AlexaHeader header;

        private AlexaEndpoint endpoint;

        private AlexaDiscoveryResponse.Payload payload;
    }

    @Getter
    @Setter
    public static class Payload {

        private AlexaDevice endpoints;
    }

    @Getter
    @Setter
    @Builder
    public static class Context {

        private List<AlexaDeviceProperty> properties;
    }
}
