package com.leyunone.cloudcloud.dao;


import com.leyunone.cloudcloud.bean.UserClientInfoModel;
import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.UserAuthorizeDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;


/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface UserAuthorizeRepository extends IBaseRepository<UserAuthorizeDO> {

    /**
     *
     * @param userId
     * @param cloud
     * @return
     */
    UserAuthorizeDO selectByUserIdAndThirdPartyCloud(String userId, ThirdPartyCloudEnum cloud);

    /**
     * 根据主键更新
     * @param userAuthorizeDO
     * @param cloud
     */
    void updateByUserIdAndThirdPartyCloud(UserAuthorizeDO userAuthorizeDO, ThirdPartyCloudEnum cloud);

    UserClientInfoModel selectUserClientInfo(String userId, ThirdPartyCloudEnum cloud);

}
