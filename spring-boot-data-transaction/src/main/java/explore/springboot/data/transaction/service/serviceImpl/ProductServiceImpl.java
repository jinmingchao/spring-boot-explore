package explore.springboot.data.transaction.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import explore.springboot.data.transaction.mapper.ProductMapper;
import explore.springboot.data.transaction.mdm.entity.Product;
import explore.springboot.data.transaction.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {


}
