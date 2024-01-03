package explore.springboot.data.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import explore.springboot.data.transaction.mdm.entity.User;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User> {
    Map<String, Object> selectUserById(Long id);
}
