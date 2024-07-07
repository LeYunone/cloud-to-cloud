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
    }

    @Test
    public void baidu2() {
        DeviceMessageDTO deviceMessageDTO = new DeviceMessageDTO();
        List<DeviceFunctionDTO> functions = new ArrayList<>();
        DeviceFunctionDTO function = new DeviceFunctionDTO();
        function.setFunctionId(1);
        function.setSignCode("switch");
        function.setValue("0");
        functions.add(function);
        deviceMessageDTO.setDeviceFunctions(functions);
        deviceMessageDTO.setDeviceId("1");
        deviceMessageDTO.setProductId("6b920ee9b5cd430693cdfe683f8936bb");
        deviceMessageReportShuntHandler.messageShunt(JSONObject.toJSONString(deviceMessageDTO));
    }
}
