package com.leyunone.cloudcloud.service;

import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/18
 */
public interface ThirdPartyConfigService {

    ThirdPartyCloudConfigInfo getConfig(String clientId);
}

