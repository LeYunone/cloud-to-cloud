package com.leyunone.cloudcloud.bean;

import lombok.Data;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/27 17:29
 */
@Data
public class ClientAccessTokenModel {

    private String accessToken;

    private Long expiresIn;

    private String refreshToken;

    private String tokenType;
    
    private String clientId;

    /**
     * 我方id
     */
    private String userId;
    
    private Integer appId;
}
