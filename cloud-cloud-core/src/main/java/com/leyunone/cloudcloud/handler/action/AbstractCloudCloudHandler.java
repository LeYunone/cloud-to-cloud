package com.leyunone.cloudcloud.handler.action;

import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.handler.factory.StrategyFactory;
import com.leyunone.cloudcloud.mangaer.AccessTokenManager;
import com.leyunone.cloudcloud.strategy.AbstractStrategyAutoRegisterComponent;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public abstract class AbstractCloudCloudHandler extends AbstractStrategyAutoRegisterComponent implements CloudCloudHandler {

    private final AccessTokenManager accessTokenManager;

    protected AbstractCloudCloudHandler(StrategyFactory factory,AccessTokenManager accessTokenManager) {
        super(factory);
        this.accessTokenManager = accessTokenManager;
    }

    @Override
    public String action(String request) {
        String accessToken = getAccessToken(request);
        AccessTokenInfo authentication = authentication(accessToken);
        return dispatchHandler(request, authentication);
    }

    /**
     * 获取token
     * @param request
     * @return
     */
    protected abstract String getAccessToken(String request);

    protected abstract String dispatchHandler(String request,AccessTokenInfo accessToken);

    /**
     * 鉴权方法
     */
    protected AccessTokenInfo authentication(String accessToken){
        return accessTokenManager.refreshAccessToken(accessToken);
    }
}
