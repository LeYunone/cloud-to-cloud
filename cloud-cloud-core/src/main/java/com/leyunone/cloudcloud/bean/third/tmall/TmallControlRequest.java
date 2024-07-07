package com.leyunone.cloudcloud.bean.third.tmall;

import lombok.*;

import java.util.List;
import java.util.Map;

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
public class TmallControlRequest {

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
        
        private List<String> deviceIds;

        private Map<String,Object> params;
        
        private Map<String,Object> extensions;
    }

}
