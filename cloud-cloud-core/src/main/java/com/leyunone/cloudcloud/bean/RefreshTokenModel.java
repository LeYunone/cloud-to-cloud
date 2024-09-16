package com.leyunone.cloudcloud.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/13 16:01
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RefreshTokenModel {

    private String refreshToken; 
    
    private String clientId;


    /**
     * 绑定的业务id
     */
    private String businessId;

    private Integer appId;
    
    private Integer refreshTime;
}
