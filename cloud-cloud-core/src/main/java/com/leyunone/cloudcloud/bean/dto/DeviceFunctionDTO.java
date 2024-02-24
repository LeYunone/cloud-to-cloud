package com.leyunone.cloudcloud.bean.dto;

import com.leyunone.cloudcloud.enums.OperationEnum;
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

    private Integer functionId;

    private String value;
    
    private Long timestamp;

    private OperationEnum operation;
}
