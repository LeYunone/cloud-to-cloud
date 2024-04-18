package com.leyunone.cloudcloud.mangaer.impl;

import cn.hutool.core.util.StrUtil;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/4/5
 */
@Service
public class CacheManagerImpl implements CacheManager {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public <T> T getData(String key, Class<T> clazz) {
        return (T)redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> Optional<T> getData(String key, Long time,TimeUnit timeUnit, Supplier<T> supplier) {
        Optional<T> optional = get(key);
        if (optional == null || !optional.isPresent()) {
            T t = supplier.get();
            if (t == null) {
                return Optional.empty();
            }
            addData(key, t, time,timeUnit);
            return Optional.of(t);
        }
        return optional;
    }

    @Override
    public <T> Optional<T> get(String key) {
        if (key == null) {
            return Optional.empty();
        } else {
            T t = (T) redisTemplate.opsForValue().get(key);
            if (t == null) {
                return Optional.empty();
            } else {
                return Optional.of(t);
            }
        }
    }

    @Override
    public boolean addData(String key, String value) {
        if (key.isEmpty()) {
            return false;
        }
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    @Override
    public <T> boolean addData(String key, T value, Long time) {
        if (key.isEmpty()) {
            return false;
        }
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
        return true;
    }

    @Override
    public boolean addData(String key, Object value, Long time, TimeUnit unit) {
        if (key.isEmpty()) {
            return false;
        }
        redisTemplate.opsForValue().set(key, value, time, unit);
        return true;
    }

    @Override
    public <T> Optional<List<T>> get(List<Object> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Optional.empty();
        }
        List<Object> multiGet = redisTemplate.opsForValue().multiGet(keys);
        List<T> collect = multiGet.stream().filter(Objects::nonNull).map(v -> (T) v).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return Optional.empty();
        } else {
            return Optional.of(collect);
        }
    }

    long defaultTimeMin = 15;

    @Override
    public <T> Optional<List<T>> getData(List<Object> keys, Long time, TimeUnit unit, Function<List<T>, List<T>> getMissData, Function<T, String> generateKey) {
        Optional<List<T>> optional = get(keys);
        List<T> hitData = new ArrayList<>();
        if (null != optional && optional.isPresent()) {
            hitData = optional.get();
        }
        List<T> missData = getMissData.apply(hitData);
        if (!CollectionUtils.isEmpty(missData)) {
            if (time == null) {
                time = defaultTimeMin;
            }
            Long finalTime = time;
            missData.forEach(t -> {
                addData(generateKey.apply(t), t, finalTime, unit);
            });
            hitData.addAll(missData);
        }
        if (CollectionUtils.isEmpty(hitData)) {
            return Optional.empty();
        } else {
            return Optional.of(hitData);
        }
    }

    @Override
    public boolean deleteData(String key) {
        if (StrUtil.isEmpty(key)) {
            return false;
        }
        redisTemplate.delete(key);
        return true;
    }

    @Override
    public boolean deleteData(List<Object> key) {
        if (CollectionUtils.isEmpty(key)) {
            return false;
        }
        redisTemplate.delete(key);
        return true;
    }
}
