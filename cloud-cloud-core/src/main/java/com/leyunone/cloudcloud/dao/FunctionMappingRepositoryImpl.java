package com.leyunone.cloudcloud.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.StatusMappingDO;
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
public class FunctionMappingRepositoryImpl extends BaseRepository<FunctionMappingMapper, StatusMappingDO> implements FunctionMappingRepository{


    @Override
    public List<StatusMappingDO> selectByProductIdsAndThirdPartyCloud(List<String> productId, String cloud) {
        LambdaQueryWrapper<StatusMappingDO> lambda = new QueryWrapper<StatusMappingDO>().lambda();
        lambda.eq(StatusMappingDO::getThirdPartyCloud,cloud);
        lambda.in(StatusMappingDO::getProductId,productId);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public StatusMappingDO selectByProductThirdCode(String productId, String thirdCode) {
        LambdaQueryWrapper<StatusMappingDO> lambda = new QueryWrapper<StatusMappingDO>().lambda();
        lambda.eq(StatusMappingDO::getProductId,productId);
        lambda.eq(StatusMappingDO::getThirdSignCode,thirdCode);
        return this.baseMapper.selectOne(lambda);
    }

}
