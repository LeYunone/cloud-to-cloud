package com.leyunone.cloudcloud.bean.third.tmall;

import lombok.*;

import java.util.List;

/**
 * :)
 *
 * @Author Leyunone
 * @Date 2024/1/16 17:32
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TmallDiscoverResponse {

    private TmallHeader header;
 
    private Payload payload;
    
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Payload{
        
        private List<TmallDevice> devices;
    }
    
}
