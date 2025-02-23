package com.leyunone.cloudcloud.handler.protocol.baidu;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduDevice;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduDiscoverAppliancesResponse;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduHeader;
import com.leyunone.cloudcloud.bean.third.baidu.DeviceDiscoveryRequest;
import com.leyunone.cloudcloud.constant.BaiduActionConstants;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.convert.baidu.BaiduDeviceConvert;
import com.leyunone.cloudcloud.handler.factory.CloudProtocolHandlerFactory;
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
public class BaiduDeviceDiscoveryHandler extends AbstractStrategyBaiduHandler<BaiduDiscoverAppliancesResponse, DeviceDiscoveryRequest> {

    private final DeviceServiceHttpManager deviceServiceHttpManager;
    private final BaiduDeviceConvert baiduDeviceConvert;

    protected BaiduDeviceDiscoveryHandler(CloudProtocolHandlerFactory factory, DeviceRelationManager deviceManager, DeviceServiceHttpManager deviceServiceHttpManager, BaiduDeviceConvert baiduDeviceConvert) {
        super(factory, deviceManager);
        this.deviceServiceHttpManager = deviceServiceHttpManager;
        this.baiduDeviceConvert = baiduDeviceConvert;
    }

    @Override
    protected BaiduDiscoverAppliancesResponse action1(DeviceDiscoveryRequest deviceDiscoveryRequest, ActionContext context) {
        List<DeviceInfo> deviceInfos = deviceServiceHttpManager.getDeviceListByUserId(context);
        super.doRelationStore(deviceInfos,
                context.getAccessTokenInfo().getUser().getUserId(),
                context.getThirdPartyCloudConfigInfo().getClientId(), ThirdPartyCloudEnum.BAIDU, (thirdMapping -> {
                    thirdMapping.setThirdId(deviceDiscoveryRequest.getPayload().getOpenUid());
                }));
        List<BaiduDevice> convert = baiduDeviceConvert.convert(deviceInfos);
        //分组
        List<BaiduDiscoverAppliancesResponse.DiscoveredGroup> groups = deviceInfos.stream()
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

    @Override
    protected String getKey() {
        return BaiduActionConstants.NAMESPACE_DISCOVERY;
    }
}
