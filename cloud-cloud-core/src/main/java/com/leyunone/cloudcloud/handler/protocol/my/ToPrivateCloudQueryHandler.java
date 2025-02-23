package com.leyunone.cloudcloud.handler.protocol.my;

import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolDTO;
import com.leyunone.cloudcloud.bean.enums.ProtocolCommandEnum;
import com.leyunone.cloudcloud.handler.factory.MyProtocolHandlerFactory;
import org.springframework.stereotype.Service;

/**
 * :)
 * 我方查询对方设备
 *
 * @Author LeYunone
 * @Date 2024/5/10 15:48
 */
@Service
public class ToPrivateCloudQueryHandler extends AbstractProtocolStrategyHandler<Boolean, MyCompanyProtocolDTO.QueryCommand> {


    protected ToPrivateCloudQueryHandler(MyProtocolHandlerFactory factory) {
        super(factory);
    }

    @Override
    protected String getKey() {
        return ProtocolCommandEnum.QUERY.name();
    }

    /**
     * 查询之后触发设备中心影子更新
     *
     * @param queryCommand
     * @return
     */
    @Override
    public Boolean handler(MyCompanyProtocolDTO.QueryCommand queryCommand) {
        return null;
    }


}
