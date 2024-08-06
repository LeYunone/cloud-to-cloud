package com.leyunone.cloudcloud.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/27 17:29
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceFunction {

    private Integer id;

    private String name;

    private String signCode;

    private String value;

    private Long timestamp;

}
