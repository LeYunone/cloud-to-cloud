package com.leyunone.cloudcloud.bean;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/12 10:15
 */
public class CurrentRequestContext {

    private static final ThreadLocal<Set<String>> sceneData = new ThreadLocal<>();

    public static boolean hasSceneData() {
        Set<String> ids = sceneData.get();
        return CollectionUtil.isNotEmpty(ids);
    }

    public static Set<String> getSceneData() {
        return sceneData.get();
    }

    public static void setSceneData(String value) {
        if(StringUtils.isBlank(value)) return;
        Set<String> ids = sceneData.get();
        if (CollectionUtil.isEmpty(ids)) {
            ids = new HashSet<>();
        }
        ids.add(value);
        sceneData.set(ids);
    }

    public static void setSceneData(Set<String> value) {
        if (CollectionUtil.isEmpty(value)) return;
        sceneData.set(value);
    }


    public static void remove() {
        sceneData.remove();
    }
}
