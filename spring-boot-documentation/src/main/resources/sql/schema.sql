DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `t_documentation` (
           `id` int NOT NULL COMMENT '唯一主键',
           `ori_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文件名称',
           `extension` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文件后缀',
           `flag` varchar(255) DEFAULT NULL COMMENT '文件标记',
           `create_time` datetime NOT NULL COMMENT '创建日期',
           `update_time` datetime DEFAULT NULL COMMENT '更新日期',
           `is_deleted` int DEFAULT '0' COMMENT '逻辑删除',
           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci