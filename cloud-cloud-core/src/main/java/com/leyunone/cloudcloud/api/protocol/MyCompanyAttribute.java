package com.leyunone.cloudcloud.api.protocol;

import lombok.*;

import java.io.Serializable;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/24 10:29
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class MyCompanyAttribute implements Serializable {

    private String signCode;
    
    private String value;
    
    private Long timeOfSample;
}
