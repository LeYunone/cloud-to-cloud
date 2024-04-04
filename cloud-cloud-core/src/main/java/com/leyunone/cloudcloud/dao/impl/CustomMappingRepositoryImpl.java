package com.leyunone.cloudcloud.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyunone.cloudcloud.dao.CustomMappingRepository;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.CustomMappingDO;
import com.leyunone.cloudcloud.dao.mapper.CustomMappingMapper;
import org.springframework.stereotype.Repository;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Repository
public class CustomMappingRepositoryImpl extends BaseRepository<CustomMappingMapper, CustomMappingDO> implements CustomMappingRepository {

    @Override
    public CustomMappingDO selectByProductThirdCode(String productId, String thirdCode) {
        LambdaQueryWrapper<CustomMappingDO> lambda = new QueryWrapper<CustomMappingDO>().lambda();
        lambda.eq(CustomMappingDO::getProductId,productId);
        lambda.eq(CustomMappingDO::getThirdSignCode,thirdCode);
        return this.baseMapper.selectOne(lambda);
    }
    
}
