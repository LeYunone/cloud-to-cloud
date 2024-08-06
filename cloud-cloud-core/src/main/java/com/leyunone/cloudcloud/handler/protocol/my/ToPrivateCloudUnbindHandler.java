package com.leyunone.cloudcloud.handler.protocol.my;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolDTO;
import com.leyunone.cloudcloud.bean.enums.ProtocolCommandEnum;
import com.leyunone.cloudcloud.dao.entity.DeviceDO;
import com.leyunone.cloudcloud.handler.factory.MyProtocolHandlerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * :)
 * 我方发现对方设备动作
 *
 * @Author LeYunone
 * @Date 2024/1/31 10:53
 */
@Service
public class ToPrivateCloudUnbindHandler extends AbstractProtocolStrategyHandler<Boolean, MyCompanyProtocolDTO.UnbindAuthCommand> {


    protected ToPrivateCloudUnbindHandler(MyProtocolHandlerFactory factory) {
        super(factory);
    }

    @Override
    protected String getKey() {
        return ProtocolCommandEnum.UNBIND.name();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handler(MyCompanyProtocolDTO.UnbindAuthCommand discoveryCommand) {
        return true;
    }

}
