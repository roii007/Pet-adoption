/*
 Navicat Premium Dump SQL

 Source Server         : My_new_DB
 Source Server Type    : MySQL
 Source Server Version : 80043 (8.0.43)
 Source Host           : localhost:3306
 Source Schema         : pet_adoption

 Target Server Type    : MySQL
 Target Server Version : 80043 (8.0.43)
 File Encoding         : 65001

 Date: 15/06/2026 15:32:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `pet_id` int NOT NULL,
  `applicant_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `applicant_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `applicant_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `house_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `has_experience` int NULL DEFAULT 0,
  `reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '待审核',
  `reviewer_id` int NULL DEFAULT NULL,
  `review_comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `reviewed_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `pet_id`(`pet_id` ASC) USING BTREE,
  CONSTRAINT `application_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `application_ibfk_2` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of application
-- ----------------------------
INSERT INTO `application` VALUES (1, 2, 2, 'lilies', '17724935164', '北京市', '自有住房', 0, '喜欢', '通过', 1, NULL, '2026-05-28 23:19:48', '2026-05-28 23:19:08');
INSERT INTO `application` VALUES (2, 3, 1, '许嘉伟', '15766942686', '铁场帅安新村江贝园街78号', '自有住房', 0, '因为想', '拒绝', 1, '无', '2026-05-30 22:49:46', '2026-05-30 22:47:11');
INSERT INTO `application` VALUES (3, 3, 6, '许嘉伟', '15766942686', '铁场帅安新村江贝园街78号', '自有住房', 1, '喜欢黑色小猫', '通过', 1, NULL, '2026-06-09 10:42:06', '2026-06-09 10:27:03');
INSERT INTO `application` VALUES (4, 3, 6, '许嘉伟', '15766942686', '铁场帅安新村江贝园街78号', '自有住房', 1, '11', '已取消', NULL, NULL, NULL, '2026-06-09 10:27:59');
INSERT INTO `application` VALUES (5, 3, 5, '许嘉伟', '15766942686', '铁场帅安新村江贝园街78号', '自有住房', 1, '家里只有一只小黑猫，怕他无聊，有个小朋猫一起玩', '通过', 1, NULL, '2026-06-09 10:49:34', '2026-06-09 10:48:25');
INSERT INTO `application` VALUES (6, 4, 7, '许嘉伟', '15766942686', '铁场帅安新村江贝园街78号', '自有住房', 1, '喜欢', 'rejected', 1, '五', '2026-06-14 21:07:29', '2026-06-14 20:43:58');
INSERT INTO `application` VALUES (7, 3, 7, '许嘉伟', '15766942686', '铁场帅安新村江贝园街78号', '自有住房', 1, '喜欢', '已取消', NULL, NULL, NULL, '2026-06-14 20:51:23');
INSERT INTO `application` VALUES (8, 2, 7, '许嘉伟', '15766942686', '铁场帅安新村江贝园街78号', '自有住房', 0, '喜欢', 'rejected', 1, '无', '2026-06-14 21:07:06', '2026-06-14 20:53:58');
INSERT INTO `application` VALUES (9, 3, 7, '许先生', '1234567899', '广东省', '自有住房', 0, '喜欢', 'cancelled', NULL, NULL, NULL, '2026-06-14 20:56:36');
INSERT INTO `application` VALUES (10, 5, 7, '许嘉伟', '15766942686', '铁场帅安新村江贝园街78号', '自有住房', 1, '喜欢', 'cancelled', NULL, NULL, NULL, '2026-06-14 21:13:33');
INSERT INTO `application` VALUES (11, 5, 1, '许嘉伟', '15766942686', '铁场帅安新村江贝园街78号', '自有住房', 1, '喜欢', 'approved', 1, NULL, '2026-06-14 21:16:16', '2026-06-14 21:14:01');

-- ----------------------------
-- Table structure for pet
-- ----------------------------
DROP TABLE IF EXISTS `pet`;
CREATE TABLE `pet`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `breed` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `color` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `weight` decimal(5, 2) NULL DEFAULT NULL,
  `health_status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `vaccination` int NULL DEFAULT 0,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `image_urls` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '上架',
  `view_count` int NULL DEFAULT 0,
  `user_id` int NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `pet_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pet
-- ----------------------------
INSERT INTO `pet` VALUES (1, '旺财', '狗', '金毛', 24, '公', '金色', 25.50, '健康', 1, '温顺乖巧的狗狗，已接种疫苗，适合家庭领养', '[\"/uploads/1.jpg\"]', '已领养', 13, 1, '2026-05-28 21:03:57');
INSERT INTO `pet` VALUES (2, '咪咪', '猫', '英短', 12, '母', '灰色', 4.20, '健康', 1, '性格温顺，喜欢粘人，已驱虫疫苗', '[\"/uploads/2026/05/28/64019ccfcf244e28ad02e1664b547c26.jpg\"]', '已领养', 4, 1, '2026-05-28 21:03:57');
INSERT INTO `pet` VALUES (3, '小白', '兔', '垂耳兔', 6, '母', '白色', 1.50, '健康', 0, '可爱的小兔子，乖巧听话', '[\"/uploads/7.jpg\"]', '下架', 21, 1, '2026-05-28 21:03:57');
INSERT INTO `pet` VALUES (5, '明明', '猫', '矮脚金渐层', 2, '母', '金黄色', 0.80, '良好', 0, '猫界“金元宝” 不挑食', '[\"/uploads/11.jpg\"]', '已领养', 13, NULL, '2026-05-30 15:45:29');
INSERT INTO `pet` VALUES (6, '面包', '猫', '未知', 12, '公', '黑色', 8.00, '健康', 1, '', '[\"/uploads/3.jpg\"]', '已领养', 4, NULL, '2026-06-08 14:13:56');
INSERT INTO `pet` VALUES (7, '可乐', '狗', '未知', 2, '母', '棕白', 3.00, '良好', 1, '安安静静的比格小狗', '[\"/uploads/6.jpg\"]', '上架', 17, NULL, '2026-06-09 10:34:13');
INSERT INTO `pet` VALUES (8, '小梨', '猫', '狸花猫', 2, '未知', '黄色', 3.00, '良好', 1, '肥肥大猫，听话', '[\"/uploads/10.jpg\"]', '上架', 2, NULL, '2026-06-14 21:15:42');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'user',
  `status` int NULL DEFAULT 1,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `email`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin@example.com', NULL, NULL, 'admin', 1, '2026-05-28 21:03:45');
INSERT INTO `user` VALUES (2, 'mimo', '96e79218965eb72c92a549dd5a330112', 'tooblue11@qq.com', '15794464799', NULL, 'user', 1, '2026-05-28 23:17:42');
INSERT INTO `user` VALUES (3, 'xu', 'e10adc3949ba59abbe56e057f20f883e', '2550631462@qq.com', NULL, NULL, 'user', 1, '2026-05-30 22:46:26');
INSERT INTO `user` VALUES (4, 'mimo1', 'e10adc3949ba59abbe56e057f20f883e', '2550631461@qq.com', NULL, NULL, 'user', 1, '2026-06-14 20:43:18');
INSERT INTO `user` VALUES (5, 'xujiawei', 'e10adc3949ba59abbe56e057f20f883e', '2550631460@qq.com', NULL, NULL, 'user', 1, '2026-06-14 21:12:57');

SET FOREIGN_KEY_CHECKS = 1;
