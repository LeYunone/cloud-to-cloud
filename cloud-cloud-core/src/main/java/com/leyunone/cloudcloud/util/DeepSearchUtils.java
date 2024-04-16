package com.leyunone.cloudcloud.util;

import com.alibaba.fastjson.JSONObject;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/15 16:13
 */
public class DeepSearchUtils {

    /**
     * 只找一个
     * @param params
     * @return
     */
    public static String findDeepestKey(JSONObject params) {
        String key = "";
        for (String json : params.keySet()) {
            try {
                JSONObject p = JSONObject.parseObject(params.get(json).toString());
                key = json + "#" + findDeepestKey(p);
            } catch (Exception e) {
                key = json;
            }
            break;
        }
        return key;
    }
}
