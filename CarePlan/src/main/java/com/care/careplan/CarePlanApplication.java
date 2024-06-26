package com.care.careplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CarePlanApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarePlanApplication.class, args);
    }

}
