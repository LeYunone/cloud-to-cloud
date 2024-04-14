package com.leyunone.cloudcloud.dao.base.repository;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 抽象Repository服务类
 *
 * @author leyunone
 * @since 2022-03-28
 * 基础服务类1  需要调节 - DO[实体]  CO[出参]  M[mapper]
 */
public class BaseRepository<M extends BaseMapper<DO>, DO, CONVERT> extends BaseCommon<M, DO, CONVERT> implements IBaseRepository<DO> {

    private Class<DO> do_Class;

    private Map<Class<?>, Method> covers;

    private Map<Class<?>, Method> collectionCovers;

    {
        this.do_Class = getDo_Class();
        this.covers = getCovers();
        this.collectionCovers = getCollectionCovers();
    }


    /**
     * 根据实体类更新
     *
     * @param o
     * @return
     */
    @Override
    public boolean updateById(Object o) {
        DO d = o.getClass().isAssignableFrom(do_Class) ? (DO) o : castToDO(o);
        return super.updateById(d);
    }

    /**
     * 创建实体
     *
     * @param entity
     * @return
     */
    @Override
    public boolean insertOrUpdate(Object entity) {
        DO aDo = castToDO(entity);
        return this.saveOrUpdate(aDo);
    }

    @Override
    public boolean insertOrUpdateBatch(Collection<DO> entitys) {
        if (CollectionUtils.isEmpty(entitys)) {
            return true;
        }
        return this.saveOrUpdateBatch(entitys);
    }

    /**
     * id删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean deleteByIds(Collection<? extends Serializable> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return true;
        }
        return super.removeByIds(ids);
    }


    /**
     * id 逻辑删除
     *
     * @param id
     * @return
     */
    @Override
    public <R> boolean deleteLogicById(Serializable id, SFunction<DO, R> tableId) {
        try {
            Field isDeleted = do_Class.getDeclaredField("isDeleted");
            if (null != isDeleted) {
                UpdateWrapper<DO> updateWrapper = new UpdateWrapper<DO>();
                updateWrapper.set("is_deleted", 0);
                LambdaUpdateWrapper<DO> lambda = updateWrapper.lambda();
                lambda.eq(tableId, id);
                return super.update(lambda);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * id 批量逻辑删除
     *
     * @param ids
     * @return
     */
    @Override
    public <R> boolean deleteLogicByIds(List<? extends Serializable> ids, SFunction<DO, R> tableId) {
        try {
            Field isDeleted = do_Class.getDeclaredField("isDeleted");
            if (null != isDeleted) {
                UpdateWrapper<DO> updateWrapper = new UpdateWrapper<DO>();
                updateWrapper.set("is_deleted", 0);
                LambdaUpdateWrapper<DO> lambda = updateWrapper.lambda();
                lambda.in(tableId, ids);
                return super.update(lambda);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public <R> boolean updateNewId(String oldId, String newId, SFunction<DO, R> tableId) {
        LambdaUpdateWrapper<DO> lambda = new UpdateWrapper().lambda();
        lambda.eq(tableId, oldId);
        lambda.set(tableId, newId);
        return this.update(lambda);
    }

    @Override
    public <R> boolean insertBatch(Collection<R> entitys) {
        List<DO> save = (List<DO>) castCover(collectionCovers, do_Class, entitys);
        return this.saveBatch(save);
    }

    @Override
    public DO selectOne(Object o) {
        return this.selectOne(o, do_Class);
    }

    /**
     * 根据条件只查询一条
     *
     * @param o
     * @param clazz
     * @return
     */
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
    public DO selectById(Serializable id) {
        return this.selectById(id, do_Class);
    }

    @Override
    public List<DO> selectByIds(List<? extends Serializable> ids) {
        return this.selectByIds(ids, do_Class);
    }

    @Override
    public <R> List<R> selectByIds(List<? extends Serializable> ids, Class<R> clazz) {
        if (org.springframework.util.CollectionUtils.isEmpty(ids)) return new ArrayList<>();
        List<DO> dos = this.baseMapper.selectBatchIds(ids);
        return (List<R>) this.castCover(this.collectionCovers, clazz, dos);
    }

    /**
     * 主键id查询
     *
     * @param id
     * @param clazz
     * @param <R>
     * @return
     */
    @Override
    public <R> R selectById(Serializable id, Class<R> clazz) {
        if (!covers.containsKey(clazz)) {
            return (R) this.baseMapper.selectById(id);
        }
        DO aDo = this.baseMapper.selectById(id);
        Object o = this.castCover(this.covers, clazz, aDo);
        return (R) o;
    }

    /**
     * 默认出来DO
     *
     * @param o
     * @return
     */
    @Override
    public List<DO> selectByCon(Object o) {
        return this.selectByCon(o, do_Class);
    }

    @Override
    public List<DO> selectByCon(LambdaQueryWrapper<DO> queryWrapper) {
        DO aDo = castToDO(null);
        return this.selectByCon(aDo, do_Class, queryWrapper);
    }

    @Override
    public List<DO> selectByCon(Object o, LambdaQueryWrapper<DO> queryWrapper) {
        return this.selectByCon(o, do_Class, queryWrapper);
    }

    /**
     * 自定义出来clazz对象
     *
     * @param o
     * @param clazz
     * @return
     */
    @Override
    public <R> List<R> selectByCon(Object o, Class<R> clazz) {
        return this.selectByCon(o, clazz, null);
    }

    @Override
    public <R> List<R> selectByCon(Object o, Class<R> clazz, LambdaQueryWrapper<DO> queryWrapper) {
        DO d = castToDO(o);
        deletedToFalse(d);
        List<DO> dos = this.getConQueryResult(d, queryWrapper);
        if (!covers.containsKey(clazz)) {
            //出DO
            return (List<R>) dos;
        }
        //target class
        return (List<R>) this.castCover(this.collectionCovers, clazz, dos);
    }

    @Override
    public Page<DO> selectByConPage(Object o, Page page) {
        LambdaQueryWrapper<DO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        DO jDO = JSON.parseObject(JSON.toJSONString(o), do_Class);
        lambdaQueryWrapper.setEntity(jDO);
        return this.page(page, lambdaQueryWrapper);
    }

    @Override
    public Page<DO> selectByConPage(Object o, Integer index, Integer size) {
        Page page = new Page(index, size);
        return this.selectByConPage(page, page);
    }

    @Override
    public <R> Page<R> selectByConPage(Object o, Integer index, Integer size, Class<R> clazz) {
        Page page = new Page(index, size);
        return this.selectByConPage(o, clazz, page);
    }

    @Override
    public <R> Page<R> selectByConPage(Object o, Class<R> clazz, Page page) {
        Page<DO> doPage = this.selectByConPage(o, page);
        List<R> cover = (List<R>) castCover(this.collectionCovers, clazz, doPage.getRecords());
        Page<R> rPage = new Page<>(doPage.getCurrent(), doPage.getSize(), doPage.getTotal());
        rPage.setRecords(cover);
        rPage.setOrders(doPage.getOrders());
        rPage.setPages(doPage.getPages());
        return rPage;
    }


    /**
     * @param o
     * @param clazz
     * @param type  排序0为desc 1为asc
     * @param <R>
     * @return
     */
    @Override
    public <R, Z> List<R> selectByConOrder(Object o, Class<R> clazz, int type, SFunction<DO, Z>... tables) {
        LambdaQueryWrapper<DO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (type == 0) {
            for (SFunction<DO, Z> table : tables) {
                lambdaQueryWrapper.orderByDesc(table);
            }
        } else {
            for (SFunction<DO, Z> table : tables) {
                lambdaQueryWrapper.orderByAsc(table);
            }
        }
        return this.selectByCon(o, clazz, lambdaQueryWrapper);
    }

    /**
     * 自定义排序查询 默认对象DO
     *
     * @param o
     * @param type
     * @param tables
     * @param <Z>
     * @return
     */
    @Override
    public <Z> List<DO> selectByConOrder(Object o, int type, SFunction<DO, Z>... tables) {
        return this.selectByConOrder(o, do_Class, type, tables);
    }
}
