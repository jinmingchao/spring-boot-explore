DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
    `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_name` varchar(30) DEFAULT NULL COMMENT '姓名',
    `age` int DEFAULT NULL COMMENT '年龄',
    `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
    `deleted` int DEFAULT '0' COMMENT '逻辑删除标记',
    PRIMARY KEY (`uid`) USING BTREE
);


CREATE TABLE `t_product` (
     `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name` varchar(128) DEFAULT NULL COMMENT '商品名称',
    `price` int DEFAULT NULL COMMENT '商品价格',
    `version` int DEFAULT '0' COMMENT '乐观锁版本号',
    PRIMARY KEY (`id`)
);