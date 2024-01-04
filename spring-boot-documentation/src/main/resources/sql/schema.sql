DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `t_documentation` (
        `id` int NOT NULL COMMENT '唯一主键',
        `name` varchar(255) DEFAULT NULL COMMENT '文件名称',
        `path` varchar(255)  DEFAULT NULL COMMENT '文件存储路径',
        `create_time` datetime NOT NULL COMMENT '创建日期',
        `update_time` datetime DEFAULT NULL COMMENT '更新日期',
        `is_deleted` int DEFAULT '0' COMMENT '逻辑删除',
        PRIMARY KEY (`id`)
);