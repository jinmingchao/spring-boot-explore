package explore.springboot.documentation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import explore.springboot.documentation.mdm.entity.Documentation;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentationMapper extends BaseMapper<Documentation> {

}
