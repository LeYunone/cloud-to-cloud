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

    /**
     * 第三方搜全绑定的业务id 比如用户id
     */
    private String businessId;

    /**
     * 我方授权的应用id
     */
    private Integer appId;
}
