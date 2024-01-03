package explore.springboot.data.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import explore.springboot.data.transaction.mdm.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMapper extends BaseMapper<Product> {
}
