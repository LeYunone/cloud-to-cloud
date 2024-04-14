package com.leyunone.cloudcloud.report;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.dto.DeviceMessageDTO;
import com.leyunone.cloudcloud.service.report.ReportMessageReportShuntHandler;
import com.leyunone.cloudcloud.support.DeviceMessageConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/12/18 17:38
 */
@SpringBootTest
public class DeviceReportService {

    @Autowired
    private DeviceMessageConsumer deviceMessageConsumer;
    @Autowired
    private ReportMessageReportShuntHandler deviceMessageReportShuntHandler;

    @Test
    public void baidu() throws IOException {
        DeviceMessageDTO deviceMessageDTO = new DeviceMessageDTO();
        List<DeviceFunctionDTO> functions = new ArrayList<>();
        DeviceFunctionDTO function = new DeviceFunctionDTO();
        function.setFunctionId(1);
        function.setSignCode("switch");
        function.setValue("0");
        functions.add(function);
        deviceMessageDTO.setDeviceFunctions(functions);
        deviceMessageDTO.setDeviceId("123");
        deviceMessageDTO.setProductId("c71c5181d04148669e12a319f4946b7e");
        deviceMessageConsumer.receiveDeviceLog(JSONObject.toJSONString(deviceMessageDTO), null, null);
    }

    @Test
    public void baidu2() {
        String message = "{\"functions\":[{\"functionId\":2,\"functionType\":1,\"signCode\":\"switch\",\"value\":\"1\"}],\"productId\":\"50ddd58d44f54f4681bffd0f237d240f\",\"messageId\":7150038410685976577,\"sessionId\":7150038410685976576,\"thenMode\":1,\"type\":\"SHADOW_FUNCTION\",\"deviceId\":412110622948462592}";
        deviceMessageReportShuntHandler.messageShunt(message);
    }
}
