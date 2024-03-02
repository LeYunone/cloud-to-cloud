package com.leyunone.cloudcloud.handler.action;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.baidu.BaiduHeader;
import com.leyunone.cloudcloud.bean.baidu.BaiduStandardRequest;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.action.AbstractCloudCloudHandler;
import com.leyunone.cloudcloud.handler.factory.CloudCloudHandlerFactory;
import com.leyunone.cloudcloud.handler.protocol.CloudProtocolHandler;
import com.leyunone.cloudcloud.mangaer.AccessTokenManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import org.springframework.stereotype.Service;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Service
public class BaiduCloudHandler extends AbstractCloudCloudHandler {

    private final ThirdPartyConfigService thirdPartyConfigService;

    protected BaiduCloudHandler(CloudCloudHandlerFactory factory, AccessTokenManager accessTokenManager, ThirdPartyConfigService thirdPartyConfigService) {
        super(factory, accessTokenManager);
        this.thirdPartyConfigService = thirdPartyConfigService;
    }

    @Override
    protected String getAccessToken(String request) {
        //获取token
        BaiduStandardRequest baiduStandardRequest = JSONObject.parseObject(request, BaiduStandardRequest.class);
        return baiduStandardRequest.getPayload().getAccessToken();
    }

    @Override
    protected String dispatchHandler(String request, AccessTokenInfo accessToken) {
        BaiduStandardRequest baiduStandardRequest = JSONObject.parseObject(request, BaiduStandardRequest.class);
        ThirdPartyCloudConfigInfo config = thirdPartyConfigService.getConfig(accessToken.getClientId());

        BaiduHeader header = baiduStandardRequest.getHeader();
        //根据namespace调用处理器
        String namespace = header.getNamespace();
        CloudProtocolHandler cloudProtocolHandler = factory.getStrategy(namespace, CloudProtocolHandler.class);
        Object action = cloudProtocolHandler.action(request, new ActionContext(accessToken, config));
        return JSONObject.toJSONString(action);
    }

    @Override
    public String getKey() {
        return ThirdPartyCloudEnum.BAIDU.name();
    }
}
