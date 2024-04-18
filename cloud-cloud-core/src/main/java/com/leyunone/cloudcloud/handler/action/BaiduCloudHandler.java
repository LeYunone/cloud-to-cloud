package com.leyunone.cloudcloud.handler.action;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.CurrentRequestContext;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduHeader;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduStandardRequest;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.constant.VoiceConstants;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.CloudCloudHandlerFactory;
import com.leyunone.cloudcloud.handler.protocol.AbstractStrategyProtocolHandler;
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


    protected BaiduCloudHandler(CloudCloudHandlerFactory factory, AccessTokenManager accessTokenManager, ThirdPartyConfigService thirdPartyConfigService) {
        super(factory, accessTokenManager, thirdPartyConfigService);
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
        AbstractStrategyProtocolHandler cloudProtocolHandler = factory.getStrategy(namespace, AbstractStrategyProtocolHandler.class);
        Object action = cloudProtocolHandler.action(request, new ActionContext(accessToken, config));
        return JSONObject.toJSONString(action);
    }

    @Override
    protected void checkSceneData(String request) {
        try {
            BaiduStandardRequest baiduStandardRequest = JSONObject.parseObject(request, BaiduStandardRequest.class);
            String sceneId = baiduStandardRequest.getPayload().getAppliance().getAdditionalApplianceDetails().get(VoiceConstants.SCENES_KEY);
            CurrentRequestContext.setSceneData(sceneId);
        } catch (Exception e) {
        }
    }

    @Override
    public String getKey() {
        return ThirdPartyCloudEnum.BAIDU.name();
    }
}
