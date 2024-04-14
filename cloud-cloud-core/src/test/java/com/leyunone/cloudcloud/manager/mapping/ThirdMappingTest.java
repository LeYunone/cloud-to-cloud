package com.leyunone.cloudcloud.manager.mapping;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.CustomConvertModel;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.handler.convert.CustomConvert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/12/14 17:00
 */
public class ThirdMappingTest {

    @Test
    public void customMapping() {
        String str = "{\"PM25\": {\"scale\": \"μg/m3\", \"value\": \"value\"}}";
        Map<String, Object> de = new HashMap<>();
        de.put("value", 100);
        Object o = iteratorMapping(str, de);
        System.out.println(o);
    }

    @Test
    public void customMapping2() {
        CustomConvertModel customConvertModel = new CustomConvertModel();
        List<DeviceFunctionDTO> deviceFunctions = new ArrayList<>();
        DeviceFunctionDTO deviceFunction = new DeviceFunctionDTO();
        deviceFunction.setSignCode("AQI");
        deviceFunction.setValue("100");
        deviceFunctions.add(deviceFunction);
        DeviceInfo deviceShadowModel = new DeviceInfo();
        deviceShadowModel.setDeviceFunctions(deviceFunctions);
        customConvertModel.setDeviceInfo(deviceShadowModel);
        customConvertModel.setMappingTemplate("{\"temperatureReading\": {\"scale\": \"CELSIUS\", \"value\": \"temperature&value\"}, \"applianceResponseTimestamp\": \"@date\"}");
        JSONObject convert = new CustomConvert(null).convert(customConvertModel);
        System.out.println(convert);
    }

    public static Object iteratorMapping(String json, Map<String, Object> deviceMap) {
        JSONObject result = new JSONObject();
        String keyValue = "";
        try {
            keyValue = json;
            JSONObject jsonObject = JSONObject.parseObject(json);
            for (String key : jsonObject.keySet()) {
                result.put(key, iteratorMapping(jsonObject.getString(key), deviceMap));
            }
        } catch (JSONException e) {
            //非json字符串 终止符
            return deviceMap.getOrDefault(keyValue, keyValue);
        }
        return result;
    }
}
