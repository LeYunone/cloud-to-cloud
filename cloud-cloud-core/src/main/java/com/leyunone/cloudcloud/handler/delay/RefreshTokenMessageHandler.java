package com.leyunone.cloudcloud.handler.delay;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.RefreshTokenModel;
import com.leyunone.cloudcloud.bean.enums.DelayMessageTypeEnum;
import com.leyunone.cloudcloud.bean.message.RefreshTokenMessage;
import com.leyunone.cloudcloud.dao.DeviceRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceDO;
import com.leyunone.cloudcloud.handler.factory.DelayMessageHandlerFactory;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.mangaer.ClientOauthManager;
import com.leyunone.cloudcloud.util.ClientTokenUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/18 10:54
 */
@Service
public class RefreshTokenMessageHandler extends AbstractDelayMessageHandler<RefreshTokenMessage> {

    private final CacheManager cacheManager;
    private final DeviceRepository deviceRepository;

    private final ClientOauthManager clientOauthManager;

    public RefreshTokenMessageHandler(DelayMessageHandlerFactory delayMessageHandlerFactory, CacheManager cacheManager, DeviceRepository deviceRepository, ClientOauthManager clientOauthManager) {
        super(delayMessageHandlerFactory);
        this.cacheManager = cacheManager;
        this.deviceRepository = deviceRepository;
        this.clientOauthManager = clientOauthManager;
    }

    @Override
    public void handle1(RefreshTokenMessage message) {
        Integer data = cacheManager.getData(message.getMessageId(), int.class);
        if (data == 3) {
            //重试三次结束
            return;
        }
        String refreshToken = message.getRefreshToken();
        String tokenKey = ClientTokenUtils.generateClientAccessToken(message.getClientId(), message.getBusinessId(), message.getAppId());
        String refreshTokenKey = ClientTokenUtils.generateClientRefreshToken(message.getClientId(), message.getBusinessId(), message.getAppId());

        String cacheRefreshToken = cacheManager.getData(refreshTokenKey, String.class);
        /**
         * 刷新令牌变化  说明重复授权操作 本次操作为旧授权操作
         */
        if (!refreshToken.equals(cacheRefreshToken)) {
            return;
        }
        List<DeviceDO> deviceDOS = deviceRepository.selectByTokenKey(tokenKey);
        /**
         * 如果没有已授权的第三方设备 则不进行刷新
         * 等待重新授权
         */
        if (CollectionUtil.isEmpty(deviceDOS)) {
            return;
        }
        if (message.isMonth()) {
            //TODO 投放下一个节点

        } else {
            //刷新令牌
            RefreshTokenModel refreshTokenModel = new RefreshTokenModel();
            refreshTokenModel.setRefreshToken(refreshToken);
            refreshTokenModel.setClientId(message.getClientId());
            refreshTokenModel.setBusinessId(message.getBusinessId());
            refreshTokenModel.setAppId(message.getAppId());
            refreshTokenModel.setRefreshTime(message.getTimingTime());
            clientOauthManager.timingRefreshUserAccessToken(refreshTokenModel, message.getMessageId());
        }
    }

    @Override
    String getKey() {
        return DelayMessageTypeEnum.OAUTH2_REFRESH_TOKEN.name();
    }
}
