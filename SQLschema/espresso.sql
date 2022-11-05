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

 Date: 05/11/2022 16:53:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for espresso_menu_category
-- ----------------------------
DROP TABLE IF EXISTS `espresso_menu_category`;
CREATE TABLE `espresso_menu_category`  (
  `id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `active_status` tinyint(1) NULL DEFAULT 1,
  `category_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_outline_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKfxtlya3a0jmqonc4ijj17bdbu`(`menu_outline_id`) USING BTREE,
  CONSTRAINT `FKfxtlya3a0jmqonc4ijj17bdbu` FOREIGN KEY (`menu_outline_id`) REFERENCES `espresso_menu_outline` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of espresso_menu_category
-- ----------------------------

-- ----------------------------
-- Table structure for espresso_menu_item
-- ----------------------------
DROP TABLE IF EXISTS `espresso_menu_item`;
CREATE TABLE `espresso_menu_item`  (
  `id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `available` tinyint(1) NULL DEFAULT 1,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `discount` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` int(11) NULL DEFAULT 0,
  `sales` bigint(20) NULL DEFAULT 0,
  `menu_category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_outline_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKl2f73b8q8fflwofj0j0aj6dee`(`menu_category_id`) USING BTREE,
  INDEX `FKf9pq4merfy07johck3ph02mwo`(`menu_outline_id`) USING BTREE,
  CONSTRAINT `FKf9pq4merfy07johck3ph02mwo` FOREIGN KEY (`menu_outline_id`) REFERENCES `espresso_menu_outline` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKl2f73b8q8fflwofj0j0aj6dee` FOREIGN KEY (`menu_category_id`) REFERENCES `espresso_menu_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of espresso_menu_item
-- ----------------------------

-- ----------------------------
-- Table structure for espresso_menu_outline
-- ----------------------------
DROP TABLE IF EXISTS `espresso_menu_outline`;
CREATE TABLE `espresso_menu_outline`  (
  `id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `active_status` tinyint(1) NULL DEFAULT 0,
  `created_date` datetime NULL DEFAULT NULL,
  `default_status` tinyint(1) NULL DEFAULT 0,
  `last_updated_date` datetime NULL DEFAULT NULL,
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `owner_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK88ug1ufhh9oqpxfbhxc216bf2`(`owner_user_id`) USING BTREE,
  CONSTRAINT `FK88ug1ufhh9oqpxfbhxc216bf2` FOREIGN KEY (`owner_user_id`) REFERENCES `espresso_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of espresso_menu_outline
-- ----------------------------

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
  `provider` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `provider_id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'user info table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of espresso_user
-- ----------------------------
INSERT INTO `espresso_user` VALUES ('1586700126404571137', 'tester01', '$2a$10$.f84CGrI1os678dKAApjWeKx1RAygMaOIjDvXkWQon.aKzjdDGEAK', 1, 1, 1, 1, 'tester01nick', '88883333', 'tester01@test.com', '2022-10-30 20:42:44', '2022-10-30 20:42:44', '2022-10-30 20:42:44', NULL, NULL);
INSERT INTO `espresso_user` VALUES ('1587692110615150593', 'tester03', '$2a$10$ngkGjnp6MGdmHF8zehunEOyTROM0xwMpbg91pPuPCnLdO8NEC76ae', 1, 1, 1, 1, 'tester01nick', '88883363', 'tester03@test.com', '2022-11-02 14:24:32', '2022-11-02 14:24:32', '2022-11-02 14:24:32', NULL, NULL);
INSERT INTO `espresso_user` VALUES ('1588815527299235841', 'tester04', '$2a$10$omceopX8WPQtvagNqPee0eHA/9qalAGs1AZwXpNAC8H5JRh7BZ/q6', 1, 1, 1, 1, 'tester04nick', '88883463', 'tester04@test.com', '2022-11-05 16:48:35', '2022-11-05 16:48:35', '2022-11-05 16:48:35', NULL, NULL);
INSERT INTO `espresso_user` VALUES ('1588815861253914626', 'thirdparty01', '$2a$10$ReHhPCOW/OjNFVJatXjqcOgG88b9mGw7glm1bai3sMXyiYoVQ6q9i', 1, 1, 1, 1, 'thirdparty01nick', '88883460', 'thirdparty01@test.com', '2022-11-05 16:49:55', '2022-11-05 16:49:55', '2022-11-05 16:49:55', NULL, NULL);

-- ----------------------------
-- Table structure for espresso_user_role
-- ----------------------------
DROP TABLE IF EXISTS `espresso_user_role`;
CREATE TABLE `espresso_user_role`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of espresso_user_role
-- ----------------------------
INSERT INTO `espresso_user_role` VALUES ('1588815527349567489', '1588815527299235841', 'ROLE_customer');
INSERT INTO `espresso_user_role` VALUES ('1588815861316829185', '1588815861253914626', 'ROLE_thirdparty');
INSERT INTO `espresso_user_role` VALUES ('3336700177404571456', '1586700126404571137', 'ROLE_customer');
INSERT INTO `espresso_user_role` VALUES ('4323343216756814423', '1586700126404571137', 'ROLE_thirdparty');
INSERT INTO `espresso_user_role` VALUES ('7586454354521111455', '1587692110615150593', 'ROLE_customer');

SET FOREIGN_KEY_CHECKS = 1;
