package com.leyunone.cloudcloud.handler.protocol.baidu;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.CustomConvertModel;
import com.leyunone.cloudcloud.bean.baidu.BaiduAttributes;
import com.leyunone.cloudcloud.bean.baidu.BaiduHeader;
import com.leyunone.cloudcloud.bean.baidu.DeviceQueryRequest;
import com.leyunone.cloudcloud.bean.baidu.DeviceQueryResponse;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.dao.CustomMappingRepository;
import com.leyunone.cloudcloud.dao.entity.CustomMappingDO;
import com.leyunone.cloudcloud.handler.convert.CustomConvert;
import com.leyunone.cloudcloud.handler.convert.baidu.BaiduStatusConverter;
import com.leyunone.cloudcloud.handler.factory.StrategyFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Service
public class DeviceQueryHandler extends AbstractStrategyBaiduHandler<JSONObject,DeviceQueryRequest>{
    
    private final CustomMappingRepository customMappingRepository;
    private final DeviceServiceHttpManager deviceServiceHttpManager;
    private final CustomConvert customConvert;
    private final BaiduStatusConverter baiduStatusConverter;
    
    protected DeviceQueryHandler(StrategyFactory factory, DeviceRelationManager deviceManager, CustomMappingRepository customMappingRepository, DeviceServiceHttpManager deviceServiceHttpManager, CustomConvert customConvert, BaiduStatusConverter baiduStatusConverter) {
        super(factory, deviceManager);
        this.customMappingRepository = customMappingRepository;
        this.deviceServiceHttpManager = deviceServiceHttpManager;
        this.customConvert = customConvert;
        this.baiduStatusConverter = baiduStatusConverter;
    }

    @Override
    protected JSONObject action1(DeviceQueryRequest deviceQueryRequest, ActionContext context) {
        String applianceId = deviceQueryRequest.getPayload().getAppliance().getApplianceId();
        DeviceInfo deviceInfo = deviceServiceHttpManager.getDeviceStatusByDeviceId(context.getAccessTokenInfo().getUser().getUserId(),Long.valueOf(applianceId), context.getThirdPartyCloudConfigInfo());
        CustomMappingDO customMappingDO = customMappingRepository.selectByProductThirdCode(deviceInfo.getProductId(), deviceQueryRequest.getHeader().getName());
        JSONObject result = new JSONObject();
        String name = deviceQueryRequest.getHeader().getName();
        String action = name.replaceAll("Request", "");
        String responseName = action + "Response";
        BaiduHeader baiduHeader = buildHeader(deviceQueryRequest.getHeader().getNamespace(), responseName);
        if (ObjectUtil.isNotNull(customMappingDO) ) {
            //转换出来的自定义payload结果集
            JSONObject convert = customConvert.
                    convert(CustomConvertModel.builder()
                            .deviceInfo(deviceInfo)
                            .mappingTemplate(customMappingDO.getMappingTemplate()).build());
            result.put("header",JSONObject.toJSONString(baiduHeader));
            result.put("payload",JSONObject.toJSONString(convert));
        } else {
            DeviceQueryResponse deviceQueryResponse = new DeviceQueryResponse();
            List<BaiduAttributes> baiduAttributes = baiduStatusConverter.convert(deviceInfo);
            deviceQueryResponse.setHeader(baiduHeader);
            DeviceQueryResponse.Payload payload = DeviceQueryResponse.Payload.builder().attributes(baiduAttributes).build();
            deviceQueryResponse.setPayload(payload);
            result = JSONObject.parseObject(JSONObject.toJSONString(deviceQueryResponse));
        }
        return result;
    }
}
