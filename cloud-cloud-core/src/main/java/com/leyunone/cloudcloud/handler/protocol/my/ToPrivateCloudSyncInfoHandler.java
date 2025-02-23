package com.leyunone.cloudcloud.handler.protocol.my;

import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolDTO;
import com.leyunone.cloudcloud.bean.enums.ProtocolCommandEnum;
import com.leyunone.cloudcloud.handler.factory.MyProtocolHandlerFactory;
import org.springframework.stereotype.Service;


/**
 * :)
 * 我方主动同步对方设备
 *
 * @Author LeYunone
 * @Date 2024/5/10 15:48
 */
@Service
public class ToPrivateCloudSyncInfoHandler extends AbstractProtocolStrategyHandler<Boolean, MyCompanyProtocolDTO.SyncCommand> {

    protected ToPrivateCloudSyncInfoHandler(MyProtocolHandlerFactory factory) {
        super(factory);
    }

    @Override
    protected String getKey() {
        return ProtocolCommandEnum.SYNCINFO.name();
    }

    /**
     * 同步服务 = 调用发现设备，我方自行判断更新
     *
     * @param syncCommand
     * @return
     */
    @Override
    public Boolean handler(MyCompanyProtocolDTO.SyncCommand syncCommand) {
        return true;
    }
}
