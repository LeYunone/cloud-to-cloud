package com.leyunone.cloudcloud.handler.report;


import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface DeviceMessageReportHandler {

    void handler(String message, DeviceCloudInfo.ThirdMapping thirdMapping);

}
