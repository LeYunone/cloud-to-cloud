package com.leyunone.cloudcloud.controller;

import cn.hutool.core.util.StrUtil;
import com.leyunone.cloudcloud.bean.dto.RequestTokenDTO;
import com.leyunone.cloudcloud.bean.vo.AccessTokenVO;
import com.leyunone.cloudcloud.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/18
 */
@RestController
@RequestMapping("/oauth")
public class OAuthorizeController {

    @Autowired
    private OAuthService oAuthService;

    @PostMapping("/authorize")
    public String authorize(@RequestParam("clientId") String clientId,
                            HttpServletRequest request) {
        return oAuthService.generateOAuthCode(clientId, request);
    }

    @PostMapping("/access_token")
    public AccessTokenVO accessToken(@RequestParam(value = "code", required = false) String code,
                                     @RequestParam(value = "refresh_token", required = false) String refreshToken,
                                     @RequestParam("client_id") String clientId) {
        if (StrUtil.isNotBlank(refreshToken)) {
            //刷新令牌动作
            try {
                return oAuthService.generateAccessTokenByRefreshToken(refreshToken, clientId);
            } catch (Exception e) {
                //兼容谷歌
                return AccessTokenVO.builder().error("invalid_grant").build();
            }
        }
        //兼容百度
        return oAuthService.generateAccessTokenByCode(code.split(",")[0], clientId.split(",")[0]);
    }


    /**
     * 客户端请求授权
     */
    @PostMapping("/auth/api/{apiId}")
    public void requestExchangeAccessToken(@RequestBody RequestTokenDTO requestToken) {
        oAuthService.requestExchangeAccessToken(requestToken);
    }
}
