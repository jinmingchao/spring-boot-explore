package spring.annotation.aspect;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class AnnotationAspectApplicationStarter {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(AnnotationAspectApplicationStarter.class, args);
        System.out.println("---  PRINT BEAN NAME START ---");
        for(String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println("---  PRINT BEAN NAME END ---");
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                System.out.println("--- Here is method commandLineRunner() ---");
//                //通过这种方式拿到所有bean的名称
//                String[] beanNames = ctx.getBeanDefinitionNames();
//                for (String beanName : beanNames) {
//                    System.out.println(beanName);
//                }
//            }
//        };
//    }

}


