package com.leyunone.cloudcloud.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.entity.ActionMappingDO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leyunone
 * @since 2023-12-14
 */
public interface ActionMappingMapper extends BaseMapper<ActionMappingDO> {

    Page<ActionMappingDO> selectPageOrder(@Param("con") ProductTypeQuery query, Page<ActionMappingDO> page);

}
