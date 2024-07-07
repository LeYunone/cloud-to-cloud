package com.leyunone.cloudcloud.bean.third.baidu;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Getter
@Setter
public class BaiduStandardRequest implements Serializable {

    private BaiduHeader header;

    private StandardPayload payload;

    @Getter
    @Setter
    public static class StandardPayload {

        private String accessToken;

        private Appliance appliance;
    }

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Appliance {

        private Map<String,String> additionalApplianceDetails;

        private String applianceId;

    }
}
