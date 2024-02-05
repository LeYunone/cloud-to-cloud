package com.leyunone.cloudcloud.handler.protocol.baidu;

import com.leyunone.cloudcloud.bean.baidu.BaiduDiscoverAppliancesResponse;
import com.leyunone.cloudcloud.bean.baidu.DeviceDiscoveryRequest;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.handler.factory.StrategyFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
public class DeviceDiscoveryHandler extends AbstractStrategyBaiduHandler<BaiduDiscoverAppliancesResponse, DeviceDiscoveryRequest> {

    protected DeviceDiscoveryHandler(StrategyFactory factory, DeviceRelationManager deviceManager) {
        super(factory, deviceManager);
    }

    @Override
    protected BaiduDiscoverAppliancesResponse action1(DeviceDiscoveryRequest deviceDiscoveryRequest, ActionContext context) {
        return null;
    }
}
