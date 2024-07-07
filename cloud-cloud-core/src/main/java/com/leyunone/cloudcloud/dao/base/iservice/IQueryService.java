package com.leyunone.cloudcloud.dao.base.iservice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author leyunone
 * @date 2022-04-05
 * 封装查询接口
 */
public interface IQueryService<DO> extends IQueryPageService<DO>{


    List<DO> selectByCon(Object o, LambdaQueryWrapper<DO> queryWrapper);

    List<DO> selectByCon(Object o);

    <R> List<R> selectByCon(Object o, Class<R> clazz, LambdaQueryWrapper<DO> queryWrapper);

    List<DO> selectByCon(LambdaQueryWrapper<DO> queryWrapper);

    <R> List<R> selectByCon(Object o, Class<R> clazz);


    <R> Page<R> selectByConPage(Object o, Integer index, Integer size, Class<R> clazz);

    <R> Page<R> selectByConPage(Object o, Class<R> clazz, Page page);

    <R> R selectById(Serializable id, Class<R> clazz);

    DO selectById(Serializable id);

    /**
     * ids自动判空 
     * @see com.baomidou.mybatisplus.core.injector.methods.SelectBatchByIds
     * @param ids
     * @return
     */
    List<DO> selectByIds(List<? extends Serializable> ids);

    <R> List<R> selectByIds(List<? extends Serializable> ids, Class<R> clazz);

    <R> R selectOne(Object o, Class<R> clazz);

    DO selectOne(Object o);

    <R,Z> List<R> selectByConOrder(Object o, Class<R> clazz, int type, SFunction<DO,Z>... tables );

    <R> List<DO> selectByConOrder(Object o, int type,SFunction<DO, R> ... tables);
}
