package com.leyunone.cloudcloud.handler.protocol.baidu;

import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduAttributes;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduHeader;
import com.leyunone.cloudcloud.bean.third.baidu.DeviceControlRequest;
import com.leyunone.cloudcloud.bean.third.baidu.DeviceControlResponse;
import com.leyunone.cloudcloud.constant.BaiduActionConstants;
import com.leyunone.cloudcloud.handler.convert.baidu.BaiduActionConvert;
import com.leyunone.cloudcloud.handler.convert.baidu.BaiduStatusConverter;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
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
public class DeviceControlHandler extends AbstractStrategyBaiduHandler<DeviceControlResponse, DeviceControlRequest> {

    private final BaiduActionConvert baiduActionConvert;
    private final DeviceServiceHttpManager deviceServiceHttpManager;
    private final BaiduStatusConverter baiduStatusConverter;

    protected DeviceControlHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, BaiduActionConvert baiduActionConvert, DeviceServiceHttpManager deviceServiceHttpManager, BaiduStatusConverter baiduStatusConverter) {
        super(factory, deviceManager);
        this.baiduActionConvert = baiduActionConvert;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
        this.baiduStatusConverter = baiduStatusConverter;
    }


    @Override
    protected DeviceControlResponse action1(DeviceControlRequest request, ActionContext context) {
        DeviceFunctionDTO convert = baiduActionConvert.convert(request);
        DeviceInfo deviceInfo = deviceServiceHttpManager.command(context.getAccessTokenInfo().getUser().getUserId(), convert, context.getThirdPartyCloudConfigInfo());
        List<BaiduAttributes> baiduAttributes = baiduStatusConverter.convert(deviceInfo);
        String name = request.getHeader().getName();
        String action = name.replaceAll("request", "");
        String responseName = action + "Confirmation";
        BaiduHeader baiduHeader = buildHeader(request.getHeader().getNamespace(), responseName);
        DeviceControlResponse.Payload payload = DeviceControlResponse.Payload.builder().attributes(baiduAttributes).build();
        return DeviceControlResponse.builder().header(baiduHeader).payload(payload).build();
    }

    @Override
    protected String getKey() {
        return BaiduActionConstants.NAMESPACE_CONTROL;
    }

}
