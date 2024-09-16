package com.leyunone.cloudcloud.bean.third.yingshi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/9/6 17:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YingshiTVAddressInfoResponse extends YingshiCommonInfo {

    public Payload data;

    @Data
    public static class Payload {

        private String id;

        private String url;

        private String expireTime;
    }
}
