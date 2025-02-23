package com.leyunone.cloudcloud.mangaer.protocol;

import com.alibaba.fastjson.JSON;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.DefaultHttpServiceFactory;
import com.leyunone.cloudcloud.mangaer.ClientOauthManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * :)
 * 萤石云协议
 *
 * @Author LeYunone
 * @Date 2024/6/3 10:21
 */
@Service
public class YingshiProtocolHttpManagerImpl extends AbstractProtocolHttpManager {

    protected YingshiProtocolHttpManagerImpl(DefaultHttpServiceFactory factory, RestTemplate restTemplate, ClientOauthManager clientOauthManager) {
        super(factory, restTemplate, clientOauthManager);
    }

    @Override
    public <T> T http(String url, HttpMethod method,
                      @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, method, requestEntity, String.class, uriVariables);
        } catch (Exception e) {
            throw e;
        }
        //https://open.ys7.com/help/658
        String body = response.getBody();
        return JSON.parseObject(body, responseType);
    }

    @Override
    protected String getKey() {
        return ThirdPartyCloudEnum.YINGSHI.name();
    }
}
