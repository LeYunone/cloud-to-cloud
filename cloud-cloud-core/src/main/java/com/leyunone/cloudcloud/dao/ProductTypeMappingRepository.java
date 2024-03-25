package com.leyunone.cloudcloud.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;

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

    List<ProductTypeMappingDO> selectByCloud(String cloud);

    Page<ProductTypeMappingDO> selectPage(ProductTypeQuery query);

    int deleteByProductId(String productId, ThirdPartyCloudEnum cloud);
}
