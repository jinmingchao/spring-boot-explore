package explore.springboot.data.transaction.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum SexEnum {

    FEMALE(0, "女"),

    MALE(1, "男");

    SexEnum(Integer sex, String description) {
        this.sex = sex;
        this.description = description;
    }

    @EnumValue //MybatisPlus将注释所标识的属性的值, 传入数据库
    private Integer sex;

    private String description;

}
