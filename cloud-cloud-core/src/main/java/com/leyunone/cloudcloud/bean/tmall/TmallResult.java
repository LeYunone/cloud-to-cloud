package com.leyunone.cloudcloud.bean.tmall;

import lombok.*;

/**
 * :)
 *
 * @Author Leyunone
 * @Date 2024/1/18 14:03
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TmallResult {

    private String deviceId;
    
    private String errorCode;
    
    private String message;
}
