package fe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class FeApplicationStarter {

//    @Autowired
//    private static RedisCache redisCache;

    public static void main(String[] args) {
        SpringApplication.run(FeApplicationStarter.class, args);
    }

}


