package com.leyunone.cloudcloud.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 产品功能属性值 Mapper 接口
 * </p>
 *
 * @author leyunone
 * @since 2023-12-14
 */
public interface FunctionMappingMapper extends BaseMapper<FunctionMappingDO> {

    Page<FunctionMappingDO> selectPageOrder(@Param("con") ProductTypeQuery query, Page<FunctionMappingDO> page);

}
