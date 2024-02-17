package com.leyunone.cloudcloud.handler.action.tmall;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.tmall.TmallHeader;
import com.leyunone.cloudcloud.bean.tmall.TmallStandardRequest;
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
 * @Author Leyunone
 * @Date 2024/1/16 16:47
 */
@Service
public class TmallCloudHandler extends AbstractCloudCloudHandler {

    private final ThirdPartyConfigService thirdPartyConfigService;

    protected TmallCloudHandler(CloudCloudHandlerFactory factory, AccessTokenManager accessTokenManager, ThirdPartyConfigService thirdPartyConfigService) {
        super(factory, accessTokenManager);
        this.thirdPartyConfigService = thirdPartyConfigService;
    }

    @Override
    protected String getAccessToken(String request) {
        //获取token
        TmallStandardRequest tmallStandardRequest = JSONObject.parseObject(request, TmallStandardRequest.class);
        return tmallStandardRequest.getPayload().getAccessToken();
    }

    @Override
    protected String dispatchHandler(String request, AccessTokenInfo accessToken) {
        TmallStandardRequest tmallStandardRequest = JSONObject.parseObject(request, TmallStandardRequest.class);
        ThirdPartyCloudConfigInfo config = thirdPartyConfigService.getConfig(accessToken.getClientId());
        TmallHeader header = tmallStandardRequest.getHeader();
        //根据namespace调用处理器
        String namespace = header.getNamespace();
        CloudProtocolHandler cloudProtocolHandler = factory.getStrategy(namespace, CloudProtocolHandler.class);
        Object action = cloudProtocolHandler.action(request, new ActionContext(accessToken, config));
        return JSONObject.toJSONString(action);
    }

    @Override
    protected String getKey() {
        return ThirdPartyCloudEnum.TMALL.name();
    }
}
