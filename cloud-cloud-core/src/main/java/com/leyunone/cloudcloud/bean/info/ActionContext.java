package com.leyunone.cloudcloud.bean.info;

import lombok.Data;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Data
public class ActionContext {

    private AccessTokenInfo accessTokenInfo;
    
    private ThirdPartyCloudConfigInfo thirdPartyCloudConfigInfo;

    public ActionContext(AccessTokenInfo accessTokenInfo, ThirdPartyCloudConfigInfo thirdPartyCloudConfigInfo) {
        this.accessTokenInfo = accessTokenInfo;
        this.thirdPartyCloudConfigInfo = thirdPartyCloudConfigInfo;
    }
}
