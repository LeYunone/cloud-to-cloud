package com.leyunone.cloudcloud.mangaer.impl;

import com.leyunone.cloudcloud.mangaer.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/4/5
 */
@Service
public class CacheManagerImpl implements CacheManager {

    @Override
    public <T> T getData(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public boolean addData(String key, String value) {
        return false;
    }

    @Override
    public boolean addData(String key, Object value, Long time) {
        return false;
    }

    @Override
    public boolean addData(String key, Object value, Long time, TimeUnit unit) {
        return false;
    }

    @Override
    public <T> Optional<List<T>> getData(List<Object> keys, Long time, Function<List<T>, List<T>> getMissData, Function<T, String> generateKey) {
        return Optional.empty();
    }

    @Override
    public boolean deleteData(String key) {
        return false;
    }
}
