package com.leyunone.cloudcloud.bean.third.alexa;

import lombok.Builder;
import lombok.Data;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/23 15:20
 */
@Data
@Builder
public class AlexaGetTokenRequest {

    private String grant_type;
    
    private String code;
    
    private String refresh_token;
    
    private String client_id;
    
    private String client_secret;
}
