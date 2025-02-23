package com.leyunone.cloudcloud.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("d_device")
public class DeviceDO {

    private static final long serialVersionUID = 1L;

    /**
     * 设备Id
     */
    @TableId
    private String deviceId;

    private String productId;

    //第三方平台id
    private String thirdDeviceId;
    /**
     * 第三方分组id
     */
    private String thirdGroupId;
    /**
     * 第三方家庭id
     */
    private String thirdHomeId;
    //所属第三方平台
    private String clientId;
    //附加信息
    private String additional;
    /**
     * 设备令牌的key  - 查询缓存中的token
     */
    private String tokenKey;
    /**
     * 刷新令牌的key
     */
    private String refreshTokenKey;
    /**
     * 是否主动续期 0否  1是
     */
    private Integer tokenRenewal;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
