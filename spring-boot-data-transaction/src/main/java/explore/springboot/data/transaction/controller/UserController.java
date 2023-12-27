package explore.springboot.data.transaction.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import explore.springboot.data.transaction.mapper.User;
import explore.springboot.data.transaction.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @PostMapping("/insert")
    public JSONObject addUser(@RequestBody User newUser){
        JSONObject result = new JSONObject();
//        UpdateWrapper<User> userAddWrapper = new UpdateWrapper<>();
//        userAddWrapper.setEntity(newUser);
        int v = userMapper.insert(newUser);
        result.put("code",200);
        return result;
    }
}
