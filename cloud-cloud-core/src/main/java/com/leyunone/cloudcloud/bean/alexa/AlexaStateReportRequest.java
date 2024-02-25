package com.leyunone.cloudcloud.bean.alexa;

import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/26
 */
@Getter
@Setter
public class AlexaStateReportRequest {

    private Directive directive;

    @Getter
    @Setter
    public static class Directive {

        private AlexaHeader header;

        private AlexaEndpoint endpoint;

        private AlexaStateReportRequest.Payload payload;

    }

    @Getter
    @Setter
    public static class Payload {
    }
}
