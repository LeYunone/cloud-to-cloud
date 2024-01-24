package com.leyunone.cloudcloud.bean.baidu;

import lombok.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Getter
@Setter
public class DiscoverAppliancesRequest {

    private BaiduHeader header;

    private Payload payload;


    @Getter
    @Setter
    public static class Payload {

        private String accessToken;

        private String openUid;

    }

}
