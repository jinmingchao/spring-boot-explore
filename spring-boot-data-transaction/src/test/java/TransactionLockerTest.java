import com.alibaba.fastjson2.JSONObject;
import explore.springboot.data.transaction.ApplicationStarter;
import explore.springboot.data.transaction.mapper.ProductMapper;
import explore.springboot.data.transaction.mdm.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = ApplicationStarter.class)
public class TransactionLockerTest {

    @Autowired
    ProductMapper productMapper;

    /**
     * 测试mybatis-plus乐观锁插件
     * 场景:
     * user1与user2同时查询并修改商品价格
     */
    @Test
    public void testTransactionLocker_0() {
        //user1 查询商品价格
        Product user1product = productMapper.selectById(1);
        //user2 查询商品价格
        Product user2product = productMapper.selectById(1);
        //user1 将价格+50
        user1product.setPrice(user1product.getPrice() + 50);
        int updatedCnt = productMapper.updateById(user1product);
        log.info("user1更新了" + updatedCnt + "条数据.");
        //user2 将商品-30
        user2product.setPrice(user2product.getPrice() - 30);
        updatedCnt = productMapper.updateById(user2product);
        log.info("user2更新了" + updatedCnt + "条数据.");
        //如果 乐观锁版本号无法匹配
        //则尝试重新查询最新价格再修改
        int loopTime = 0;
        while (updatedCnt == 0) { // 修改条数为0, 即修改失败
            loopTime += 1;
            if (loopTime == 3) break;// 3次则跳出逻辑, 返回
            log.info("user2修改失败,共计" + loopTime + "次.");
            user2product = productMapper.selectById(1);
            user2product.setPrice(user2product.getPrice() - 30);
            makeUser2FailsMoreTime(); //触发user2修改失败
            updatedCnt = productMapper.updateById(user2product);
        }

        log.info("最终结果: user2更新" + (updatedCnt == 0 ? "失败" : "成功"));
        //最后数据的结果
        Product rs = productMapper.selectById(1);
        System.out.println("商品最终结果为: " + JSONObject.toJSONString(rs));
    }


    /**
     * 让user2更新失败多次的方法
     *
     */
    public void makeUser2FailsMoreTime(){
            //user1再改一下
            Product user1product = productMapper.selectById(1);
            user1product.setPrice(user1product.getPrice() + 50);
            productMapper.updateById(user1product);
    }
}
