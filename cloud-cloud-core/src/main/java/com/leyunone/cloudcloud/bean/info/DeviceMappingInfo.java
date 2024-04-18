package com.leyunone.cloudcloud.bean.info;

import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/17 15:01
 */
@Data
@Accessors(chain = true)
public class DeviceMappingInfo {

    private String deviceId;

    private String productId;

    private String userId;

    private ThirdPartyCloudEnum thirdPartyCloud;

    private String thirdId;

    private String clientId;
}
