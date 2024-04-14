package com.leyunone.cloudcloud.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.FunctionMappingRepository;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import com.leyunone.cloudcloud.dao.mapper.FunctionMappingMapper;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
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
public class FunctionMappingRepositoryImpl extends BaseRepository<FunctionMappingMapper, FunctionMappingDO,Object> implements FunctionMappingRepository {


    @Override
    public List<FunctionMappingDO> selectByProductIdsAndThirdPartyCloud(List<String> productId, String cloud) {
        LambdaQueryWrapper<FunctionMappingDO> lambda = new QueryWrapper<FunctionMappingDO>().lambda();
        lambda.eq(FunctionMappingDO::getThirdPartyCloud,cloud);
        lambda.in(FunctionMappingDO::getProductId,productId);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public List<FunctionMappingDO> selectByProductIdsAndThirdPartyCloud(String productId, String cloud) {
        return this.selectByProductIdsAndThirdPartyCloud(CollectionUtil.newArrayList(productId),cloud);
    }

    @Override
    public Page<FunctionMappingDO> selectPageOrder(ProductTypeQuery query) {
        Page<FunctionMappingDO> page = new Page<>(query.getIndex(),query.getSize());
        return this.baseMapper.selectPageOrder(query,page);
    }

    @Override
    public FunctionMappingDO selectByProductThirdCode(String productId, String thirdCode) {
        LambdaQueryWrapper<FunctionMappingDO> lambda = new QueryWrapper<FunctionMappingDO>().lambda();
        lambda.eq(FunctionMappingDO::getProductId,productId);
        lambda.eq(FunctionMappingDO::getThirdSignCode,thirdCode);
        return this.baseMapper.selectOne(lambda);
    }

}
