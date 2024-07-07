package com.leyunone.cloudcloud.handler.report;

import cn.hutool.core.util.StrUtil;
import com.leyunone.cloudcloud.bean.enums.ReportTypeEnum;
import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.dao.ThirdPartyClientRepository;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.DeviceReportHandlerFactory;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import com.leyunone.cloudcloud.service.ThirdPartyConfigServiceImpl;
import com.leyunone.cloudcloud.strategy.AbstractStrategyAutoRegisterComponent;
import org.springframework.web.client.RestTemplate;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public abstract class AbstractDeviceMessageReportHandler extends AbstractStrategyAutoRegisterComponent implements DeviceMessageReportHandler {

    protected RestTemplate restTemplate;

    protected ThirdPartyConfigService thirdPartyConfigService;

    public AbstractDeviceMessageReportHandler(DeviceReportHandlerFactory factory, RestTemplate restTemplate, ThirdPartyConfigService thirdPartyConfigService) {
        super(factory);
        this.restTemplate = restTemplate;
        this.thirdPartyConfigService = thirdPartyConfigService;
    }

    @Override
    public void handler(String message, DeviceCloudInfo.ThirdMapping thirdMapping) {
        //冷缺
        if (cooling(message)) {
            handler1(message, thirdMapping);
        }
    }

    /**
     * 实际处理
     *
     * @param message
     * @param thirdMapping
     */
    public abstract void handler1(String message, DeviceCloudInfo.ThirdMapping thirdMapping);

    protected boolean cooling(String message) {
        return Boolean.TRUE;
    }

    /**
     * third cloud
     *
     * @return third cloud
     */
    public abstract ThirdPartyCloudEnum getCloud();

    public abstract ReportTypeEnum type();

    @Override
    public String getKey() {
        return generateKey(getCloud(), type());
    }

    private String generateKey(ThirdPartyCloudEnum cloudEnum, ReportTypeEnum typeEnum) {
        return StrUtil.join("_", typeEnum, cloudEnum);
    }
}
