package explore.springboot.data.transaction.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import explore.springboot.data.transaction.mapper.User;
import explore.springboot.data.transaction.mapper.UserMapper;
import explore.springboot.data.transaction.utils.ControllerResultGenerator;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController implements ApplicationRunner {

    public ControllerResultGenerator resultGenerator;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/insert")
    public JSONObject addUser(@RequestBody User newUser) {
        JSONObject result = new JSONObject();
//        UpdateWrapper<User> userAddWrapper = new UpdateWrapper<>();
//        userAddWrapper.setEntity(newUser);
        int v = userMapper.insert(newUser);
        System.out.println("v: " + v);
        result.put("code", 200);

        return result;
    }

    @GetMapping("/queryAll")
    public JSONObject queryAllUser() {
        JSONObject result = new JSONObject();
        List<User> allUsers = userMapper.selectList(null);
        result.put("result", JSONArray.toJSONString(allUsers));
        return result;
    }

    @GetMapping("/selectUserById/{id}")
    public Object selectUserById(@PathVariable("id") Long id) {
        Map<String, Object> rMap = userMapper.selectUserById(id);
        JSONObject resultObject = resultGenerator.


    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        resultGenerator = new ControllerResultGenerator();
    }
}
