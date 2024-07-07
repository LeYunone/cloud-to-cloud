package com.leyunone.cloudcloud.google;

import com.alibaba.fastjson.parser.ParserConfig;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.third.google.GoogleControlResponse;
import com.leyunone.cloudcloud.bean.third.google.GoogleDiscoveryResponse;
import com.leyunone.cloudcloud.bean.third.google.GoogleQueryResponse;
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
        thirdPartyCloudConfigEntity.setClientId("testgoogle");
        thirdPartyCloudConfigEntity.setReportUrl("api.amazonalexa.com");
        thirdPartyCloudConfigEntity.setThirdPartyCloud(ThirdPartyCloudEnum.ALEXA);
        thirdPartyCloudConfigEntity.setMainUrl("http://127.0.0.1:8401/smarthome/api/test");
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
        String request = "{\n" +
                "  \"requestId\": \"ff36a3cc-ec34-11e6-b1a0-64510650abcf\",\n" +
                "  \"inputs\": [\n" +
                "    {\n" +
                "      \"intent\": \"action.devices.EXECUTE\",\n" +
                "      \"payload\": {\n" +
                "        \"commands\": [\n" +
                "          {\n" +
                "            \"devices\": [\n" +
                "              {\n" +
                "                \"id\": \"1\",\n" +
                "                \"customData\": {\n" +
                "                  \"productId\": \"开关产品id\",\n" +
                "                  \"barValue\": true,\n" +
                "                  \"bazValue\": \"sheepdip\"\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"id\": \"2\",\n" +
                "                \"customData\": {\n" +
                "                  \"productId\": \"RGB产品id\",\n" +
                "                  \"barValue\": false,\n" +
                "                  \"bazValue\": \"moarsheep\"\n" +
                "                }\n" +
                "              }\n" +
                "            ],\n" +
                "            \"execution\": [\n" +
                "              {\n" +
                "                \"command\": \"action.devices.commands.OnOff\",\n" +
                "                \"params\": {\n" +
                "                  \"on\": true\n" +
                "                }\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        GoogleControlResponse action = googleControlHandler.action(request, actionContext);
        System.out.println(action);
    }

    @Test
    public void query() {
        
        String request = "{\n" +
                "  \"requestId\": \"ff36a3cc-ec34-11e6-b1a0-64510650abcf\",\n" +
                "  \"inputs\": [\n" +
                "    {\n" +
                "      \"intent\": \"action.devices.QUERY\",\n" +
                "      \"payload\": {\n" +
                "        \"devices\": [\n" +
                "          {\n" +
                "            \"id\": \"1\",\n" +
                "            \"customData\": {\n" +
                "              \"productId\": \"开关产品id\",\n" +
                "              \"barValue\": true,\n" +
                "              \"bazValue\": \"foo\"\n" +
                "            }\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"2\",\n" +
                "            \"customData\": {\n" +
                "              \"productId\": \"RGB产品id\",\n" +
                "              \"barValue\": false,\n" +
                "              \"bazValue\": \"bar\"\n" +
                "            }\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        GoogleQueryResponse action = googleQueryHandler.action(request, actionContext);
        System.out.println(action);
    }
}
