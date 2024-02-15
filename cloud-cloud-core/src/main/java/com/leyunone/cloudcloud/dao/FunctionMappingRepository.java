package com.leyunone.cloudcloud.dao;


import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface FunctionMappingRepository extends IBaseRepository<FunctionMappingDO> {

    List<FunctionMappingDO> selectByProductIdsAndThirdPartyCloud(List<String> productId, String cloud);

    FunctionMappingDO selectByProductThirdCode(String productId,String thirdCode);

}
