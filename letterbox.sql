/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : letterbox

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 29/06/2023 21:20:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_banner
-- ----------------------------
DROP TABLE IF EXISTS `t_banner`;
CREATE TABLE `t_banner` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '轮播图主键',
  `banner_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '轮播图名称',
  `detail` varchar(255) DEFAULT NULL COMMENT '轮播图描述',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '轮播图路径',
  `goto` varchar(255) DEFAULT NULL COMMENT '跳转链接',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1启用 0禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='轮播图表';

-- ----------------------------
-- Records of t_banner
-- ----------------------------
BEGIN;
INSERT INTO `t_banner` (`id`, `banner_name`, `detail`, `url`, `goto`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (1, 'banner1.png', NULL, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/banners/banner1.png', NULL, '2023-06-17 18:08:33', '2023-06-17 18:08:33', 0, 1);
INSERT INTO `t_banner` (`id`, `banner_name`, `detail`, `url`, `goto`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (2, 'banner2.png', NULL, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/banners/banner2.png', NULL, '2023-06-17 18:08:36', '2023-06-17 18:08:36', 0, 1);
INSERT INTO `t_banner` (`id`, `banner_name`, `detail`, `url`, `goto`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (3, 'banner3.png', NULL, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/banners/banner3.png', NULL, '2023-06-17 18:08:40', '2023-06-17 18:08:40', 0, 1);
INSERT INTO `t_banner` (`id`, `banner_name`, `detail`, `url`, `goto`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (4, 'banner4.png', NULL, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/banners/banner4.png', NULL, '2023-06-17 18:10:01', '2023-06-17 18:10:01', 0, 1);
INSERT INTO `t_banner` (`id`, `banner_name`, `detail`, `url`, `goto`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (5, 'banner5.png', NULL, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/banners/banner5.png', NULL, '2023-06-17 18:10:05', '2023-06-17 18:10:05', 0, 1);
INSERT INTO `t_banner` (`id`, `banner_name`, `detail`, `url`, `goto`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (6, 'banner6.png', NULL, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/banners/banner6.png', NULL, '2023-06-17 18:10:08', '2023-06-17 18:10:08', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_cart`;
CREATE TABLE `t_cart` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '购物车主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '购物车用户',
  `order_id` int NOT NULL COMMENT '订单ID',
  `good_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `count` int NOT NULL DEFAULT '1' COMMENT '数量',
  `price` int NOT NULL COMMENT '单价',
  `total_price` int NOT NULL COMMENT '总价',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1启用 0禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='购物车表';

-- ----------------------------
-- Records of t_cart
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '分类主键',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `icon` varchar(255) NOT NULL COMMENT '图标',
  `count` int NOT NULL DEFAULT '0' COMMENT '分类总量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1启用 0禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类表';

-- ----------------------------
-- Records of t_category
-- ----------------------------
BEGIN;
INSERT INTO `t_category` (`id`, `class_name`, `icon`, `count`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (1, 'paper', 'el-icon-tickets', 6, '2023-06-22 14:38:23', '2023-06-23 18:12:06', 0, 1);
INSERT INTO `t_category` (`id`, `class_name`, `icon`, `count`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (2, 'cover', 'el-icon-message', 6, '2023-06-22 14:38:52', '2023-06-22 14:38:52', 0, 1);
INSERT INTO `t_category` (`id`, `class_name`, `icon`, `count`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (3, 'ink', 'el-icon-edit', 6, '2023-06-22 14:39:07', '2023-06-22 14:39:07', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '文件主键',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件全称',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件类型',
  `size` bigint NOT NULL COMMENT '文件大小',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '下载链接',
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件内容md5',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1启用 0禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件表';

-- ----------------------------
-- Records of t_file
-- ----------------------------
BEGIN;
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (1, '史卢比1.webp', 'webp', 14, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/02966184198e4b42b0e9256aee4aff9c.webp', '8965fc63ab53a2b72c05989e3d93dfe9', '2023-06-03 21:43:52', '2023-06-03 21:43:52', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (2, '史卢比2.webp', 'webp', 11, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/2d13c744ad664494980462db3acae0f1.webp', '0be47531324fc6d6087cf32b618d80f3', '2023-06-03 21:46:40', '2023-06-03 21:46:40', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (3, '史卢比3.webp', 'webp', 9, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/175a8f58a6f64c278da33102c4f6536e.webp', '6618a6c3faf3cedf1b2b975c28d0372a', '2023-06-03 21:46:50', '2023-06-03 21:46:50', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (4, '史卢比4.webp', 'webp', 14, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/27ad270e45d34017a840a03e9139152a.webp', '824513a2486ac62de2966805d578d582', '2023-06-03 21:46:55', '2023-06-03 21:46:55', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (5, '史卢比5.webp', 'webp', 15, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/542953d2904e4863996e2f47a404f622.webp', '283a7cb5c7eaf929d9e87455a7a497f9', '2023-06-03 21:47:00', '2023-06-03 21:47:00', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (6, '迷航追踪.jpg', 'jpg', 307, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/248714eeb0854d6ea58e4c0f02483602.jpg', '0cae285ab359cce69e3b643b254ce716', '2023-06-03 21:47:16', '2023-06-03 21:47:16', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (7, '史卢比1.webp', 'webp', 14, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/02966184198e4b42b0e9256aee4aff9c.webp', '8965fc63ab53a2b72c05989e3d93dfe9', '2023-06-03 21:47:38', '2023-06-03 21:47:38', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (8, '史卢比5.webp', 'webp', 15, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/542953d2904e4863996e2f47a404f622.webp', '283a7cb5c7eaf929d9e87455a7a497f9', '2023-06-07 16:55:58', '2023-06-07 16:55:58', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (9, '白鹭logo.png', 'png', 146, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/af8112351cde45718b22fe1a5f5550ec.png', 'cb9d3fce398fa21da4a89d65c0db7600', '2023-06-18 00:33:43', '2023-06-18 00:33:43', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (10, '胶版纸.png', 'png', 87, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/3b867d3e00774ff5b30473bb6774f237.png', 'afb2e953e84fb49108f0cf3932f91069', '2023-06-22 22:52:31', '2023-06-22 22:52:31', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (11, '书写纸.png', 'png', 223, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/c4844b7a4f40413bbb1f16bec4eaec59.png', '7be822d9c388882582b541b45bc9d6ff', '2023-06-22 22:54:07', '2023-06-22 22:54:07', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (12, '轻涂纸.jpeg', 'jpeg', 12, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/7f7c68de7e4847cbb15209d584bb4fe8.jpeg', 'c28985a0584e151f35af49d0e635e287', '2023-06-22 22:57:58', '2023-06-22 22:57:58', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (13, '轻型纸.jpeg', 'jpeg', 8, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/92ca44d6f7f0400098b6f12aa0b9fe20.jpeg', 'ac27cf984ee6b7b86bbe8fbe1f12b0fc', '2023-06-22 22:58:02', '2023-06-22 22:58:02', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (14, '白卡纸.jpeg', 'jpeg', 16, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/5e53efc4ec3f457fbf5481871a8b413c.jpeg', 'cbded3f813f6496e42b9e72b48bb929b', '2023-06-22 22:59:14', '2023-06-22 22:59:14', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (15, '中式信封.jpeg', 'jpeg', 22, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/647f76550a9343efb29d46f75ee8188e.jpeg', '7566d90f4a22873d92182628170051da', '2023-06-22 23:03:04', '2023-06-22 23:03:04', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (16, '西式信封.jpeg', 'jpeg', 23, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/597ff09d4d974034b06efc2ba2dac781.jpeg', 'd2bd4d1f3475c4044baed8b0e77c16eb', '2023-06-22 23:03:05', '2023-06-22 23:03:05', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (17, '菱形信封.jpeg', 'jpeg', 46, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/893b40f37fc446a1a637c2803bf7b40c.jpeg', 'a1719c1066409011dbe17caeb0d5e735', '2023-06-22 23:03:13', '2023-06-22 23:03:13', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (18, '档案袋.jpeg', 'jpeg', 14, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/5761794a1d2a48eba0d77518bc90bc7e.jpeg', '0dd4e82c469337235877512199be6673', '2023-06-22 23:03:16', '2023-06-22 23:03:16', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (19, '开窗信封.jpeg', 'jpeg', 65, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/6472af03365a438fa16c67d446b195f6.jpeg', 'd800d08fc206c14bc92e39a21f7ccf54', '2023-06-22 23:03:18', '2023-06-22 23:03:18', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (20, 'coki.png', 'png', 8, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/3c6545c7e1744621ba6e41b31a9d91c6.png', '069f8dcc7bde82ef7adea4cbd5dd3b37', '2023-06-22 23:10:49', '2023-06-22 23:10:49', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (21, 'WCF.png', 'png', 8, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/ea9356b14e0f4faab6b4f8aab3386be5.png', 'e8f55f9d639cd4b1f1f519f416b7fa3f', '2023-06-22 23:10:51', '2023-06-22 23:10:51', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (22, 'WF.png', 'png', 9, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/df46b3ec6dca43449c97e7cea645b894.png', 'ec961e5993586e54437fc5ddfb513f38', '2023-06-22 23:10:54', '2023-06-22 23:10:54', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (23, 'willion.png', 'png', 8, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/2f396705c8964918b954c9da154d9ae5.png', '0fb0fe4980ab77a2755ab5d723064269', '2023-06-22 23:15:01', '2023-06-22 23:15:01', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (24, 'family.png', 'png', 8, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/98b5c0ba8da447eda8ef113a7554cf27.png', 'ed52594cd2304f023d99e819f773be48', '2023-06-22 23:15:04', '2023-06-22 23:15:04', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (25, 'romeobohemian.png', 'png', 8, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/a09de414ccf5438789f96d3789efbce7.png', 'c7d5dca5daebcc9c6fbd4268a22f9ae3', '2023-06-22 23:15:06', '2023-06-22 23:15:06', 0, 1);
INSERT INTO `t_file` (`id`, `file_name`, `type`, `size`, `url`, `md5`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (26, '2023.jpg', 'jpg', 24, 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/a736a960ff3f4469bca6c42c915f764b.jpg', 'bc2b5e1c1e6aefe50bc5bc277dfe64b6', '2023-06-23 17:54:28', '2023-06-23 17:54:28', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_good
-- ----------------------------
DROP TABLE IF EXISTS `t_good`;
CREATE TABLE `t_good` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '商品主键',
  `good_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '暂无描述' COMMENT '商品描述',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品类别',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品图片',
  `price` int NOT NULL DEFAULT '0' COMMENT '商品价格',
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '计量单位',
  `repository` int NOT NULL DEFAULT '0' COMMENT '商品库存',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1上架 0下架',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='信件配件表';

-- ----------------------------
-- Records of t_good
-- ----------------------------
BEGIN;
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (1, 'offset', '胶版纸', 'paper', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/3b867d3e00774ff5b30473bb6774f237.png', 0, '张', 98, '2023-06-22 14:43:13', '2023-06-23 20:17:36', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (2, 'writing', '书写纸', 'paper', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/c4844b7a4f40413bbb1f16bec4eaec59.png', 0, '张', 99, '2023-06-22 14:43:48', '2023-06-23 20:16:04', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (3, 'lightcoated', '轻涂纸', 'paper', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/7f7c68de7e4847cbb15209d584bb4fe8.jpeg', 0, '张', 99, '2023-06-22 14:44:17', '2023-06-23 20:16:07', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (4, 'light', '轻型纸', 'paper', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/92ca44d6f7f0400098b6f12aa0b9fe20.jpeg', 0, '张', 99, '2023-06-22 14:44:54', '2023-06-23 20:16:10', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (5, 'whitecardboard', '白卡纸', 'paper', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/5e53efc4ec3f457fbf5481871a8b413c.jpeg', 0, '张', 99, '2023-06-22 14:45:53', '2023-06-23 20:16:12', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (6, 'electronic', '电子版', 'paper', 'null', 0, '张', 1, '2023-06-22 14:46:42', '2023-06-22 14:46:42', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (7, 'Chinese', '中式信封', 'cover', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/647f76550a9343efb29d46f75ee8188e.jpeg', 0, '封', 49, '2023-06-22 14:47:27', '2023-06-23 20:17:36', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (8, 'Western', '西式信封', 'cover', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/597ff09d4d974034b06efc2ba2dac781.jpeg', 0, '封', 50, '2023-06-22 14:47:56', '2023-06-23 20:16:23', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (9, 'diamond', '菱形信封', 'cover', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/893b40f37fc446a1a637c2803bf7b40c.jpeg', 0, '封', 50, '2023-06-22 14:48:28', '2023-06-23 20:16:26', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (10, 'filebag', '档案袋', 'cover', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/5761794a1d2a48eba0d77518bc90bc7e.jpeg', 0, '封', 50, '2023-06-22 14:48:57', '2023-06-23 20:16:30', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (11, 'window', '开窗信封', 'cover', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/6472af03365a438fa16c67d446b195f6.jpeg', 0, '封', 50, '2023-06-22 14:49:27', '2023-06-23 20:16:34', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (12, 'electroenve', '电子信封', 'cover', 'null', 0, '封', 1, '2023-06-22 14:50:30', '2023-06-22 14:50:30', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (13, 'zh_coki', 'coki字体', 'ink', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/3c6545c7e1744621ba6e41b31a9d91c6.png', 0, '-', 1, '2023-06-22 14:51:41', '2023-06-22 14:51:41', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (14, 'zh_WCF', 'WCF字体', 'ink', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/ea9356b14e0f4faab6b4f8aab3386be5.png', 0, '-', 1, '2023-06-22 14:51:58', '2023-06-22 14:51:58', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (15, 'zh_WF', 'WF字体', 'ink', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/df46b3ec6dca43449c97e7cea645b894.png', 0, '-', 1, '2023-06-22 14:52:17', '2023-06-22 14:52:17', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (16, 'en_willion', 'willion字体', 'ink', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/2f396705c8964918b954c9da154d9ae5.png', 0, '-', 1, '2023-06-22 14:52:44', '2023-06-22 14:52:44', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (17, 'en_family', 'family字体', 'ink', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/98b5c0ba8da447eda8ef113a7554cf27.png', 0, '-', 1, '2023-06-22 14:53:03', '2023-06-22 14:53:03', 0, 1);
INSERT INTO `t_good` (`id`, `good_name`, `detail`, `class_name`, `image_url`, `price`, `unit`, `repository`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (18, 'en_romeobohemian', 'rome字体', 'ink', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/a09de414ccf5438789f96d3789efbce7.png', 0, '-', 1, '2023-06-22 14:53:28', '2023-06-22 14:53:28', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_letter
-- ----------------------------
DROP TABLE IF EXISTS `t_letter`;
CREATE TABLE `t_letter` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '信件主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件作者',
  `call_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '联系电话',
  `receiver` varchar(255) NOT NULL COMMENT '收件方',
  `receiver_phone` varchar(255) NOT NULL COMMENT '收件电话',
  `from_address` varchar(255) NOT NULL COMMENT '信件来源',
  `to_address` varchar(255) NOT NULL COMMENT '信件地址',
  `postal_code` varchar(255) NOT NULL COMMENT '邮编',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件内容',
  `url` varchar(255) DEFAULT NULL COMMENT '文件下载地址',
  `paper_id` int DEFAULT NULL COMMENT '信纸ID 1胶版纸 2书写纸 3轻涂纸 4轻型纸 5白卡纸 6电子版',
  `cover_id` int DEFAULT NULL COMMENT '信封ID 1中式信封 2西式信封 3菱形信封 4档案袋 5开窗信封 6电子信封\n',
  `ink_id` int DEFAULT NULL COMMENT '墨水ID',
  `order_id` int DEFAULT NULL COMMENT '订单ID',
  `status` int DEFAULT NULL COMMENT '默认0未支付 1已支付 2未发出 3已发出 4已退回 5已收到',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1启用 0禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='信件表';

-- ----------------------------
-- Records of t_letter
-- ----------------------------
BEGIN;
INSERT INTO `t_letter` (`id`, `username`, `call_phone`, `receiver`, `receiver_phone`, `from_address`, `to_address`, `postal_code`, `context`, `url`, `paper_id`, `cover_id`, `ink_id`, `order_id`, `status`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (1, 'admin', '17785939239', 'aaa', '1234567890', 'test', 'test', '20185807', '之一：常常在月光之下，独自在林中水边踏着绿茵，呼吸浓烈的草香与泥士味，或是借此舒散苦闷，或是沉思默想，过度的室内生活与书斋生活恰恰是造成现代知识分子神经紧张与病态的原因，而萧然意远，旷达恬\n静，不滞于物，不凝于心的境界只有从自然中获得，你总是不能否认吧？', NULL, 1, 1, 13, NULL, 2, '2023-06-04 12:18:46', '2023-06-04 12:18:46', 0, 1);
INSERT INTO `t_letter` (`id`, `username`, `call_phone`, `receiver`, `receiver_phone`, `from_address`, `to_address`, `postal_code`, `context`, `url`, `paper_id`, `cover_id`, `ink_id`, `order_id`, `status`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (2, 'admin', '17785939239', 'bbb', '1234567890', 'test', 'test', '20185807', '之二：一个人的思想不动笔就不再会有系统，日子久了，也就放过去了，甚至于忘了，岂不可惜，就为这个缘故，我常常逼你多写信，这也是很重要的：理性认识的训练。我屡次要你生活正规化，学习规范化，不正规如何能持久？不持久如何能有成绩？ 如何能巩固已有的成绩？而且对作品的了解与掌握，就需要长期的慢\n慢消化、咀嚼、吸收•', NULL, 1, 2, 14, NULL, 2, '2023-06-04 12:19:41', '2023-06-04 12:19:41', 0, 1);
INSERT INTO `t_letter` (`id`, `username`, `call_phone`, `receiver`, `receiver_phone`, `from_address`, `to_address`, `postal_code`, `context`, `url`, `paper_id`, `cover_id`, `ink_id`, `order_id`, `status`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (3, 'admin', '17785939239', 'ccc', '1234567890', 'test', 'test', '12345678', '你若安好，便是晴天，你若不安好，便是雨天', NULL, 2, 3, 15, NULL, 2, '2023-06-04 21:39:02', '2023-06-04 21:39:02', 0, 1);
INSERT INTO `t_letter` (`id`, `username`, `call_phone`, `receiver`, `receiver_phone`, `from_address`, `to_address`, `postal_code`, `context`, `url`, `paper_id`, `cover_id`, `ink_id`, `order_id`, `status`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (4, 'admin', '17785939239', 'ddd', '1234567890', 'test', 'test', '12345678', 'abcdefghijklmnopqrstuvwxyz', NULL, 3, 4, 16, NULL, 2, '2023-06-04 21:40:23', '2023-06-04 21:40:23', 0, 1);
INSERT INTO `t_letter` (`id`, `username`, `call_phone`, `receiver`, `receiver_phone`, `from_address`, `to_address`, `postal_code`, `context`, `url`, `paper_id`, `cover_id`, `ink_id`, `order_id`, `status`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (5, 'admin', '17785939239', 'eee', '1234567890', 'test', 'test', '12345678', 'adfadsfsdgdfghgfjhfgjf\nsdasgfsdgdfhgfdjhfghjf\ndafdsgfdhdgfhfghjfhgjfhg', NULL, 4, 5, 17, NULL, 2, '2023-06-04 21:42:14', '2023-06-04 21:42:14', 0, 1);
INSERT INTO `t_letter` (`id`, `username`, `call_phone`, `receiver`, `receiver_phone`, `from_address`, `to_address`, `postal_code`, `context`, `url`, `paper_id`, `cover_id`, `ink_id`, `order_id`, `status`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (6, 'admin', '17785939239', 'spider man', '1234567890', '重庆大学A区', 'Anywhere else', '12345678', 'The Spider-Man: Into The Spider-Verse;\nThe Spider-Man: Across The Spider-Verse;\nThe Amazing Spider Man;', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/texts/24b55f9298eb4fb0bfeb11b1a020976f.txt', 5, 6, 18, NULL, 2, '2023-06-05 15:05:09', '2023-06-05 15:05:09', 0, 1);
INSERT INTO `t_letter` (`id`, `username`, `call_phone`, `receiver`, `receiver_phone`, `from_address`, `to_address`, `postal_code`, `context`, `url`, `paper_id`, `cover_id`, `ink_id`, `order_id`, `status`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (7, 'admin', '17785939239', 'admin', '17785939239', '重庆沙坪坝区', 'test', '12345678', 'this is a test message about the letter uploading, designed by bailu from chongqing, China', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/texts/d2d93975d5114e28a97984f64e3ced07.txt', 6, 6, 0, NULL, 2, '2023-06-12 23:38:42', '2023-06-12 23:38:42', 0, 1);
INSERT INTO `t_letter` (`id`, `username`, `call_phone`, `receiver`, `receiver_phone`, `from_address`, `to_address`, `postal_code`, `context`, `url`, `paper_id`, `cover_id`, `ink_id`, `order_id`, `status`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (8, 'admin', '17785939239', 'admin', '123456789', '重庆沙坪坝区', 'test', '12345678', 'hello java test about the repository decrease!', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/texts/2cfe450e411f4a1eb18ab08cde2d1cc7.txt', 1, 7, 17, NULL, 2, '2023-06-23 20:17:36', '2023-06-23 20:17:36', 0, 1);
INSERT INTO `t_letter` (`id`, `username`, `call_phone`, `receiver`, `receiver_phone`, `from_address`, `to_address`, `postal_code`, `context`, `url`, `paper_id`, `cover_id`, `ink_id`, `order_id`, `status`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (9, 'admin', '17785939239', 'admin', '1778593239', '重庆沙坪坝区', 'test', '12345678', 'hello java test about the repository decrease!', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/texts/8b9c522730da41d39db03970a3dfffde.txt', 6, 12, 17, NULL, 2, '2023-06-23 20:18:49', '2023-06-23 20:18:49', 0, 1);
INSERT INTO `t_letter` (`id`, `username`, `call_phone`, `receiver`, `receiver_phone`, `from_address`, `to_address`, `postal_code`, `context`, `url`, `paper_id`, `cover_id`, `ink_id`, `order_id`, `status`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (10, 'jdxy', '17785939239', 'admin', '17785939239', '重庆沙区', 'admin', '12345678', 'jdxy write this to the admin', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/texts/b9170714cf0b44caad268d6ec9156d68.txt', 6, 12, 17, NULL, 2, '2023-06-23 20:25:38', '2023-06-23 20:25:38', 0, 1);
INSERT INTO `t_letter` (`id`, `username`, `call_phone`, `receiver`, `receiver_phone`, `from_address`, `to_address`, `postal_code`, `context`, `url`, `paper_id`, `cover_id`, `ink_id`, `order_id`, `status`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (11, 'admin', '17785939239', 'admin', '17785939239', '重庆沙坪坝区', 'test', '123456', 'hello my name is dxy', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/texts/b7bc0049cd2e4f7f9e52321fcf220e07.txt', 6, 12, 16, NULL, 2, '2023-06-23 21:58:04', '2023-06-23 21:58:04', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `menu_name` varchar(255) DEFAULT NULL COMMENT '菜单名',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '访问路径',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '组件路径',
  `perms` varchar(255) DEFAULT NULL COMMENT '访问权限',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `pid` int DEFAULT NULL COMMENT '父级id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1启用 0禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单表';

-- ----------------------------
-- Records of t_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (1, '首页管理', NULL, NULL, NULL, 'el-icon-house', '主页', NULL, '2023-05-17 11:05:25', '2023-06-23 22:25:48', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (2, '系统管理', NULL, NULL, NULL, 'el-icon-menu', '系统管理', NULL, '2023-05-17 11:05:41', '2023-05-22 23:14:54', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (3, '文件管理', NULL, NULL, NULL, 'el-icon-document', '文件管理', NULL, '2023-05-17 11:06:11', '2023-05-22 23:15:01', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (4, '业务管理', NULL, NULL, NULL, 'el-icon-s-shop', '业务管理', NULL, '2023-05-17 11:06:18', '2023-05-22 23:15:12', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (5, '设置', 'setting', 'settingview/SettingView', 'sys:set:adm', 'el-icon-setting', '设置', 1, '2023-05-17 11:06:23', '2023-05-17 11:06:23', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (6, '主页', 'home', 'chartview/HomeView', 'sys:chart:adm', 'el-icon-data-line', '图表', 1, '2023-05-17 11:07:05', '2023-06-19 23:20:03', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (7, '用户管理', 'sys/user', 'systemview/UserView', 'sys:user:adm', 'el-icon-s-custom', '用户管理', 2, '2023-05-17 11:07:48', '2023-05-17 11:07:48', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (8, '个人设置', 'sys/admin', 'systemview/AdminView', 'sys:user:self', 'el-icon-user-solid', '个人信息设置', 2, '2023-05-17 11:08:28', '2023-05-17 11:08:28', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (9, '权限管理', 'sys/role', 'systemview/RoleView', 'sys:role:adm', 'el-icon-s-check', '角色权限管理', 2, '2023-05-17 11:08:38', '2023-05-17 11:08:38', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (10, '菜单管理', 'sys/menu', 'systemview/MenuView', 'sys:menu:adm', 'el-icon-s-grid', '菜单管理', 2, '2023-05-17 11:09:22', '2023-05-17 11:09:22', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (11, '图片管理', 'file/pic', 'fileview/PictureView', 'sys:file:pic', 'el-icon-picture', '图片管理', 3, '2023-05-17 11:10:30', '2023-05-17 11:10:30', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (12, '文本管理', 'file/text', 'fileview/TextView', 'sys:file:txt', 'el-icon-notebook-1', '文本信件管理', 3, '2023-05-17 11:10:42', '2023-05-17 11:10:42', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (13, '轮播图', 'shop/banner', 'shopview/BannerView', 'sys:banner:adm', 'el-icon-view', '轮播图管理', 4, '2023-06-12 23:41:02', '2023-06-12 23:41:15', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (14, '写信', 'shop/writing', 'shopview/WritingView', 'sys:letter:w', 'el-icon-message', '写信功能', 4, '2023-06-12 23:41:56', '2023-06-22 14:16:37', 0, 1);
INSERT INTO `t_menu` (`id`, `menu_name`, `path`, `component`, `perms`, `icon`, `description`, `pid`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (15, '库存管理', 'shop/category', 'shopview/CategoryView', 'sys:good:adm', 'el-icon-s-cooperation', '库存管理', 4, '2023-06-23 18:14:46', '2023-06-23 18:14:46', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单用户',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单地址',
  `total_price` int NOT NULL COMMENT '订单总价',
  `status` int NOT NULL DEFAULT '0' COMMENT '默认0未支付 1已支付 2已出库 3已完成 4已退回',
  `expire_time` int NOT NULL DEFAULT '24' COMMENT '24小时超时',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1有效 0超时',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';

-- ----------------------------
-- Records of t_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色主键',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名',
  `level` int NOT NULL DEFAULT '0' COMMENT '等级',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1启用 0禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
BEGIN;
INSERT INTO `t_role` (`id`, `role_name`, `level`, `description`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (1, 'ROLE_SUPER', 1, '超级管理员', '2023-05-15 22:43:26', '2023-05-23 22:45:11', 0, 1);
INSERT INTO `t_role` (`id`, `role_name`, `level`, `description`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (2, 'ROLE_USER', 2, '用户管理员', '2023-05-15 22:43:34', '2023-05-22 15:48:22', 0, 1);
INSERT INTO `t_role` (`id`, `role_name`, `level`, `description`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (3, 'ROLE_ADMIN', 2, '权限管理员', '2023-05-22 15:37:32', '2023-05-22 15:48:22', 0, 1);
INSERT INTO `t_role` (`id`, `role_name`, `level`, `description`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (4, 'ROLE_COMMON', 3, '普通用户', '2023-05-15 22:44:19', '2023-05-22 15:48:22', 0, 1);
INSERT INTO `t_role` (`id`, `role_name`, `level`, `description`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (5, 'ROLE_FILE', 2, '文件管理员', '2023-05-23 22:09:58', '2023-05-23 22:09:58', 0, 1);
INSERT INTO `t_role` (`id`, `role_name`, `level`, `description`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (6, 'ROLE_SHOP', 2, '业务管理员', '2023-06-17 23:49:45', '2023-06-17 23:50:04', 0, 1);
INSERT INTO `t_role` (`id`, `role_name`, `level`, `description`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (7, 'ROLE_GOOD', 2, '库存管理员', '2023-06-23 18:17:45', '2023-06-23 18:17:45', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色菜单主角',
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `menu_id` int DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色菜单关系表';

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (126, 5, 8);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (127, 5, 3);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (128, 5, 11);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (129, 5, 12);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (130, 5, 13);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (134, 6, 1);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (135, 6, 5);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (136, 6, 6);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (137, 6, 8);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (138, 6, 4);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (139, 6, 13);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (140, 6, 14);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (144, 1, 1);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (145, 1, 5);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (146, 1, 6);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (147, 1, 2);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (148, 1, 7);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (149, 1, 8);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (150, 1, 9);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (151, 1, 10);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (152, 1, 3);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (153, 1, 11);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (154, 1, 12);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (155, 1, 4);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (156, 1, 13);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (157, 1, 14);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (158, 1, 15);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (159, 7, 1);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (160, 7, 5);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (161, 7, 6);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (162, 7, 15);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (163, 4, 6);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (164, 4, 8);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (165, 4, 12);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (166, 4, 14);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (167, 2, 1);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (168, 2, 5);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (169, 2, 6);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (170, 2, 2);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (171, 2, 7);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (172, 2, 8);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (173, 2, 9);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (174, 2, 10);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (175, 2, 14);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (176, 3, 2);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (177, 3, 7);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (178, 3, 8);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (179, 3, 9);
INSERT INTO `t_role_menu` (`id`, `role_id`, `menu_id`) VALUES (180, 3, 10);
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0未删除 1已删除',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 启用 0 禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `role_name`, `avatar`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (1, 'admin', '$2a$10$3XLIoAVDd6EYp0cIz5nq8.ju8AMhR/0hJcNE5G4xvmDMd2CBFAnVG', '管理员', '123456@qq.com', '17785939239', '重庆沙坪坝区', 'ROLE_SUPER', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/02966184198e4b42b0e9256aee4aff9c.webp', '2023-05-09 19:22:32', '2023-06-03 21:47:40', 0, 1);
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `role_name`, `avatar`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (2, 'bailu', '$2a$10$Ffk.HxyCWTN4QpAMlOHNT.vybxNvOrgadC98AWDuZt/fzdfAnEBpG', '白鹭', '123456@qq.com', '17785939239', '重庆沙区', 'ROLE_SUPER', 'http://localhost:9090/file/download/067a5f331f8b4f9183363c1520ac1b32.webp', '2023-05-21 22:27:23', '2023-06-27 21:20:31', 0, 1);
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `role_name`, `avatar`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (3, 'useradmin', '$2a$10$r6VOJNH1aBNAqio3E6X0OuMvEOVNLuUTk0VDExjlxJZw.5jUG8Ugq', '用户管理员', '123456@qq.com', '222222', '重庆沙区', 'ROLE_USER', NULL, '2023-05-21 22:27:50', '2023-05-24 21:24:43', 0, 1);
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `role_name`, `avatar`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (4, 'roleadmin', '$2a$10$erjEfR5qk80FA20bYYfBA.idnDBKyHzpLcxjpeZn3PVuXGOCee04C', '权限管理员', '123456@qq.com', '333333', '重庆沙区', 'ROLE_ADMIN', NULL, '2023-05-21 22:28:09', '2023-05-24 21:24:46', 0, 1);
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `role_name`, `avatar`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (5, 'jdxy', '$2a$10$9Op0eXFgJM6LsCbzWVZUMOJumzf22Oihk9ZrM7ybV1OJYjMTSzQom', '普通用户', '1242857354@qq.com', '17785939239', '重庆沙区', 'ROLE_COMMON', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/af8112351cde45718b22fe1a5f5550ec.png', '2023-05-21 22:28:35', '2023-06-18 00:33:44', 0, 1);
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `role_name`, `avatar`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (6, 'fileadmin', '$2a$10$.Yld/mwB4J/s/K/M86zKHezOwrMVHB0Ef1/buW6ChJfzCMZEOj5kO', '文件管理员', '123456@qq.com', '555555', '重庆沙区', 'ROLE_FILE', NULL, '2023-05-21 22:30:19', '2023-05-24 21:24:51', 0, 1);
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `role_name`, `avatar`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (7, 'test5', '$2a$10$Mepwnj8ivItAiPmF.4ILxOob3E80EoV92YIohpZMjQHIMx2pvSFPi', 't5', '123456@qq.com', '666666', '重庆沙区', 'ROLE_SHOP', NULL, '2023-05-21 22:31:03', '2023-06-23 20:41:45', 0, 1);
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `role_name`, `avatar`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (8, 'shopadmin', '$2a$10$KNZsdJfgDpd/6vNiiBpDPe1k6L7j4iMaLFqBo2yDUoqxNaxzAq0uC', '业务管理员', '123456@qq.com', '777777', '重庆沙区', 'ROLE_SHOP', NULL, '2023-05-21 22:31:24', '2023-06-17 23:57:23', 0, 1);
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `role_name`, `avatar`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (9, 'ddyyyds', '$2a$10$iajI7b6Qg0EaF3mjZdzRXeeO4LRJfqqpBqRNs1FPObQe28WcxiacS', '董大勇yyds', '123456@qq.com', '123456', '重庆大学B区八舍', 'ROLE_COMMON', 'http://localhost:9090/file/download/3e1595254cae417ea93818a0741e2ae9.webp', '2023-05-23 14:54:58', '2023-05-24 21:24:57', 0, 1);
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `role_name`, `avatar`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (10, 'admin1', '$2a$10$5X2NtZAYpeIg3QeO/G6TdOjloG1KGVzTtjDJq7tvhDluhLqMIDRK.', 'admin1', NULL, NULL, NULL, 'ROLE_USER', 'https://letterbox-beta.oss-cn-chengdu.aliyuncs.com/imgs/542953d2904e4863996e2f47a404f622.webp', '2023-06-07 16:43:36', '2023-06-07 16:56:01', 0, 1);
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `role_name`, `avatar`, `create_time`, `update_time`, `deleted`, `enable`) VALUES (11, 'adminCO', '$2a$10$nqTirkGbUaBfJHDDI.hHTOoALkmj5eOG3H8VmdfHZEm6r5h6RzWbm', '普通用户', NULL, NULL, NULL, 'ROLE_COMMON', NULL, '2023-06-19 23:15:27', '2023-06-19 23:15:27', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户角色主键',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `role_id` int DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关系表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
BEGIN;
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES (1, 1, 1);
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES (2, 2, 1);
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES (3, 3, 2);
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES (4, 4, 3);
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES (5, 5, 4);
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES (6, 6, 5);
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES (7, 9, 4);
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES (9, 10, 2);
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES (10, 8, 6);
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES (11, 11, 4);
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES (12, 7, 6);
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`) VALUES (13, 12, 4);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
