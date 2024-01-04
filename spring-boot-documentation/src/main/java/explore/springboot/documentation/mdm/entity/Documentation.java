package explore.springboot.documentation.mdm.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@TableName("t_documentation")
public class Documentation {

    @TableId(value= "id", type= IdType.ASSIGN_ID)
    Integer id;  /** 唯一主键 **/

    String name; /** 文件名称 **/

    String path; /** 文件存储路径 **/

    @TableField("create_time")
    Date createTime; /** 创建日期 **/

    @TableField("update_time")
    Date updateTime; /** 更新日期 **/

    @TableField("is_deleted")
    @TableLogic
    Integer isDeleted; /** 逻辑删除 **/

}
