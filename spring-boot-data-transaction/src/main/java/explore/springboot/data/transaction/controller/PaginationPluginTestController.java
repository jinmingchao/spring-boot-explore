package explore.springboot.data.transaction.controller;


import explore.springboot.data.transaction.exception.checked.TransactionCheckedException_1;
import explore.springboot.data.transaction.mapper.UserMapper;
import explore.springboot.data.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paginationTest")
public class PaginationPluginTestController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @GetMapping("/testPagination_0")
    public Object testPagination_0()  {
        return userService.testPagination_0();
    }
}
