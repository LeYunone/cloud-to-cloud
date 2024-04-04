package com.leyunone.cloudcloud.web.bean.dto;

import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @Author LeyunOne
 * @Date 2024/4/2 16:42
 */
@Getter
@Setter
public class ThirdClientConfigDTO {

    private String clientId;

    private String clientSecret;

    private String mainUrl;

    private String icon;

    private String additionalInformation;

    private String reportUrl;

    private ThirdPartyCloudEnum thirdPartyCloud;

    private String thirdClientId;

    private String thirdClientSecret;
}
