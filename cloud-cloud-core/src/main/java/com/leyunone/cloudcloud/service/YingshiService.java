package com.leyunone.cloudcloud.service;


import com.leyunone.cloudcloud.bean.CallBackParams;
import com.leyunone.cloudcloud.bean.third.yingshi.YingshiTVAddressInfoResponse;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/8/30 13:57
 */
public interface YingshiService {
    
    void cleanCache();

    /**
     * 弹出已授权设备
     *
     * @param businessId 业务id
     * @param appId      我方应用id
     * @return
     */
    List<String> popBusinessDevices(String businessId, Integer appId, String clientId, boolean invalidate);

    /**
     * 缓存回流
     *
     * @param businessId
     * @param appId
     * @param deviceIds
     */
    void pushBusinessDevices(String businessId, Integer appId, String clientId, List<String> deviceIds);


    /**
     * 已授权授权记录
     *
     * @param deviceIds
     * @param callBackParams
     */
    void deviceInStoragePack(List<String> deviceIds, CallBackParams callBackParams);

    /**
     * 取消已授权设备
     *
     * @param deviceIds
     * @param callBackParams
     */
    void cancelDevice(List<String> deviceIds, CallBackParams callBackParams);

    void cancelAuth(String businessId, Integer appId, String clientId, List<String> deviceIds);

    /**
     * 取消本次链路已授权设备
     *
     * @param deviceIds
     */
    void cancelAuth(List<Long> deviceIds);


    void loadToken(CallBackParams callBackParams);

    /**
     * 保存通道号
     *
     * @param deviceSerials
     */
    void saveCameraNo(List<String> deviceSerials);

    int getCameraNo(String deviceId);

    /**
     * 获取萤石设备令牌
     *
     * @param deviceId 我方设备id
     * @return
     */
    String getDeviceToken(String deviceId);

    YingshiTVAddressInfoResponse getTVURl(String myDeviceId);
}
