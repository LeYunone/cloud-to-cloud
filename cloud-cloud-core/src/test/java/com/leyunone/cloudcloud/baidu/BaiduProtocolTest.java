package com.leyunone.cloudcloud.baidu;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduDiscoverAppliancesResponse;
import com.leyunone.cloudcloud.bean.third.baidu.DeviceControlResponse;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.protocol.baidu.BaiduDeviceControlHandler;
import com.leyunone.cloudcloud.handler.protocol.baidu.BaiduDeviceDiscoveryHandler;
import com.leyunone.cloudcloud.handler.protocol.baidu.BaiduDeviceQueryHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/12 15:19
 */
@SpringBootTest
public class BaiduProtocolTest {

    @Autowired
    private BaiduDeviceDiscoveryHandler discoverDeviceHandler;
    @Autowired
    private BaiduDeviceControlHandler deviceControlHandler;
    @Autowired
    private BaiduDeviceQueryHandler baiduDeviceQueryHandler;

    private static final ActionContext actionContext;

    static {
        AccessTokenInfo accessTokenEntity = new AccessTokenInfo();
        AccessTokenInfo.User userAuthorizeEntity = new AccessTokenInfo.User();
        userAuthorizeEntity.setUserId("1037");
        accessTokenEntity.setUser(userAuthorizeEntity);
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);

        ThirdPartyCloudConfigInfo thirdPartyCloudConfigEntity = new ThirdPartyCloudConfigInfo();
        thirdPartyCloudConfigEntity.setClientId("testbaidu");
        thirdPartyCloudConfigEntity.setReportUrl("api.amazonalexa.com");
        thirdPartyCloudConfigEntity.setThirdPartyCloud(ThirdPartyCloudEnum.ALEXA);
        thirdPartyCloudConfigEntity.setMainUrl("http://192.168.151.203:8401/smarthome/api/root/intelligent/voice");
        actionContext = new ActionContext(accessTokenEntity, thirdPartyCloudConfigEntity);
    }

    @Test
    public void discovery() {
        String request = "{\n" +
                "    \"header\": {\n" +
                "        \"namespace\": \"DuerOS.ConnectedHome.Discovery\",\n" +
                "        \"name\": \"DiscoverAppliancesRequest\",\n" +
                "        \"messageId\": \"6d6d6e14-8aee-473e-8c24-0d31ff9c17a2\",\n" +
                "        \"payloadVersion\": \"1\"\n" +
                "    },\n" +
                "    \"payload\": {\n" +
                "        \"accessToken\": \"[OAuth Token here]\",\n" +
                "        \"openUid\": \"27a7d83c2d3cfbad5d387cd35f3ca17b\"\n" +
                "    }\n" +
                "}";
        BaiduDiscoverAppliancesResponse action = discoverDeviceHandler.action(request, actionContext);
        System.out.println(action);
    }
    
    @Test
    public void control(){
        String request = "{\n" +
                "    \"header\": {\n" +
                "        \"namespace\": \"DuerOS.ConnectedHome.Control\",\n" +
                "        \"name\": \"TurnOnRequest\",\n" +
                "        \"messageId\": \"01ebf625-0b89-4c4d-b3aa-32340e894688\",\n" +
                "        \"payloadVersion\": \"1\"\n" +
                "    },\n" +
                "    \"payload\": {\n" +
                "        \"accessToken\": \"[OAuth token here]\",\n" +
                "        \"appliance\": {\n" +
                "            \"additionalApplianceDetails\": {\"sceneId\":\"5752\",\"productId\":\"scene\"},\n" +
                "            \"applianceId\": \"5752\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        DeviceControlResponse action = deviceControlHandler.action(request, actionContext);
        System.out.println();
    }
    
    @Test
    public void query() {
        String request = "{\n" +
                "    \"header\": {\n" +
                "        \"namespace\": \"DuerOS.ConnectedHome.Query\",\n" +
                "        \"name\": \"GetTurnOnStateRequest\",\n" +
                "        \"messageId\": \"01ebf625-0b89-4c4d-b3aa-32340e894688\",\n" +
                "        \"payloadVersion\": \"1\"\n" +
                "    },\n" +
                "    \"payload\": {\n" +
                "        \"accessToken\": \"[OAuth token here]\",\n" +
                "        \"appliance\": {\n" +
                "            \"additionalApplianceDetails\": {},\n" +
                "            \"applianceId\": \"[Device ID for Ceiling Fan]\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        JSONObject action = baiduDeviceQueryHandler.action(request, actionContext);
        System.out.println(action);
    }
}
