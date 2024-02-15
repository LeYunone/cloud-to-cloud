package com.leyunone.cloudcloud.dao;


import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface ProductTypeMappingRepository extends IBaseRepository<ProductTypeMappingDO> {

    List<ProductTypeMappingDO> selectByProductIds(List<String> productIds,String cloud);
}
