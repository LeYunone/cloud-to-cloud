package com.leyunone.cloudcloud.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 语音平台动作标识
 * </p>
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("c_third_party_action")
public class ThirdPartyActionDO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 技能标识
     */
    private String actionIdentify;

    /**
     * 语音平台  
     */
    private String thirdPartyCloud;

    /**
     * 技能类型 0查询 1控制 
     */
    private Integer actionType;
}
