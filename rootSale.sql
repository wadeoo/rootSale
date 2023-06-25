/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50553 (5.5.53)
 Source Host           : localhost:3306
 Source Schema         : rootsale

 Target Server Type    : MySQL
 Target Server Version : 50553 (5.5.53)
 File Encoding         : 65001

 Date: 14/06/2023 15:32:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for artwork
-- ----------------------------
DROP TABLE IF EXISTS `artwork`;
CREATE TABLE `artwork`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `details` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of artwork
-- ----------------------------
INSERT INTO `artwork` VALUES (1, '雄鹰翱翔', 'http://192.168.43.242:8080/artwork/1/image', '这件木雕以栩栩如生的雄鹰展翅高飞为主题，传递着自由和力量的象征。', '手工雕刻而成的雄鹰木雕，栩栩如生地展现了鹰的肌肉线条和羽毛细节。其优雅的姿态和凌空飞翔的动态给人一种强大和自由的感觉。雕塑使用优质的红木，经过精心打磨和上光，展现出木材天然的纹理和光泽。', 5000.00);
INSERT INTO `artwork` VALUES (2, '莲花的舞蹈', 'http://192.168.43.242:8080/artwork/2/image', '高雅和纯洁的象征，展现婀娜多姿的莲花。', '精雕细琢的莲花木雕，木材纹理与色彩相得益彰。', 3800.00);
INSERT INTO `artwork` VALUES (3, '古韵山水', 'http://192.168.43.242:8080/artwork/3/image', '展现江南水乡的柔美和古韵，仿佛一幅山水画。', '红松木材质，细腻的雕刻勾勒出起伏的山峦和江水。', 6500.00);
INSERT INTO `artwork` VALUES (4, '麒麟瑞兽', 'http://192.168.43.242:8080/artwork/4/image', '吉祥和幸福的象征，栩栩如生的麒麟形象。', '神话传说中的麒麟雕刻，带有古典神秘的氛围。', 7200.00);
INSERT INTO `artwork` VALUES (5, '龙腾九天', 'http://192.168.43.242:8080/artwork/5/image', '展现中国传统文化中神秘龙的力量和威严。', '以精心雕刻的龙形象为主题的木雕作品，栩栩如生的细节和雕塑师的巧手。', 9800.00);
INSERT INTO `artwork` VALUES (6, '梅花绽放', 'http://192.168.43.242:8080/artwork/6/image', '寓意坚韧和希望，展现绽放的梅花之美。', '木质梅花雕刻，表现出细腻的花瓣纹理和自然生长的形态。', 4500.00);
INSERT INTO `artwork` VALUES (7, '佛光普照', 'http://192.168.43.242:8080/artwork/7/image', '展现佛教文化中的智慧和祥和。', '佛陀形象的木雕作品，细致的表情和手势传达出智慧和慈悲。', 8200.00);
INSERT INTO `artwork` VALUES (8, '鱼跃龙门', 'http://192.168.43.242:8080/artwork/8/image', '象征着飞跃和成功的意象，展现鱼儿腾跃之美。', '栩栩如生的鱼跃雕塑，捕捉到鱼儿跃出水面的瞬间。', 6700.00);
INSERT INTO `artwork` VALUES (9, '山松雅韵', 'http://192.168.43.242:8080/artwork/9/image', '表达山水之间的静谧与恬淡。', '红松木雕刻而成的山水作品，展现出层峦叠嶂和飘逸的松树。', 5900.00);

-- ----------------------------
-- Table structure for orda
-- ----------------------------
DROP TABLE IF EXISTS `orda`;
CREATE TABLE `orda`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `artwork_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `artwork_image_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `quantity` int(11) NULL DEFAULT NULL,
  `total_price` decimal(10, 2) NULL DEFAULT NULL,
  `is_cart` int(11) NULL DEFAULT NULL,
  `order_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orda
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '123', '123', '123');

SET FOREIGN_KEY_CHECKS = 1;
