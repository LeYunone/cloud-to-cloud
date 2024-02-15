package com.leyunone.cloudcloud.dao;


import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.CustomMappingDO;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface CustomMappingRepository extends IBaseRepository<CustomMappingDO> {

    CustomMappingDO selectByProductThirdCode(String productId, String thirdCode);

}
