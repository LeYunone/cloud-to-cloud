package com.leyunone.cloudcloud.bean.tmall;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * :)
 *
 * @Author Leyunone
 * @Date 2024/1/17 16:12
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TmallDevice {


    private String deviceId;
    /**
     * 设备别名，
     * 用户可以为该设备设置一个别名如"落地扇"
     * 猫精支持的设备别名列表参考：https://open.bot.tmall.com/oauth/api/aliaslist
     * 推荐使用猫精支持的设备别名，否则语音无法识别
     * 非必填
     */
    private String deviceName;
    /**
     * 设备的品类英文名，如aircondition
     * 猫精支持的品类列表参考：https://www.aligenie.com/doc/357554/eq19cg
     * 必填
     */
    private String deviceType;
    /**
     * 品牌名称
     * 必须是猫精平台支持的品牌名，注意！！！
     * 必填
     */
    private String brand;
    /**
     * 产品型号
     * 必须与在猫精平台创建产品的型号一致，注意！！！
     * 必填
     */
    private String model;
    /**
     * 设备的位置
     * 猫精支持的位置列表参考https://open.bot.tmall.com/oauth/api/placelist
     * 推荐使用猫精支持的位置，否则语音无法识别
     * 用户可以为该设备设置位置，如"客厅"
     * 这时可以对天猫精灵说"打开客厅的落地扇"
     * 选填
     */
    private String zone;
    /**
     * 设备的属性状态，参照设备对应的产品功能定义
     * 非必填
     **/
    private Map<String,Object> status;

    /**
     * 拓展信息
     */
    private Map<String,Object> extensions;

}
