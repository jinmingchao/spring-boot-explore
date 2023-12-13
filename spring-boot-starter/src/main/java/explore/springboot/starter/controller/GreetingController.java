package explore.springboot.starter.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject greeting(@RequestParam(name = "name", required = false, defaultValue = "jinmingchao") String name, Model model) {
        System.out.println("name is " + name);
        JSONObject result = new JSONObject();
        result.put("code","ok");
        return result;
    }

}
