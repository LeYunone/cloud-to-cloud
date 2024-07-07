package com.leyunone.cloudcloud.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2024/3/26 17:38
 */
public class DaoUtils {

    public static <DO, R> void comparisonDb(IBaseRepository<DO> baseDao, Function<DO, R> rule, List<DO> dos, List<?> dtos, Class<DO> clazz) {
        Map<R, DO> rtMap = CollectionFunctionUtils.mapTo(dos, rule);
        //进行过滤 增量修改
        List<DO> update = new ArrayList<>();
        List<DO> insert = new ArrayList<>();
        dtos.forEach(dto -> {
            try {
                DO d = clazz.newInstance();
                BeanUtil.copyProperties(dto, d);
                if (rtMap.containsKey(rule.apply(d))) {
                    update.add(d);
                    rtMap.remove(rule.apply(d));
                } else {
                    insert.add(d);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        });
        if (CollectionUtil.isNotEmpty(rtMap)) {
            baseDao.deleteById(CollectionUtil.newArrayList(rtMap.keySet()));
        }
        if (CollectionUtil.isNotEmpty(update)) {
            baseDao.updateBatchById(update);
        }
        if (CollectionUtil.isNotEmpty(insert)) {
            baseDao.saveBatch(insert);
        }
    }
}
