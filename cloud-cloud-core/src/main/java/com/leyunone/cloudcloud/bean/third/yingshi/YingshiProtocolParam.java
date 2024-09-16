package com.leyunone.cloudcloud.bean.third.yingshi;

import lombok.*;

/**
 * :)
 * 萤石协议的参数（全参数）
 *
 * @Author LeYunone
 * @Date 2024/8/23 10:25
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YingshiProtocolParam {

    /**
     * 开放平台用户凭证
     */
    private String accessToken;
    /**
     * 设备序列号
     */
    private String deviceSerial;
    /**
     * 设备型号
     */
    private String model;
    /**
     * 音量
     */
    private Integer volume;
    /**
     * 通道号
     */
    private Integer channelNo;
    /**
     * 镜像方向：0-上下, 1-左右, 2-中心
     */
    private Integer command;

    //秘钥
    private String appKey;
    private String appSecret;
}
