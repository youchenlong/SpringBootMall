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