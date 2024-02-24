package com.leyunone.cloudcloud.handler.protocol.baidu;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.leyunone.cloudcloud.bean.baidu.BaiduDevice;
import com.leyunone.cloudcloud.bean.baidu.BaiduDiscoverAppliancesResponse;
import com.leyunone.cloudcloud.bean.baidu.BaiduHeader;
import com.leyunone.cloudcloud.bean.baidu.DeviceDiscoveryRequest;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.handler.convert.baidu.BaiduDeviceConvert;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
import com.leyunone.cloudcloud.handler.factory.StrategyFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
@Service
public class DeviceDiscoveryHandler extends AbstractStrategyBaiduHandler<BaiduDiscoverAppliancesResponse, DeviceDiscoveryRequest> {

    private final DeviceServiceHttpManager deviceServiceHttpManager;
    private final BaiduDeviceConvert baiduDeviceConvert;

    protected DeviceDiscoveryHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, DeviceServiceHttpManager deviceServiceHttpManager, BaiduDeviceConvert baiduDeviceConvert) {
        super(factory, deviceManager);
        this.deviceServiceHttpManager = deviceServiceHttpManager;
        this.baiduDeviceConvert = baiduDeviceConvert;
    }

    @Override
    protected BaiduDiscoverAppliancesResponse action1(DeviceDiscoveryRequest deviceDiscoveryRequest, ActionContext context) {
        AccessTokenInfo.User user = context.getAccessTokenInfo().getUser();
        List<DeviceInfo> deviceShadowModels = deviceServiceHttpManager.getDeviceListByUserId(user.getUserId(), context.getThirdPartyCloudConfigInfo());
        List<BaiduDevice> convert = baiduDeviceConvert.convert(deviceShadowModels);
        //分组
        List<BaiduDiscoverAppliancesResponse.DiscoveredGroup> groups = deviceShadowModels.stream()
                .filter(t -> StrUtil.isNotBlank(t.getGroupName()))
                .collect(Collectors.groupingBy(DeviceInfo::getGroupName))
                .values().stream().map(group -> {
                    String groupName = CollectionUtil.getFirst(group).getGroupName();
                    List<String> deviceIds = group.stream().map(DeviceInfo::getDeviceId).collect(Collectors.toList());
                    return BaiduDiscoverAppliancesResponse.DiscoveredGroup.builder().groupName(groupName).applianceIds(Convert.toList(String.class, deviceIds)).build();
                }).collect(Collectors.toList());
        BaiduHeader baiduHeader = buildHeader(deviceDiscoveryRequest.getHeader().getNamespace(), "DiscoverAppliancesResponse");
        return BaiduDiscoverAppliancesResponse
                .builder()
                .header(baiduHeader)
                .payload(BaiduDiscoverAppliancesResponse
                        .Payload
                        .builder()
                        .discoveredAppliances(convert)
                        .discoveredGroups(groups).build())
                .build();
    }
}
