package com.leyunone.cloudcloud.bean.tmall;

import lombok.*;

/**
 * :)
 *
 * @Author Leyunone
 * @Date 2024/1/16 17:32
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TmallDiscoverRequest {

    private TmallHeader header;

    private Payload payload;


    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @EqualsAndHashCode
    public static class Payload {

        private String accessToken;

    }

}
