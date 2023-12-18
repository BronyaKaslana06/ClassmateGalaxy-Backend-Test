/*
 Navicat Premium Data Transfer

 Source Server         : se2023
 Source Server Type    : MySQL
 Source Server Version : 100328
 Source Host           : 43.133.192.56:3306
 Source Schema         : classmate_galaxy

 Target Server Type    : MySQL
 Target Server Version : 100328
 File Encoding         : 65001

 Date: 01/12/2023 00:36:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` int NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `time` timestamp NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '评论时间',
  `like_num` int NULL DEFAULT NULL COMMENT '点赞数',
  `parent_id` int NULL DEFAULT NULL COMMENT '回复的父评论id，无父评论则为Null',
  `root_parent` int NULL DEFAULT NULL COMMENT '最顶层父评论的id，若本身为最顶层则为Null',
  `post_id` int NULL DEFAULT NULL COMMENT '外键，评论所属的帖子id',
  `user_id` int NULL DEFAULT NULL COMMENT '外键，评论者的id',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `post_id`(`post_id` ASC) USING BTREE,
  CONSTRAINT `post_id` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for planet
-- ----------------------------
DROP TABLE IF EXISTS `planet`;
CREATE TABLE `planet`  (
  `planet_id` int NOT NULL AUTO_INCREMENT COMMENT '星球id',
  `planet_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '星球名',
  `member_num` int NULL DEFAULT NULL COMMENT '星球成员数',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '星球简介',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '星球图片',
  `owner_id` int NULL DEFAULT NULL COMMENT '外键，星球拥有者id',
  PRIMARY KEY (`planet_id`) USING BTREE,
  INDEX `owner_id_planet`(`owner_id` ASC) USING BTREE,
  CONSTRAINT `owner_id_planet` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of planet
-- ----------------------------

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `post_id` int NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '帖子标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '帖子内容',
  `like_num` int NULL DEFAULT NULL COMMENT '点赞数',
  `collect_num` int NULL DEFAULT NULL COMMENT '收藏数',
  `view_num` int NULL DEFAULT NULL COMMENT '浏览数',
  `photo_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '帖子图片url',
  `visibility` int NULL DEFAULT NULL COMMENT '帖子的可见性。1为仅所属星球内人员可见。2为仅关注人员可看。3为1、2均可见。4为仅自己可见。',
  `publish_time` timestamp NOT NULL DEFAULT current_timestamp COMMENT '发布时间',
  `alter_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `planet_id` int NULL DEFAULT NULL COMMENT '外键，星球id',
  `author_id` int NULL DEFAULT NULL COMMENT '外键，帖子作者id',
  PRIMARY KEY (`post_id`) USING BTREE,
  INDEX `planet_id`(`planet_id` ASC) USING BTREE,
  INDEX `author_id`(`author_id` ASC) USING BTREE,
  CONSTRAINT `author_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `planet_id` FOREIGN KEY (`planet_id`) REFERENCES `planet` (`planet_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `head_photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_follows
-- ----------------------------
DROP TABLE IF EXISTS `user_follows`;
CREATE TABLE `user_follows`  (
  `follow_id` int NOT NULL AUTO_INCREMENT COMMENT '关注关系的唯一标识符',
  `follower_id` int NULL DEFAULT NULL COMMENT '关注者id',
  `following_id` int NULL DEFAULT NULL COMMENT '被关注者id',
  `follow_time` timestamp NULL DEFAULT current_timestamp COMMENT '关注时间',
  `status` int NULL DEFAULT NULL COMMENT '表示关注状态，1表示关注中，0表示取消关注',
  PRIMARY KEY (`follow_id`) USING BTREE,
  INDEX `follower_id`(`follower_id` ASC) USING BTREE,
  INDEX `following_id`(`following_id` ASC) USING BTREE,
  CONSTRAINT `follower_id` FOREIGN KEY (`follower_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `following_id` FOREIGN KEY (`following_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_follows
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
