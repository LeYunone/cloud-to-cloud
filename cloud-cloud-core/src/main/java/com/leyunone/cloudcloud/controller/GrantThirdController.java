package com.leyunone.cloudcloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaGetTokenResponse;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaHeader;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaTokenDTO;
import com.leyunone.cloudcloud.mangaer.AccessTokenManager;
import com.leyunone.cloudcloud.mangaer.impl.AlexaTokenManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/23 11:51
 */
@RestController
@RequestMapping("/thirdToken/manager")
public class GrantThirdController {

    private final AlexaTokenManager alexaTokenManager;
    private final AccessTokenManager accessTokenManager;

    public GrantThirdController(AlexaTokenManager alexaTokenManager, AccessTokenManager accessTokenManager) {
        this.alexaTokenManager = alexaTokenManager;
        this.accessTokenManager = accessTokenManager;
    }

    /**
     * 由lambda应用触发 处理并保存alexa的token
     *
     * @param alexaTokenDTO
     * @return
     */
    @PostMapping("/alexaAcceptGrant")
    public String alexaToken(@RequestBody AlexaTokenDTO alexaTokenDTO) {
        //请求Alexa获取令牌
        String accessCode = alexaTokenDTO.getDirective().getPayload().getGrant().getCode();
        //用户的授权token
        String token = alexaTokenDTO.getDirective().getPayload().getGrantee().getToken();
        
        AccessTokenInfo accessToken = accessTokenManager.getAccessToken(token);
        String userId = accessToken.getUser().getUserId();
        alexaTokenManager.saveTokenCode(userId, accessCode);

        try {
            alexaTokenManager.loadAlexaToken(accessCode, accessToken);
        } catch (Exception e) {
        }

        return JSONObject.toJSONString(new AlexaGetTokenResponse(AlexaGetTokenResponse.Event.builder().header(AlexaHeader.builder()
                .namespace("Alexa.Authorization")
                .name("AcceptGrant.Response")
                .messageId(UUID.randomUUID().toString())
                .payloadVersion("3")
                .build()).payload(new AlexaGetTokenResponse.Payload()).build()));
    }
}
