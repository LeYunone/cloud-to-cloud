package com.leyunone.cloudcloud.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.ActionMappingDO;
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
public interface ActionMappingRepository extends IBaseRepository<ActionMappingDO> {

    List<ActionMappingDO> selectByProductIds(List<String> productIds, String cloud);

    List<ActionMappingDO> selectByProductId(String productId, String cloud);

    Page<ActionMappingDO> selectPageOrder(ProductTypeQuery query);

    int deleteByProductId(String productId, ThirdPartyCloudEnum cloud);

    void updateNull(List<String> productIds);
}
