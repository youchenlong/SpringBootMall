SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `first_letter` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '首字母',
  `factory_status` int(1) NULL DEFAULT NULL COMMENT '是否为品牌制造商：0->不是；1->是',
  `brand_story` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '品牌故事',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci COMMENT = '品牌表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_brand
-- ----------------------------
INSERT INTO `pms_brand` VALUES (1, '万和', 'W', 1, '万和成立于1993年8月，总部位于广东顺德国家级高新技术开发区内，是国内生产规模最大的燃气具专业制造企业，也是中国燃气具发展战略的首倡者和推动者、中国五金制品协会燃气用具分会第三届理事长单位。');
INSERT INTO `pms_brand` VALUES (2, '三星', 'S', 1, '三星集团（英文：SAMSUNG、韩文：삼성）是韩国最大的跨国企业集团，三星集团包括众多的国际下属企业，旗下子公司有：三星电子、三星物产、三星人寿保险等，业务涉及电子、金融、机械、化学等众多领域。');
INSERT INTO `pms_brand` VALUES (3, '华为', 'H', 1, '荣耀品牌成立于2013年,是华为旗下手机双品牌之一。荣耀以“创新、品质、服务”为核心战略,为全球年轻人提供潮酷的全场景智能化体验,打造年轻人向往的先锋文化和潮流生活方式');
INSERT INTO `pms_brand` VALUES (4, '格力', 'G', 1, '格力的故事');
INSERT INTO `pms_brand` VALUES (5, '小米', 'M', 1, '小米公司正式成立于2010年4月，是一家专注于高端智能手机、互联网电视自主研发的创新型科技企业。主要由前谷歌、微软、摩托、金山等知名公司的顶尖人才组建。');
INSERT INTO `pms_brand` VALUES (6, 'OPPO', 'O', 1, 'OPPO于2008年推出第一款“笑脸手机”，由此开启探索和引领至美科技之旅。今天，OPPO凭借以Find和R系列手机为核心的智能终端产品，以及OPPO+等互联网服务，让全球消费者尽享至美科技。');
INSERT INTO `pms_brand` VALUES (7, '七匹狼', 'S', 1, '七匹狼的故事');
INSERT INTO `pms_brand` VALUES (8, '海澜之家', 'H', 1, '“海澜之家”（英文缩写：HLA）是海澜之家股份有限公司旗下的服装品牌，总部位于中国江苏省无锡市江阴市，主要采用连锁零售的模式，销售男性服装、配饰与相关产品。');
INSERT INTO `pms_brand` VALUES (9, 'Apple', 'A', 1, '苹果公司(Apple Inc. )是美国的一家高科技公司。 由史蒂夫·乔布斯、斯蒂夫·沃兹尼亚克和罗·韦恩(Ron Wayne)等人于1976年4月1日创立,并命名为美国苹果电脑公司(Apple Computer Inc. ),2007年1月9日更名为苹果公司,总部位于加利福尼亚州的...');
INSERT INTO `pms_brand` VALUES (10, 'NIKE', 'N', 1, 'NIKE的故事');

-- ----------------------------
-- Table structure for pms_product
-- ----------------------------
DROP TABLE IF EXISTS `pms_product`;
CREATE TABLE `pms_product`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_id` bigint(20) NOT NULL,
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关键词',
  `price` decimal(10, 2) DEFAULT NULL,
  `sale` int(11) DEFAULT NULL COMMENT '销量',
  `stock` int(11) DEFAULT NULL COMMENT '库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_product
-- ----------------------------
INSERT INTO `pms_product` VALUES (1, 1, '万和燃气热水器', '天然气家用四重防冻直流变频节能全新升级增压水伺服恒温高抗风', '家用电器', 7999, 0, 1000);
INSERT INTO `pms_product` VALUES (2, 8, 'HLA海澜之家简约动物印花短袖T恤', 'HLA海澜之家简约动物印花短袖T恤', '服装', 399, 0, 1000);
INSERT INTO `pms_product` VALUES (3, 5, 'Xiaomi Book Pro 14 2022 锐龙版', '2.8K超清大师屏 高端轻薄笔记本电脑', '手机数码', 8999, 0, 1000);
INSERT INTO `pms_product` VALUES (4, 5, '小米12 Pro 天玑版', '天玑9000+处理器 5000万疾速影像 2K超视感屏 120Hz高刷 67W快充', '手机数码', 8999, 0, 1000);
INSERT INTO `pms_product` VALUES (5, 10, 'NIKE ROSHE RUN 运动鞋', '511881-010黑色41码', '服装', 899, 0, 1000);
INSERT INTO `pms_product` VALUES (6, 10, 'NIKE AIR MAX 90 ESSENTIAL', 'AJ1285-101白色41码', '服装', 899, 0, 1000);
INSERT INTO `pms_product` VALUES (7, 9, 'Apple iPhone 14', '128GB 支持移动联通电信5G 双卡双待手机', '手机数码', 8999, 0, 1000);
INSERT INTO `pms_product` VALUES (8, 3, 'HUAWEI Mate 50', '直屏旗舰 超光变XMAGE影像 北斗卫星消息', '手机数码', 8999, 0, 1000);
INSERT INTO `pms_product` VALUES (9, 6, 'OPPO Reno8', '8GB+128GB 鸢尾紫 新配色上市 80W超级闪充 5000万水光人像三摄', '手机数码', 8999, 0, 1000);
INSERT INTO `pms_product` VALUES (10, 7, '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', '服装', 399, 0, 1000);

-- ----------------------------
-- Table structure for ums_user
-- ----------------------------
DROP TABLE IF EXISTS `ums_user`;
CREATE TABLE `ums_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `nickname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电话号码',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `gender` int(1) DEFAULT 0 COMMENT '性别：0->未知；1->男；2->女',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_user
-- ----------------------------
INSERT INTO `ums_user` VALUES (1, '202224021001', '202224021001', 'Alice', '13096190001', '2091838001@qq.com', '0', '2024-05-21 15:55:01', NULL);
INSERT INTO `ums_user` VALUES (2, '202224021002', '202224021002', 'Bob', '13096190002', '2091838002@qq.com', '1', '2024-05-21 15:55:01', '2000-05-02 01:01:01');
INSERT INTO `ums_user` VALUES (3, '202224021003', '202224021003', 'Cindy', '13096190003', '2091838003@qq.com', '2', '2024-05-21 15:55:01', '2000-05-03 01:01:01');
INSERT INTO `ums_user` VALUES (4, '202224021004', '202224021004', 'David', '13096190004', '2091838004@qq.com', '1', '2024-05-21 15:55:01', '2000-05-04 01:01:01');
INSERT INTO `ums_user` VALUES (5, '202224021005', '202224021005', 'Frank', '13096190005', '2091838005@qq.com', '1', '2024-05-21 15:55:01', '2000-05-05 01:01:01');
INSERT INTO `ums_user` VALUES (6, '202224021006', '202224021006', 'George', '13096190006', '2091838006@qq.com', '1', '2024-05-21 15:55:01', '2000-05-06 01:01:01');
INSERT INTO `ums_user` VALUES (7, '202224021007', '202224021007', 'Helen', '13096190007', '2091838007@qq.com', '0', '2024-05-21 15:55:01', NULL);
INSERT INTO `ums_user` VALUES (8, '202224021008', '202224021008', 'Issac', '13096190008', '2091838008@qq.com', '1', '2024-05-21 15:55:01', '2000-05-08 01:01:01');
INSERT INTO `ums_user` VALUES (9, '202224021009', '202224021009', 'Joker', '13096190009', '2091838009@qq.com', '0', '2024-05-21 15:55:01', NULL);
INSERT INTO `ums_user` VALUES (10, '202224021010', '202224021010', 'Ken', '13096190010', '2091838010@qq.com', '1', '2024-05-21 15:55:01', '2000-05-10 01:01:01');

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `status` int(1) DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime DEFAULT NULL COMMENT '评价时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order` VALUES (1, 1, 0, '2024-05-21 16:17:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (2, 2, 0, '2024-05-21 16:17:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (3, 3, 0, '2024-05-21 16:17:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (4, 4, 0, '2024-05-21 16:17:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (5, 5, 0, '2024-05-21 16:17:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (6, 6, 0, '2024-05-21 16:17:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (7, 7, 0, '2024-05-21 16:17:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (8, 8, 0, '2024-05-21 16:17:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (9, 9, 0, '2024-05-21 16:17:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (10, 10, 0, '2024-05-21 16:17:01', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `product_quantity` int(11) DEFAULT 0 COMMENT '购买数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci COMMENT = '订单明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------
-- INSERT INTO `oms_order_item` VALUES (1, 1, 1, 1);
-- INSERT INTO `oms_order_item` VALUES (2, 2, 2, 1);
-- INSERT INTO `oms_order_item` VALUES (3, 3, 3, 1);
-- INSERT INTO `oms_order_item` VALUES (4, 4, 4, 1);
-- INSERT INTO `oms_order_item` VALUES (5, 5, 5, 1);
-- INSERT INTO `oms_order_item` VALUES (6, 6, 6, 1);
-- INSERT INTO `oms_order_item` VALUES (7, 7, 7, 1);
-- INSERT INTO `oms_order_item` VALUES (8, 8, 8, 1);
-- INSERT INTO `oms_order_item` VALUES (9, 9, 9, 1);
-- INSERT INTO `oms_order_item` VALUES (10, 10, 10, 1);

-- ----------------------------
-- Table structure for oms_cart
-- ----------------------------
DROP TABLE IF EXISTS `oms_cart`;
CREATE TABLE `oms_cart`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `status` int(1) DEFAULT NULL COMMENT '购物车状态：0->正常；1->已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_cart
-- ----------------------------
INSERT INTO `oms_cart` VALUES (1, 1, 0, '2024-05-21 16:44:01', NULL);
INSERT INTO `oms_cart` VALUES (2, 2, 0, '2024-05-21 16:44:01', NULL);
INSERT INTO `oms_cart` VALUES (3, 3, 0, '2024-05-21 16:44:01', NULL);
INSERT INTO `oms_cart` VALUES (4, 4, 0, '2024-05-21 16:44:01', NULL);
INSERT INTO `oms_cart` VALUES (5, 5, 0, '2024-05-21 16:44:01', NULL);
INSERT INTO `oms_cart` VALUES (6, 6, 0, '2024-05-21 16:44:01', NULL);
INSERT INTO `oms_cart` VALUES (7, 7, 0, '2024-05-21 16:44:01', NULL);
INSERT INTO `oms_cart` VALUES (8, 8, 0, '2024-05-21 16:44:01', NULL);
INSERT INTO `oms_cart` VALUES (9, 9, 0, '2024-05-21 16:44:01', NULL);
INSERT INTO `oms_cart` VALUES (10, 10, 0, '2024-05-21 16:44:01', NULL);

-- ----------------------------
-- Table structure for oms_cart_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_cart_item`;
CREATE TABLE `oms_cart_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cart_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `product_quantity` int(11) DEFAULT 0 COMMENT '购买数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_cart_item
-- ----------------------------
INSERT INTO `oms_cart_item` VALUES (1, 1, 1, 1);
INSERT INTO `oms_cart_item` VALUES (2, 2, 2, 1);
INSERT INTO `oms_cart_item` VALUES (3, 3, 3, 1);
INSERT INTO `oms_cart_item` VALUES (4, 4, 4, 1);
INSERT INTO `oms_cart_item` VALUES (5, 5, 5, 1);
INSERT INTO `oms_cart_item` VALUES (6, 6, 6, 1);
INSERT INTO `oms_cart_item` VALUES (7, 7, 7, 1);
INSERT INTO `oms_cart_item` VALUES (8, 8, 8, 1);
INSERT INTO `oms_cart_item` VALUES (9, 9, 9, 1);
INSERT INTO `oms_cart_item` VALUES (10, 10, 10, 1);