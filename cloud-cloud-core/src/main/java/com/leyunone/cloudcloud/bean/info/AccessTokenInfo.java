package com.leyunone.cloudcloud.bean.info;

import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.*;

import java.time.LocalDateTime;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenInfo {

    private String accessToken;

    private String refreshToken;

    private String clientId;

    /**
     * 有效时间
     */
    private Long expiresIn;

    private String tokenType;

    private User user;

    @Getter
    @Setter
    public static class User {
        private Long tenantId;

        private Long appId;

        private String userId;

        private String thirdInfo;

        private ThirdPartyCloudEnum thirdPartyCloud;
    }
}
