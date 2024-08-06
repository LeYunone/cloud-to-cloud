package com.leyunone.cloudcloud.api;

import com.leyunone.cloudcloud.api.protocol.MyCompanyAttribute;
import lombok.Data;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/7 14:28
 */
@Data
public class MyCompanyProtocolShadowModel {

    /**
     * 第三方设备id
     */
    private String deviceId;

    private boolean online;

    private List<MyCompanyAttribute> attributes;
}
