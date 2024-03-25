package com.leyunone.cloudcloud.bean.info;

import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.*;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCloudInfo {

    private String deviceId;

    private String productId;

    private List<ThirdMapping> mapping;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class ThirdMapping {

        private String userId;

        private ThirdPartyCloudEnum thirdPartyCloud;

        private String thirdId;

        private String clientId;
    }
}
