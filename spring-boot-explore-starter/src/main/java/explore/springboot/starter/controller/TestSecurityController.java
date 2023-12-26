package explore.springboot.starter.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestSecurityController {

    @RequestMapping(value = "/home123")
    public ModelAndView testViewController(ModelAndView model){
        model.setViewName("home");
        return model;
    }
}
