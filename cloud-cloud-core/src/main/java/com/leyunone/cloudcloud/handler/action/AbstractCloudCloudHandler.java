package com.leyunone.cloudcloud.handler.action;

import com.leyunone.cloudcloud.bean.CurrentRequestContext;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.handler.factory.CloudCloudHandlerFactory;
import com.leyunone.cloudcloud.handler.factory.StrategyFactory;
import com.leyunone.cloudcloud.mangaer.AccessTokenManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
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

    protected final ThirdPartyConfigService thirdPartyConfigService;

    protected AbstractCloudCloudHandler(CloudCloudHandlerFactory factory, AccessTokenManager accessTokenManager, ThirdPartyConfigService thirdPartyConfigService) {
        super(factory);
        this.accessTokenManager = accessTokenManager;
        this.thirdPartyConfigService = thirdPartyConfigService;
    }

    @Override
    public String action(String request) {
        try {
            String accessToken = getAccessToken(request);
            AccessTokenInfo authentication = authentication(accessToken);
            checkSceneData(request);
            return dispatchHandler(request, authentication);
        } finally {
            CurrentRequestContext.remove();
        }
    }

    /**
     * 获取token
     *
     * @param request
     * @return
     */
    protected abstract String getAccessToken(String request);

    protected abstract String dispatchHandler(String request, AccessTokenInfo accessToken);

    /**
     * 鉴权方法
     */
    protected AccessTokenInfo authentication(String accessToken) {
        return accessTokenManager.refreshAccessToken(accessToken);
    }


    /**
     * 检查本次请求是否是场景数据
     *
     * @param request
     * @return
     */
    protected abstract void checkSceneData(String request);
}
