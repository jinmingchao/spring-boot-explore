package explore.springboot.data.transaction.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_info")
public class User {

    private Long id;

    private String name;

    private Integer age;

    private String email;

}
