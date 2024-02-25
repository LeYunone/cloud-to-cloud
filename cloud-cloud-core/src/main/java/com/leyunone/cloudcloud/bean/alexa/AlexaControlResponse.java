package com.leyunone.cloudcloud.bean.alexa;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
public class AlexaControlResponse {

    private Event event;

    private Context context;

    public AlexaControlResponse(Event event, Context context) {
        this.event = event;
        this.context = context;
    }

    @Getter
    @Setter
    @Builder
    public static class Event {

        private AlexaHeader header;

        private AlexaEndpoint endpoint;

        private Payload payload;
    }

    @Getter
    @Setter
    @Builder
    public static class Context {

        private List<AlexaDeviceProperty> properties;
    }

    @Getter
    @Setter
    @Builder
    public static class Payload {

    }
}
