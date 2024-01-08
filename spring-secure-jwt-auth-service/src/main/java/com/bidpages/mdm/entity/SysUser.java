package com.bidpages.mdm.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_user")
public class SysUser implements Serializable {
    private static final Long serialVersionUid = -40356785423868312L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号状态(0:正常 1:停用)
     */
    private String status;


    /**
     * 邮箱
     */
    private String email;


    /**
     * 手机号
     */
    @TableField("phonenumber")
    private String phoneNum;

    /**
     * 用户性别 (0男, 1女, 2未知)
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户类型(0管理员, 1普通用户)
     */
    @TableField("user_type")
    private String userType;

    /**
     * 创建人用户Id
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField("update_by")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 删除标记(0未删除, 1删除)
     */
    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;
}
