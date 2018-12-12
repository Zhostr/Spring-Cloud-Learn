package zst.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: Spring Cloud Config Server
 * @author: Zhoust
 * @date: 2018/12/12 下午2:52
 * @version: V1.0
 */
@SpringBootApplication
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class);
    }

}