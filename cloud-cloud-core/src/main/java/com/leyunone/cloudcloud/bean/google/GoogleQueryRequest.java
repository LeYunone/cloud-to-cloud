package com.leyunone.cloudcloud.bean.google;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/27 18:05
 */
@Getter
@Setter
public class GoogleQueryRequest {

    private String requestId;

    private List<Input> inputs;

    @Data
    public static class Input {

        private String intent;

        private Payload payload;

    }

    @Data
    public static class Payload {

        private List<GoogleDevice> devices;
    }
}
