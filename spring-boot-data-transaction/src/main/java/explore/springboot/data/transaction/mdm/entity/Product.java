package explore.springboot.data.transaction.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("t_product")
public class Product {

    @TableId
    private Long id;

    private String name;

    private Integer price;

    @Version //标识乐观锁版本号字段
    private Integer version;

}
