package com.leyunone.cloudcloud.handler.delay;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.leyunone.cloudcloud.bean.ClientAccessTokenModel;
import com.leyunone.cloudcloud.bean.enums.DelayMessageTypeEnum;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.message.TimingRefreshTokenMessage;
import com.leyunone.cloudcloud.dao.DeviceRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceDO;
import com.leyunone.cloudcloud.exception.AccessTokenException;
import com.leyunone.cloudcloud.handler.factory.DelayMessageHandlerFactory;
import com.leyunone.cloudcloud.handler.factory.NoOauth2GetTokenHandlerFactory;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.mangaer.DelayMessageManager;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import com.leyunone.cloudcloud.service.token.NonStandardTokenHandler;
import com.leyunone.cloudcloud.util.TimeTranslationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * :)
 * 循环定时刷新全局令牌的动作,使用延时队列头尾投递完成循环保证刷新令牌的动作不丢失
 * 适用:
 * 1.萤石云:https://open.ezviz.com/help/822
 *
 * @Author LeYunone
 * @Date 2024/6/18 10:54
 */
@Service
public class TimingLoopOverallTokenMessageHandler extends AbstractDelayMessageHandler<TimingRefreshTokenMessage> {

    private final Logger logger = LoggerFactory.getLogger(TimingLoopOverallTokenMessageHandler.class);
    private final ThirdPartyConfigService thirdPartyCloudService;
    private final DeviceRepository deviceRepository;
    private final NoOauth2GetTokenHandlerFactory noOauth2GetTokenHandlerFactory;
    private final CacheManager cacheManager;
    private final DelayMessageManager delayMessageManager;

    public TimingLoopOverallTokenMessageHandler(DelayMessageHandlerFactory delayMessageHandlerFactory, ThirdPartyConfigService thirdPartyCloudService, DeviceRepository deviceRepository, NoOauth2GetTokenHandlerFactory noOauth2GetTokenHandlerFactory, CacheManager cacheManager, DelayMessageManager delayMessageManager) {
        super(delayMessageHandlerFactory);
        this.thirdPartyCloudService = thirdPartyCloudService;
        this.deviceRepository = deviceRepository;
        this.noOauth2GetTokenHandlerFactory = noOauth2GetTokenHandlerFactory;
        this.cacheManager = cacheManager;
        this.delayMessageManager = delayMessageManager;
    }

    @Override
    public void handle1(TimingRefreshTokenMessage message) {
        if (StrUtil.isBlank(message.getMessageId()) || StrUtil.isBlank(message.getClientId())) {
            return;
        }
        Integer count = cacheManager.getData(message.getMessageId(), int.class);
        if (count == 3) {
            //TODO 重试超过3次 报警人工干预
            logger.error("TimingLoopOverallTokenMessageHandler request many ,clientId:{}", message.getClientId());
            return;
        }
        ThirdPartyCloudConfigInfo config = thirdPartyCloudService.getConfig(message.getClientId());
        if (ObjectUtil.isNull(config)) {
            return;
        }
        //检查是否有该平台设备
        List<DeviceDO> deviceDOS = deviceRepository.selectByClientId(message.getClientId());
        if (CollectionUtil.isEmpty(deviceDOS)) {
            return;
        }
        NonStandardTokenHandler template = noOauth2GetTokenHandlerFactory.getStrategy(config.getThirdPartyCloud().name(), NonStandardTokenHandler.class);
        if (ObjectUtil.isNotNull(template)) {
            /**
             *  非OAUTH2标准流程的全局授权方式, 比如萤石云:直接获取托管设备的全权Token
             */
            ClientAccessTokenModel clientAccessTokenModel = null;
            try {
                clientAccessTokenModel = template.get(message.getClientId());
            } catch (AccessTokenException e) {
                //一分钟之后重试
                delayMessageManager.pushMessage(message, 1000 * 60);
                cacheManager.count(message.getMessageId(), 1);
                return;
            }
            if (ObjectUtil.isNotNull(clientAccessTokenModel)) {
                //刷新成功更新缓存token,投递下一个时间的刷新消息
                Set<String> tokenKeys = deviceDOS.stream().map(DeviceDO::getTokenKey).collect(Collectors.toSet());
                for (String key : tokenKeys) {
                    cacheManager.addData(key, clientAccessTokenModel.getAccessToken(), 15L, TimeUnit.DAYS);

                }

                if (ObjectUtil.isNotNull(clientAccessTokenModel.getExpiresIn())) {
                    //初始化
                    cacheManager.deleteData(message.getMessageId());
                    //投递下一个时间
                    message.setMessageId(UUID.randomUUID().toString());
                    delayMessageManager.pushMessage(message, TimeTranslationUtils.expiresInTimeToInt(clientAccessTokenModel.getExpiresIn(), config.getThirdPartyCloud()));
                }
            }
        }

    }

    @Override
    String getKey() {
        return DelayMessageTypeEnum.LOOP_OVERALL_TOKEN.name();
    }
}
