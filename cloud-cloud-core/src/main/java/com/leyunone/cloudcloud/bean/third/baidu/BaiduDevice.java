package com.leyunone.cloudcloud.bean.third.baidu;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
@Data
@Builder
public class BaiduDevice {

    private List<String> applianceTypes;

    /**
     * 设备唯一标识
     */
    private String applianceId;

    /**
     * 设备型号
     */
    private String modelName;

    /**
     * 设备版本
     */
    private String version;

    /**
     * 设备名称（用户识别）
     */
    private String friendlyName;

    /**
     * 描述
     */
    private String friendlyDescription;

    /**
     * 设备当前是否能够到达。true表示设备当前可以到达，false表示当前设备不能到达。
     */
    private String isReachable;

    /**
     * 设备支持的操作类型数组
     */
    private List<String> actions;

    /**
     * 提供给设备云使用，存放设备或场景相关的附加信息，是键值对。DuerOS不解析或使用这些数据。
     */
    private Map<String, String> additionalApplianceDetails;

    /**
     * 厂商名称
     */
    private String manufacturerName;

    /**
     * 设备的属性信息。
     */
    private List<BaiduAttributes> attributes;

}
