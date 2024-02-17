package com.leyunone.cloudcloud.controller;

import com.leyunone.cloudcloud.bean.vo.AccessTokenVO;
import com.leyunone.cloudcloud.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public AccessTokenVO accessToken(@RequestParam("code") String code,
                                     @RequestParam("client_id") String clientId) {
        return oAuthService.generateAccessTokenByCode(code.split(",")[0], clientId.split(",")[0]);
    }
}
