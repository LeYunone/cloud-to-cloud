package com.leyunone.cloudcloud.handler.protocol.tmall;

import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.third.tmall.TmallDevice;
import com.leyunone.cloudcloud.bean.third.tmall.TmallDiscoverRequest;
import com.leyunone.cloudcloud.bean.third.tmall.TmallDiscoverResponse;
import com.leyunone.cloudcloud.bean.third.tmall.TmallHeader;
import com.leyunone.cloudcloud.constant.TmallActionConstants;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.convert.tmall.TmallDeviceInfoConverter;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/16 17:20
 */
@Service
public class TmallDiscoverDeviceHandler extends AbstractStrategyTmallHandler<TmallDiscoverResponse, TmallDiscoverRequest> {

    private final TmallDeviceInfoConverter tmallDeviceInfoConverter;
    private final DeviceServiceHttpManager deviceServiceHttpManager;

    protected TmallDiscoverDeviceHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, TmallDeviceInfoConverter tmallDeviceInfoConverter, DeviceServiceHttpManager deviceServiceHttpManager) {
        super(factory, deviceManager);
        this.tmallDeviceInfoConverter = tmallDeviceInfoConverter;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
    }

    @Override
    protected TmallDiscoverResponse action1(TmallDiscoverRequest tmallDiscoverRequest, ActionContext context) {
        String userId = context.getAccessTokenInfo().getUser().getUserId();
        List<DeviceInfo> deviceInfos = deviceServiceHttpManager.getDeviceListByUserId(context);
        /**
         * 设备关系存储
         */
        super.doRelationStore(deviceInfos, userId, context.getThirdPartyCloudConfigInfo().getClientId(), ThirdPartyCloudEnum.TMALL, (thirdMapping -> {
        }));
        TmallHeader header = tmallDiscoverRequest.getHeader();
        List<TmallDevice> tmallDevices = tmallDeviceInfoConverter.convert(deviceInfos);
        return TmallDiscoverResponse.builder()
                .header(super.buildHeader(header, header.getName() + "Response"))
                .payload(TmallDiscoverResponse.Payload.builder()
                        .devices(tmallDevices).build())
                .build();
    }

    @Override
    protected String getKey() {
        return TmallActionConstants.NAMESPACE_DISCOVERY;
    }
}
