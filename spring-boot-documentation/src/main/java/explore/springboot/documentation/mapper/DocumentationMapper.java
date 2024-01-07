package explore.springboot.documentation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import explore.springboot.documentation.mdm.entity.Documentation;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface DocumentationMapper extends BaseMapper<Documentation> {

    Documentation selectDocumentationByPid(Long pid);
}
