package com.leyunone.cloudcloud.bean.third.alexa;

import com.alibaba.fastjson.JSONArray;
import lombok.Getter;
import lombok.Setter;


/**
 * :)
 *  发现设备响应 json镜像对象
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/26
 */
@Getter
@Setter
public class AlexaDiscoveryResponseImage {

    private Event event;

    @Getter
    @Setter
    public static class Event {

        private AlexaHeader header;

        private Payload payload;
    }

    @Getter
    @Setter
    public static class Payload {

        private JSONArray endpoints;
    }
}
