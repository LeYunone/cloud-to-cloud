package com.leyunone.cloudcloud.dao.base.iservice;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.api.R;

import java.io.Serializable;
import java.util.List;

/**
 * @author leyunone
 * @create 2022-03-28 16:54
 * 基础服务接口 T规定  入参
 */
public interface IBaseRepository<DO> extends IQueryService<DO> {

    /**
     * 插入或更新 ： 带id更新  反之插入
     *
     * @return
     */
    boolean insertOrUpdate(Object entity);

    /**
     * 批量插入
     *
     * @param params 会通过copy转换为实体
     * @return
     */
    boolean insertOrUpdateBatch(List params);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    boolean deleteById(Serializable id);

    /**
     * 根据ids批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteByIdBatch(List ids);

    boolean save(DO d);

    <R> boolean deleteLogicById(Serializable id, SFunction<DO, R> tableId);
}
