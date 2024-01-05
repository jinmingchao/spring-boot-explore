package explore.springboot.documentation.mdm.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@TableName("t_documentation")
public class Documentation {
    /**
     * 唯一主键
     **/
    @TableId(value = "id")
    Integer id;

    /**
     * 文件原名称
     **/
    @TableField("ori_name")
    String oriName;

    /**
     * 文件后缀
     **/
    String extension;

    /**
     * 文件标记
     **/
    String flag;

    /**
     * 创建日期
     **/
    @TableField("create_time")
    Date createTime;

    /**
     * 更新日期
     **/
    @TableField("update_time")
    Date updateTime;

    /**
     * 逻辑删除
     **/
    @TableField("is_deleted")
    @TableLogic
    Integer isDeleted;

}
