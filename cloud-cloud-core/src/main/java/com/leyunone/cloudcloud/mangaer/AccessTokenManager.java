package com.leyunone.cloudcloud.mangaer;

import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public interface AccessTokenManager {

    AccessTokenInfo refreshAccessToken(String accessToken);
}
