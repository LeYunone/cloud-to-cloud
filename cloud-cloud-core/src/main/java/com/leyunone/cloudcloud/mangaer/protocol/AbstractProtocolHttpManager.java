package com.leyunone.cloudcloud.mangaer.protocol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.exception.AccessTokenException;
import com.leyunone.cloudcloud.exception.ResultCode;
import com.leyunone.cloudcloud.exception.ServiceException;
import com.leyunone.cloudcloud.handler.factory.DefaultHttpServiceFactory;
import com.leyunone.cloudcloud.mangaer.ClientOauthManager;
import com.leyunone.cloudcloud.mangaer.ProtocolServiceHttpManager;
import com.leyunone.cloudcloud.strategy.AbstractStrategyAutoRegisterComponent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/3 10:18
 */
public abstract class AbstractProtocolHttpManager extends AbstractStrategyAutoRegisterComponent implements ProtocolServiceHttpManager {

    protected final RestTemplate restTemplate;
    private final ClientOauthManager clientOauthManager;

    protected AbstractProtocolHttpManager(DefaultHttpServiceFactory factory, RestTemplate restTemplate, ClientOauthManager clientOauthManager) {
        super(factory);
        this.restTemplate = restTemplate;
        this.clientOauthManager = clientOauthManager;
    }

    protected <T> T http(String url, HttpMethod method,
                         @Nullable HttpEntity<?> requestEntity, Class<T> responseType) {
        T t = null;
        try {
            t = this.http(url, method, requestEntity, responseType, new JSONObject());
        } catch (AccessTokenException accessTokenException) {
//            clientOauthManager.requestRefreshAccessTokenByKeys(CollectionUtil.newArrayList(refreshTokenKey));
        }
        return t;
    }

    public <T> T http(String url, HttpMethod method,
                      @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        ResponseEntity<String> response = restTemplate.exchange(url, method, requestEntity, String.class, uriVariables);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ServiceException(ResultCode.ERROR);
        }
        String body = response.getBody();
        return (T) JSON.parseObject(JSON.toJSONString(body), responseType);
    }
}
