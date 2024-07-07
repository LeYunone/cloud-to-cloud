package com.leyunone.cloudcloud.handler.report.alexa;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.enums.ReportTypeEnum;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaDeviceProperty;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaDeviceReportBean;
import com.leyunone.cloudcloud.bean.dto.DeviceMessageDTO;
import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.DeviceReportHandlerFactory;
import com.leyunone.cloudcloud.mangaer.impl.AlexaTokenManager;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import com.leyunone.cloudcloud.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/19 17:40
 */
@Service
public class AlexaDeviceOnlineReportHandler extends AbstractAlexaDeviceMessageReportHandler {

    private final CacheManager cacheManager;
    private final AlexaTokenManager alexaTokenManager;
    private final ThirdPartyConfigService thirdPartyConfigService;
    public static final Logger logger = LoggerFactory.getLogger(AlexaDeviceOnlineReportHandler.class);

    public AlexaDeviceOnlineReportHandler(DeviceReportHandlerFactory factory, RestTemplate restTemplate, ThirdPartyConfigService thirdPartyConfigService, CacheManager cacheManager, AlexaTokenManager alexaTokenManager, ThirdPartyConfigService thirdPartyConfigService1) {
        super(factory, restTemplate, thirdPartyConfigService);
        this.cacheManager = cacheManager;
        this.alexaTokenManager = alexaTokenManager;
        this.thirdPartyConfigService = thirdPartyConfigService1;
    }


    @Override
    public void handler1(String message, DeviceCloudInfo.ThirdMapping thirdMapping) {
        DeviceMessageDTO deviceMessage = JSONObject.parseObject(message, DeviceMessageDTO.class);

        String clientId = thirdMapping.getClientId();
        ThirdPartyCloudConfigInfo config = thirdPartyConfigService.getConfig(clientId);
        String tokenOrRefresh = alexaTokenManager.getTokenOrRefresh(thirdMapping.getUserId());
        /**
         * 设备上下线
         */
        JSONObject online = new JSONObject();
        online.put("value", deviceMessage.isOnline() ? "OK" : "UNREACHABLE");
        AlexaDeviceReportBean deviceReportBean = super.buildReport(deviceMessage.getDeviceId(), CollectionUtil.newArrayList(AlexaDeviceProperty.builder()
                .name("connectivity")
                .namespace("Alexa.EndpointHealth")
                .value(online)
                .timeOfSample(TimeUtils.getUTCyyyyMMddTHHmmssSSSZ())
                .uncertaintyInMilliseconds(0L)
                .build()), tokenOrRefresh);
        deviceReportBean.setContext(AlexaDeviceReportBean.Context.builder()
                .properties(CollectionUtil.newArrayList(AlexaDeviceProperty.builder()
                        .namespace("Alexa.EndpointHealth")
                        .name("connectivity")
                        .value(online)
                        .timeOfSample(TimeUtils.getUTCyyyyMMddTHHmmssSSSZ())
                        .uncertaintyInMilliseconds(0L)
                        .build()))
                .build());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        JSONObject params = JSON.parseObject(JSONObject.toJSONString(deviceReportBean));
        HttpEntity<JSONObject> entity = new HttpEntity<>(params, httpHeaders);
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(config.getReportUrl(), HttpMethod.POST, entity, String.class);
            logger.debug("alexa device online report response:{}", exchange.toString());
        } catch (Exception e) {
            logger.error("alexa device online report error,e:{}", e.getMessage());
        }
    }

    /**
     * 设备上下线 针对离线在线属性判断执行同步
     *
     * @param message
     * @return
     */
    @Override
    protected boolean cooling(String message) {
        DeviceMessageDTO deviceMessage = JSONObject.parseObject(message, DeviceMessageDTO.class);
        String key = new StringBuilder(getCloud().name()).append("_").append(deviceMessage.getDeviceId()).toString();
        String data = cacheManager.getData(key, String.class);
        if (StrUtil.isNotBlank(data)) {
            Boolean currentBoolean = Boolean.valueOf(data);
            if (currentBoolean.equals(deviceMessage.isOnline())) {
                return Boolean.FALSE;
            }
        } else {
            cacheManager.addData(key, String.valueOf(deviceMessage.isOnline()), 15L, TimeUnit.SECONDS);
        }
        return Boolean.TRUE;
    }


    @Override
    public ThirdPartyCloudEnum getCloud() {
        return ThirdPartyCloudEnum.ALEXA;
    }

    @Override
    public ReportTypeEnum type() {
        return ReportTypeEnum.ONLINE;
    }
}
