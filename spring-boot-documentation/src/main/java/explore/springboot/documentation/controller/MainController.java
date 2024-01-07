package explore.springboot.documentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Model model) {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1);
        return "fe1";
    }
}
