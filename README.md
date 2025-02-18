# Cloud-to-Cloud(云云接入脚手架)

## 前言

测试网站：

配置说明：[https://leyunone.com/github-project/voice-cloud-cloud-config.html](https://leyunone.com/github-project/voice-cloud-cloud-config.html)

- 注：学习测试以及使用请拉取 master 分支，release 是开发分支，如果有帮助到你，感谢不留情面的提Issues或pull吐槽
- 开源不易，点个 star 鼓励一下吧！

## 简介

`问：这是什么？`

**答**：对接各大产商云如**小度**、**天猫精灵**，**小爱同学**，**Alexa**等技能协议的云云接入转化器，是一个全平台通用的中间层物联网设备模型转化平台。

`问：有什么用？`

**答**：

1. 自带完整体系的小度、小米、Alexa、Google等主流物联网平台云云接入协议，拆箱即用
2. 产商云与开发者云的模型转化被数据库配置化，可由页面或数据库操作进行可视化配置他方云协议模型与我方云协议模型的转化关系。
3. 是物联网云云接入协议技能平台的一个完整（从架构功能，授权到可视化页面）流程的解决方案

`问：定位是什么？`

**答**：以天猫精灵官网的结构图为例：

![](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-01-17/e2446cf2-1c5f-4ef7-bc43-5105acbe636d.png)

本项目平台介于`天猫精灵`到`第三方设备控制云`之间，将本该由开发云进行控制协议、发现协议等设备交互的云云协议转化，全权由本项目平台进行全平台通用的可配置模式的转化

`问：怎么用？`

**答：** 下载代码，运行数据库文件，各使用者根据自身情况修改一个类中的代码。部署项目，打开前后端不分离的配置页面，配置各平台中开发云与产商云设备模型转化。

## 流程与架构图

云云接入协议流程如下：

![](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-01-18/38e77e82-d415-4b63-ad7c-1b37810d6515.png)

本平台 = 产商云技能

以设备控制为例，简单的流程是：

1. 用户语音触发三方平台，以下以发起 `打开空调` 为例
2. 三方平台根据 `打开空调` 找到对应设备，将设备id与 `打开空调` 动作通过HTTP接口调用的方式，转递给产商云
3. 产商云根据 `控制设备` 指令协议进行接口对接，将其消耗至内部云服务中，进行实施的设备控制



云云接入的核心也将围绕着将对方模型转换为我方模型这一理念进行设计，大致架构图为：

![](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-01-18/1d5540e7-8e5a-4415-895b-f61f5a111e2e.png)

## 平台部署与代码调整

部署环境：

|    环境     |  版本  |
| :---------: | :----: |
|     JDK     |   8    |
|    Mysql    |  不限  |
|    Redis    |  不限  |
|  Rabbitmq   |  不限  |
| Google-auth | 1.19.0 |

数据库见voice_cloud.sql文件

## 后言

本项目只是一个云云协议转化的脚手架平台，如果你现在需要：

- 对接某产商的物联网云协议
- 在某产商的开发平台上创建技能
- ...

就可以选择该项目作为你需求的基石，希望能帮助到你

## 相关链接

 * 小度  [https://dueros.baidu.com/didp/doc/dueros-bot-platform/dbp-smart-home/protocol/discovery-message_markdown](https://dueros.baidu.com/didp/doc/dueros-bot-platform/dbp-smart-home/protocol/discovery-message_markdown)
 * 小爱同学 [https://developers.xiaoai.mi.com/documents/Home?type=/api/doc/render_markdown/SkillAccess/skill/CustomSkillsMain](https://developers.xiaoai.mi.com/documents/Home?type=/api/doc/render_markdown/SkillAccess/skill/CustomSkillsMain)
 * 华为HiLink  [https://developer.huawei.com/consumer/cn/doc/smarthome-Guides/yunaccount-0000001075288087](https://developer.huawei.com/consumer/cn/doc/smarthome-Guides/yunaccount-0000001075288087)

 * 天猫精灵  [https://www.aligenie.com/doc/357554/cmhq2c](https://www.aligenie.com/doc/357554/cmhq2c)
 * Alexa [https://developer.amazon.com/en-US/docs/alexa/device-apis/alexa-discovery.html](https://developer.amazon.com/en-US/docs/alexa/device-apis/alexa-discovery.html)

 * Google [https://developers.home.google.com/cloud-to-cloud/integration/sync?hl=zh-cn](https://developers.home.google.com/cloud-to-cloud/integration/sync?hl=zh-cn)
 * [物联网语音云云接入](https://leyunone.com/unidentified-business/iot-cloud-cloud.html)
 * [云云对接协议中的值组装](https://leyunone.com/Interesting-design/value-assemble.html)

## 以下选读（部署流程）

因为各开发云的业务不同，本平台一定得由使用者进行一定的代码调整，下面将以百度为例进行代码跟踪；

### oauth2授权

授权入口：`OAuthorizeController`

提供服务端授权码

```java
	@PostMapping("/authorize")    
	public String authorize(@RequestParam("clientId") String clientId, HttpServletRequest request) {
        return oAuthService.generateOAuthCode(clientId, request);
    }
```

'第三方'请求获取授权码

```java
@PostMapping("/access_token")
public AccessTokenVO accessToken(@RequestParam(value = "code", required = false) String code,
     //.....省略
```

oauth2的授权过程由于需要前端页面参与，推荐自行消化oauth2授权部分；

### 协议入口

入口：`PortalController`

```java
	@PostMapping("baidu")
    public String baidu(@RequestBody String payload) {
        return thirdPartyPortalService.portal(payload, ThirdPartyCloudEnum.BAIDU);
    }
```

使用策略模式走到 `BaiduCloudHandler`中

该类中进行如下动作

1. 刷新产商云在平台的token
2. 检查场景类特殊设备
3. 调用协议执行方法

以百度的发现设备报文为例：

```json
{
    "header": {
        "namespace": "DuerOS.ConnectedHome.Discovery",
        "name": "DiscoverAppliancesRequest",
        "messageId": "6d6d6e14-8aee-473e-8c24-0d31ff9c17a2",
        "payloadVersion": "1"
    },
    "payload": {
        "accessToken": "[OAuth Token here]",
        "openUid": "27a7d83c2d3cfbad5d387cd35f3ca17b"
    }
}
```

接下来链路为：

1. 通过namespace可以找到并执行`BaiduDeviceDiscoveryHandler`类的`action1`方法
2. 使用者在`DeviceServiceHttpManager`中修改我方协议中实际发现设备动作的请求，已知授权时的token（用户信息，业务信息），本次链路的平台配置和上下文信息。调用修改后的方法拿到设备后，将发现到的设备与本次请求的用户关系存储到数据库中，并且在缓存中建立设备-用户的关系文档。
3. 执行`BaiduDeviceConvert`模型转化方法，在`BaiduMappingAssembler`类中将发现到的产品通过数据库中配置信息，包装我方-对方的映射关系对象，最终解析将我方的产品变为百度方的产品报文；
4. 组装响应参，返回，结束；

架构大体上使用策略+抽象工厂的模式搭建，通过上述的路线查看代码，只需要修改`DeviceServiceHttpManager`中实际云通讯部分的代码即可完成部署；

**包括上报、同步、协议对接部分的所有开发云的响应参请以本项目中的DeviceInfo和DeviceFunctionDTO为准**

