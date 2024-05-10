package spring.annotation.aspect.service.impl;

import org.springframework.stereotype.Service;
import spring.annotation.aspect.annotation.JUserAnnotation;
import spring.annotation.aspect.bean.UserBean;
import spring.annotation.aspect.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    @JUserAnnotation(firstVal = "xiaoming")
    public UserBean selSingleUser() {
        return getMockUser();
    }

    @JUserAnnotation(firstVal = "xiaoming")
    public UserBean getMockUser(){
        System.out.println("getMockUser()");
        return null;
    }


}
