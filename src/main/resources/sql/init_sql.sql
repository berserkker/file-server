/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : app

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 31/08/2019 17:11:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fileinfo
-- ----------------------------
DROP TABLE IF EXISTS `fileinfo`;
CREATE TABLE `fileinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '文件所在路径',
  `type` varchar(255) NOT NULL DEFAULT '' COMMENT '文件类型',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '文件名称',
  `suffix` varchar(255) NOT NULL DEFAULT '' COMMENT '文件后缀',
  `size` double NOT NULL DEFAULT '0' COMMENT '文件大小',
  `uuid` varchar(255) NOT NULL DEFAULT '' COMMENT '文件uuid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
