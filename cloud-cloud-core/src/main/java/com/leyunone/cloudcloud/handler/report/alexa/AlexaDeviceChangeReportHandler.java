package com.leyunone.cloudcloud.handler.report.alexa;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaDeviceProperty;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaDeviceReportBean;
import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.dao.entity.ThirdPartyClientDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.convert.alexa.AlexaStatusConverter;
import com.leyunone.cloudcloud.handler.factory.DeviceReportHandlerFactory;
import com.leyunone.cloudcloud.mangaer.AlexaTokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * :)
 * 上报url api.amazonalexa.com
 *
 * @Author LeYunone
 * @Date 2024/2/3 11:20
 */
@Service
public class AlexaDeviceChangeReportHandler extends AbstractAlexaDeviceMessageReportHandler {

    private final Logger logger = LoggerFactory.getLogger(AlexaDeviceChangeReportHandler.class);

    private final AlexaStatusConverter alexaStatusConverter;
    private final AlexaTokenManager alexaTokenManager;

    public AlexaDeviceChangeReportHandler(DeviceReportHandlerFactory factory, RestTemplate restTemplate,AlexaStatusConverter alexaStatusConverter, AlexaTokenManager alexaTokenManager) {
        super(factory,restTemplate);
        this.alexaStatusConverter = alexaStatusConverter;
        this.alexaTokenManager = alexaTokenManager;
    }


    @Override
    public void handler2(DeviceInfo deviceInfo, ThirdPartyClientDO config, DeviceCloudInfo.ThirdMapping thirdMapping) {
        List<AlexaDeviceProperty> properties = alexaStatusConverter.convert(deviceInfo);
        String tokenOrRefresh = alexaTokenManager.getTokenOrRefresh(thirdMapping.getUserId());
        if(StrUtil.isBlank(tokenOrRefresh)) return;
        AlexaDeviceReportBean alexaDeviceReportBean = super.buildReport(deviceInfo.getDeviceId(), properties,tokenOrRefresh);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(tokenOrRefresh);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        JSONObject params = JSON.parseObject(JSONObject.toJSONString(alexaDeviceReportBean));
        HttpEntity<JSONObject> entity = new HttpEntity<>(params, httpHeaders);
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(config.getReportUrl(), HttpMethod.POST, entity, String.class);
            logger.debug("alexa status report response:{}", exchange.toString());
        } catch (Exception e) {
            logger.error("alexa status report error,e:{}", e.getMessage());
        }
    }

    @Override
    public ThirdPartyCloudEnum getCloud() {
        return ThirdPartyCloudEnum.ALEXA;
    }

}
