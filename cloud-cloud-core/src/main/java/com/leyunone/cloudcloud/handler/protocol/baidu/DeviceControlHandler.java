package com.leyunone.cloudcloud.handler.protocol.baidu;
import com.leyunone.cloudcloud.bean.baidu.*;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.enums.BaiduProtocolEnum;
import com.leyunone.cloudcloud.handler.factory.ConvertHandlerFactory;
import com.leyunone.cloudcloud.handler.factory.StrategyFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * :)
 * 标注策略组件
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Service
public class DeviceControlHandler extends AbstractStrategyBaiduHandler<DeviceControlRequest, DeviceControlResponse> {
    private final ConvertHandlerFactory converterFactory;

    protected DeviceControlHandler(ConvertHandlerFactory converterFactory, StrategyFactory factory, DeviceRelationManager deviceManager) {
        super(factory, deviceManager);
        this.converterFactory = converterFactory;
    }

    @Override
    protected DeviceControlResponse action1(DeviceControlRequest request, ActionContext context) {
        List<BaiduAttributes> baiduAttributes = null;
        String name = request.getHeader().getName();
        String action = name.replaceAll("request", "");
        String responseName = action + "Confirmation";
        BaiduHeader baiduHeader = buildHeader(request.getHeader().getNamespace(), responseName);
        DeviceControlResponse.Payload payload = DeviceControlResponse.Payload.builder().attributes(baiduAttributes).build();
        return DeviceControlResponse.builder().header(baiduHeader).payload(payload).build();
    }

    @Override
    protected String getKey() {
        return BaiduProtocolEnum.NAMESPACE_CONTROL.getName();
    }

}
