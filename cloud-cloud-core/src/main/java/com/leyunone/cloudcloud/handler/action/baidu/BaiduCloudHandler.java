package com.leyunone.cloudcloud.handler.action.baidu;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.baidu.BaiduHeader;
import com.leyunone.cloudcloud.bean.baidu.BaiduStandardRequest;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.action.AbstractCloudCloudHandler;
import com.leyunone.cloudcloud.handler.action.CloudCloudHandler;
import com.leyunone.cloudcloud.handler.factory.CloudCloudHandlerFactory;
import com.leyunone.cloudcloud.handler.protocol.CloudProtocolHandler;
import com.leyunone.cloudcloud.mangaer.AccessTokenManager;
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

    protected BaiduCloudHandler(CloudCloudHandlerFactory factory, AccessTokenManager accessTokenManager) {
        super(factory, accessTokenManager);
    }

    @Override
    protected String getAccessToken(String request) {
        return null;
    }

    @Override
    protected String dispatchHandler(String request, AccessTokenInfo accessToken) {
        BaiduStandardRequest baiduStandardRequest = JSONObject.parseObject(request, BaiduStandardRequest.class);
        BaiduHeader header = baiduStandardRequest.getHeader();
        //根据namespace调用处理器
        String namespace = header.getNamespace();
        CloudProtocolHandler cloudProtocolHandler = (CloudProtocolHandler) factory.getStrategy(namespace);
        Object action = cloudProtocolHandler.action(request, new ActionContext());
        return JSONObject.toJSONString(action);
    }

    @Override
    public String getKey() {
        return ThirdPartyCloudEnum.BAIDU.name();
    }
}
