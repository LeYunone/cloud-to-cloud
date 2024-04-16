package com.leyunone.cloudcloud.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
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
public interface FunctionMappingRepository extends IBaseRepository<FunctionMappingDO> {

    List<FunctionMappingDO> selectByProductIdsAndThirdPartyCloud(List<String> productId, String cloud);

    List<FunctionMappingDO> selectByProductIdsAndThirdPartyCloud(String productId, String cloud);

    Page<FunctionMappingDO> selectPageOrder(ProductTypeQuery query);

    FunctionMappingDO selectByProductThirdCode(String productId, String thirdCode);

    void deleteByProductId(String productId, ThirdPartyCloudEnum cloud);

    void updateNull(List<String> productIds);
}
