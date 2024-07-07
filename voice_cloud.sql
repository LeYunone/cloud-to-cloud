/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : 127.0.0.1:3306
 Source Schema         : voice_cloud

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 06/05/2024 23:37:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for c_third_party_action
-- ----------------------------
DROP TABLE IF EXISTS `c_third_party_action`;
CREATE TABLE `c_third_party_action`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `action_identify` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '技能标识',
  `third_party_cloud` enum('BAIDU','XIAOMI','HUAWEI','TMALL','ALEXA','GOOGLE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语音平台  ',
  `action_type` tinyint(1) NULL DEFAULT NULL COMMENT '技能类型 0查询 1控制 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '语音平台动作标识' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of c_third_party_action
-- ----------------------------
INSERT INTO `c_third_party_action` VALUES (1, 'Alexa.BrightnessController', 'ALEXA', 1);
INSERT INTO `c_third_party_action` VALUES (2, 'Alexa.PowerController', 'ALEXA', 1);
INSERT INTO `c_third_party_action` VALUES (3, 'Alexa.ColorController', 'ALEXA', 1);
INSERT INTO `c_third_party_action` VALUES (4, 'Alexa.ColorTemperatureController', 'ALEXA', 1);
INSERT INTO `c_third_party_action` VALUES (5, 'Alexa.ThermostatController', 'ALEXA', 1);
INSERT INTO `c_third_party_action` VALUES (6, 'Alexa.ModeController', 'ALEXA', 1);
INSERT INTO `c_third_party_action` VALUES (7, 'Alexa.RangeController', 'ALEXA', 1);
INSERT INTO `c_third_party_action` VALUES (8, 'Alexa.TemperatureSensor', 'ALEXA', 1);
INSERT INTO `c_third_party_action` VALUES (9, 'Alexa.PercentageController', 'ALEXA', 1);

-- ----------------------------
-- Table structure for c_third_party_client
-- ----------------------------
DROP TABLE IF EXISTS `c_third_party_client`;
CREATE TABLE `c_third_party_client`  (
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `client_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `main_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `skill_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `additional_information` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `redirect_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `report_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上报url',
  `third_party_cloud` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `third_client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '产商云客户端id',
  `third_client_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '产商云客户端秘钥',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of c_third_party_client
-- ----------------------------
INSERT INTO `c_third_party_client` VALUES ('testalexa', 'test', 'test', 'test', 'test', 'test', 'test', 'https://api.amazonalexa.com/v3/events', 'ALEXA', '2023-12-06 15:55:36', '2024-04-10 15:33:17', 'test', 'test');
INSERT INTO `c_third_party_client` VALUES ('testbaidu', 'test', 'test', 'test', 'test', 'test', 'test', 'https://xiaodu.baidu.com/saiya/smarthome/changereport', 'BAIDU', '2023-12-06 15:55:36', '2024-04-10 15:21:41', NULL, NULL);
INSERT INTO `c_third_party_client` VALUES ('testgoogle', 'test', 'test', 'test', 'test', 'test', 'test', NULL, 'GOOGLE', '2023-12-06 15:55:36', NULL, NULL, NULL);
INSERT INTO `c_third_party_client` VALUES ('testxiaomi', 'test', 'test', 'test', 'test', 'test', 'test', 'https://api.home.mi.com/api/xiot/notify', 'XIAOMI', '2023-12-06 15:55:36', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for d_device
-- ----------------------------
DROP TABLE IF EXISTS `d_device`;
CREATE TABLE `d_device`  (
  `device_id` bigint(20) NOT NULL COMMENT '设备Id',
  `product_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`device_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of d_device
-- ----------------------------
INSERT INTO `d_device` VALUES (1, '开关产品id', '2024-04-19 05:39:32', '2024-04-19 06:09:30');
INSERT INTO `d_device` VALUES (2, 'RGB产品id', '2024-04-19 05:45:23', '2024-04-19 06:09:30');
INSERT INTO `d_device` VALUES (3, 'HVAC产品id', '2024-04-19 06:05:15', '2024-04-19 06:09:30');

-- ----------------------------
-- Table structure for d_device_capability
-- ----------------------------
DROP TABLE IF EXISTS `d_device_capability`;
CREATE TABLE `d_device_capability`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `capability_semantics` json NULL COMMENT '能力语义',
  `capability_configuration` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资源配置',
  `third_party_cloud` enum('BAIDU','XIAOMI','HUAWEI','TMALL','ALEXA','GOOGLE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语音平台  ',
  `instance_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '实例名',
  `value_semantics` json NULL COMMENT '值语义',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '解释',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '第三平台能力映射表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of d_device_capability
-- ----------------------------
INSERT INTO `d_device_capability` VALUES (1, '{\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Setting.Opening\"}}]}', '{\"ordered\": false, \"supportedModes\": [{\"value\": \"Position.Up\", \"modeResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Open\"}}]}}, {\"value\": \"Position.Down\", \"modeResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Close\"}}]}}]}', 'ALEXA', 'Blinds.Position', '{\"stateMappings\": [{\"@type\": \"StatesToValue\", \"value\": \"Position.Down\", \"states\": [\"Alexa.States.Closed\"]}, {\"@type\": \"StatesToValue\", \"value\": \"Position.Up\", \"states\": [\"Alexa.States.Open\"]}], \"actionMappings\": [{\"@type\": \"ActionsToDirective\", \"actions\": [\"Alexa.Actions.Close\", \"Alexa.Actions.Lower\"], \"directive\": {\"name\": \"SetMode\", \"payload\": {\"mode\": \"Position.Down\"}}}, {\"@type\": \"ActionsToDirective\", \"actions\": [\"Alexa.Actions.Open\", \"Alexa.Actions.Raise\"], \"directive\": {\"name\": \"SetMode\", \"payload\": {\"mode\": \"Position.Up\"}}}]}', '打开关闭； \r\n语言：[打开、抬起]  [关闭、放下]', NULL);
INSERT INTO `d_device_capability` VALUES (2, NULL, '{\"supportedModes\": [\"HEAT\", \"COOL\"]}', 'ALEXA', NULL, NULL, '恒温器组件，制冷、制热 模式', NULL);
INSERT INTO `d_device_capability` VALUES (3, '{\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Setting.FanSpeed\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Speed\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Velocidad\", \"locale\": \"es-MX\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Vitesse\", \"locale\": \"fr-CA\"}}]}', '{\"presets\": [{\"rangeValue\": 3, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Maximum\"}}, {\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.High\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Highest\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Fast\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Alta\", \"locale\": \"es-MX\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Élevée\", \"locale\": \"fr-CA\"}}]}}, {\"rangeValue\": 2, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Medium\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Medium\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Mid\", \"locale\": \"en-US\"}}]}}, {\"rangeValue\": 1, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Minimum\"}}, {\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Low\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Lowest\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Slow\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Baja\", \"locale\": \"es-MX\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Faible\", \"locale\": \"fr-CA\"}}]}}, {\"rangeValue\": 0, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Close\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Close\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Stop\", \"locale\": \"en-US\"}}]}}, {\"rangeValue\": 4, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Setting.Auto\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Automatic\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Automatically \", \"locale\": \"en-US\"}}]}}], \"supportedRange\": {\"precision\": 1, \"maximumValue\": 4, \"minimumValue\": 0}}', 'ALEXA', 'Fan.Speed', NULL, '风速，0-4，关、低、中、高、自动；\r\n语言：[关闭、停止] [最低、最小] [中等、中间] [最高，最大] [自动、自动地]', NULL);
INSERT INTO `d_device_capability` VALUES (4, '{\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Setting.FanSpeed\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Speed\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Velocidad\", \"locale\": \"es-MX\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Vitesse\", \"locale\": \"fr-CA\"}}]}', '{\"presets\": [{\"rangeValue\": 3, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Maximum\"}}, {\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.High\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Highest\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Fast\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Alta\", \"locale\": \"es-MX\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Élevée\", \"locale\": \"fr-CA\"}}]}}, {\"rangeValue\": 2, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Medium\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Medium\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Mid\", \"locale\": \"en-US\"}}]}}, {\"rangeValue\": 1, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Minimum\"}}, {\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Low\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Lowest\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Slow\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Baja\", \"locale\": \"es-MX\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Faible\", \"locale\": \"fr-CA\"}}]}}, {\"rangeValue\": 4, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Setting.Auto\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Automatic\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Automatically \", \"locale\": \"en-US\"}}]}}], \"supportedRange\": {\"precision\": 1, \"maximumValue\": 4, \"minimumValue\": 1}}', 'ALEXA', 'Fan.Speed', NULL, '风速，1-4，低、中、高、自动；\r\n语言：[最低、最小] [中等、中间] [最高，最大] [自动、自动地]', NULL);
INSERT INTO `d_device_capability` VALUES (5, NULL, '{\"supportedModes\": [\"HEAT\", \"COOL\", \"AUTO\"]}', 'ALEXA', NULL, NULL, '恒温器组件，制冷、制热、自动 模式', NULL);
INSERT INTO `d_device_capability` VALUES (6, NULL, '{\"supportedModes\": [\"HEAT\", \"OFF\"]}', 'ALEXA', NULL, NULL, '恒温器组件，制热、关闭 模式', NULL);
INSERT INTO `d_device_capability` VALUES (7, '{\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Setting.FanSpeed\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Speed\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Velocidad\", \"locale\": \"es-MX\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Vitesse\", \"locale\": \"fr-CA\"}}]}', '{\"presets\": [{\"rangeValue\": 3, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Maximum\"}}, {\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.High\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Highest\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Fast\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Alta\", \"locale\": \"es-MX\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Élevée\", \"locale\": \"fr-CA\"}}]}}, {\"rangeValue\": 2, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Medium\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Medium\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Mid\", \"locale\": \"en-US\"}}]}}, {\"rangeValue\": 1, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Minimum\"}}, {\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Low\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Lowest\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Slow\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Baja\", \"locale\": \"es-MX\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Faible\", \"locale\": \"fr-CA\"}}]}}, {\"rangeValue\": 0, \"presetResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Close\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Close\", \"locale\": \"en-US\"}}, {\"@type\": \"text\", \"value\": {\"text\": \"Stop\", \"locale\": \"en-US\"}}]}}], \"supportedRange\": {\"precision\": 1, \"maximumValue\": 4, \"minimumValue\": 0}}', 'ALEXA', 'Fan.Speed', NULL, '风速，0-3，关、低、中、高；\r\n语言：[关闭、停止] [最低、最小] [中等、中间] [最高，最大] ', NULL);
INSERT INTO `d_device_capability` VALUES (8, '{\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Setting.Auto\"}}]}', '{\"ordered\": false, \"supportedModes\": [{\"value\": \"Auto.Open\", \"modeResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Open\"}}]}}, {\"value\": \"Auto.Off\", \"modeResources\": {\"friendlyNames\": [{\"@type\": \"asset\", \"value\": {\"assetId\": \"Alexa.Value.Close\"}}]}}]}		', 'ALEXA', 'Auto.State', '{\"stateMappings\": [{\"@type\": \"StatesToValue\", \"value\": \"Auto.Open\", \"states\": [\"Alexa.States.Open\"]}, {\"@type\": \"StatesToValue\", \"value\": \"Auto.Off\", \"states\": [\"Alexa.States.Close\"]}], \"actionMappings\": [{\"@type\": \"ActionsToDirective\", \"actions\": [\"Alexa.Actions.Close\"], \"directive\": {\"name\": \"SetMode\", \"payload\": {\"mode\": \"Auto.Off\"}}}, {\"@type\": \"ActionsToDirective\", \"actions\": [\"Alexa.Actions.Open\"], \"directive\": {\"name\": \"SetMode\", \"payload\": {\"mode\": \"Auto.Open\"}}}]}', '自动风速模式的打开关闭； \r\n语言：[打开自动风速]  [关闭自动风速]', NULL);
INSERT INTO `d_device_capability` VALUES (9, NULL, '{\"temperatureMaxK\": 6500, \"temperatureMinK\": 2700}', 'GOOGLE', 'colorTemperatureRange', NULL, '色温范围 2700-6500', NULL);
INSERT INTO `d_device_capability` VALUES (10, NULL, 'rgb', 'GOOGLE', 'colorModel', NULL, '颜色设置：RGB模式', NULL);
INSERT INTO `d_device_capability` VALUES (11, NULL, 'hsv', 'GOOGLE', 'colorModel', NULL, '颜色设置：HSV模式', NULL);
INSERT INTO `d_device_capability` VALUES (12, NULL, 'true', 'GOOGLE', 'discreteOnlyOpenClose', NULL, '打开关闭：开合模式', NULL);
INSERT INTO `d_device_capability` VALUES (13, NULL, 'true', 'GOOGLE', 'commandOnlyOpenClose', NULL, '打开关闭：相对模式', NULL);
INSERT INTO `d_device_capability` VALUES (14, NULL, '[\r\n    \"UP\",\r\n    \"DOWN\"\r\n  ]', 'GOOGLE', 'openDirection', NULL, '打开关闭：上下开关模式', NULL);
INSERT INTO `d_device_capability` VALUES (15, NULL, '[\r\n    \"LEFT\",\r\n    \"RIGHT\"\r\n  ]', 'GOOGLE', 'openDirection', NULL, '打开关闭：左右开关模式', NULL);
INSERT INTO `d_device_capability` VALUES (16, NULL, '[\r\n            \"cool\",\r\n						\"heat\"\r\n  ]', 'GOOGLE', 'availableThermostatModes', NULL, '空调模式：加热、制冷模式', NULL);
INSERT INTO `d_device_capability` VALUES (17, NULL, '{\r\n	\"speeds\": [\r\n      {\r\n        \"speed_name\": \"speed_low\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"Low\",\r\n              \"Slow\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      },\r\n			{\r\n        \"speed_name\": \"speed_medium\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"Medium\",\r\n              \"Moderate\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      },\r\n      {\r\n        \"speed_name\": \"speed_high\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"High\",\r\n              \"Fast\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      },\r\n			{\r\n        \"speed_name\": \"speed_off\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"Off\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      },\r\n			{\r\n        \"speed_name\": \"speed_auto\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"Auto\",\r\n							\"Automated\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      }\r\n    ],\r\n    \"ordered\": true\r\n}', 'GOOGLE', 'availableFanSpeeds', NULL, '风速 关、低、中、高、自动', NULL);
INSERT INTO `d_device_capability` VALUES (18, NULL, 'C', 'GOOGLE', 'thermostatTemperatureUnit', NULL, '温度单位：C', NULL);
INSERT INTO `d_device_capability` VALUES (19, NULL, '{\"minThresholdCelsius\":16,\"maxThresholdCelsius\":32}', 'GOOGLE', 'thermostatTemperatureRange', NULL, '温度设置：16C-32C', NULL);
INSERT INTO `d_device_capability` VALUES (20, NULL, '{\r\n	\"speeds\": [\r\n      {\r\n        \"speed_name\": \"speed_low\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"Low\",\r\n              \"Slow\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      },\r\n			{\r\n        \"speed_name\": \"speed_medium\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"Medium\",\r\n              \"Moderate\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      },\r\n      {\r\n        \"speed_name\": \"speed_high\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"High\",\r\n              \"Fast\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      },\r\n			{\r\n        \"speed_name\": \"speed_auto\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"Auto\",\r\n							\"Automated\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      }\r\n    ],\r\n    \"ordered\": true\r\n}', 'GOOGLE', 'availableFanSpeeds', NULL, '风速 低、中、高、自动', NULL);
INSERT INTO `d_device_capability` VALUES (21, NULL, '[\r\n    \"heat\",\r\n    \"cool\",\r\n		\"auto\",\r\n		\"fan-only\"\r\n  ]', 'GOOGLE', 'availableThermostatModes', NULL, '空调模式：加热、制冷、自动、送风模式', NULL);
INSERT INTO `d_device_capability` VALUES (22, NULL, '{\r\n	\"speeds\": [\r\n      {\r\n        \"speed_name\": \"speed_low\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"Low\",\r\n              \"Slow\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      },\r\n			{\r\n        \"speed_name\": \"speed_medium\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"Medium\",\r\n              \"Moderate\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      },\r\n      {\r\n        \"speed_name\": \"speed_high\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"High\",\r\n              \"Fast\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      },\r\n			{\r\n        \"speed_name\": \"speed_off\",\r\n        \"speed_values\": [\r\n          {\r\n            \"speed_synonym\": [\r\n              \"Off\"\r\n            ],\r\n            \"lang\": \"en\"\r\n          }\r\n        ]\r\n      }\r\n    ],\r\n    \"ordered\": true\r\n}', 'GOOGLE', 'availableFanSpeeds', NULL, '风速 关、低、中、高', NULL);
INSERT INTO `d_device_capability` VALUES (23, NULL, '[\r\n    {\r\n      \"name\": \"CarbonDioxideLevel\",\r\n      \"numericCapabilities\": {\r\n        \"rawValueUnit\": \"PARTS_PER_MILLION\"\r\n      }\r\n    }\r\n  ]', 'GOOGLE', 'sensorStatesSupported', NULL, '传感器：CO2', NULL);
INSERT INTO `d_device_capability` VALUES (24, NULL, '[\r\n    {\r\n      \"name\": \"PM2.5\",\r\n      \"numericCapabilities\": {\r\n        \"rawValueUnit\": \"MICROGRAMS_PER_CUBIC_METER\"\r\n      }\r\n    }\r\n  ]', 'GOOGLE', 'sensorStatesSupported', NULL, '传感器：PM2.5', NULL);
INSERT INTO `d_device_capability` VALUES (25, NULL, 'true', 'GOOGLE', 'commandOnlyBrightness', NULL, '亮度调节支持', NULL);
INSERT INTO `d_device_capability` VALUES (26, NULL, '1', 'GOOGLE', 'bufferRangeCelsius', NULL, '温度步长1', NULL);
INSERT INTO `d_device_capability` VALUES (27, NULL, 'false', 'GOOGLE', 'queryOnlyTemperatureSetting', NULL, '温度控制', NULL);
INSERT INTO `d_device_capability` VALUES (28, NULL, '[\"off\",\"heat\"]', 'GOOGLE', 'availableThermostatModes', NULL, '地暖模式：开、关', NULL);

-- ----------------------------
-- Table structure for m_action_mapping
-- ----------------------------
DROP TABLE IF EXISTS `m_action_mapping`;
CREATE TABLE `m_action_mapping`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '产品id',
  `third_sign_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '第三方code',
  `third_action_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `sign_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '功能标识',
  `function_id` tinyint(3) NULL DEFAULT NULL COMMENT '功能id',
  `default_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '默认值',
  `value_of` tinyint(1) NULL DEFAULT NULL COMMENT ' 0：透传 1：需要转换 ',
  `value_mapping` json NULL COMMENT '值映射',
  `third_party_cloud` enum('BAIDU','XIAOMI','HUAWEI','TMALL','ALEXA','GOOGLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '平台',
  `operation` enum('SUM','DECREASE','ADJUST') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作',
  `convert_function` enum('RGB_TO_HEX','HEX_TO_RGB','LINEAR_1000_TO_10000','CELSIUS_UNIT','RGB_TO_INT','RGBW_TO_HSV','INT_TO_RGB','HSV_TO_RGBW') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转换函数',
  `update_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 219 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '操作技能映射表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_action_mapping
-- ----------------------------
INSERT INTO `m_action_mapping` VALUES (1, '开关产品id', NULL, 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (2, '开关产品id', NULL, 'turnOff', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (3, '开关产品id', NULL, 'getTurnOnState', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (4, '灯光产品id2', NULL, 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:24:46', NULL);
INSERT INTO `m_action_mapping` VALUES (5, '灯光产品id2', NULL, 'turnOff', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:24:46', NULL);
INSERT INTO `m_action_mapping` VALUES (6, '灯光产品id2', NULL, 'getTurnOnState', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:24:46', NULL);
INSERT INTO `m_action_mapping` VALUES (7, '灯光产品id2', 'brightness#value', 'setBrightnessPercentage', 'dimming_p', 2, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:24:46', NULL);
INSERT INTO `m_action_mapping` VALUES (8, '灯光产品id2', 'deltaPercentage#value', 'incrementBrightnessPercentage', 'dimming_p', 2, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:24:46', NULL);
INSERT INTO `m_action_mapping` VALUES (9, '灯光产品id2', 'deltaPercentage#value', 'decrementBrightnessPercentage', 'dimming_p', 2, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:24:46', NULL);
INSERT INTO `m_action_mapping` VALUES (10, '空调产品id', NULL, 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (11, '空调产品id', NULL, 'turnOff', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (12, '空调产品id', NULL, 'getTurnOnState', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (13, '空调产品id', 'fanSpeed#value', 'SetFanSpeed', 'wind_speed', 5, '', 0, '{\"LOW\": \"1\", \"MAX\": \"3\", \"MIN\": \"1\", \"AUTO\": \"4\", \"HIGH\": \"3\", \"MIDDLE\": \"2\", \"RANDOM\": \"4\", \"MIDDLE_LOW\": \"2\", \"MIDDLE_HIGH\": \"2\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (14, '空调产品id', 'targetTemperature#value', 'setTemperature', 'set_temperature', 7, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (15, '空调产品id', 'deltaValue#value', 'incrementTemperature', 'set_temperature', 7, NULL, 0, NULL, 'BAIDU', 'SUM', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (16, '空调产品id', 'deltaValue#value', 'decrementTemperature', 'set_temperature', 7, NULL, 0, NULL, 'BAIDU', 'DECREASE', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (17, '空调产品id', 'getTemperatureReading', 'getTemperatureReading', 'temperature', 6, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (18, '空调产品id', 'mode#value', 'setMode', 's_operation_mode', 9, '', 1, '{\"FAN\": \"4\", \"NAI\": \"7\", \"AUTO\": \"1\", \"COOL\": \"3\", \"HEAT\": \"2\", \"CLEAN\": \"7\", \"SLEEP\": \"6\", \"DEHUMIDIFICATION\": \"5\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (19, '灯光产品id', NULL, 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (20, '灯光产品id', NULL, 'turnOff', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (21, '灯光产品id', NULL, 'getTurnOnState', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (22, '灯光产品id3', 'brightness#value', 'setBrightnessPercentage', 'dimming_p', 3, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (23, '灯光产品id3', 'deltaPercentage#value', 'incrementBrightnessPercentage', 'dimming_p', 3, NULL, 0, NULL, 'BAIDU', 'SUM', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (24, '灯光产品id3', 'deltaPercentage#value', 'decrementBrightnessPercentage', 'dimming_p', 3, NULL, 0, NULL, 'BAIDU', 'DECREASE', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (25, '灯光产品id3', NULL, 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (26, '灯光产品id3', NULL, 'turnOff', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (27, '色温灯产品id', NULL, 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (28, '色温灯产品id', NULL, 'turnOff', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (29, '色温灯产品id', 'brightness#value', 'setBrightnessPercentage', 'dimming_p', 2, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (30, '色温灯产品id', 'deltaPercentage#value', 'incrementBrightnessPercentage', 'dimming_p', 2, NULL, 0, NULL, 'BAIDU', 'SUM', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (31, '色温灯产品id', 'deltaPercentage#value', 'decrementBrightnessPercentage', 'dimming_p', 2, NULL, 0, NULL, 'BAIDU', 'DECREASE', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (32, '色温灯产品id', 'deltaPercentage#value', 'incrementColorTemperature', 'color_temperature', 3, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (33, 'RGB产品id', NULL, 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:25:25', NULL);
INSERT INTO `m_action_mapping` VALUES (34, 'RGB产品id', NULL, 'turnOff', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:25:25', NULL);
INSERT INTO `m_action_mapping` VALUES (35, 'RGB产品id', 'color', 'setColor', 'rgb', 2, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:25:25', NULL);
INSERT INTO `m_action_mapping` VALUES (36, 'RGBW产品id', 'color', 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:26:28', NULL);
INSERT INTO `m_action_mapping` VALUES (37, 'RGBW产品id', 'color', 'turnOff', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:26:28', NULL);
INSERT INTO `m_action_mapping` VALUES (38, 'RGBW产品id', 'color', 'setColor', 'rgbw', 2, NULL, 1, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:28', NULL);
INSERT INTO `m_action_mapping` VALUES (39, 'RGBCW产品id', NULL, 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (40, 'RGBCW产品id', NULL, 'turnOff', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (41, 'RGBCW产品id', 'brightness#value', 'setBrightnessPercentage', 'dimming_p', 3, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (42, 'RGBCW产品id', 'deltaPercentage#value', 'incrementBrightnessPercentage', 'dimming_p', 3, NULL, 0, NULL, 'BAIDU', 'SUM', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (43, 'RGBCW产品id', 'deltaPercentage#value', 'decrementBrightnessPercentage', 'dimming_p', 3, NULL, 0, NULL, 'BAIDU', 'DECREASE', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (44, 'RGBCW产品id', 'color', 'setColor', 'rgb', 2, NULL, 1, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (45, 'RGBCW产品id', 'colorTemperatureInKelvin', 'setColorTemperature', 'color_temperature', 4, NULL, 1, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (46, '开合帘产品id', NULL, 'pause', 'stop', 2, NULL, 1, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (47, '开合帘产品id', NULL, 'turnOn', 'curtain_switch', 1, NULL, 1, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (48, '开合帘产品id', NULL, 'turnOff', 'curtain_switch', 1, NULL, NULL, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (49, '卷帘产品id', NULL, 'pause', 'stop', 2, NULL, 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (50, '卷帘产品id', NULL, 'turnOff', 'curtain_switch', 1, NULL, NULL, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (51, '卷帘产品id', NULL, 'turnOn', 'curtain_switch', 1, NULL, 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (52, '百叶帘产品id', NULL, 'turnOff', 'curtain_switch', 1, NULL, NULL, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (53, '百叶帘产品id', NULL, 'turnOn', 'curtain_switch', 1, NULL, 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (54, '百叶帘产品id', NULL, 'pause', 'stop', 2, NULL, 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (55, '单开合帘产品id', NULL, 'turnOff', 'curtain_switch', 1, NULL, NULL, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (56, '单开合帘产品id', NULL, 'turnOn', 'curtain_switch', 1, NULL, 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (57, '单开合帘产品id', NULL, 'pause', 'stop', 2, NULL, 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (58, '卷帘产品id2', NULL, 'turnOff', 'curtain_switch', 1, NULL, NULL, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (59, '卷帘产品id2', NULL, 'turnOn', 'curtain_switch', 1, NULL, 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (60, '卷帘产品id2', NULL, 'pause', 'stop', 2, NULL, 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (61, 'HVAC产品id', NULL, 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:26:09', NULL);
INSERT INTO `m_action_mapping` VALUES (62, 'HVAC产品id', NULL, 'turnOff', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:26:09', NULL);
INSERT INTO `m_action_mapping` VALUES (63, 'HVAC产品id', 'mode#value', 'setMode', 'control_mode', 2, NULL, 1, '{\"COOL\": \"0\", \"HEAT\": \"1\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:26:09', NULL);
INSERT INTO `m_action_mapping` VALUES (64, 'HVAC产品id', 'fanSpeed#value', 'setFanSpeed', 'wind_speed', 5, NULL, 1, '{\"low\": \"1\", \"auto\": \"4\", \"high\": \"3\", \"middle\": \"2\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:26:09', NULL);
INSERT INTO `m_action_mapping` VALUES (65, 'HVAC产品id', 'deltaValue#value', 'incrementTemperature', 'set_temperature', 7, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:09', NULL);
INSERT INTO `m_action_mapping` VALUES (66, 'HVAC产品id', 'deltaValue#value', 'decrementTemperature', 'set_temperature', 7, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:09', NULL);
INSERT INTO `m_action_mapping` VALUES (67, 'HVAC产品id', 'targetTemperature#value', 'setTemperature', 'set_temperature', 7, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:09', NULL);
INSERT INTO `m_action_mapping` VALUES (68, '地暖产品id', NULL, 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:26:40', NULL);
INSERT INTO `m_action_mapping` VALUES (69, '地暖产品id', NULL, 'turnOff', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, '2024-04-18 09:26:40', NULL);
INSERT INTO `m_action_mapping` VALUES (70, '地暖产品id', 'deltaValue#value', 'incrementTemperature', 'set_temperature', 2, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:40', NULL);
INSERT INTO `m_action_mapping` VALUES (71, '地暖产品id', 'deltaValue#value', 'decrementTemperature', 'set_temperature', 2, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:40', NULL);
INSERT INTO `m_action_mapping` VALUES (72, '地暖产品id', 'targetTemperature#value', 'setTemperature', 'set_temperature', 2, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:40', NULL);
INSERT INTO `m_action_mapping` VALUES (73, '空气净化器产品id', NULL, 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (74, '空气净化器产品id', NULL, 'turnOff', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (75, '空气净化器产品id', 'fanSpeed#value', 'setFanSpeed', 'wind_speed', 5, NULL, 1, '{\"low\": \"1\", \"high\": \"3\", \"middle\": \"2\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (76, 'DVD产品id', NULL, 'turnOn', 'switch', 1, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (77, 'DVD产品id', NULL, 'turnOff', 'switch', 1, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (78, 'DVD产品id', NULL, 'pause', 'play_operation', 2, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (79, 'DVD产品id', NULL, 'continue', 'play_operation', 2, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (80, 'DVD产品id', NULL, 'incrementTVChannel', 'selector', 3, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (81, 'DVD产品id', NULL, 'decrementTVChannel', 'selector', 3, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (82, 'DVD产品id', 'deltaValue#value', 'incrementVolume', 'volume_adjustment', 5, '', 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (83, 'DVD产品id', 'decrementVolume#value', 'decrementVolume', 'volume_adjustment', 5, '', 1, '{\"\": \"0\"}', 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (84, 'DVD产品id', 'deltaValue#value', 'setVolume', 'volume_roll', 4, '', 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (85, '灯光产品id3', NULL, 'getTurnOnState', 'switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (86, '色温灯产品id', NULL, 'getTurnOnState', 'switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (87, 'RGB产品id', NULL, 'getTurnOnState', 'switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:25:25', NULL);
INSERT INTO `m_action_mapping` VALUES (88, 'RGBW产品id', NULL, 'getTurnOnState', 'switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:28', NULL);
INSERT INTO `m_action_mapping` VALUES (89, 'RGBCW产品id', NULL, 'getTurnOnState', 'switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (90, '开合帘产品id', NULL, 'getTurnOnState', 'curtain_switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (91, '卷帘产品id', NULL, 'getTurnOnState', 'curtain_switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (92, '百叶帘产品id', NULL, 'getTurnOnState', 'curtain_switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (93, '单开合帘产品id', NULL, 'getTurnOnState', 'curtain_switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (94, '卷帘产品id2', NULL, 'getTurnOnState', 'curtain_switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (95, 'HVAC产品id', NULL, 'getFanSpeed', 'wind_speed', 5, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:09', NULL);
INSERT INTO `m_action_mapping` VALUES (96, 'HVAC产品id', NULL, 'getTurnOnState', 'curtain_switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:09', NULL);
INSERT INTO `m_action_mapping` VALUES (97, 'HVAC产品id', NULL, 'getTemperatureReading', 'temperature', 6, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:09', NULL);
INSERT INTO `m_action_mapping` VALUES (98, '地暖产品id', NULL, 'getTurnOnState', 'curtain_switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:40', NULL);
INSERT INTO `m_action_mapping` VALUES (99, '地暖产品id', NULL, 'getTemperatureReading', 'temperature', 3, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:40', NULL);
INSERT INTO `m_action_mapping` VALUES (100, '空气净化器产品id', NULL, 'getTurnOnState', 'curtain_switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (101, '空气净化器产品id', NULL, 'getFanSpeed', 'wind_speed', 5, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (102, '空气净化器产品id', NULL, 'getAirPM25', 'PM2.5', 8, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (103, '空气净化器产品id', NULL, 'getCO2Quantity', 'CO2', 7, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (104, 'DVD产品id', NULL, 'getTurnOnState', 'curtain_switch', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (105, 'AQI产品id', NULL, 'getAirQualityIndex', 'AQI', 2, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (106, 'PM10传感器产品id', NULL, 'getAirPM10', 'PM10', 2, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (107, 'pm2.5传感器产品id', NULL, 'getAirPM25', 'PM2.5', 2, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (108, '温度传感器产品id', NULL, 'getTemperatureReading', 'temperature', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (109, '温度传感器产品id', NULL, 'getTemperatureReading', 'temperature', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (110, '湿度传感器产品id', NULL, 'getHumidity', 'humidity', 1, NULL, 0, NULL, 'BAIDU', NULL, NULL, '2024-04-18 09:26:19', NULL);
INSERT INTO `m_action_mapping` VALUES (111, 'CO2传感器产品id', NULL, 'getCO2Quantity', 'CO2', 2, NULL, 0, NULL, 'BAIDU', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (112, '开关产品id', NULL, 'TurnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (113, '开关产品id', NULL, 'TurnOff', 'switch', 1, NULL, 1, '{\"\": \"0\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (114, '灯光产品id', NULL, 'TurnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (115, '灯光产品id', NULL, 'TurnOff', 'switch', 1, NULL, 1, '{\"\": \"0\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (116, '灯光产品id2', NULL, 'TurnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (117, '灯光产品id2', NULL, 'TurnOff', 'switch', 1, NULL, 1, '{\"\": \"0\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (118, '灯光产品id2', 'brightness', 'SetBrightness', 'dimming_p', 2, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (119, '灯光产品id2', 'brightnessDelta', 'AdjustBrightness', 'dimming_p', 2, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (120, '灯光产品id3', NULL, 'TurnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (121, '灯光产品id3', NULL, 'TurnOff', 'switch', 1, NULL, 1, '{\"\": \"0\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (122, '灯光产品id3', 'brightness', 'SetBrightness', 'dimming_p', 3, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (123, '灯光产品id3', 'brightnessDelta', 'AdjustBrightness', 'dimming_p', 3, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (124, '色温灯产品id', NULL, 'TurnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (125, '色温灯产品id', NULL, 'TurnOff', 'switch', 1, NULL, 1, '{\"\": \"0\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (126, '色温灯产品id', 'brightness', 'SetBrightness', 'dimming_p', 2, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (127, '色温灯产品id', 'brightnessDelta', 'AdjustBrightness', 'dimming_p', 2, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (128, '色温灯产品id', 'colorTemperatureInKelvin', 'SetColorTemperature', 'color_temperature', 3, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (129, '色温灯产品id', NULL, 'IncreaseColorTemperature', 'color_temperature', 3, '100', 0, NULL, 'ALEXA', 'SUM', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (130, '色温灯产品id', NULL, 'DecreaseColorTemperature', 'color_temperature', 3, '100', 0, NULL, 'ALEXA', 'DECREASE', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (131, 'RGB产品id', NULL, 'TurnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (132, 'RGB产品id', NULL, 'TurnOff', 'switch', 1, NULL, 1, '{\"\": \"0\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (133, 'RGB产品id', 'color', 'SetColor', 'rgb', 2, NULL, 1, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (134, 'RGBW产品id', NULL, 'TurnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (135, 'RGBW产品id', NULL, 'TurnOff', 'switch', 1, NULL, 1, '{\"\": \"0\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (136, 'RGBCW产品id', NULL, 'TurnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (137, 'RGBCW产品id', NULL, 'TurnOff', 'switch', 1, NULL, 1, '{\"\": \"0\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (138, 'RGBCW产品id', 'color', 'SetColor', 'rgb', 2, NULL, 1, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (139, 'RGBCW产品id', 'brightness', 'SetBrightness', 'dimming_p', 3, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (140, 'RGBCW产品id', 'brightnessDelta', 'AdjustBrightness', 'dimming_p', 3, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (141, '开合帘产品id', 'mode#value', 'SetMode', 'curtain_switch', 1, NULL, 1, NULL, 'ALEXA', NULL, NULL, '2024-04-16 17:12:53', NULL);
INSERT INTO `m_action_mapping` VALUES (142, '开合帘产品id', 'percentage', 'SetPercentage', 'curtain_location', 3, NULL, 0, NULL, 'ALEXA', NULL, NULL, '2024-04-16 17:12:53', NULL);
INSERT INTO `m_action_mapping` VALUES (143, '开合帘产品id', 'percentageDelta', 'AdjustPercentage', 'curtain_location', 3, NULL, 0, NULL, 'ALEXA', NULL, NULL, '2024-04-16 17:12:53', NULL);
INSERT INTO `m_action_mapping` VALUES (144, '卷帘产品id', 'mode#value', 'SetMode', 'curtain_switch', 1, NULL, 1, '{\"Position.Up\": \"0\", \"Position.Down\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (145, '卷帘产品id', 'percentage', 'SetPercentage', 'curtain_location', 3, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (146, '卷帘产品id', 'percentageDelta', 'AdjustPercentage', 'curtain_location', 3, NULL, 0, NULL, 'ALEXA', 'ADJUST', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (147, '百叶帘产品id', 'mode#value', 'SetMode', 'curtain_switch', 1, NULL, 1, '{\"Position.Up\": \"0\", \"Position.Down\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (148, '百叶帘产品id', 'percentage', 'SetPercentage', 'curtain_location', 3, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (149, '百叶帘产品id', 'percentageDelta', 'AdjustPercentage', 'curtain_location', 3, NULL, 0, NULL, 'ALEXA', 'ADJUST', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (150, '单开合帘产品id', 'mode#value', 'SetMode', 'curtain_switch', 1, NULL, 1, '{\"Position.Up\": \"0\", \"Position.Down\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (151, '卷帘产品id2', 'mode#value', 'SetMode', 'curtain_switch', 1, NULL, 1, '{\"Position.Up\": \"0\", \"Position.Down\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (152, 'HVAC产品id', NULL, 'TurnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (153, 'HVAC产品id', NULL, 'TurnOff', 'switch', 1, NULL, 1, '{\"\": \"0\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (154, 'HVAC产品id', 'thermostatMode#value', 'SetThermostatMode', 'control_mode', 2, NULL, 1, '{\"COOL\": \"0\", \"HEAT\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (155, 'HVAC产品id', 'rangeValue', 'SetRangeValue', 'wind_speed', 5, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (156, 'HVAC产品id', 'rangeValue', 'AdjustRangeValue', 'wind_speed', 5, '1', 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (157, 'HVAC产品id', 'targetSetpoint#value', 'SetTargetTemperature', 'set_temperature', 7, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (158, 'HVAC产品id', 'targetSetpointDelta#value', 'AdjustTargetTemperature', 'set_temperature', 7, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (159, '空调产品id', NULL, 'TurnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (160, '空调产品id', NULL, 'TurnOff', 'switch', 1, NULL, 1, '{\"\": \"0\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (161, '空调产品id', 'rangeValue', 'SetRangeValue', 'wind_speed', 5, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (162, '空调产品id', 'rangeValue', 'AdjustRangeValue', 'wind_speed', 5, '1', 0, NULL, 'ALEXA', 'ADJUST', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (163, '空调产品id', 'targetSetpoint#value', 'SetTargetTemperature', 'set_temperature', 7, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (164, '空调产品id', 'targetSetpointDelta#value', 'AdjustTargetTemperature', 'set_temperature', 7, NULL, 0, NULL, 'ALEXA', 'ADJUST', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (165, '空调产品id', 'thermostatMode#value', 'SetThermostatMode', 's_operation_mode', 9, NULL, 1, '{\"AUTO\": \"1\", \"COOL\": \"3\", \"HEAT\": \"2\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (166, '地暖产品id', NULL, 'TurnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (167, '地暖产品id', NULL, 'TurnOff', 'switch', 1, NULL, 1, '{\"\": \"0\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (168, '地暖产品id', 'targetSetpoint#value', 'SetTargetTemperature', 'set_temperature', 2, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (169, '地暖产品id', 'targetSetpointDelta#value', 'AdjustTargetTemperature', 'set_temperature', 2, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (170, '地暖产品id', 'thermostatMode#value', 'SetThermostatMode', 'heat_switch', 4, NULL, 1, '{\"OFF\": \"0\", \"HEAT\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (171, '空气净化器产品id', NULL, 'TurnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (172, '空气净化器产品id', NULL, 'TurnOff', 'switch', 1, NULL, 1, '{\"\": \"0\"}', 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (173, '空气净化器产品id', 'rangeValue', 'SetRangeValue', 'wind_speed', 5, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (174, '空气净化器产品id', 'rangeValue', 'AdjustRangeValue', 'wind_speed', 5, '1', 0, NULL, 'ALEXA', 'ADJUST', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (176, '开关产品id', 'on', 'action.devices.commands.OnOff', 'switch', 1, NULL, 1, '{\"true\": \"1\", \"false\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (177, '灯光产品id', 'on', 'action.devices.commands.OnOff', 'switch', 1, NULL, 1, '{\"true\": \"1\", \"false\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (178, '灯光产品id2', 'on', 'action.devices.commands.OnOff', 'switch', 1, NULL, 1, '{\"true\": \"1\", \"false\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (179, '灯光产品id2', 'brightness', 'action.devices.commands.BrightnessAbsolute', 'dimming_p', 2, NULL, 0, NULL, 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (180, '灯光产品id3', 'on', 'action.devices.commands.OnOff', 'switch', 1, NULL, 1, '{\"true\": \"1\", \"false\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (181, '灯光产品id3', 'brightness', 'action.devices.commands.BrightnessAbsolute', 'dimming_p', 3, NULL, 0, NULL, 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (182, '色温灯产品id', 'on', 'action.devices.commands.OnOff', 'switch', 1, NULL, 1, '{\"true\": \"1\", \"false\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (183, '色温灯产品id', 'brightness', 'action.devices.commands.BrightnessAbsolute', 'dimming_p', 2, NULL, 0, NULL, 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (184, '色温灯产品id', 'color#temperature', 'action.devices.commands.ColorAbsolute', 'color_temperature', 3, NULL, 0, NULL, 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (185, 'RGB产品id', 'on', 'action.devices.commands.OnOff', 'switch', 1, NULL, 1, '{\"true\": \"1\", \"false\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (186, 'RGB产品id', 'color#spectrumRGB', 'action.devices.commands.ColorAbsolute', 'rgb', 2, NULL, 0, NULL, 'GOOGLE', NULL, 'INT_TO_RGB', NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (187, 'RGBW产品id', 'on', 'action.devices.commands.OnOff', 'switch', 1, NULL, 1, '{\"true\": \"1\", \"false\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (188, 'RGBW产品id', 'spectrumHSV#spectrumRGB', 'action.devices.commands.ColorAbsolute', 'rgbw', 2, NULL, 0, NULL, 'GOOGLE', NULL, 'HSV_TO_RGBW', NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (189, 'RGBCW产品id', 'on', 'action.devices.commands.OnOff', 'switch', 1, NULL, 1, '{\"true\": \"1\", \"false\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (190, 'RGBCW产品id', 'color#spectrumRGB', 'action.devices.commands.ColorAbsolute', 'rgb', 2, NULL, 0, NULL, 'GOOGLE', NULL, 'INT_TO_RGB', NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (191, 'RGBCW产品id', 'brightness', 'action.devices.commands.BrightnessAbsolute', 'dimming_p', 3, NULL, 0, NULL, 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (193, '开合帘产品id', 'openPercent', 'action.devices.commands.OpenClose', 'curtain_location', 3, NULL, 0, '{\"\": \"\"}', 'GOOGLE', NULL, NULL, '2024-04-16 17:22:03', NULL);
INSERT INTO `m_action_mapping` VALUES (194, '开合帘产品id', 'openRelativePercent', 'action.devices.commands.OpenCloseRelative', 'curtain_location', 3, NULL, 0, NULL, 'GOOGLE', NULL, NULL, '2024-04-16 17:22:03', NULL);
INSERT INTO `m_action_mapping` VALUES (195, '卷帘产品id', 'openPercent', 'action.devices.commands.OpenClose', 'curtain_location', 3, NULL, 0, NULL, 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (196, '卷帘产品id', 'openRelativePercent', 'action.devices.commands.OpenCloseRelative', 'curtain_location', 3, NULL, 0, NULL, 'GOOGLE', 'ADJUST', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (197, '百叶帘产品id', 'openPercent', 'action.devices.commands.OpenClose', 'curtain_location', 3, NULL, 0, NULL, 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (198, '百叶帘产品id', 'openRelativePercent', 'action.devices.commands.OpenCloseRelative', 'curtain_location', 3, NULL, 0, NULL, 'GOOGLE', 'ADJUST', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (199, '单开合帘产品id', 'openPercent', 'action.devices.commands.OpenClose', 'curtain_switch', 1, NULL, 1, '{\"0\": \"1\", \"100\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (200, '卷帘产品id2', 'openPercent', 'action.devices.commands.OpenClose', 'curtain_switch', 1, NULL, 1, '{\"0\": \"1\", \"100\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (201, 'HVAC产品id', 'on', 'action.devices.commands.OnOff', 'switch', 1, NULL, 1, '{\"true\": \"1\", \"false\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (202, 'HVAC产品id', 'thermostatMode', 'action.devices.commands.ThermostatSetMode', 'control_mode', 2, NULL, 1, '{\"cool\": \"0\", \"heat\": \"1\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (203, 'HVAC产品id', 'fanSpeed', 'action.devices.commands.SetFanSpeed', 'wind_speed', 5, NULL, 1, '{\"speed_low\": \"1\", \"speed_off\": \"0\", \"speed_auto\": \"4\", \"speed_high\": \"3\", \"speed_medium\": \"2\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (204, 'HVAC产品id', 'thermostatTemperatureSetpoint', 'action.devices.commands.ThermostatTemperatureSetpoint', 'set_temperature', 7, NULL, 0, NULL, 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (205, 'HVAC产品id', 'thermostatTemperatureRelativeDegree', 'action.devices.commands.TemperatureRelative', 'set_temperature', 7, NULL, 0, NULL, 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (206, '空调产品id', 'on', 'action.devices.commands.OnOff', 'switch', 1, NULL, 1, '{\"true\": \"1\", \"false\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (207, '空调产品id', 'fanSpeed', 'action.devices.commands.SetFanSpeed', 'wind_speed', 5, NULL, 1, '{\"speed_low\": \"1\", \"speed_auto\": \"4\", \"speed_high\": \"3\", \"speed_medium\": \"2\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (208, '空调产品id', 'thermostatTemperatureSetpoint', 'action.devices.commands.ThermostatTemperatureSetpoint', 'set_temperature', 7, NULL, 0, NULL, 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (209, '空调产品id', 'thermostatTemperatureRelativeDegree', 'action.devices.commands.TemperatureRelative', 'set_temperature', 7, NULL, 0, NULL, 'GOOGLE', 'ADJUST', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (210, '空调产品id', 'thermostatMode', 'action.devices.commands.ThermostatSetMode', 's_operation_mode', 9, NULL, 1, '{\"auto\": \"1\", \"cool\": \"3\", \"heat\": \"2\", \"fan-only\": \"4\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (211, '地暖产品id', 'thermostatMode', 'action.devices.commands.ThermostatSetMode', 'switch', 1, NULL, 1, '{\"off\": \"0\", \"heat\": \"1\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (212, '地暖产品id', 'thermostatTemperatureSetpoint', 'action.devices.commands.ThermostatTemperatureSetpoint', 'set_temperature', 2, NULL, 0, NULL, 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (213, '地暖产品id', 'thermostatTemperatureRelativeDegree', 'action.devices.commands.TemperatureRelative', 'set_temperature', 2, NULL, 0, NULL, 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (214, 'RGBCW产品id', 'colorTemperatureInKelvin', 'SetColorTemperature', 'color_temperature', 4, NULL, 0, NULL, 'ALEXA', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (215, 'RGBCW产品id', NULL, 'IncreaseColorTemperature', 'color_temperature', 4, '100', 0, NULL, 'ALEXA', 'SUM', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (216, '色温灯产品id', NULL, 'DecreaseColorTemperature', 'color_temperature', 3, '100', 0, NULL, 'ALEXA', 'DECREASE', NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (217, '空气净化器产品id', 'on', 'action.devices.commands.OnOff', 'switch', 1, NULL, 1, '{\"true\": \"1\", \"false\": \"0\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (218, '空气净化器产品id', 'fanSpeed', 'action.devices.commands.SetFanSpeed', 'wind_speed', 5, NULL, 1, '{\"speed_low\": \"1\", \"speed_off\": \"0\", \"speed_high\": \"3\", \"speed_medium\": \"2\"}', 'GOOGLE', NULL, NULL, NULL, NULL);
INSERT INTO `m_action_mapping` VALUES (219, 'scene', NULL, 'turnOn', 'switch', 1, NULL, 1, '{\"\": \"1\"}', 'BAIDU', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for m_custom_mapping
-- ----------------------------
DROP TABLE IF EXISTS `m_custom_mapping`;
CREATE TABLE `m_custom_mapping`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mapping_template` json NULL COMMENT '映射模板  @标识表示开始执行代码  signcode&value 指定属性值    =指定值映射',
  `third_sign_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '第三方功能标识',
  `product_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '产品id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `third_party_cloud` enum('BAIDU','XIAOMI','HUAWEI','TMALL','ALEXA','GOOGLE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语音平台  ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '自定义映射模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_custom_mapping
-- ----------------------------
INSERT INTO `m_custom_mapping` VALUES (1, '{\"AQI\": {\"value\": \"AQI&value\"}, \"level\": {\"value\": \"AQI&value=value @ value > 200 ? \\\"重度\\\" : value > 150 ? \\\"中度\\\" : \\\"轻度\\\"\"}}', 'GetAirPM25Response', 'AQI产品id', 'AQI定制体', 'BAIDU');
INSERT INTO `m_custom_mapping` VALUES (2, '{\"PM25\": {\"scale\": \"μg/m3\", \"value\": \"PM2.5&value\"}}', 'GetAirQualityIndexResponse', 'pm2.5传感器产品id', 'PM2.5定制体', 'BAIDU');
INSERT INTO `m_custom_mapping` VALUES (3, '{\"PM10\": {\"scale\": \"μg/m3\", \"value\": \"PM10&value\"}}', 'GetAirPM10Request', '	\r\n3a135c37afec48bbaf171a1b73f17665	\r\n', 'PM10定制体', 'BAIDU');
INSERT INTO `m_custom_mapping` VALUES (4, '{\"ppm\": {\"value\": \"CO2&value\"}}', 'GetCO2QuantityRequest', 'CO2传感器产品id', 'CO2定制体', 'BAIDU');
INSERT INTO `m_custom_mapping` VALUES (5, '{\"temperatureReading\": {\"scale\": \"CELSIUS\", \"value\": \"temperature&value\"}, \"applianceResponseTimestamp\": \"@date\"}', 'GetTemperatureReadingRequest', '空调产品id', '当前温度定制体', 'BAIDU');
INSERT INTO `m_custom_mapping` VALUES (6, '{\"temperatureReading\": {\"scale\": \"CELSIUS\", \"value\": \"temperature&value\"}, \"applianceResponseTimestamp\": \"@date\"}', 'GetTemperatureReadingRequest', 'HVAC产品id', '当前温度定制体', 'BAIDU');
INSERT INTO `m_custom_mapping` VALUES (7, '{\"temperatureReading\": {\"scale\": \"CELSIUS\", \"value\": \"temperature&value\"}, \"applianceResponseTimestamp\": \"@date\"}', 'GetTemperatureReadingRequest', '地暖产品id', '当前温度定制体', 'BAIDU');
INSERT INTO `m_custom_mapping` VALUES (8, '{\"PM25\": {\"scale\": \"μg/m3\", \"value\": \"PM2.5&value\"}}', 'GetAirQualityIndexResponse', '空气净化器产品id', 'PM2.5定制体', 'BAIDU');
INSERT INTO `m_custom_mapping` VALUES (9, '{\"ppm\": {\"value\": \"CO2&value\"}}', 'GetCO2QuantityRequest', '空气净化器产品id', 'CO2定制体', 'BAIDU');

-- ----------------------------
-- Table structure for m_device_mapping
-- ----------------------------
DROP TABLE IF EXISTS `m_device_mapping`;
CREATE TABLE `m_device_mapping`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `third_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用于设备上报数据',
  `device_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `third_party_cloud` enum('BAIDU','XIAOMI','HUAWEI','TMALL','ALEXA','GOOGLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `client_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `cuid_idx`(`third_party_cloud`, `user_id`, `device_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 256 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_device_mapping
-- ----------------------------
INSERT INTO `m_device_mapping` VALUES (252, NULL, 1, 123, 'GOOGLE', 'testgoogle', '2024-04-19 05:39:32', '2024-04-19 05:39:32');
INSERT INTO `m_device_mapping` VALUES (253, NULL, 2, 123, 'GOOGLE', 'testgoogle', '2024-04-19 05:45:23', '2024-04-19 05:45:23');
INSERT INTO `m_device_mapping` VALUES (254, NULL, 1, 123, 'ALEXA', 'testalexa', '2024-04-19 05:59:50', '2024-04-19 05:59:50');
INSERT INTO `m_device_mapping` VALUES (255, NULL, 2, 123, 'ALEXA', 'testalexa', '2024-04-19 05:59:50', '2024-04-19 05:59:50');
INSERT INTO `m_device_mapping` VALUES (256, NULL, 3, 123, 'ALEXA', 'testalexa', '2024-04-19 06:05:15', '2024-04-19 06:05:15');

-- ----------------------------
-- Table structure for m_function_mapping
-- ----------------------------
DROP TABLE IF EXISTS `m_function_mapping`;
CREATE TABLE `m_function_mapping`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品id',
  `sign_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能标识',
  `function_id` tinyint(3) NULL DEFAULT NULL COMMENT '功能id',
  `third_sign_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语音技能枚举 详细请见代码',
  `third_action_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `third_party_cloud` enum('BAIDU','XIAOMI','HUAWEI','TMALL','ALEXA','GOOGLE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语音平台 ',
  `value_of` tinyint(1) NULL DEFAULT NULL COMMENT '0：透传 1：需要转换',
  `value_mapping` json NULL COMMENT '值映射',
  `convert_function` enum('RGB_TO_HEX','HEX_TO_RGB','LINEAR_1000_TO_10000','CELSIUS_UNIT','RGB_TO_INT','RGBW_TO_HSV','STRING_TO_BOOLEAN','STRING_TO_INT','STRING_TO_DOUBLE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转换函数',
  `capability_config_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '能力配置id，可配多个 逗号分隔',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `legal_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取值范围',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_idx`(`product_id`, `third_sign_code`, `third_action_code`, `third_party_cloud`) USING BTREE COMMENT '动作与产品仅一对一'
) ENGINE = InnoDB AUTO_INCREMENT = 182 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品功能属性映射' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_function_mapping
-- ----------------------------
INSERT INTO `m_function_mapping` VALUES (1, '开关产品id', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"off\", \"1\": \"on\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (2, '灯光产品id2', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"off\", \"1\": \"on\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (3, '灯光产品id2', 'dimming_p', 2, 'brightness', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (4, '空调产品id', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"off\", \"1\": \"on\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (5, '空调产品id', 'wind_speed', 5, 'gear', NULL, 'BAIDU', 1, '{\"1\": \"LOW\", \"2\": \"MIDDLE\", \"3\": \"HIGH\", \"4\": \"AUTO\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (6, '空调产品id', 'temperature', 6, 'temperature', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (7, '空调产品id', 'set_temperature', 7, 'targetTemperature', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (8, '空调产品id', 's_operation_mode', 9, 'mode', NULL, 'BAIDU', 1, '{\"1\": \"AUTO\", \"2\": \"HEAT\", \"3\": \"COOL\", \"4\": \"FAN\", \"5\": \"DEHUMIDIFICATION\", \"6\": \"SLEEP\", \"7\": \"CLEAN\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (9, '开关产品id', 'switch', 1, '2', '1', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:11:25', NULL);
INSERT INTO `m_function_mapping` VALUES (10, '灯光产品id2', 'switch', 1, '2', '1', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:12:16', NULL);
INSERT INTO `m_function_mapping` VALUES (11, '灯光产品id2', 'dimming_p', 2, '2', '3', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:12:16', NULL);
INSERT INTO `m_function_mapping` VALUES (12, '空调产品id', 'switch', 1, '2', '1', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:11:03', NULL);
INSERT INTO `m_function_mapping` VALUES (13, '空调产品id', 'wind_speed', 5, '3', '2', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:03', NULL);
INSERT INTO `m_function_mapping` VALUES (14, '空调产品id', 'temperature', 6, '4', '7', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:03', NULL);
INSERT INTO `m_function_mapping` VALUES (15, '空调产品id', 'set_temperature', 7, '2', '4', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:03', NULL);
INSERT INTO `m_function_mapping` VALUES (16, '空调产品id', 's_operation_mode', 9, '2', '2', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:03', NULL);
INSERT INTO `m_function_mapping` VALUES (17, '灯光产品id', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"off\", \"1\": \"on\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (18, '灯光产品id3', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (19, '灯光产品id3', 'dimming_p', 2, 'brightness', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (20, '色温灯产品id', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"off\", \"1\": \"on\"}', NULL, NULL, NULL, NULL, '2024-03-28 16:27:25', NULL);
INSERT INTO `m_function_mapping` VALUES (21, '色温灯产品id', 'dimming_p', 2, 'brightness', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, '2024-03-28 16:27:25', NULL);
INSERT INTO `m_function_mapping` VALUES (22, 'RGB产品id', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"off\", \"1\": \"on\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (23, 'RGBW产品id', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, NULL, NULL, NULL, NULL, NULL, '2024-04-16 17:23:25', NULL);
INSERT INTO `m_function_mapping` VALUES (24, 'RGBCW产品id', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"off\", \"1\": \"on\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (25, 'RGBCW产品id', 'dimming_p', 3, 'brightness', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (26, '开合帘产品id', 'curtain_switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"on\", \"1\": \"off\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (27, '卷帘产品id', 'curtain_switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"on\", \"1\": \"off\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (28, '百叶帘产品id', 'curtain_switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"on\", \"1\": \"off\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (29, '单开合帘产品id', 'curtain_switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"on\", \"1\": \"off\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (30, '卷帘产品id2', 'curtain_switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"on\", \"1\": \"off\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (31, 'HVAC产品id', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"off\", \"1\": \"on\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (32, 'HVAC产品id', 'control_mode', 2, 'mode', NULL, 'BAIDU', 1, '{\"0\": \"COOL\", \"1\": \"HEAT\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (33, 'HVAC产品id', 'wind_speed', 5, 'fanSpeed', NULL, 'BAIDU', 1, '{\"1\": \"low\", \"2\": \"middle\", \"3\": \"high\", \"4\": \"auto\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (34, '地暖产品id', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"off\", \"1\": \"on\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (35, '空气净化器产品id', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (36, '空气净化器产品id', 'wind_speed', 5, 'fanSpeed', NULL, 'BAIDU', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (37, '空气净化器产品id', 'CO2', 7, 'co2', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (38, '空气净化器产品id', 'PM2.5	\r\n', 8, 'pm2.5', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (39, 'DVD产品id', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"0\": \"off\", \"1\": \"on\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (40, 'DVD产品id', 'volume_roll', 1, 'volume', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (41, 'AQI产品id', 'AQI', 2, 'airQuality', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (42, 'pm2.5传感器产品id', 'PM2.5', 2, 'pm2.5', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (43, 'PM10传感器产品id', 'PM10', 2, 'pm10', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (44, '温度传感器产品id', 'temperature', 1, 'temperature', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (45, '湿度传感器产品id', 'humidity', 1, 'humidity', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (46, 'CO2传感器产品id', 'CO2', 1, 'co2', NULL, 'BAIDU', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (47, 'HVAC产品id', 'switch', 1, '2', '1', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:11:19', NULL);
INSERT INTO `m_function_mapping` VALUES (48, 'HVAC产品id', 'control_mode', 2, '2', '2', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:19', NULL);
INSERT INTO `m_function_mapping` VALUES (49, 'HVAC产品id', 'wind_speed', 5, '3', '2', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:19', NULL);
INSERT INTO `m_function_mapping` VALUES (50, 'HVAC产品id', 'temperature', 6, '4', '7', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:19', NULL);
INSERT INTO `m_function_mapping` VALUES (51, 'HVAC产品id', 'set_temperature', 7, '2', '4', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:19', NULL);
INSERT INTO `m_function_mapping` VALUES (52, 'RGB产品id', 'switch', 1, '2', '1', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:12:09', NULL);
INSERT INTO `m_function_mapping` VALUES (53, 'RGB产品id', 'rgb', 2, '2', '4', 'XIAOMI', 1, '{\"rgb\": \"toInteger\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:12:09', NULL);
INSERT INTO `m_function_mapping` VALUES (54, 'RGBCW产品id', 'switch', 1, '2', '1', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:11:44', NULL);
INSERT INTO `m_function_mapping` VALUES (55, 'RGBCW产品id', 'rgb', 2, '2', '4', 'XIAOMI', 1, '{\"rgb\": \"toInteger\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:11:44', NULL);
INSERT INTO `m_function_mapping` VALUES (56, 'RGBCW产品id', 'dimming_p', 3, '2', '3', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:44', NULL);
INSERT INTO `m_function_mapping` VALUES (57, 'RGBCW产品id', 'color_temperature', 4, '2', '5', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:44', NULL);
INSERT INTO `m_function_mapping` VALUES (58, 'RGBW产品id', 'switch', 1, '2', '1', 'XIAOMI', 1, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:58', NULL);
INSERT INTO `m_function_mapping` VALUES (59, 'RGBW产品id', 'rgbw', 2, '2', '4', 'XIAOMI', 1, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:58', NULL);
INSERT INTO `m_function_mapping` VALUES (60, '卷帘产品id2', 'curtain_switch', 1, '2', '1', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:10:19', NULL);
INSERT INTO `m_function_mapping` VALUES (61, '单开合帘产品id', 'curtain_switch', 1, '2', '1', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:12:22', NULL);
INSERT INTO `m_function_mapping` VALUES (62, '卷帘产品id', 'curtain_switch', 1, '2', '1', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:09:18', NULL);
INSERT INTO `m_function_mapping` VALUES (63, '卷帘产品id', 'curtain_location', 3, '2', '2', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:09:18', NULL);
INSERT INTO `m_function_mapping` VALUES (64, '地暖产品id', 'switch', 1, '2', '1', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:10:45', NULL);
INSERT INTO `m_function_mapping` VALUES (65, '地暖产品id', 'set_temperature', 2, '2', '5', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:10:45', NULL);
INSERT INTO `m_function_mapping` VALUES (66, '地暖产品id', 'temperature', 3, '2', '6', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:10:45', NULL);
INSERT INTO `m_function_mapping` VALUES (67, '地暖产品id', 'heat_switch', 4, '2', '9', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:10:45', NULL);
INSERT INTO `m_function_mapping` VALUES (68, '开合帘产品id', 'curtain_switch', 1, '2', '1', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:51', NULL);
INSERT INTO `m_function_mapping` VALUES (69, '开合帘产品id', 'curtain_location', 3, '2', '2', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:51', NULL);
INSERT INTO `m_function_mapping` VALUES (70, '空气净化器产品id', 'switch', 1, '2', '1', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:08:58', NULL);
INSERT INTO `m_function_mapping` VALUES (71, '空气净化器产品id', 'heat_exchange', 2, '2', '6', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:08:58', NULL);
INSERT INTO `m_function_mapping` VALUES (72, '空气净化器产品id', 'wind_speed', 5, '2', '5', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:08:58', NULL);
INSERT INTO `m_function_mapping` VALUES (73, '空气净化器产品id', 'auto_wind', 6, '2', '3', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:08:58', NULL);
INSERT INTO `m_function_mapping` VALUES (74, '空气净化器产品id', 'CO2', 7, '3', '8', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:08:58', NULL);
INSERT INTO `m_function_mapping` VALUES (75, '空气净化器产品id', 'PM2.5', 8, '3', '4', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:08:58', NULL);
INSERT INTO `m_function_mapping` VALUES (76, '空气净化器产品id', 'filter_available', 9, '4', '1', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:08:58', NULL);
INSERT INTO `m_function_mapping` VALUES (77, '百叶帘产品id', 'curtain_switch', 1, '2', '1', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:09:38', NULL);
INSERT INTO `m_function_mapping` VALUES (78, '百叶帘产品id', 'curtain_location', 3, '2', '2', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:09:38', NULL);
INSERT INTO `m_function_mapping` VALUES (79, 'DVD产品id', 'switch', 1, '5', '1', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:10:30', NULL);
INSERT INTO `m_function_mapping` VALUES (80, '灯光产品id', 'switch', 1, '2', '1', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:12:02', NULL);
INSERT INTO `m_function_mapping` VALUES (81, 'DVD产品id', 'volume_roll', 4, '2', '2', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:10:30', NULL);
INSERT INTO `m_function_mapping` VALUES (82, 'DVD产品id', 'music_mode', 7, '3', '3', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:10:30', NULL);
INSERT INTO `m_function_mapping` VALUES (83, 'DVD产品id', 'mute', 9, '2', '1', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:10:30', NULL);
INSERT INTO `m_function_mapping` VALUES (84, '色温灯产品id', 'switch', 1, '2', '1', 'XIAOMI', 1, '{\"0\": \"false\", \"1\": \"true\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:11:32', NULL);
INSERT INTO `m_function_mapping` VALUES (85, '色温灯产品id', 'dimming_p', 2, '2', '3', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:32', NULL);
INSERT INTO `m_function_mapping` VALUES (86, '色温灯产品id', 'color_temperature', 3, '2', '5', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:11:32', NULL);
INSERT INTO `m_function_mapping` VALUES (87, '灯光产品id3', 'switch', 1, '2', '1', 'XIAOMI', 1, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:12:28', NULL);
INSERT INTO `m_function_mapping` VALUES (88, '灯光产品id3', 'dimming_p', 2, '2', '3', 'XIAOMI', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-18 08:12:28', NULL);
INSERT INTO `m_function_mapping` VALUES (89, 'RGB产品id', 'rgb', 1, 'color', NULL, 'BAIDU', 0, NULL, 'RGB_TO_HEX', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (90, 'RGBCW产品id', 'rgb', 2, 'color', NULL, 'BAIDU', 0, NULL, 'RGB_TO_HEX', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (91, 'RGBW产品id', 'rgbw', 2, 'color', NULL, 'BAIDU', 0, NULL, NULL, NULL, '', NULL, '2024-04-16 17:23:25', NULL);
INSERT INTO `m_function_mapping` VALUES (92, 'RGBCW产品id', 'color_temperature', 4, 'colorTemperatureInKelvin', NULL, 'BAIDU', 0, NULL, 'LINEAR_1000_TO_10000', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (93, '开关产品id', 'switch', 1, 'powerState', 'Alexa.PowerController', 'ALEXA', 1, '{\"0\": \"OFF\", \"1\": \"ON\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (94, '灯光产品id', 'switch', 1, 'powerState', 'Alexa.PowerController', 'ALEXA', 1, '{\"0\": \"OFF\", \"1\": \"ON\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (95, '灯光产品id2', 'switch', 1, 'powerState', 'Alexa.PowerController', 'ALEXA', 1, '{\"0\": \"OFF\", \"1\": \"ON\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (96, '灯光产品id2', 'dimming_p', 2, 'brightness', 'Alexa.BrightnessController', 'ALEXA', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (97, '灯光产品id3', 'switch', 1, 'powerState', 'Alexa.PowerController', 'ALEXA', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (98, '灯光产品id3', 'dimming_p', 3, 'brightness', 'Alexa.BrightnessController', 'ALEXA', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (99, '色温灯产品id', 'switch', 1, 'powerState', 'Alexa.PowerController', 'ALEXA', 1, '{\"0\": \"OFF\", \"1\": \"ON\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (100, '色温灯产品id', 'dimming_p', 2, 'brightness', 'Alexa.BrightnessController', 'ALEXA', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (101, '色温灯产品id', 'color_temperature', 3, 'colorTemperatureInKelvin', 'Alexa.ColorTemperatureController', 'ALEXA', 1, NULL, 'LINEAR_1000_TO_10000', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (102, 'RGB产品id', 'switch', 1, 'powerState', 'Alexa.PowerController', 'ALEXA', 1, '{\"0\": \"OFF\", \"1\": \"ON\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (103, 'RGB产品id', 'rgb', 2, 'color', 'Alexa.ColorController', 'ALEXA', 1, NULL, 'RGB_TO_HEX', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (104, 'RGBW产品id', 'switch', 1, 'powerState', 'Alexa.PowerController', 'ALEXA', 1, NULL, NULL, NULL, NULL, NULL, '2024-04-19 03:00:46', NULL);
INSERT INTO `m_function_mapping` VALUES (105, 'RGBCW产品id', 'switch', 1, 'powerState', 'Alexa.PowerController', 'ALEXA', 1, '{\"0\": \"OFF\", \"1\": \"ON\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (106, 'RGBCW产品id', 'rgb', 2, 'color', 'Alexa.ColorController', 'ALEXA', 1, NULL, 'RGB_TO_HEX', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (107, 'RGBCW产品id', 'dimming_p', 3, 'brightness', 'Alexa.BrightnessController', 'ALEXA', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (108, '开合帘产品id', 'curtain_switch', 1, 'mode', 'Alexa.ModeController', 'ALEXA', 1, '{\"0\": \"Position.Up\", \"1\": \"Position.Down\"}', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (109, '开合帘产品id', 'curtain_location', 3, 'percentage', 'Alexa.PercentageController', 'ALEXA', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (110, '卷帘产品id', 'curtain_switch', 1, 'mode', 'Alexa.ModeController', 'ALEXA', 1, '{\"0\": \"Position.Up\", \"1\": \"Position.Down\"}', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (111, '卷帘产品id', 'curtain_location', 3, 'percentage', 'Alexa.PercentageController', 'ALEXA', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (112, '百叶帘产品id', 'curtain_switch', 1, 'mode', 'Alexa.ModeController', 'ALEXA', 1, '{\"0\": \"Position.Up\", \"1\": \"Position.Down\"}', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (113, '百叶帘产品id', 'curtain_location', 3, 'percentage', 'Alexa.PercentageController', 'ALEXA', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (114, '单开合帘产品id', 'curtain_switch', 1, 'mode', 'Alexa.ModeController', 'ALEXA', 1, '{\"0\": \"Position.Up\", \"1\": \"Position.Down\"}', NULL, '1', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (115, '卷帘产品id2', 'curtain_switch', 1, 'mode', 'Alexa.ModeController', 'ALEXA', 1, '{\"0\": \"Position.Up\", \"1\": \"Position.Down\"}', NULL, '1', NULL, NULL, '2024-04-19 03:00:53', NULL);
INSERT INTO `m_function_mapping` VALUES (116, 'HVAC产品id', 'switch', 1, 'powerState', 'Alexa.PowerController', 'ALEXA', 1, '{\"0\": \"OFF\", \"1\": \"ON\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (117, 'HVAC产品id', 'control_mode', 2, 'thermostatMode', 'Alexa.ThermostatController', 'ALEXA', 1, '{\"0\": \"COOL\", \"1\": \"HEAT\"}', NULL, '2', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (118, 'HVAC产品id', 'wind_speed', 5, 'rangeValue', 'Alexa.RangeController', 'ALEXA', 0, NULL, NULL, '3', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (119, 'HVAC产品id', 'temperature', 6, 'temperature', 'Alexa.TemperatureSensor', 'ALEXA', 0, NULL, 'CELSIUS_UNIT', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (120, 'HVAC产品id', 'set_temperature', 7, 'targetSetpoint', 'Alexa.ThermostatController', 'ALEXA', 0, NULL, 'CELSIUS_UNIT', '2', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (121, '空调产品id', 'switch', 1, 'powerState', 'Alexa.PowerController', 'ALEXA', 1, '{\"0\": \"OFF\", \"1\": \"ON\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (122, '空调产品id', 'wind_speed', 5, 'rangeValue', 'Alexa.RangeController', 'ALEXA', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (123, '空调产品id', 'temperature', 6, 'temperature', 'Alexa.TemperatureSensor', 'ALEXA', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (124, '空调产品id', 'set_temperature', 7, 'targetSetpoint', 'Alexa.ThermostatController', 'ALEXA', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (125, '空调产品id', 's_operation_mode', 9, 'thermostatMode', 'Alexa.ThermostatController', 'ALEXA', 1, '{\"1\": \"AUTO\", \"2\": \"HEAT\", \"3\": \"COOL\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (126, '地暖产品id', 'switch', 1, 'powerState', 'Alexa.PowerController', 'ALEXA', 1, '{\"0\": \"OFF\", \"1\": \"ON\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (127, '地暖产品id', 'set_temperature', 2, 'targetSetpoint', 'Alexa.ThermostatController', 'ALEXA', 0, NULL, 'CELSIUS_UNIT', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (128, '地暖产品id', 'temperature', 3, 'temperature', 'Alexa.TemperatureSensor', 'ALEXA', 0, NULL, 'CELSIUS_UNIT', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (129, '地暖产品id', 'heat_switch', 4, 'thermostatMode', 'Alexa.ThermostatController', 'ALEXA', 1, '{\"0\": \"OFF\", \"1\": \"HEAT\"}', NULL, '6', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (130, '空气净化器产品id', 'switch', 1, 'powerState', 'Alexa.PowerController', 'ALEXA', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (131, '空气净化器产品id', 'wind_speed', 5, 'rangeValue', 'Alexa.RangeController', 'ALEXA', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (133, '开关产品id', 'switch', 1, 'on', 'action.devices.traits.OnOff', 'GOOGLE', 1, '{\"0\": false, \"1\": true}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (134, '灯光产品id', 'switch', 1, 'on', 'action.devices.traits.OnOff', 'GOOGLE', 1, '{\"0\": false, \"1\": true}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (135, '灯光产品id2', 'switch', 1, 'on', 'action.devices.traits.OnOff', 'GOOGLE', 1, '{\"0\": false, \"1\": true}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (136, '灯光产品id2', 'dimming_p', 2, 'brightness', 'action.devices.traits.Brightness', 'GOOGLE', 0, NULL, 'STRING_TO_INT', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (137, '灯光产品id3', 'switch', 1, 'on', 'action.devices.traits.OnOff', 'GOOGLE', 1, '{\"0\": false, \"1\": true}', NULL, NULL, NULL, NULL, '2024-04-19 05:30:57', NULL);
INSERT INTO `m_function_mapping` VALUES (138, '灯光产品id3', 'dimming_p', 3, 'brightness', 'action.devices.traits.Brightness', 'GOOGLE', 0, NULL, 'STRING_TO_INT', NULL, NULL, NULL, '2024-04-19 05:30:57', NULL);
INSERT INTO `m_function_mapping` VALUES (139, '色温灯产品id', 'switch', 1, 'on', 'action.devices.traits.OnOff', 'GOOGLE', 1, '{\"0\": false, \"1\": true}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (140, '色温灯产品id', 'dimming_p', 2, 'brightness', 'action.devices.traits.Brightness', 'GOOGLE', 0, NULL, 'STRING_TO_INT', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (141, '色温灯产品id', 'color_temperature', 3, 'color#temperatureK', 'action.devices.traits.ColorSetting', 'GOOGLE', 0, NULL, 'STRING_TO_INT', '9', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (142, 'RGB产品id', 'switch', 1, 'on', 'action.devices.traits.OnOff', 'GOOGLE', 1, '{\"0\": false, \"1\": true}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (143, 'RGB产品id', 'rgb', 2, 'color#spectrumRgb', 'action.devices.traits.ColorSetting', 'GOOGLE', 0, NULL, 'RGB_TO_INT', '10', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (144, 'RGBW产品id', 'switch', 1, 'on', 'action.devices.traits.OnOff', 'GOOGLE', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (145, 'RGBW产品id', 'rgbw', 2, 'color#spectrumHsv', 'action.devices.traits.ColorSetting', 'GOOGLE', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (146, 'RGBCW产品id', 'switch', 1, 'on', 'action.devices.traits.OnOff', 'GOOGLE', 1, '{\"0\": false, \"1\": true}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (147, 'RGBCW产品id', 'rgb', 2, 'color#spectrumRgb', 'action.devices.traits.ColorSetting', 'GOOGLE', 0, NULL, 'RGB_TO_INT', '10', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (148, 'RGBCW产品id', 'dimming_p', 3, 'brightness', 'action.devices.traits.Brightness', 'GOOGLE', 0, NULL, 'STRING_TO_INT', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (149, '开合帘产品id', 'curtain_location', 3, 'openPercent', 'action.devices.traits.OpenClose', 'GOOGLE', 0, NULL, 'STRING_TO_INT', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (150, '卷帘产品id', 'curtain_location', 3, '[]openState#openPercent', 'action.devices.traits.OpenClose', 'GOOGLE', 0, NULL, 'STRING_TO_INT', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (151, '百叶帘产品id', 'curtain_location', 3, 'openPercent', 'action.devices.traits.OpenClose', 'GOOGLE', 0, NULL, 'STRING_TO_INT', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (152, '单开合帘产品id', 'curtain_switch', 1, 'openPercent', 'action.devices.traits.OpenClose', 'GOOGLE', 1, '{\"0\": 100, \"1\": 0}', 'STRING_TO_INT', '12', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (153, '卷帘产品id2', 'curtain_switch', 1, 'openPercent', 'action.devices.traits.OpenClose', 'GOOGLE', 1, '{\"0\": 100, \"1\": 0}', 'STRING_TO_INT', '12', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (154, 'HVAC产品id', 'switch', 1, 'on', 'action.devices.traits.OnOff', 'GOOGLE', 1, '{\"0\": false, \"1\": true}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (155, 'HVAC产品id', 'control_mode', 2, 'thermostatMode', 'action.devices.traits.TemperatureSetting', 'GOOGLE', 1, '{\"0\": \"cool\", \"1\": \"heat\"}', NULL, '16', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (156, 'HVAC产品id', 'wind_speed', 5, 'currentFanSpeedSetting', 'action.devices.traits.FanSpeed', 'GOOGLE', 1, '{\"0\": \"speed_off\", \"1\": \"speed_low\", \"2\": \"speed_medium\", \"3\": \"speed_high\", \"4\": \"speed_auto\"}', NULL, '17', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (157, 'HVAC产品id', 'temperature', 6, 'thermostatTemperatureAmbient', 'action.devices.traits.TemperatureSetting', 'GOOGLE', 0, NULL, 'STRING_TO_INT', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (158, 'HVAC产品id', 'set_temperature', 7, 'thermostatTemperatureSetpoint', 'action.devices.traits.TemperatureSetting', 'GOOGLE', 0, NULL, 'STRING_TO_INT', '18,19,27', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (159, '空调产品id', 'switch', 1, 'on', 'action.devices.traits.OnOff', 'GOOGLE', 1, '{\"0\": false, \"1\": true}', NULL, NULL, NULL, NULL, '2024-04-15 14:09:00', NULL);
INSERT INTO `m_function_mapping` VALUES (160, '空调产品id', 'wind_speed', 5, 'currentFanSpeedSetting', 'action.devices.traits.FanSpeed', 'GOOGLE', 1, '{\"1\": \"speed_low\", \"2\": \"speed_medium\", \"3\": \"speed_high\", \"4\": \"speed_auto\"}', NULL, '20', NULL, NULL, '2024-04-15 14:09:00', NULL);
INSERT INTO `m_function_mapping` VALUES (161, '空调产品id', 'temperature', 6, 'thermostatTemperatureAmbient', 'action.devices.traits.TemperatureSetting', 'GOOGLE', 0, NULL, 'STRING_TO_INT', NULL, NULL, NULL, '2024-04-15 14:09:00', NULL);
INSERT INTO `m_function_mapping` VALUES (162, '空调产品id', 'set_temperature', 7, 'thermostatTemperatureSetpoint', 'action.devices.traits.TemperatureSetting', 'GOOGLE', 0, NULL, 'STRING_TO_INT', '18,19', NULL, NULL, '2024-04-15 14:09:00', NULL);
INSERT INTO `m_function_mapping` VALUES (163, '空调产品id', 's_operation_mode', 9, 'thermostatMode', 'action.devices.traits.TemperatureSetting', 'GOOGLE', 1, '{\"1\": \"auto\", \"2\": \"heat\", \"3\": \"cool\", \"4\": \"fan-only\"}', NULL, '21', NULL, NULL, '2024-04-15 14:09:00', NULL);
INSERT INTO `m_function_mapping` VALUES (164, '地暖产品id', 'set_temperature', 2, 'thermostatTemperatureSetpoint', 'action.devices.traits.TemperatureSetting', 'GOOGLE', 0, NULL, 'STRING_TO_INT', '18,19,26', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (165, '地暖产品id', 'temperature', 3, 'thermostatTemperatureAmbient', 'action.devices.traits.TemperatureSetting', 'GOOGLE', 0, NULL, 'STRING_TO_INT', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (166, '空气净化器产品id', 'switch', 1, 'on', 'action.devices.traits.OnOff', 'GOOGLE', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (167, '空气净化器产品id', 'wind_speed', 5, 'currentFanSpeedSetting', 'action.devices.traits.FanSpeed', 'GOOGLE', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (168, '空气净化器产品id', 'CO2', 7, 'currentSensorStateData#CarbonDioxideLevel', 'action.devices.traits.SensorState', 'GOOGLE', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (169, '空气净化器产品id', 'PM2.5', 8, 'PM2.5#CarbonDioxideLevel', 'action.devices.traits.SensorState', 'GOOGLE', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (170, 'HVAC产品id', 'fault', 0, '2', '3', 'XIAOMI', 1, '{\"defaultValue\": \"0\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:11:19', '2024-03-04 10:22:51');
INSERT INTO `m_function_mapping` VALUES (171, '地暖产品id', 'fault', 0, '2', '2', 'XIAOMI', 1, '{\"defaultValue\": \"0\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:10:45', '2024-03-04 10:22:51');
INSERT INTO `m_function_mapping` VALUES (172, '空气净化器产品id', 'fault', 0, '2', '2', 'XIAOMI', 1, '{\"defaultValue\": \"0\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:08:58', '2024-03-04 10:22:51');
INSERT INTO `m_function_mapping` VALUES (173, '空调产品id', 'fault', 0, '2', '3', 'XIAOMI', 1, '{\"defaultValue\": \"0\"}', NULL, NULL, NULL, NULL, '2024-04-18 08:11:03', '2024-03-04 10:22:51');
INSERT INTO `m_function_mapping` VALUES (176, '空调产品id', 's_operation_mode', 9, 'activeThermostatMode', 'action.devices.traits.TemperatureSetting', 'GOOGLE', 1, '{\"1\": \"auto\", \"2\": \"heat\", \"3\": \"cool\", \"4\": \"fan-only\"}', NULL, NULL, NULL, NULL, '2024-04-15 14:09:00', NULL);
INSERT INTO `m_function_mapping` VALUES (177, 'HVAC产品id', 'control_mode', 2, 'activeThermostatMode', 'action.devices.traits.TemperatureSetting', 'GOOGLE', 1, '{\"0\": \"cool\", \"1\": \"heat\"}', NULL, '16', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (178, '地暖产品id', 'switch', 1, 'thermostatMode', 'action.devices.traits.TemperatureSetting', 'GOOGLE', 1, '{\"0\": \"off\", \"1\": \"heat\"}', NULL, '28', NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (181, 'scene', 'switch', 1, 'turnOnState', NULL, 'BAIDU', 1, '{\"1\": \"ON\"}', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `m_function_mapping` VALUES (182, '灯光产品id3', 'test', 3, 'test#color_tewr', NULL, 'GOOGLE', 0, '{\"\": \"\"}', 'RGB_TO_HEX', NULL, 'test', NULL, '2024-04-19 05:30:57', '2024-04-16 14:30:11');

-- ----------------------------
-- Table structure for m_product_type_mapping
-- ----------------------------
DROP TABLE IF EXISTS `m_product_type_mapping`;
CREATE TABLE `m_product_type_mapping`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '产品id',
  `third_product` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '第三方产品名',
  `third_product2` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '第三方产品名 - 第二形式',
  `third_product_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '第三方产品id',
  `third_brand` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '第三方品牌',
  `third_party_cloud` enum('BAIDU','XIAOMI','HUAWEI','TMALL','ALEXA','GOOGLE') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语音平台 ',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '产品类型映射关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_product_type_mapping
-- ----------------------------
INSERT INTO `m_product_type_mapping` VALUES (1, '开关产品id', NULL, NULL, 'SWITCH', NULL, 'BAIDU', '2024-04-16 17:23:04');
INSERT INTO `m_product_type_mapping` VALUES (2, '灯光产品id2', NULL, NULL, 'LIGHT', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (3, '空调产品id', NULL, NULL, 'AIR_CONDITION', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (4, '灯光产品id', NULL, NULL, 'SWITCH', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (5, '灯光产品id3', NULL, NULL, 'SWITCH', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (6, '灯光产品id3', NULL, NULL, 'LIGHT', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (7, '色温灯产品id', NULL, NULL, 'SWITCH', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (8, '色温灯产品id', NULL, NULL, 'LIGHT', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (9, 'RGB产品id', NULL, NULL, 'LIGHT', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (10, 'RGBW产品id', NULL, NULL, 'LIGHT', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (11, 'RGBCW产品id', NULL, NULL, 'LIGHT', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (12, '开合帘产品id', NULL, NULL, 'CURTAIN', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (13, '卷帘产品id', NULL, NULL, 'CURTAIN', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (14, '百叶帘产品id', NULL, NULL, 'CURTAIN', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (15, '单开合帘产品id', NULL, NULL, 'CURTAIN', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (16, '卷帘产品id2', NULL, NULL, 'CURTAIN', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (17, 'HVAC产品id', NULL, NULL, 'AIR_CONDITION', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (18, '地暖产品id', NULL, NULL, 'HEATER', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (19, '空气净化器产品id', NULL, NULL, 'AIR_PURIFIER', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (20, '空气净化器产品id', NULL, NULL, 'AIR_CONDITION', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (21, 'DVD产品id', NULL, NULL, 'DVD', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (22, 'AQI产品id', NULL, NULL, 'AIR_PURIFIER', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (23, 'pm2.5传感器产品id', NULL, NULL, 'AIR_PURIFIER', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (24, 'PM10传感器产品id', NULL, NULL, 'AIR_PURIFIER', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (25, '温度传感器产品id', NULL, NULL, 'AIR_PURIFIER', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (26, '湿度传感器产品id', NULL, NULL, 'AIR_PURIFIER', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (27, 'CO2传感器产品id', NULL, NULL, 'AIR_PURIFIER', NULL, 'BAIDU', NULL);
INSERT INTO `m_product_type_mapping` VALUES (28, '开关产品id', NULL, NULL, 'LIGHT', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (29, '灯光产品id', NULL, NULL, 'LIGHT', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (30, '灯光产品id2', NULL, NULL, 'LIGHT', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (31, '灯光产品id3', NULL, NULL, 'LIGHT', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (32, '色温灯产品id', NULL, NULL, 'LIGHT', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (33, 'RGB产品id', NULL, NULL, 'LIGHT', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (34, 'RGBW产品id', NULL, NULL, 'LIGHT', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (35, 'RGBCW产品id', NULL, NULL, 'LIGHT', NULL, 'ALEXA', '2024-04-16 17:22:28');
INSERT INTO `m_product_type_mapping` VALUES (36, '开合帘产品id', NULL, NULL, 'INTERIOR_BLIND', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (37, '卷帘产品id', NULL, NULL, 'INTERIOR_BLIND', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (38, '百叶帘产品id', NULL, NULL, 'INTERIOR_BLIND', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (39, '单开合帘产品id', NULL, NULL, 'INTERIOR_BLIND', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (40, '卷帘产品id2', NULL, NULL, 'INTERIOR_BLIND', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (41, 'HVAC产品id', NULL, NULL, 'THERMOSTAT', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (42, '空调产品id', NULL, NULL, 'THERMOSTAT', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (43, '地暖产品id', NULL, NULL, 'THERMOSTAT', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (44, '空气净化器产品id', NULL, NULL, 'AIR_FRESHENER', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (45, 'DVD产品id', NULL, NULL, 'MUSIC_SYSTEM', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (46, 'HVAC产品id', NULL, NULL, 'TEMPERATURE_SENSOR', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (47, '空调产品id', NULL, NULL, 'TEMPERATURE_SENSOR', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (48, '地暖产品id', NULL, NULL, 'TEMPERATURE_SENSOR', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (49, 'HVAC产品id', NULL, NULL, 'FAN', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (50, '空调产品id', NULL, NULL, 'FAN', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (51, '地暖产品id', NULL, NULL, 'FAN', NULL, 'ALEXA', NULL);
INSERT INTO `m_product_type_mapping` VALUES (52, 'HVAC产品id', NULL, NULL, 'urn:miot-spec-v2:device:air-conditioner:0000A004:test-air:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (53, 'RGB产品id', NULL, NULL, 'urn:miot-spec-v2:device:light:0000A001:test-rgbcw:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (54, 'RGBCW产品id', NULL, NULL, 'urn:miot-spec-v2:device:light:0000A001:test-rgbcw:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (55, 'RGBW产品id', NULL, NULL, 'urn:miot-spec-v2:device:light:0000A001:test-rgbcw:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (56, '灯光产品id2', NULL, NULL, 'urn:miot-spec-v2:device:light:0000A001:test-rgbcw:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (57, '卷帘产品id2', NULL, NULL, 'urn:miot-spec-v2:device:curtain:0000A00C:test-c6:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (58, '单开合帘产品id', NULL, NULL, 'urn:miot-spec-v2:device:curtain:0000A00C:test-c6:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (59, '卷帘产品id', NULL, NULL, 'urn:miot-spec-v2:device:curtain:0000A00C:test-c6:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (60, '地暖产品id', NULL, NULL, 'urn:miot-spec-v2:device:heater:0000A01A:test-h1:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (61, '开关产品id', NULL, NULL, 'urn:miot-spec-v2:device:switch:0000A003:test-sw1:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (62, '开合帘产品id', NULL, NULL, 'urn:miot-spec-v2:device:curtain:0000A00C:test-c6:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (63, '空气净化器产品id', NULL, NULL, 'urn:miot-spec-v2:device:air-fresh:0000A012:test-air3:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (64, '空调产品id', NULL, NULL, 'urn:miot-spec-v2:device:air-conditioner:0000A004:test-air:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (65, '百叶帘产品id', NULL, NULL, 'urn:miot-spec-v2:device:curtain:0000A00C:test-c6:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (66, '灯光产品id', NULL, NULL, 'urn:miot-spec-v2:device:light:0000A001:test-rgbcw:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (67, 'DVD产品id', NULL, NULL, 'urn:miot-spec-v2:device:speaker:0000A015:test-s:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (68, '色温灯产品id', NULL, NULL, 'urn:miot-spec-v2:device:light:0000A001:test-rgbcw:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (69, '灯光产品id3', NULL, NULL, 'urn:miot-spec-v2:device:light:0000A001:test-rgbcw:1', NULL, 'XIAOMI', NULL);
INSERT INTO `m_product_type_mapping` VALUES (70, '开关产品id', NULL, NULL, 'action.devices.types.SWITCH', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (71, '灯光产品id', NULL, NULL, 'action.devices.types.LIGHT', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (72, '灯光产品id2', NULL, NULL, 'action.devices.types.LIGHT', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (73, '灯光产品id3', NULL, NULL, 'action.devices.types.LIGHT', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (74, '色温灯产品id', NULL, NULL, 'action.devices.types.LIGHT', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (75, 'RGB产品id', NULL, NULL, 'action.devices.types.LIGHT', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (76, 'RGBCW产品id', NULL, NULL, 'action.devices.types.LIGHT', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (77, '开合帘产品id', NULL, NULL, 'action.devices.types.CURTAIN', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (78, '卷帘产品id', NULL, NULL, 'action.devices.types.CURTAIN', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (79, '百叶帘产品id', NULL, NULL, 'action.devices.types.CURTAIN', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (80, '单开合帘产品id', NULL, NULL, 'action.devices.types.CURTAIN', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (81, '卷帘产品id2', NULL, NULL, 'action.devices.types.CURTAIN', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (82, 'HVAC产品id', NULL, NULL, 'action.devices.types.AC_UNIT', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (83, '空调产品id', NULL, NULL, 'action.devices.types.AC_UNIT', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (84, '地暖产品id', NULL, NULL, 'action.devices.types.THERMOSTAT', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (85, '空气净化器产品id', NULL, NULL, 'action.devices.types.AIRPURIFIER', NULL, 'GOOGLE', NULL);
INSERT INTO `m_product_type_mapping` VALUES (96, 'scene', NULL, NULL, 'SCENE_TRIGGER', NULL, 'BAIDU', NULL);

-- ----------------------------
-- Table structure for u_user_authorize
-- ----------------------------
DROP TABLE IF EXISTS `u_user_authorize`;
CREATE TABLE `u_user_authorize`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `client_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `third_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `third_party_cloud` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of u_user_authorize
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
