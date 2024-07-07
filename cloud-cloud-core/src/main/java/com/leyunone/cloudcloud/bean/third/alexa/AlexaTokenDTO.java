package com.leyunone.cloudcloud.bean.third.alexa;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @Author LeYunOne
 * @Date 2024/2/23 13:43
 */
@Data
public class AlexaTokenDTO {

    private Directive directive;

    @Data
    public static class Directive {
        private AlexaHeader header;
        
        private Payload payload;
    }

    @Getter
    @Setter
    public static class Payload {
        private Grant grant;

        private Grantee grantee;
    }

    @Data
    public static class Grant {

        private String type;

        private String code;
    }

    @Data
    public static class Grantee {

        private String type;
        //我们token
        private String token;
    }
}
