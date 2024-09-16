package com.leyunone.cloudcloud.bean.third.yingshi;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/30 17:59
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class YingshiDevice {
    /**
     * 设备展示名称，如C1(41956565)
     */
    private String displayName;
    /**
     * 设备短序列号
     */
    private String subSerial;
    /**
     * 设备长序列号
     */
    private String fullSerial;
    /**
     * 设备型号
     */
    private String model;
    /**
     * 设备大类型
     */
    private String category;
    /**
     * 设备在线状态：1-在线，0-不在线
     */
    private Integer status;
    /**
     * 设备图片
     */
    private String defaultPicPath;
    /**
     * 是否支持wifi 0-不支持 1-支持 2-支持带userId的新的wifi配置方式 3-支持smartwifi
     */
    private Integer supportWifi;
    /**
     * 设备协议版本
     */
    private String releaseVersion;
    /**
     * 设备真实版本号
     */
    private String version;
    /**
     * 可用于添加的通道数
     */
    private Integer availableChannelCount;
    /**
     * N1，R1，A1等设备关联的设备数
     */
    private Integer relatedDeviceCount;
    /**
     * 设备是否支持云存储：0-不支持，1-支持
     */
    private Integer supportCloud;
    /**
     * 能力级
     */
    private String supportExt;
    /**
     * 路由名称前缀，用于AP配网
     */
    private String routerNamePre;
    /**
     * 路由密码前缀，用于AP配网
     */
    private String routerPasswordPre;
    /**
     * 设备首次上线时间
     */
    private String createTime;

}

