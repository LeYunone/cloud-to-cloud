package com.leyunone.cloudcloud.bean.alexa;

import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/26
 */
@Getter
@Setter
public class DeviceStatusReportResponse {

    @Getter
    @Setter
    public static class Event{

        private AlexaHeader alexaHeader;

        private AlexaEndpoint endpoint;
    }
}
