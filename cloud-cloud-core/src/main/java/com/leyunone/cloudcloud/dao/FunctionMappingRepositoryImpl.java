package com.leyunone.cloudcloud.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import com.leyunone.cloudcloud.dao.mapper.FunctionMappingMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Repository
public class FunctionMappingRepositoryImpl extends BaseRepository<FunctionMappingMapper, FunctionMappingDO> implements FunctionMappingRepository{


    @Override
    public List<FunctionMappingDO> selectByProductIdsAndThirdPartyCloud(List<String> productId, String cloud) {
        LambdaQueryWrapper<FunctionMappingDO> lambda = new QueryWrapper<FunctionMappingDO>().lambda();
        lambda.eq(FunctionMappingDO::getThirdPartyCloud,cloud);
        lambda.in(FunctionMappingDO::getProductId,productId);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public FunctionMappingDO selectByProductThirdCode(String productId, String thirdCode) {
        LambdaQueryWrapper<FunctionMappingDO> lambda = new QueryWrapper<FunctionMappingDO>().lambda();
        lambda.eq(FunctionMappingDO::getProductId,productId);
        lambda.eq(FunctionMappingDO::getThirdSignCode,thirdCode);
        return this.baseMapper.selectOne(lambda);
    }

}
