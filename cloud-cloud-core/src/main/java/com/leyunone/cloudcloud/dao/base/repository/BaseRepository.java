package com.leyunone.cloudcloud.dao.base.repository;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 抽象Repository服务类
 *
 * @author leyunone
 * @since 2022-03-28
 * 基础服务类1  需要调节 - DO[实体]  CO[出参]  M[mapper]
 */
public  class BaseRepository<M extends BaseMapper<DO>, DO> extends BaseCommon<M, DO> implements IBaseRepository<DO> {

    public BaseRepository () {
        Class<?> c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) t).getActualTypeArguments();
            super.do_Class = (Class<?>) params[1];
        }
    }

    @Override
    public boolean insertOrUpdate(Object entity) {
        DO aDo = castToDO(entity);
        return this.saveOrUpdate(aDo);
    }

    @Override
    public boolean insertOrUpdateBatch(List params) {
        List list = castToDO(params);
        return this.saveOrUpdateBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean deleteByIdBatch(List ids) {
        return super.removeByIds(ids);
    }

    @Override
    public <R> boolean deleteLogicById(Serializable id, SFunction<DO, R> tableId) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("isDeleted",1);
        LambdaUpdateWrapper lambda = updateWrapper.lambda();
        lambda.eq(tableId,id);
        return super.update(lambda);
    }

    @Override
    public List<DO> selectByCon(Object o) {
        return (List<DO>) this.selectByCon(o, do_Class);
    }

    @Override
    public List<DO> selectByCon(LambdaQueryWrapper<DO> queryWrapper) {
        return null;
    }

    @Override
    public List<DO> selectByCon(Object o, LambdaQueryWrapper<DO> queryWrapper) {
        return null;
    }

    @Override
    public <R> List<R> selectByCon(Object o, Class<R> clazz) {
        return this.selectByCon(o, clazz, null);
    }

    @Override
    public <R> List<R> selectByCon(Object o, Class<R> clazz, LambdaQueryWrapper<DO> queryWrapper) {
        deletedToFalse(o);
        DO d = castToDO(o);
        List<DO> dos = this.getConQueryResult(d, queryWrapper);
        //target class
        List<R> rs = this.castCover(dos, clazz);
        return rs;
    }
    @Override
    public <R>R selectById(Serializable id, Class<R> clazz) {
        DO aDo = this.baseMapper.selectById(id);
        R r = null;
        try {
            r = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtil.copyProperties(aDo,r);
        return r;
    }

    @Override
    public DO selectById(Serializable id) {
        return (DO)this.selectById(id,do_Class);
    }

    @Override
    public <R> R selectOne(Object o, Class<R> clazz) {
        List<R> rs = this.selectByCon(o, clazz);
        if (CollectionUtils.isNotEmpty(rs)) {
            return rs.get(0);
        } else {
            return null;
        }
    }

    @Override
    public DO selectOne(Object o) {
        return (DO) this.selectOne(o, do_Class);
    }

    @Override
    public Page selectByConPage(Object o, Page page) {
        return null;
    }

    @Override
    public Page selectByConOrderPage(Object con, Page page, SFunction function, boolean isDesc) {
        return null;
    }

    @Override
    protected Object castCover(Map<Class<?>, Method> cover, Class<?> clazz, Object o) {
        return null;
    }

    @Override
    protected <R>List<R> castCover(List<DO> dos,Class<R> tarClazz) {
        List<R> list = BeanUtil.copyToList(dos, tarClazz);
        return list;
    }
}
