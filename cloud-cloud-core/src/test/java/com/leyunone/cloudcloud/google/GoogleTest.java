package com.leyunone.cloudcloud.google;

import com.alibaba.fastjson.parser.ParserConfig;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.third.google.GoogleDiscoveryResponse;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.protocol.google.GoogleControlHandler;
import com.leyunone.cloudcloud.handler.protocol.google.GoogleDiscoveryHandler;
import com.leyunone.cloudcloud.handler.protocol.google.GoogleQueryHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/1 17:38
 */
@SpringBootTest
public class GoogleTest {


    @Autowired
    private GoogleDiscoveryHandler googleDiscoveryHandler;
    @Autowired
    private GoogleControlHandler googleControlHandler;
    @Autowired
    private GoogleQueryHandler googleQueryHandler;
    private static final ActionContext actionContext;

    static {
        AccessTokenInfo accessTokenEntity = new AccessTokenInfo();
        AccessTokenInfo.User user = new AccessTokenInfo.User();
        user.setUserId("123");
        accessTokenEntity.setUser(user);
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);

        ThirdPartyCloudConfigInfo thirdPartyCloudConfigEntity = new ThirdPartyCloudConfigInfo();
        thirdPartyCloudConfigEntity.setClientId("0043da-4f3a-9eef");
        thirdPartyCloudConfigEntity.setSkillId("d1fd03c3-c1b3-3f8a-c6e9-5deb0b8d9ddb");
        thirdPartyCloudConfigEntity.setReportUrl("api.amazonalexa.com");
        thirdPartyCloudConfigEntity.setThirdPartyCloud(ThirdPartyCloudEnum.ALEXA);
        thirdPartyCloudConfigEntity.setMainUrl("http://127.0.0.1:8401/smarthome/api/test");
        thirdPartyCloudConfigEntity.setClientSecret("d7e37547ba92");
        actionContext = new ActionContext(accessTokenEntity, thirdPartyCloudConfigEntity);
    }

    @Test
    public void discovery() {
        String request = "{\n" +
                "  \"requestId\": \"6894439706274654512\",\n" +
                "  \"inputs\": [\n" +
                "    {\n" +
                "      \"intent\": \"action.devices.SYNC\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        GoogleDiscoveryResponse action = googleDiscoveryHandler.action(request, actionContext);
        System.out.println();
    }

    @Test
    public void control() {
    }

    @Test
    public void stateReport() {
    }
}
