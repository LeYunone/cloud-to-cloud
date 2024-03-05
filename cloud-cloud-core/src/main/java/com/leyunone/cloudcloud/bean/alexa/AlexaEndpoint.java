package com.leyunone.cloudcloud.bean.alexa;

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

    private String cookie;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Scope {

        private String type;

        private String token;
    }
}
