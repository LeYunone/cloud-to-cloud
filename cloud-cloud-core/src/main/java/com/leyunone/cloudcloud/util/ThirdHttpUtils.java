package com.leyunone.cloudcloud.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.baidu.BaiduHeader;
import com.leyunone.cloudcloud.bean.baidu.BaiduStatusReportRequest;
import com.leyunone.cloudcloud.dao.entity.UserAuthorizeDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ThirdHttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(ThirdHttpUtils.class);

    public static final String BAIDU_STATUS_REPORT = "https://xiaodu.baidu.com/saiya/smarthome/changereport";

    public static void baiduStatusReportHttp(BaiduStatusReportRequest reportRequest) {
        Map<String, String> heads = new HashMap<>();
        heads.put("Content-Type", "application/json;charset=UTF-8");
        request(BAIDU_STATUS_REPORT, heads, Method.POST, reportRequest);
    }

    private static void request(String url, Map<String, String> heads, Method method, Object param) {
        logger.info("third http,url:{},params:{}", url, param);
        HttpUtil.createRequest(method, url)
                .headerMap(heads, false)
                .body(JSONObject.toJSONString(param))
                .execute().body();
    }
}
