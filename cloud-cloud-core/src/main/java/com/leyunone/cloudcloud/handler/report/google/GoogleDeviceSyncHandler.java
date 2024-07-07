package com.leyunone.cloudcloud.handler.report.google;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.homegraph.v1.HomeGraphService;
import com.google.api.services.homegraph.v1.model.RequestSyncDevicesRequest;
import com.google.api.services.homegraph.v1.model.RequestSyncDevicesResponse;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.DeviceSyncHandlerFactory;
import com.leyunone.cloudcloud.handler.report.AbstractSyncInfoReportHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/15 16:02
 */
@Service
public class GoogleDeviceSyncHandler extends AbstractSyncInfoReportHandler {

    private static GoogleCredentials credentials;
    private static HomeGraphService homeGraphService;
    private final Logger logger = LoggerFactory.getLogger(GoogleDeviceSyncHandler.class);


    /**
     * 使用线程池管理消息中心内消息下发事件，与主线程任务分隔
     * RejectedExecutionHandler#CallerRunsPolicy :不丢弃任务，由线程池线程或主线程执行
     */
    private final ExecutorService messagePools = new ThreadPoolExecutor(2, 4,
            1L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(24),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    static {
        /**
         * 先拿到Google-api的访问token 
         */
        try {
            GoogleDeviceSyncHandler.credentials = GoogleCredentials.getApplicationDefault()
                    .createScoped(CollectionUtil.newArrayList("https://www.googleapis.com/auth/homegraph"));
            // 创建面向Google-homegraph的api令牌
            GoogleDeviceSyncHandler.homeGraphService =
                    new HomeGraphService.Builder(
                            GoogleNetHttpTransport.newTrustedTransport(),
                            GsonFactory.getDefaultInstance(),
                            new HttpCredentialsAdapter(GoogleDeviceSyncHandler.credentials))
                            .setApplicationName("HomeGraphExample/1.0")
                            .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected GoogleDeviceSyncHandler(DeviceSyncHandlerFactory factory) {
        super(factory);
    }

    @Override
    protected String getKey() {
        return ThirdPartyCloudEnum.GOOGLE.name();
    }


    @Override
    public void handler(List<String> userIds) {
        if (ObjectUtil.isNull(GoogleDeviceSyncHandler.credentials) || ObjectUtil.isNull(GoogleDeviceSyncHandler.homeGraphService))
            return;

        // Request sync.
        userIds.forEach(userId -> messagePools.execute(() -> {
            RequestSyncDevicesRequest request =
                    new RequestSyncDevicesRequest().setAgentUserId(String.valueOf(userId)).setAsync(false);
            try {
                RequestSyncDevicesResponse execute = GoogleDeviceSyncHandler.homeGraphService.devices().requestSync(request).execute();
                logger.debug("google user request sync response:{},id:{}", execute, userId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
