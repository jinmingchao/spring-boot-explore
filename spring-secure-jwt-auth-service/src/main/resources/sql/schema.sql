DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user`
(
    `id`          bigint NOT NULL COMMENT '主键',
    `user_name`   varchar(64)  DEFAULT NULL COMMENT '用户名',
    `nick_name`   varchar(64)  DEFAULT NULL COMMENT '昵称',
    `password`    varchar(64)  DEFAULT NULL COMMENT '密码',
    `status`      char(1)      DEFAULT '0' COMMENT '账号状态(0:正常 1:停用)',
    `email`       varchar(64)  DEFAULT NULL COMMENT '邮箱',
    `phonenumber` varchar(32)  DEFAULT NULL COMMENT '手机号',
    `sex`         char(1)      DEFAULT NULL COMMENT '用户性别 (0男, 1女, 2未知)',
    `avatar`      varchar(128) DEFAULT NULL COMMENT '头像',
    `user_type`   char(1)      DEFAULT '1' COMMENT '用户类型(0管理员, 1普通用户)',
    `create_by`   bigint       DEFAULT NULL COMMENT '创建人用户Id',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`   bigint       DEFAULT NULL COMMENT '更新人用户Id',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `del_flag`    int          DEFAULT '0' COMMENT '删除标记(0未删除, 1删除)',
    PRIMARY KEY (`id`)
);