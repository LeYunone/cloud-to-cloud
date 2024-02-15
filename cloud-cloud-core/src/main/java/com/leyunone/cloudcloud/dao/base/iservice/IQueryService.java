package com.leyunone.cloudcloud.dao.base.iservice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author leyunone
 * @date 2022-04-05
 * 封装查询接口
 */
public interface IQueryService<DO> extends IQueryPageService{

    List<DO> selectByCon(Object o);

    List<DO> selectByCon(LambdaQueryWrapper<DO> queryWrapper);

    List<DO> selectByCon(Object o, LambdaQueryWrapper<DO> queryWrapper);

    <R> List<R> selectByCon(Object o, Class<R> clazz);

    <R> List<R> selectByCon(Object o, Class<R> clazz, LambdaQueryWrapper<DO> queryWrapper);

    <R> R selectById(Serializable id, Class<R> clazz) throws IllegalAccessException, InstantiationException;

    DO selectById(Serializable id);

    <R> R selectOne(Object o, Class<R> clazz);

    DO selectOne(Object o);
}
