package com.leyunone.cloudcloud.bean.third.xiaomi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author LeYunone
 * @date 2023-12-21 16:09:25
**/
public class GetDeviceStatus {

    @EqualsAndHashCode(callSuper = false)
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Request extends XiaomiCommonPayload {

        private List<String> devices;

    }

    @EqualsAndHashCode(callSuper = false)
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Response extends XiaomiCommonPayload {

        private List<XiaomiDevice> devices;

    }

}
