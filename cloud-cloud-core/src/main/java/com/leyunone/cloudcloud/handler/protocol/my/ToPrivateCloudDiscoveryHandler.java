package com.leyunone.cloudcloud.handler.protocol.my;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolDTO;
import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolDiscoveryRequest;
import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolDiscoveryResponse;
import com.leyunone.cloudcloud.bean.enums.ProtocolCommandEnum;
import com.leyunone.cloudcloud.dao.DeviceRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceDO;
import com.leyunone.cloudcloud.handler.factory.IotHttpServiceFactory;
import com.leyunone.cloudcloud.handler.factory.MyProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.ClientOauthManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * :)
 * 我方发现对方设备动作
 *
 * @Author LeYunone
 * @Date 2024/1/31 10:53
 */
@Service
public class ToPrivateCloudDiscoveryHandler extends AbstractProtocolStrategyHandler<MyCompanyProtocolDiscoveryResponse, MyCompanyProtocolDTO.DiscoveryCommand> {


    protected ToPrivateCloudDiscoveryHandler(MyProtocolHandlerFactory factory) {
        super(factory);
    }

    @Override
    protected String getKey() {
        return ProtocolCommandEnum.DISCOVERY.name();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MyCompanyProtocolDiscoveryResponse handler(MyCompanyProtocolDTO.DiscoveryCommand discoveryCommand) {
        return null;
    }

}
