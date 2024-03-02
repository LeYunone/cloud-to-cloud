package com.leyunone.cloudcloud.bean.google;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/28 9:43
 */
@Getter
@Setter
public class GoogleControlRequest {

    private String requestId;

    private List<Input> inputs;

    @Getter
    @Setter
    public static class Input {

        private String intent;

        private Payload payload;
    }

    @Getter
    @Setter
    public static class Payload {

        private List<Command> commands;
    }

    @Getter
    @Setter
    public static class Command {

        private List<GoogleDevice> devices;

        private List<Execution> execution;
    }

    @Getter
    @Setter
    public static class Execution {

        private String command;

        private JSONObject params;
    }

    @Data
    public static class Color {

        private String temperature;

        private String spectrumRGB;

        private String spectrumHSV;
    }
}
