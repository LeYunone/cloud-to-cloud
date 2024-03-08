package com.leyunone.cloudcloud.bean.alexa;

import lombok.Data;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/23 15:36
 */
@Data
public class AlexaToken {

    private String accessToken;

    private String tokenType;

    private Integer expiresIn;

    private String refreshToken;
}
