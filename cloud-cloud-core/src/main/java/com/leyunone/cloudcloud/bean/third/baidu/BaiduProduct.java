package com.leyunone.cloudcloud.bean.third.baidu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Getter
@Setter
public class BaiduProduct {
    
    private String deviceId;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 第三方产品id
     */
    private List<String> thirdPartyPids;

    /**
     * 当时属性
     */
    private List<BaiduAttributes> attributes;

    /**
     * 技能表
     */
    private List<String> actions;
}
