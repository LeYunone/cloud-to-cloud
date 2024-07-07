package com.leyunone.cloudcloud.bean.third.google;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/28 9:44
 */
@Data
public class GoogleControlResponse {

    private String requestId;

    private Payload payload;

    public GoogleControlResponse(String requestId, Payload payload) {
        this.requestId = requestId;
        this.payload = payload;
    }

    @Data
    @Builder
    public static class Payload {

        private List<Command> commands;
    }

    @Data
    @Builder
    public static class Command {

        private List<String> ids;

        private String status;

        /**
         * key:属性名  value:属性值
         */
        private Object states;
    }
}
