package com.leyunone.cloudcloud.mangaer;

import com.leyunone.cloudcloud.bean.dto.RequestTokenDTO;
import org.springframework.stereotype.Service;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/27 17:18
 */
@Service
public interface ClientOauthManager {

    void requestExchangeAccessToken(RequestTokenDTO requestTokenModel);

    /**
     * 对方从我方获取token
     * @param clientId 客户端id
     * @param userId 用户id
     * @param appId 应用标识
     * @return
     */
    String getAccessToken(String clientId,String userId,Integer appId);
}
