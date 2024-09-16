package com.leyunone.cloudcloud.bean.third.yingshi;

import lombok.Data;

import java.util.Set;

/**
 * :)
 * 等待添加中的萤石设备
 *
 * @Author LeYunone
 * @Date 2024/9/6 10:27
 */
@Data
public class YingshiTobeDevice {

    private String businessId;

    private Integer appId;

    private String clientId;

    private Set<String> deviceIds;
}
