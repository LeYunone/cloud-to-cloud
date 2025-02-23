package com.leyunone.cloudcloud.handler.protocol.tmall;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.enums.TmallResultEnum;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.bean.mapping.StatusMapping;
import com.leyunone.cloudcloud.bean.third.tmall.TmallControlRequest;
import com.leyunone.cloudcloud.bean.third.tmall.TmallControlResponse;
import com.leyunone.cloudcloud.bean.third.tmall.TmallHeader;
import com.leyunone.cloudcloud.bean.third.tmall.TmallResult;
import com.leyunone.cloudcloud.constant.TmallActionConstants;
import com.leyunone.cloudcloud.enums.OperationEnum;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/18 10:53
 */
@Service
public class TmallDeviceControlHandler extends AbstractStrategyTmallHandler<TmallControlResponse, TmallControlRequest> {

    private final ProductMappingService productMappingManager;
    private final DeviceServiceHttpManager deviceServiceHttpManager;

    protected TmallDeviceControlHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, ProductMappingService productMappingManager, DeviceServiceHttpManager deviceServiceHttpManager) {
        super(factory, deviceManager);
        this.productMappingManager = productMappingManager;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
    }


    /**
     * @param tmallControlRequest
     * @param context
     * @return
     */
    @Override
    protected TmallControlResponse action1(TmallControlRequest tmallControlRequest, ActionContext context) {

        List<DeviceFunctionDTO> functionCodeCommands = this.buildParams(tmallControlRequest);
        List<DeviceInfo> commands = deviceServiceHttpManager.commands(context, functionCodeCommands);
        Map<String, DeviceInfo> deviceResult = CollectionFunctionUtils.mapTo(commands, DeviceInfo::getDeviceId);
        /**
         * 封装结果集
         */
        List<TmallResult> results = tmallControlRequest
                .getPayload().getDeviceIds().stream().map(deviceId -> {
                    TmallResultEnum result = TmallResultEnum.SUCCESS;
                    if (!deviceResult.containsKey(Long.valueOf(deviceId))) {
                        result = TmallResultEnum.DEVICE_NOT_SUPPORT_FUNCTION;
                    }
                    DeviceInfo deviceInfo = deviceResult.get(Long.valueOf(deviceId));
                    if (!deviceInfo.isOnline()) {
                        result = TmallResultEnum.IOT_DEVICE_OFFLINE;
                    }
                    return TmallResult.builder().deviceId(deviceId)
                            .message(result.getMessage()).errorCode(result.name()).build();
                }).collect(Collectors.toList());

        return TmallControlResponse.builder()
                .header(super.buildHeader(tmallControlRequest.getHeader(), "CorrectResponse"))
                .payload(TmallControlResponse.Payload.builder().deviceResponseList(results).build()).build();
    }

    private List<DeviceFunctionDTO> buildParams(TmallControlRequest tmallControlRequest) {
        TmallControlRequest.Payload payload = tmallControlRequest.getPayload();
        Map<String, Object> extensions = payload.getExtensions();
        Map<String, String> deviceMap = payload.getDeviceIds().stream().filter(extensions::containsKey).collect(Collectors.toMap(t -> t, deviceId -> {
            JSONObject extensionInfo = JSONObject.parseObject(JSONObject.toJSONString(extensions.get(deviceId)));
            return extensionInfo.getString("productId");
        }));

        List<ProductMapping> mapping = productMappingManager.getMapping(CollectionUtil.newArrayList(deviceMap.values()), ThirdPartyCloudEnum.TMALL);
        Map<String, ProductMapping> productMap = CollectionFunctionUtils.mapTo(mapping, ProductMapping::getProductId);
        TmallHeader header = tmallControlRequest.getHeader();
        String name = header.getName();

        DeviceFunctionDTO functionCodeCommand = new DeviceFunctionDTO();
        //控制仅支持一种属性
        Object value = payload.getParams().values().stream().findFirst().orElse(null);
        /**
         * name : 1\thing.attribute.set 设置属性
         *        2\thing.attribute.adjust 上调/下调属性
         */
        switch (name) {
            case "thing.attribute.set":
                functionCodeCommand.setValue(ObjectUtil.isNotNull(value) ? String.valueOf(value) : null);
                break;
            case "thing.attribute.adjust":
                functionCodeCommand.setValue(String.valueOf(value));
                functionCodeCommand.setOperation(Integer.parseInt((String) value) < 0 ? OperationEnum.DECREASE : OperationEnum.SUM);
                break;
            default:
        }
        return deviceMap.keySet().stream().map(deviceId -> {
            String productId = deviceMap.get(deviceId);

            ProductMapping productMapping = productMap.get(productId);
            if (ObjectUtil.isNull(productMapping)) return null;
            Map<String, StatusMapping> functionMap = CollectionFunctionUtils.mapTo(productMapping.getStatusMappings(), StatusMapping::getThirdSignCode);
            DeviceFunctionDTO currentDeviceCommand = new DeviceFunctionDTO();
            BeanUtil.copyProperties(functionCodeCommand, currentDeviceCommand);
            currentDeviceCommand.setDeviceId(deviceId);
            currentDeviceCommand.setSignCode(functionMap.get(deviceId).getSignCode());
            return currentDeviceCommand;
        }).collect(Collectors.toList());
    }

    @Override
    protected String getKey() {
        return TmallActionConstants.NAMESPACE_CONTROL;
    }
}
