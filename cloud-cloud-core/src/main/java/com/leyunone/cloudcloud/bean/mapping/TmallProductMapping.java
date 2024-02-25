package com.leyunone.cloudcloud.bean.mapping;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/18 9:35
 */
@Getter
@Setter
@ToString
public class TmallProductMapping extends ProductMapping{
    /**
     * 产品名
     * 可能配置 设备名
     */
    private String productName;

    /**
     * 设备品号英文名
     */
    private String deviceTypeEnName;

    /**
     * 天猫精灵支持的品牌
     */
    private String brand;
}
