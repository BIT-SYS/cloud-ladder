package io.cloudladder.cloudladderserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties
public class CloudladderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudladderServerApplication.class, args);
    }

}
