package com.leyunone.cloudcloud.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import org.apache.ibatis.annotations.Param;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface ProductTypeMappingMapper extends BaseMapper<ProductTypeMappingDO> {

    Page<ProductTypeMappingDO> selectPageOrder(@Param("con") ProductTypeQuery query, Page<ProductTypeMappingDO> page);

}
