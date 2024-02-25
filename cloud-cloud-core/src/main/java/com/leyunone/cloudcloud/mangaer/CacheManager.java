package com.leyunone.cloudcloud.mangaer;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public interface CacheManager {


    /**
     * String类型查询
     * @param key  key
     * @param <T> 类型
     * @param clazz 目标class
     * @return return
     */
    <T> T getData(String key,Class<T> clazz);


    /**
     * 添加string类型
     * @param key keyServletUtils
     * @param value value
     * @return  boolean
     */
    boolean addData(String key, String value);


    /**
     * 添加string类型
     * @param key keyServletUtils
     * @param value value
     * @return  boolean
     */
    boolean addData(String key, Object value, Long time);

    boolean addData(String key, Object value, Long time, TimeUnit unit);



    /**
     * 批量查询数据并为miss key重新查询并缓存
     * @param <T>
     * @param keys
     * @param time
     * @param getMissData
     * @param generateKey
     * @return
     */
    <T> Optional<List<T>> getData(List<Object> keys,
                              Long time,
                              Function<List<T>,List<T>> getMissData,
                              Function<T,String>  generateKey);

    boolean deleteData(String key);

}
