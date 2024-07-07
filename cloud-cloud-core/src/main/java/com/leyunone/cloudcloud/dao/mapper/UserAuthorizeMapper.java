package com.leyunone.cloudcloud.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyunone.cloudcloud.bean.UserClientInfoModel;
import com.leyunone.cloudcloud.dao.entity.UserAuthorizeDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import org.apache.ibatis.annotations.Param;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface UserAuthorizeMapper extends BaseMapper<UserAuthorizeDO> {

    UserClientInfoModel selectUserClientInfo(@Param("userId") String userId, @Param("cloud") ThirdPartyCloudEnum cloud);

}
