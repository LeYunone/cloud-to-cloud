package com.leyunone.cloudcloud.bean.third.alexa;

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
public class AlexaDiscoveryRequest {

    private Directive directive;

    @Getter
    @Setter
    public static class Directive {

        private AlexaHeader header;

        private Payload payload;

    }

    @Getter
    @Setter
    public static class Payload {

        private Scope scope;
    }

    @Getter
    @Setter
    public static class Scope {

        private String type;

        private String token;
    }
}
