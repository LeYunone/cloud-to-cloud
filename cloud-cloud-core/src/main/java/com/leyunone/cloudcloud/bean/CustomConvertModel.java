package com.leyunone.cloudcloud.bean;

import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import lombok.*;


/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/12/14 14:48
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomConvertModel {

    private DeviceInfo deviceInfo;

    /**
     * 映射模板
     */
    private String mappingTemplate;
}
