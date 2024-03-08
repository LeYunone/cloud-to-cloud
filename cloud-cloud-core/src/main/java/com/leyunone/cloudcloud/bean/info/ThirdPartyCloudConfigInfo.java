package com.leyunone.cloudcloud.bean.info;

import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ThirdPartyCloudConfigInfo {

    private String clientId;

    private String clientSecret;

    private Long appId;

    private Long tenantId;

    private String appUuid;

    private String tenantUuid;

    private String mainUrl;

    private String skillId;

    private String icon;

    private String additionalInformation;

    private String redirectUri;

    private String appSecret;

    private String reportUrl;

    private ThirdPartyCloudEnum thirdPartyCloudEnum;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String thirdSecret;

    private String thirdClientId;
}
