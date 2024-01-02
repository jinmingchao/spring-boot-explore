package explore.springboot.data.transaction.controller;

import explore.springboot.data.transaction.exception.checked.TransactionCheckedException_1;
import explore.springboot.data.transaction.exception.checked.TransactionCheckedException_2;
import explore.springboot.data.transaction.mapper.UserMapper;
import explore.springboot.data.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 *  SpringBoot 声明式事务@Transactional 功能测试
 *
 *
 */
@RestController
@RequestMapping("/TAtest")
public class TransactionTestController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @GetMapping("/testTransaction_0")
    public Object testTransaction_0() throws TransactionCheckedException_1, Exception {
        return userService.testTransaction_0();
    }

    @GetMapping("/testTransaction_1")
    public Object testTransaction_1() {
        return userService.testTransaction_1();
    }

    @GetMapping("/testTransaction_2")
    public Object testTransaction_2() throws TransactionCheckedException_1, TransactionCheckedException_2 {
        return userService.testTransaction_2();
    }


}
