package com.leyunone.cloudcloud.api.protocol;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/29 14:06
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MyCompanyProtocolControlResponse {

    private Payload payload;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class Payload{
        
    }
}
