DROP TABLE IF EXISTS `t_documentation`;

CREATE TABLE `t_documentation` (
                                   `id` bigint(20) NOT NULL COMMENT '唯一主键',
                                   `pid` bigint(20) DEFAULT '-1' COMMENT '对应初始版本文件id, -1: 自身即为初始版本',
                                   `ori_name` varchar(255) DEFAULT NULL COMMENT '文件名称',
                                   `extension` varchar(255) DEFAULT NULL COMMENT '文件后缀',
                                   `flag` varchar(255) DEFAULT NULL COMMENT '文件标记',
                                   `create_time` datetime NOT NULL COMMENT '创建日期',
                                   `update_time` datetime NOT NULL COMMENT '更新日期',
                                   `is_deleted` int(11) DEFAULT '0' COMMENT '逻辑删除',
                                   PRIMARY KEY (`id`),
                                   KEY `idx_pid` (`pid`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4