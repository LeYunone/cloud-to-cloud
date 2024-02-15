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
 * <p>
 *
 * </p>
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("c_third_party_client")
public class ThirdPartyClientDO {

    private static final long serialVersionUID = 1L;

    @TableId
    private String clientId;

    private String clientSecret;

    private Long appId;

    private Long tenantId;

    private String appUuid;

    private String tenantUuid;

    private String mainUrl;

    private String skillId;

    private String icon;

    private String additionalInformation;

    private String redirectUri;

    private String appSecret;

    private String reportUrl;

    private String thirdPartyCloud;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
