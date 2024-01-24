package com.leyunone.cloudcloud.service;

import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/23
 */
public interface ThirdPartyPortalService {

    String portal(String request, ThirdPartyCloudEnum cloud);
}
