package explore.springboot.data.transaction;


import explore.springboot.data.transaction.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Arrays;

//@MapperScan(basePackages = "explore.springboot.data.transaction.mapper")
@SpringBootApplication
public class ApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                System.out.println("--- Here is method commandLineRunner() ---");
                //通过这种方式拿到所有bean的名称
                String[] beanNames = ctx.getBeanDefinitionNames();
                //通过这种方式拿到指定名称的bean
                DataSource dataSource = (DataSource) ctx.getBean("dataSource");

                Arrays.sort(beanNames);
                for (String beanName : beanNames) {
                    System.out.println(beanName);
                }
            }
        };

    }
}


