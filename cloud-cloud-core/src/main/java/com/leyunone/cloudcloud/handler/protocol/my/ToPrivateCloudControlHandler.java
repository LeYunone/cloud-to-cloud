package com.leyunone.cloudcloud.handler.protocol.my;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.cloudcloud.api.protocol.MyCompanyCommand;
import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolControlRequest;
import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolDTO;
import com.leyunone.cloudcloud.bean.enums.ProtocolCommandEnum;
import com.leyunone.cloudcloud.dao.DeviceRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceDO;
import com.leyunone.cloudcloud.handler.factory.IotHttpServiceFactory;
import com.leyunone.cloudcloud.handler.factory.MyProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.mangaer.ClientOauthManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 * 我方控制对方设备
 *
 * @Author LeYunone
 * @Date 2024/5/10 15:48
 */
@Service
public class ToPrivateCloudControlHandler extends AbstractProtocolStrategyHandler<Boolean, MyCompanyProtocolDTO.ControlCommand> {

    private final DeviceRepository deviceRepository;
    private final ClientOauthManager clientOauthManager;
    private final IotHttpServiceFactory iotHttpServiceFactory;
    private final ThirdPartyConfigService thirdPartyConfigService;
    private final CacheManager cacheManager;

    public ToPrivateCloudControlHandler(MyProtocolHandlerFactory factory, DeviceRepository deviceRepository, ClientOauthManager clientOauthManager, IotHttpServiceFactory iotHttpServiceFactory, ThirdPartyConfigService thirdPartyConfigService, CacheManager cacheManager) {
        super(factory);
        this.deviceRepository = deviceRepository;
        this.clientOauthManager = clientOauthManager;
        this.iotHttpServiceFactory = iotHttpServiceFactory;
        this.thirdPartyConfigService = thirdPartyConfigService;
        this.cacheManager = cacheManager;
    }


    @Override
    protected String getKey() {
        return ProtocolCommandEnum.CONTROL.name();
    }

    @Override
    public Boolean handler(MyCompanyProtocolDTO.ControlCommand controlCommand) {
        return true;
    }


}
