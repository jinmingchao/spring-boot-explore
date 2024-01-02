package explore.springboot.data.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import explore.springboot.data.transaction.exception.checked.TransactionCheckedException_1;
import explore.springboot.data.transaction.exception.checked.TransactionCheckedException_2;
import explore.springboot.data.transaction.mapper.User;

public interface UserService extends IService<User> {

    Object testTransaction_0() throws Exception, TransactionCheckedException_1;

    Object testTransaction_1();

    Object testTransaction_2() throws TransactionCheckedException_1, TransactionCheckedException_2;

//     List testTransaction_2();
}
