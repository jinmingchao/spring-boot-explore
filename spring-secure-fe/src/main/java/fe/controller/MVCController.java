package fe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 只做视图的映射
 */
@Controller
public class MVCController {

    @GetMapping("/login")
    public String helloPage(){
        //跳转登录表单页面
        return "loginPage";
    }

    public String testCors()
}
