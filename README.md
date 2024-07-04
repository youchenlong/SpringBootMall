# Welcome to SpringBootMall

### 数据库

* ER图
  ![ER](docs/img/ER.png)
* 数据表
  * pms_brand (id, name, first_letter, factory_status, brand_story)
    * factory_status: 是否为品牌制造商：0->不是；1->是
  * pms_product (id, brand_id, name, description, keywords, price, sale, stock)
    * keywords: 服装，手机数码，家用电器，家具家装，汽车用品
  * ums_user (id, username, password, nickname, phone, email, gender, create_time, birthday)
    * gender: 性别：0->未知；1->男；2->女
  * oms_order (id, user_id, status, create_time, payment_time, delivery_time, receive_time, comment_time, update_time)
    * status: 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
  * oms_order_item (id, order_id, product_id, product_quantity)
  * oms_cart (id, user_id, status, create_time, update_time)
    * status: 购物车状态：0->正常；1->已删除
  * oms_cart_item (id, cart_id, product_id, product_quantity)
* 创建数据库并导入数据
  ```shell
  create database mall_tiny;
  use mall_tiny;
  source mall_tiny_filled.sql;
  ```
* 测试
  * PmsBrandDaoTest -> PmsProductDaoTest -> UmsUserDaoTest -> OmsCartDaoTest -> OmsCartItemDaoTest -> OmsOrderDaoTest -> OmsOrderItemDaoTest
* 一些不合理的约束
  * 一个用户只能有一个购物车 (在添加购物车时判断购物车是否存在)
  * 品牌名不能相同（在添加品牌时判断品牌是否存在）

### 业务需求

* 商品管理
  * 品牌增删改查
  * 商品增删改查
  * 品牌缓存
    - [X]  Redis(String + List)
      - String存储<"brand:" + "brandId:" + brandId, brand>
      - List存储<"brand:" + "allBrandIds", brandIds>
  * 商品缓存
    - [X]  Redis(String + List)
      - String存储<"product:" + "productId:" + productId, product>
      - List存储<"product:" + "allProductIds", productIds>
  * 热门商品
    - [ ]  Redis(Sorted Set)
  * 商品检索
    - [ ]  ElasticSearch
* 订单管理
  * 订单增删改查
  * 购物车
    - [X]  Redis(Hash + List + String)
      - Hash存储<"cart:" + "cartId:" + cartId, cart>和<"cart:" + "cartItemId:" + cartItemId, cartItem>, 设置过期键
      - String存储<"cart:" + "userId:" + userId, cartId>
      - List存储<"cart:" + "allCartIds", cartIds>, <"cart:" + "allCartItemIdsOfCartId:" + cartId, cartItemIds>
  * 订单超时处理
    - [ ]  Redis(过期键)
    - [ ]  RabbitMQ(消息TTL+死信Exchange)
  * 限时抢购
    - [ ]  Redis(过期键) + RabbitMQ(消息TTL+死信Exchange)
* 用户管理
  * 用户增删改查
  * 用户认证（获取验证码，判断验证码是否正确）
    - [X]  Redis(String)
      - String存储<telephone, authCode>

### 练习

* MySQL
  - [X]  MySQL增删改查？
  - [ ]  索引？
  - [ ]  读写分离？
  - [ ]  分库分表？
* Redis
  - [ ]  Redis的应用场景？
  - [ ]  内存管理？
    - 删除过期键 -> 定期删除
    - 内存淘汰机制 -> allkeys-lru
  - [ ]  持久化？
    - AOF
  - [ ]  一致性？
    - Cache Aside Pattern -> 异步重试(消息队列)
  - [ ]  缓存穿透、缓存雪崩？
  - [ ]  高可用？
    - 哨兵模式
* RabbitMQ
  - [ ]  RabbitMQ的应用场景？
* ElasticSearch
  - [ ]  ElasticSearch的应用场景？
