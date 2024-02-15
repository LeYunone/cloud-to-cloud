package com.leyunone.cloudcloud.bean.dto;

import lombok.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceFunctionDTO {
    
    private String deviceId;

    private String signCode;

    private String value;
    
    private Long timestamp;
}
