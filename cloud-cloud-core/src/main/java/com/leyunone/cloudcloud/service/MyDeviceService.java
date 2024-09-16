package com.leyunone.cloudcloud.service;


import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/6 17:42
 */
public interface MyDeviceService {

    /**
     * 删除设备
     * @param deviceIds
     */
    void deleteDevice(List<String> deviceIds);
}
