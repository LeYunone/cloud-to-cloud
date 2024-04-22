# 语言技能平台云云模型转化配置

## 背景

市场上各大产商云与我方云进行云云接入，协议对接中；一定存在我方设备类型与对方设备类型转换的动作。

在语言技能平台开发设计中，决定采用数据库配置模式映射的方式进行云云模型间的转化配置。

本篇详细说明各大平台在配置中的涉及表、参数含义以及过程。

# 涉及表

|          表名          |     表描述      |
| :--------------------: | :-------------: |
| m_product_type_mapping | 产品类型映射表  |
|   m_function_mapping   |  属性值映射表   |
|    m_action_mapping    | 设备功能映射表  |
|    m_custom_mapping    |  自定义响应参   |
|  d_device_capability   | 设备技能配置表  |
|  c_third_party_action  | namespace命名表 |

# 百度

**涉及表：** 

- `m_product_type_mapping`
- `m_function_mapping`
- `m_action_mapping`
- `m_custom_mapping`

## 产品类型映射表  

`m_product_type_mapping`

见 [https://dueros.baidu.com/didp/doc/dueros-bot-platform/dbp-smart-home/protocol/discovery-message_markdown](https://dueros.baidu.com/didp/doc/dueros-bot-platform/dbp-smart-home/protocol/discovery-message_markdown)

![image-20240319165135166](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/BAIDU-TYPE.png)

产品类型会影响小度APP界面该设备可控的功能

## 属性值映射表

`m_function_mapping`

百度的属性见文档：[https://dueros.baidu.com/didp/doc/dueros-bot-platform/dbp-smart-home/protocol/attributes_markdown](https://dueros.baidu.com/didp/doc/dueros-bot-platform/dbp-smart-home/protocol/attributes_markdown)

该表的属性映射直接作为百度的操作响应参返回:

```json
{
    "name": "turnOnState",
    "value": "ON",
    "scale": "",
    "timestampOfSample": 1496741861,
    "uncertaintyInMilliseconds": 10,
    "legalValue": "(ON, OFF)"
}
```

在`m_function_mapping` 中字段`value_mapping` 配置，对方code + value 映射成我方code +  value的模式；

比如我方云的开关功能标识为 `switch`，当值为 `0` 时关，为`1`时开

按照上述案例，可配成：

![image-20240319174819734](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/BAIDU-FUNCTION.png)

假设不存在value的映射关系，比如`brightness` 属性:

```json
{
    "name": "brightness",
    "value": 50,
    "scale": "%",
    "timestampOfSample": 1496741861,
    "uncertaintyInMilliseconds": 10,
    "legalValue": "[0, 100]"
}
```

则不需要配置`value_mapping`：

![image-20240319175006244](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_17138606032489.png)

并且，当我方属性与对方属性的值计算或者单位有冲突时，需要配置转换函数，详见代码：

比如说百度的Rgb控制，需要HSB格式：

```json
{
    "name": "color",
    "value": {
        "hue": 350.5,
        "saturation": 0.7138,
        "brightness": 0.6524
    },
    "scale": "",
    "timestampOfSample": 1496741861,
    "uncertaintyInMilliseconds": 10,
    "legalValue": "OBJECT"
}
```

但是我方云的颜色属性值为三原色的 {"r":255,"g":"255","b":255}，这时候则需要代码中提前配置好的函数 ：见 `ConvertFunctionEnum` 类

![image-20240319175350177](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_17138607756739.png)

## 设备功能映射表

`m_action_mapping`

一个产品的技能列表来自该表的配置，直接取表中的`third_party_code`字段

百度产品的技能表见文档：[https://dueros.baidu.com/didp/doc/dueros-bot-platform/dbp-smart-home/protocol/discovery-message_markdown](https://dueros.baidu.com/didp/doc/dueros-bot-platform/dbp-smart-home/protocol/discovery-message_markdown)

该表的作用是针对百度云进行控制设备时，进行对方云属性转换成我方云属性的映射。

例如开灯：

```json
{
    "header": {
        "namespace": "DuerOS.ConnectedHome.Control",
        "name": "TurnOnRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1"
    },
    "payload": {
        "accessToken": "[OAuth token here]",
        "appliance": {
            "additionalApplianceDetails": {},
            "applianceId": "[Device ID for Light]"
        }
    }
}
```

header中的name,`TurnOnRequest` ，是由技能 即 `m_action_mapping` 表中 `third_party_code` 字段 = turnOn  ，后面加上Request组成的。

并且翻阅文档可以发现，百度的所有控制操作都是有 技能名+Request作为控制指令的标识名。

并且百度的行为与技能是一对一的关系，因此配置百度的技能表，需要配置技能后，再判断是否需要映射这个控制指令的值转换关系。

有以下三种情况

**开关**：有值映射关系

![image-20240319181239508](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_17138618974456.png)

**亮度**：无值映射关系

![image-20240319181341977](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_17138629244119.png)

**技能**：查询动作

![image-20240319181440912](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_17138629822105.png)

以上可以看到在third_sign_code中，例如setBrightnessPercentage，设置灯光亮度的控制请求，配置了`brightness#value`

这里先读官方报文：[https://dueros.baidu.com/didp/doc/dueros-bot-platform/dbp-smart-home/protocol/control-message_markdown#SetBrightnessPercentageRequest](https://dueros.baidu.com/didp/doc/dueros-bot-platform/dbp-smart-home/protocol/control-message_markdown#SetBrightnessPercentageRequest)

```json
{
    "header": {
        "namespace": "DuerOS.ConnectedHome.Control",
        "name": "SetBrightnessPercentageRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1"
    },
    "payload": {
        "accessToken": "[OAuth token here]",
        "appliance": {
            "additionalApplianceDetails": {},
            "applianceId": "[Device ID]"
        },
        "brightness": {
            "value": 50.0
        }
    }
}
```

其控制值是payload中的brightness中，因此使用`brightness#value` 交由平台进行解析处理即可；

其余的所有控制指令同理

## 自定义响应参

`m_custom_mapping`

因为百度响应参存在部分的特殊性，只靠属性值映射表无法涵盖所有属性的响应结构。

比方说查询空气质量的响应结构：

```json
{
        "AQI": {
            "value": 10
        },
        "level":{
            "value":"轻度污染"
        }
}
```

所以还需要自定义响应参去定制化对应属性的响应体

![](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_17138632746868.png)

通过解析&、，、@执行填充符的代码，详见代码`CustomConvert`

# Alexa

**涉及表：** 

- `m_product_type_mapping`
- `m_function_mapping`
- `m_action_mapping`
- `d_device_capability`
- `c_third_party_action`

## 产品类型映射表

见文档[Alexa.Discovery Interface 3 | Alexa Skills Kit (amazon.com)](https://developer.amazon.com/en-US/docs/alexa/device-apis/alexa-discovery.html#display-categories)

Alexa产品的类型可被组合，组合之后的功能会展现在其APP中，配置形式和百度一致

## 属性值映射表

`m_function_mapping`

`d_device_capability`

一个产品的技能列表来自该表的配置，直接取表中的`third_action_code`字段

因为Alexa中的技能与属性是一对一的关系，因此有对应属性就一定有一个技能名

比如开关：

![image-20240320100041505](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_17138635502084.png)

对应技能名`Alexa.PowerController` ，这个技能的属性名 `powerState`

一个技能对应多个属性值的情况，比如温控：

![image-20240320100409131](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_17138636549756.png)

函数转化，比如Alexa的HSB颜色，我方为RGB：

![image-20240320100804148](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/image-20240423172251405.png)

以上只是配置我方云与产商云的模型映射关系，Alexa除了这层处理外，还需我们提供技能的语义配置词。

见文档：[Friendly Name Resources and Assets | Alexa Skills Kit (amazon.com)](https://developer.amazon.com/en-US/docs/alexa/device-apis/resources-and-assets.html#capability-resources)

在`Alexa.ModeController` 、 `Alexa.RangeController`、`Alexa.ThermostatController` 中需要以下结构：

```json
  {
              "type": "AlexaInterface",
              "interface": "Alexa.ModeController",
              "instance": "Blinds.Position",
              "version": "3",
              "properties": {
                "supported": [
                  {
                    "name": "mode"
                  }
                ],
                "retrievable": true,
                "proactivelyReported": true
              },
              "capabilityResources": {
                "friendlyNames": [
                  {
                    "@type": "asset",
                    "value": {
                      "assetId": "Alexa.Setting.Opening"
                    }
                  }
                ]
              },
              "configuration": {
                "ordered": false,
                "supportedModes": [
                  {
                    "value": "Position.Up",
                    "modeResources": {
                      "friendlyNames": [
                        {
                          "@type": "asset",
                          "value": {
                            "assetId": "Alexa.Value.Open"
                          }
                        }
                      ]
                    }
                  },
                  {
                    "value": "Position.Down",
                    "modeResources": {
                      "friendlyNames": [
                        {
                          "@type": "asset",
                          "value": {
                            "assetId": "Alexa.Value.Close"
                          }
                        }
                      ]
                    }
                  }
                ]
              },
              "semantics": {
                "actionMappings": [
                  {
                    "@type": "ActionsToDirective",
                    "actions": ["Alexa.Actions.Close", "Alexa.Actions.Lower"],
                    "directive": {
                      "name": "SetMode",
                      "payload": {
                        "mode": "Position.Down"
                      }
                    }
                  },
                  {
                    "@type": "ActionsToDirective",
                    "actions": ["Alexa.Actions.Open", "Alexa.Actions.Raise"],
                    "directive": {
                      "name": "SetMode",
                      "payload": {
                        "mode": "Position.Up"
                      }
                    }
                  }
                ],
                "stateMappings": [
                  {
                    "@type": "StatesToValue",
                    "states": ["Alexa.States.Closed"],
                    "value": "Position.Down"
                  },
                  {
                    "@type": "StatesToValue",
                    "states": ["Alexa.States.Open"],
                    "value": "Position.Up"
                  }  
                ]
              }
            }
```

`capabilityResources` ，触发词；`configuration` ，值配置； `semantics` ，语义值映射关系。

以上述配置为例，在`d_device_capability`表中配置：

![](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/image-20240320102724430.png)

最终我们只需要在 `m_function_mapping`表中将该能力配置，赋值到技能上，而技能与属性又是一对一的关系，因此表配置变成了：

![image-20240320102954131](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/image-20240423172957774.png)

## 设备功能映射表

`m_action_mapping`

![image-20240423173319339](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/image-20240423173829454.png)

Alexa的功能映射没有特殊点，和百度的处理一样，取每个控制协议中的payload的具体指对象，组装成x#x，对应到具体控制值

**Alexa的设置颜色**：

```json
{
  "directive": {
    "header": {
      "namespace": "Alexa.ColorController",
      "name": "SetColor",
      "messageId": "Unique version 4 UUID",
      "correlationToken": "Opaque correlation token",
      "payloadVersion": "3"
    },
    "endpoint": {
      "scope": {
        "type": "BearerToken",
        "token": "OAuth2.0 bearer token"
      },
      "endpointId": "Endpoint ID",
      "cookie": {}
    },
    "payload": {
      "color": {
        "hue": 350.5,
        "saturation": 0.7138,
        "brightness": 0.6524
      }
    }
  }
}
```

**Alexa的设置模式**：

```json
{
  "directive": {
    "header": {
      "namespace": "Alexa.ThermostatController",
      "name": "SetThermostatMode",
      "messageId": "Unique version 4 UUID",
      "correlationToken": "Opaque correlation token",
      "payloadVersion": "3.2"
    },
    "endpoint": {
      "scope": {
        "type": "BearerToken",
        "token": "OAuth2 bearer token"
      },
      "endpointId": "endpoint id",
      "cookie": {}
    },
    "payload": {
      "thermostatMode" : {
        "value": "COOL"
      }
    }
  }
}
```

## namespace命名表

`c_third_party_action`

我们需要将Alexa所有控制指令的`namespace` 记录到表 `c_third_party_action` 中，因为Alexa与其他的平台的控制指令的请求参模式有些不同；

百度的：

```json
{
    "header": {
        "namespace": "DuerOS.ConnectedHome.Control",
        "name": "TurnOnRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1"
    }
}
```

namespace统统都是 **DuerOS.ConnectedHome.Control**，其余平台也是；

但Alexa将每个控制的命名都是技能名命名，因为技能平台**策略工厂模式框架设计**，所以需要将涉及的所有接口命名配置到表中：

![image-20240320103739690](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/image-20240320103739690.png)

# Google

**涉及表：** 

- `m_product_type_mapping`
- `m_function_mapping`
- `m_action_mapping`
- `d_device_capability`

## 产品类型映射表

见文档：[智能家居设备类型  | Cloud-to-cloud  | Google Home Developers](https://developers.home.google.com/cloud-to-cloud/guides?hl=zh-cn)

设备类型，国外对产品的理念与我国有差异；

最明显的就是空调，空调类型在APP上没有设置温度的按钮；

又因为Google一个产品只有一个产品类型，需要根据需要去舍弃掉部分界面功能。

比如说空调没有设置温度和设置模式，温控器有设置温度但没有设置风速等

配置形式和百度一致

## 属性值映射表

一个产品的技能列表来自该表的配置，直接取表中的`third_action_code`字段

Google的一个属性与技能一定是一对一的，因此有技能有一定有一个属性，反之需要配一个属性则一定有一个对应的技能

不过Google的属性并非一个简单的code值，他存在对象内结构的属性；

因此有三种情况：

**1、**

```json
{
    "color":{
      "temperatureK":3000,
      "spectrumRgb":16711935,
      "spectrumHsv":{
      		"hue":300,
         	"saturation":1
      }
	}
}
```

**2、**

```json
{"fan":value}
```

**3、**

```json
{
    "currentSensorStateData": [{
         "name": "CarbonMonoxideLevel",
         "rawValue": 200
		}]
}
```

因此通过表配置，我们需要通过解析特殊编码，自定义的解析以上结构的情况

第一种：

![image-20240320110016629](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/image-20240423175927487.png)

通过#号，动态的组装color{},内的值对象

第三种：

![image-20240320110122643](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20240320110122643.png)

识别[]号，将属性值设置为数组逻辑；

**技能配置**

该表还是一张技能表，google需要我们返回技能中的配置：

```json
{
  "requestId": "6894439706274654512",
  "payload": {
    "agentUserId": "user123",
    "devices": [
      {
        "id": "123",
        "type": "action.devices.types.LIGHT",
        "traits": [
          "action.devices.traits.ColorSetting",
          "action.devices.traits.Brightness",
          "action.devices.traits.OnOff"
        ],
        "name": {
          "name": "Simple light"
        },
        "willReportState": true,
        "attributes": {
          "colorTemperatureRange": {
            "temperatureMinK": 2000,
            "temperatureMaxK": 6500
          }
        }
      }
    ]
  }
}
```

**attributes** 属性，所以在 `d_device_capability` 表中配置key-value格式的数据，以表示对应技能中的key-value:

![image-20240320110717797](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/H%7DNGJC%7B%297%5DO%24Z%5DEB5EX%247TW.png)

最后只需要在 `m_function_mapping` 表中设置一个或多个配置id：

![image-20240320110841404](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/IA0P1%7EG%40%28N58%7E%7BU_S9AO%24H3.png)

**TIP**：Google的属性值的数据类型非常严格，官方文档中是字符串就一定得返回字符串，是整数则一定要整数

## 设备功能映射表

Google的操作指令：

```json
{
  "requestId": "6894439706274654520",
  "inputs": [
    {
      "intent": "action.devices.EXECUTE",
      "payload": {
        "commands": [
          {
            "devices": [
              {
                "id": "123"
              }
            ],
            "execution": [
              {
                "command": "action.devices.commands.ColorAbsolute",
                "params": {
                  "color": {
                    "name": "Warm White",
                    "temperature": 3000
                  }
                }
              }
            ]
          }
        ]
      }
    }
  ]
}
```

将command+params 作为产商云code，和属性一样有两种情况。一是上述这样对象结构内的某个key，二是

```json
{
    "params": {
         "on": true
     }
}
```

因此谷歌的控制行为映射和属性值一样，存在简单的key-value以及json结构的格式

所以该表的配置：

简单key-value：

![](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/D1.png)

对象结构：

![image-20240320112706988](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2024-04-23/QFP%40RYMZ%7DYBX%5DAYKD2YAG9I.png)

做好code-code映射后，还可以根据需要配置`convert_function`，将对方属性值单位转换为我方云值单位；

# 小米

小米，华为，天猫精灵三者相似，产商都允许开发者在其云管理上自定义创建设备，说明产商云的值与我方云的值可以做到一模一样的标识signCode；

因此这类产商云，自行根据自定义配置进行数据库配置；

比方说小米设备pid，sid，分别对应设备id以及设备属性
