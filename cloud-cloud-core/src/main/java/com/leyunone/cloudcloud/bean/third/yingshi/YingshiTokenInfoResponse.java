package com.leyunone.cloudcloud.bean.third.yingshi;


import lombok.Data;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/9/2 15:05
 */
@Data
public class YingshiTokenInfoResponse extends YingshiCommonInfo {

    private Payload data;

    @Data
    public static class Payload {

        private String accessToken;

        private Long expireTime;
    }
}
