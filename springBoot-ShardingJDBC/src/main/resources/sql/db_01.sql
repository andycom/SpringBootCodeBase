/*
Navicat MySQL Data Transfer

Source Server         : LOCAL_HOST
Source Server Version : 80023
Source Host           : 127.0.0.1:3306
Source Database       : db_01

Target Server Type    : MYSQL
Target Server Version : 80023
File Encoding         : 65001

Date: 2022-02-14 00:21:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_type` varchar(50) DEFAULT NULL COMMENT ' 数据类别,首字母大写 ',
  `data_code` varchar(50) DEFAULT NULL COMMENT ' 数据编码 ',
  `data_value` varchar(200) DEFAULT NULL COMMENT ' 数据名称/值 ',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', null, 'QDAMA', '全球卖菜', '2021-09-20 16:15:08');

-- ----------------------------
-- Table structure for t_order_0
-- ----------------------------
DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE `t_order_0` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order_0
-- ----------------------------

-- ----------------------------
-- Table structure for t_order_1
-- ----------------------------
DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order_1
-- ----------------------------
INSERT INTO `t_order_1` VALUES ('1', '1', '1', 'remark:1', '2021-09-05 23:43:05');
INSERT INTO `t_order_1` VALUES ('2', '5', '5', 'remark:5', '2021-09-05 23:43:05');
INSERT INTO `t_order_1` VALUES ('3', '9', '9', 'remark:9', '2021-09-05 23:43:05');
INSERT INTO `t_order_1` VALUES ('4', '13', '13', 'remark:13', '2021-09-06 00:43:11');
INSERT INTO `t_order_1` VALUES ('5', '17', '17', 'remark:17', '2021-09-06 00:43:11');
INSERT INTO `t_order_1` VALUES ('6', '13', '13', 'remark:13', '2021-09-11 23:27:47');
INSERT INTO `t_order_1` VALUES ('7', '17', '17', 'remark:17', '2021-09-11 23:27:47');
INSERT INTO `t_order_1` VALUES ('8', '13', '13', 'remark:13', '2021-09-11 23:43:01');
INSERT INTO `t_order_1` VALUES ('9', '17', '17', 'remark:17', '2021-09-11 23:43:01');
INSERT INTO `t_order_1` VALUES ('10', '61', '61', 'remark:11', '2021-09-20 22:36:38');
INSERT INTO `t_order_1` VALUES ('11', '65', '65', 'remark:15', '2021-09-20 22:36:38');
INSERT INTO `t_order_1` VALUES ('12', '69', '69', 'remark:19', '2021-09-20 22:36:38');
INSERT INTO `t_order_1` VALUES ('13', '13', '13', 'remark:12', '2022-02-13 23:04:48');
INSERT INTO `t_order_1` VALUES ('14', '17', '17', 'remark:16', '2022-02-13 23:04:48');
