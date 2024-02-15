package com.leyunone.cloudcloud.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.dao.mapper.ProductTypeMappingMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Service
public class ProductTypeMappingRepositoryImpl extends BaseRepository<ProductTypeMappingMapper, ProductTypeMappingDO> implements ProductTypeMappingRepository {

    @Override
    public List<ProductTypeMappingDO> selectByProductIds(List<String> productIds,String cloud) {
        LambdaQueryWrapper<ProductTypeMappingDO> lambda = new QueryWrapper<ProductTypeMappingDO>().lambda();
        lambda.in(ProductTypeMappingDO::getProductId, productIds);
        lambda.eq(ProductTypeMappingDO::getThirdPartyCloud,cloud);
        return this.baseMapper.selectList(lambda);
    }
}
