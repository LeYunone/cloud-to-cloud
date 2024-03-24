package com.leyunone.cloudcloud.dao;


import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.StatusMappingDO;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface FunctionMappingRepository extends IBaseRepository<StatusMappingDO> {

    List<StatusMappingDO> selectByProductIdsAndThirdPartyCloud(List<String> productId, String cloud);

    StatusMappingDO selectByProductThirdCode(String productId, String thirdCode);

}
