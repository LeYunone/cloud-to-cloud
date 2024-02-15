package com.leyunone.cloudcloud.dao.base.repository;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LeYuna 基本方法
 * @email 365627310@qq.com
 * @date 2022-07-11
 */
public abstract class BaseCommon<M extends BaseMapper<DO>, DO> extends ServiceImpl<M,DO>{

    public Class do_Class;

    protected List<DO> getConQueryResult(Object o, LambdaQueryWrapper<DO> queryWrapper) {
        if (null == queryWrapper) {
            queryWrapper = new LambdaQueryWrapper<>();
        }
        DO aDo = castToDO(o);
        queryWrapper.setEntity(aDo);
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 执行转换
     * @param clazz
     * @param o
     * @return
     */
    protected abstract Object castCover(Map<Class<?>, Method> cover, Class<?> clazz, Object o);

    protected abstract <R>List<R> castCover(List<DO> dos,Class<R> tarClazz);

    /**
     * 转换得到对应的DO对象
     * @param o
     * @return
     */
    protected DO castToDO(Object o) {
        DO d = null;
        try {
            d = (DO) do_Class.newInstance();
        } catch (Exception e) {
        }
        if (null != o) {
            BeanUtil.copyProperties(o,d);
        }
        return d;
    }

    protected List<DO> castToDO(List<Object> o) {
        if(CollectionUtil.isEmpty(o)) return new ArrayList<>();
        return BeanUtil.copyToList(o,do_Class);
    }

    /**
     * 如果包含字段deleted，且值为空，那么给上默认值0
     *
     * @param con
     */
    protected void deletedToFalse(Object con) {
        Class<?> aClass = con.getClass();
        try {
            Field deletedField = aClass.getDeclaredField("isDeleted");
            boolean accessible = deletedField.isAccessible();
            try {
                if (!accessible) {
                    deletedField.setAccessible(true);
                }
                Object value = deletedField.get(con);
                if (value == null) {
                    deletedField.set(con, 0);
                }
            } finally {
                if (!accessible) {
                    deletedField.setAccessible(accessible);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
    }
}
