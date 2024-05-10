package spring.annotation.aspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.annotation.aspect.annotation.JUserAnnotation;
import spring.annotation.aspect.bean.UserBean;
import spring.annotation.aspect.service.UserService;



@RestController
@RequestMapping("/myaspect")
public class UserController {

        @Autowired
        UserService userService;

        @GetMapping("/selSingleUser")
        public UserBean selSingleUser(){
            return userService.selSingleUser();
        }
}
