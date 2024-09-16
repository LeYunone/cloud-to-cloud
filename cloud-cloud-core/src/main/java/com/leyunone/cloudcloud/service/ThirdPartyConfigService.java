package com.leyunone.cloudcloud.service;

import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/18
 */
public interface ThirdPartyConfigService {

    ThirdPartyCloudConfigInfo getConfig(String clientId);

    List<ThirdPartyCloudConfigInfo> getConfig(List<String> clientIds);
}

