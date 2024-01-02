package explore.springboot.data.transaction.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import explore.springboot.data.transaction.exception.checked.TransactionCheckedException_1;
import explore.springboot.data.transaction.exception.checked.TransactionCheckedException_2;
import explore.springboot.data.transaction.exception.unchecked.TransactionRuntimeException_1;
import explore.springboot.data.transaction.mapper.User;
import explore.springboot.data.transaction.mapper.UserMapper;
import explore.springboot.data.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 默认情况下
     * Any RuntimeException or Error triggers rollback, and any checked Exception does not
     * 抛出CheckedException不回滚, 抛出RuntimeException or Error这两种回滚
     */
    @Transactional
    public Object testTransaction_0() throws TransactionCheckedException_1, TransactionRuntimeException_1 {
        Map<String, Object> rMap = new HashMap();
        rMap.put("msg", "success");
        rMap.put("code", 200);

        Random nameSuffix = new Random(), age = new Random();
        User newUser;
        int n = 0;

        for (int i = 0; i < 100; i++) {
            newUser = User.builder().name("jinmingchao" + nameSuffix.nextInt(100)).age(age.nextInt(200)).email("44182174@qq.com").build();
            n += userMapper.insert(newUser);
            if (n == 50) {
                System.out.println("已插入" + n + "条数据, 抛出异常.");
                if ((newUser.getAge() & 1) == 0) {
                    throw new TransactionRuntimeException_1();
                } else {
                    throw new TransactionCheckedException_1();
                }
            }
        }

        System.out.println("共插入" + n + "条数据.");
        return rMap;
    }

    /**
     * 使用 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly() 手动进行回滚
     */
    @Transactional
    public Object testTransaction_1() {
        Map<String, Object> rMap = new HashMap();
        rMap.put("msg", "success");
        rMap.put("code", 200);

        Random nameSuffix = new Random(), age = new Random();
        User newUser;
        int n = 0;

        for (int i = 0; i < 100; i++) {
            newUser = User.builder().name("jinmingchao" + nameSuffix.nextInt(100)).age(age.nextInt(200)).email("44182174@qq.com").build();
            n += userMapper.insert(newUser);
            if (n == 50) {
                System.out.println("已插入" + n + "条数据, 抛出异常.");
                try {
                    throw new Exception("抛出异常");

                } catch (Exception e) {
                    e.printStackTrace();
                    //事务手动回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return rMap;
                }
            }
        }

        System.out.println("共插入" + n + "条数据.");
        return rMap;
    }

    /**
     * rollbackFor指定抛出哪些受检异常时回滚
     * 如下, 抛出Exception或TransactionTestException_1时回滚, TransactionTestException_2时不回滚
     */
    @Transactional(rollbackFor = {TransactionCheckedException_1.class, Exception.class})
    public Object testTransaction_2() throws TransactionCheckedException_1, TransactionCheckedException_2 {
        Map<String, Object> rMap = new HashMap();
        rMap.put("msg", "success");
        rMap.put("code", 200);

        Random nameSuffix = new Random(), age = new Random();
        User newUser;
        int n = 0;

        for (int i = 0; i < 100; i++) {
            newUser = User.builder().name("jinmingchao" + nameSuffix.nextInt(100)).age(age.nextInt(200)).email("44182174@qq.com").build();
            n += userMapper.insert(newUser);
            if (n == 50) {
                System.out.println("已插入" + n + "条数据, 抛出异常.");
                if ((newUser.getAge() & 1) == 0) {
                    throw new TransactionCheckedException_1();
                } else {
                    throw new TransactionCheckedException_2();
                }
            }
        }
        System.out.println("共插入" + n + "条数据.");
        return rMap;
    }

    @Transactional
    public List testTransaction_3() {
        // 1. 插入
        Random r = new Random();
        User newUser = User.builder().name("jinmingchao" + r.nextInt(100)).age(r.nextInt(200)).email("44182174@qq.com").build();
        int i = userMapper.insert(newUser);
        System.out.println("插入了" + i + "条数据");
        // 2. 查询
        List<User> uList = userMapper.selectList(null);
        // 3. 删除
        i = userMapper.deleteById(uList.get(0));
        System.out.println("删除了" + i + "条数据");
        return uList;
    }
}
