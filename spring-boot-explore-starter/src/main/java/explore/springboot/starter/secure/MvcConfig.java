package explore.springboot.starter.secure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("homePage");
        registry.addViewController("/").setViewName("homePage");
        registry.addViewController("/hello").setViewName("helloPage");
        registry.addViewController("/login").setViewName("loginPage");
    }
}
