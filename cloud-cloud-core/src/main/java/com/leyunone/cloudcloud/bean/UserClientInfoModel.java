package com.leyunone.cloudcloud.bean;

import lombok.Data;

/**
 * :)
 * 用户客户端信息
 *
 * @Author LeYunOne\
 * @Date 2024/2/23 15:12
 */
@Data
public class UserClientInfoModel {

    private String userId;

    private String clientId;

    private String clientSecret;

    private String thirdInfo;
}
