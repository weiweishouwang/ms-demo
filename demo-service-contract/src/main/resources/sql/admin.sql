/*
 Navicat MySQL Data Transfer

 Source Server         : 139.224.62.74
 Source Server Version : 50718
 Source Host           : 139.224.62.74
 Source Database       : demo

 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 07/14/2017 11:24:40 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL,
  `account` char(20) NOT NULL,
  `passwd` char(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `version` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
