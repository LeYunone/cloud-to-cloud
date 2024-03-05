package com.leyunone.cloudcloud.bean.alexa;

import lombok.Getter;
import lombok.Setter;

/**
 * :)
 * 亚马逊标准请求头
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/26
 */
@Getter
@Setter
public class AlexaStandardRequest {

    private Directive directive;

    @Getter
    @Setter
    public static class Directive {

        private AlexaHeader header;

        private Payload payload;

        private Endpoint endpoint;

    }

    @Getter
    @Setter
    public static class Endpoint {

        private Scope scope;
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
