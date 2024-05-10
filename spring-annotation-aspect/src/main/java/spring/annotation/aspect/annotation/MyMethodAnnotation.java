package spring.annotation.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //只能用来标记方法
public @interface MyMethodAnnotation {

}
