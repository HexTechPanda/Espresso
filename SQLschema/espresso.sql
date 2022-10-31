/*
 Navicat Premium Data Transfer

 Source Server         : LocalMySQL
 Source Server Type    : MySQL
 Source Server Version : 50728 (5.7.28-log)
 Source Host           : localhost:3306
 Source Schema         : espresso

 Target Server Type    : MySQL
 Target Server Version : 50728 (5.7.28-log)
 File Encoding         : 65001

 Date: 31/10/2022 10:45:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for espresso_user
-- ----------------------------
DROP TABLE IF EXISTS `espresso_user`;
CREATE TABLE `espresso_user`  (
  `id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'user_id',
  `username` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'username',
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'password, encryted, admin/1234',
  `is_account_non_expired` int(2) NULL DEFAULT 1 COMMENT '1 expire, 0 not expire',
  `is_account_non_locked` int(2) NULL DEFAULT 1 COMMENT '1 locked, 0 not locked',
  `is_credentials_non_expired` int(2) NULL DEFAULT 1 COMMENT '1 expire, 0 not expire',
  `is_enabled` int(2) NULL DEFAULT 1 COMMENT '1 enable 0 unenable',
  `nick_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'nick_name',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'mobile',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'email',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create_date',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'update_date',
  `pwd_update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'pwd_update_date',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'user info table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of espresso_user
-- ----------------------------
INSERT INTO `espresso_user` VALUES ('1586700126404571137', 'tester01', '$2a$10$.f84CGrI1os678dKAApjWeKx1RAygMaOIjDvXkWQon.aKzjdDGEAK', 1, 1, 1, 1, 'tester01nick', '88883333', 'tester01@test.com', '2022-10-30 20:42:44', '2022-10-30 20:42:44', '2022-10-30 20:42:44');

SET FOREIGN_KEY_CHECKS = 1;
