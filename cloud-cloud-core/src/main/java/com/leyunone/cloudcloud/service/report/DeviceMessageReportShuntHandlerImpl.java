package com.leyunone.cloudcloud.service.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.DeviceReportHandlerFactory;
import com.leyunone.cloudcloud.handler.report.AbstractDeviceMessageReportHandler;
import com.leyunone.cloudcloud.mangaer.DeviceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Slf4j
@Service
public class DeviceMessageReportShuntHandlerImpl implements DeviceMessageReportShuntHandler {

    private final DeviceManager deviceManager;
    private final DeviceReportHandlerFactory deviceReportHandlerFactory;

    public DeviceMessageReportShuntHandlerImpl(DeviceManager deviceManager, DeviceReportHandlerFactory deviceReportHandlerFactory) {
        this.deviceManager = deviceManager;
        this.deviceReportHandlerFactory = deviceReportHandlerFactory;
    }

    /**
     * 一条设备信息过来 根据用户信息+平台进行上报分流
     *
     * @param msg
     */
    @Override
    public void messageShunt(String msg) {
        JSONObject jsonObject = JSON.parseObject(msg);
        Long deviceId = jsonObject.getLong("deviceId");
        DeviceCloudInfo deviceEntity = deviceManager.selectByDeviceId(deviceId);
        if (null == deviceEntity) {
            log.trace("device is not exist id:{}", deviceId);
            return;
        }
        List<DeviceCloudInfo.ThirdMapping> mapping = deviceEntity.getMapping();
        mapping.forEach(m -> {
            try {
                ThirdPartyCloudEnum cloud = m.getCloud();
                AbstractDeviceMessageReportHandler template = deviceReportHandlerFactory.getStrategy(cloud.name(), AbstractDeviceMessageReportHandler.class);
                if (null != template) {
                    template.handler(msg, m);
                }
            } catch (Exception e) {
                log.warn("device message report third cloud error, third cloud" + m.getCloud() + "msg" + msg + ",ex", e);
            }
        });
    }
}
