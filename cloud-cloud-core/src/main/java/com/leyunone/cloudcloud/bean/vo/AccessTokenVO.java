package com.leyunone.cloudcloud.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessTokenVO {

    private String access_token;

    private Long expires_in;

    private String refresh_token;

    private String token_type;
    
    private String error;
}
