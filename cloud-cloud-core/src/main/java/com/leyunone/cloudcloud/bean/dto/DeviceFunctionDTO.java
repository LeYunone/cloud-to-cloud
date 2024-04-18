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
    
    private String productId;
    
    private String deviceId;

    private String signCode;

    private Integer functionId;
    
    private boolean scene;

    private Object value;
    
    private Long timestamp = System.currentTimeMillis();

    private OperationEnum operation;
}
