package com.leyunone.cloudcloud.alexa;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.leyunone.cloudcloud.bean.info.AccessTokenInfo;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaDiscoveryRequest;
import com.leyunone.cloudcloud.bean.third.alexa.AlexaHeader;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.protocol.alexa.AlexaDeviceControlHandler;
import com.leyunone.cloudcloud.handler.protocol.alexa.AlexaDiscoveryHandler;
import com.leyunone.cloudcloud.handler.protocol.alexa.AlexaStateReportHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/21 17:05
 */
@SpringBootTest
public class AlexaProtocolTest {

    @Autowired
    private AlexaDiscoveryHandler alexaDiscoveryHandler;
    @Autowired
    private AlexaDeviceControlHandler alexaDeviceControlHandler;
    @Autowired
    private AlexaStateReportHandler alexaStateReportHandler;
    private static final ActionContext actionContext;

    static {
        AccessTokenInfo accessTokenEntity = new AccessTokenInfo();
        AccessTokenInfo.User userAuthorizeEntity = new AccessTokenInfo.User();
        userAuthorizeEntity.setUserId("123");
        accessTokenEntity.setUser(userAuthorizeEntity);
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
        AlexaDiscoveryRequest alexaDiscoveryRequest = new AlexaDiscoveryRequest();
        AlexaDiscoveryRequest.Directive directive = new AlexaDiscoveryRequest.Directive();
        directive.setHeader(AlexaHeader.builder()
                .payloadVersion("1")
                .namespace("1")
                .name("1")
                .messageId("1")
                .build());
        alexaDiscoveryRequest.setDirective(directive);
        String request = JSONObject.toJSONString(alexaDiscoveryRequest);
        alexaDiscoveryHandler.action(request, actionContext);
    }

    @Test
    public void control() {
        //开关
        String requestSwitch = "{\n" +
                "  \"directive\": {\n" +
                "    \"header\": {\n" +
                "      \"namespace\": \"Alexa.PowerController\",\n" +
                "      \"name\": \"TurnOn\",\n" +
                "      \"messageId\": \"Unique version 4 UUID\",\n" +
                "      \"correlationToken\": \"Opaque correlation token\",\n" +
                "      \"payloadVersion\": \"3\"\n" +
                "    },\n" +
                "    \"endpoint\": {\n" +
                "      \"scope\": {\n" +
                "        \"type\": \"BearerToken\",\n" +
                "        \"token\": \"OAuth2.0 bearer token\"\n" +
                "      },\n" +
                "      \"endpointId\": \"465302590452797445\",\n" +
                "      \"cookie\": {\"productId\":\"1287aeec6e14409d9067a87c9f696aaf\"}\n" +
                "    },\n" +
                "    \"payload\": {}\n" +
                "  }\n" +
                "}";

        //颜色
        String requestColor = "{\n" +
                "  \"directive\": {\n" +
                "    \"header\": {\n" +
                "      \"namespace\": \"Alexa.ColorController\",\n" +
                "      \"name\": \"SetColor\",\n" +
                "      \"messageId\": \"Unique version 4 UUID\",\n" +
                "      \"correlationToken\": \"Opaque correlation token\",\n" +
                "      \"payloadVersion\": \"3\"\n" +
                "    },\n" +
                "    \"endpoint\": {\n" +
                "      \"scope\": {\n" +
                "        \"type\": \"BearerToken\",\n" +
                "        \"token\": \"OAuth2.0 bearer token\"\n" +
                "      },\n" +
                "      \"endpointId\": \"465302590452797446\",\n" +
                "      \"cookie\": {\"productId\":\"2082f17119c040558daf7d803d16468d\"}\n" +
                "    },\n" +
                "    \"payload\": {\n" +
                "      \"color\": {\n" +
                "        \"hue\": 350.5,\n" +
                "        \"saturation\": 0.7138,\n" +
                "        \"brightness\": 0.6524\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        //窗帘 -模式控制
        String requestClMode = "{\n" +
                "  \"directive\": {\n" +
                "    \"header\": {\n" +
                "      \"namespace\": \"Alexa.ModeController\",\n" +
                "      \"instance\": \"Blinds.Position\",\n" +
                "      \"name\": \"SetMode\",\n" +
                "      \"messageId\": \"Unique version 4 UUID\",\n" +
                "      \"correlationToken\": \"Opaque correlation token\",\n" +
                "      \"payloadVersion\": \"3\"\n" +
                "    },\n" +
                "    \"endpoint\": {\n" +
                "      \"scope\": {\n" +
                "        \"type\": \"BearerToken\",\n" +
                "        \"token\": \"OAuth2.0 bearer token\"\n" +
                "      },\n" +
                "      \"endpointId\": \"465302590452797453\",\n" +
                "      \"cookie\": {\"productId\":\"b672cf21a8b04068bbfc6299033f0af9\"}\n" +
                "    },\n" +
                "    \"payload\": {\n" +
                "      \"mode\": \"Position.Up\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        
        //范围控制器 - 风速
        String requestFan = "{\n" +
                "  \"directive\": {\n" +
                "    \"header\": {\n" +
                "      \"namespace\": \"Alexa.RangeController\",\n" +
                "      \"instance\": \"Fan.Speed\",\n" +
                "      \"name\": \"SetRangeValue\",\n" +
                "      \"messageId\": \"Unique version 4 UUID\",\n" +
                "      \"correlationToken\": \"Opaque correlation token\",\n" +
                "      \"payloadVersion\": \"3\"\n" +
                "    },\n" +
                "    \"endpoint\": {\n" +
                "      \"scope\": {\n" +
                "        \"type\": \"BearerToken\",\n" +
                "        \"token\": \"OAuth2.0 bearer token\"\n" +
                "      },\n" +
                "      \"endpointId\": \"465302590452797456\",\n" +
                "      \"cookie\": {\"productId\":\"a82da4dce33c4410beb5d2e2f8c8518c\"}\n" +
                "    },\n" +
                "    \"payload\": {\n" +
                "      \"rangeValue\": 3\n" +
                "    }\n" +
                "  }\n" +
                "}";
        alexaDeviceControlHandler.action(requestFan, actionContext);
    }

    @Test
    public void stateReport() {
        String request = "{\n" +
                "  \"directive\": {\n" +
                "    \"header\": {\n" +
                "      \"namespace\": \"Alexa\",\n" +
                "      \"name\": \"ReportState\",\n" +
                "      \"messageId\": \"Unique version 4 UUID\",\n" +
                "      \"correlationToken\": \"Opaque correlation token\",\n" +
                "      \"payloadVersion\": \"3\"\n" +
                "    },\n" +
                "    \"endpoint\": {\n" +
                "      \"scope\": {\n" +
                "        \"type\": \"BearerToken\",\n" +
                "        \"token\": \"OAuth2.0 bearer token\"\n" +
                "      },\n" +
                "      \"endpointId\": \"465302590452797456\",\n" +
                "      \"cookie\": {\"productId\":\"a82da4dce33c4410beb5d2e2f8c8518c\"}\n" +
                "    },\n" +
                "    \"payload\": {}\n" +
                "  }\n" +
                "}";
        alexaStateReportHandler.action(request, actionContext);
    }

}
