package com.leyunone.cloudcloud.handler.action;

import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.CloudCloudHandlerFactory;
import com.leyunone.cloudcloud.mangaer.AccessTokenManager;
import org.springframework.stereotype.Service;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/3/6
 */
@Service
public class XiaoMiCloudHandler extends AbstractCloudCloudHandler{

    protected XiaoMiCloudHandler(CloudCloudHandlerFactory factory, AccessTokenManager accessTokenManager) {
        super(factory, accessTokenManager);
    }

    @Override
    protected String getAccessToken(String request) {
        return null;
    }

    @Override
    protected String dispatchHandler(String request, AccessTokenInfo accessToken) {
        return null;
    }


    @Override
    public String getKey() {
        return ThirdPartyCloudEnum.XIAOMI.name();
    }
}
