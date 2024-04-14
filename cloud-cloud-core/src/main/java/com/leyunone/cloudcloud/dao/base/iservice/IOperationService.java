package com.leyunone.cloudcloud.dao.base.iservice;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author LeYunone
 * @create 2022/10/17
 */
public interface IOperationService<DO> {

    boolean deleteById(Serializable id);

    boolean deleteByIds(Collection<? extends Serializable> ids);

    <R> boolean deleteLogicById(Serializable id, SFunction<DO, R> tableId);

    <R> boolean deleteLogicByIds(List<? extends Serializable> ids, SFunction<DO, R> tableId);

    boolean insertOrUpdate(Object entity);

    boolean insertOrUpdateBatch(Collection<DO> entitys);

    <R> boolean updateNewId(String oldId, String newId, SFunction<DO, R> tableId);

    <R> boolean insertBatch(Collection<R> entitys);
}
