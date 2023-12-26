package explore.springboot.starter.controller;


import com.alibaba.fastjson2.JSONObject;
import explore.springboot.starter.mdm.entity.User;
import explore.springboot.starter.mdm.vo.UserVO;
import explore.springboot.starter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jpa")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject userAdd(@RequestBody UserVO userVo) {
        JSONObject result = new JSONObject();
        result.put("code", "ok");
        try {
            User newUser = new User();
            newUser.setName(userVo.getName());
            newUser.setEmail(userVo.getEmail());
            userRepository.save(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "error");
        }
        return result;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
