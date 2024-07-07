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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlexaEndpoint {

    private Scope scope;

    private String endpointId;

    private Object cookie;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Scope {

        private String type;

        private String token;
    }
}
