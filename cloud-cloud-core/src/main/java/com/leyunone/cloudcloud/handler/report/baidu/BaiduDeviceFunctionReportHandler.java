package com.leyunone.cloudcloud.handler.report.baidu;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.enums.ReportTypeEnum;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduAttributes;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduHeader;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduStatusReportRequest;
import com.leyunone.cloudcloud.bean.dto.DeviceMessageDTO;
import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.convert.baidu.BaiduStatusConverter;
import com.leyunone.cloudcloud.handler.factory.DeviceReportHandlerFactory;
import com.leyunone.cloudcloud.handler.report.AbstractStatusCommonReportHandler;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import com.leyunone.cloudcloud.util.ThirdHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Slf4j
@Service
public class BaiduDeviceFunctionReportHandler extends AbstractStatusCommonReportHandler {

    private final BaiduStatusConverter baiduStatusConverter;

    private final CacheManager cacheManager;

    public BaiduDeviceFunctionReportHandler(DeviceReportHandlerFactory factory, RestTemplate restTemplate, ThirdPartyConfigService thirdPartyConfigService, BaiduStatusConverter baiduStatusConverter, CacheManager cacheManager) {
        super(factory, restTemplate, thirdPartyConfigService);
        this.baiduStatusConverter = baiduStatusConverter;
        this.cacheManager = cacheManager;
    }


    @Override
    public void handler2(DeviceInfo deviceInfo, ThirdPartyCloudConfigInfo config, DeviceCloudInfo.ThirdMapping thirdMapping) {

        List<BaiduAttributes> baiduAttributes = baiduStatusConverter.convert(deviceInfo);
        baiduAttributes.forEach(a -> {
            BaiduStatusReportRequest.Appliance appliance = BaiduStatusReportRequest.Appliance.builder().applianceId(deviceInfo.getDeviceId()).attributeName(a.getName()).build();
            BaiduStatusReportRequest.Payload payload = BaiduStatusReportRequest.Payload
                    .builder()
                    .botId(config.getSkillId())
                    .openUid(thirdMapping.getThirdId())
                    .appliance(appliance).build();
            BaiduHeader baiduHeader = BaiduHeader.builder().messageId(IdUtil.fastUUID()).payloadVersion("1").name("ChangeReportRequest").namespace("DuerOS.ConnectedHome.Control").build();
            BaiduStatusReportRequest request = BaiduStatusReportRequest.builder().header(baiduHeader).payload(payload).build();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            JSONObject params = JSON.parseObject(JSONObject.toJSONString(request));
            HttpEntity<JSONObject> entity = new HttpEntity<>(params,httpHeaders);
            try {
                ResponseEntity<String> exchange = restTemplate.exchange(config.getReportUrl(), HttpMethod.POST, entity, String.class);
                log.debug("report baidu iot cloud,result {}",JSONObject.toJSONString(exchange));
            }catch (Exception e){
                log.warn("baidu http request,{}",e.getMessage());
            }
        });
    }

    @Override
    public ThirdPartyCloudEnum getCloud() {
        return ThirdPartyCloudEnum.BAIDU;
    }

    @Override
    public ReportTypeEnum type() {
        return ReportTypeEnum.STATUS;
    }

    /**
     * 百度内置领冷却一分钟
     * @param message
     * @return
     */
    @Override
    protected boolean cooling(String message) {
        DeviceMessageDTO deviceMessage = JSONObject.parseObject(message, DeviceMessageDTO.class);
        String key = String.join("_", getCloud().name(), getKey(), deviceMessage.getDeviceId());

        boolean cooling;
        String data = cacheManager.getData(key, String.class);
        cooling = StrUtil.isBlank(data);
        if (cooling) {
            cacheManager.addData(key, "1", 1L, TimeUnit.MINUTES);
        }
        return cooling;
    }
}
