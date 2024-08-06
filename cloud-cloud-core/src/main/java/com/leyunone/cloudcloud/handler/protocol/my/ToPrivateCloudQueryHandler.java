package com.leyunone.cloudcloud.handler.protocol.my;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.cloudcloud.api.MyCompanyProtocolShadowModel;
import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolDTO;
import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolQueryRequest;
import com.leyunone.cloudcloud.api.protocol.MyCompanyProtocolQueryResponse;
import com.leyunone.cloudcloud.bean.enums.ProtocolCommandEnum;
import com.leyunone.cloudcloud.dao.DeviceRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceDO;
import com.leyunone.cloudcloud.handler.factory.MyProtocolHandlerFactory;
import com.leyunone.cloudcloud.mangaer.ClientOauthManager;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
