package com.leyunone.cloudcloud.dao.base.repository;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Getter;
import org.mapstruct.factory.Mappers;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author LeYuna 基本方法
 * @email 365627310@qq.com
 * @date 2022-07-11
 */
@Getter
public abstract class BaseCommon<M extends BaseMapper<DO>, DO,CONVERT> extends ServiceImpl<M, DO> {

    private Class<DO> do_Class;

    private Class<?> t_Class;

    /**
     * 转换器
     */
    private Map<Class<?>, Method> covers = new HashMap<>();

    /**
     * 集合转换器 list
     * 规则：List > Object ，非唯一场景中，当存在List转换模式时，优先考虑list，
     */
    private Map<Class<?>, Method> collectionCovers = new HashMap<>();

    private Object mapperCover;

    private boolean isCopy = false;

    public BaseCommon() {
        Class<?> clazzz = getClass();
        Type generic = clazzz.getGenericSuperclass();
        if (generic instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) generic).getActualTypeArguments();
            //DO
            this.do_Class = (Class<DO>) actualTypeArguments[1];
            //虚泛
            this.t_Class = (Class<?>) actualTypeArguments[2];
        }
        //如果虚泛是一个接口，默认当做Convert
        if (t_Class.isInterface()) {
            //转换器
            this.mapperCover = Mappers.getMapper(t_Class);
            Method[] methods = mapperCover.getClass().getMethods();
            for (Method method : methods) {
                if (method.getName().equals("equals")) continue;
                Class<?> returnType = method.getReturnType();
                //入参
                Class<?>[] parameterTypes = method.getParameterTypes();
                //拉取DO -> T 的转换模式
                if (1 == parameterTypes.length && parameterTypes[0].isAssignableFrom(do_Class)) {
                    covers.put(returnType, method);
                } else {
                    //集合分支
                    if (0 != parameterTypes.length && Collection.class.isAssignableFrom(parameterTypes[0])) {
                        Type[] genericParameterTypes = method.getGenericParameterTypes();

                        //出参泛型
                        if (Collection.class.isAssignableFrom(returnType)) {
                            Type genericSuperclass = method.getGenericReturnType();
                            if (genericSuperclass instanceof ParameterizedType) {
                                Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
                                if (actualTypeArguments.length > 1) continue;
                                returnType = (Class<?>) actualTypeArguments[0];
                            }
                        }

                        //集合泛型
                        if (null != genericParameterTypes && 1 == genericParameterTypes.length && genericParameterTypes[0] instanceof ParameterizedType) {
                            //泛型存在
                            ParameterizedType pt = (ParameterizedType) genericParameterTypes[0];
                            if (pt.getActualTypeArguments().length > 1) continue;
                            Class<?> tempC = (Class<?>) pt.getActualTypeArguments()[0];
                            if (pt.getActualTypeArguments().length == 1 && tempC.isAssignableFrom(do_Class)) {
                                collectionCovers.put(returnType, method);
                            }
                        }
                    }
                }
            }
            //锁住转化器
            covers = Collections.unmodifiableMap(covers);
            collectionCovers = Collections.unmodifiableMap(collectionCovers);
        } else {
            //使用bean反射拷贝的方法
            isCopy = true;
        }
    }

    /**
     * 执行转换
     *
     * @param clazz 目标对象
     * @param o     原对象
     * @return
     */
    protected Object castCover(Map<Class<?>, Method> cover, Class<?> clazz, Object o) {
        if (clazz.isAssignableFrom(do_Class)) {
            return o;
        }
        if (ObjectUtil.isNull(o)) {
            return null;
        }
        if (isCopy) {
            if (covers == cover) {
                return JSONObject.parseObject(JSONObject.toJSONString(o),clazz);
            }
            if (collectionCovers == cover) {
                return JSONObject.parseArray(JSONObject.toJSONString(o),clazz);
            }

            return null;
        }
        //选取分支
        boolean isList = cover == this.collectionCovers ? true : false;
        Method method = cover.get(clazz);
        //复数循环
        for (int i = 0; method == null && i <= 1; i++) {
            cover = isList ? this.covers : this.collectionCovers;
            method = cover.get(clazz);
        }
        if (method == null) {
            return null;
        }
        try {
            if (Collection.class.isAssignableFrom(method.getParameterTypes()[0]) && !Collection.class.isAssignableFrom(o.getClass())) {
                //强行同步类型
                List arr = new ArrayList();
                arr.add(o);
                o = arr;
            }
            Object invoke = method.invoke(this.mapperCover, o);
            if (!isList && Collection.class.isAssignableFrom(invoke.getClass())) {
                //如果不是集合 但是走了集合分支
                return ((Collection) invoke).iterator().next();
            }
            if (isList && !Collection.class.isAssignableFrom(invoke.getClass())) {
                //如果是集合 但是走了单数分支
                List<Object> list = new ArrayList<>();
                list.add(invoke);
                return list;
            }
            return invoke;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected DO castToDO(Object o) {
        if (null != o && o.getClass().isAssignableFrom(do_Class)) {
            return (DO) o;
        }
        DO d = null;
        try {
            d = (DO) do_Class.newInstance();
        } catch (Exception e) {
        }
        if (null != o) {
            BeanUtil.copyProperties(o, d);
        }
        return d;
    }

    /**
     * 如果包含字段deleted，且值为空，那么给上默认值0
     *
     * @param con
     */
    protected void deletedToFalse(Object con) {
        if (null == con) return;
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


    /**
     * 查询规则：条件对象 与 自定义Lambda条件 AND 拼接
     *
     * @param o
     * @param queryWrapper
     * @return
     */
    protected List<DO> getConQueryResult(Object o, LambdaQueryWrapper<DO> queryWrapper) {
        if (null == queryWrapper) {
            queryWrapper = new LambdaQueryWrapper<>();
        }
        if (null != o) {
            DO jDO = (DO) JSON.parseObject(JSON.toJSONString(o), do_Class);
            queryWrapper.setEntity(jDO);
        }
        return this.baseMapper.selectList(queryWrapper);
    }
}
