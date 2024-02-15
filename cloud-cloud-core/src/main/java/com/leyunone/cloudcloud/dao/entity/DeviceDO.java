package com.leyunone.cloudcloud.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
    private Long deviceId;

    private String productId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
