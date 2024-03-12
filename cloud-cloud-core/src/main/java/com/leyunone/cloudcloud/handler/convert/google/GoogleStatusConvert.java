package com.leyunone.cloudcloud.handler.convert.google;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.enums.GoogleStatusValueEnum;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.mapping.GoogleProductMapping;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.bean.mapping.StatusMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/27 18:11
 */
@Service
public class GoogleStatusConvert extends AbstractGoogleDataConverterTemplate<Map<String, Map<String,Object>>, List<DeviceInfo>> {

    protected GoogleStatusConvert(ProductMappingService productMappingService) {
        super(productMappingService);
    }

    /**
     * @param r 设备模型
     * @return key：设备id    value：属性名-属性值 对象
     */
    @Override
    public Map<String, Map<String,Object>> convert(List<DeviceInfo> r) {
        Map<String, Map<String,Object>> result = new HashMap<>();
        List<ProductMapping> mappings = productMappingService.getMapping(r.stream()
                .map(DeviceInfo::getProductId).collect(Collectors.toList()), ThirdPartyCloudEnum.GOOGLE);
        Map<String, GoogleProductMapping> productMappingMap = super.convertToMapByProductId(mappings);
        r.forEach(d -> {
            GoogleProductMapping googleProductMapping = productMappingMap.get(d.getProductId());
            if (ObjectUtil.isNull(googleProductMapping)) return;
            result.put(String.valueOf(d.getDeviceId()), this.statusConvert(d.getDeviceFunctions(), googleProductMapping.getStatusMappings()));
        });
        return result;
    }

    /**
     * key:属性名
     * value:属性值
     *
     * @param status
     * @param functionMappings
     * @return
     */
    public Map<String, Object> statusConvert(List<DeviceFunctionDTO> status, List<StatusMapping> functionMappings) {
        Map<String, Object> result = new HashMap<>();
        Map<String, List<StatusMapping>> statusMap = CollectionFunctionUtils.groupTo(functionMappings, StatusMapping::getSignCode);
        status.stream().filter(t -> statusMap.containsKey(t.getSignCode())).forEach(deviceFunction -> {
            List<StatusMapping> functionMap = statusMap.get(deviceFunction.getSignCode());
            functionMap.forEach(f -> {
                Object value = this.valueOf(deviceFunction.getValue(), f);
                //非该属性映射
                if (ObjectUtil.isNull(value)) return;
                /**
                 * 以#分隔 key 例：1、color#temperatureK    color#spectrumRgb
                 *                {"color":{
                 *                      "temperatureK":3000,
                 *                      "spectrumRgb":16711935,
                 *                      "spectrumHsv":{
                 *                          "hue":300,
                 *                          "saturation":1
                 *                      }
                 *                 }}
                 *                2、fan
                 *                {"fan":value}
                 *                3、
                 *                {"currentSensorStateData": [{
                 *                         "name": "CarbonMonoxideLevel",
                 *                         "rawValue": 200
                 *                      }]}
                 */
                String[] statusCode = f.getThirdSignCode().split("#");
                if (statusCode.length > 1) {
                    if (value.getClass().isAssignableFrom(List.class)) {
                        /**
                         * 值为数组类型
                         */
                        List list = (List) value;
                        ArrayList o = (ArrayList) result.getOrDefault(statusCode[0], new ArrayList<>());
                        o.addAll(list);
                        value = o;
                    } else {
                        value = this.buildValueMapping(statusCode, value, result);
                    }
                }

                result.put(statusCode[0].replace("[]",""), value);
            });
            /**
             * 在线状态恒为true
             */
            result.put("online", true);
        });
        return result;
    }

    private Object buildValueMapping(String[] statusCode, Object value, Map<String, Object> result) {
        String oneCode = statusCode[0];
        boolean isArray = false;
        if (oneCode.contains("[]")) {
            //数组值
            isArray = true;
            oneCode = oneCode.replace("[]", "");
        }
        //存储属性值
        Object valueStorage = value;
        //初始点位
        JSONObject preV = (JSONObject) result.getOrDefault(oneCode, new JSONObject());
        value = preV;
        for (int i = 1; i < statusCode.length; i++) {
            //当前code对象
            JSONObject currentV = (JSONObject) preV.getOrDefault(statusCode[i], new JSONObject());
            if (i == statusCode.length - 1) {
                //最后一个code赋予值
                preV.put(statusCode[i], valueStorage);
                break;
            }
            preV.put(statusCode[i], currentV);
            //对象传递,记录当前对象用于下一次遍历赋值
            preV = currentV;
        }
        return isArray ? CollectionUtil.newArrayList(value) : value;
    }

    @Override
    protected Object valueOf(String value, StatusMapping mapping) {
        /**
         * 数组体需单独拿出来解析
         */
        GoogleStatusValueEnum byEnumName = GoogleStatusValueEnum.getByEnumName(value);
        if (ObjectUtil.isNotNull(byEnumName)) {
            return byEnumName.valueConvert(value, mapping);
        }
        return super.valueOf(value, mapping);
    }
}
