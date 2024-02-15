package com.leyunone.cloudcloud.service.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


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


    /**
     * 一条设备信息过来 根据用户信息+平台进行上报分流
     * @param msg
     */
    @Override
    public void messageShunt(String msg) {
    }
}
