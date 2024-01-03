package explore.springboot.data.transaction.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import explore.springboot.data.transaction.mdm.entity.User;
import explore.springboot.data.transaction.mapper.UserMapper;
import explore.springboot.data.transaction.service.UserService;
import explore.springboot.data.transaction.utils.ControllerResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 *   SpringBoot + Mybatis Plus基本功能测试
 *
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController implements ApplicationRunner {

    public ControllerResultGenerator resultGenerator;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @PostMapping("/insert")
    public JSONObject addUser(@RequestBody User newUser) {
        JSONObject result = new JSONObject();
        int v = userMapper.insert(newUser);
        System.out.println("v: " + v);
        result.put("code", 200);

        return result;
    }

    @GetMapping("/queryAll")
    public Object queryAllUser() {
        List<User> allUsers = userMapper.selectList(null);
        return allUsers;
    }

    /**
     * 测试 Mybatis Plus 自定义mapper
     *
     * @param id
     * @return
     */
    @GetMapping("/selectUserById/{id}")
    public Object selectUserById(@PathVariable("id") Long id) {
        Map<String, Object> rMap = userMapper.selectUserById(id);
        return rMap;
    }

    @GetMapping("/test_performance_schema")
    public Object testPerformanceSchema() {
        Map<String, Object> rMap = new HashMap();
        rMap.put("msg", "success");
        rMap.put("code", 200);

        Random nameSuffix = new Random(), age = new Random();
        User newUser;
        int n = 0;


        while (true) {
            newUser = User.builder().name("jinmingchao" + nameSuffix.nextInt(100)).age(age.nextInt(200)).email("44182174@qq.com").build();
            n += userMapper.insert(newUser);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (n == 100) {
                break;
            }
        }
        return rMap;
    }

    @GetMapping("/logicdel/{id}")
    public Object testPerformanceSchema(@PathVariable("id") Integer id) {
        Map<String, Object> rMap = new HashMap();
        rMap.put("msg", "success");
        rMap.put("code", 200);

        int cnt = userMapper.deleteById(id);
        log.info("删除"+cnt+"条数据.");

        return rMap;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        resultGenerator = new ControllerResultGenerator();
    }
}
