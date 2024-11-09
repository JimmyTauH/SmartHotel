/*
 Navicat Premium Dump SQL

 Source Server         : Tencent
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39-0ubuntu0.22.04.1)
 Source Host           : 42.194.239.205:3306
 Source Schema         : hotelms4v2

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39-0ubuntu0.22.04.1)
 File Encoding         : 65001

 Date: 09/11/2024 17:05:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '竞赛名称',
  `descr` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '竞赛简介',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '竞赛内容',
  `applystart` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '报名开始时间',
  `applyend` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '报名结束时间',
  `form` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '竞赛形式',
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主办方',
  `number` int NULL DEFAULT 0 COMMENT '剩余房型',
  `read_count` int NULL DEFAULT 0 COMMENT '浏览量',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `hotelID` int NULL DEFAULT NULL COMMENT '关联酒店',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '竞赛' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (30, '大床房（小冰箱+智能客控）', '20-22平方米\n1张大床1.8米\n加床费用：该房型不可加床\n1-6楼', '<h2>全部设施</h2><ul><li><h3>清洁服务</h3><p><i></i>每日打扫、打扫工具、洗衣用品</p><p><i></i>熨衣设备、熨裤机</p></li><li><h3>洗浴用品</h3><p><i></i>牙刷、牙膏</p><p><i></i>沐浴露、洗发水护发素、香皂、浴帽、梳子、剃须刀</p></li></ul>', NULL, NULL, NULL, '150（特价）', 3, 1, 'http://localhost:9091/files/1730864860245-ee08bb96-498d-4cf7-aa5b-3e437813d7f2.png', 12);
INSERT INTO `activity` VALUES (31, '高级大床房（小冰箱+智能客控）', '\n25平方米\n1张大床1.8米\n加床费用：该房型不可加床\n1-5楼', '<h2>全部设施</h2><h3>清洁服务</h3><h2><ul><li><p><i></i>每日打扫、打扫工具、洗衣用品</p><p><i></i>熨衣设备、熨裤机</p></li></ul></h2><h3>洗浴用品</h3><h2><ul><li><p><i></i>牙刷、牙膏</p><p><i></i>沐浴露、洗发水护发素、香皂、浴帽、梳子、剃须刀</p></li></ul></h2>', NULL, NULL, NULL, '200', 3, 0, 'http://localhost:9091/files/1730865051507-82271b9a-d3cb-45fd-aca2-b8288fe397c0.png', 12);
INSERT INTO `activity` VALUES (32, '高级双床房（小冰箱+智能客控）', '29平方米\n2张单人床1.2米\n加床费用：该房型不可加床\n2-5楼', '<h2>全部设施</h2><h3>清洁服务</h3><h2><ul><li><p><i></i>每日打扫、打扫工具、洗衣用品</p><p><i></i>熨衣设备、熨裤机</p></li></ul></h2><h3>洗浴用品</h3><h2><ul><li><p><i></i>牙刷、牙膏</p><p><i></i>沐浴露、洗发水护发素、香皂、浴帽、梳子、剃须刀</p></li></ul></h2>', '2024-11-12', '2024-11-23', '携程', '250（特价）', 4, 92, 'http://localhost:9091/files/1730865709540-c6c03219-5b32-4b89-b5ae-ae99283cafc9.png', 12);

-- ----------------------------
-- Table structure for activity_sign
-- ----------------------------
DROP TABLE IF EXISTS `activity_sign`;
CREATE TABLE `activity_sign`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `activity_id` int NULL DEFAULT NULL COMMENT '竞赛id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '报名时间',
  `start_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `end_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '竞赛报名表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of activity_sign
-- ----------------------------
INSERT INTO `activity_sign` VALUES (1, 1, 14, '2024-06-10 12:59:44', NULL, NULL);
INSERT INTO `activity_sign` VALUES (2, 1, 7, '2024-06-10 16:24:08', NULL, NULL);
INSERT INTO `activity_sign` VALUES (3, 27, 1, '2024-11-03 22:30:16', NULL, NULL);
INSERT INTO `activity_sign` VALUES (5, 29, 7, '2024-11-04 08:10:37', NULL, NULL);
INSERT INTO `activity_sign` VALUES (8, 32, 7, '2024-11-07 23:33:57', '2024-11-12', '2024-11-23');
INSERT INTO `activity_sign` VALUES (9, 30, 7, '2024-11-06 23:33:57', '2024-11-12', '2024-11-23');
INSERT INTO `activity_sign` VALUES (10, 30, 7, '2024-11-05 23:33:57', '2024-11-12', '2024-11-23');

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin', '管理员', 'http://localhost:9091/files/1717861781556-img5.png', 'ADMIN', '13677889922', 'admin11@xm.com');

-- ----------------------------
-- Table structure for admin_recep
-- ----------------------------
DROP TABLE IF EXISTS `admin_recep`;
CREATE TABLE `admin_recep`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `hotelid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_recep
-- ----------------------------
INSERT INTO `admin_recep` VALUES (1, 'recep', 'recep', '服务员', 'http://localhost:9091/files/1717861781556-img5.png', 'RECEP', '13677889922', 'test@xm.com', 4);

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `descr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '简介',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签',
  `user_id` int NULL DEFAULT NULL COMMENT '发布人ID',
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发布日期',
  `read_count` int NULL DEFAULT 0 COMMENT '浏览量',
  `category_id` int NULL DEFAULT NULL COMMENT '分类ID',
  `contest_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '博客信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (7, '际庭酒店式公寓', '<p>公寓地理位置优越，位于杨浦区杨树浦路2300号交通便利，附近商场（百联滨江购物中心，宝龙旭辉广场，奥特莱斯，等大型商场）直线距离不远，附近配套设施齐舍，给商务洽谈、旅游观光等八方来宾带来极大的便利。酒店有欢聚高级双床房、欢享舒适大床房、欢喜特惠大床房、欢享大床房、欢享高级大床房，欢悦大床房、多种房型供您选择，彰显尊荣地位，轻享房如居家般随兴，客房设计温馨舒适，配备高品质的床品和设施，是家的感觉，又胜过家的感觉，带来不期而遇的温暖和方寸之间的美感，民俗与高雅共生，传统与现代并存。化繁为简，怡然栖居。四方食事，人间烟火，这里有丰富多样的设施与服务，包括餐厅、会议室、接待室等，内部配备网络系统、投影仪，可为您提供不同规格的会议接待、商务谈判、会晤接见及私人会谈、演示和签约仪式等。熠熠生辉、恢宏从容，您尽管放松心境，尽情享受。<br/></p>', '酒店有欢聚高级双床房、欢享舒适大床房、欢喜特惠大床房、欢享大床房、欢享高级大床房，欢悦大床房、多种房型供您选择，彰显尊荣地位，轻享房如居家般随兴，客房设计温馨舒适，配备高品质的床品和设施，是家的感觉，又胜过家的感觉，带来不期而遇的温暖和方寸之间的美感', NULL, 'http://localhost:9091/files/1728616308666-2.png', '[\"大床房\",\"双床房\",\"电竞游戏房\",\"3-4人房\",\"浪漫情侣房\",\"温馨亲子房\"]', 5, '2024-10-11', 101, 2, 24);
INSERT INTO `blog` VALUES (8, '崇明漫墅美郡民宿', '<p>漫墅----慢生活，漫情怀。<br/>美丽的崇明，静享一片悠闲时光。<br/>漫墅为您营造一篇音乐、书与咖啡交融的主题乐章，1000多平的大草坪，可露营，可烧烤，远离尘世的喧嚣，静享下午温暖的阳光。<br/>跨过长江隧桥，下高速左转，沿着美丽的陈彷公路，行驶约2公里，即到达漫墅美郡。<br/></p>', '漫墅----慢生活，漫情怀。\n美丽的崇明，静享一片悠闲时光。\n漫墅为您营造一篇音乐、书与咖啡交融的主题乐章，1000多平的大草坪，可露营，可烧烤，远离尘世的喧嚣，静享下午温暖的阳光。\n跨过长江隧桥，下高速左转，沿着美丽的陈彷公路，行驶约2公里，即到达漫墅美郡。', NULL, 'http://localhost:9091/files/1728616573999-3.png', '[\"大床房\",\"双床房\",\"温馨亲子房\",\"浪漫情侣房\"]', 7, '2024-10-11', 6, 3, 24);
INSERT INTO `blog` VALUES (9, '河泾美居酒店', '<p>上海虹桥漕河泾美居酒店位于上海漕河泾新兴技术开发区和上海虹桥经济技术开发区之间，距离NECC约20分钟车程。<br/>酒店拥有一百余间的智能客房，法式设计尽显浪漫及典雅；配备丰富中西式早餐的自助餐厅, 还设有大堂吧、健身房等设施，满足商旅宾客的各类需求。<br/></p>', '上海虹桥漕河泾美居酒店位于上海漕河泾新兴技术开发区和上海虹桥经济技术开发区之间，距离NECC约20分钟车程。\n酒店拥有一百余间的智能客房，法式设计尽显浪漫及典雅；配备丰富中西式早餐的自助餐厅, 还设有大堂吧、健身房等设施，满足商旅宾客的各类需求。', '上海漕河泾新兴技术开发区和上海虹桥经济技术开发区', 'http://localhost:9091/files/1728616627132-70.jpg', '[\"大床房\",\"双床房\",\"3-4人房\",\"电竞游戏房\",\"浪漫情侣房\",\"温馨亲子房\"]', 7, '2024-10-11', 14, 1, 24);
INSERT INTO `blog` VALUES (10, '花筑·麦社民宿', '<p style=\"line-height:1.6;\">&nbsp;&nbsp;&nbsp;&nbsp;花筑·上海麦社民宿位于上海国际旅游度假区，距离上海迪士尼正门约3公里，提供民宿至迪士尼接送服务,地铁站约15分钟车程，交通便利；民宿周边景点有奕欧来购物村、上海科技馆、佛罗伦萨小镇。<br/>&nbsp;&nbsp;&nbsp;&nbsp;3层楼，有客厅餐厅及自助厨房，全部是朝南大床房。部分房间提供加床。<br/>&nbsp;&nbsp;&nbsp;&nbsp;这里每间客房都融入了民宿主人的精心设计，民宿坚持客户至上，诚信经营的理念。以亲情化、人性化、更为突出细节化的服务。给每一位入住的客人以家为核心的入住体验。门口沿河有观景台，前后庭院和屋顶花园非常休闲。<br/></p>', '花筑·上海麦社民宿位于上海国际旅游度假区，距离上海迪士尼正门约3公里，提供民宿至迪士尼接送服务,地铁站约15分钟车程，交通便利；民宿周边景点有奕欧来购物村、上海科技馆、佛罗伦萨小镇。\n3层楼，有客厅餐厅及自助厨房，全部是朝南大床房。部分房间提供加床。\n这里每间客房都融入了民宿主人的精心设计，民宿坚持客户至上，诚信经营的理念。以亲情化、人性化、更为突出细节化的服务。给每一位入住的客人以家为核心的入住体验。门口沿河有观景台，前后庭院和屋顶花园非常休闲。', '上海国际旅游度假区', 'http://localhost:9091/files/1728667558874-R5_D.jpg', '[\"大床房\",\"双床房\",\"3-4人房\",\"电竞游戏房\",\"浪漫情侣房\"]', 7, '2024-10-12', 306, 3, 24);
INSERT INTO `blog` VALUES (11, '轻住·长裕优选酒店', '<p>位于浦建路，近中国农业银行，交通便捷。<br/>宾馆拥有温馨舒适的各类房型，设施齐全，卫生干净。酒店四周环境优雅，各项配套设施齐全，是商旅宾客放松身心理想之地。我们热忱欢迎您的到来！<br/></p><p>预订服务提供方</p><p><img src=\"https://dimg04.c-ctrip.com/images/1mc3p12000dinujg1955A_Z_702_0_Q70.jpg\"/></p><p><img src=\"https://dimg04.c-ctrip.com/images/0zm0e12000c0rbl4aCAC0_Z_702_0_Q70.jpg\"/></p><p><img src=\"https://dimg04.c-ctrip.com/images/0201u12000fjgyz4j47F3_Z_702_0_Q70.jpg\"/></p><p><img src=\"https://dimg04.c-ctrip.com/images/0202y12000c3wj0024A09_Z_702_0_Q70.jpg\"/></p>', '位于浦建路，近中国农业银行，交通便捷。\n宾馆拥有温馨舒适的各类房型，设施齐全，卫生干净。酒店四周环境优雅，各项配套设施齐全，是商旅宾客放松身心理想之地。我们热忱欢迎您的到来！', '111', 'http://localhost:9091/files/1728614772253-1.png', '[\"大床房\"]', 7, '2024-06-10', 41, 1, 24);
INSERT INTO `blog` VALUES (12, '云和夜泊酒店(上海浦东国际机场店)', '<p>云和夜泊酒店(上海浦东国际机场店)位于浦东新区祝桥秋亭路，距上海迪士尼约18分钟车程， 距上海野生动物园约20分钟车程，距十六号线惠南站约15分钟车程。酒店地理位置优越，并配备大巴、中巴免费接送浦东机场. 迪士尼. 野生动物园. 十六号线惠南站（以上接送均为班车形式）。<br/>酒店客房空间宽敞，设计简约优雅，线条明朗。所有客房均采用栖云系列优质床品、全套精油草本精华洗浴用品供客使用，网络方面配备百兆高速光纤，WIFI全覆盖，致力于为客提供舒适便捷的住宿体验环境，秉承以人为本的服务理念，致力于让您乘兴而来、满意而归！<br/></p><p>预订服务提供方</p>', '云和夜泊酒店(上海浦东国际机场店)位于浦东新区祝桥秋亭路，距上海迪士尼约18分钟车程， 距上海野生动物园约20分钟车程，距十六号线惠南站约15分钟车程。酒店地理位置优越，并配备大巴、中巴免费接送浦东机场. 迪士尼. 野生动物园. 十六号线惠南站（以上接送均为班车形式）。', '上海浦东新区秋亭路128弄', 'http://localhost:9091/files/1730641575979-a57d35a1-0bff-4fda-862a-cf6e98f46593.png', '[\"大床房\"]', 7, '2024-11-03', 244, 1, NULL);

-- ----------------------------
-- Table structure for blog_encrypt
-- ----------------------------
DROP TABLE IF EXISTS `blog_encrypt`;
CREATE TABLE `blog_encrypt`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `descr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '简介',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签',
  `user_id` int NULL DEFAULT NULL COMMENT '发布人ID',
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发布日期',
  `read_count` int NULL DEFAULT 0 COMMENT '浏览量',
  `category_id` int NULL DEFAULT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '博客信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_encrypt
-- ----------------------------
INSERT INTO `blog_encrypt` VALUES (30, '呢喃河上的幽灵—第三话 新颜与初漫    加密', '<p><img src=\"http://localhost:9091/files/1711218123828-0547f8366c914e17c6b70e07f35fa61e_w2160_h1620_s916.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1711218127294-071bedaaa6a4b00cd6af4eddf274b784_w2160_h1620_s685.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1711218130082-38b2e63fa27d7dfb10201048e36d5c88_w2160_h1620_s572.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1711218132701-6d4c49d1c18d90b5618fb65909ade25f_w2160_h1620_s671.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1711218135126-a29e6b10df65ed21ab529676f8c89916_w2160_h1620_s529.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1711218137415-f8c2f60b8adb77cfd5b35a603f7529c3_w2160_h1620_s569.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', '前情提要：\n记者贺初漫化名为贺新颜踏上呢喃岛。因迷路与受到惊吓失足滚落山坡，最终被岛上村民徐宁发现背回村内。\n\n我又来闪现更新了！抱歉鸽了那么久。下次更新估计会在两个月后，依旧是一人全包无小助手所以随缘更新！求心心，求评论，画原创真的很需要鼓励呀！感谢还在等这漫画的小伙们呀QWQ', 'http://localhost:9091/files/1711218008684-85ca49143df94c80ae765dbad388babc~tplv-k3u1fbpfcp-jj_189_126_0_0_q75.avis', '[\"绘画\",\"幽灵\",\"第三话\"]', 3, '2024-03-24', 8, 1);
INSERT INTO `blog_encrypt` VALUES (31, '后母坏，还是继父恶？    加密', '<h4 id=\"xpvu8\"><font size=\"4\">《周处除三害》很好看，台式黑帮片似乎有望取代港产片。此外，台剧在网飞的资金支持下，也咔咔发力，每年都有一两部火到国内的热播剧。</font></h4><h5><font size=\"4\" style=\"font-weight: normal;\">有人在微博上说，台片台剧的逐渐兴起和港片的日益衰败，和前者一开始就主动放弃了内地市场，而后者又大力迎合，多少有些关系。\n\n我不知道这个说法对不对，但能观察到的现象确实是这两年的台剧台片，在价值观上突飞猛进。</font></h5><h5><font size=\"4\" style=\"font-weight: normal;\">&nbsp;我还记得小时候是根本不看台剧的，实在因为价值观巨大的土和过时：有一部片子，忘了叫什么名字，曾经全国热播，但剧情线似乎是男主因为“爱”强奸了女主，出狱之后，又用“爱”感动了女主，两人最后幸福地在一起了。（似乎是琼瑶剧？）我因为没看，所以不知道回忆是否准确，但我很清楚地记得，当年旁听到相关剧情的台词，简直想拿起菜刀砍死男主，以及所有让女主跟男主结合的配角。\n\n这两年的台剧就，咦？有点不一样了。</font></h5><h5><font size=\"4\" style=\"font-weight: normal;\">而对比来看且令我痛心疾首的是，港剧和港片，尤其是爱情或者生活向的作品，变得其土无比：比如去年还算热门的电影《饭戏攻心》，WTF is this？！\n\n说回《周处除三害》里借女主之口说出的一小段剧情，就是香港仔把女主的妈妈从另一个男人手里救出来，但仍然长期家暴妈妈，妈妈替香港仔顶债顶罪，要坐十多年牢；等母亲去坐牢之后，女主从小就被继父霸占。\n\n这里有两条暗线，一是，为什么“妈妈”总是会碰到家暴自己的男性？“被家暴”也是一种习惯和特质。如果一名女性成长于一个暴力（比如父亲对母亲和子女施暴）的家庭，她有可能会形成吸引“施暴”伴侣的“受虐”气质，甚至主动去招惹对方的暴力；对比而言，如果是男性成长于同类家庭，他也有不小的可能会获得“施暴”的性格（当然，往好了说，他同样也有可能变得憎恨暴力）。换句话说，如果《周处除三害》往下推演，女主爱上了解救自己的男主并在一起，他们大概率会陷入病态的施暴与受虐关系。\n\n还有一条暗线是，继父性侵继女概率到底有多大？\n\n我以前一直很好奇，为什么在中西文化里，“后母”都永远是童话故事里的反派。后来明白了，在中世纪，“亲妈”的确活不长，平均年龄不过是30来岁，生娃时难产死去的概率相当不小。很自然，丈夫铁定是要续弦的，这是“后母”留下恶名的最大原因。\n\n反过来说，如果是父亲死了，母亲一般会独立抚养子女，再嫁的偏少。就算带着孩子再嫁，男孩也就还好，如果是女孩，大多又无法接受教育，难以留下对继父的控诉。\n\n从20世纪80年代开始，本地的离婚率逐渐走高，单亲家庭的孩子颇多。就我个人观察的范围来说，由离异母亲抚养的女孩，几乎都受过继父大大小小的骚扰（小指的是不恰当的抚摸、偷窥；大就是《周处除三害》里的情节再现）。</font></h5><h5><font size=\"4\" style=\"font-weight: normal;\">&nbsp;我不知道这是因为我的取样范本有问题，还是普遍如此。\n\n做过类似观察的朋友，不妨来聊聊。</font><br/></h5>', '后母坏，还是继父恶？', 'http://localhost:9091/files/1711218331263-594dd79b39f84ba3ad070ec77188b874~tplv-k3u1fbpfcp-jj_189_126_0_0_q75.avis', '[\"写作\",\"关系\",\"周处除三害\"]', 6, '2024-03-24', 2, 2);
INSERT INTO `blog_encrypt` VALUES (32, '?填好个税，一年多给几千块 ~ 聊聊个人所得税，你该退税还是补税？   加密', '<h5><span style=\"font-weight: normal;\">为什么你需要补税？</span><br/><span style=\"font-weight: normal;\">大多数情况下，公司发工资会替你把税交了，这个行为叫预缴。</span><br/>为什么预缴呢？因为国家规定：</h5><blockquote><p><br/>《个人所得税扣缴申报管理办法(试行)》(国家税务总局公告2018年第61号发布) <br/>第六条：扣缴义务人向居民个人支付工资、薪金所得时,应当按照累计预扣法计算预扣税款,并按月办理扣缴申报。<br/><br/></p></blockquote><blockquote><p><br/>这也就是我们每个月发工资都会扣税的原因。<br/>那为什么需要补税呢？因为预缴是根据你在当前公司的收入进行缴税，公司会计算你的累进税率，你会发现每到年底税交的越来越高了，这是累进预缴导致的。<br/>有些人在年中换了工作了，新公司不知道你之前已经交到哪个阶段的个税了，因此预缴时计税金额会重新累计。<br/>因此补税的原因不外乎：<br/><br/><u>工作变更<br/>公司主体变更（如：公司拆分）<br/></u><br/>为什么说预缴是天才发明？<br/>预缴制简直是个天才发明，不但会大大减少逃税人数，而且能减轻税务工作量（转移至各公司），且可以让缴税的人对税率的感知没有那么强烈。<br/>达成这种效果主要原因有两点，分别是 损失厌恶 、 心理账户 。<br/>损失厌恶<br/>人们对损失的敏感程度通常远远大于对同等价值的收益的敏感程度<br/>人们对损失的敏感程度通常远远大于对同等价值的收益的敏感程度<br/>人们对损失的敏感程度通常远远大于对同等价值的收益的敏感程度<br/>牢记这句话。<br/>一个最简单的例子，短视频中经常会出现的 最有效的 6 条学习方式，最后一条最重要 。这种放大损失的语言，常常能诱发更高的完播率。<br/><br/>虽然我很讨厌以这种方式留住用户，但常常在刷到这类视频时，也忍不住多看一样，虽然知道它最终可能也没什么实质内容。<br/><br/>还有一种就是我们常常刷掉一个视频，又返回去看一眼，又刷掉又返回去。我常常会有这种心理，这个视频我是不是应该看一看的纠结。<br/>个税也是同理，个税预缴是减少我们的收益，而个税年终汇算则是直接让我们从口袋中掏钱。<br/>就算汇算综合到月度计算，同样也是，一种是公司扣完发给你，另一种是发给你之后你再掏出来一部分。大家感受一下这其中的区别。<br/>心理账户<br/>人们可能会将个税缴纳视作开销，而且是意外开销，意外开销总是让人痛苦的。<br/>比如我每个月 1w 块，其中 3k 拿来租房，3k 拿来吃饭， 2k 拿来娱乐，2k 拿来缴五险一金。<br/>这时候到年终汇算时，人们则容易苦不堪言。<br/>且这种带来的直接后果是，我想把税留到最后一天交，同时最后一天也很容易忘记交，因为大脑也不想要这种意外支出。<br/>最终则导致 漏交、拒交 个税的人数大大增加。<br/>专项附加扣除严谨度<br/><br/>子女教育（未婚，无接触）<br/>赡养老人（容易被查）<br/>继续教育 - 学历提升（基本不查）<br/><br/>学历提升可以选择一个对应学历，每个学历 4 年，共 16 年左右抵税<br/><br/><br/>继续教育 - 证书获取（基本不查）<br/><br/>证书获取有人一个证书可以一直抵税，建议： 营养师证、焊工证等<br/><br/><br/>租房买房（基本不查）<br/>大病医疗（未填过，未知）<br/><br/></p><p><br/></p></blockquote>', '一年一度个税年度综合汇算清缴的时间又到了，作为开发者的你，肯定过了起征点了吧。?\n去年退税退了 5676 ，今年看这个估计得补好几千，但是个税年度汇算清缴还没有预约到，抓紧提前算算金额，做做心理建设。\\同时，了解个税都扣在哪了，才可以让我们合理避税~\n下面我们简单聊聊 补税 和 预缴 ，顺便讲讲专项附加扣除应该怎么填。', 'http://localhost:9091/files/1711218674211-g.avis', '[\"个人所得税\"]', 3, '2024-03-24', 5, 2);
INSERT INTO `blog_encrypt` VALUES (33, '神州志西游Q&A - 手游版本和存档的说明      加密', '<p>目录<br/><br/>手游版怎么购买？<br/><br/>购买了iOS内测版，如何安装？<br/><br/>购买了安卓内测版，如何获得安装包？<br/><br/>如何跨平台存档同步 ？如何还原存档？<br/><br/><br/><br/>如果还有问题，请加入我们QQ群，找群管理私聊解决。<br/><br/>QQ群号：111111111（1群）222222222（2群）<br/><br/><br/><br/>手游版怎么购买？<br/><br/><br/>若是海外区苹果商店可直接下载：<a href=\"https://apps.apple.com/us/app/journey-to-the-west-mobile/id1669592311\" target=\"_blank\">https://apps.apple.com/us/app/journey-to-the-west-mobile/id1669592311</a>1<br/><br/>国区可在爱发电上购买内测版（iOS 和安卓，和PC版同步更新）：<a href=\"https://afdian.net/a/JTW_ZStudio\" target=\"_blank\">https://a/JTW_ZStudio</a>2<br/><br/>注意：今后国区有的正式版会通过邀请码或者激活码将已有内测版兑换成正式版。<br/><br/><br/><br/><br/><br/>购买了上iOS内测版，如何安装？<br/>我们通过Testflight来进行内测版的分发和更新。<br/><br/>第一步：上购买iOS内测版后，系统会和您发起对话，请将email留给我们，我们将把邀请码发到这个email地址中。<br/><br/>第二步：在应用商店下载“TestFlight”软件<br/></p>', '神州志西游Q&A - 手游版本和存档的说明', 'http://localhost:9091/files/1711218959758-szzxy.jpg', '[\"游戏\",\"神州志西游\"]', 6, '2024-03-24', 4, 3);
INSERT INTO `blog_encrypt` VALUES (34, '【西游日记】23年12月更新     加密', '<h1><a href=\"https://afdian.net/p/375dfe769fca11eea4fe5254001e7c00\" id=\"m4gkd\">【西游日记】23年12月更新</a></h1><article><p><b>12月21日开始进入<a href=\"https://store.steampowered.com/app/1449070/_/#app_reviews_hash\">Steam冬季大促，我们也有给力的折扣奉上</a>！欢迎大家购买神州志送给朋友当圣诞礼物哦！本月，我们进行了大量的素材优化，同时对于DLC的开发也在有条不紊地进行中！</b></p><p>紫灵和青灵在释放技能时会进行形态切换。</p><p><img src=\"http://localhost:9091/files/1711219184635-szzxy2.jpg.gif\" style=\"max-width:100%;\" contenteditable=\"false\"/></p><p>重做了选人头像。<br/></p><p><img src=\"http://localhost:9091/files/1711219196653-szzxy3.jpg.gif\" style=\"max-width:100%;\" contenteditable=\"false\"/></p><p>我们对如下技能增加了残影特效：悟空的三头六臂，唐僧的超凡入圣，八戒的八戒归一，白龙的绝处逢生，沙僧的四象归元。是不是变得更酷了？</p><p><img src=\"http://localhost:9091/files/1711219208560-szzxy3.jpg.gif\" style=\"max-width:100%;\" contenteditable=\"false\"/></p><p>其他优化</p><ul><li>完善活体护甲的解释。</li><li>略微加强孟婆的技能。</li><li>优化邪恶结界特效。</li><li>大量素材优化。</li></ul><p><br/></p><p>漏洞修复</p><ul><li>修复移花接木多次叠加导致游戏速度变慢的问题。</li><li>修复卧虎藏龙被多重打出时变成基础效果的问题。</li><li>修复佛旨纶音减血不叠加的问题。</li><li>修复孟婆攻击附加易伤没有说明的问题。</li><li>修复蝙蝠尖牙技能在图鉴里飞行状态没有显示的问题。</li><li>修复阴神玉女的技能解释问题。</li></ul></article>', '12月21日开始进入Steam冬季大促，我们也有给力的折扣奉上！欢迎大家购买神州志送给朋友当圣诞礼物哦！本月，我们进行了大量的素材优化，同时对于DLC的开发也在有条不紊地进行中！', 'http://localhost:9091/files/1711219144516-szzxy.jpg', '[\"西游日记\",\"游戏\"]', 6, '2024-03-24', 9, 3);
INSERT INTO `blog_encrypt` VALUES (35, '古代官兵佩刀3      加密', '<h2 id=\"hhbsp\">来自漫画作者可燃冰老师设计的二次贩售模型稿件：古代官兵佩刀3种！</h2><p>适合不同等级的角色佩戴使用。</p><p>文件格式：</p><p>?古代官兵佩刀3种.skp</p><p><b>商品页直达：<a target=\"_blank\" href=\"https://xxxxxxxxxxxxxxxxx\">https://xxxxxxxxxxxxxxxxx</a></b><br/></p><p><img src=\"http://localhost:9091/files/1711219547340-d.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1711219551403-d2.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1711219556089-d3.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1711219559523-d4.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1711219562491-d5.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', '来自漫画作者可燃冰老师设计的二次贩售模型稿件：古代官兵佩刀3种！\n适合不同等级的角色佩戴，一次购买永久使用！', 'http://localhost:9091/files/1711219425384-d.jpeg', '[\"素材\",\"佩刀\",\"古代官兵\",\"可燃冰\"]', 3, '2024-03-24', 26, 7);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '博客分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '标准酒店');
INSERT INTO `category` VALUES (2, '温馨公寓');
INSERT INTO `category` VALUES (3, '美居民宿');
INSERT INTO `category` VALUES (4, '独栋别墅');
INSERT INTO `category` VALUES (6, '其他');

-- ----------------------------
-- Table structure for certification
-- ----------------------------
DROP TABLE IF EXISTS `certification`;
CREATE TABLE `certification`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NULL DEFAULT NULL COMMENT '账号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '本人照片',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `card1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证正面',
  `card2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证反面',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '常住地址',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '待审核' COMMENT '审核状态',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核理由',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '认证信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of certification
-- ----------------------------
INSERT INTO `certification` VALUES (1, 7, 'sanqi', 'http://localhost:9091/files/1718004835864-OIP (2).jpg', '13002859313', '2021302111111', 'http://localhost:9091/files/1718004845178-QQ图片20230503000208.jpg', 'http://localhost:9091/files/1718004848663-QQ图片20230503000208.jpg', '武汉大学 计算机学院', '审核通过', NULL);

-- ----------------------------
-- Table structure for check_in
-- ----------------------------
DROP TABLE IF EXISTS `check_in`;
CREATE TABLE `check_in`  (
  `id` int NOT NULL,
  `room_id` int NULL DEFAULT NULL,
  `intime` datetime NULL DEFAULT NULL,
  `guest_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gender` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `guest_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `guest_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of check_in
-- ----------------------------

-- ----------------------------
-- Table structure for check_out
-- ----------------------------
DROP TABLE IF EXISTS `check_out`;
CREATE TABLE `check_out`  (
  `id` int NOT NULL,
  `room_id` int NULL DEFAULT NULL,
  `outime` datetime NULL DEFAULT NULL,
  `guest_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gender` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `guest_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `guest_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of check_out
-- ----------------------------

-- ----------------------------
-- Table structure for circulars
-- ----------------------------
DROP TABLE IF EXISTS `circulars`;
CREATE TABLE `circulars`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '系统通知id',
  `user_id` int NULL DEFAULT NULL COMMENT '被通知用户id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '通知内容',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '通知标题',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '通知时间',
  `isChecked` tinyint NULL DEFAULT 0 COMMENT '消息是否已读',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统通知表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of circulars
-- ----------------------------
INSERT INTO `circulars` VALUES (33, 7, '您的帖子<a href=\"/front/blogDetail?blogId=6\" target=\"_blank\" style=\"color: blue;\">《轻住·长裕优选酒店》</a>有了新的回复：酒店环境不错', '新的回复', '2024-10-11 21:07:22', 1);
INSERT INTO `circulars` VALUES (34, 7, '您的帖子<a href=\"/front/blogDetail?blogId=10\" target=\"_blank\" style=\"color: blue;\">《花筑·麦社民宿》</a>有了新的回复：酒店的房型非常丰富', '新的回复', '2024-10-12 19:39:59', 1);

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fid` int NULL DEFAULT NULL COMMENT '关联ID',
  `user_id` int NOT NULL COMMENT '收藏人ID',
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模块',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '收藏' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES (1, 5, 14, '竞赛');
INSERT INTO `collect` VALUES (2, 10, 8, '帖子');
INSERT INTO `collect` VALUES (3, 29, 1, '酒店');
INSERT INTO `collect` VALUES (4, 28, 1, '酒店');
INSERT INTO `collect` VALUES (5, 12, 7, '帖子');
INSERT INTO `collect` VALUES (8, 32, 7, '酒店');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `user_id` int NULL DEFAULT NULL COMMENT '评论人',
  `reply_id` int NULL DEFAULT NULL COMMENT '回复人',
  `pid` int NULL DEFAULT NULL COMMENT '父级ID',
  `root_id` int NULL DEFAULT NULL COMMENT '根节点ID',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论时间',
  `fid` int NULL DEFAULT NULL COMMENT '关联帖子/竞赛ID',
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模块',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (111, '欢迎投递！', 14, 14, NULL, 111, '2024-06-10 21:11:52', 50, '帖子');
INSERT INTO `comment` VALUES (112, '酒店环境不错', 7, 7, NULL, 112, '2024-10-11 21:07:22', 6, '帖子');
INSERT INTO `comment` VALUES (113, '酒店的房型非常丰富', 8, 7, NULL, 113, '2024-10-12 19:39:59', 10, '帖子');
INSERT INTO `comment` VALUES (114, '这个房型非常喜欢', 7, NULL, NULL, 114, '2024-11-06 13:24:39', 32, '酒店');

-- ----------------------------
-- Table structure for concern
-- ----------------------------
DROP TABLE IF EXISTS `concern`;
CREATE TABLE `concern`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '关注用户id',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '关注时间',
  `creator_id` int NULL DEFAULT NULL COMMENT '创作者id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of concern
-- ----------------------------

-- ----------------------------
-- Table structure for fault_report
-- ----------------------------
DROP TABLE IF EXISTS `fault_report`;
CREATE TABLE `fault_report`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `logistics_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `report_time` datetime NOT NULL,
  `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fault_report
-- ----------------------------

-- ----------------------------
-- Table structure for faultreport
-- ----------------------------
DROP TABLE IF EXISTS `faultreport`;
CREATE TABLE `faultreport`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `reporter_id` int NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `report_time` datetime NULL DEFAULT NULL,
  `state` tinyint(1) NULL DEFAULT NULL,
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of faultreport
-- ----------------------------

-- ----------------------------
-- Table structure for homepage
-- ----------------------------
DROP TABLE IF EXISTS `homepage`;
CREATE TABLE `homepage`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `synopsis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '创作者个人主页' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of homepage
-- ----------------------------
INSERT INTO `homepage` VALUES (1, 3, 'http://localhost:9091/files/1712135870852-title.jpg', '<p>只是在做自己喜欢的事。?<br/>让自己开心的前提下构建着自家世界。?<br/>除此之外偶尔会画一点点点同人。?<br/>对每一位支持者都献上真挚的感谢，希望也能给你带来笑容！?<br/></p>');
INSERT INTO `homepage` VALUES (7, 6, 'http://localhost:9091/files/1717837652560-7ef360eb-e943-437b-9deb-53992d40a526.jpg', '<p>主页简介！！</p>');
INSERT INTO `homepage` VALUES (8, 7, 'http://localhost:9091/files/1717863305323-]1OBR0~JLU2U@UG$F{ZJPVS.png', '<p>珞珈山的菜鸡程序员</p>');

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fid` int NULL DEFAULT NULL COMMENT '关联ID',
  `user_id` int NOT NULL COMMENT '点赞人ID',
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模块',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '点赞' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of likes
-- ----------------------------
INSERT INTO `likes` VALUES (1, 5, 6, '竞赛');
INSERT INTO `likes` VALUES (2, 1, 6, '竞赛');
INSERT INTO `likes` VALUES (3, 10, 8, '帖子');
INSERT INTO `likes` VALUES (4, 6, 8, '帖子');
INSERT INTO `likes` VALUES (5, 7, 1, '帖子');
INSERT INTO `likes` VALUES (6, 27, 1, '酒店');
INSERT INTO `likes` VALUES (7, 12, 1, '帖子');
INSERT INTO `likes` VALUES (10, 12, 7, '帖子');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员档位标题',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员档位简介',
  `integral` decimal(10, 0) NULL DEFAULT NULL COMMENT '积分',
  `welfare_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员福利标题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会员档次表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES (3, 3, '黄金搭档，年终巨献：超长精品付费节目！', '<h1>黄金搭档，年终巨献！</h1><p><b>时长138分钟</b>，快乐的时光也可以很长久！<br/>限时独家优惠价，只需<b>5</b>元，即可解锁收听！<br/>主播：崔化剂 老李头</p><p>【影视工作室“南栒映画”创始人，导演、作家、“生息海”乐队创始人；影视、音乐、文学多领域全开花，灵魂和身体永远都走在路上。】</p>', 100, '素材1');
INSERT INTO `member` VALUES (6, 3, 'test2', '<p><img src=\"http://localhost:9091/files/1712559832981-d3.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 30, '素材2');
INSERT INTO `member` VALUES (7, 8, '测试1', '<p>测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1测试1</p>', 1, '测试1');
INSERT INTO `member` VALUES (8, 9, '咨询答疑', '<p><b>答疑范围：</b></p><ol><li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;范围1</li><li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;范围2</li><li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;范围3</li></ol>', 10, '三次咨询答疑机会');
INSERT INTO `member` VALUES (9, 11, '3d可动马偶', '<h1><b id=\"ol1dc\">马头墙su模型</b></h1><p><b>3d可动</b></p><p><b>无级调节</b></p><p>只需30积分</p><p><img src=\"http://localhost:9091/files/1713180298060-马2.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 30, '3d可动马偶');
INSERT INTO `member` VALUES (10, 12, '可动马模型', '<p>可动马模型可动马模型可动马模型可动马模型可动马模型可动马模型</p>', 30, '可动马模型');
INSERT INTO `member` VALUES (11, 13, '会员1', '<p><img src=\"http://localhost:9091/files/1713456087445-马1.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 30, '会员1');

-- ----------------------------
-- Table structure for membership_benefits
-- ----------------------------
DROP TABLE IF EXISTS `membership_benefits`;
CREATE TABLE `membership_benefits`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `member_id` int NOT NULL COMMENT '关联id',
  `user_id` int NOT NULL COMMENT '关联用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of membership_benefits
-- ----------------------------
INSERT INTO `membership_benefits` VALUES (1, 'text12', '<p><img src=\"http://localhost:9091/files/1712557967108-d.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 3, 3);
INSERT INTO `membership_benefits` VALUES (2, '素材2', '<p><img src=\"http://localhost:9091/files/1712559859751-d2.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1712559866754-d4.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 6, 3);
INSERT INTO `membership_benefits` VALUES (3, '没有福利', '<p>谢谢</p>', 7, 8);
INSERT INTO `membership_benefits` VALUES (4, '加v', '<p>请加v：7976sdsdas</p>', 8, 9);
INSERT INTO `membership_benefits` VALUES (5, '3d可动', '<p><img src=\"http://localhost:9091/files/1713180374053-马3.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 9, 11);
INSERT INTO `membership_benefits` VALUES (6, '3d可动马压缩包', '<p><img src=\"http://localhost:9091/files/1713455698830-2.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p><p><img src=\"http://localhost:9091/files/1713455702606-马2.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 10, 12);
INSERT INTO `membership_benefits` VALUES (7, '3d可动马', '<p><img src=\"http://localhost:9091/files/1713456102993-马3.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 11, 13);

-- ----------------------------
-- Table structure for my_welfare
-- ----------------------------
DROP TABLE IF EXISTS `my_welfare`;
CREATE TABLE `my_welfare`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '表id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `user_id` int NOT NULL COMMENT '购买人id',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '购买时间',
  `db_user_id` int NOT NULL COMMENT '创作者id',
  `member_id` int NULL DEFAULT NULL COMMENT '福利id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of my_welfare
-- ----------------------------
INSERT INTO `my_welfare` VALUES (1, '素材2', '<p><img src=\"http://localhost:9091/files/1712559859751-d2.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1712559866754-d4.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 6, '2024-04-08 17:01:38', 3, 6);
INSERT INTO `my_welfare` VALUES (2, '黄金搭档，年终巨献：超长精品付费节目', '<p><img src=\"http://localhost:9091/files/1712557967108-d.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 6, '2024-04-10 14:55:03', 3, 3);
INSERT INTO `my_welfare` VALUES (3, 'text12', '<p><img src=\"http://localhost:9091/files/1712557967108-d.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 8, '2024-04-11 15:32:20', 3, 3);
INSERT INTO `my_welfare` VALUES (4, '素材2', '<p><img src=\"http://localhost:9091/files/1712559859751-d2.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1712559866754-d4.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 8, '2024-04-11 15:33:05', 3, 6);
INSERT INTO `my_welfare` VALUES (5, '没有福利', '<p>谢谢</p>', 9, '2024-04-15 00:58:00', 8, 7);
INSERT INTO `my_welfare` VALUES (6, '素材2', '<p><img src=\"http://localhost:9091/files/1712559859751-d2.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1712559866754-d4.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 10, '2024-04-15 19:06:55', 3, 6);
INSERT INTO `my_welfare` VALUES (7, '素材2', '<p><img src=\"http://localhost:9091/files/1712559859751-d2.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1712559866754-d4.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 11, '2024-04-15 19:16:21', 3, 6);
INSERT INTO `my_welfare` VALUES (8, '没有福利', '<p>谢谢</p>', 3, '2024-04-17 15:06:02', 8, 7);
INSERT INTO `my_welfare` VALUES (9, '没有福利', '<p>谢谢</p>', 12, '2024-04-18 23:51:11', 8, 7);
INSERT INTO `my_welfare` VALUES (10, 'text12', '<p><img src=\"http://localhost:9091/files/1712557967108-d.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 12, '2024-04-18 23:51:24', 3, 3);
INSERT INTO `my_welfare` VALUES (11, '素材2', '<p><img src=\"http://localhost:9091/files/1712559859751-d2.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9091/files/1712559866754-d4.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 12, '2024-04-18 23:51:27', 3, 6);
INSERT INTO `my_welfare` VALUES (12, '3d可动马压缩包', '<p><img src=\"http://localhost:9091/files/1713455698830-2.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p><p><img src=\"http://localhost:9091/files/1713455702606-马2.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 13, '2024-04-18 23:58:21', 12, 10);
INSERT INTO `my_welfare` VALUES (13, 'text12', '<p><img src=\"http://localhost:9091/files/1712557967108-d.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 13, '2024-04-18 23:58:33', 3, 3);
INSERT INTO `my_welfare` VALUES (14, '3d可动马', '<p><img src=\"http://localhost:9091/files/1713456102993-马3.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 3, '2024-04-20 00:53:45', 13, 11);
INSERT INTO `my_welfare` VALUES (15, 'text12', '<p><img src=\"http://localhost:9091/files/1712557967108-d.jpeg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 10, '2024-04-23 00:38:29', 3, 3);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建时间',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `hotel` int NULL DEFAULT NULL,
  `room` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `state` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '公告信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '设施维护通知', '尊敬的宾客，酒店将在2024年10月15日至17日期间对部分公共设施进行维护。期间可能会有短暂影响，敬请谅解。', '2023-09-05', 'admin', NULL, NULL, NULL);
INSERT INTO `notice` VALUES (2, '停车服务温馨提示', '尊敬的宾客，酒店提供有限停车位，先到先得。请您在抵达前与酒店确认车位情况，感谢您的配合。', '2024-06-09', 'admin', NULL, NULL, NULL);
INSERT INTO `notice` VALUES (3, '早餐服务调整通知', '尊敬的宾客，为优化服务体验，酒店早餐时间调整为每天6:30至10:00。早餐供应地点为一楼餐厅，感谢您的理解与支持。', '2024-06-09', 'admin', NULL, NULL, NULL);
INSERT INTO `notice` VALUES (4, '入住时间及退房时间', '尊敬的宾客，酒店的入住时间为下午14:00，退房时间为中午12:00。如需延迟退房，请提前告知前台，具体费用按酒店政策执行。', '2024-06-10', 'admin', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for records
-- ----------------------------
DROP TABLE IF EXISTS `records`;
CREATE TABLE `records`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '时间',
  `integral` decimal(10, 0) NULL DEFAULT NULL COMMENT '积分',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of records
-- ----------------------------
INSERT INTO `records` VALUES (1, 3, '2024-04-03 09:45:06', 10, '签到');
INSERT INTO `records` VALUES (2, 3, '2024-04-03 10:47:12', 298, '充值');
INSERT INTO `records` VALUES (3, 3, '2024-04-03 10:56:43', 6, '充值');
INSERT INTO `records` VALUES (4, 3, '2024-04-03 10:57:31', 6, '充值');
INSERT INTO `records` VALUES (5, 3, '2024-04-03 11:00:41', 6, '充值');
INSERT INTO `records` VALUES (6, 3, '2024-04-03 11:01:17', 648, '充值');
INSERT INTO `records` VALUES (7, 3, '2024-04-05 14:45:37', 10, '充值');
INSERT INTO `records` VALUES (8, 3, '2024-04-05 14:45:45', 12, '充值');
INSERT INTO `records` VALUES (9, 3, '2024-04-05 15:21:06', 648, '充值');
INSERT INTO `records` VALUES (10, 3, '2024-04-05 17:49:22', 328, '充值');
INSERT INTO `records` VALUES (11, 3, '2024-04-08 16:44:25', 10, '充值');
INSERT INTO `records` VALUES (12, 3, '2024-04-08 16:44:30', 12, '充值');
INSERT INTO `records` VALUES (13, 6, '2024-04-08 17:00:07', 10, '充值');
INSERT INTO `records` VALUES (14, 6, '2024-04-08 17:01:01', 6, '充值');
INSERT INTO `records` VALUES (15, 6, '2024-04-08 17:01:30', 6, '充值');
INSERT INTO `records` VALUES (16, 6, '2024-04-08 17:01:38', 30, '开通会员扣除');
INSERT INTO `records` VALUES (17, 3, '2024-04-08 17:01:38', 30, '开通会员获取');
INSERT INTO `records` VALUES (18, 6, '2024-04-10 14:54:39', 10, '充值');
INSERT INTO `records` VALUES (19, 6, '2024-04-10 14:54:41', 98, '充值');
INSERT INTO `records` VALUES (20, 6, '2024-04-10 14:55:03', 100, '开通会员扣除');
INSERT INTO `records` VALUES (21, 3, '2024-04-10 14:55:03', 100, '开通会员获取');
INSERT INTO `records` VALUES (30, 8, '2024-04-11 15:29:00', 10, '签到');
INSERT INTO `records` VALUES (31, 8, '2024-04-11 15:29:04', 128, '充值');
INSERT INTO `records` VALUES (32, 8, '2024-04-11 15:31:45', 128, '充值');
INSERT INTO `records` VALUES (33, 8, '2024-04-11 15:32:20', 100, '开通会员扣除');
INSERT INTO `records` VALUES (34, 3, '2024-04-11 15:32:20', 100, '开通会员获取');
INSERT INTO `records` VALUES (35, 8, '2024-04-11 15:32:54', 6, '充值');
INSERT INTO `records` VALUES (36, 8, '2024-04-11 15:33:05', 30, '开通会员扣除');
INSERT INTO `records` VALUES (37, 3, '2024-04-11 15:33:05', 30, '开通会员获取');
INSERT INTO `records` VALUES (38, 8, '2024-04-11 16:44:30', 328, '充值');
INSERT INTO `records` VALUES (39, 8, '2024-04-11 17:13:15', 6, '充值');
INSERT INTO `records` VALUES (46, 9, '2024-04-15 00:56:33', 10, '签到');
INSERT INTO `records` VALUES (47, 9, '2024-04-15 00:56:41', 128, '充值');
INSERT INTO `records` VALUES (48, 9, '2024-04-15 00:58:00', 1, '开通会员扣除');
INSERT INTO `records` VALUES (49, 8, '2024-04-15 00:58:00', 1, '开通会员获取');
INSERT INTO `records` VALUES (50, 10, '2024-04-15 19:01:40', 10, '签到');
INSERT INTO `records` VALUES (51, 10, '2024-04-15 19:01:43', 128, '充值');
INSERT INTO `records` VALUES (52, 10, '2024-04-15 19:01:44', 128, '充值');
INSERT INTO `records` VALUES (53, 10, '2024-04-15 19:01:44', 128, '充值');
INSERT INTO `records` VALUES (54, 10, '2024-04-15 19:06:55', 30, '开通会员扣除');
INSERT INTO `records` VALUES (55, 3, '2024-04-15 19:06:55', 30, '开通会员获取');
INSERT INTO `records` VALUES (56, 11, '2024-04-15 19:15:52', 10, '签到');
INSERT INTO `records` VALUES (57, 11, '2024-04-15 19:16:01', 328, '充值');
INSERT INTO `records` VALUES (58, 11, '2024-04-15 19:16:21', 30, '开通会员扣除');
INSERT INTO `records` VALUES (59, 3, '2024-04-15 19:16:21', 30, '开通会员获取');
INSERT INTO `records` VALUES (60, 3, '2024-04-17 15:06:02', 1, '开通会员扣除');
INSERT INTO `records` VALUES (61, 8, '2024-04-17 15:06:02', 1, '开通会员获取');
INSERT INTO `records` VALUES (62, 12, '2024-04-18 23:50:55', 10, '签到');
INSERT INTO `records` VALUES (63, 12, '2024-04-18 23:50:58', 128, '充值');
INSERT INTO `records` VALUES (64, 12, '2024-04-18 23:51:11', 1, '开通会员扣除');
INSERT INTO `records` VALUES (65, 8, '2024-04-18 23:51:11', 1, '开通会员获取');
INSERT INTO `records` VALUES (66, 12, '2024-04-18 23:51:24', 100, '开通会员扣除');
INSERT INTO `records` VALUES (67, 3, '2024-04-18 23:51:24', 100, '开通会员获取');
INSERT INTO `records` VALUES (68, 12, '2024-04-18 23:51:27', 30, '开通会员扣除');
INSERT INTO `records` VALUES (69, 3, '2024-04-18 23:51:27', 30, '开通会员获取');
INSERT INTO `records` VALUES (70, 13, '2024-04-18 23:57:54', 10, '签到');
INSERT INTO `records` VALUES (71, 13, '2024-04-18 23:57:56', 12, '充值');
INSERT INTO `records` VALUES (72, 13, '2024-04-18 23:58:12', 128, '充值');
INSERT INTO `records` VALUES (73, 13, '2024-04-18 23:58:21', 30, '开通会员扣除');
INSERT INTO `records` VALUES (74, 12, '2024-04-18 23:58:21', 30, '开通会员获取');
INSERT INTO `records` VALUES (75, 13, '2024-04-18 23:58:33', 100, '开通会员扣除');
INSERT INTO `records` VALUES (76, 3, '2024-04-18 23:58:33', 100, '开通会员获取');
INSERT INTO `records` VALUES (77, 3, '2024-04-20 00:53:45', 30, '开通会员扣除');
INSERT INTO `records` VALUES (78, 13, '2024-04-20 00:53:45', 30, '开通会员获取');
INSERT INTO `records` VALUES (79, 3, '2024-04-20 14:13:41', 328, '充值');
INSERT INTO `records` VALUES (80, 10, '2024-04-23 00:38:29', 100, '开通会员扣除');
INSERT INTO `records` VALUES (81, 3, '2024-04-23 00:38:29', 100, '开通会员获取');
INSERT INTO `records` VALUES (82, 14, '2024-06-08 11:18:39', 10, '签到');

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `activity_id` int NOT NULL,
  `state` int(10) UNSIGNED ZEROFILL NULL DEFAULT 0000000000 COMMENT '0 空闲\r\n1 已预定\r\n2 已入住\r\n3 打扫整理中',
  `room_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of room
-- ----------------------------

-- ----------------------------
-- Table structure for service
-- ----------------------------
DROP TABLE IF EXISTS `service`;
CREATE TABLE `service`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建时间',
  `user` int NULL DEFAULT NULL COMMENT '创建人',
  `hotel` int NULL DEFAULT NULL COMMENT '酒店',
  `room` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `state` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '公告信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of service
-- ----------------------------
INSERT INTO `service` VALUES (1, '设施维护通知', '尊敬的宾客，酒店将在2024年10月15日至17日期间对部分公共设施进行维护。期间可能会有短暂影响，敬请谅解。', '2023-09-05', 9, 7, '302', 1);
INSERT INTO `service` VALUES (2, '停车服务温馨提示', '尊敬的宾客，酒店提供有限停车位，先到先得。请您在抵达前与酒店确认车位情况，感谢您的配合。', '2024-06-09', 9, 8, '205', 1);
INSERT INTO `service` VALUES (3, '早餐服务调整通知', '尊敬的宾客，为优化服务体验，酒店早餐时间调整为每天6:30至10:00。早餐供应地点为一楼餐厅，感谢您的理解与支持。', '2024-06-09', 9, 7, '312', 1);
INSERT INTO `service` VALUES (4, '入住时间及退房时间', '尊敬的宾客，酒店的入住时间为下午14:00，退房时间为中午12:00。如需延迟退房，请提前告知前台，具体费用按酒店政策执行。', '2024-06-10', 8, 7, '306', 0);
INSERT INTO `service` VALUES (13, '房间清洁', '111', '2024-09-30T16:00:00.000Z', 9, 7, '213', 0);
INSERT INTO `service` VALUES (14, '早餐服务', '222111', '2024-11-03T18:29:46.000Z', 9, 1, '401', 0);
INSERT INTO `service` VALUES (15, '早餐服务', 'beizhu1', '2024-11-07T08:38:47.000Z', 9, 1, NULL, 1);
INSERT INTO `service` VALUES (16, '早餐服务', '2', '2024-11-08T16:00:00.000Z', 7, 1, NULL, NULL);

-- ----------------------------
-- Table structure for sign_in
-- ----------------------------
DROP TABLE IF EXISTS `sign_in`;
CREATE TABLE `sign_in`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '签到时间',
  `user_id` int NULL DEFAULT NULL COMMENT '签到用户Id',
  `integral` decimal(10, 0) NULL DEFAULT NULL COMMENT '本次签到的积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sign_in
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '简介',
  `birth` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生日',
  `integral` decimal(10, 0) UNSIGNED NOT NULL COMMENT '积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'elysia', 'elysian', 'elysia', 'http://localhost:9091/files/1705558132617-1697438073596-avatar.png', 'USER', '女', '18383838383', '18383838383@163.com', '正在创作 毕业设计', '2000-12-22', 2657);
INSERT INTO `user` VALUES (2, 'kevin', 'elysian', 'kevin', 'http://localhost:9091/files/1705565856634-ROG_FLOW_X13_product_wallpaper_3840x2400.jpg', 'USER', NULL, '18318338075', NULL, '简介简介简介简介简介', NULL, 110);
INSERT INTO `user` VALUES (3, 'test1', '123456789', 'test1', 'http://localhost:9091/files/1712820559058-1697438073596-avatar.png', 'USER', NULL, '12345678901', NULL, NULL, NULL, 337);
INSERT INTO `user` VALUES (4, 'num2', 'num2', 'num2', 'http://localhost:9091/files/1713113586356-1697438073596-avatar.png', 'USER', NULL, '13345678900', '1234tgfweqw3124123@123.com', NULL, NULL, 137);
INSERT INTO `user` VALUES (5, 'user5', '12345', 'user5', 'http://localhost:9091/files/1713455395862-头像.jpg', 'USER', NULL, '13434344333', '23124fer2@163.COM', NULL, NULL, 37);
INSERT INTO `user` VALUES (6, 'test', '123', 'test', NULL, 'USER', NULL, NULL, NULL, NULL, NULL, 20);
INSERT INTO `user` VALUES (7, 'sanqi', 'sanqi', 'sanqi', 'http://localhost:9091/files/1717863322272-吴彦祖.jpg', 'USER', NULL, '13002859313', '1078825799@qq.com', NULL, NULL, 0);
INSERT INTO `user` VALUES (8, 'hoteltest', 'hoteltest', 'hoteltest', NULL, 'USER', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (9, '1234', '1234', '1234', NULL, 'USER', NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for user_server
-- ----------------------------
DROP TABLE IF EXISTS `user_server`;
CREATE TABLE `user_server`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '简介',
  `birth` int NULL DEFAULT NULL COMMENT '生日',
  `integral` decimal(10, 0) UNSIGNED NOT NULL COMMENT '积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_server
-- ----------------------------
INSERT INTO `user_server` VALUES (1, 'server1', '123456', 'server1', NULL, 'SERVER', NULL, NULL, NULL, NULL, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
