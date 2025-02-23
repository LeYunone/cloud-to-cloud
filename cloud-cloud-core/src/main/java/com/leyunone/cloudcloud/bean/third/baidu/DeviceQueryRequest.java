package com.leyunone.cloudcloud.bean.third.baidu;

import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Getter
@Setter
public class DeviceQueryRequest {

    private BaiduHeader header;
    
    private Payload payload;

    @Getter
    @Setter
    public static class Payload {

        private Appliance appliance;
    }

    @Getter
    @Setter
    public static class Appliance {
        //设备id
        private String applianceId;
    }
}
