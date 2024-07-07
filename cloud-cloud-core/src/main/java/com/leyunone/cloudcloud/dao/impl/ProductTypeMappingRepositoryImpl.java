package com.leyunone.cloudcloud.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.ProductTypeMappingRepository;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.dao.mapper.ProductTypeMappingMapper;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
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
public class ProductTypeMappingRepositoryImpl extends BaseRepository<ProductTypeMappingMapper, ProductTypeMappingDO,Object> implements ProductTypeMappingRepository {

    @Override
    public List<ProductTypeMappingDO> selectByProductIds(List<String> productIds, String cloud) {
        LambdaQueryWrapper<ProductTypeMappingDO> lambda = new QueryWrapper<ProductTypeMappingDO>().lambda();
        lambda.in(ProductTypeMappingDO::getProductId, productIds);
        lambda.eq(ProductTypeMappingDO::getThirdPartyCloud, cloud);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public List<ProductTypeMappingDO> selectByProductId(String productIds, String cloud) {
        return this.selectByProductIds(CollectionUtil.newArrayList(productIds), cloud);
    }

    @Override
    public List<ProductTypeMappingDO> selectByCloud(String cloud) {
        LambdaQueryWrapper<ProductTypeMappingDO> lambda = new QueryWrapper<ProductTypeMappingDO>().select("DISTINCT third_product_id").lambda();
        lambda.eq(ProductTypeMappingDO::getThirdPartyCloud, cloud);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public Page<ProductTypeMappingDO> selectPage(ProductTypeQuery query) {
        LambdaQueryWrapper<ProductTypeMappingDO> lambda = new QueryWrapper<ProductTypeMappingDO>().select("DISTINCT product_id").lambda();
        lambda.eq(ProductTypeMappingDO::getThirdPartyCloud, query.getThirdPartyCloud());
        Page<ProductTypeMappingDO> page = new Page<>(query.getIndex(), query.getSize());
        return (Page<ProductTypeMappingDO>) this.baseMapper.selectPage(page, lambda);
    }

    @Override
    public Page<ProductTypeMappingDO> selectPageOrder(ProductTypeQuery query) {
        Page<ProductTypeMappingDO> page = new Page<>(query.getIndex(), query.getSize());
        return this.baseMapper.selectPageOrder(query, page);
    }

    @Override
    public int deleteByProductId(String productId, ThirdPartyCloudEnum cloud) {
        LambdaQueryWrapper<ProductTypeMappingDO> lambda = new QueryWrapper<ProductTypeMappingDO>().lambda();
        lambda.eq(ProductTypeMappingDO::getProductId, productId);
        lambda.eq(ProductTypeMappingDO::getThirdPartyCloud, cloud);
        return this.baseMapper.delete(lambda);
    }
}
