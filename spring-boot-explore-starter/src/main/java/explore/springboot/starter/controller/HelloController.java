package explore.springboot.starter.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController = @Controller + @ResponseBody
//返回数据, 不返回试图

@RestController
public class HelloController {

//    @GetMapping("/")
//    public String index(){
//        return "Hello world.";
//    }

    @GetMapping("/testRestController")
    public JSONObject test_1() {
        JSONObject res = new JSONObject();
        res.put("code","200");
        res.put("msg","success");
        return res;
    }


}
