package explore.springboot.data.transaction.mdm.entity;

import com.baomidou.mybatisplus.annotation.*;
import explore.springboot.data.transaction.enums.SexEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("user_info")
public class User {

    // @TableId: 指定主键字段
    // value = "uid": 将id映射到表中的字段是uid
    // type = IdType.AUTO 设置id生成方式为:type= IdType.AUTO(自增)
    //        默认是: IdType.ASSIGN_ID(默认): 雪花算法生成id
    //        雪花算法: 生成一个8字节Long类型数据，保证单表递增以及全局不重复
    @TableId(value= "uid", type= IdType.AUTO)
    private Long id;

    //设置属性对应的字段名称
    @TableField("user_name")
    private String name;

    private Integer age;

    private SexEnum sex;

    private String email;

    @TableField("deleted")
    @TableLogic // 逻辑删字段, 默认设置值为0即可
                // 在查询的时候mybatis会去掉deleted=1的(已经逻辑删了的)行
    private Integer isDeleted;

}
