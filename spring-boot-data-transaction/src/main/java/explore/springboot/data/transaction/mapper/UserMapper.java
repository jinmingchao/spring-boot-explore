package explore.springboot.data.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;


public interface UserMapper extends BaseMapper<User> {
    Map<String, Object> selectUserById(Long id);
}
