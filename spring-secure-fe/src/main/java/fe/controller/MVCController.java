package fe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 只做视图的映射
 */
@Controller
public class MVCController {

    @GetMapping("/login")
    public String helloPage() {
        //跳转登录表单页面
        return "loginPage";
    }


    @PostMapping("/loginlocal")
    public String testCors() {
        return "helloworld";
    }
}
