package com.leyunone.cloudcloud.dao;


import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.ThirdPartyClientDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface ThirdPartyClientRepository extends IBaseRepository<ThirdPartyClientDO> {

    ThirdPartyClientDO selectByClientId(String clientId);

    ThirdPartyClientDO selectByCloud(ThirdPartyCloudEnum cloudEnum);

    int deleteByCloud(ThirdPartyCloudEnum cloudEnum);
}
