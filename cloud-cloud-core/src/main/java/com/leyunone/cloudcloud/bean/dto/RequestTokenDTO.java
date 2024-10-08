package com.leyunone.cloudcloud.bean.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * :)
 *  请求token
 * @Author LeYunone
 * @Date 2024/5/30 17:35
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RequestTokenDTO {

    private String clientId;

    private String code;

    private String apiId;
    //绑定的业务id
    private String businessId;

    private Integer appId;

    private String redirectUri;
}
