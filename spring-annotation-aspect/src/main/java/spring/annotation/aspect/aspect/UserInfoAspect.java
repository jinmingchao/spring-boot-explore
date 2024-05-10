package spring.annotation.aspect.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserInfoAspect {

    @Pointcut("@annotation(spring.annotation.aspect.annotation.JUserAnnotation)")
    public void access() {
        System.out.println("access()!!");
    }

//    @Pointcut("@execution(spring.annotation.aspect.service.impl.*)")

    @Before("access()")
    public void beforeAccess(JoinPoint joinPoint){
        System.out.println("beforeAccess()");
    }

    @After("access()")
    public void afterAccess(JoinPoint joinPoint){
        System.out.println("afterAccess()");
    }

    @Around("execution(spring.annotation.aspect.bean.UserBean spring.annotation.aspect.service.impl.UserServiceImpl.getMockUser())")
    public void aroundAccess(){
        System.out.println("aroundAccess");
    }
//    @AfterReturning(value="Pointcut()",returning="result")
//    public void afterReturningMethod(JoinPoint joinPoint,Object result){
//        log.info("调用了返回通知,result :{}",result);
//    }


}
