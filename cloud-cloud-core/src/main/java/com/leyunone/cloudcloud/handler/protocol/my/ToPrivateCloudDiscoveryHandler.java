package com.leyunone.cloudcloud.handler.protocol.my;

import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolDTO;
import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolDiscoveryResponse;
import com.leyunone.cloudcloud.bean.enums.ProtocolCommandEnum;
import com.leyunone.cloudcloud.handler.factory.MyProtocolHandlerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
